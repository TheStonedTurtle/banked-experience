/*
 * Copyright (c) 2019, TheStonedTurtle <https://github.com/TheStonedTurtle>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package thestonedturtle.bankedexperience.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import net.runelite.api.ItemID;

@Getter
public enum Secondaries
{
	/**
	 * Herblore
	 */
	UNFINISHED_POTION(new ItemStack(ItemID.VIAL_OF_WATER, 1)),
	SWAMP_TAR(new ItemStack(ItemID.SWAMP_TAR, 15)),
	VIAL_OF_BLOOD(new ItemStack(ItemID.VIAL_OF_BLOOD, 1)),
	// Guam
	ATTACK_POTION(new ItemStack(ItemID.EYE_OF_NEWT, 1)),
	// Marrentil
	ANTIPOISON(new ItemStack(ItemID.UNICORN_HORN_DUST, 1)),
	// Tarromin
	STRENGTH_POTION(new ItemStack(ItemID.LIMPWURT_ROOT, 1)),
	SERUM_207(new ItemStack(ItemID.ASHES, 1)),
	// Harralander
	COMPOST_POTION(new ItemStack(ItemID.VOLCANIC_ASH, 1)),
	RESTORE_POTION(new ItemStack(ItemID.RED_SPIDERS_EGGS, 1)),
	ENERGY_POTION(new ItemStack(ItemID.CHOCOLATE_DUST, 1)),
	COMBAT_POTION(new ItemStack(ItemID.GOAT_HORN_DUST, 1)),
	// Ranarr Weed
	DEFENCE_POTION(new ItemStack(ItemID.WHITE_BERRIES, 1)),
	PRAYER_POTION(new ItemStack(ItemID.SNAPE_GRASS, 1)),
	// Toadflax
	AGILITY_POTION(new ItemStack(ItemID.TOADS_LEGS, 1)),
	SARADOMIN_BREW(new ItemStack(ItemID.CRUSHED_NEST, 1)),
	// Irit
	SUPER_ATTACK(new ItemStack(ItemID.EYE_OF_NEWT, 1)),
	SUPERANTIPOISON(new ItemStack(ItemID.UNICORN_HORN_DUST, 1)),
	// Avantoe
	FISHING_POTION(new ItemStack(ItemID.SNAPE_GRASS, 1)),
	SUPER_ENERGY_POTION(new ItemStack(ItemID.MORT_MYRE_FUNGUS, 1)),
	HUNTER_POTION(new ItemStack(ItemID.KEBBIT_TEETH_DUST, 1)),
	// Kwuarm
	SUPER_STRENGTH(new ItemStack(ItemID.LIMPWURT_ROOT, 1)),
	// Snapdragon
	SUPER_RESTORE(new ItemStack(ItemID.RED_SPIDERS_EGGS, 1)),
	SANFEW_SERUM(new ItemStack(ItemID.SNAKE_WEED, 1), new ItemStack(ItemID.UNICORN_HORN_DUST, 1), new ItemStack(ItemID.SUPER_RESTORE4, 1), new ItemStack(ItemID.NAIL_BEAST_NAILS, 1)),
	// Cadantine
	SUPER_DEFENCE_POTION(new ItemStack(ItemID.WHITE_BERRIES, 1)),
	// Lantadyme
	ANTIFIRE_POTION(new ItemStack(ItemID.DRAGON_SCALE_DUST, 1)),
	MAGIC_POTION(new ItemStack(ItemID.POTATO_CACTUS, 1)),
	// Dwarf Weed
	RANGING_POTION(new ItemStack(ItemID.WINE_OF_ZAMORAK, 1)),
	MENAPHITE_REMEDY(new ItemStack(ItemID.LILY_OF_THE_SANDS, 1)),
	// Torstol
	ZAMORAK_BREW(new ItemStack(ItemID.JANGERBERRIES, 1)),
	SUPER_COMBAT_POTION(new ItemStack(ItemID.SUPER_ATTACK4, 1), new ItemStack(ItemID.SUPER_STRENGTH4, 1), new ItemStack(ItemID.SUPER_DEFENCE4, 1)),
	ANTIVENOM_PLUS(new ItemStack(ItemID.ANTIVENOM4, 1)),
	// Other
	STAMINA_POTION(new ByDose(ItemID.SUPER_ENERGY1, ItemID.SUPER_ENERGY2, ItemID.SUPER_ENERGY3, ItemID.SUPER_ENERGY4)),
	EXTENDED_ANTIFIRE(new ByDose(ItemID.ANTIFIRE_POTION1, ItemID.ANTIFIRE_POTION2, ItemID.ANTIFIRE_POTION3, ItemID.ANTIFIRE_POTION4)),
	EXTENDED_SUPER_ANTIFIRE(new ByDose(ItemID.SUPER_ANTIFIRE_POTION1, ItemID.SUPER_ANTIFIRE_POTION2, ItemID.SUPER_ANTIFIRE_POTION3, ItemID.SUPER_ANTIFIRE_POTION4)),
	// Degrime
	DEGRIME(new Degrime()),
	/**
	 * Smithing
	 */
	COAL_ORE(new ItemStack(ItemID.COAL, 1)),
	COAL_ORE_2(new ItemStack(ItemID.COAL, 2)),
	COAL_ORE_3(new ItemStack(ItemID.COAL, 3)),
	COAL_ORE_4(new ItemStack(ItemID.COAL, 4)),
	COAL_ORE_6(new ItemStack(ItemID.COAL, 6)),
	COAL_ORE_8(new ItemStack(ItemID.COAL, 8)),
	/**
	 * Crafting
	 */
	GOLD_BAR(new ItemStack(ItemID.GOLD_BAR, 1)),
	SILVER_BAR(new ItemStack(ItemID.SILVER_BAR, 1)),
	WATER_ORB(new ItemStack(ItemID.WATER_ORB, 1)),
	EARTH_ORB(new ItemStack(ItemID.EARTH_ORB, 1)),
	FIRE_ORB(new ItemStack(ItemID.FIRE_ORB, 1)),
	AIR_ORB(new ItemStack(ItemID.AIR_ORB, 1)),
	BUCKET_OF_SAND(new ItemStack(ItemID.BUCKET_OF_SAND, 1)),
	BUCKET_OF_SAND_6(new ItemStack(ItemID.BUCKET_OF_SAND, 6)),
	HARD_LEATHER_SHIELD(new ItemStack(ItemID.OAK_SHIELD, 1), new ItemStack(ItemID.BRONZE_NAILS, 15)),
	COIN(new ItemStack(ItemID.COINS_995, 1)),
	COIN_3(new ItemStack(ItemID.COINS_995, 3)),
	LEATHER_BODY(new ItemStack(ItemID.LEATHER_BODY, 1)),
	LEATHER_CHAPS(new ItemStack(ItemID.LEATHER_CHAPS, 1)),
	/**
	 * Construction
	 */
	COINS_70(new ItemStack(ItemID.COINS_995, 70)),
	COINS_100(new ItemStack(ItemID.COINS_995, 100)),
	COINS_175(new ItemStack(ItemID.COINS_995, 175)),
	COINS_250(new ItemStack(ItemID.COINS_995, 250)),
	COINS_350(new ItemStack(ItemID.COINS_995, 350)),
	COINS_500(new ItemStack(ItemID.COINS_995, 500)),
	COINS_1050(new ItemStack(ItemID.COINS_995, 1050)),
	COINS_1500(new ItemStack(ItemID.COINS_995, 1500)),
	// Mahogany Homes secondary rates are calculated utilizing the averages as generated by the wiki
	// https://oldschool.runescape.wiki/w/Mahogany_Homes
	STEEL_BAR_PLANK(new ItemStack(ItemID.STEEL_BAR, 0.052)),
	STEEL_BAR_OAK(new ItemStack(ItemID.STEEL_BAR, 0.051)),
	STEEL_BAR_TEAK(new ItemStack(ItemID.STEEL_BAR, 0.043)),
	STEEL_BAR_MAHOGANY(new ItemStack(ItemID.STEEL_BAR, 0.04)),
	/**
	 * Cooking
	 */
	JUG_OF_WATER(new ItemStack(ItemID.JUG_OF_WATER, 1)),
	/**
	 * Fletching
	 */
	BOW_STRING(new ItemStack(ItemID.BOW_STRING, 1)),
	FEATHER(new ItemStack(ItemID.FEATHER, 1)),
	HEADLESS_ARROW(new ItemStack(ItemID.HEADLESS_ARROW, 1)),
	CROSSBOW_STRING(new ItemStack(ItemID.CROSSBOW_STRING, 1)),
	BRONZE_LIMBS(new ItemStack(ItemID.BRONZE_LIMBS, 1)),
	BLURITE_LIMBS(new ItemStack(ItemID.BLURITE_LIMBS, 1)),
	IRON_LIMBS(new ItemStack(ItemID.IRON_LIMBS, 1)),
	STEEL_LIMBS(new ItemStack(ItemID.STEEL_LIMBS, 1)),
	MITHRIL_LIMBS(new ItemStack(ItemID.MITHRIL_LIMBS, 1)),
	ADAMANTITE_LIMBS(new ItemStack(ItemID.ADAMANTITE_LIMBS, 1)),
	RUNITE_LIMBS(new ItemStack(ItemID.RUNITE_LIMBS, 1)),
	DRAGON_LIMBS(new ItemStack(ItemID.DRAGON_LIMBS, 1)),
	/**
	 * Prayer
	 */
	BUCKET_OF_SLIME(new ItemStack(ItemID.BUCKET_OF_SLIME, 1)),
	/**
	 * Thieving
	 */
	BEER_GLASS_5TH(new ItemStack(ItemID.BEER_GLASS, 0.2)),
	BEER_GLASS_4TH(new ItemStack(ItemID.BEER_GLASS, 0.25)),
	BEER_GLASS_3RD(new ItemStack(ItemID.BEER_GLASS, 1.0 / 3)),
	BEER_GLASS_HALF(new ItemStack(ItemID.BEER_GLASS, 0.5)),
	;
	private final ItemStack[] items;
	private final SecondaryHandler customHandler;

	public interface SecondaryHandler
	{
		ItemStack[] getInfoItems();
	}

	public static class ByDose implements SecondaryHandler
	{
		@Getter
		// index + 1 = amount of doses the item id is worth
		private final int[] items;
		@Getter
		private final ItemStack[] infoItems;

		private ByDose(final int... items)
		{
			this.items = items;
			this.infoItems =  new ItemStack[]{new ItemStack(items[0], 0)};
		}
	}

	@NoArgsConstructor
	public static class Degrime implements SecondaryHandler
	{
		@Getter
		private final ItemStack[] infoItems = new ItemStack[0];

		public int getTotalNaturesRequired(int itemCount)
		{
			// Assume each spell cast is being maximally efficient by using a earth staff, 1 slot of natures, and 27 for herbs
			final int requiredCasts = (int) Math.ceil(itemCount / 27.0d);

			// 1 spell cast requires at least 2 nature runes.
			return requiredCasts * 2;
		}
	}

	Secondaries(ItemStack... items)
	{
		this.items = items;
		this.customHandler = null;
	}

	Secondaries(SecondaryHandler customHandler)
	{
		this.items = new ItemStack[0];
		this.customHandler = customHandler;
	}
}
