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

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSortedSet;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
import thestonedturtle.bankedexperience.data.modifiers.ConsumptionModifier;
import thestonedturtle.bankedexperience.data.modifiers.Modifier;

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
	ANTIDOTE_PLUS_UNF(ItemID.ANTIDOTE_UNF, "Antidote+ (unf)", 68, 0,
		ExperienceItem.TOADFLAX, Secondaries.COCONUT_MILK, new ItemStack(ItemID.ANTIDOTE_UNF, 1)),
	ANTIDOTE_PLUS(ItemID.ANTIDOTE4, "Antidote+", 68, 155,
		ExperienceItem.ANTIDOTE_PLUS_POTION_UNF, Secondaries.ANTIDOTE_PLUS, new ItemStack(ItemID.ANTIDOTE4, 1)),
	SARADOMIN_BREW(ItemID.SARADOMIN_BREW3, "Saradomin brew", 81, 180,
		ExperienceItem.TOADFLAX_POTION_UNF, Secondaries.SARADOMIN_BREW, new ItemStack(ItemID.SARADOMIN_BREW3, 1)),
	// Irit
	IRIT_POTION_UNF(ItemID.IRIT_POTION_UNF, "Unfinished potion", 45, 0,
		ExperienceItem.IRIT_LEAF, Secondaries.UNFINISHED_POTION, new ItemStack(ItemID.IRIT_POTION_UNF, 1)),
	SUPER_ATTACK(ItemID.SUPER_ATTACK3, "Super attack", 45, 100,
		ExperienceItem.IRIT_POTION_UNF, Secondaries.SUPER_ATTACK, new ItemStack(ItemID.SUPER_ATTACK3, 1)),
	SUPERANTIPOISON(ItemID.SUPERANTIPOISON3, "Superantipoison", 48, 106.3,
		ExperienceItem.IRIT_POTION_UNF, Secondaries.SUPERANTIPOISON, new ItemStack(ItemID.SUPERANTIPOISON3, 1)),
	ANTIDOTE_PLUS_PlUS_UNF(ItemID.ANTIDOTE_UNF_5951, "Antidote++ (unf)", 79, 0,
		ExperienceItem.IRIT_LEAF, Secondaries.COCONUT_MILK, new ItemStack(ItemID.ANTIDOTE_UNF_5951, 1)),
	ANTIDOTE_PLUS_PLUS(ItemID.ANTIDOTE4_5952, "Antidote++", 79, 177.5,
		ExperienceItem.ANTIDOTE_PLUS_PLUS_POTION_UNF, Secondaries.ANTIDOTE_PLUS_PLUS, new ItemStack(ItemID.ANTIDOTE4_5952, 1)),
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
	WEAPON_POISON(ItemID.WEAPON_POISON, "Weapon Poison", 60, 137.5,
		ExperienceItem.KWUARM_POTION_UNF, Secondaries.WEAPON_POISON, new ItemStack(ItemID.WEAPON_POISON, 1)),
	SNAPDRAGON_POTION_UNF(ItemID.SNAPDRAGON_POTION_UNF, "Unfinished potion", 63, 0,
		ExperienceItem.SNAPDRAGON, Secondaries.UNFINISHED_POTION, new ItemStack(ItemID.SNAPDRAGON_POTION_UNF, 1)),
	SUPER_RESTORE(ItemID.SUPER_RESTORE3, "Super restore", 63, 142.5,
		ExperienceItem.SNAPDRAGON_POTION_UNF, Secondaries.SUPER_RESTORE, new ItemStack(ItemID.SUPER_RESTORE3, 1)),
	SANFEW_SERUM(ItemID.SANFEW_SERUM3, "Sanfew serum", 65, 160,
		ExperienceItem.SNAPDRAGON_POTION_UNF, Secondaries.SANFEW_SERUM, new ItemStack(ItemID.SANFEW_SERUM3, 1)),
	// Cadantine
	CADANTINE_POTION_UNF(ItemID.CADANTINE_POTION_UNF, "Unfinished potion", 66, 0,
		ExperienceItem.CADANTINE, Secondaries.UNFINISHED_POTION, new ItemStack(ItemID.CADANTINE_POTION_UNF, 1)),
	CADANTINE_BLOOD_POTION_UNF(ItemID.CADANTINE_BLOOD_POTION_UNF, "Unfinished blood potion", 80, 0,
		ExperienceItem.CADANTINE, Secondaries.VIAL_OF_BLOOD, new ItemStack(ItemID.CADANTINE_BLOOD_POTION_UNF, 1)),
	SUPER_DEFENCE_POTION(ItemID.SUPER_DEFENCE3, "Super defence", 66, 150,
		ExperienceItem.CADANTINE_POTION_UNF, Secondaries.SUPER_DEFENCE_POTION, new ItemStack(ItemID.SUPER_DEFENCE3, 1)),
	BASTION_POTION(ItemID.BASTION_POTION3, "Bastion potion", 80, 155,
		ExperienceItem.CADANTINE_BLOOD_POTION_UNF, Secondaries.RANGING_POTION, new ItemStack(ItemID.BASTION_POTION3, 1)),
	BATTLEMAGE_POTION(ItemID.BATTLEMAGE_POTION3, "Battlemage potion", 80, 155,
		ExperienceItem.CADANTINE_BLOOD_POTION_UNF, Secondaries.MAGIC_POTION, new ItemStack(ItemID.BATTLEMAGE_POTION3, 1)),
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
	MENAPHITE_REMEDY(ItemID.MENAPHITE_REMEDY3, "Menaphite remedy", 88, 200,
		ExperienceItem.DWARF_WEED_POTION_UNF, Secondaries.MENAPHITE_REMEDY, new ItemStack(ItemID.MENAPHITE_REMEDY3, 1)),
	// Torstol
	TORSTOL_POTION_UNF(ItemID.TORSTOL_POTION_UNF, "Unfinished potion", 78, 0,
		ExperienceItem.TORSTOL, Secondaries.UNFINISHED_POTION, new ItemStack(ItemID.TORSTOL_POTION_UNF, 1)),
	SUPER_COMBAT_POTION(ItemID.SUPER_COMBAT_POTION4, "Super combat", 90, 150,
		ExperienceItem.TORSTOL, Secondaries.SUPER_COMBAT_POTION, new ItemStack(ItemID.SUPER_COMBAT_POTION4, 1)),
	ANTIVENOM_PLUS(ItemID.ANTIVENOM3_12915, "Anti-venom+", 94, 125,
		ExperienceItem.TORSTOL, Secondaries.ANTIVENOM_PLUS, new ItemStack(ItemID.ANTIVENOM3_12915, 1)),
	ZAMORAK_BREW(ItemID.ZAMORAK_BREW3, "Zamorak brew", 78, 175,
		ExperienceItem.TORSTOL_POTION_UNF, Secondaries.ZAMORAK_BREW, new ItemStack(ItemID.ZAMORAK_BREW3, 1)),
	SUPER_COMBAT_POTION_2(ItemID.SUPER_COMBAT_POTION4, "Super combat", 90, 150,
		ExperienceItem.TORSTOL_POTION_UNF, Secondaries.SUPER_COMBAT_POTION, new ItemStack(ItemID.SUPER_COMBAT_POTION4, 1)),
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
	// Cleaning Grimy Herbs but no XP (Nardah)
	NARDAH_CLEAN_GUAM(ItemID.GUAM_LEAF, "Nardah clean guam", 3, 0,
		ExperienceItem.GRIMY_GUAM_LEAF, null, new ItemStack(ItemID.GUAM_LEAF, 1)),
	NARDAH_CLEAN_MARRENTILL(ItemID.MARRENTILL, "Nardah clean marrentill", 5, 0,
		ExperienceItem.GRIMY_MARRENTILL, null, new ItemStack(ItemID.MARRENTILL, 1)),
	NARDAH_CLEAN_TARROMIN(ItemID.TARROMIN, "Nardah clean tarromin", 11, 0,
		ExperienceItem.GRIMY_TARROMIN, null, new ItemStack(ItemID.TARROMIN, 1)),
	NARDAH_CLEAN_HARRALANDER(ItemID.HARRALANDER, "Nardah clean harralander", 20, 0,
		ExperienceItem.GRIMY_HARRALANDER, null, new ItemStack(ItemID.HARRALANDER, 1)),
	NARDAH_CLEAN_RANARR_WEED(ItemID.RANARR_WEED, "Nardah clean ranarr weed", 25, 0,
		ExperienceItem.GRIMY_RANARR_WEED, null, new ItemStack(ItemID.RANARR_WEED, 1)),
	NARDAH_CLEAN_TOADFLAX(ItemID.TOADFLAX, "Nardah clean toadflax", 30, 0,
		ExperienceItem.GRIMY_TOADFLAX, null, new ItemStack(ItemID.TOADFLAX, 1)),
	NARDAH_CLEAN_IRIT_LEAF(ItemID.IRIT_LEAF, "Nardah clean irit leaf", 40, 0,
		ExperienceItem.GRIMY_IRIT_LEAF, null, new ItemStack(ItemID.IRIT_LEAF, 1)),
	NARDAH_CLEAN_AVANTOE(ItemID.AVANTOE, "Nardah clean avantoe", 48, 0,
		ExperienceItem.GRIMY_AVANTOE, null, new ItemStack(ItemID.AVANTOE, 1)),
	NARDAH_CLEAN_KWUARM(ItemID.KWUARM, "Nardah clean kwuarm", 54, 0,
		ExperienceItem.GRIMY_KWUARM, null, new ItemStack(ItemID.KWUARM, 1)),
	NARDAH_CLEAN_SNAPDRAGON(ItemID.SNAPDRAGON, "Nardah clean snapdragon", 59, 0,
		ExperienceItem.GRIMY_SNAPDRAGON, null, new ItemStack(ItemID.SNAPDRAGON, 1)),
	NARDAH_CLEAN_CADANTINE(ItemID.CADANTINE, "Nardah clean cadantine", 65, 0,
		ExperienceItem.GRIMY_CADANTINE, null, new ItemStack(ItemID.CADANTINE, 1)),
	NARDAH_CLEAN_LANTADYME(ItemID.LANTADYME, "Nardah clean lantadyme", 67, 0,
		ExperienceItem.GRIMY_LANTADYME, null, new ItemStack(ItemID.LANTADYME, 1)),
	NARDAH_CLEAN_DWARF_WEED(ItemID.DWARF_WEED, "Nardah clean dwarf weed", 70, 0,
		ExperienceItem.GRIMY_DWARF_WEED, null, new ItemStack(ItemID.DWARF_WEED, 1)),
	NARDAH_CLEAN_TORSTOL(ItemID.TORSTOL, "Nardah clean torstol", 75, 0,
		ExperienceItem.GRIMY_TORSTOL, null, new ItemStack(ItemID.TORSTOL, 1)),
	// Degrime SPell
	DEGRIME_GUAM(ItemID.GUAM_LEAF, "Degrime guam", 3, 1.2,
		ExperienceItem.GRIMY_GUAM_LEAF, Secondaries.DEGRIME, new ItemStack(ItemID.GUAM_LEAF, 1)),
	DEGRIME_MARRENTILL(ItemID.MARRENTILL, "Degrime marrentill", 5, 1.9,
		ExperienceItem.GRIMY_MARRENTILL, Secondaries.DEGRIME, new ItemStack(ItemID.MARRENTILL, 1)),
	DEGRIME_TARROMIN(ItemID.TARROMIN, "Degrime tarromin", 11, 2.5,
		ExperienceItem.GRIMY_TARROMIN, Secondaries.DEGRIME, new ItemStack(ItemID.TARROMIN, 1)),
	DEGRIME_HARRALANDER(ItemID.HARRALANDER, "Degrime harralander", 20, 3.1,
		ExperienceItem.GRIMY_HARRALANDER, Secondaries.DEGRIME, new ItemStack(ItemID.HARRALANDER, 1)),
	DEGRIME_RANARR_WEED(ItemID.RANARR_WEED, "Degrime ranarr weed", 25, 3.7,
		ExperienceItem.GRIMY_RANARR_WEED, Secondaries.DEGRIME, new ItemStack(ItemID.RANARR_WEED, 1)),
	DEGRIME_TOADFLAX(ItemID.TOADFLAX, "Degrime toadflax", 30, 4.0,
		ExperienceItem.GRIMY_TOADFLAX, Secondaries.DEGRIME, new ItemStack(ItemID.TOADFLAX, 1)),
	DEGRIME_IRIT_LEAF(ItemID.IRIT_LEAF, "Degrime irit leaf", 40, 4.4,
		ExperienceItem.GRIMY_IRIT_LEAF, Secondaries.DEGRIME, new ItemStack(ItemID.IRIT_LEAF, 1)),
	DEGRIME_AVANTOE(ItemID.AVANTOE, "Degrime avantoe", 48, 5.0,
		ExperienceItem.GRIMY_AVANTOE, Secondaries.DEGRIME, new ItemStack(ItemID.AVANTOE, 1)),
	DEGRIME_KWUARM(ItemID.KWUARM, "Degrime kwuarm", 54, 5.6,
		ExperienceItem.GRIMY_KWUARM, Secondaries.DEGRIME, new ItemStack(ItemID.KWUARM, 1)),
	DEGRIME_SNAPDRAGON(ItemID.SNAPDRAGON, "Degrime snapdragon", 59, 5.9,
		ExperienceItem.GRIMY_SNAPDRAGON, Secondaries.DEGRIME, new ItemStack(ItemID.SNAPDRAGON, 1)),
	DEGRIME_CADANTINE(ItemID.CADANTINE, "Degrime cadantine", 65, 6.2,
		ExperienceItem.GRIMY_CADANTINE, Secondaries.DEGRIME, new ItemStack(ItemID.CADANTINE, 1)),
	DEGRIME_LANTADYME(ItemID.LANTADYME, "Degrime lantadyme", 67, 6.5,
		ExperienceItem.GRIMY_LANTADYME, Secondaries.DEGRIME, new ItemStack(ItemID.LANTADYME, 1)),
	DEGRIME_DWARF_WEED(ItemID.DWARF_WEED, "Degrime dwarf weed", 70, 6.9,
		ExperienceItem.GRIMY_DWARF_WEED, Secondaries.DEGRIME, new ItemStack(ItemID.DWARF_WEED, 1)),
	DEGRIME_TORSTOL(ItemID.TORSTOL, "Degrime torstol", 75, 7.5,
		ExperienceItem.GRIMY_TORSTOL, Secondaries.DEGRIME, new ItemStack(ItemID.TORSTOL, 1)),
	// Weapon Poison
	WEAPON_POISON_PLUS_UNF(ItemID.WEAPON_POISON_UNF, "Weapon poison+ (unf)", 73, 0,
		ExperienceItem.CACTUS_SPINE, Secondaries.COCONUT_MILK, new ItemStack(ItemID.WEAPON_POISON_UNF, 1)),
	WEAPON_POISON_PLUS(ItemID.WEAPON_POISON_5937, "Weapon poison(+)", 73, 165,
		ExperienceItem.WEAPON_POISON_PLUS_UNF, Secondaries.WEAPON_POISON_PLUS, new ItemStack(ItemID.WEAPON_POISON_5937, 1)),
	WEAPON_POISON_PLUS_PLUS_UNF(ItemID.WEAPON_POISON_UNF_5939, "Weapon poison++ (unf)", 82, 0,
		ExperienceItem.CAVE_NIGHTSHADE, Secondaries.COCONUT_MILK, new ItemStack(ItemID.WEAPON_POISON_UNF_5939, 1)),
	WEAPON_POISON_PLUS_PLUS(ItemID.WEAPON_POISON_5940, "Weapon poison(++)", 82, 190,
		ExperienceItem.WEAPON_POISON_PLUS_PLUS_UNF, Secondaries.WEAPON_POISON_PLUS_PLUS, new ItemStack(ItemID.WEAPON_POISON_5940, 1)),
	// Other
	AMYLASE_CRYSTAL(ItemID.AMYLASE_CRYSTAL, "Convert to crystals", 0, 0,
		ExperienceItem.MARK_OF_GRACE, null , new ItemStack(ItemID.AMYLASE_CRYSTAL, 10)),
	STAMINA_POTION(ItemID.STAMINA_POTION1, "Stamina potion", 77, 25.5,
		ExperienceItem.AMYLASE_CRYSTAL, Secondaries.STAMINA_POTION , new ItemStack(ItemID.STAMINA_POTION1, 1)),
	EXTENDED_ANTIFIRE(ItemID.EXTENDED_ANTIFIRE1, "Extended antifire", 84, 27.5,
		ExperienceItem.LAVA_SCALE_SHARD, Secondaries.EXTENDED_ANTIFIRE , new ItemStack(ItemID.EXTENDED_ANTIFIRE1, 1)),
	EXTENDED_SUPER_ANTIFIRE(ItemID.EXTENDED_SUPER_ANTIFIRE1, "Extended super antifire", 98, 40,
		ExperienceItem.LAVA_SCALE_SHARD, Secondaries.EXTENDED_SUPER_ANTIFIRE , new ItemStack(ItemID.EXTENDED_SUPER_ANTIFIRE1, 1)),
	/**
	 * Construction
	 */
	PLANK(ItemID.PLANK, "Regular Plank", 1, 0,
		ExperienceItem.LOGS, Secondaries.COINS_100, new ItemStack(ItemID.PLANK, 1)),
	PLANKS(ItemID.PLANK, "Regular plank products", 1, 29,
		ExperienceItem.PLANK, null, null),
	OAK_PLANK(ItemID.OAK_PLANK, "Oak Plank", 15, 0,
		ExperienceItem.OAK_LOGS, Secondaries.COINS_250, new ItemStack(ItemID.OAK_PLANK, 1)),
	OAK_PLANKS(ItemID.OAK_PLANK, "Oak products", 15, 60,
		ExperienceItem.OAK_PLANK, null, null),
	TEAK_PLANK(ItemID.TEAK_PLANK, "Teak Plank", 35, 0,
		ExperienceItem.TEAK_LOGS, Secondaries.COINS_500, new ItemStack(ItemID.TEAK_PLANK, 1)),
	TEAK_PLANKS(ItemID.TEAK_PLANK, "Teak products", 35, 90,
		ExperienceItem.TEAK_PLANK, null, null),
	MYTHICAL_CAPE(ItemID.MYTHICAL_CAPE, "Mythical cape racks", 47, 123.33,
		ExperienceItem.TEAK_PLANK, null, null),
	MAHOGANY_PLANK(ItemID.MAHOGANY_PLANK, "Mahogany Plank", 50, 0,
		ExperienceItem.MAHOGANY_LOGS, Secondaries.COINS_1500, new ItemStack(ItemID.MAHOGANY_PLANK, 1)),
	MAHOGANY_PLANKS(ItemID.MAHOGANY_PLANK, "Mahogany products", 50, 140,
		ExperienceItem.MAHOGANY_PLANK, null, null),
	PLANK_MAKE(ItemID.ASTRAL_RUNE, "Regular Plank Make", 1, 0,
		ExperienceItem.LOGS, Secondaries.PLANK_MAKE_REGULAR, new ItemStack(ItemID.PLANK, 1)),
	OAK_PLANK_MAKE(ItemID.ASTRAL_RUNE, "Oak Plank Make", 15, 0,
		ExperienceItem.OAK_LOGS, Secondaries.PLANK_MAKE_OAK, new ItemStack(ItemID.OAK_PLANK, 1)),
	TEAK_PLANK_MAKE(ItemID.ASTRAL_RUNE, "Teak Plank Make", 35, 0,
		ExperienceItem.TEAK_LOGS, Secondaries.PLANK_MAKE_TEAK, new ItemStack(ItemID.TEAK_PLANK, 1)),
	MAHOGANY_PLANK_MAKE(ItemID.ASTRAL_RUNE, "Mahogany Plank Make", 50, 0,
		ExperienceItem.MAHOGANY_LOGS, Secondaries.PLANK_MAKE_MAHOGANY, new ItemStack(ItemID.MAHOGANY_PLANK, 1)),
	LONG_BONE(ItemID.LONG_BONE, "Long Bone", 30, 4500, ExperienceItem.LONG_BONE, null, null),
	CURVED_BONE(ItemID.CURVED_BONE, "Curved Bone", 30, 6750, ExperienceItem.CURVED_BONE, null, null),
	// Mahogany Homes XP rates are calculated utilizing the averages as generated by the wiki
	// https://oldschool.runescape.wiki/w/Mahogany_Homes
	MAHOGANY_HOMES_PLANK(ItemID.HOSIDIUS_BLUEPRINTS, "Mahogany Homes", 1, 93.650, true,
		ExperienceItem.PLANK, Secondaries.STEEL_BAR_PLANK, null),
	MAHOGANY_HOMES_OAK(ItemID.HOSIDIUS_BLUEPRINTS, "Mahogany Homes", 20, 200.003, true,
		ExperienceItem.OAK_PLANK, Secondaries.STEEL_BAR_OAK, null),
	MAHOGANY_HOMES_TEAK(ItemID.HOSIDIUS_BLUEPRINTS, "Mahogany Homes", 50, 287.852, true,
		ExperienceItem.TEAK_PLANK, Secondaries.STEEL_BAR_TEAK, null),
	MAHOGANY_HOMES_MAHOGANY(ItemID.HOSIDIUS_BLUEPRINTS, "Mahogany Homes", 70, 346.120, true,
		ExperienceItem.MAHOGANY_PLANK, Secondaries.STEEL_BAR_MAHOGANY, null),
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
	SUPERIOR_DRAGON_BONES(ItemID.SUPERIOR_DRAGON_BONES, "Superior dragon bones", 70, 150.0,
		ExperienceItem.SUPERIOR_DRAGON_BONES, null, null),
	WYRM_BONES(ItemID.WYRM_BONES, "Wyrm bones", 1, 50.0,
		ExperienceItem.WYRM_BONES, null, null),
	DRAKE_BONES(ItemID.DRAKE_BONES, "Drake bones", 1, 80.0,
		ExperienceItem.DRAKE_BONES, null, null),
	HYDRA_BONES(ItemID.HYDRA_BONES, "Hydra bones", 1, 110.0,
		ExperienceItem.HYDRA_BONES, null, null),
	// Bonemeal
	BONEMEAL(ItemID.BONEMEAL, "Bonemeal", 1, 18,
		ExperienceItem.BONEMEAL, Secondaries.BUCKET_OF_SLIME, null),
	WOLF_BONEMEAL(ItemID.WOLF_BONEMEAL, "Wolf bonemeal", 1, 18,
		ExperienceItem.WOLF_BONEMEAL, Secondaries.BUCKET_OF_SLIME, null),
	BURNT_BONEMEAL(ItemID.BURNT_BONEMEAL, "Burnt bonemeal", 1, 18,
		ExperienceItem.BURNT_BONEMEAL, Secondaries.BUCKET_OF_SLIME, null),
	MONKEY_BONEMEAL(ItemID.MONKEY_BONEMEAL, "Monkey bonemeal", 1, 20,
		ExperienceItem.MONKEY_BONEMEAL, Secondaries.BUCKET_OF_SLIME, null),
	BAT_BONEMEAL(ItemID.BAT_BONEMEAL, "Bat bonemeal", 1, 21.2,
		ExperienceItem.BAT_BONEMEAL, Secondaries.BUCKET_OF_SLIME, null),
	JOGRE_BONEMEAL(ItemID.JOGRE_BONEMEAL, "Jogre bonemeal", 1, 60,
		ExperienceItem.JOGRE_BONEMEAL, Secondaries.BUCKET_OF_SLIME, null),
	BIG_BONEMEAL(ItemID.BIG_BONEMEAL, "Big bonemeal", 1, 60,
		ExperienceItem.BIG_BONEMEAL, Secondaries.BUCKET_OF_SLIME, null),
	ZOGRE_BONEMEAL(ItemID.ZOGRE_BONEMEAL, "Zogre bonemeal", 1, 90,
		ExperienceItem.ZOGRE_BONEMEAL, Secondaries.BUCKET_OF_SLIME, null),
	SHAIKAHAN_BONEMEAL(ItemID.SHAIKAHAN_BONEMEAL, "Shaikahan bonemeal", 1, 100,
		ExperienceItem.SHAIKAHAN_BONEMEAL, Secondaries.BUCKET_OF_SLIME, null),
	BABYDRAGON_BONEMEAL(ItemID.BABY_DRAGON_BONEMEAL, "Babydragon bonemeal", 1, 120,
		ExperienceItem.BABYDRAGON_BONEMEAL, Secondaries.BUCKET_OF_SLIME, null),
	WYVERN_BONEMEAL(ItemID.WYVERN_BONEMEAL, "Wyvern bonemeal", 1, 288,
		ExperienceItem.WYVERN_BONEMEAL, Secondaries.BUCKET_OF_SLIME, null),
	DRAGON_BONEMEAL(ItemID.DRAGON_BONEMEAL, "Dragon bonemeal", 1, 288,
		ExperienceItem.DRAGON_BONEMEAL, Secondaries.BUCKET_OF_SLIME, null),
	FAYRG_BONEMEAL(ItemID.FAYRG_BONEMEAL, "Fayrg bonemeal", 1, 336,
		ExperienceItem.FAYRG_BONEMEAL, Secondaries.BUCKET_OF_SLIME, null),
	LAVA_DRAGON_BONEMEAL(ItemID.LAVA_DRAGON_BONEMEAL, "Lava dragon bonemeal", 1, 340,
		ExperienceItem.LAVA_DRAGON_BONEMEAL, Secondaries.BUCKET_OF_SLIME, null),
	RAURG_BONEMEAL(ItemID.RAURG_BONEMEAL, "Raurg bonemeal", 1, 384,
		ExperienceItem.RAURG_BONEMEAL, Secondaries.BUCKET_OF_SLIME, null),
	DAGANNOTH_BONEMEAL(ItemID.DAGANNOTHKING_BONEMEAL, "Dagannoth bonemeal", 1, 500,
		ExperienceItem.DAGANNOTH_BONEMEAL, Secondaries.BUCKET_OF_SLIME, null),
	OURG_BONEMEAL(ItemID.OURG_BONEMEAL, "Ourg bonemeal", 1, 560,
		ExperienceItem.OURG_BONEMEAL, Secondaries.BUCKET_OF_SLIME, null),
	SUPERIOR_DRAGON_BONEMEAL(ItemID.SUPERIOR_DRAGON_BONEMEAL, "Superior dragon bonemeal", 70, 600,
		ExperienceItem.SUPERIOR_DRAGON_BONEMEAL, Secondaries.BUCKET_OF_SLIME, null),
	WYRM_BONEMEAL(ItemID.WYRM_BONEMEAL, "Wyrm bonemeal", 1, 200,
		ExperienceItem.WYRM_BONEMEAL, Secondaries.BUCKET_OF_SLIME, null),
	DRAKE_BONEMEAL(ItemID.DRAKE_BONEMEAL, "Drake bonemeal", 1, 320,
		ExperienceItem.DRAKE_BONEMEAL, Secondaries.BUCKET_OF_SLIME, null),
	HYDRA_BONEMEAL(ItemID.HYDRA_BONEMEAL, "Hydra bonemeal", 1, 440,
		ExperienceItem.HYDRA_BONEMEAL, Secondaries.BUCKET_OF_SLIME, null),
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
		ExperienceItem.ENSOULED_GOBLIN_HEAD, Secondaries.BASIC_REANIMATION, null),
	ENSOULED_MONKEY_HEAD(ItemID.ENSOULED_MONKEY_HEAD_13451, "Ensouled monkey head", 1, 182.0,
		ExperienceItem.ENSOULED_MONKEY_HEAD, Secondaries.BASIC_REANIMATION, null),
	ENSOULED_IMP_HEAD(ItemID.ENSOULED_IMP_HEAD_13454, "Ensouled imp head", 1, 286.0,
		ExperienceItem.ENSOULED_IMP_HEAD, Secondaries.BASIC_REANIMATION, null),
	ENSOULED_MINOTAUR_HEAD(ItemID.ENSOULED_MINOTAUR_HEAD_13457, "Ensouled minotaur head", 1, 364.0,
		ExperienceItem.ENSOULED_MINOTAUR_HEAD, Secondaries.BASIC_REANIMATION, null),
	ENSOULED_SCORPION_HEAD(ItemID.ENSOULED_SCORPION_HEAD_13460, "Ensouled scorpion head", 1, 454.0,
		ExperienceItem.ENSOULED_SCORPION_HEAD, Secondaries.BASIC_REANIMATION, null),
	ENSOULED_BEAR_HEAD(ItemID.ENSOULED_BEAR_HEAD_13463, "Ensouled bear head", 1, 480.0,
		ExperienceItem.ENSOULED_BEAR_HEAD, Secondaries.BASIC_REANIMATION, null),
	ENSOULED_UNICORN_HEAD(ItemID.ENSOULED_UNICORN_HEAD_13466, "Ensouled unicorn head", 1, 494.0,
		ExperienceItem.ENSOULED_UNICORN_HEAD, Secondaries.BASIC_REANIMATION, null),
	ENSOULED_DOG_HEAD(ItemID.ENSOULED_DOG_HEAD_13469, "Ensouled dog head", 1, 520.0,
		ExperienceItem.ENSOULED_DOG_HEAD, Secondaries.ADEPT_REANIMATION, null),
	ENSOULED_CHAOS_DRUID_HEAD(ItemID.ENSOULED_CHAOS_DRUID_HEAD_13472, "Ensouled druid head", 1, 584.0,
		ExperienceItem.ENSOULED_CHAOS_DRUID_HEAD, Secondaries.ADEPT_REANIMATION, null),
	ENSOULED_GIANT_HEAD(ItemID.ENSOULED_GIANT_HEAD_13475, "Ensouled giant head", 1, 650.0,
		ExperienceItem.ENSOULED_GIANT_HEAD, Secondaries.ADEPT_REANIMATION, null),
	ENSOULED_OGRE_HEAD(ItemID.ENSOULED_OGRE_HEAD_13478, "Ensouled ogre head", 1, 716.0,
		ExperienceItem.ENSOULED_OGRE_HEAD, Secondaries.ADEPT_REANIMATION, null),
	ENSOULED_ELF_HEAD(ItemID.ENSOULED_ELF_HEAD_13481, "Ensouled elf head", 1, 754.0,
		ExperienceItem.ENSOULED_ELF_HEAD, Secondaries.ADEPT_REANIMATION, null),
	ENSOULED_TROLL_HEAD(ItemID.ENSOULED_TROLL_HEAD_13484, "Ensouled troll head", 1, 780.0,
		ExperienceItem.ENSOULED_TROLL_HEAD, Secondaries.ADEPT_REANIMATION, null),
	ENSOULED_HORROR_HEAD(ItemID.ENSOULED_HORROR_HEAD_13487, "Ensouled horror head", 1, 832.0,
		ExperienceItem.ENSOULED_HORROR_HEAD, Secondaries.ADEPT_REANIMATION, null),
	ENSOULED_KALPHITE_HEAD(ItemID.ENSOULED_KALPHITE_HEAD_13490, "Ensouled kalphite head", 1, 884.0,
		ExperienceItem.ENSOULED_KALPHITE_HEAD, Secondaries.EXPERT_REANIMATION, null),
	ENSOULED_DAGANNOTH_HEAD(ItemID.ENSOULED_DAGANNOTH_HEAD_13493, "Ensouled dagannoth head", 1, 936.0,
		ExperienceItem.ENSOULED_DAGANNOTH_HEAD, Secondaries.EXPERT_REANIMATION, null),
	ENSOULED_BLOODVELD_HEAD(ItemID.ENSOULED_BLOODVELD_HEAD_13496, "Ensouled bloodveld head", 1, 1040.0,
		ExperienceItem.ENSOULED_BLOODVELD_HEAD, Secondaries.EXPERT_REANIMATION, null),
	ENSOULED_TZHAAR_HEAD(ItemID.ENSOULED_TZHAAR_HEAD_13499, "Ensouled tzhaar head", 1, 1104.0,
		ExperienceItem.ENSOULED_TZHAAR_HEAD, Secondaries.EXPERT_REANIMATION, null),
	ENSOULED_DEMON_HEAD(ItemID.ENSOULED_DEMON_HEAD_13502, "Ensouled demon head", 1, 1170.0,
		ExperienceItem.ENSOULED_DEMON_HEAD, Secondaries.EXPERT_REANIMATION, null),
	ENSOULED_HELLHOUND_HEAD(ItemID.ENSOULED_HELLHOUND_HEAD_26997, "Ensouled hellhound head", 1, 1200,
		ExperienceItem.ENSOULED_HELLHOUND_HEAD, Secondaries.EXPERT_REANIMATION, null),
	ENSOULED_AVIANSIE_HEAD(ItemID.ENSOULED_AVIANSIE_HEAD_13505, "Ensouled aviansie head", 1, 1234.0,
		ExperienceItem.ENSOULED_AVIANSIE_HEAD, Secondaries.MASTER_REANIMATION, null),
	ENSOULED_ABYSSAL_HEAD(ItemID.ENSOULED_ABYSSAL_HEAD_13508, "Ensouled abyssal head", 1, 1300.0,
		ExperienceItem.ENSOULED_ABYSSAL_HEAD, Secondaries.MASTER_REANIMATION, null),
	ENSOULED_DRAGON_HEAD(ItemID.ENSOULED_DRAGON_HEAD_13511, "Ensouled dragon head", 1, 1560.0,
		ExperienceItem.ENSOULED_DRAGON_HEAD, Secondaries.MASTER_REANIMATION, null),
	// Fossils
	SMALL_LIMBS(ItemID.SMALL_FOSSILISED_LIMBS, "Small limbs", 1, 0,
		ExperienceItem.SMALL_LIMBS, null, new ItemStack(ItemID.UNIDENTIFIED_SMALL_FOSSIL, 1)),
	SMALL_SPINE(ItemID.SMALL_FOSSILISED_SPINE, "Small spine", 1, 0,
		ExperienceItem.SMALL_SPINE, null, new ItemStack(ItemID.UNIDENTIFIED_SMALL_FOSSIL, 1)),
	SMALL_RIBS(ItemID.SMALL_FOSSILISED_RIBS, "Small ribs", 1, 0,
		ExperienceItem.SMALL_RIBS, null, new ItemStack(ItemID.UNIDENTIFIED_SMALL_FOSSIL, 1)),
	SMALL_PELVIS(ItemID.SMALL_FOSSILISED_PELVIS, "Small pelvis", 1, 0,
		ExperienceItem.SMALL_PELVIS, null, new ItemStack(ItemID.UNIDENTIFIED_SMALL_FOSSIL, 1)),
	SMALL_SKULL(ItemID.SMALL_FOSSILISED_SKULL, "Small skull", 1, 0,
		ExperienceItem.SMALL_SKULL, null, new ItemStack(ItemID.UNIDENTIFIED_SMALL_FOSSIL, 1)),
	SMALL_FOSSIL(ItemID.UNIDENTIFIED_SMALL_FOSSIL, "Small fossil", 1, 500,
		ExperienceItem.SMALL_FOSSIL, null, null),
	MEDIUM_LIMBS(ItemID.MEDIUM_FOSSILISED_LIMBS, "Medium limbs", 1, 0,
		ExperienceItem.MEDIUM_LIMBS, null, new ItemStack(ItemID.UNIDENTIFIED_MEDIUM_FOSSIL, 1)),
	MEDIUM_SPINE(ItemID.MEDIUM_FOSSILISED_SPINE, "Medium spine", 1, 0,
		ExperienceItem.MEDIUM_SPINE, null, new ItemStack(ItemID.UNIDENTIFIED_MEDIUM_FOSSIL, 1)),
	MEDIUM_RIBS(ItemID.MEDIUM_FOSSILISED_RIBS, "Medium ribs", 1, 0,
		ExperienceItem.MEDIUM_RIBS, null, new ItemStack(ItemID.UNIDENTIFIED_MEDIUM_FOSSIL, 1)),
	MEDIUM_PELVIS(ItemID.MEDIUM_FOSSILISED_PELVIS, "Medium pelvis", 1, 0,
		ExperienceItem.MEDIUM_PELVIS, null, new ItemStack(ItemID.UNIDENTIFIED_MEDIUM_FOSSIL, 1)),
	MEDIUM_SKULL(ItemID.MEDIUM_FOSSILISED_SKULL, "Medium skull", 1, 0,
		ExperienceItem.MEDIUM_SKULL, null, new ItemStack(ItemID.UNIDENTIFIED_MEDIUM_FOSSIL, 1)),
	MEDIUM_FOSSIL(ItemID.UNIDENTIFIED_MEDIUM_FOSSIL, "Medium fossil", 1, 1000,
		ExperienceItem.MEDIUM_FOSSIL, null, null),
	LARGE_LIMBS(ItemID.LARGE_FOSSILISED_LIMBS, "Large limbs", 1, 0,
		ExperienceItem.LARGE_LIMBS, null, new ItemStack(ItemID.UNIDENTIFIED_LARGE_FOSSIL, 1)),
	LARGE_SPINE(ItemID.LARGE_FOSSILISED_SPINE, "Large spine", 1, 0,
		ExperienceItem.LARGE_SPINE, null, new ItemStack(ItemID.UNIDENTIFIED_LARGE_FOSSIL, 1)),
	LARGE_RIBS(ItemID.LARGE_FOSSILISED_RIBS, "Large ribs", 1, 0,
		ExperienceItem.LARGE_RIBS, null, new ItemStack(ItemID.UNIDENTIFIED_LARGE_FOSSIL, 1)),
	LARGE_PELVIS(ItemID.LARGE_FOSSILISED_PELVIS, "Large pelvis", 1, 0,
		ExperienceItem.LARGE_PELVIS, null, new ItemStack(ItemID.UNIDENTIFIED_LARGE_FOSSIL, 1)),
	LARGE_SKULL(ItemID.LARGE_FOSSILISED_SKULL, "Large skull", 1, 0,
		ExperienceItem.LARGE_SKULL, null, new ItemStack(ItemID.UNIDENTIFIED_LARGE_FOSSIL, 1)),
	LARGE_FOSSIL(ItemID.UNIDENTIFIED_LARGE_FOSSIL, "Large fossil", 1, 1500,
		ExperienceItem.LARGE_FOSSIL, null, null),
	RARE_LIMBS(ItemID.RARE_FOSSILISED_LIMBS, "Rare limbs", 1, 0,
		ExperienceItem.RARE_LIMBS, null, new ItemStack(ItemID.UNIDENTIFIED_RARE_FOSSIL, 1)),
	RARE_SPINE(ItemID.RARE_FOSSILISED_SPINE, "Rare spine", 1, 0,
		ExperienceItem.RARE_SPINE, null, new ItemStack(ItemID.UNIDENTIFIED_RARE_FOSSIL, 1)),
	RARE_RIBS(ItemID.RARE_FOSSILISED_RIBS, "Rare ribs", 1, 0,
		ExperienceItem.RARE_RIBS, null, new ItemStack(ItemID.UNIDENTIFIED_RARE_FOSSIL, 1)),
	RARE_PELVIS(ItemID.RARE_FOSSILISED_PELVIS, "Rare pelvis", 1, 0,
		ExperienceItem.RARE_PELVIS, null, new ItemStack(ItemID.UNIDENTIFIED_RARE_FOSSIL, 1)),
	RARE_SKULL(ItemID.RARE_FOSSILISED_SKULL, "Rare skull", 1, 0,
		ExperienceItem.RARE_SKULL, null, new ItemStack(ItemID.UNIDENTIFIED_RARE_FOSSIL, 1)),
	RARE_TUSK(ItemID.RARE_FOSSILISED_TUSK, "Rare tusk", 1, 0,
		ExperienceItem.RARE_TUSK, null, new ItemStack(ItemID.UNIDENTIFIED_RARE_FOSSIL, 1)),
	RARE_FOSSIL(ItemID.UNIDENTIFIED_RARE_FOSSIL, "Rare fossil", 1, 2500,
		ExperienceItem.RARE_FOSSIL, null, null),
	// Ashes
	FIENDISH_ASHES(ItemID.FIENDISH_ASHES, "Fiendish ashes", 1, 10,
		ExperienceItem.FIENDISH_ASHES, null, null),
	VILE_ASHES(ItemID.VILE_ASHES, "Vile ashes", 1, 25,
		ExperienceItem.VILE_ASHES, null, null),
	MALICIOUS_ASHES(ItemID.MALICIOUS_ASHES, "Malicious ashes", 1, 65,
		ExperienceItem.MALICIOUS_ASHES, null, null),
	ABYSSAL_ASHES(ItemID.ABYSSAL_ASHES, "Abyssal ashes", 1, 85,
		ExperienceItem.ABYSSAL_ASHES, null, null),
	INFERNAL_ASHES(ItemID.INFERNAL_ASHES, "Infernal ashes", 1, 110,
		ExperienceItem.INFERNAL_ASHES, null, null),
	// Bird Eggs
	BIRD_EGG_NEST_RED(ItemID.BIRD_NEST, "Bird's egg (Red)", 1, 0,
		ExperienceItem.BIRD_EGG_NEST_RED, null, new ItemStack(ItemID.BIRDS_EGG, 1)),
	BIRD_EGG_NEST_BLUE(ItemID.BIRD_NEST_5072, "Bird's egg (Blue)", 1, 0,
		ExperienceItem.BIRD_EGG_NEST_BLUE, null, new ItemStack(ItemID.BIRDS_EGG_5077, 1)),
	BIRD_EGG_NEST_GREEN(ItemID.BIRD_NEST_5071, "Bird's egg (Green)", 1, 0,
		ExperienceItem.BIRD_EGG_NEST_GREEN, null, new ItemStack(ItemID.BIRDS_EGG_5078, 1)),
	BIRD_EGG_RED(ItemID.BIRDS_EGG, "Offer bird's egg", 1, 100,
		ExperienceItem.BIRD_EGG_RED, null, null),
	BIRD_EGG_BLUE(ItemID.BIRDS_EGG_5077, "Offer bird's egg", 1, 100,
		ExperienceItem.BIRD_EGG_BLUE, null, null),
	BIRD_EGG_GREEN(ItemID.BIRDS_EGG_5078, "Offer bird's egg", 1, 100,
		ExperienceItem.BIRD_EGG_GREEN, null, null),
	/**
	 * Cooking
	 */
	COOK_BEEF(ItemID.COOKED_MEAT, "Cooked meat", 1, 30.0,
		ExperienceItem.RAW_BEEF, null, new ItemStack(ItemID.COOKED_MEAT, 1)),
	COOK_RAT_MEAT(ItemID.COOKED_MEAT, "Cooked meat", 1, 30.0,
		ExperienceItem.RAW_RAT_MEAT, null, new ItemStack(ItemID.COOKED_MEAT, 1)),
	COOK_BEAR_MEAT(ItemID.COOKED_MEAT, "Cooked meat", 1, 30.0,
		ExperienceItem.RAW_BEAR_MEAT, null, new ItemStack(ItemID.COOKED_MEAT, 1)),
	COOK_YAK_MEAT(ItemID.COOKED_MEAT, "Cooked meat", 1, 40.0,
		ExperienceItem.RAW_YAK_MEAT, null, new ItemStack(ItemID.COOKED_MEAT, 1)),
	COOK_UGTHANKI_MEAT(ItemID.UGTHANKI_MEAT, "Ugthanki meat", 1, 40.0,
		ExperienceItem.RAW_UGTHANKI_MEAT, null, new ItemStack(ItemID.UGTHANKI_MEAT, 1)),
	COOK_CHICKEN(ItemID.COOKED_CHICKEN, "Cooked chicken", 1, 30.0,
		ExperienceItem.RAW_CHICKEN, null, new ItemStack(ItemID.COOKED_CHICKEN, 1)),
	COOK_RABBIT(ItemID.COOKED_RABBIT, "Cooked rabbit", 1, 30.0,
		ExperienceItem.RAW_RABBIT, null, new ItemStack(ItemID.COOKED_RABBIT, 1)),
	COOK_SHRIMPS(ItemID.SHRIMPS, "Shrimps", 1, 30.0,
		ExperienceItem.RAW_SHRIMPS, null, new ItemStack(ItemID.SHRIMPS, 1)),
	COOK_SARDINE(ItemID.SARDINE, "Sardine", 1, 40.0,
		ExperienceItem.RAW_SARDINE, null, new ItemStack(ItemID.SARDINE, 1)),
	COOK_ANCHOVIES(ItemID.ANCHOVIES, "Anchovies", 1, 30.0,
		ExperienceItem.RAW_ANCHOVIES, null, new ItemStack(ItemID.ANCHOVIES, 1)),
	COOK_HERRING(ItemID.HERRING, "Herring", 5, 50.0,
		ExperienceItem.RAW_HERRING, null, new ItemStack(ItemID.HERRING, 1)),
	COOK_MACKEREL(ItemID.MACKEREL, "Mackerel", 10, 60.0,
		ExperienceItem.RAW_MACKEREL, null, new ItemStack(ItemID.MACKEREL, 1)),
	COOK_BIRD_MEAT(ItemID.ROAST_BIRD_MEAT, "Roast bird meat", 11, 62.5,
		ExperienceItem.RAW_BIRD_MEAT, null, new ItemStack(ItemID.ROAST_BIRD_MEAT, 1)),
	COOK_THIN_SNAIL(ItemID.THIN_SNAIL_MEAT, "Thin snail meat", 12, 70.0,
		ExperienceItem.THIN_SNAIL, null, new ItemStack(ItemID.THIN_SNAIL_MEAT, 1)),
	COOK_TROUT(ItemID.TROUT, "Trout", 15, 70.0,
		ExperienceItem.RAW_TROUT, null, new ItemStack(ItemID.TROUT, 1)),
	COOK_LEAN_SNAIL(ItemID.LEAN_SNAIL_MEAT, "Lean snail meat", 17, 80.0,
		ExperienceItem.LEAN_SNAIL, null, new ItemStack(ItemID.LEAN_SNAIL_MEAT, 1)),
	COOK_COD(ItemID.COD, "Cod", 18, 75.0,
		ExperienceItem.RAW_COD, null, new ItemStack(ItemID.COD, 1)),
	COOK_PIKE(ItemID.PIKE, "Pike", 20, 80.0,
		ExperienceItem.RAW_PIKE, null, new ItemStack(ItemID.PIKE, 1)),
	COOK_FAT_SNAIL(ItemID.FAT_SNAIL_MEAT, "Fat snail meat", 22, 95.0,
		ExperienceItem.FAT_SNAIL, null, new ItemStack(ItemID.FAT_SNAIL_MEAT, 1)),
	COOK_BEAST_MEAT(ItemID.ROAST_BEAST_MEAT, "Roast beast meat", 21, 62.0,
		ExperienceItem.RAW_BEAST_MEAT, null, new ItemStack(ItemID.ROAST_BEAST_MEAT, 1)),
	COOK_SALMON(ItemID.SALMON, "Salmon", 25, 90.0,
		ExperienceItem.RAW_SALMON, null, new ItemStack(ItemID.SALMON, 1)),
	COOK_TUNA(ItemID.TUNA, "Tuna", 30, 100.0,
		ExperienceItem.RAW_TUNA, null, new ItemStack(ItemID.TUNA, 1)),
	COOK_RAINBOW_FISH(ItemID.RAINBOW_FISH, "Rainbow fish", 35, 110.0,
		ExperienceItem.RAW_RAINBOW_FISH, null, new ItemStack(ItemID.RAINBOW_FISH, 1)),
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
	SWEETCORN(ItemID.COOKED_SWEETCORN, "Cooked sweetcorn", 28, 104,
		ExperienceItem.SWEETCORN, null, new ItemStack(ItemID.COOKED_SWEETCORN, 1)),
	STEW(ItemID.STEW, "Stew", 25, 117,
		ExperienceItem.UNCOOKED_STEW, null, new ItemStack(ItemID.STEW, 1)),
	CURRY(ItemID.CURRY, "Curry", 60, 280,
		ExperienceItem.UNCOOKED_CURRY, null, new ItemStack(ItemID.CURRY, 1)),
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
	// Regular Leather
	LEATHER(ItemID.LEATHER, "Tan Leather", 1, 0,
		ExperienceItem.COW_HIDE, Secondaries.COIN, new ItemStack(ItemID.LEATHER, 1)),
	LEATHER_GLOVES(ItemID.LEATHER_GLOVES, "Leather gloves", 1, 13.8,
		ExperienceItem.LEATHER, null, new ItemStack(ItemID.LEATHER_GLOVES, 1)),
	LEATHER_BOOTS(ItemID.LEATHER_BOOTS, "Leather boots", 7, 16.2,
		ExperienceItem.LEATHER, null, new ItemStack(ItemID.LEATHER_BOOTS, 1)),
	LEATHER_COWL(ItemID.LEATHER_COWL, "Leather cowl", 9, 18.5,
		ExperienceItem.LEATHER, null, new ItemStack(ItemID.LEATHER_COWL, 1)),
	LEATHER_VAMBRACES(ItemID.LEATHER_VAMBRACES, "Leather vambraces", 11, 22,
		ExperienceItem.LEATHER, null, new ItemStack(ItemID.LEATHER_VAMBRACES, 1)),
	LEATHER_BODY(ItemID.LEATHER_BODY, "Leather body", 14, 25,
		ExperienceItem.LEATHER, null, new ItemStack(ItemID.LEATHER_BODY, 1)),
	LEATHER_CHAPS(ItemID.LEATHER_CHAPS, "Leather chaps", 18, 27,
		ExperienceItem.LEATHER, null, new ItemStack(ItemID.LEATHER_CHAPS, 1)),
	COIF(ItemID.COIF, "Coif", 38, 37,
		ExperienceItem.LEATHER, null, new ItemStack(ItemID.COIF, 1)),
	// Hard Leather
	HARD_LEATHER(ItemID.HARD_LEATHER, "Tan Hard Leather", 1, 0,
		ExperienceItem.COW_HIDE, Secondaries.COIN_3, new ItemStack(ItemID.HARD_LEATHER, 1)),
	HARDLEATHER_BODY(ItemID.HARDLEATHER_BODY, "Hardleather body", 28, 35,
		ExperienceItem.HARD_LEATHER, null, new ItemStack(ItemID.HARDLEATHER_BODY, 1)),
	HARD_LEATHER_SHIELD(ItemID.HARD_LEATHER_SHIELD, "Hard leather shield", 41, 70,
		ExperienceItem.HARD_LEATHER, Secondaries.HARD_LEATHER_SHIELD, new ItemStack(ItemID.HARD_LEATHER_SHIELD, 1)),
	// Studded
	STUDDED_BODY(ItemID.STUDDED_BODY, "Studded chaps", 41, 40,
		ExperienceItem.STEEL_STUDS, Secondaries.LEATHER_BODY, new ItemStack(ItemID.STUDDED_BODY, 1)),
	STUDDED_CHAPS(ItemID.STUDDED_CHAPS, "Studded chaps", 44, 42,
		ExperienceItem.STEEL_STUDS, Secondaries.LEATHER_CHAPS, new ItemStack(ItemID.STUDDED_CHAPS, 1)),
	// D'hide/Dragon Leather
	GREEN_DRAGONHIDE(ItemID.GREEN_DRAGON_LEATHER, "Tan Green D'hide", 57, 0,
		ExperienceItem.GREEN_DRAGONHIDE, null, new ItemStack(ItemID.GREEN_DRAGON_LEATHER, 1)),
	BLUE_DRAGONHIDE(ItemID.BLUE_DRAGON_LEATHER, "Tan Blue D'hide", 66, 0,
		ExperienceItem.BLUE_DRAGONHIDE, null, new ItemStack(ItemID.BLUE_DRAGON_LEATHER, 1)),
	RED_DRAGONHIDE(ItemID.RED_DRAGON_LEATHER, "Tan Red D'hide", 73, 0,
		ExperienceItem.RED_DRAGONHIDE, null, new ItemStack(ItemID.RED_DRAGON_LEATHER, 1)),
	BLACK_DRAGONHIDE(ItemID.BLACK_DRAGON_LEATHER, "Tan Black D'hide", 79, 0,
		ExperienceItem.BLACK_DRAGONHIDE, null, new ItemStack(ItemID.BLACK_DRAGON_LEATHER, 1)),
	GREEN_DRAGON_LEATHER(ItemID.GREEN_DHIDE_VAMBRACES, "Green D'hide product", 57, 62.0,
		ExperienceItem.GREEN_DRAGON_LEATHER, null, null),
	BLUE_DRAGON_LEATHER(ItemID.BLUE_DHIDE_VAMBRACES, "Blue D'hide product", 66, 70.0,
		ExperienceItem.BLUE_DRAGON_LEATHER, null, null),
	RED_DRAGON_LEATHER(ItemID.RED_DHIDE_VAMBRACES, "Red D'hide product", 73, 78.0,
		ExperienceItem.RED_DRAGON_LEATHER, null, null),
	BLACK_DRAGON_LEATHER(ItemID.BLACK_DHIDE_VAMBRACES, "Black D'hide product", 79, 86.0,
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
	UNSTRUNG_SYMBOL(ItemID.UNSTRUNG_SYMBOL, "Unstrung Symbol", 16, 50,
		ExperienceItem.SILVER_BAR, null, new ItemStack(ItemID.TIARA, 1)),
	UNSTRUNG_EMBLEM(ItemID.UNSTRUNG_EMBLEM, "Unstrung Emblem", 17, 50,
		ExperienceItem.SILVER_BAR, null, new ItemStack(ItemID.TIARA, 1)),
	SILVER_SICKLE(ItemID.SILVER_SICKLE, "Silver Sickel", 18, 50,
		ExperienceItem.SILVER_BAR, null, new ItemStack(ItemID.TIARA, 1)),
	OPAL_BRACELET(ItemID.OPAL_BRACELET, "Opal bracelet", 22 , 45,
		ExperienceItem.OPAL, Secondaries.SILVER_BAR, new ItemStack(ItemID.OPAL_BRACELET, 1)),
	TIARA(ItemID.TIARA, "Tiara", 23, 52.5,
		ExperienceItem.SILVER_BAR, null, new ItemStack(ItemID.TIARA, 1)),
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
	// Gold Jewelery
	GOLD_RING(ItemID.GOLD_RING, "Gold ring", 5, 15,
		ExperienceItem.GOLD_BAR, null, new ItemStack(ItemID.GOLD_RING, 1)),
	GOLD_NECKLACE(ItemID.GOLD_NECKLACE, "Gold necklace", 6, 20,
		ExperienceItem.GOLD_BAR, null, new ItemStack(ItemID.GOLD_NECKLACE, 1)),
	GOLD_BRACELET(ItemID.GOLD_BRACELET, "Gold bracelet", 7, 25,
		ExperienceItem.GOLD_BAR, null, new ItemStack(ItemID.GOLD_BRACELET, 1)),
	GOLD_AMULET_U(ItemID.GOLD_AMULET_U, "Gold amulet (u)", 8, 30,
		ExperienceItem.GOLD_BAR, null, new ItemStack(ItemID.GOLD_AMULET_U, 1)),
	GOLD_TIARA(ItemID.GOLD_TIARA, "Gold tiara", 42, 35,
		ExperienceItem.GOLD_BAR, null, new ItemStack(ItemID.GOLD_TIARA, 1)),
	//Amethyst
	AMETHYST_BOLT_TIPS(ItemID.AMETHYST_BOLT_TIPS, "Amethyst bolt tips", 83, 60,
		ExperienceItem.AMETHYST, null, new ItemStack(ItemID.AMETHYST_BOLT_TIPS, 15)),
	AMETHYST_ARROWTIPS(ItemID.AMETHYST_ARROWTIPS, "Amethyst arrowtips", 85, 60,
		ExperienceItem.AMETHYST, null, new ItemStack(ItemID.AMETHYST_ARROWTIPS, 15)),
	AMETHYST_JAVELIN_HEADS(ItemID.AMETHYST_JAVELIN_HEADS, "Amethyst javelin heads", 87, 60,
		ExperienceItem.AMETHYST, null, new ItemStack(ItemID.AMETHYST_BOLT_TIPS, 5)),
	AMETHYST_DART_TIP(ItemID.AMETHYST_DART_TIP, "Amethyst dart tips", 89, 60,
		ExperienceItem.AMETHYST, null, new ItemStack(ItemID.AMETHYST_BOLT_TIPS, 8)),
	// RNG section
	// Soda Ash
	MOLTEN_GLASS(ItemID.MOLTEN_GLASS, "Furnace", 1, 20,
		ExperienceItem.SODA_ASH, null, new ItemStack(ItemID.MOLTEN_GLASS, 1)),
	MOLTEN_GLASS_SPELL(ItemID.MOLTEN_GLASS, "SGM [1.3x]", 1, 10, true,
		ExperienceItem.SODA_ASH, Secondaries.BUCKET_OF_SAND, new ItemStack(ItemID.MOLTEN_GLASS, 1.3)),
	// Seaweed
	SODA_ASH(ItemID.SODA_ASH, "Soda Ash", 1, 0,
		ExperienceItem.SEAWEED, null, new ItemStack(ItemID.SODA_ASH, 1)),
	S_MOLTEN_GLASS_SPELL(ItemID.MOLTEN_GLASS, "SGM [1.3x]", 1, 10, true,
		ExperienceItem.SEAWEED, Secondaries.BUCKET_OF_SAND, new ItemStack(ItemID.MOLTEN_GLASS, 1.3)),
	// Giant Seaweed
	G_SODA_ASH(ItemID.SODA_ASH, "Soda Ash", 1, 0,
		ExperienceItem.GIANT_SEAWEED, null, new ItemStack(ItemID.SODA_ASH, 6)),
	MOLTEN_GLASS_SPELL_18_PICKUP(ItemID.MOLTEN_GLASS, "SGM 18:3 Pickup [1.6x]", 1, 60,  true, // XP per seaweed
		ExperienceItem.GIANT_SEAWEED, Secondaries.BUCKET_OF_SAND_6, new ItemStack(ItemID.MOLTEN_GLASS, 9.6)),
	MOLTEN_GLASS_SPELL_18(ItemID.MOLTEN_GLASS, "SGM 18:3 [1.488x]", 1, 60,  true, // XP per seaweed
		ExperienceItem.GIANT_SEAWEED, Secondaries.BUCKET_OF_SAND_6, new ItemStack(ItemID.MOLTEN_GLASS, 8.928)),
	MOLTEN_GLASS_SPELL_12(ItemID.MOLTEN_GLASS, "SGM 12:2 [1.45x]", 1, 60,  true, // XP per seaweed
		ExperienceItem.GIANT_SEAWEED, Secondaries.BUCKET_OF_SAND_6, new ItemStack(ItemID.MOLTEN_GLASS, 8.7)),
	/**
	 * Smithing
	 */
	// Smelting ores (Furnace)
	IRON_ORE(ItemID.IRON_BAR, "Iron bar", 15, 12.5,
		ExperienceItem.IRON_ORE, null, new ItemStack(ItemID.IRON_BAR, 1)),
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
	// Blast Furnace
	BF_STEEL_ORE(ItemID.STEEL_BAR, "BF Steel Bar", 30, 17.5,
		ExperienceItem.IRON_ORE, Secondaries.COAL_ORE, new ItemStack(ItemID.STEEL_BAR, 1)),
	BF_MITHRIL_ORE(ItemID.MITHRIL_BAR, "BF Mithril Bar", 50, 30,
		ExperienceItem.MITHRIL_ORE, Secondaries.COAL_ORE_2, new ItemStack(ItemID.MITHRIL_BAR, 1)),
	BF_ADAMANTITE_ORE(ItemID.ADAMANTITE_BAR, "BF Adamantite bar", 70, 37.5,
		ExperienceItem.ADAMANTITE_ORE, Secondaries.COAL_ORE_3, new ItemStack(ItemID.ADAMANTITE_BAR, 1)),
	BF_RUNITE_ORE(ItemID.RUNITE_BAR, "BF Runite bar", 85, 50,
		ExperienceItem.RUNITE_ORE, Secondaries.COAL_ORE_4, new ItemStack(ItemID.RUNITE_BAR, 1)),
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
	DRAGONFRUIT_TREE_SEED(ItemID.DRAGONFRUIT_TREE_SEED, "Dragonfruit sapling", 81, 0,
		ExperienceItem.DRAGONFRUIT_TREE_SEED, null, new ItemStack(ItemID.DRAGONFRUIT_SAPLING, 1)),
	CELASTRUS_SEED(ItemID.CELASTRUS_SEED, "Celastrus sapling", 85, 0,
		ExperienceItem.CELASTRUS_SEED, null, new ItemStack(ItemID.CELASTRUS_SAPLING, 1)),
	REDWOOD_TREE_SEED(ItemID.REDWOOD_TREE_SEED, "Redwood sapling", 90, 0,
		ExperienceItem.REDWOOD_TREE_SEED, null, new ItemStack(ItemID.REDWOOD_SAPLING, 1)),
	CRYSTAL_ACORN(ItemID.CRYSTAL_ACORN, "Crystal sapling", 74, 0,
		ExperienceItem.CRYSTAL_ACORN, null, new ItemStack(ItemID.CRYSTAL_SAPLING, 1)),
	HESPORI_SEED(ItemID.HESPORI_SEED, "Hespori", 65, 12662,
		ExperienceItem.HESPORI_SEED, null, null),

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
	DRAGONFRUIT_SAPLING(ItemID.DRAGONFRUIT_SAPLING, "Dragonfruit tree", 81, 17825,
		ExperienceItem.DRAGONFRUIT_SAPLING, null, null),
	CELASTRUS_SAPLING(ItemID.CELASTRUS_SAPLING, "Celastrus tree", 85, 14404.5,
		ExperienceItem.CELASTRUS_SAPLING, null, null),
	REDWOOD_SAPLING(ItemID.REDWOOD_SAPLING, "Redwood tree", 90, 22680,
		ExperienceItem.REDWOOD_SAPLING, null, null),
	CRYSTAL_SAPLING(ItemID.CRYSTAL_SAPLING, "Crystal tree", 74, 13366,
		ExperienceItem.CRYSTAL_SAPLING, null, null),

	/**
	 * Fletching
	 */
	// General
	F_HEADLESS_ARROWS(ItemID.HEADLESS_ARROW, "Headless arrow", 1, 1,
		ExperienceItem.F_ARROW_SHAFT, Secondaries.FEATHER, new ItemStack(ItemID.HEADLESS_ARROW, 1)),
	// Logs
	F_ARROW_SHAFT(ItemID.ARROW_SHAFT, "Arrow shaft", 1, 5,
		ExperienceItem.F_LOGS, null, new ItemStack(ItemID.ARROW_SHAFT, 15)),
	F_SHORTBOW_U(ItemID.SHORTBOW_U, "Shortbow (u)", 5, 5,
		ExperienceItem.F_LOGS, null, new ItemStack(ItemID.SHORTBOW_U, 1)),
	F_WOODEN_STOCK(ItemID.WOODEN_STOCK, "Wooden stock", 9, 6,
		ExperienceItem.F_LOGS, null, new ItemStack(ItemID.WOODEN_STOCK, 1)),
	F_LONGBOW_U(ItemID.LONGBOW_U, "Longbow (u)", 10, 10,
		ExperienceItem.F_LOGS, null, new ItemStack(ItemID.LONGBOW_U, 1)),
	// Oak Logs
	F_OAK_ARROW_SHAFT(ItemID.ARROW_SHAFT, "Arrow shaft", 15, 10,
		ExperienceItem.F_OAK_LOGS, null, new ItemStack(ItemID.ARROW_SHAFT, 30)),
	F_OAK_SHORTBOW_U(ItemID.OAK_SHORTBOW_U, "Oak shortbow (u)", 25, 16.5,
		ExperienceItem.F_OAK_LOGS, null, new ItemStack(ItemID.OAK_SHORTBOW_U, 1)),
	F_OAK_STOCK(ItemID.OAK_STOCK, "Oak stock", 24, 16,
		ExperienceItem.F_OAK_LOGS, null, new ItemStack(ItemID.OAK_STOCK, 1)),
	F_OAK_LONGBOW_U(ItemID.OAK_LONGBOW_U, "Oak longbow (u)", 25, 25,
		ExperienceItem.F_OAK_LOGS, null, new ItemStack(ItemID.OAK_LONGBOW_U, 1)),
	F_OAK_SHIELD(ItemID.OAK_SHIELD, "Oak shield", 27, 50 * 0.5,
		ExperienceItem.F_OAK_LOGS, null, new ItemStack(ItemID.OAK_SHIELD, 0.5)),
	// Willow Logs
	F_WILLOW_ARROW_SHAFT(ItemID.ARROW_SHAFT, "Arrow shaft", 30, 15,
		ExperienceItem.F_WILLOW_LOGS, null, new ItemStack(ItemID.ARROW_SHAFT, 45)),
	F_WILLOW_SHORTBOW_U(ItemID.WILLOW_SHORTBOW_U, "Willow shortbow (u)", 35, 33.3,
		ExperienceItem.F_WILLOW_LOGS, null, new ItemStack(ItemID.WILLOW_SHORTBOW_U, 1)),
	F_WILLOW_STOCK(ItemID.WILLOW_STOCK, "Willow stock", 39, 22,
		ExperienceItem.F_WILLOW_LOGS, null, new ItemStack(ItemID.WILLOW_STOCK, 1)),
	F_WILLOW_LONGBOW_U(ItemID.WILLOW_LONGBOW_U, "Willow longbow (u)", 40, 41.5,
		ExperienceItem.F_WILLOW_LOGS, null, new ItemStack(ItemID.WILLOW_LONGBOW_U, 1)),
	F_WILLOW_SHIELD(ItemID.OAK_SHIELD, "Willow shield", 42, 83 * 0.5,
		ExperienceItem.F_WILLOW_LOGS, null, new ItemStack(ItemID.WILLOW_SHIELD, 0.5)),
	// Teak Logs
	F_TEAK_STOCK(ItemID.TEAK_STOCK, "Teak stock", 46, 27,
		ExperienceItem.F_TEAK_LOGS, null, new ItemStack(ItemID.TEAK_STOCK, 1)),
	// Maple Logs
	F_MAPLE_ARROW_SHAFT(ItemID.ARROW_SHAFT, "Arrow shaft", 45, 20,
		ExperienceItem.F_MAPLE_LOGS, null, new ItemStack(ItemID.ARROW_SHAFT, 60)),
	F_MAPLE_SHORTBOW_U(ItemID.MAPLE_SHORTBOW_U, "Maple shortbow (u)", 50, 50,
		ExperienceItem.F_MAPLE_LOGS, null, new ItemStack(ItemID.MAPLE_SHORTBOW_U, 1)),
	F_MAPLE_STOCK(ItemID.MAPLE_STOCK, "Maple stock", 54, 32,
		ExperienceItem.F_MAPLE_LOGS, null, new ItemStack(ItemID.MAPLE_STOCK, 1)),
	F_MAPLE_LONGBOW_U(ItemID.MAPLE_LONGBOW_U, "Maple longbow (u)", 55, 58.3,
		ExperienceItem.F_MAPLE_LOGS, null, new ItemStack(ItemID.MAPLE_LONGBOW_U, 1)),
	F_MAPLE_SHIELD(ItemID.MAPLE_SHIELD, "Maple shield", 57, 116.5 * 0.5,
		ExperienceItem.F_MAPLE_LOGS, null, new ItemStack(ItemID.MAPLE_SHIELD, 0.5)),
	// Mahogany Logs
	F_MAHOGANY_STOCK(ItemID.MAHOGANY_STOCK, "Mahogany stock", 61, 41,
		ExperienceItem.F_MAHOGANY_LOGS, null, new ItemStack(ItemID.MAHOGANY_STOCK, 1)),
	// Yew Logs
	F_YEW_ARROW_SHAFT(ItemID.ARROW_SHAFT, "Arrow shaft", 60, 25,
		ExperienceItem.F_YEW_LOGS, null, new ItemStack(ItemID.ARROW_SHAFT, 75)),
	F_YEW_SHORTBOW_U(ItemID.YEW_SHORTBOW_U, "Yew shortbow (u)", 65, 67.5,
		ExperienceItem.F_YEW_LOGS, null, new ItemStack(ItemID.YEW_SHORTBOW_U, 1)),
	F_YEW_STOCK(ItemID.YEW_STOCK, "Yew stock", 69, 50,
		ExperienceItem.F_YEW_LOGS, null, new ItemStack(ItemID.YEW_STOCK, 1)),
	F_YEW_LONGBOW_U(ItemID.YEW_LONGBOW_U, "Yew longbow (u)", 70, 75,
		ExperienceItem.F_YEW_LOGS, null, new ItemStack(ItemID.YEW_LONGBOW_U, 1)),
	F_YEW_SHIELD(ItemID.YEW_SHIELD, "Yew shield", 72, 150 * 0.5,
		ExperienceItem.F_YEW_LOGS, null, new ItemStack(ItemID.YEW_SHIELD, 0.5)),
	// Magic Logs
	F_MAGIC_ARROW_SHAFT(ItemID.ARROW_SHAFT, "Arrow shaft", 75, 30,
		ExperienceItem.F_MAGIC_LOGS, null, new ItemStack(ItemID.ARROW_SHAFT, 90)),
	F_MAGIC_STOCK(ItemID.MAGIC_STOCK, "Magic stock", 78, 70,
		ExperienceItem.F_MAGIC_LOGS, null, new ItemStack(ItemID.MAGIC_STOCK, 1)),
	F_MAGIC_SHORTBOW_U(ItemID.MAGIC_SHORTBOW_U, "Magic shortbow (u)", 80, 83.3,
		ExperienceItem.F_MAGIC_LOGS, null, new ItemStack(ItemID.MAGIC_SHORTBOW_U, 1)),
	F_MAGIC_LONGBOW_U(ItemID.MAGIC_LONGBOW_U, "Magic longbow (u)", 85, 91.5,
		ExperienceItem.F_MAGIC_LOGS, null, new ItemStack(ItemID.MAGIC_LONGBOW_U, 1)),
	F_MAGIC_SHIELD(ItemID.MAGIC_SHIELD, "Magic shield", 87, 183 * 0.5,
		ExperienceItem.F_MAGIC_LOGS, null, new ItemStack(ItemID.MAGIC_SHIELD, 0.5)),
	// Redwood Logs
	F_REDWOOD_ARROW_SHAFT(ItemID.ARROW_SHAFT, "Arrow shaft", 90, 35,
		ExperienceItem.F_REDWOOD_LOGS, null, new ItemStack(ItemID.ARROW_SHAFT, 105)),
	F_REDWOOD_SHIELD(ItemID.REDWOOD_SHIELD, "Redwood shield", 92, 216 * 0.5,
		ExperienceItem.F_REDWOOD_LOGS, null, new ItemStack(ItemID.REDWOOD_SHIELD, 0.5)),
	// Strung Bows
	F_SHORTBOW(ItemID.SHORTBOW, "Shortbow", 5, 5,
		ExperienceItem.F_SHORTBOW_U, Secondaries.BOW_STRING, new ItemStack(ItemID.SHORTBOW, 1)),
	F_LONGBOW(ItemID.LONGBOW, "Longbow", 10, 10,
		ExperienceItem.F_LONGBOW_U, Secondaries.BOW_STRING, new ItemStack(ItemID.LONGBOW, 1)),
	F_OAK_SHORTBOW(ItemID.OAK_SHORTBOW, "Oak shortbow", 20, 16.5,
		ExperienceItem.F_OAK_SHORTBOW_U, Secondaries.BOW_STRING, new ItemStack(ItemID.OAK_SHORTBOW, 1)),
	F_OAK_LONGBOW(ItemID.OAK_LONGBOW, "Oak longbow", 25, 25,
		ExperienceItem.F_OAK_LONGBOW_U, Secondaries.BOW_STRING, new ItemStack(ItemID.OAK_LONGBOW, 1)),
	F_WILLOW_SHORTBOW(ItemID.WILLOW_SHORTBOW, "Willow shortbow", 35, 33.2,
		ExperienceItem.F_WILLOW_SHORTBOW_U, Secondaries.BOW_STRING, new ItemStack(ItemID.WILLOW_SHORTBOW, 1)),
	F_WILLOW_LONGBOW(ItemID.WILLOW_LONGBOW, "Willow longbow", 40, 41.5,
		ExperienceItem.F_WILLOW_LONGBOW_U, Secondaries.BOW_STRING, new ItemStack(ItemID.WILLOW_LONGBOW, 1)),
	F_MAPLE_SHORTBOW(ItemID.MAPLE_SHORTBOW, "Maple shortbow", 50, 50,
		ExperienceItem.F_MAPLE_SHORTBOW_U, Secondaries.BOW_STRING, new ItemStack(ItemID.MAPLE_SHORTBOW, 1)),
	F_MAPLE_LONGBOW(ItemID.MAPLE_LONGBOW, "Maple longbow", 55, 58.2,
		ExperienceItem.F_MAPLE_LONGBOW_U, Secondaries.BOW_STRING, new ItemStack(ItemID.MAPLE_LONGBOW, 1)),
	F_YEW_SHORTBOW(ItemID.YEW_SHORTBOW, "Yew shortbow", 65, 67.5,
		ExperienceItem.F_YEW_SHORTBOW_U, Secondaries.BOW_STRING, new ItemStack(ItemID.YEW_SHORTBOW, 1)),
	F_YEW_LONGBOW(ItemID.YEW_LONGBOW, "Yew longbow", 70, 75,
		ExperienceItem.F_YEW_LONGBOW_U, Secondaries.BOW_STRING, new ItemStack(ItemID.YEW_LONGBOW, 1)),
	F_MAGIC_SHORTBOW(ItemID.MAGIC_SHORTBOW, "Magic shortbow", 80, 83.2,
		ExperienceItem.F_MAGIC_SHORTBOW_U, Secondaries.BOW_STRING, new ItemStack(ItemID.MAGIC_SHORTBOW, 1)),
	F_MAGIC_LONGBOW(ItemID.MAGIC_LONGBOW, "Magic longbow", 85, 91.5,
		ExperienceItem.F_MAGIC_LONGBOW_U, Secondaries.BOW_STRING, new ItemStack(ItemID.MAGIC_LONGBOW, 1)),
	// Darts
	F_BRONZE_DARTS(ItemID.BRONZE_DART, "Bronze dart", 10, 1.8,
		ExperienceItem.F_BRONZE_DART_TIP, Secondaries.FEATHER, new ItemStack(ItemID.BRONZE_DART, 1)),
	F_IRON_DARTS(ItemID.IRON_DART, "Iron dart", 22, 3.8,
		ExperienceItem.F_IRON_DART_TIP, Secondaries.FEATHER, new ItemStack(ItemID.IRON_DART, 1)),
	F_STEEL_DARTS(ItemID.STEEL_DART, "Steel dart", 37, 7.5,
		ExperienceItem.F_STEEL_DART_TIP, Secondaries.FEATHER, new ItemStack(ItemID.STEEL_DART, 1)),
	F_MITHRIL_DARTS(ItemID.MITHRIL_DART, "Mithril dart", 52, 11.2,
		ExperienceItem.F_MITHRIL_DART_TIP, Secondaries.FEATHER, new ItemStack(ItemID.MITHRIL_DART, 1)),
	F_ADAMANT_DARTS(ItemID.ADAMANT_DART, "Adamant dart", 67, 15,
		ExperienceItem.F_ADAMANT_DART_TIP, Secondaries.FEATHER, new ItemStack(ItemID.ADAMANT_DART, 1)),
	F_RUNE_DARTS(ItemID.RUNE_DART, "Rune dart", 81, 18.8,
		ExperienceItem.F_RUNE_DART_TIP, Secondaries.FEATHER, new ItemStack(ItemID.RUNE_DART, 1)),
	F_AMETHYST_DARTS(ItemID.AMETHYST_DART, "Amethyst dart", 90, 21,
		ExperienceItem.F_AMETHYST_DART_TIP, Secondaries.FEATHER, new ItemStack(ItemID.AMETHYST_DART, 1)),
	F_DRAGON_DARTS(ItemID.DRAGON_DART, "Dragon dart", 95, 25,
		ExperienceItem.F_DRAGON_DART_TIP, Secondaries.FEATHER, new ItemStack(ItemID.DRAGON_DART, 1)),
	// Arrows
	F_BRONZE_ARROW(ItemID.BRONZE_ARROW, "Bronze arrow", 1, 1,
		ExperienceItem.F_BRONZE_ARROWTIPS, Secondaries.HEADLESS_ARROW, new ItemStack(ItemID.BRONZE_ARROW, 1)),
	F_IRON_ARROW(ItemID.IRON_ARROW, "Iron arrow", 1, 2.5,
		ExperienceItem.F_IRON_ARROWTIPS, Secondaries.HEADLESS_ARROW, new ItemStack(ItemID.IRON_ARROW, 1)),
	F_STEEL_ARROW(ItemID.STEEL_ARROW, "Steel arrow", 30, 2.5,
		ExperienceItem.F_STEEL_ARROWTIPS, Secondaries.HEADLESS_ARROW, new ItemStack(ItemID.STEEL_ARROW, 1)),
	F_MITHRIL_ARROW(ItemID.MITHRIL_ARROW, "Mithril arrow", 45, 5,
		ExperienceItem.F_MITHRIL_ARROWTIPS, Secondaries.HEADLESS_ARROW, new ItemStack(ItemID.MITHRIL_ARROW, 1)),
	F_BROAD_ARROW(ItemID.BROAD_ARROWS, "Broad arrow", 52, 10,
		ExperienceItem.F_BROAD_ARROWHEADS, Secondaries.HEADLESS_ARROW, new ItemStack(ItemID.BROAD_ARROWS, 1)),
	F_ADAMANT_ARROW(ItemID.ADAMANT_ARROW, "Adamant arrow", 60, 10,
		ExperienceItem.F_ADAMANT_ARROWTIPS, Secondaries.HEADLESS_ARROW, new ItemStack(ItemID.ADAMANT_ARROW, 1)),
	F_RUNE_ARROW(ItemID.RUNE_ARROW, "Rune arrow", 75, 12.5,
		ExperienceItem.F_RUNE_ARROWTIPS, Secondaries.HEADLESS_ARROW, new ItemStack(ItemID.RUNE_ARROW, 1)),
	F_AMETHYST_ARROW(ItemID.AMETHYST_ARROW, "Amethyst arrow", 82, 13.5,
		ExperienceItem.F_AMETHYST_ARROWTIPS, Secondaries.HEADLESS_ARROW, new ItemStack(ItemID.AMETHYST_ARROW, 1)),
	F_DRAGON_ARROW(ItemID.DRAGON_ARROW, "Dragon arrow", 90, 15,
		ExperienceItem.F_DRAGON_ARROWTIPS, Secondaries.HEADLESS_ARROW, new ItemStack(ItemID.DRAGON_ARROW, 1)),
	// Bolts
	BRONZE_BOLTS(ItemID.BRONZE_BOLTS, "Bronze bolts", 9, 0.5,
		ExperienceItem.BRONZE_BOLTS, Secondaries.FEATHER, new ItemStack(ItemID.BRONZE_BOLTS, 1)),
	BLURITE_BOLTS(ItemID.BLURITE_BOLTS, "Blurite bolts", 24, 1,
		ExperienceItem.BLURITE_BOLTS, Secondaries.FEATHER, new ItemStack(ItemID.IRON_BOLTS, 1)),
	IRON_BOLTS(ItemID.IRON_BOLTS, "Iron bolts", 39, 1.5,
		ExperienceItem.IRON_BOLTS, Secondaries.FEATHER, new ItemStack(ItemID.IRON_BOLTS, 1)),
	SILVER_BOLTS(ItemID.SILVER_BOLTS, "Silver bolts", 43, 2.5,
		ExperienceItem.SILVER_BOLTS, Secondaries.FEATHER, new ItemStack(ItemID.SILVER_BOLTS, 1)),
	STEEL_BOLTS(ItemID.STEEL_BOLTS, "Steel bolts", 46, 3.5,
		ExperienceItem.STEEL_BOLTS, Secondaries.FEATHER, new ItemStack(ItemID.STEEL_BOLTS, 1)),
	MITHRIL_BOLTS(ItemID.MITHRIL_BOLTS, "Mithril bolts", 54, 5,
		ExperienceItem.MITHRIL_BOLTS, Secondaries.FEATHER, new ItemStack(ItemID.MITHRIL_BOLTS, 1)),
	BROAD_BOLTS(ItemID.BROAD_BOLTS, "Broad bolts", 55, 3,
		ExperienceItem.BROAD_BOLTS, Secondaries.FEATHER, new ItemStack(ItemID.BROAD_BOLTS, 1)),
	ADAMANT_BOLTS(ItemID.ADAMANT_BOLTS, "Adamant bolts", 61, 7,
		ExperienceItem.ADAMANT_BOLTS, Secondaries.FEATHER, new ItemStack(ItemID.ADAMANT_BOLTS, 1)),
	RUNE_BOLTS(ItemID.RUNITE_BOLTS, "Runite bolts", 69, 10,
		ExperienceItem.RUNE_BOLTS, Secondaries.FEATHER, new ItemStack(ItemID.RUNITE_BOLTS, 1)),
	DRAGON_BOLTS(ItemID.DRAGON_BOLTS, "Dragon bolts", 84, 12,
		ExperienceItem.DRAGON_BOLTS, Secondaries.FEATHER, new ItemStack(ItemID.DRAGON_BOLTS, 1)),
	// Unstrung Crossbows
	F_BRONZE_CROSSBOW_U(ItemID.WOODEN_STOCK, "Bronze crossbow (u)", 9, 12,
		ExperienceItem.F_WOODEN_STOCK, Secondaries.BRONZE_LIMBS, new ItemStack(ItemID.BRONZE_CROSSBOW_U, 1)),
	F_BLURITE_CROSSBOW_U(ItemID.OAK_STOCK, "Blurite crossbow (u)", 24, 32,
		ExperienceItem.F_OAK_STOCK, Secondaries.BLURITE_LIMBS, new ItemStack(ItemID.BLURITE_CROSSBOW_U, 1)),
	F_IRON_CROSSBOW_U(ItemID.WILLOW_STOCK, "Iron crossbow (u)", 39, 44,
		ExperienceItem.F_WILLOW_STOCK, Secondaries.IRON_LIMBS, new ItemStack(ItemID.IRON_CROSSBOW_U, 1)),
	F_STEEL_CROSSBOW_U(ItemID.TEAK_STOCK, "Steel crossbow (u)", 46, 54,
		ExperienceItem.F_TEAK_STOCK, Secondaries.STEEL_LIMBS, new ItemStack(ItemID.STEEL_CROSSBOW_U, 1)),
	F_MITHRIL_CROSSBOW_U(ItemID.MAPLE_STOCK, "Mithril crossbow (u)", 54, 64,
		ExperienceItem.F_MAPLE_STOCK, Secondaries.MITHRIL_LIMBS, new ItemStack(ItemID.MITHRIL_CROSSBOW_U, 1)),
	F_ADAMANTITE_CROSSBOW_U(ItemID.MAHOGANY_STOCK, "Adamantite crossbow (u)", 61, 82,
		ExperienceItem.F_MAHOGANY_STOCK, Secondaries.ADAMANTITE_LIMBS, new ItemStack(ItemID.ADAMANT_CROSSBOW_U, 1)),
	F_RUNITE_CROSSBOW_U(ItemID.YEW_STOCK, "Runite crossbow (u)", 69, 100,
		ExperienceItem.F_YEW_STOCK, Secondaries.RUNITE_LIMBS, new ItemStack(ItemID.RUNITE_CROSSBOW_U, 1)),
	F_DRAGON_CROSSBOW_U(ItemID.MAGIC_STOCK, "Dragon crossbow (u)", 78, 135,
		ExperienceItem.F_MAGIC_STOCK, Secondaries.DRAGON_LIMBS, new ItemStack(ItemID.DRAGON_CROSSBOW_U, 1)),
	// Crossbows
	F_BRONZE_CROSSBOW(ItemID.BRONZE_CROSSBOW_U, "Bronze crossbow", 9, 6,
		ExperienceItem.F_BRONZE_CROSSBOW_U, Secondaries.CROSSBOW_STRING, new ItemStack(ItemID.BRONZE_CROSSBOW, 1)),
	F_BLURITE_CROSSBOW(ItemID.BLURITE_CROSSBOW_U, "Blurite crossbow", 24, 16,
		ExperienceItem.F_BLURITE_CROSSBOW_U, Secondaries.CROSSBOW_STRING, new ItemStack(ItemID.BLURITE_CROSSBOW, 1)),
	F_IRON_CROSSBOW(ItemID.IRON_CROSSBOW_U, "Iron crossbow", 39, 22,
		ExperienceItem.F_IRON_CROSSBOW_U, Secondaries.CROSSBOW_STRING, new ItemStack(ItemID.IRON_CROSSBOW, 1)),
	F_STEEL_CROSSBOW(ItemID.STEEL_CROSSBOW_U, "Steel crossbow", 46, 27,
		ExperienceItem.F_STEEL_CROSSBOW_U, Secondaries.CROSSBOW_STRING, new ItemStack(ItemID.STEEL_CROSSBOW, 1)),
	F_MITHRIL_CROSSBOW(ItemID.MITHRIL_CROSSBOW_U, "Mithril crossbow", 54, 32,
		ExperienceItem.F_MITHRIL_CROSSBOW_U, Secondaries.CROSSBOW_STRING, new ItemStack(ItemID.MITHRIL_CROSSBOW, 1)),
	F_ADAMANTITE_CROSSBOW(ItemID.ADAMANT_CROSSBOW_U, "Adamant crossbow", 61, 41,
		ExperienceItem.F_ADAMANTITE_CROSSBOW_U, Secondaries.CROSSBOW_STRING, new ItemStack(ItemID.ADAMANT_CROSSBOW, 1)),
	F_RUNITE_CROSSBOW(ItemID.RUNITE_CROSSBOW_U, "Rune crossbow", 69, 50,
		ExperienceItem.F_RUNITE_CROSSBOW_U, Secondaries.CROSSBOW_STRING, new ItemStack(ItemID.RUNE_CROSSBOW, 1)),
	F_DRAGON_CROSSBOW(ItemID.DRAGON_CROSSBOW_U, "Dragon crossbow", 78, 70,
		ExperienceItem.F_DRAGON_CROSSBOW_U, Secondaries.CROSSBOW_STRING, new ItemStack(ItemID.DRAGON_CROSSBOW, 1)),
	// Other
	F_BATTLESTAFF(ItemID.BATTLESTAFF, "Battlestaff", 40, 80,
		ExperienceItem.F_CELASTRUS_BARK, null, new ItemStack(ItemID.BATTLESTAFF, 1)),
	/**
	 * Hunter
	 */
	BIRD_HOUSE(ItemID.BIRD_HOUSE, "Bird house", 5, 280, ExperienceItem.H_LOGS, null, null),
	PREBUILT_BIRD_HOUSE(ItemID.BIRD_HOUSE, "Bird house", 5, 280, ExperienceItem.BIRD_HOUSE, null, null),
	OAK_BIRD_HOUSE(ItemID.OAK_BIRD_HOUSE, "Oak bird house", 14, 420, ExperienceItem.H_OAK_LOGS, null, null),
	PREBUILT_OAK_BIRD_HOUSE(ItemID.OAK_BIRD_HOUSE, "Oak bird house", 14, 420, ExperienceItem.OAK_BIRD_HOUSE, null, null),
	WILLOW_BIRD_HOUSE(ItemID.WILLOW_BIRD_HOUSE, "Willow bird house", 24, 560, ExperienceItem.H_WILLOW_LOGS, null, null),
	PREBUILT_WILLOW_BIRD_HOUSE(ItemID.WILLOW_BIRD_HOUSE, "Willow bird house", 24, 560, ExperienceItem.WILLOW_BIRD_HOUSE, null, null),
	TEAK_BIRD_HOUSE(ItemID.TEAK_BIRD_HOUSE, "Teak bird house", 34, 700, ExperienceItem.H_TEAK_LOGS, null, null),
	PREBUILT_TEAK_BIRD_HOUSE(ItemID.TEAK_BIRD_HOUSE, "Teak bird house", 34, 700, ExperienceItem.TEAK_BIRD_HOUSE, null, null),
	MAPLE_BIRD_HOUSE(ItemID.MAPLE_BIRD_HOUSE, "Maple bird house", 44, 820, ExperienceItem.H_MAPLE_LOGS, null, null),
	PREBUILT_MAPLE_BIRD_HOUSE(ItemID.MAPLE_BIRD_HOUSE, "Maple bird house", 44, 820, ExperienceItem.MAPLE_BIRD_HOUSE, null, null),
	MAHOGANY_BIRD_HOUSE(ItemID.MAHOGANY_BIRD_HOUSE, "Mahogany bird house", 49, 960, ExperienceItem.H_MAHOGANY_LOGS, null, null),
	PREBUILT_MAHOGANY_BIRD_HOUSE(ItemID.MAHOGANY_BIRD_HOUSE, "Mahogany bird house", 49, 960, ExperienceItem.MAHOGANY_BIRD_HOUSE, null, null),
	YEW_BIRD_HOUSE(ItemID.YEW_BIRD_HOUSE, "Yew bird house", 59, 1020, ExperienceItem.H_YEW_LOGS, null, null),
	PREBUILT_YEW_BIRD_HOUSE(ItemID.YEW_BIRD_HOUSE, "Yew bird house", 59, 1020, ExperienceItem.YEW_BIRD_HOUSE, null, null),
	MAGIC_BIRD_HOUSE(ItemID.MAGIC_BIRD_HOUSE, "Magic bird house", 74, 1140, ExperienceItem.H_MAGIC_LOGS, null, null),
	PREBUILT_MAGIC_BIRD_HOUSE(ItemID.MAGIC_BIRD_HOUSE, "Magic bird house", 74, 1140,  ExperienceItem.MAGIC_BIRD_HOUSE, null, null),
	REDWOOD_BIRD_HOUSE(ItemID.REDWOOD_BIRD_HOUSE, "Redwood bird house", 89, 1200, ExperienceItem.H_REDWOOD_LOGS, null, null),
	PREBUILT_REDWOOD_BIRD_HOUSE(ItemID.REDWOOD_BIRD_HOUSE, "Redwood bird house", 89, 1200, ExperienceItem.REDWOOD_BIRD_HOUSE, null, null),
	/**
	 * Firemaking
	 */
	BURN_LOGS(ItemID.LOGS, "Burn logs", 1, 40, ExperienceItem.FM_LOGS, null, null),
	BURN_ACHEY_TREE(ItemID.ACHEY_TREE_LOGS, "Burn Achey Tree logs", 1, 40, ExperienceItem.FM_ACHEY_TREE_LOGS, null, null),
	BURN_OAK(ItemID.OAK_LOGS, "Burn Oak logs", 15, 60, ExperienceItem.FM_OAK_LOGS, null, null),
	BURN_WILLOW(ItemID.WILLOW_LOGS, "Burn Willow Logs", 30, 90, ExperienceItem.FM_WILLOW_LOGS, null, null),
	BURN_TEAK(ItemID.TEAK_LOGS, "Burn Teak logs", 35, 105, ExperienceItem.FM_TEAK_LOGS, null, null),
	BURN_ARCTIC_PINE(ItemID.ARCTIC_PINE_LOGS, "Burn Arctic Pine logs", 42, 125, ExperienceItem.FM_ARCTIC_PINE_LOGS, null, null),
	BURN_MAPLE(ItemID.MAPLE_LOGS, "Burn Maple logs", 45, 135, ExperienceItem.FM_MAPLE_LOGS, null, null),
	BURN_MAHOGANY(ItemID.MAHOGANY_LOGS, "Burn Mahogany logs", 50, 157.5, ExperienceItem.FM_MAHOGANY_LOGS, null, null),
	BURN_YEW(ItemID.YEW_LOGS, "Burn Yew logs", 60, 202.5, ExperienceItem.FM_YEW_LOGS, null, null),
	BURN_BLISTERWOOD(ItemID.BLISTERWOOD_LOGS, "Burn Blisterwood logs", 62, 96, ExperienceItem.FM_BLISTERWOOD_LOGS, null, null),
	BURN_MAGIC(ItemID.MAGIC_LOGS, "Burn Magic logs", 75, 303.8, ExperienceItem.FM_MAGIC_LOGS, null, null),
	BURN_REDWOOD(ItemID.REDWOOD_LOGS, "Burn Redwood logs", 90, 350, ExperienceItem.FM_REDWOOD_LOGS, null, null),
	/**
	 * Thieving
	 */
	WINTER_SQUIRK(ItemID.WINTER_SQIRKJUICE, "Make sq'irkjuice", 1, 0,
		ExperienceItem.WINTER_SQIRK, Secondaries.BEER_GLASS_5TH, new ItemStack(ItemID.WINTER_SQIRKJUICE, 0.2)),
	WINTER_SQUIRKJUICE(ItemID.WINTER_SQIRKJUICE, "Redeem sq'irkjuice", 1, 350,
		ExperienceItem.WINTER_SQIRKJUICE, null, null),
	SPRING_SQUIRK(ItemID.SPRING_SQIRKJUICE, "Make sq'irkjuice", 25, 0,
		ExperienceItem.SPRING_SQIRK, Secondaries.BEER_GLASS_4TH, new ItemStack(ItemID.SPRING_SQIRKJUICE, 0.25)),
	SPRING_SQUIRKJUICE(ItemID.SPRING_SQIRKJUICE, "Redeem sq'irkjuice", 25, 1350,
		ExperienceItem.SPRING_SQIRKJUICE, null, null),
	AUTUMN_SQUIRK(ItemID.AUTUMN_SQIRKJUICE, "Make sq'irkjuice", 45, 0,
		ExperienceItem.AUTUMN_SQIRK, Secondaries.BEER_GLASS_3RD, new ItemStack(ItemID.AUTUMN_SQIRKJUICE, 1.0 / 3)),
	AUTUMN_SQUIRKJUICE(ItemID.AUTUMN_SQIRKJUICE, "Redeem sq'irkjuice", 45, 2350,
		ExperienceItem.AUTUMN_SQIRKJUICE, null, null),
	SUMMER_SQUIRK(ItemID.SUMMER_SQIRKJUICE, "Make sq'irkjuice", 65, 0,
		ExperienceItem.SUMMER_SQIRK, Secondaries.BEER_GLASS_HALF, new ItemStack(ItemID.SUMMER_SQIRKJUICE, 0.5)),
	SUMMER_SQUIRKJUICE(ItemID.SUMMER_SQIRKJUICE, "Redeem sq'irkjuice", 65, 3000,
		ExperienceItem.SUMMER_SQIRKJUICE, null, null),
	;

	private final int icon;
	private final String name;
	private final int level;
	private final double xp;
	private final boolean rngActivity;
	private final ExperienceItem experienceItem;
	private final Skill skill;
	@Nullable
	private final Secondaries secondaries;
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
		this(icon, name, level, xp, false, experienceItem, secondaries, output);
	}

	Activity(
		final int icon,
		final String name,
		final int level,
		final double xp,
		final boolean rngActivity,
		final ExperienceItem experienceItem,
		@Nullable final Secondaries secondaries,
		@Nullable final ItemStack output)
	{
		this.icon = icon;
		this.name = name;
		this.skill = experienceItem.getSkill();
		this.level = level;
		this.xp = xp;
		this.rngActivity = rngActivity;
		this.experienceItem = experienceItem;
		this.secondaries = secondaries;
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

			// Attach names to all ItemStacks (secondaries)
			if (a.getSecondaries() != null)
			{
				for (final ItemStack stack : a.getSecondaries().getItems())
				{
					stack.updateItemInfo(m);
				}

				// If it has a custom handler those items need to be prepared as well
				final Secondaries.SecondaryHandler handler = a.getSecondaries().getCustomHandler();
				if (handler != null)
				{
					for (final ItemStack stack : handler.getInfoItems())
					{
						stack.updateItemInfo(m);
					}
				}
			}
		}
	}

	// Never used, should it be removed?
	public double getXpRate(final Modifier modifier)
	{
		return modifier.appliesTo(this) ? modifier.appliedXpRate(this) : xp;
	}

	public double getXpRate(final Collection<Modifier> modifiers)
	{
		// Apply all modifiers
		double tempXp = xp;
		float savePercentage = 0;
		float savePercentageMultiplicative = 1f;
		for (final Modifier modifier : modifiers)
		{
			if (!modifier.appliesTo(this))
			{
				continue;
			}

			if (modifier instanceof ConsumptionModifier)
			{
				ConsumptionModifier consumptionModifier = (ConsumptionModifier) modifier;
				if (consumptionModifier.isAdditive())
				{
					savePercentage += consumptionModifier.getSavePercentage();
				}
				else
				{
					// Multiplicative stacking is calculated using the chance to consume not the chance to save
					// For example a 50% chance to save is .5 and a 5% chance to save is .95
					savePercentageMultiplicative *= (1 - consumptionModifier.getSavePercentage());
				}
			}

			tempXp += (modifier.appliedXpRate(this) - xp);
		}

		// Dividing the XP by the chance of consuming the item will give you the average xp per item
		if (savePercentage != 0f || savePercentageMultiplicative != 1f)
		{
			float consumptionChance = (1 - savePercentage) * savePercentageMultiplicative;
			tempXp = tempXp / consumptionChance;
		}

		// Round to two decimal places
		return BigDecimal.valueOf(tempXp).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}

	private static boolean isOneNull(final Object a, final Object b)
	{
		return (a == null && b != null) || (a != null && b == null);
	}

	public boolean shouldUpdateLinked(final Activity old)
	{
		if (old.getLinkedItem() != linkedItem)
		{
			return true;
		}

		final ItemStack oldOutput = old.getOutput();
		// If both are null and the item hasn't change it shouldn't be updated
		if (oldOutput == null && output == null)
		{
			return false;
		}

		// If one was null an update should happen
		if (isOneNull(oldOutput, output))
		{
			return true;
		}

		return oldOutput.getQty() != output.getQty() || oldOutput.getId() != output.getId();
	}
}
