package thestonedturtle.bankedexperience;

import com.google.inject.Provides;
import java.awt.image.BufferedImage;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
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
	private boolean prepared = false;

	@Override
	protected void startUp() throws Exception
	{
		final BankedCalculatorPanel panel = new BankedCalculatorPanel(client, config, skillIconManager, itemManager);
		navButton = NavigationButton.builder()
			.tooltip("Banked XP")
			.icon(ICON)
			.priority(6)
			.panel(panel)
			.build();

		clientToolbar.addNavigation(navButton);
	}

	@Override
	protected void shutDown() throws Exception
	{
		clientToolbar.removeNavigation(navButton);
	}
}
