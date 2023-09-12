package thestonedturtle.bankedexperience;

import com.google.inject.Provides;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.swing.SwingUtilities;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.InventoryID;
import net.runelite.api.Item;
import net.runelite.api.ItemComposition;
import net.runelite.api.ItemContainer;
import net.runelite.api.events.AccountHashChanged;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.api.events.WidgetLoaded;
import net.runelite.api.widgets.Widget;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.game.ItemManager;
import net.runelite.client.game.SkillIconManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;
import thestonedturtle.bankedexperience.data.Activity;
import thestonedturtle.bankedexperience.data.ExperienceItem;
import thestonedturtle.bankedexperience.data.WidgetInventoryInfo;
import thestonedturtle.bankedexperience.data.modifiers.Modifiers;

@Slf4j
@PluginDescriptor(
	name = "Banked Experience"
)
public class BankedExperiencePlugin extends Plugin
{
	private static final BufferedImage ICON = ImageUtil.loadImageResource(BankedExperiencePlugin.class, "banked.png");
	private static final Map<Integer, Integer> EMPTY_MAP = new HashMap<>();
	public static final String CONFIG_GROUP = "bankedexperience";
	public static final String VAULT_CONFIG_KEY = "grabFromSeedVault";
	public static final String INVENTORY_CONFIG_KEY = "grabFromInventory";
	public static final String LOOTING_BAG_CONFIG_KEY = "grabFromLootingBag";
	public static final String FOSSIL_CHEST_CONFIG_KEY = "grabFromFossilChest";
	public static final String SECONDARY_SHOW_MODE_KEY = "showWhatSecondaries";
	public static final String ACTIVITY_CONFIG_KEY = "ITEM_";
	private static final int LOOTING_BAG_ID = 516;

	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Inject
	private ClientToolbar clientToolbar;

	@Inject
	private ConfigManager configManager;

	@Inject
	private ItemManager itemManager;

	@Inject
	private SkillIconManager skillIconManager;

	@Inject
	private BankedExperienceConfig config;

	@Provides
	BankedExperienceConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(BankedExperienceConfig.class);
	}

	private final Map<Integer, Integer> inventoryHashMap = new HashMap<>();
	private NavigationButton navButton;
	private BankedCalculatorPanel panel;
	private boolean prepared = false;
	private long accountHash = -1;

	@Override
	protected void startUp() throws Exception
	{
		panel = new BankedCalculatorPanel(client, config, skillIconManager, itemManager, configManager);
		navButton = NavigationButton.builder()
			.tooltip("Banked XP")
			.icon(ICON)
			.priority(6)
			.panel(panel)
			.build();

		clientToolbar.addNavigation(navButton);

		accountHash = client.getAccountHash();

		if (!prepared)
		{
			clientThread.invoke(() ->
			{
				switch (client.getGameState())
				{
					case LOGIN_SCREEN:
					case LOGIN_SCREEN_AUTHENTICATOR:
					case LOGGING_IN:
					case LOADING:
					case LOGGED_IN:
					case CONNECTION_LOST:
					case HOPPING:
						ExperienceItem.prepareItemCompositions(itemManager);
						Activity.prepareItemCompositions(itemManager);
						Modifiers.prepare(itemManager);
						loadSavedActivities();
						prepared = true;
						return true;
					default:
						return false;
				}
			});
		}
	}

	@Override
	protected void shutDown() throws Exception
	{
		clientToolbar.removeNavigation(navButton);
		panel = null;
		navButton = null;
		inventoryHashMap.clear();
		accountHash = -1;
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (!event.getGroup().equals(CONFIG_GROUP))
		{
			return;
		}

		final int inventoryId;
		switch (event.getKey())
		{
			case VAULT_CONFIG_KEY:
				inventoryId = InventoryID.SEED_VAULT.getId();
				break;
			case INVENTORY_CONFIG_KEY:
				inventoryId = InventoryID.INVENTORY.getId();
				break;
			case LOOTING_BAG_CONFIG_KEY:
				inventoryId = LOOTING_BAG_ID;
				break;
			case FOSSIL_CHEST_CONFIG_KEY:
				inventoryId = WidgetInventoryInfo.FOSSIL_CHEST.getId();
				break;
			default:
				return;
		}

		SwingUtilities.invokeLater(() -> panel.setInventoryMap(inventoryId, EMPTY_MAP));
	}

	@Subscribe
	public void onItemContainerChanged(ItemContainerChanged ev)
	{
		if (ev.getContainerId() == InventoryID.BANK.getId()
			|| (ev.getContainerId() == InventoryID.SEED_VAULT.getId() && config.grabFromSeedVault())
			|| (ev.getContainerId() == InventoryID.INVENTORY.getId() && config.grabFromInventory())
			|| (ev.getContainerId() == LOOTING_BAG_ID && config.grabFromLootingBag()))
		{
			updateItemsFromItemContainer(ev.getContainerId(), ev.getItemContainer());
		}
	}

	@Subscribe
	public void onWidgetLoaded(WidgetLoaded e)
	{
		if (!config.grabFromFossilChest())
		{
			return;
		}

		final WidgetInventoryInfo widgetInfo = WidgetInventoryInfo.getByGroupId(e.getGroupId());
		if (widgetInfo == null)
		{
			return;
		}

		final Widget w = client.getWidget(widgetInfo.getGroupId(), widgetInfo.getChildId());
		if (w == null || w.getChildren() == null)
		{
			return;
		}

		final Map<Integer, Integer> m = new HashMap<>();
		for (int i = 0; i < w.getChildren().length; i++)
		{
			final Widget childWidget = w.getChild(i);
			if (childWidget.getItemId() <= 0 || childWidget.getItemQuantity() <= 0)
			{
				continue;
			}

			m.merge(childWidget.getItemId(), childWidget.getItemQuantity(), Integer::sum);
		}

		updateInventoryMap(widgetInfo.getId(), m);
	}

	@Subscribe
	public void onAccountHashChanged(AccountHashChanged e)
	{
		if (accountHash == client.getAccountHash())
		{
			return;
		}
		accountHash = client.getAccountHash();
		inventoryHashMap.clear();

		SwingUtilities.invokeLater(panel::resetInventoryMaps);
	}

	private void updateItemsFromItemContainer(final int inventoryId, final ItemContainer c)
	{
		// Check if the contents have changed.
		if (c == null)
		{
			return;
		}

		final Map<Integer, Integer> m = new HashMap<>();
		for (Item item : c.getItems())
		{
			if (item.getId() == -1)
			{
				continue;
			}

			// Account for noted items, ignore placeholders.
			int itemID = item.getId();
			final ItemComposition itemComposition = itemManager.getItemComposition(itemID);
			if (itemComposition.getPlaceholderTemplateId() != -1)
			{
				continue;
			}

			if (itemComposition.getNote() != -1)
			{
				itemID = itemComposition.getLinkedNoteId();
			}

			final int qty = m.getOrDefault(itemID, 0) + item.getQuantity();
			m.put(itemID, qty);
		}

		updateInventoryMap(inventoryId, m);
	}

	private void updateInventoryMap(final int inventoryId, final Map<Integer, Integer> m)
	{
		final int curHash = m.hashCode();
		if (curHash != inventoryHashMap.getOrDefault(inventoryId, -1))
		{
			inventoryHashMap.put(inventoryId, curHash);
			SwingUtilities.invokeLater(() -> panel.setInventoryMap(inventoryId, m));
		}
	}

	private void loadSavedActivities()
	{
		for (final ExperienceItem item : ExperienceItem.values())
		{
			final String activityName = configManager.getConfiguration(CONFIG_GROUP, ACTIVITY_CONFIG_KEY + item.name());
			if (activityName == null || activityName.equals(""))
			{
				continue;
			}

			for (final Activity activity : Activity.values())
			{
				if (activityName.equals(activity.name()))
				{
					item.setSelectedActivity(activity);
					break;
				}
			}
		}
	}
}
