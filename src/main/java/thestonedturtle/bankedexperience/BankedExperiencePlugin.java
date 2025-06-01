package thestonedturtle.bankedexperience;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.EnumComposition;
import net.runelite.api.EnumID;
import net.runelite.api.InventoryID;
import net.runelite.api.Item;
import net.runelite.api.ItemComposition;
import net.runelite.api.ItemContainer;
import net.runelite.api.ScriptID;
import net.runelite.api.events.AccountHashChanged;
import net.runelite.api.events.ClientTick;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.api.events.ScriptPostFired;
import net.runelite.api.events.VarbitChanged;
import net.runelite.api.events.WidgetLoaded;
import net.runelite.api.widgets.ComponentID;
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

import javax.inject.Inject;
import javax.swing.SwingUtilities;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static thestonedturtle.bankedexperience.BankedExperienceConfig.POTION_STORAGE_KEY;

@Slf4j
@PluginDescriptor(
		name = "Banked Experience"
)
public class BankedExperiencePlugin extends Plugin
{
	private static final BufferedImage ICON = ImageUtil.loadImageResource(BankedExperiencePlugin.class, "banked.png");
	private static final Map<Integer, Integer> EMPTY_MAP = new HashMap<>();
	public static final String CONFIG_GROUP = "bankedexperience";
	private static final String VAULT_CONFIG_KEY = "grabFromSeedVault";
	private static final String INVENTORY_CONFIG_KEY = "grabFromInventory";
	private static final String LOOTING_BAG_CONFIG_KEY = "grabFromLootingBag";
	private static final String FOSSIL_CHEST_CONFIG_KEY = "grabFromFossilChest";
	public static final String ACTIVITY_CONFIG_KEY = "ITEM_";
	private static final int LOOTING_BAG_ID = 516;

	private static final int POTION_STORAGE_FAKE_INVENTORY_ID = -420;

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

	private boolean rebuildPotions = false;
	private Set<Integer> potionStoreVars;

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

		clientThread.invoke(() ->
		{
			switch (client.getGameState())
			{
				case LOGGED_IN:
					if (config.grabFromPotionStorage() && client.getItemContainer(InventoryID.BANK) != null)
					{
						rebuildPotions = true;
					}
					// intentional fall through
				case LOGIN_SCREEN:
				case LOGIN_SCREEN_AUTHENTICATOR:
				case LOGGING_IN:
				case LOADING:
				case CONNECTION_LOST:
				case HOPPING:
					if (!prepared)
					{
						ExperienceItem.prepareItemCompositions(itemManager);
						Activity.prepareItemCompositions(itemManager);
						Modifiers.prepare(itemManager);
						loadSavedActivities();
						prepared = true;
					}
					return true;
				default:
					return false;
			}
		});
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
			case POTION_STORAGE_KEY:
				inventoryId = POTION_STORAGE_FAKE_INVENTORY_ID;
				if (config.grabFromPotionStorage())
				{
					clientThread.invoke(() ->
					{
						if (client.getItemContainer(InventoryID.BANK) != null)
						{
							rebuildPotions = true;
						}
					});
				}
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

	@Subscribe
	public void onScriptPostFired(ScriptPostFired event)
	{
		if (event.getScriptId() == ScriptID.BANKMAIN_FINISHBUILDING && config.grabFromPotionStorage())
		{
			rebuildPotions = true;
		}
	}

	@Subscribe
	public void onClientTick(ClientTick event)
	{
		if (rebuildPotions)
		{
			updatePotionStorageMap();
			rebuildPotions = false;

			Widget w = client.getWidget(ComponentID.BANK_POTIONSTORE_CONTENT);
			if (w != null && potionStoreVars == null)
			{
				// cache varps that the potion store rebuilds on
				int[] trigger = w.getVarTransmitTrigger();
				potionStoreVars = new HashSet<>();
				Arrays.stream(trigger).forEach(potionStoreVars::add);
			}
		}
	}

	@Subscribe
	public void onVarbitChanged(VarbitChanged varbitChanged)
	{
		if (potionStoreVars != null && potionStoreVars.contains(varbitChanged.getVarpId()))
		{
			rebuildPotions = true;
		}
	}

	// Copied mostly from BankPlugin:getPotionStoragePrice
	private void updatePotionStorageMap()
	{
		final Map<Integer, Integer> potionQtyMap = new HashMap<>();

		EnumComposition potionStorePotions = client.getEnum(EnumID.POTIONSTORE_POTIONS);
		EnumComposition potionStoreUnfinishedPotions = client.getEnum(EnumID.POTIONSTORE_UNFINISHED_POTIONS);
		for (EnumComposition e : new EnumComposition[]{potionStorePotions, potionStoreUnfinishedPotions})
		{
			for (int potionEnumId : e.getIntVals())
			{
				EnumComposition potionEnum = client.getEnum(potionEnumId);
				client.runScript(ScriptID.POTIONSTORE_DOSES, potionEnumId);
				int doses = client.getIntStack()[0];

				if (doses > 0)
				{
					// Always pull the item ID for 1-dose potions
					final int itemId = potionEnum.getIntValue(1);
					potionQtyMap.put(itemId, doses);
				}
			}
		}

		updateInventoryMap(POTION_STORAGE_FAKE_INVENTORY_ID, potionQtyMap);
	}
}
