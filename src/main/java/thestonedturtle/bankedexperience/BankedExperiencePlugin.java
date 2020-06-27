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
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.api.events.ScriptCallbackEvent;
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

@Slf4j
@PluginDescriptor(
	name = "Banked Experience"
)
public class BankedExperiencePlugin extends Plugin
{
	private static final BufferedImage ICON = ImageUtil.getResourceStreamFromClass(BankedExperiencePlugin.class, "banked.png");
	private static final Map<Integer, Integer> EMPTY_MAP = new HashMap<>();
	private static final String CONFIG_GROUP = "bankedexperience";
	private static final String VAULT_CONFIG_KEY = "grabFromSeedVault";
	private static final String INVENTORY_CONFIG_KEY = "grabFromInventory";
	private static final String LOOTING_BAG_CONFIG_KEY = "grabFromLootingBag";
	private static final int LOOTING_BAG_ID = 516;

	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Inject
	private ClientToolbar clientToolbar;

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
	private int lastCheckTick = -1;

	@Override
	protected void startUp() throws Exception
	{
		panel = new BankedCalculatorPanel(client, config, skillIconManager, itemManager);
		navButton = NavigationButton.builder()
			.tooltip("Banked XP")
			.icon(ICON)
			.priority(6)
			.panel(panel)
			.build();

		clientToolbar.addNavigation(navButton);

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
			default:
				return;
		}

		SwingUtilities.invokeLater(() -> panel.setInventoryMap(inventoryId, EMPTY_MAP));
	}

	@Subscribe
	public void onScriptCallbackEvent(ScriptCallbackEvent event)
	{
		if (!event.getEventName().equals("setBankTitle") || client.getTickCount() == lastCheckTick)
		{
			return;
		}

		updateItemsFromInventory(InventoryID.BANK);
		lastCheckTick = client.getTickCount();
	}

	@Subscribe
	public void onItemContainerChanged(ItemContainerChanged ev)
	{
		if ((ev.getContainerId() == InventoryID.SEED_VAULT.getId() && config.grabFromSeedVault())
			|| (ev.getContainerId() == InventoryID.INVENTORY.getId() && config.grabFromInventory())
			|| (ev.getContainerId() == LOOTING_BAG_ID && config.grabFromLootingBag()))
		{
			updateItemsFromItemContainer(ev.getContainerId(), ev.getItemContainer());
		}
	}

	private void updateItemsFromInventory(final InventoryID inventoryID)
	{
		updateItemsFromItemContainer(inventoryID.getId(), client.getItemContainer(inventoryID));
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

		final int curHash = m.hashCode();
		if (curHash != inventoryHashMap.getOrDefault(inventoryId, -1))
		{
			inventoryHashMap.put(inventoryId, curHash);
			SwingUtilities.invokeLater(() -> panel.setInventoryMap(inventoryId, m));
		}
	}
}
