package thestonedturtle.bankedexperience;

import com.github.thestonedturtle.bankedexperience.data.Activity;
import com.github.thestonedturtle.bankedexperience.data.ExperienceItem;
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
import net.runelite.api.ItemContainer;
import net.runelite.api.events.ScriptCallbackEvent;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemManager;
import net.runelite.client.game.SkillIconManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;

@Slf4j
@PluginDescriptor(
	name = "Banked Experience"
)
public class BankedExperiencePlugin extends Plugin
{
	private final static BufferedImage ICON = ImageUtil.getResourceStreamFromClass(BankedExperiencePlugin.class, "banked.png");

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

	private NavigationButton navButton;
	private BankedCalculatorPanel panel;
	private boolean prepared = false;
	private int bankHash = -1;
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
	}

	@Subscribe
	public void onScriptCallbackEvent(ScriptCallbackEvent event)
	{
		if (!event.getEventName().equals("setBankTitle") || client.getTickCount() == lastCheckTick)
		{
			return;
		}

		log.info("Running");
		// Check if the contents have changed.
		final ItemContainer c = client.getItemContainer(InventoryID.BANK);
		if (c == null)
		{
			return;
		}

		final Item[] widgetItems = c.getItems();
		if (widgetItems == null || widgetItems.length == 0)
		{
			return;
		}

		final Map<Integer, Integer> m = new HashMap<>();
		for (Item widgetItem : widgetItems)
		{
			m.put(widgetItem.getId(), widgetItem.getQuantity());
		}

		final int curHash = m.hashCode();
		if (bankHash != curHash)
		{
			bankHash = curHash;
			SwingUtilities.invokeLater(() -> panel.setBankMap(m));
		}

		lastCheckTick = client.getTickCount();
	}
}
