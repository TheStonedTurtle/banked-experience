package thestonedturtle.bankedexperience;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import thestonedturtle.bankedexperience.config.SecondaryMode;

@ConfigGroup("bankedexperience")
public interface BankedExperienceConfig extends Config
{
	@ConfigItem(
		keyName = "cascadeBankedXp",
		name = "Include output items",
		description = "Includes output items in the item quantity calculations",
		position = 1
	)
	default boolean cascadeBankedXp()
	{
		return true;
	}

	//No longer used, kept alive for migration purposes.
	@ConfigItem(
		keyName = "showSecondaries",
		name = "",
		description = "",
		hidden = true
	)
	default boolean showSecondaries()
	{
		return false;
	}

	@ConfigItem(
			keyName = BankedExperiencePlugin.SECONDARY_SHOW_MODE_KEY,
			name = "Show secondaries",
			description = "Toggles whether any, required or all secondaries will be displayed for the selected items",
			position = 2
	)
	default SecondaryMode showWhatSecondaries()
	{
		return showSecondaries() ? SecondaryMode.ALL : SecondaryMode.NONE;
	}

	@ConfigItem(
		keyName = "limitToCurrentLevel",
		name = "Respect level requirements",
		description = "Toggles whether the exp calculation will limit to your current skill level",
		position = 3
	)
	default boolean limitToCurrentLevel()
	{
		return true;
	}

	@ConfigItem(
		keyName = BankedExperiencePlugin.VAULT_CONFIG_KEY,
		name = "Include seed vault",
		description = "Toggles whether the items stored inside the Seed Vault at the Farming Guild will be included in the calculations",
		position = 4
	)
	default boolean grabFromSeedVault()
	{
		return true;
	}

	@ConfigItem(
		keyName = BankedExperiencePlugin.INVENTORY_CONFIG_KEY,
		name = "Include player inventory",
		description = "Toggles whether the items inside your inventory will be included in the calculations",
		position = 5
	)
	default boolean grabFromInventory()
	{
		return false;
	}

	@ConfigItem(
		keyName = BankedExperiencePlugin.LOOTING_BAG_CONFIG_KEY,
		name = "Include looting bag",
		description = "Toggles whether the items stored inside your Looting Bag will be included in the calculations",
		position = 6
	)
	default boolean grabFromLootingBag()
	{
		return false;
	}

	@ConfigItem(
		keyName = BankedExperiencePlugin.FOSSIL_CHEST_CONFIG_KEY,
		name = "Include Fossil Chest",
		description = "Toggles whether the fossils stored inside your Fossil Island chest will be included in the calculations",
		position = 7
	)
	default boolean grabFromFossilChest()
	{
		return true;
	}

	@ConfigItem(
		keyName = "ignoredItems",
		name = "",
		description = "",
		hidden = true
	)
	default String ignoredItems()
	{
		return "";
	}

	@ConfigItem(
		keyName = "ignoredItems",
		name = "",
		description = ""
	)
	void ignoredItems(String val);
}
