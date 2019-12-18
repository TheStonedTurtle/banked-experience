package thestonedturtle.bankedexperience;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class BankedExperiencePluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(BankedExperiencePlugin.class);
		RuneLite.main(args);
	}
}