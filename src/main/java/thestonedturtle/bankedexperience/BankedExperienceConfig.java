package thestonedturtle.bankedexperience;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("bankedexperience")
public interface BankedExperienceConfig extends Config
{
	String POTION_STORAGE_KEY = "grabFromPotionStorage";

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

	@ConfigItem(
		keyName = "showSecondaries",
		name = "Show required secondaries",
		description = "Toggles whether the Secondaries will be displayed for the selected item",
		position = 2
	)
	default boolean showSecondaries()
	{
		return false;
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
		keyName = "grabFromSeedVault",
		name = "Include seed vault",
		description = "Toggles whether the items stored inside the Seed Vault at the Farming Guild will be included in the calculations",
		position = 4
	)
	default boolean grabFromSeedVault()
	{
		return true;
	}

	@ConfigItem(
		keyName = "grabFromInventory",
		name = "Include player inventory",
		description = "Toggles whether the items inside your inventory will be included in the calculations",
		position = 5
	)
	default boolean grabFromInventory()
	{
		return false;
	}

	@ConfigItem(
		keyName = "grabFromLootingBag",
		name = "Include looting bag",
		description = "Toggles whether the items stored inside your Looting Bag will be included in the calculations",
		position = 6
	)
	default boolean grabFromLootingBag()
	{
		return false;
	}

	@ConfigItem(
		keyName = "grabFromFossilChest",
		name = "Include Fossil Chest",
		description = "Toggles whether the fossils stored inside your Fossil Island chest will be included in the calculations",
		position = 7
	)
	default boolean grabFromFossilChest()
	{
		return true;
	}

	@ConfigItem(
		keyName = POTION_STORAGE_KEY,
		name = "Include Potion Storage",
		description = "Toggles whether items in your potion storage should be included in the calculations",
		position = 7
	)
	default boolean grabFromPotionStorage()
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
