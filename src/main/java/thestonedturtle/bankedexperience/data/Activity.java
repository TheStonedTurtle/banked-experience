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
package com.github.thestonedturtle.bankedexperience.data;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSortedSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import lombok.Getter;
import net.runelite.api.ItemComposition;
import net.runelite.api.ItemID;
import net.runelite.api.Skill;
import net.runelite.client.game.ItemManager;

/**
 * A specific in-game action that consumes bank-able item(s) and rewards {@link Skill} experience.
 * An `Activity` is linked to an {@link ExperienceItem} and the {@link Skill} it requires.
 */
@Getter
public enum Activity
{
	/**
	 * Herblore
	 */
	// Guam
	GUAM_POTION_UNF(ItemID.GUAM_POTION_UNF, "Unfinished Potion", 1, 0,
		ExperienceItem.GUAM_LEAF, Secondaries.UNFINISHED_POTION, new ItemStack(ItemID.GUAM_POTION_UNF, 1)),
	GUAM_TAR(ItemID.GUAM_TAR, "Guam tar", 19, 30,
		ExperienceItem.GUAM_LEAF, Secondaries.SWAMP_TAR, new ItemStack(ItemID.GUAM_TAR, 15)),
	ATTACK_POTION(ItemID.ATTACK_POTION3, "Attack potion", 3, 25,
		ExperienceItem.GUAM_LEAF_POTION_UNF, Secondaries.ATTACK_POTION, new ItemStack(ItemID.ATTACK_POTION3, 1)),
	// Marrentil
	MARRENTILL_POTION_UNF(ItemID.MARRENTILL_POTION_UNF, "Unfinished potion", 1, 0,
		ExperienceItem.MARRENTILL, Secondaries.UNFINISHED_POTION, new ItemStack(ItemID.MARRENTILL_POTION_UNF, 1)),
	MARRENTILL_TAR(ItemID.MARRENTILL_TAR, "Marrentill tar", 31, 42.5,
		ExperienceItem.MARRENTILL, Secondaries.SWAMP_TAR, new ItemStack(ItemID.MARRENTILL_TAR, 15)),
	ANTIPOISON(ItemID.ANTIPOISON3, "Antipoison", 5, 37.5,
		ExperienceItem.MARRENTILL_POTION_UNF, Secondaries.ANTIPOISON, new ItemStack(ItemID.ANTIPOISON3, 1)),
	// Tarromin
	TARROMIN_POTION_UNF(ItemID.TARROMIN_POTION_UNF, "Unfinished potion", 1, 0,
		ExperienceItem.TARROMIN, Secondaries.UNFINISHED_POTION, new ItemStack(ItemID.TARROMIN_POTION_UNF, 1)),
	TARROMIN_TAR(ItemID.TARROMIN_TAR, "Tarromin tar", 39, 55,
		ExperienceItem.TARROMIN, Secondaries.SWAMP_TAR, new ItemStack(ItemID.TARROMIN_TAR, 15)),
	STRENGTH_POTION(ItemID.STRENGTH_POTION3, "Strength potion", 12, 50,
		ExperienceItem.TARROMIN_POTION_UNF, Secondaries.STRENGTH_POTION, new ItemStack(ItemID.STRENGTH_POTION3, 1)),
	SERUM_207(ItemID.SERUM_207_3, "Serum 207", 15, 50,
		ExperienceItem.TARROMIN_POTION_UNF, Secondaries.SERUM_207, new ItemStack(ItemID.SERUM_207_3, 1)),
	// Harralander
	HARRALANDER_POTION_UNF(ItemID.HARRALANDER_POTION_UNF, "Unfinished potion", 1, 0,
		ExperienceItem.HARRALANDER, Secondaries.UNFINISHED_POTION, new ItemStack(ItemID.HARRALANDER_POTION_UNF, 1)),
	HARRALANDER_TAR(ItemID.HARRALANDER_TAR, "Harralander tar", 44, 72.5,
		ExperienceItem.HARRALANDER, Secondaries.SWAMP_TAR, new ItemStack(ItemID.HARRALANDER_TAR, 15)),
	COMPOST_POTION(ItemID.COMPOST_POTION3, "Compost potion", 21, 60,
		ExperienceItem.HARRALANDER_POTION_UNF, Secondaries.COMPOST_POTION, new ItemStack(ItemID.COMPOST_POTION3, 1)),
	RESTORE_POTION(ItemID.RESTORE_POTION3, "Restore potion", 22, 62.5,
		ExperienceItem.HARRALANDER_POTION_UNF, Secondaries.RESTORE_POTION, new ItemStack(ItemID.RESTORE_POTION3, 1)),
	ENERGY_POTION(ItemID.ENERGY_POTION3, "Energy potion", 26, 67.5,
		ExperienceItem.HARRALANDER_POTION_UNF, Secondaries.ENERGY_POTION, new ItemStack(ItemID.ENERGY_POTION3, 1)),
	COMBAT_POTION(ItemID.COMBAT_POTION3, "Combat potion", 36, 84,
		ExperienceItem.HARRALANDER_POTION_UNF, Secondaries.COMBAT_POTION, new ItemStack(ItemID.COMBAT_POTION3, 1)),
	// Ranarr Weed
	RANARR_POTION_UNF(ItemID.RANARR_POTION_UNF, "Unfinished potion", 30, 0,
		ExperienceItem.RANARR_WEED, Secondaries.UNFINISHED_POTION, new ItemStack(ItemID.RANARR_POTION_UNF, 1)),
	DEFENCE_POTION(ItemID.DEFENCE_POTION3, "Defence potion", 30, 75,
		ExperienceItem.RANARR_POTION_UNF, Secondaries.DEFENCE_POTION, new ItemStack(ItemID.DEFENCE_POTION3, 1)),
	PRAYER_POTION(ItemID.PRAYER_POTION3, "Prayer potion", 38, 87.5,
		ExperienceItem.RANARR_POTION_UNF, Secondaries.PRAYER_POTION, new ItemStack(ItemID.PRAYER_POTION3, 1)),
	// Toadflax
	TOADFLAX_POTION_UNF(ItemID.TOADFLAX_POTION_UNF, "Unfinished potion", 34, 0,
		ExperienceItem.TOADFLAX, Secondaries.UNFINISHED_POTION, new ItemStack(ItemID.TOADFLAX_POTION_UNF, 1)),
	AGILITY_POTION(ItemID.AGILITY_POTION3, "Agility potion", 34, 80,
		ExperienceItem.TOADFLAX_POTION_UNF, Secondaries.AGILITY_POTION, new ItemStack(ItemID.AGILITY_POTION3, 1)),
	SARADOMIN_BREW(ItemID.SARADOMIN_BREW3, "Saradomin brew", 81, 180,
		ExperienceItem.TOADFLAX_POTION_UNF, Secondaries.SARADOMIN_BREW, new ItemStack(ItemID.SARADOMIN_BREW3, 1)),
	// Irit
	IRIT_POTION_UNF(ItemID.IRIT_POTION_UNF, "Unfinished potion", 45, 0,
		ExperienceItem.IRIT_LEAF, Secondaries.UNFINISHED_POTION, new ItemStack(ItemID.IRIT_POTION_UNF, 1)),
	SUPER_ATTACK(ItemID.SUPER_ATTACK3, "Super attack", 45, 100,
		ExperienceItem.IRIT_POTION_UNF, Secondaries.SUPER_ATTACK, new ItemStack(ItemID.SUPER_ATTACK3, 1)),
	SUPERANTIPOISON(ItemID.SUPERANTIPOISON3, "Superantipoison", 48, 106.3,
		ExperienceItem.IRIT_POTION_UNF, Secondaries.SUPERANTIPOISON, new ItemStack(ItemID.SUPERANTIPOISON3, 1)),
	// Avantoe
	AVANTOE_POTION_UNF(ItemID.AVANTOE_POTION_UNF, "Unfinished potion", 50, 0,
		ExperienceItem.AVANTOE, Secondaries.UNFINISHED_POTION, new ItemStack(ItemID.AVANTOE_POTION_UNF, 1)),
	FISHING_POTION(ItemID.FISHING_POTION3, "Fishing potion", 50, 112.5,
		ExperienceItem.AVANTOE_POTION_UNF, Secondaries.FISHING_POTION, new ItemStack(ItemID.FISHING_POTION3, 1)),
	SUPER_ENERGY_POTION(ItemID.SUPER_ENERGY3_20549, "Super energy potion", 52, 117.5,
		ExperienceItem.AVANTOE_POTION_UNF, Secondaries.SUPER_ENERGY_POTION, new ItemStack(ItemID.SUPER_ENERGY3_20549, 1)),
	HUNTER_POTION(ItemID.HUNTER_POTION3, "Hunter potion", 53, 120,
		ExperienceItem.AVANTOE_POTION_UNF, Secondaries.HUNTER_POTION, new ItemStack(ItemID.HUNTER_POTION3, 1)),
	// Kwuarm
	KWUARM_POTION_UNF(ItemID.KWUARM_POTION_UNF, "Unfinished potion", 55, 0,
		ExperienceItem.KWUARM, Secondaries.UNFINISHED_POTION, new ItemStack(ItemID.KWUARM_POTION_UNF, 1)),
	SUPER_STRENGTH(ItemID.SUPER_STRENGTH3, "Super strength", 55, 125,
		ExperienceItem.KWUARM_POTION_UNF, Secondaries.SUPER_STRENGTH, new ItemStack(ItemID.SUPER_STRENGTH3, 1)),
	// Snapdragon
	SNAPDRAGON_POTION_UNF(ItemID.SNAPDRAGON_POTION_UNF, "Unfinished potion", 63, 0,
		ExperienceItem.SNAPDRAGON, Secondaries.UNFINISHED_POTION, new ItemStack(ItemID.SNAPDRAGON_POTION_UNF, 1)),
	SUPER_RESTORE(ItemID.SUPER_RESTORE3, "Super restore", 63, 142.5,
		ExperienceItem.SNAPDRAGON_POTION_UNF, Secondaries.SUPER_RESTORE, new ItemStack(ItemID.SUPER_RESTORE3, 1)),
	SANFEW_SERUM(ItemID.SANFEW_SERUM3, "Sanfew serum", 65, 160,
		ExperienceItem.SNAPDRAGON_POTION_UNF, Secondaries.SANFEW_SERUM, new ItemStack(ItemID.SANFEW_SERUM3, 1)),
	// Cadantine
	CADANTINE_POTION_UNF(ItemID.CADANTINE_POTION_UNF, "Unfinished potion", 66, 0,
		ExperienceItem.CADANTINE, Secondaries.UNFINISHED_POTION, new ItemStack(ItemID.CADANTINE_POTION_UNF, 1)),
	SUPER_DEFENCE_POTION(ItemID.SUPER_DEFENCE3, "Super defence", 66, 150,
		ExperienceItem.CADANTINE_POTION_UNF, Secondaries.SUPER_DEFENCE_POTION, new ItemStack(ItemID.SUPER_DEFENCE3, 1)),
	// Lantadyme
	LANTADYME_POTION_UNF(ItemID.LANTADYME_POTION_UNF, "Unfinished potion", 69, 0,
		ExperienceItem.LANTADYME, Secondaries.UNFINISHED_POTION, new ItemStack(ItemID.LANTADYME_POTION_UNF, 1)),
	ANTIFIRE_POTION(ItemID.ANTIFIRE_POTION3, "Anti-fire potion", 69, 157.5,
		ExperienceItem.LANTADYME_POTION_UNF, Secondaries.ANTIFIRE_POTION, new ItemStack(ItemID.ANTIFIRE_POTION3, 1)),
	MAGIC_POTION(ItemID.MAGIC_POTION3, "Magic potion", 76, 172.5,
		ExperienceItem.LANTADYME_POTION_UNF, Secondaries.MAGIC_POTION, new ItemStack(ItemID.MAGIC_POTION3, 1)),
	// Dwarf Weed
	DWARF_WEED_POTION_UNF(ItemID.DWARF_WEED_POTION_UNF, "Unfinished potion", 72, 0,
		ExperienceItem.DWARF_WEED, Secondaries.UNFINISHED_POTION, new ItemStack(ItemID.DWARF_WEED_POTION_UNF, 1)),
	RANGING_POTION(ItemID.RANGING_POTION3, "Ranging potion", 72, 162.5,
		ExperienceItem.DWARF_WEED_POTION_UNF, Secondaries.RANGING_POTION, new ItemStack(ItemID.RANGING_POTION3, 1)),
	// Torstol
	TORSTOL_POTION_UNF(ItemID.TORSTOL_POTION_UNF, "Unfinished potion", 78, 0,
		ExperienceItem.TORSTOL, Secondaries.UNFINISHED_POTION, new ItemStack(ItemID.TORSTOL_POTION_UNF, 1)),
	SUPER_COMBAT_POTION(ItemID.SUPER_COMBAT_POTION4, "Super combat", 90, 150,
		ExperienceItem.TORSTOL, Secondaries.SUPER_COMBAT_POTION, new ItemStack(ItemID.SUPER_COMBAT_POTION4, 1)),
	ANTIVENOM_PLUS(ItemID.ANTIVENOM3_12915, "Anti-venom+", 94, 125,
		ExperienceItem.TORSTOL, Secondaries.ANTIVENOM_PLUS, new ItemStack(ItemID.ANTIVENOM3_12915, 1)),
	ZAMORAK_BREW(ItemID.ZAMORAK_BREW3, "Zamorak brew", 78, 175,
		ExperienceItem.TORSTOL_POTION_UNF, Secondaries.ZAMORAK_BREW, new ItemStack(ItemID.ZAMORAK_BREW3, 1)),
	// Cleaning Grimy Herbs
	CLEAN_GUAM(ItemID.GUAM_LEAF, "Clean guam", 3, 2.5,
		ExperienceItem.GRIMY_GUAM_LEAF, null, new ItemStack(ItemID.GUAM_LEAF, 1)),
	CLEAN_MARRENTILL(ItemID.MARRENTILL, "Clean marrentill", 5, 3.8,
		ExperienceItem.GRIMY_MARRENTILL, null, new ItemStack(ItemID.MARRENTILL, 1)),
	CLEAN_TARROMIN(ItemID.TARROMIN, "Clean tarromin", 11, 5,
		ExperienceItem.GRIMY_TARROMIN, null, new ItemStack(ItemID.TARROMIN, 1)),
	CLEAN_HARRALANDER(ItemID.HARRALANDER, "Clean harralander", 20, 6.3,
		ExperienceItem.GRIMY_HARRALANDER, null, new ItemStack(ItemID.HARRALANDER, 1)),
	CLEAN_RANARR_WEED(ItemID.RANARR_WEED, "Clean ranarr weed", 25, 7.5,
		ExperienceItem.GRIMY_RANARR_WEED, null, new ItemStack(ItemID.RANARR_WEED, 1)),
	CLEAN_TOADFLAX(ItemID.TOADFLAX, "Clean toadflax", 30, 8,
		ExperienceItem.GRIMY_TOADFLAX, null, new ItemStack(ItemID.TOADFLAX, 1)),
	CLEAN_IRIT_LEAF(ItemID.IRIT_LEAF, "Clean irit leaf", 40, 8.8,
		ExperienceItem.GRIMY_IRIT_LEAF, null, new ItemStack(ItemID.IRIT_LEAF, 1)),
	CLEAN_AVANTOE(ItemID.AVANTOE, "Clean avantoe", 48, 10,
		ExperienceItem.GRIMY_AVANTOE, null, new ItemStack(ItemID.AVANTOE, 1)),
	CLEAN_KWUARM(ItemID.KWUARM, "Clean kwuarm", 54, 11.3,
		ExperienceItem.GRIMY_KWUARM, null, new ItemStack(ItemID.KWUARM, 1)),
	CLEAN_SNAPDRAGON(ItemID.SNAPDRAGON, "Clean snapdragon", 59, 11.8,
		ExperienceItem.GRIMY_SNAPDRAGON, null, new ItemStack(ItemID.SNAPDRAGON, 1)),
	CLEAN_CADANTINE(ItemID.CADANTINE, "Clean cadantine", 65, 12.5,
		ExperienceItem.GRIMY_CADANTINE, null, new ItemStack(ItemID.CADANTINE, 1)),
	CLEAN_LANTADYME(ItemID.LANTADYME, "Clean lantadyme", 67, 13.1,
		ExperienceItem.GRIMY_LANTADYME, null, new ItemStack(ItemID.LANTADYME, 1)),
	CLEAN_DWARF_WEED(ItemID.DWARF_WEED, "Clean dwarf weed", 70, 13.8,
		ExperienceItem.GRIMY_DWARF_WEED, null, new ItemStack(ItemID.DWARF_WEED, 1)),
	CLEAN_TORSTOL(ItemID.TORSTOL, "Clean torstol", 75, 15,
		ExperienceItem.GRIMY_TORSTOL, null, new ItemStack(ItemID.TORSTOL, 1)),
	/**
	 * Construction
	 */
	PLANK(ItemID.PLANK, "Regular Plank", 1, 0,
		ExperienceItem.LOGS, Secondaries.COINS_100, new ItemStack(ItemID.PLANK, 1)),
	PLANKS(ItemID.PLANK, "Regular plank products", 1, 29,
		ExperienceItem.PLANK, null, null),
	OAK_PLANK(ItemID.OAK_PLANK, "Oak Plank", 1, 0,
		ExperienceItem.OAK_LOGS, Secondaries.COINS_250, new ItemStack(ItemID.OAK_PLANK, 1)),
	OAK_PLANKS(ItemID.OAK_PLANK, "Oak products", 1, 60,
		ExperienceItem.OAK_PLANK, null, null),
	TEAK_PLANK(ItemID.TEAK_PLANK, "Teak Plank", 1, 0,
		ExperienceItem.TEAK_LOGS, Secondaries.COINS_500, new ItemStack(ItemID.TEAK_PLANK, 1)),
	TEAK_PLANKS(ItemID.TEAK_PLANK, "Teak products", 1, 90,
		ExperienceItem.TEAK_PLANK, null, null),
	MYTHICAL_CAPE(ItemID.MYTHICAL_CAPE, "Mythical cape rakes", 1, 123.33,
		ExperienceItem.TEAK_PLANK, null, null),
	MAHOGANY_PLANK(ItemID.MAHOGANY_PLANK, "Mahogany Plank", 1, 0,
		ExperienceItem.MAHOGANY_LOGS, Secondaries.COINS_1500, new ItemStack(ItemID.MAHOGANY_PLANK, 1)),
	MAHOGANY_PLANKS(ItemID.MAHOGANY_PLANK, "Mahogany products", 1, 140,
		ExperienceItem.MAHOGANY_PLANK, null, null),
	/**
	 * Prayer
	 */
	BONES(ItemID.BONES, "Bones", 1, 4.5,
		ExperienceItem.BONES, null, null),
	WOLF_BONES(ItemID.WOLF_BONES, "Wolf bones", 1, 4.5,
		ExperienceItem.WOLF_BONES, null, null),
	BURNT_BONES(ItemID.BURNT_BONES, "Burnt bones", 1, 4.5,
		ExperienceItem.BURNT_BONES, null, null),
	MONKEY_BONES(ItemID.MONKEY_BONES, "Monkey bones", 1, 5.0,
		ExperienceItem.MONKEY_BONES, null, null),
	BAT_BONES(ItemID.BAT_BONES, "Bat bones", 1, 5.3,
		ExperienceItem.BAT_BONES, null, null),
	JOGRE_BONES(ItemID.JOGRE_BONES, "Jogre bones", 1, 15.0,
		ExperienceItem.JOGRE_BONES, null, null),
	BIG_BONES(ItemID.BIG_BONES, "Big bones", 1, 15.0,
		ExperienceItem.BIG_BONES, null, null),
	ZOGRE_BONES(ItemID.ZOGRE_BONES, "Zogre bones", 1, 22.5,
		ExperienceItem.ZOGRE_BONES, null, null),
	SHAIKAHAN_BONES(ItemID.SHAIKAHAN_BONES, "Shaikahan bones", 1, 25.0,
		ExperienceItem.SHAIKAHAN_BONES, null, null),
	BABYDRAGON_BONES(ItemID.BABYDRAGON_BONES, "Babydragon bones", 1, 30.0,
		ExperienceItem.BABYDRAGON_BONES, null, null),
	WYVERN_BONES(ItemID.WYVERN_BONES, "Wyvern bones", 1, 72.0,
		ExperienceItem.WYVERN_BONES, null, null),
	DRAGON_BONES(ItemID.DRAGON_BONES, "Dragon bones", 1, 72.0,
		ExperienceItem.DRAGON_BONES, null, null),
	FAYRG_BONES(ItemID.FAYRG_BONES, "Fayrg bones", 1, 84.0,
		ExperienceItem.FAYRG_BONES, null, null),
	LAVA_DRAGON_BONES(ItemID.LAVA_DRAGON_BONES, "Lava dragon bones", 1, 85.0,
		ExperienceItem.LAVA_DRAGON_BONES, null, null),
	RAURG_BONES(ItemID.RAURG_BONES, "Raurg bones", 1, 96.0,
		ExperienceItem.RAURG_BONES, null, null),
	DAGANNOTH_BONES(ItemID.DAGANNOTH_BONES, "Dagannoth bones", 1, 125.0,
		ExperienceItem.DAGANNOTH_BONES, null, null),
	OURG_BONES(ItemID.OURG_BONES, "Ourg bones", 1, 140.0,
		ExperienceItem.OURG_BONES, null, null),
	SUPERIOR_DRAGON_BONES(ItemID.SUPERIOR_DRAGON_BONES, "Superior dragon bones", 1, 150.0,
		ExperienceItem.SUPERIOR_DRAGON_BONES, null, null),
	WYRM_BONES(ItemID.WYRM_BONES, "Wyrm bones", 1, 50.0,
		ExperienceItem.WYRM_BONES, null, null),
	DRAKE_BONES(ItemID.DRAKE_BONES, "Drake bones", 1, 80.0,
		ExperienceItem.DRAKE_BONES, null, null),
	HYDRA_BONES(ItemID.HYDRA_BONES, "Hydra bones", 1, 110.0,
		ExperienceItem.HYDRA_BONES, null, null),
	// Shade Remains (Pyre Logs)
	LOAR_REMAINS(ItemID.LOAR_REMAINS, "Loar remains", 1, 33.0,
		ExperienceItem.LOAR_REMAINS, null, null),
	PHRIN_REMAINS(ItemID.PHRIN_REMAINS, "Phrin remains", 1, 46.5,
		ExperienceItem.PHRIN_REMAINS, null, null),
	RIYL_REMAINS(ItemID.RIYL_REMAINS, "Riyl remains", 1, 59.5,
		ExperienceItem.RIYL_REMAINS, null, null),
	ASYN_REMAINS(ItemID.ASYN_REMAINS, "Asyn remains", 1, 82.5,
		ExperienceItem.ASYN_REMAINS, null, null),
	FIYR_REMAINS(ItemID.FIYR_REMAINS, "Fiyre remains", 1, 84.0,
		ExperienceItem.FIYR_REMAINS, null, null),
	// Ensouled Heads
	ENSOULED_GOBLIN_HEAD(ItemID.ENSOULED_GOBLIN_HEAD_13448, "Ensouled goblin head", 1, 130.0,
		ExperienceItem.ENSOULED_GOBLIN_HEAD, null, null),
	ENSOULED_MONKEY_HEAD(ItemID.ENSOULED_MONKEY_HEAD_13451, "Ensouled monkey head", 1, 182.0,
		ExperienceItem.ENSOULED_MONKEY_HEAD, null, null),
	ENSOULED_IMP_HEAD(ItemID.ENSOULED_IMP_HEAD_13454, "Ensouled imp head", 1, 286.0,
		ExperienceItem.ENSOULED_IMP_HEAD, null, null),
	ENSOULED_MINOTAUR_HEAD(ItemID.ENSOULED_MINOTAUR_HEAD_13457, "Ensouled minotaur head", 1, 364.0,
		ExperienceItem.ENSOULED_MINOTAUR_HEAD, null, null),
	ENSOULED_SCORPION_HEAD(ItemID.ENSOULED_SCORPION_HEAD_13460, "Ensouled scorpion head", 1, 454.0,
		ExperienceItem.ENSOULED_SCORPION_HEAD, null, null),
	ENSOULED_BEAR_HEAD(ItemID.ENSOULED_BEAR_HEAD_13463, "Ensouled bear head", 1, 480.0,
		ExperienceItem.ENSOULED_BEAR_HEAD, null, null),
	ENSOULED_UNICORN_HEAD(ItemID.ENSOULED_UNICORN_HEAD_13466, "Ensouled unicorn head", 1, 494.0,
		ExperienceItem.ENSOULED_UNICORN_HEAD, null, null),
	ENSOULED_DOG_HEAD(ItemID.ENSOULED_DOG_HEAD_13469, "Ensouled dog head", 1, 520.0,
		ExperienceItem.ENSOULED_DOG_HEAD, null, null),
	ENSOULED_CHAOS_DRUID_HEAD(ItemID.ENSOULED_CHAOS_DRUID_HEAD_13472, "Ensouled druid head", 1, 584.0,
		ExperienceItem.ENSOULED_CHAOS_DRUID_HEAD, null, null),
	ENSOULED_GIANT_HEAD(ItemID.ENSOULED_GIANT_HEAD_13475, "Ensouled giant head", 1, 650.0,
		ExperienceItem.ENSOULED_GIANT_HEAD, null, null),
	ENSOULED_OGRE_HEAD(ItemID.ENSOULED_OGRE_HEAD_13478, "Ensouled ogre head", 1, 716.0,
		ExperienceItem.ENSOULED_OGRE_HEAD, null, null),
	ENSOULED_ELF_HEAD(ItemID.ENSOULED_ELF_HEAD_13481, "Ensouled elf head", 1, 754.0,
		ExperienceItem.ENSOULED_ELF_HEAD, null, null),
	ENSOULED_TROLL_HEAD(ItemID.ENSOULED_TROLL_HEAD_13484, "Ensouled troll head", 1, 780.0,
		ExperienceItem.ENSOULED_TROLL_HEAD, null, null),
	ENSOULED_HORROR_HEAD(ItemID.ENSOULED_HORROR_HEAD_13487, "Ensouled horror head", 1, 832.0,
		ExperienceItem.ENSOULED_HORROR_HEAD, null, null),
	ENSOULED_KALPHITE_HEAD(ItemID.ENSOULED_KALPHITE_HEAD_13490, "Ensouled kalphite head", 1, 884.0,
		ExperienceItem.ENSOULED_KALPHITE_HEAD, null, null),
	ENSOULED_DAGANNOTH_HEAD(ItemID.ENSOULED_DAGANNOTH_HEAD_13493, "Ensouled dagannoth head", 1, 936.0,
		ExperienceItem.ENSOULED_DAGANNOTH_HEAD, null, null),
	ENSOULED_BLOODVELD_HEAD(ItemID.ENSOULED_BLOODVELD_HEAD_13496, "Ensouled bloodveld head", 1, 1040.0,
		ExperienceItem.ENSOULED_BLOODVELD_HEAD, null, null),
	ENSOULED_TZHAAR_HEAD(ItemID.ENSOULED_TZHAAR_HEAD_13499, "Ensouled tzhaar head", 1, 1104.0,
		ExperienceItem.ENSOULED_TZHAAR_HEAD, null, null),
	ENSOULED_DEMON_HEAD(ItemID.ENSOULED_DEMON_HEAD_13502, "Ensouled demon head", 1, 1170.0,
		ExperienceItem.ENSOULED_DEMON_HEAD, null, null),
	ENSOULED_AVIANSIE_HEAD(ItemID.ENSOULED_AVIANSIE_HEAD_13505, "Ensouled aviansie head", 1, 1234.0,
		ExperienceItem.ENSOULED_AVIANSIE_HEAD, null, null),
	ENSOULED_ABYSSAL_HEAD(ItemID.ENSOULED_ABYSSAL_HEAD_13508, "Ensouled abyssal head", 1, 1300.0,
		ExperienceItem.ENSOULED_ABYSSAL_HEAD, null, null),
	ENSOULED_DRAGON_HEAD(ItemID.ENSOULED_DRAGON_HEAD_13511, "Ensouled dragon head", 1, 1560.0,
		ExperienceItem.ENSOULED_DRAGON_HEAD, null, null),
	/**
	 * Cooking
	 */
	COOK_HERRING(ItemID.HERRING, "Herring", 5, 50.0,
		ExperienceItem.RAW_HERRING, null, new ItemStack(ItemID.HERRING, 1)),
	COOK_MACKEREL(ItemID.MACKEREL, "Mackerel", 10, 60.0,
		ExperienceItem.RAW_MACKEREL, null, new ItemStack(ItemID.MACKEREL, 1)),
	COOK_TROUT(ItemID.TROUT, "Trout", 15, 70.0,
		ExperienceItem.RAW_TROUT, null, new ItemStack(ItemID.TROUT, 1)),
	COOK_COD(ItemID.COD, "Cod", 18, 75.0,
		ExperienceItem.RAW_COD, null, new ItemStack(ItemID.COD, 1)),
	COOK_PIKE(ItemID.PIKE, "Pike", 20, 80.0,
		ExperienceItem.RAW_PIKE, null, new ItemStack(ItemID.PIKE, 1)),
	COOK_SALMON(ItemID.SALMON, "Salmon", 25, 90.0,
		ExperienceItem.RAW_SALMON, null, new ItemStack(ItemID.SALMON, 1)),
	COOK_TUNA(ItemID.TUNA, "Tuna", 30, 100.0,
		ExperienceItem.RAW_TUNA, null, new ItemStack(ItemID.TUNA, 1)),
	COOK_KARAMBWAN(ItemID.COOKED_KARAMBWAN, "Cooked Karambwan", 30, 190.0,
		ExperienceItem.RAW_KARAMBWAN, null, new ItemStack(ItemID.COOKED_KARAMBWAN, 1)),
	COOK_LOBSTER(ItemID.LOBSTER, "Lobster", 40, 120.0,
		ExperienceItem.RAW_LOBSTER, null, new ItemStack(ItemID.LOBSTER, 1)),
	COOK_BASS(ItemID.BASS, "Bass", 43, 130.0,
		ExperienceItem.RAW_BASS, null, new ItemStack(ItemID.BASS, 1)),
	COOK_SWORDFISH(ItemID.SWORDFISH, "Swordfish", 45, 140.0,
		ExperienceItem.RAW_SWORDFISH, null, new ItemStack(ItemID.SWORDFISH, 1)),
	COOK_MONKFISH(ItemID.MONKFISH, "Monkfish", 62, 150.0,
		ExperienceItem.RAW_MONKFISH, null, new ItemStack(ItemID.MONKFISH, 1)),
	COOK_SHARK(ItemID.SHARK, "Shark", 80, 210.0,
		ExperienceItem.RAW_SHARK, null, new ItemStack(ItemID.SHARK, 1)),
	COOK_SEA_TURTLE(ItemID.SEA_TURTLE, "Sea turtle", 82, 211.3,
		ExperienceItem.RAW_SEA_TURTLE, null, new ItemStack(ItemID.SEA_TURTLE, 1)),
	COOK_ANGLERFISH(ItemID.ANGLERFISH, "Anglerfish", 84, 230.0,
		ExperienceItem.RAW_ANGLERFISH, null, new ItemStack(ItemID.ANGLERFISH, 1)),
	COOK_DARK_CRAB(ItemID.DARK_CRAB, "Dark crab", 90, 215.0,
		ExperienceItem.RAW_DARK_CRAB, null, new ItemStack(ItemID.DARK_CRAB, 1)),
	COOK_MANTA_RAY(ItemID.MANTA_RAY, "Manta ray", 91, 216.2,
		ExperienceItem.RAW_MANTA_RAY, null, new ItemStack(ItemID.MANTA_RAY, 1)),
	WINE(ItemID.JUG_OF_WINE, "Jug of wine", 35, 200,
		ExperienceItem.GRAPES, Secondaries.JUG_OF_WATER, new ItemStack(ItemID.JUG_OF_WINE, 1)),
	/**
	 * Crafting
	 */
	// Spinning
	BALL_OF_WOOL(ItemID.BALL_OF_WOOL, "Ball of wool", 1, 2.5,
		ExperienceItem.WOOL, null, new ItemStack(ItemID.BALL_OF_WOOL, 1)),
	BOW_STRING(ItemID.BOW_STRING, "Bow string", 1, 15,
		ExperienceItem.FLAX, null, new ItemStack(ItemID.BOW_STRING, 1)),
	// Glass Blowing
	BEER_GLASS(ItemID.BEER_GLASS, "Beer glass", 1, 17.5,
		ExperienceItem.MOLTEN_GLASS, null, new ItemStack(ItemID.BEER_GLASS, 1)),
	CANDLE_LANTERN(ItemID.CANDLE_LANTERN, "Candle lantern", 4, 19,
		ExperienceItem.MOLTEN_GLASS, null, new ItemStack(ItemID.CANDLE_LANTERN, 1)),
	OIL_LAMP(ItemID.OIL_LAMP, "Oil lamp", 12, 25,
		ExperienceItem.MOLTEN_GLASS, null, new ItemStack(ItemID.OIL_LAMP, 1)),
	VIAL(ItemID.VIAL, "Vial", 33, 35,
		ExperienceItem.MOLTEN_GLASS, null, new ItemStack(ItemID.VIAL, 1)),
	EMPTY_FISHBOWL(ItemID.EMPTY_FISHBOWL, "Empty fishbowl", 42, 42.5,
		ExperienceItem.MOLTEN_GLASS, null, new ItemStack(ItemID.EMPTY_FISHBOWL, 1)),
	UNPOWERED_ORB(ItemID.UNPOWERED_ORB, "Unpowered orb", 46, 52.5,
		ExperienceItem.MOLTEN_GLASS, null, new ItemStack(ItemID.UNPOWERED_ORB, 1)),
	LANTERN_LENS(ItemID.LANTERN_LENS, "Lantern lens", 49, 55,
		ExperienceItem.MOLTEN_GLASS, null, new ItemStack(ItemID.LANTERN_LENS, 1)),
	LIGHT_ORB(ItemID.LIGHT_ORB, "Light orb", 87, 70,
		ExperienceItem.MOLTEN_GLASS, null, new ItemStack(ItemID.LIGHT_ORB, 1)),
	// D'hide/Dragon Leather
	GREEN_DRAGONHIDE(ItemID.GREEN_DRAGON_LEATHER, "Tan Green D'hide", 57, 0,
		ExperienceItem.GREEN_DRAGONHIDE, null, new ItemStack(ItemID.GREEN_DRAGON_LEATHER, 1)),
	BLUE_DRAGONHIDE(ItemID.BLUE_DRAGON_LEATHER, "Tan Blue D'hide", 66, 0,
		ExperienceItem.BLUE_DRAGONHIDE, null, new ItemStack(ItemID.BLUE_DRAGON_LEATHER, 1)),
	RED_DRAGONHIDE(ItemID.RED_DRAGON_LEATHER, "Tan Red D'hide", 73, 0,
		ExperienceItem.RED_DRAGONHIDE, null, new ItemStack(ItemID.RED_DRAGON_LEATHER, 1)),
	BLACK_DRAGONHIDE(ItemID.BLACK_DRAGON_LEATHER, "Tan Black D'hide", 79, 0,
		ExperienceItem.BLACK_DRAGONHIDE, null, new ItemStack(ItemID.BLACK_DRAGON_LEATHER, 1)),
	GREEN_DRAGON_LEATHER(ItemID.GREEN_DHIDE_VAMB, "Green D'hide product", 57, 62.0,
		ExperienceItem.GREEN_DRAGON_LEATHER, null, null),
	BLUE_DRAGON_LEATHER(ItemID.BLUE_DHIDE_VAMB, "Blue D'hide product", 66, 70.0,
		ExperienceItem.BLUE_DRAGON_LEATHER, null, null),
	RED_DRAGON_LEATHER(ItemID.RED_DHIDE_VAMB, "Red D'hide product", 73, 78.0,
		ExperienceItem.RED_DRAGON_LEATHER, null, null),
	BLACK_DRAGON_LEATHER(ItemID.BLACK_DHIDE_VAMB, "Black D'hide product", 79, 86.0,
		ExperienceItem.BLACK_DRAGON_LEATHER, null, null),
	// Uncut Gems
	UNCUT_OPAL(ItemID.OPAL, "Cut opal", 1, 15.0,
		ExperienceItem.UNCUT_OPAL, null, new ItemStack(ItemID.OPAL, 1)),
	UNCUT_JADE(ItemID.JADE, "Cut jade", 13, 20.0,
		ExperienceItem.UNCUT_JADE, null, new ItemStack(ItemID.JADE, 1)),
	UNCUT_RED_TOPAZ(ItemID.RED_TOPAZ, "Cut red topaz", 16, 25.0,
		ExperienceItem.UNCUT_RED_TOPAZ, null, new ItemStack(ItemID.RED_TOPAZ, 1)),
	UNCUT_SAPPHIRE(ItemID.SAPPHIRE, "Cut sapphire", 20, 50.0,
		ExperienceItem.UNCUT_SAPPHIRE, null, new ItemStack(ItemID.SAPPHIRE, 1)),
	UNCUT_EMERALD(ItemID.EMERALD, "Cut emerald", 27, 67.5,
		ExperienceItem.UNCUT_EMERALD, null, new ItemStack(ItemID.EMERALD, 1)),
	UNCUT_RUBY(ItemID.RUBY, "Cut ruby", 34, 85,
		ExperienceItem.UNCUT_RUBY, null, new ItemStack(ItemID.RUBY, 1)),
	UNCUT_DIAMOND(ItemID.DIAMOND, "Cut diamond", 43, 107.5,
		ExperienceItem.UNCUT_DIAMOND, null, new ItemStack(ItemID.DIAMOND, 1)),
	UNCUT_DRAGONSTONE(ItemID.DRAGONSTONE, "Cut dragonstone", 55, 137.5,
		ExperienceItem.UNCUT_DRAGONSTONE, null, new ItemStack(ItemID.DRAGONSTONE, 1)),
	UNCUT_ONYX(ItemID.ONYX, "Cut onyx", 67, 167.5,
		ExperienceItem.UNCUT_ONYX, null, new ItemStack(ItemID.ONYX, 1)),
	UNCUT_ZENYTE(ItemID.ZENYTE, "Cut zenyte", 89, 200.0,
		ExperienceItem.UNCUT_ZENYTE, null, new ItemStack(ItemID.ZENYTE, 1)),
	// Silver Jewelery
	OPAL_RING(ItemID.OPAL_RING, "Opal ring", 1 , 10,
		ExperienceItem.OPAL, Secondaries.SILVER_BAR, new ItemStack(ItemID.OPAL_RING, 1)),
	OPAL_NECKLACE(ItemID.OPAL_NECKLACE, "Opal necklace", 16 , 35,
		ExperienceItem.OPAL, Secondaries.SILVER_BAR, new ItemStack(ItemID.OPAL_NECKLACE, 1)),
	OPAL_BRACELET(ItemID.OPAL_BRACELET, "Opal bracelet", 22 , 45,
		ExperienceItem.OPAL, Secondaries.SILVER_BAR, new ItemStack(ItemID.OPAL_BRACELET, 1)),
	OPAL_AMULET(ItemID.OPAL_AMULET, "Opal amulet", 27 , 55,
		ExperienceItem.OPAL, Secondaries.SILVER_BAR, new ItemStack(ItemID.OPAL_AMULET, 1)),
	JADE_RING(ItemID.JADE_RING, "Jade ring", 13 , 32,
		ExperienceItem.JADE, Secondaries.SILVER_BAR, new ItemStack(ItemID.JADE_RING, 1)),
	JADE_NECKLACE(ItemID.JADE_NECKLACE, "Jade necklace", 25 , 54,
		ExperienceItem.JADE, Secondaries.SILVER_BAR, new ItemStack(ItemID.JADE_NECKLACE, 1)),
	JADE_BRACELET(ItemID.JADE_BRACELET, "Jade bracelet", 29 , 60,
		ExperienceItem.JADE, Secondaries.SILVER_BAR, new ItemStack(ItemID.JADE_BRACELET, 1)),
	JADE_AMULET(ItemID.JADE_AMULET, "Jade amulet", 34 , 70,
		ExperienceItem.JADE, Secondaries.SILVER_BAR, new ItemStack(ItemID.JADE_AMULET, 1)),
	TOPAZ_RING(ItemID.TOPAZ_RING, "Topaz ring", 16 , 35,
		ExperienceItem.RED_TOPAZ, Secondaries.SILVER_BAR, new ItemStack(ItemID.TOPAZ_RING, 1)),
	TOPAZ_NECKLACE(ItemID.TOPAZ_NECKLACE, "Topaz necklace", 32 , 70,
		ExperienceItem.RED_TOPAZ, Secondaries.SILVER_BAR, new ItemStack(ItemID.TOPAZ_NECKLACE, 1)),
	TOPAZ_BRACELET(ItemID.TOPAZ_BRACELET, "Topaz bracelet", 38 , 75,
		ExperienceItem.RED_TOPAZ, Secondaries.SILVER_BAR, new ItemStack(ItemID.TOPAZ_BRACELET, 1)),
	TOPAZ_AMULET(ItemID.TOPAZ_AMULET, "Topaz amulet", 45 , 80,
		ExperienceItem.RED_TOPAZ, Secondaries.SILVER_BAR, new ItemStack(ItemID.TOPAZ_AMULET, 1)),
	// Gold Jewelery
	SAPPHIRE_RING(ItemID.SAPPHIRE_RING, "Sapphire ring", 20 , 40,
		ExperienceItem.SAPPHIRE, Secondaries.GOLD_BAR, new ItemStack(ItemID.SAPPHIRE_RING, 1)),
	SAPPHIRE_NECKLACE(ItemID.SAPPHIRE_NECKLACE, "Sapphire necklace", 22 , 55,
		ExperienceItem.SAPPHIRE, Secondaries.GOLD_BAR, new ItemStack(ItemID.SAPPHIRE_NECKLACE, 1)),
	SAPPHIRE_BRACELET(ItemID.SAPPHIRE_BRACELET, "Sapphire bracelet", 23 , 60,
		ExperienceItem.SAPPHIRE, Secondaries.GOLD_BAR, new ItemStack(ItemID.SAPPHIRE_BRACELET, 1)),
	SAPPHIRE_AMULET(ItemID.SAPPHIRE_AMULET, "Sapphire amulet", 24 , 65,
		ExperienceItem.SAPPHIRE, Secondaries.GOLD_BAR, new ItemStack(ItemID.SAPPHIRE_AMULET, 1)),
	EMERALD_RING(ItemID.EMERALD_RING, "Emerald ring", 27 , 55,
		ExperienceItem.EMERALD, Secondaries.GOLD_BAR, new ItemStack(ItemID.EMERALD_RING, 1)),
	EMERALD_NECKLACE(ItemID.EMERALD_NECKLACE, "Emerald necklace", 29 , 60,
		ExperienceItem.EMERALD, Secondaries.GOLD_BAR, new ItemStack(ItemID.EMERALD_NECKLACE, 1)),
	EMERALD_BRACELET(ItemID.EMERALD_BRACELET, "Emerald bracelet", 30 , 65,
		ExperienceItem.EMERALD, Secondaries.GOLD_BAR, new ItemStack(ItemID.EMERALD_BRACELET, 1)),
	EMERALD_AMULET(ItemID.EMERALD_AMULET, "Emerald amulet", 31 , 70,
		ExperienceItem.EMERALD, Secondaries.GOLD_BAR, new ItemStack(ItemID.EMERALD_AMULET, 1)),
	RUBY_RING(ItemID.RUBY_RING, "Ruby ring", 34 , 70,
		ExperienceItem.RUBY, Secondaries.GOLD_BAR, new ItemStack(ItemID.RUBY_RING, 1)),
	RUBY_NECKLACE(ItemID.RUBY_NECKLACE, "Ruby necklace", 40 , 75,
		ExperienceItem.RUBY, Secondaries.GOLD_BAR, new ItemStack(ItemID.RUBY_NECKLACE, 1)),
	RUBY_BRACELET(ItemID.RUBY_BRACELET, "Ruby bracelet", 42 , 80,
		ExperienceItem.RUBY, Secondaries.GOLD_BAR, new ItemStack(ItemID.RUBY_BRACELET, 1)),
	RUBY_AMULET(ItemID.RUBY_AMULET, "Ruby amulet", 50 , 85,
		ExperienceItem.RUBY, Secondaries.GOLD_BAR, new ItemStack(ItemID.RUBY_AMULET, 1)),
	DIAMOND_RING(ItemID.DIAMOND_RING, "Diamond ring", 43 , 85,
		ExperienceItem.DIAMOND, Secondaries.GOLD_BAR, new ItemStack(ItemID.DIAMOND_RING, 1)),
	DIAMOND_NECKLACE(ItemID.DIAMOND_NECKLACE, "Diamond necklace", 56 , 90,
		ExperienceItem.DIAMOND, Secondaries.GOLD_BAR, new ItemStack(ItemID.DIAMOND_NECKLACE, 1)),
	DIAMOND_BRACELET(ItemID.DIAMOND_BRACELET, "Diamond bracelet", 58 , 95,
		ExperienceItem.DIAMOND, Secondaries.GOLD_BAR, new ItemStack(ItemID.DIAMOND_BRACELET, 1)),
	DIAMOND_AMULET(ItemID.DIAMOND_AMULET, "Diamond amulet", 70 , 100,
		ExperienceItem.DIAMOND, Secondaries.GOLD_BAR, new ItemStack(ItemID.DIAMOND_AMULET, 1)),
	DRAGONSTONE_RING(ItemID.DRAGONSTONE_RING, "Dragonstone ring", 55 , 100,
		ExperienceItem.DRAGONSTONE, Secondaries.GOLD_BAR, new ItemStack(ItemID.DRAGONSTONE_RING, 1)),
	DRAGON_NECKLACE(ItemID.DRAGON_NECKLACE, "Dragon necklace", 72 , 105,
		ExperienceItem.DRAGONSTONE, Secondaries.GOLD_BAR, new ItemStack(ItemID.DRAGON_NECKLACE, 1)),
	DRAGONSTONE_BRACELET(ItemID.DRAGONSTONE_BRACELET, "Dragonstone bracelet", 74 , 110,
		ExperienceItem.DRAGONSTONE, Secondaries.GOLD_BAR, new ItemStack(ItemID.DRAGONSTONE_BRACELET, 1)),
	DRAGONSTONE_AMULET(ItemID.DRAGONSTONE_AMULET, "Dragonstone amulet", 80 , 150,
		ExperienceItem.DRAGONSTONE, Secondaries.GOLD_BAR, new ItemStack(ItemID.DRAGONSTONE_AMULET, 1)),
	ONYX_RING(ItemID.ONYX_RING, "Onyx ring", 67 , 115,
		ExperienceItem.ONYX, Secondaries.GOLD_BAR, new ItemStack(ItemID.ONYX_RING, 1)),
	ONYX_NECKLACE(ItemID.ONYX_NECKLACE, "Onyx necklace", 82 , 120,
		ExperienceItem.ONYX, Secondaries.GOLD_BAR, new ItemStack(ItemID.ONYX_NECKLACE, 1)),
	REGEN_BRACELET(ItemID.REGEN_BRACELET, "Regen bracelet", 84 , 125,
		ExperienceItem.ONYX, Secondaries.GOLD_BAR, new ItemStack(ItemID.REGEN_BRACELET, 1)),
	ONYX_AMULET(ItemID.ONYX_AMULET, "Onyx amulet", 90 , 165,
		ExperienceItem.ONYX, Secondaries.GOLD_BAR, new ItemStack(ItemID.ONYX_AMULET, 1)),
	ZENYTE_RING(ItemID.ZENYTE_RING, "Zenyte ring", 89 , 150,
		ExperienceItem.ZENYTE, Secondaries.GOLD_BAR, new ItemStack(ItemID.ZENYTE_RING, 1)),
	ZENYTE_NECKLACE(ItemID.ZENYTE_NECKLACE, "Zenyte necklace", 92 , 165,
		ExperienceItem.ZENYTE, Secondaries.GOLD_BAR, new ItemStack(ItemID.ZENYTE_NECKLACE, 1)),
	ZENYTE_BRACELET(ItemID.ZENYTE_BRACELET, "Zenyte bracelet", 95 , 180,
		ExperienceItem.ZENYTE, Secondaries.GOLD_BAR, new ItemStack(ItemID.ZENYTE_BRACELET, 1)),
	ZENYTE_AMULET(ItemID.ZENYTE_AMULET, "Zenyte amulet", 98 , 200 ,
		ExperienceItem.ZENYTE, Secondaries.GOLD_BAR, new ItemStack(ItemID.ZENYTE_AMULET, 1)),
	// Battle Staves
	WATER_BATTLESTAFF(ItemID.WATER_BATTLESTAFF, "Water battlestaff", 54, 100,
		ExperienceItem.BATTLESTAFF, Secondaries.WATER_ORB, new ItemStack(ItemID.WATER_BATTLESTAFF, 1)),
	EARTH_BATTLESTAFF(ItemID.EARTH_BATTLESTAFF, "Earth battlestaff", 58, 112.5,
		ExperienceItem.BATTLESTAFF, Secondaries.EARTH_ORB, new ItemStack(ItemID.EARTH_BATTLESTAFF, 1)),
	FIRE_BATTLESTAFF(ItemID.FIRE_BATTLESTAFF, "Fire battlestaff", 62, 125,
		ExperienceItem.BATTLESTAFF, Secondaries.FIRE_ORB, new ItemStack(ItemID.FIRE_BATTLESTAFF, 1)),
	AIR_BATTLESTAFF(ItemID.AIR_BATTLESTAFF, "Air battlestaff", 66, 137.5,
		ExperienceItem.BATTLESTAFF, Secondaries.AIR_ORB, new ItemStack(ItemID.AIR_BATTLESTAFF, 1)),
	/**
	 * Smithing
	 */
	// Smelting ores (Furnace)
	IRON_ORE(ItemID.IRON_BAR, "Iron bar", 15, 12.5,
		ExperienceItem.IRON_ORE, Secondaries.COAL_ORE, new ItemStack(ItemID.IRON_BAR, 1)),
	STEEL_ORE(ItemID.STEEL_BAR, "Steel bar", 30, 17.5,
		ExperienceItem.IRON_ORE, Secondaries.COAL_ORE_2, new ItemStack(ItemID.STEEL_BAR, 1)),
	SILVER_ORE(ItemID.SILVER_BAR, "Silver Bar", 20, 13.67,
		ExperienceItem.SILVER_ORE, null, new ItemStack(ItemID.SILVER_BAR, 1)),
	GOLD_ORE(ItemID.GOLD_BAR, "Gold bar", 40, 22.5,
		ExperienceItem.GOLD_ORE, null, new ItemStack(ItemID.GOLD_BAR, 1)),
	GOLD_ORE_GAUNTLETS(ItemID.GOLDSMITH_GAUNTLETS, "Goldsmith gauntlets", 40, 56.2,
		ExperienceItem.GOLD_ORE, null, new ItemStack(ItemID.GOLD_BAR, 1)),
	MITHRIL_ORE(ItemID.MITHRIL_BAR, "Mithril bar", 50, 30,
		ExperienceItem.MITHRIL_ORE, Secondaries.COAL_ORE_4, new ItemStack(ItemID.MITHRIL_BAR, 1)),
	ADAMANTITE_ORE(ItemID.ADAMANTITE_BAR, "Adamantite bar", 70, 37.5,
		ExperienceItem.ADAMANTITE_ORE, Secondaries.COAL_ORE_6, new ItemStack(ItemID.ADAMANTITE_BAR, 1)),
	RUNITE_ORE(ItemID.RUNITE_BAR, "Runite bar", 85, 50,
		ExperienceItem.RUNITE_ORE, Secondaries.COAL_ORE_8, new ItemStack(ItemID.RUNITE_BAR, 1)),
	// Smelting bars (Anvil)
	BRONZE_BAR(ItemID.BRONZE_BAR, "Bronze products", 1, 12.5,
		ExperienceItem.BRONZE_BAR, null, null),
	IRON_BAR(ItemID.IRON_BAR, "Iron products", 15, 25.0,
		ExperienceItem.IRON_BAR, null, null),
	STEEL_BAR(ItemID.STEEL_BAR, "Steel products", 30, 37.5,
		ExperienceItem.STEEL_BAR, null, null),
	CANNONBALLS(ItemID.CANNONBALL, "Cannonballs", 35, 25.5,
		ExperienceItem.STEEL_BAR, null, new ItemStack(ItemID.CANNONBALL, 4)),
	MITHRIL_BAR(ItemID.MITHRIL_BAR, "Mithril products", 50, 50.0,
		ExperienceItem.MITHRIL_BAR, null, null),
	ADAMANTITE_BAR(ItemID.ADAMANTITE_BAR, "Adamantite products", 70, 62.5,
		ExperienceItem.ADAMANTITE_BAR, null, null),
	RUNITE_BAR(ItemID.RUNITE_BAR, "Runite products", 85, 75.0,
		ExperienceItem.RUNITE_BAR, null, null),
	/**
	 * Farming
	 */
	ACORN(ItemID.OAK_SAPLING, "Oak sapling", 15, 0,
		ExperienceItem.ACORN, null, new ItemStack(ItemID.OAK_SAPLING, 1)),
	WILLOW_SEED(ItemID.WILLOW_SAPLING, "Willow sapling", 30, 0,
		ExperienceItem.WILLOW_SEED, null, new ItemStack(ItemID.WILLOW_SAPLING, 1)),
	MAPLE_SEED(ItemID.MAPLE_SAPLING, "Maple sapling", 45, 0,
		ExperienceItem.MAPLE_SEED, null, new ItemStack(ItemID.MAPLE_SAPLING, 1)),
	YEW_SEED(ItemID.YEW_SAPLING, "Yew sapling", 60, 0,
		ExperienceItem.YEW_SEED, null, new ItemStack(ItemID.YEW_SAPLING, 1)),
	MAGIC_SEED(ItemID.MAGIC_SAPLING, "Magic sapling", 75, 0,
		ExperienceItem.MAGIC_SEED, null, new ItemStack(ItemID.MAGIC_SAPLING, 1)),
	APPLE_TREE_SEED(ItemID.APPLE_SAPLING, "Apple sapling", 27, 0,
		ExperienceItem.APPLE_TREE_SEED, null, new ItemStack(ItemID.APPLE_SAPLING, 1)),
	BANANA_TREE_SEED(ItemID.BANANA_SAPLING, "Banana sapling", 33, 0,
		ExperienceItem.BANANA_TREE_SEED, null, new ItemStack(ItemID.BANANA_SAPLING, 1)),
	ORANGE_TREE_SEED(ItemID.ORANGE_SAPLING, "Orange sapling", 39, 0,
		ExperienceItem.ORANGE_TREE_SEED, null, new ItemStack(ItemID.ORANGE_SAPLING, 1)),
	CURRY_TREE_SEED(ItemID.CURRY_SAPLING, "Curry sapling", 42, 0,
		ExperienceItem.CURRY_TREE_SEED, null, new ItemStack(ItemID.CURRY_SAPLING, 1)),
	PINEAPPLE_SEED(ItemID.PINEAPPLE_SAPLING, "Pineapple sapling", 51, 0,
		ExperienceItem.PINEAPPLE_SEED, null, new ItemStack(ItemID.PINEAPPLE_SAPLING, 1)),
	PAPAYA_TREE_SEED(ItemID.PAPAYA_SAPLING, "Papaya sapling", 57, 0,
		ExperienceItem.PAPAYA_TREE_SEED, null, new ItemStack(ItemID.PAPAYA_SAPLING, 1)),
	PALM_TREE_SEED(ItemID.PALM_SAPLING, "Palm sapling", 68, 0,
		ExperienceItem.PALM_TREE_SEED, null, new ItemStack(ItemID.PALM_SAPLING, 1)),
	CALQUAT_TREE_SEED(ItemID.CALQUAT_SAPLING, "Calquat sapling", 72, 0,
		ExperienceItem.CALQUAT_TREE_SEED, null, new ItemStack(ItemID.CALQUAT_SAPLING, 1)),
	TEAK_SEED(ItemID.TEAK_SAPLING, "Teak sapling", 35, 0,
		ExperienceItem.TEAK_SEED, null, new ItemStack(ItemID.TEAK_SAPLING, 1)),
	MAHOGANY_SEED(ItemID.MAHOGANY_SAPLING, "Mahogany sapling", 55, 0,
		ExperienceItem.MAHOGANY_SEED, null, new ItemStack(ItemID.MAHOGANY_SAPLING, 1)),
	SPIRIT_SEED(ItemID.SPIRIT_SAPLING, "Spirit sapling", 83, 0,
		ExperienceItem.SPIRIT_SEED, null, new ItemStack(ItemID.SPIRIT_SAPLING, 1)),
	OAK_SAPPLING(ItemID.OAK_SAPLING, "Oak tree", 15, 481.3,
		ExperienceItem.OAK_SAPLING, null, null),
	WILLOW_SAPLING(ItemID.WILLOW_SAPLING, "Willow tree", 30, 1481.5,
		ExperienceItem.WILLOW_SAPLING, null, null),
	MAPLE_SAPLING(ItemID.MAPLE_SAPLING, "Maple tree", 45, 3448.4,
		ExperienceItem.MAPLE_SAPLING, null, null),
	YEW_SAPLING(ItemID.YEW_SAPLING, "Yew tree", 60, 7150.9,
		ExperienceItem.YEW_SAPLING, null, null),
	MAGIC_SAPLING(ItemID.MAGIC_SAPLING, "Magic tree", 75, 13913.8,
		ExperienceItem.MAGIC_SAPLING, null, null),
	APPLE_TREE_SAPLING(ItemID.APPLE_SAPLING, "Apple tree", 27, 1272.5,
		ExperienceItem.APPLE_TREE_SAPLING, null, null),
	BANANA_TREE_SAPLING(ItemID.BANANA_SAPLING, "Banana tree", 33, 1841.5,
		ExperienceItem.BANANA_TREE_SAPLING, null, null),
	ORANGE_TREE_SAPLING(ItemID.ORANGE_SAPLING, "Orange tree", 39, 2586.7,
		ExperienceItem.ORANGE_TREE_SAPLING, null, null),
	CURRY_TREE_SAPLING(ItemID.CURRY_SAPLING, "Curry tree", 42, 3036.9,
		ExperienceItem.CURRY_TREE_SAPLING, null, null),
	PINEAPPLE_SAPLING(ItemID.PINEAPPLE_SAPLING, "Pineapple tree", 51, 4791.7,
		ExperienceItem.PINEAPPLE_SAPLING, null, null),
	PAPAYA_TREE_SAPLING(ItemID.PAPAYA_SAPLING, "Papaya tree", 57, 6380.4,
		ExperienceItem.PAPAYA_TREE_SAPLING, null, null),
	PALM_TREE_SAPLING(ItemID.PALM_SAPLING, "Palm tree", 68, 10509.6,
		ExperienceItem.PALM_TREE_SAPLING, null, null),
	CALQUAT_TREE_SAPLING(ItemID.CALQUAT_SAPLING, "Calquat tree", 72, 12516.5,
		ExperienceItem.CALQUAT_TREE_SAPLING, null, null),
	TEAK_SAPLING(ItemID.TEAK_SAPLING, "Teak tree", 35, 7325,
		ExperienceItem.TEAK_SAPLING, null, null),
	MAHOGANY_SAPLING(ItemID.MAHOGANY_SAPLING, "Mahogany tree", 55, 15783,
		ExperienceItem.MAHOGANY_SAPLING, null, null),
	SPIRIT_SAPLING(ItemID.SPIRIT_SAPLING, "Spirit tree", 83, 19500,
		ExperienceItem.SPIRIT_SAPLING, null, null),
	;

	private final int icon;
	private final String name;
	private final int level;
	private final double xp;
	private final ExperienceItem experienceItem;
	private final Skill skill;
	private final ItemStack[] secondaries;
	@Nullable
	private final ItemStack output;
	private ItemInfo outputItemInfo = null;
	@Nullable
	private final ExperienceItem linkedItem;

	private static final ImmutableMultimap<ExperienceItem, Activity> ITEM_MAP;
	public static final ImmutableSortedSet<Skill> BANKABLE_SKILLS;
	static
	{
		final ImmutableMultimap.Builder<ExperienceItem, Activity> map = ImmutableMultimap.builder();
		final ImmutableSortedSet.Builder<Skill> set = ImmutableSortedSet.orderedBy(Comparator.comparing(Skill::getName));
		for (final Activity item : values())
		{
			map.put(item.getExperienceItem(), item);
			set.add(item.getSkill());
		}
		ITEM_MAP = map.build();
		BANKABLE_SKILLS = set.build();
	}

	Activity(
		final int icon,
		final String name,
		final int level,
		final double xp,
		final ExperienceItem experienceItem,
		@Nullable final Secondaries secondaries,
		@Nullable final ItemStack output)
	{
		this.icon = icon;
		this.name = name;
		this.skill = experienceItem.getSkill();
		this.level = level;
		this.xp = xp;
		this.experienceItem = experienceItem;
		this.secondaries = secondaries == null ? new ItemStack[0] : secondaries.getItems();
		this.output = output;
		this.linkedItem = output == null ? null : ExperienceItem.getByItemId(output.getId());
	}

	/**
	 * Get all Activities for this ExperienceItem
	 * @param item ExperienceItem to check for
	 * @return an empty Collection if no activities
	 */
	public static List<Activity> getByExperienceItem(ExperienceItem item)
	{
		final Collection<Activity> activities = ITEM_MAP.get(item);
		if (activities == null)
		{
			return new ArrayList<>();
		}

		return new ArrayList<>(activities);
	}

	/**
	 * Get all Activities for this ExperienceItem limited to level
	 * @param item ExperienceItem to check for
	 * @param limitLevel Level to check Activitiy requirements against. -1/0 value disables limits
	 * @return an empty Collection if no activities
	 */
	public static List<Activity> getByExperienceItem(final ExperienceItem item, final int limitLevel)
	{
		// Return as list to allow getting by index
		final List<Activity> l = getByExperienceItem(item);
		if (limitLevel <= 0)
		{
			return l;
		}

		return l.stream().filter(a -> a.getLevel() <= limitLevel).collect(Collectors.toList());
	}

	/**
	 * Attaches the Item Composition to each ExperienceItem on client initial load
	 * @param m ItemManager
	 */
	public static void prepareItemCompositions(ItemManager m)
	{
		for (Activity a : values())
		{
			final ItemStack output = a.getOutput();
			if (output == null)
			{
				continue;
			}

			if (a.getOutputItemInfo() != null)
			{
				return;
			}

			final ItemComposition c = m.getItemComposition(output.getId());
			a.outputItemInfo = new ItemInfo(c.getName(), c.isStackable());
		}
	}

	public double getXpRate(final float modifier)
	{
		return experienceItem.isIgnoreBonus() ? xp : xp * modifier;
	}
}
