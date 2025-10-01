package thestonedturtle.bankedexperience;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup(BankedExperienceConfig.CONFIG_GROUP)
public interface BankedExperienceConfig extends Config
{
	/**
	 * The name of the group under which all of this plugins configuration key/value
	 * pairs will be stored by Runelite via the config API. The config key names below
	 * are within this named configuration group.
	 */
	public static final String CONFIG_GROUP = "bankedexperience";

	public static final String POTION_STORAGE_KEY = "grabFromPotionStorage";
	public static final String VAULT_CONFIG_KEY = "grabFromSeedVault";
	public static final String INVENTORY_CONFIG_KEY = "grabFromInventory";
	public static final String LOOTING_BAG_CONFIG_KEY = "grabFromLootingBag";
	public static final String FOSSIL_CHEST_CONFIG_KEY = "grabFromFossilChest";

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
		keyName = VAULT_CONFIG_KEY,
		name = "Include seed vault",
		description = "Toggles whether the items stored inside the Seed Vault at the Farming Guild will be included in the calculations",
		position = 4
	)
	default boolean grabFromSeedVault()
	{
		return true;
	}

	@ConfigItem(
		keyName = INVENTORY_CONFIG_KEY,
		name = "Include player inventory",
		description = "Toggles whether the items inside your inventory will be included in the calculations",
		position = 5
	)
	default boolean grabFromInventory()
	{
		return false;
	}

	@ConfigItem(
		keyName = LOOTING_BAG_CONFIG_KEY,
		name = "Include looting bag",
		description = "Toggles whether the items stored inside your Looting Bag will be included in the calculations",
		position = 6
	)
	default boolean grabFromLootingBag()
	{
		return false;
	}

	@ConfigItem(
		keyName = FOSSIL_CHEST_CONFIG_KEY,
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
		position = 8
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
