package thestonedturtle.bankedexperience;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class BankedExperiencePluginTest
{
	
	public static void main(String[] args) throws Exception
	{
		loadBankedExperiencePlugin();
		RuneLite.main(args);
	}

	/*
	 * Simply calls ExternalPluginManager with unchecked warnings turned off
	 * because that runelite method lacks the SafeVarArgs annotation the JDK
	 * expects and generates a warning for no reason that generates a gradle
	 * report otherwise
	 */
	@SuppressWarnings("unchecked")
	private static final void loadBankedExperiencePlugin()
	{
		ExternalPluginManager.loadBuiltin(BankedExperiencePlugin.class);
	}
}
