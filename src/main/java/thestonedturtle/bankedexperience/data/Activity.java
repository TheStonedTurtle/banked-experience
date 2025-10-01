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
import com.google.common.math.DoubleMath;

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
import net.runelite.api.gameval.ItemID;
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
	GUAM_POTION_UNF(ItemID.GUAMVIAL, "Unfinished Potion", 1, 0, ExperienceItem.GUAM_LEAF, Secondaries.UNFINISHED_POTION, new ItemStack(ItemID.GUAMVIAL, 1)),
	GUAM_TAR(ItemID.SALAMANDER_TAR_GREEN, "Guam tar", 19, 30, ExperienceItem.GUAM_LEAF, Secondaries.SWAMP_TAR, new ItemStack(ItemID.SALAMANDER_TAR_GREEN, 15)),
	ATTACK_POTION(ItemID._3DOSE1ATTACK, "Attack potion", 3, 25, ExperienceItem.GUAM_POTION_UNF, Secondaries.ATTACK_POTION, new ItemStack(ItemID._3DOSE1ATTACK, 1)),
	// Marrentil
	MARRENTILL_POTION_UNF(ItemID.MARRENTILLVIAL, "Unfinished potion", 1, 0, ExperienceItem.MARRENTILL, Secondaries.UNFINISHED_POTION, new ItemStack(ItemID.MARRENTILLVIAL, 1)),
	MARRENTILL_TAR(ItemID.SALAMANDER_TAR_ORANGE, "Marrentill tar", 31, 42.5, ExperienceItem.MARRENTILL, Secondaries.SWAMP_TAR, new ItemStack(ItemID.SALAMANDER_TAR_ORANGE, 15)),
	ANTIPOISON(ItemID._3DOSEANTIPOISON, "Antipoison", 5, 37.5, ExperienceItem.MARRENTILL_POTION_UNF, Secondaries.ANTIPOISON, new ItemStack(ItemID._3DOSEANTIPOISON, 1)),
	// Tarromin
	TARROMIN_POTION_UNF(ItemID.TARROMINVIAL, "Unfinished potion", 1, 0, ExperienceItem.TARROMIN, Secondaries.UNFINISHED_POTION, new ItemStack(ItemID.TARROMINVIAL, 1)),
	TARROMIN_TAR(ItemID.SALAMANDER_TAR_RED, "Tarromin tar", 39, 55, ExperienceItem.TARROMIN, Secondaries.SWAMP_TAR, new ItemStack(ItemID.SALAMANDER_TAR_RED, 15)),
	STRENGTH_POTION(ItemID._3DOSE1STRENGTH, "Strength potion", 12, 50, ExperienceItem.TARROMIN_POTION_UNF, Secondaries.STRENGTH_POTION, new ItemStack(ItemID._3DOSE1STRENGTH, 1)),
	SERUM_207(ItemID.MORT_SERUM3, "Serum 207", 15, 50, ExperienceItem.TARROMIN_POTION_UNF, Secondaries.SERUM_207, new ItemStack(ItemID.MORT_SERUM3, 1)),
	// Harralander
	HARRALANDER_POTION_UNF(ItemID.HARRALANDERVIAL, "Unfinished potion", 1, 0, ExperienceItem.HARRALANDER, Secondaries.UNFINISHED_POTION, new ItemStack(ItemID.HARRALANDERVIAL, 1)),
	HARRALANDER_TAR(ItemID.SALAMANDER_TAR_BLACK, "Harralander tar", 44, 72.5, ExperienceItem.HARRALANDER, Secondaries.SWAMP_TAR, new ItemStack(ItemID.SALAMANDER_TAR_BLACK, 15)),
	COMPOST_POTION(ItemID.SUPERCOMPOST_POTION_3, "Compost potion", 21, 60, ExperienceItem.HARRALANDER_POTION_UNF, Secondaries.COMPOST_POTION, new ItemStack(ItemID.SUPERCOMPOST_POTION_3, 1)),
	RESTORE_POTION(ItemID._3DOSESTATRESTORE, "Restore potion", 22, 62.5, ExperienceItem.HARRALANDER_POTION_UNF, Secondaries.RESTORE_POTION, new ItemStack(ItemID._3DOSESTATRESTORE, 1)),
	ENERGY_POTION(ItemID._3DOSE1ENERGY, "Energy potion", 26, 67.5, ExperienceItem.HARRALANDER_POTION_UNF, Secondaries.ENERGY_POTION, new ItemStack(ItemID._3DOSE1ENERGY, 1)),
	COMBAT_POTION(ItemID._3DOSECOMBAT, "Combat potion", 36, 84, ExperienceItem.HARRALANDER_POTION_UNF, Secondaries.COMBAT_POTION, new ItemStack(ItemID._3DOSECOMBAT, 1)),
	GOADING_POTION(ItemID._3DOSEGOADING, "Goading potion", 54, 132, ExperienceItem.HARRALANDER_POTION_UNF, Secondaries.GOADING_POTION, new ItemStack(ItemID._3DOSEGOADING, 1)),
	// Ranarr Weed
	RANARR_POTION_UNF(ItemID.RANARRVIAL, "Unfinished potion", 30, 0, ExperienceItem.RANARR_WEED, Secondaries.UNFINISHED_POTION, new ItemStack(ItemID.RANARRVIAL, 1)),
	DEFENCE_POTION(ItemID._3DOSE1DEFENSE, "Defence potion", 30, 75, ExperienceItem.RANARR_POTION_UNF, Secondaries.DEFENCE_POTION, new ItemStack(ItemID._3DOSE1DEFENSE, 1)),
	PRAYER_POTION(ItemID._3DOSEPRAYERRESTORE, "Prayer potion", 38, 87.5, ExperienceItem.RANARR_POTION_UNF, Secondaries.PRAYER_POTION, new ItemStack(ItemID._3DOSEPRAYERRESTORE, 1)),
	// Toadflax
	TOADFLAX_POTION_UNF(ItemID.TOADFLAXVIAL, "Unfinished potion", 34, 0, ExperienceItem.TOADFLAX, Secondaries.UNFINISHED_POTION, new ItemStack(ItemID.TOADFLAXVIAL, 1)),
	AGILITY_POTION(ItemID._3DOSE1AGILITY, "Agility potion", 34, 80, ExperienceItem.TOADFLAX_POTION_UNF, Secondaries.AGILITY_POTION, new ItemStack(ItemID._3DOSE1AGILITY, 1)),
	ANTIDOTE_PLUS_UNF(ItemID.UNFINISHED_ANTIDOTE_, "Antidote+ (unf)", 68, 0, ExperienceItem.TOADFLAX, Secondaries.COCONUT_MILK, new ItemStack(ItemID.UNFINISHED_ANTIDOTE_, 1)),
	ANTIDOTE_PLUS(ItemID.ANTIDOTE_4, "Antidote+", 68, 155, ExperienceItem.ANTIDOTE_PLUS_POTION_UNF, Secondaries.ANTIDOTE_PLUS, new ItemStack(ItemID.ANTIDOTE_4, 1)),
	SARADOMIN_BREW(ItemID._3DOSEPOTIONOFSARADOMIN, "Saradomin brew", 81, 180, ExperienceItem.TOADFLAX_POTION_UNF, Secondaries.SARADOMIN_BREW, new ItemStack(ItemID._3DOSEPOTIONOFSARADOMIN, 1)),
	// Irit
	IRIT_POTION_UNF(ItemID.IRITVIAL, "Unfinished potion", 45, 0, ExperienceItem.IRIT_LEAF, Secondaries.UNFINISHED_POTION, new ItemStack(ItemID.IRITVIAL, 1)),
	SUPER_ATTACK(ItemID._3DOSE2ATTACK, "Super attack", 45, 100, ExperienceItem.IRIT_POTION_UNF, Secondaries.SUPER_ATTACK, new ItemStack(ItemID._3DOSE2ATTACK, 1)),
	SUPERANTIPOISON(ItemID._3DOSE2ANTIPOISON, "Superantipoison", 48, 106.3, ExperienceItem.IRIT_POTION_UNF, Secondaries.SUPERANTIPOISON, new ItemStack(ItemID._3DOSE2ANTIPOISON, 1)),
	ANTIDOTE_PLUS_PlUS_UNF(ItemID.UNFINISHED_ANTIDOTE__, "Antidote++ (unf)", 79, 0, ExperienceItem.IRIT_LEAF, Secondaries.COCONUT_MILK, new ItemStack(ItemID.UNFINISHED_ANTIDOTE__, 1)),
	ANTIDOTE_PLUS_PLUS(ItemID.ANTIDOTE__4, "Antidote++", 79, 177.5, ExperienceItem.ANTIDOTE_PLUS_PLUS_POTION_UNF, Secondaries.ANTIDOTE_PLUS_PLUS, new ItemStack(ItemID.ANTIDOTE__4, 1)),
	// Avantoe
	AVANTOE_POTION_UNF(ItemID.AVANTOEVIAL, "Unfinished potion", 50, 0, ExperienceItem.AVANTOE, Secondaries.UNFINISHED_POTION, new ItemStack(ItemID.AVANTOEVIAL, 1)),
	FISHING_POTION(ItemID._3DOSEFISHERSPOTION, "Fishing potion", 50, 112.5, ExperienceItem.AVANTOE_POTION_UNF, Secondaries.FISHING_POTION, new ItemStack(ItemID._3DOSEFISHERSPOTION, 1)),
	SUPER_ENERGY_POTION(ItemID.BR_ENERGY3, "Super energy potion", 52, 117.5, ExperienceItem.AVANTOE_POTION_UNF, Secondaries.SUPER_ENERGY_POTION, new ItemStack(ItemID.BR_ENERGY3, 1)),
	HUNTER_POTION(ItemID._3DOSEHUNTING, "Hunter potion", 53, 120, ExperienceItem.AVANTOE_POTION_UNF, Secondaries.HUNTER_POTION, new ItemStack(ItemID._3DOSEHUNTING, 1)),
	// Kwuarm
	KWUARM_POTION_UNF(ItemID.KWUARMVIAL, "Unfinished potion", 55, 0, ExperienceItem.KWUARM, Secondaries.UNFINISHED_POTION, new ItemStack(ItemID.KWUARMVIAL, 1)),
	SUPER_STRENGTH(ItemID._3DOSE2STRENGTH, "Super strength", 55, 125, ExperienceItem.KWUARM_POTION_UNF, Secondaries.SUPER_STRENGTH, new ItemStack(ItemID._3DOSE2STRENGTH, 1)),
	WEAPON_POISON(ItemID.WEAPON_POISON, "Weapon Poison", 60, 137.5, ExperienceItem.KWUARM_POTION_UNF, Secondaries.WEAPON_POISON, new ItemStack(ItemID.WEAPON_POISON, 1)),
	SNAPDRAGON_POTION_UNF(ItemID.SNAPDRAGONVIAL, "Unfinished potion", 63, 0, ExperienceItem.SNAPDRAGON, Secondaries.UNFINISHED_POTION, new ItemStack(ItemID.SNAPDRAGONVIAL, 1)),
	SUPER_RESTORE(ItemID._3DOSE2RESTORE, "Super restore", 63, 142.5, ExperienceItem.SNAPDRAGON_POTION_UNF, Secondaries.SUPER_RESTORE, new ItemStack(ItemID._3DOSE2RESTORE, 1)),
	SANFEW_SERUM(ItemID.SANFEW_SALVE_3_DOSE, "Sanfew serum", 65, 160, ExperienceItem.SNAPDRAGON_POTION_UNF, Secondaries.SANFEW_SERUM, new ItemStack(ItemID.SANFEW_SALVE_3_DOSE, 1)),
	// Cadantine
	CADANTINE_POTION_UNF(ItemID.CADANTINEVIAL, "Unfinished potion", 66, 0, ExperienceItem.CADANTINE, Secondaries.UNFINISHED_POTION, new ItemStack(ItemID.CADANTINEVIAL, 1)),
	CADANTINE_BLOOD_POTION_UNF(ItemID.CADANTINE_BLOODVIAL, "Unfinished blood potion", 80, 0, ExperienceItem.CADANTINE, Secondaries.VIAL_OF_BLOOD, new ItemStack(ItemID.CADANTINE_BLOODVIAL, 1)),
	SUPER_DEFENCE_POTION(ItemID._3DOSE2DEFENSE, "Super defence", 66, 150, ExperienceItem.CADANTINE_POTION_UNF, Secondaries.SUPER_DEFENCE_POTION, new ItemStack(ItemID._3DOSE2DEFENSE, 1)),
	BASTION_POTION(ItemID._3DOSEBASTION, "Bastion potion", 80, 155, ExperienceItem.CADANTINE_BLOOD_POTION_UNF, Secondaries.RANGING_POTION, new ItemStack(ItemID._3DOSEBASTION, 1)),
	BATTLEMAGE_POTION(ItemID._3DOSEBATTLEMAGE, "Battlemage potion", 80, 155, ExperienceItem.CADANTINE_BLOOD_POTION_UNF, Secondaries.MAGIC_POTION, new ItemStack(ItemID._3DOSEBATTLEMAGE, 1)),
	// Lantadyme
	LANTADYME_POTION_UNF(ItemID.LANTADYMEVIAL, "Unfinished potion", 69, 0, ExperienceItem.LANTADYME, Secondaries.UNFINISHED_POTION, new ItemStack(ItemID.LANTADYMEVIAL, 1)),
	ANTIFIRE_POTION(ItemID._3DOSE1ANTIDRAGON, "Anti-fire potion", 69, 157.5, ExperienceItem.LANTADYME_POTION_UNF, Secondaries.ANTIFIRE_POTION, new ItemStack(ItemID._3DOSE1ANTIDRAGON, 1)),
	MAGIC_POTION(ItemID._3DOSE1MAGIC, "Magic potion", 76, 172.5, ExperienceItem.LANTADYME_POTION_UNF, Secondaries.MAGIC_POTION, new ItemStack(ItemID._3DOSE1MAGIC, 1)),
	// Dwarf Weed
	DWARF_WEED_POTION_UNF(ItemID.DWARFWEEDVIAL, "Unfinished potion", 72, 0, ExperienceItem.DWARF_WEED, Secondaries.UNFINISHED_POTION, new ItemStack(ItemID.DWARFWEEDVIAL, 1)),
	RANGING_POTION(ItemID._3DOSERANGERSPOTION, "Ranging potion", 72, 162.5, ExperienceItem.DWARF_WEED_POTION_UNF, Secondaries.RANGING_POTION, new ItemStack(ItemID._3DOSERANGERSPOTION, 1)),
	ANCIENT_BREW(ItemID._3DOSEANCIENTBREW, "Ancient Brew", 85, 190, ExperienceItem.DWARF_WEED_POTION_UNF, Secondaries.ANCIENT_BREW, new ItemStack(ItemID._3DOSEANCIENTBREW, 1)),
	MENAPHITE_REMEDY(ItemID._3DOSESTATRENEWAL, "Menaphite remedy", 88, 200, ExperienceItem.DWARF_WEED_POTION_UNF, Secondaries.MENAPHITE_REMEDY, new ItemStack(ItemID._3DOSESTATRENEWAL, 1)),
	// Torstol
	TORSTOL_POTION_UNF(ItemID.TORSTOLVIAL, "Unfinished potion", 78, 0, ExperienceItem.TORSTOL, Secondaries.UNFINISHED_POTION, new ItemStack(ItemID.TORSTOLVIAL, 1)),
	SUPER_COMBAT_POTION(ItemID._4DOSE2COMBAT, "Super combat", 90, 150, ExperienceItem.TORSTOL, Secondaries.SUPER_COMBAT_POTION, new ItemStack(ItemID._4DOSE2COMBAT, 1)),
	ANTIVENOM_PLUS(ItemID.ANTIVENOM_3, "Anti-venom+", 94, 125, ExperienceItem.TORSTOL, Secondaries.ANTIVENOM_PLUS, new ItemStack(ItemID.ANTIVENOM_3, 1)),
	ZAMORAK_BREW(ItemID._3DOSEPOTIONOFZAMORAK, "Zamorak brew", 78, 175, ExperienceItem.TORSTOL_POTION_UNF, Secondaries.ZAMORAK_BREW, new ItemStack(ItemID._3DOSEPOTIONOFZAMORAK, 1)),
	SUPER_COMBAT_POTION_2(ItemID._4DOSE2COMBAT, "Super combat", 90, 150, ExperienceItem.TORSTOL_POTION_UNF, Secondaries.SUPER_COMBAT_POTION, new ItemStack(ItemID._4DOSE2COMBAT, 1)),
	EXTENDED_ANTIVENOM_PLUS(ItemID.EXTENDED_ANTIVENOM_1, "Extended Anti-venom+", 94, 20, ExperienceItem.ARAXYTE_VENOM_SACK, Secondaries.EXTENDED_ANTIVENOM_PLUS, new ItemStack(ItemID.EXTENDED_ANTIVENOM_1, 1)),
	// Huasca
	HUASCA_POTION_UNF(ItemID.HUASCAVIAL, "Unfinished potion", 58, 0, ExperienceItem.HUASCA, Secondaries.UNFINISHED_POTION, new ItemStack(ItemID.HUASCAVIAL, 1)),
	PRAYER_REGENERATION(ItemID._3DOSE1PRAYER_REGENERATION, "Prayer regeneration", 58, 132, ExperienceItem.HUASCA_POTION_UNF, Secondaries.PRAYER_REGENERATION_POTION, new ItemStack(ItemID._3DOSE1PRAYER_REGENERATION, 1)),
	// Cleaning Grimy Herbs
	CLEAN_GUAM(ItemID.GUAM_LEAF, "Clean guam", 3, 2.5, ExperienceItem.GRIMY_GUAM_LEAF, null, new ItemStack(ItemID.GUAM_LEAF, 1)),
	CLEAN_MARRENTILL(ItemID.MARENTILL, "Clean marrentill", 5, 3.8, ExperienceItem.GRIMY_MARRENTILL, null, new ItemStack(ItemID.MARENTILL, 1)),
	CLEAN_TARROMIN(ItemID.TARROMIN, "Clean tarromin", 11, 5, ExperienceItem.GRIMY_TARROMIN, null, new ItemStack(ItemID.TARROMIN, 1)),
	CLEAN_HARRALANDER(ItemID.HARRALANDER, "Clean harralander", 20, 6.3, ExperienceItem.GRIMY_HARRALANDER, null, new ItemStack(ItemID.HARRALANDER, 1)),
	CLEAN_RANARR_WEED(ItemID.RANARR_WEED, "Clean ranarr weed", 25, 7.5, ExperienceItem.GRIMY_RANARR_WEED, null, new ItemStack(ItemID.RANARR_WEED, 1)),
	CLEAN_TOADFLAX(ItemID.TOADFLAX, "Clean toadflax", 30, 8, ExperienceItem.GRIMY_TOADFLAX, null, new ItemStack(ItemID.TOADFLAX, 1)),
	CLEAN_IRIT_LEAF(ItemID.IRIT_LEAF, "Clean irit leaf", 40, 8.8, ExperienceItem.GRIMY_IRIT_LEAF, null, new ItemStack(ItemID.IRIT_LEAF, 1)),
	CLEAN_AVANTOE(ItemID.AVANTOE, "Clean avantoe", 48, 10, ExperienceItem.GRIMY_AVANTOE, null, new ItemStack(ItemID.AVANTOE, 1)),
	CLEAN_KWUARM(ItemID.KWUARM, "Clean kwuarm", 54, 11.3, ExperienceItem.GRIMY_KWUARM, null, new ItemStack(ItemID.KWUARM, 1)),
	CLEAN_SNAPDRAGON(ItemID.SNAPDRAGON, "Clean snapdragon", 59, 11.8, ExperienceItem.GRIMY_SNAPDRAGON, null, new ItemStack(ItemID.SNAPDRAGON, 1)),
	CLEAN_CADANTINE(ItemID.CADANTINE, "Clean cadantine", 65, 12.5, ExperienceItem.GRIMY_CADANTINE, null, new ItemStack(ItemID.CADANTINE, 1)),
	CLEAN_LANTADYME(ItemID.LANTADYME, "Clean lantadyme", 67, 13.1, ExperienceItem.GRIMY_LANTADYME, null, new ItemStack(ItemID.LANTADYME, 1)),
	CLEAN_DWARF_WEED(ItemID.DWARF_WEED, "Clean dwarf weed", 70, 13.8, ExperienceItem.GRIMY_DWARF_WEED, null, new ItemStack(ItemID.DWARF_WEED, 1)),
	CLEAN_TORSTOL(ItemID.TORSTOL, "Clean torstol", 75, 15, ExperienceItem.GRIMY_TORSTOL, null, new ItemStack(ItemID.TORSTOL, 1)),
	CLEAN_HUASCA(ItemID.HUASCA, "Clean huasca", 58, 11.8, ExperienceItem.GRIMY_HUASCA, null, new ItemStack(ItemID.HUASCA, 1)),
	// Cleaning Grimy Herbs but no XP (Nardah)
	NARDAH_CLEAN_GUAM(ItemID.GUAM_LEAF, "Nardah clean guam", 3, 0, ExperienceItem.GRIMY_GUAM_LEAF, null, new ItemStack(ItemID.GUAM_LEAF, 1)),
	NARDAH_CLEAN_MARRENTILL(ItemID.MARENTILL, "Nardah clean marrentill", 5, 0, ExperienceItem.GRIMY_MARRENTILL, null, new ItemStack(ItemID.MARENTILL, 1)),
	NARDAH_CLEAN_TARROMIN(ItemID.TARROMIN, "Nardah clean tarromin", 11, 0, ExperienceItem.GRIMY_TARROMIN, null, new ItemStack(ItemID.TARROMIN, 1)),
	NARDAH_CLEAN_HARRALANDER(ItemID.HARRALANDER, "Nardah clean harralander", 20, 0, ExperienceItem.GRIMY_HARRALANDER, null, new ItemStack(ItemID.HARRALANDER, 1)),
	NARDAH_CLEAN_RANARR_WEED(ItemID.RANARR_WEED, "Nardah clean ranarr weed", 25, 0, ExperienceItem.GRIMY_RANARR_WEED, null, new ItemStack(ItemID.RANARR_WEED, 1)),
	NARDAH_CLEAN_TOADFLAX(ItemID.TOADFLAX, "Nardah clean toadflax", 30, 0, ExperienceItem.GRIMY_TOADFLAX, null, new ItemStack(ItemID.TOADFLAX, 1)),
	NARDAH_CLEAN_IRIT_LEAF(ItemID.IRIT_LEAF, "Nardah clean irit leaf", 40, 0, ExperienceItem.GRIMY_IRIT_LEAF, null, new ItemStack(ItemID.IRIT_LEAF, 1)),
	NARDAH_CLEAN_AVANTOE(ItemID.AVANTOE, "Nardah clean avantoe", 48, 0, ExperienceItem.GRIMY_AVANTOE, null, new ItemStack(ItemID.AVANTOE, 1)),
	NARDAH_CLEAN_KWUARM(ItemID.KWUARM, "Nardah clean kwuarm", 54, 0, ExperienceItem.GRIMY_KWUARM, null, new ItemStack(ItemID.KWUARM, 1)),
	NARDAH_CLEAN_SNAPDRAGON(ItemID.SNAPDRAGON, "Nardah clean snapdragon", 59, 0, ExperienceItem.GRIMY_SNAPDRAGON, null, new ItemStack(ItemID.SNAPDRAGON, 1)),
	NARDAH_CLEAN_CADANTINE(ItemID.CADANTINE, "Nardah clean cadantine", 65, 0, ExperienceItem.GRIMY_CADANTINE, null, new ItemStack(ItemID.CADANTINE, 1)),
	NARDAH_CLEAN_LANTADYME(ItemID.LANTADYME, "Nardah clean lantadyme", 67, 0, ExperienceItem.GRIMY_LANTADYME, null, new ItemStack(ItemID.LANTADYME, 1)),
	NARDAH_CLEAN_DWARF_WEED(ItemID.DWARF_WEED, "Nardah clean dwarf weed", 70, 0, ExperienceItem.GRIMY_DWARF_WEED, null, new ItemStack(ItemID.DWARF_WEED, 1)),
	NARDAH_CLEAN_TORSTOL(ItemID.TORSTOL, "Nardah clean torstol", 75, 0, ExperienceItem.GRIMY_TORSTOL, null, new ItemStack(ItemID.TORSTOL, 1)),
	NARDAH_CLEAN_HUASCA(ItemID.HUASCA, "Nardah clean huasca", 58, 0, ExperienceItem.GRIMY_HUASCA, null, new ItemStack(ItemID.HUASCA, 1)),
	// Degrime Spell
	DEGRIME_GUAM(ItemID.GUAM_LEAF, "Degrime guam", 3, 1.2, ExperienceItem.GRIMY_GUAM_LEAF, Secondaries.DEGRIME, new ItemStack(ItemID.GUAM_LEAF, 1)),
	DEGRIME_MARRENTILL(ItemID.MARENTILL, "Degrime marrentill", 5, 1.9, ExperienceItem.GRIMY_MARRENTILL, Secondaries.DEGRIME, new ItemStack(ItemID.MARENTILL, 1)),
	DEGRIME_TARROMIN(ItemID.TARROMIN, "Degrime tarromin", 11, 2.5, ExperienceItem.GRIMY_TARROMIN, Secondaries.DEGRIME, new ItemStack(ItemID.TARROMIN, 1)),
	DEGRIME_HARRALANDER(ItemID.HARRALANDER, "Degrime harralander", 20, 3.1, ExperienceItem.GRIMY_HARRALANDER, Secondaries.DEGRIME, new ItemStack(ItemID.HARRALANDER, 1)),
	DEGRIME_RANARR_WEED(ItemID.RANARR_WEED, "Degrime ranarr weed", 25, 3.7, ExperienceItem.GRIMY_RANARR_WEED, Secondaries.DEGRIME, new ItemStack(ItemID.RANARR_WEED, 1)),
	DEGRIME_TOADFLAX(ItemID.TOADFLAX, "Degrime toadflax", 30, 4.0, ExperienceItem.GRIMY_TOADFLAX, Secondaries.DEGRIME, new ItemStack(ItemID.TOADFLAX, 1)),
	DEGRIME_IRIT_LEAF(ItemID.IRIT_LEAF, "Degrime irit leaf", 40, 4.4, ExperienceItem.GRIMY_IRIT_LEAF, Secondaries.DEGRIME, new ItemStack(ItemID.IRIT_LEAF, 1)),
	DEGRIME_AVANTOE(ItemID.AVANTOE, "Degrime avantoe", 48, 5.0, ExperienceItem.GRIMY_AVANTOE, Secondaries.DEGRIME, new ItemStack(ItemID.AVANTOE, 1)),
	DEGRIME_KWUARM(ItemID.KWUARM, "Degrime kwuarm", 54, 5.6, ExperienceItem.GRIMY_KWUARM, Secondaries.DEGRIME, new ItemStack(ItemID.KWUARM, 1)),
	DEGRIME_SNAPDRAGON(ItemID.SNAPDRAGON, "Degrime snapdragon", 59, 5.9, ExperienceItem.GRIMY_SNAPDRAGON, Secondaries.DEGRIME, new ItemStack(ItemID.SNAPDRAGON, 1)),
	DEGRIME_CADANTINE(ItemID.CADANTINE, "Degrime cadantine", 65, 6.2, ExperienceItem.GRIMY_CADANTINE, Secondaries.DEGRIME, new ItemStack(ItemID.CADANTINE, 1)),
	DEGRIME_LANTADYME(ItemID.LANTADYME, "Degrime lantadyme", 67, 6.5, ExperienceItem.GRIMY_LANTADYME, Secondaries.DEGRIME, new ItemStack(ItemID.LANTADYME, 1)),
	DEGRIME_DWARF_WEED(ItemID.DWARF_WEED, "Degrime dwarf weed", 70, 6.9, ExperienceItem.GRIMY_DWARF_WEED, Secondaries.DEGRIME, new ItemStack(ItemID.DWARF_WEED, 1)),
	DEGRIME_TORSTOL(ItemID.TORSTOL, "Degrime torstol", 75, 7.5, ExperienceItem.GRIMY_TORSTOL, Secondaries.DEGRIME, new ItemStack(ItemID.TORSTOL, 1)),
	DEGRIME_HUASCA(ItemID.HUASCA, "Degrime huasca", 58, 5.9, ExperienceItem.GRIMY_HUASCA, Secondaries.DEGRIME, new ItemStack(ItemID.HUASCA, 1)),
	// Refine Herbs to Paste
	REFINE_GUAM_LEAF(ItemID.MM_MOX_PASTE, "Mox Paste", 1, 6, ExperienceItem.GUAM_LEAF, null, new ItemStack(ItemID.MM_MOX_PASTE, 10)),
	REFINE_MARRENTILL(ItemID.MM_MOX_PASTE, "Mox Paste", 1, 6, ExperienceItem.MARRENTILL, null, new ItemStack(ItemID.MM_MOX_PASTE, 13)),
	REFINE_TARROMIN(ItemID.MM_MOX_PASTE, "Mox Paste", 1, 6, ExperienceItem.TARROMIN, null, new ItemStack(ItemID.MM_MOX_PASTE, 15)),
	REFINE_HARRALANDER(ItemID.MM_MOX_PASTE, "Mox Paste", 1, 6, ExperienceItem.HARRALANDER, null, new ItemStack(ItemID.MM_MOX_PASTE, 20)),
	REFINE_RANARR_WEED(ItemID.MM_LYE_PASTE, "Lye Paste", 1, 6, ExperienceItem.RANARR_WEED, null, new ItemStack(ItemID.MM_LYE_PASTE, 26)),
	REFINE_TOADFLAX(ItemID.MM_LYE_PASTE, "Lye Paste", 1, 6, ExperienceItem.TOADFLAX, null, new ItemStack(ItemID.MM_LYE_PASTE, 32)),
	REFINE_IRIT_LEAF(ItemID.MM_AGA_PASTE, "Aga Paste", 1, 6, ExperienceItem.IRIT_LEAF, null, new ItemStack(ItemID.MM_AGA_PASTE, 30)),
	REFINE_AVANTOE(ItemID.MM_LYE_PASTE, "Lye Paste", 1, 6, ExperienceItem.AVANTOE, null, new ItemStack(ItemID.MM_LYE_PASTE, 30)),
	REFINE_KWUARM(ItemID.MM_LYE_PASTE, "Lye Paste", 1, 6, ExperienceItem.KWUARM, null, new ItemStack(ItemID.MM_LYE_PASTE, 33)),
	REFINE_SNAPDRAGON(ItemID.MM_LYE_PASTE, "Lye Paste", 1, 6, ExperienceItem.SNAPDRAGON, null, new ItemStack(ItemID.MM_LYE_PASTE, 40)),
	REFINE_CADANTINE(ItemID.MM_AGA_PASTE, "Aga Paste", 1, 6, ExperienceItem.CADANTINE, null, new ItemStack(ItemID.MM_AGA_PASTE, 34)),
	REFINE_LANTADYME(ItemID.MM_AGA_PASTE, "Aga Paste", 1, 6, ExperienceItem.LANTADYME, null, new ItemStack(ItemID.MM_AGA_PASTE, 40)),
	REFINE_DWARF_WEED(ItemID.MM_AGA_PASTE, "Aga Paste", 1, 6, ExperienceItem.DWARF_WEED, null, new ItemStack(ItemID.MM_AGA_PASTE, 42)),
	REFINE_TORSTOL(ItemID.MM_AGA_PASTE, "Aga Paste", 1, 6, ExperienceItem.TORSTOL, null, new ItemStack(ItemID.MM_AGA_PASTE, 44)),
	REFINE_HUASCA(ItemID.MM_AGA_PASTE, "Aga Paste", 1, 6, ExperienceItem.HUASCA, null, new ItemStack(ItemID.MM_AGA_PASTE, 20)),
	// Refine Unfinished Potions to Paste
	REFINE_GUAM_POTION_UNF(ItemID.MM_MOX_PASTE, "Mox Paste", 1, 6, ExperienceItem.GUAM_POTION_UNF, null, new ItemStack(ItemID.MM_MOX_PASTE, 10)),
	REFINE_MARRENTILL_POTION_UNF(ItemID.MM_MOX_PASTE, "Mox Paste", 1, 6, ExperienceItem.MARRENTILL_POTION_UNF, null, new ItemStack(ItemID.MM_MOX_PASTE, 13)),
	REFINE_TARROMIN_POTION_UNF(ItemID.MM_MOX_PASTE, "Mox Paste", 1, 6, ExperienceItem.TARROMIN_POTION_UNF, null, new ItemStack(ItemID.MM_MOX_PASTE, 15)),
	REFINE_HARRALANDER_POTION_UNF(ItemID.MM_MOX_PASTE, "Mox Paste", 1, 6, ExperienceItem.HARRALANDER_POTION_UNF, null, new ItemStack(ItemID.MM_MOX_PASTE, 20)),
	REFINE_RANARR_POTION_UNF(ItemID.MM_LYE_PASTE, "Lye Paste", 1, 6, ExperienceItem.RANARR_POTION_UNF, null, new ItemStack(ItemID.MM_LYE_PASTE, 26)),
	REFINE_TOADFLAX_POTION_UNF(ItemID.MM_LYE_PASTE, "Lye Paste", 1, 6, ExperienceItem.TOADFLAX_POTION_UNF, null, new ItemStack(ItemID.MM_LYE_PASTE, 32)),
	REFINE_IRIT_POTION_UNF(ItemID.MM_AGA_PASTE, "Aga Paste", 1, 6, ExperienceItem.IRIT_POTION_UNF, null, new ItemStack(ItemID.MM_AGA_PASTE, 30)),
	REFINE_AVANTOE_POTION_UNF(ItemID.MM_LYE_PASTE, "Lye Paste", 1, 6, ExperienceItem.AVANTOE_POTION_UNF, null, new ItemStack(ItemID.MM_LYE_PASTE, 30)),
	REFINE_KWUARM_POTION_UNF(ItemID.MM_LYE_PASTE, "Lye Paste", 1, 6, ExperienceItem.KWUARM_POTION_UNF, null, new ItemStack(ItemID.MM_LYE_PASTE, 33)),
	REFINE_SNAPDRAGON_POTION_UNF(ItemID.MM_LYE_PASTE, "Lye Paste", 1, 6, ExperienceItem.SNAPDRAGON_POTION_UNF, null, new ItemStack(ItemID.MM_LYE_PASTE, 40)),
	REFINE_CADANTINE_POTION_UNF(ItemID.MM_AGA_PASTE, "Aga Paste", 1, 6, ExperienceItem.CADANTINE_POTION_UNF, null, new ItemStack(ItemID.MM_AGA_PASTE, 34)),
	REFINE_LANTADYME_POTION_UNF(ItemID.MM_AGA_PASTE, "Aga Paste", 1, 6, ExperienceItem.LANTADYME_POTION_UNF, null, new ItemStack(ItemID.MM_AGA_PASTE, 40)),
	REFINE_DWARF_WEED_POTION_UNF(ItemID.MM_AGA_PASTE, "Aga Paste", 1, 6, ExperienceItem.DWARF_WEED_POTION_UNF, null, new ItemStack(ItemID.MM_AGA_PASTE, 42)),
	REFINE_TORSTOL_POTION_UNF(ItemID.MM_AGA_PASTE, "Aga Paste", 1, 6, ExperienceItem.TORSTOL_POTION_UNF, null, new ItemStack(ItemID.MM_AGA_PASTE, 44)),
	REFINE_HUASCA_POTION_UNF(ItemID.MM_AGA_PASTE, "Aga Paste", 1, 6, ExperienceItem.HUASCA_POTION_UNF, null, new ItemStack(ItemID.MM_AGA_PASTE, 20)),
	// Mastering Mixology
	// XP rates are calculated assuming the lowest xp potions are made e.g. MMM, AAA, LLL
	// https://oldschool.runescape.wiki/w/Mastering_Mixology#Depositing
	MASTERING_MIXOLOGY_MOX_PASTE(ItemID.MM_POTION_MAL_UNFINISHED, "Mastering Mixology", 60, 5.5, true, ExperienceItem.MOX_PASTE, null, null),
	MASTERING_MIXOLOGY_AGA_PASTE(ItemID.MM_POTION_MAL_UNFINISHED, "Mastering Mixology", 60, 6.33, true, ExperienceItem.AGA_PASTE, null, null),
	MASTERING_MIXOLOGY_LYE_PASTE(ItemID.MM_POTION_MAL_UNFINISHED, "Mastering Mixology", 60, 6.33, true, ExperienceItem.LYE_PASTE, null, null),
	// Weapon Poison
	WEAPON_POISON_PLUS_UNF(ItemID.UNFINISHED_WEAPON_POISON_, "Weapon poison+ (unf)", 73, 0, ExperienceItem.CACTUS_SPINE, Secondaries.COCONUT_MILK, new ItemStack(ItemID.UNFINISHED_WEAPON_POISON_, 1)),
	WEAPON_POISON_PLUS(ItemID.WEAPON_POISON_, "Weapon poison(+)", 73, 190, ExperienceItem.WEAPON_POISON_PLUS_UNF, Secondaries.WEAPON_POISON_PLUS, new ItemStack(ItemID.WEAPON_POISON_, 1)),
	WEAPON_POISON_PLUS_PLUS_UNF(ItemID.UNFINISHED_WEAPON_POISON__, "Weapon poison++ (unf)", 82, 0, ExperienceItem.CAVE_NIGHTSHADE, Secondaries.COCONUT_MILK, new ItemStack(ItemID.UNFINISHED_WEAPON_POISON__, 1)),
	WEAPON_POISON_PLUS_PLUS_UNF2(ItemID.UNFINISHED_WEAPON_POISON__, "Weapon poison++ (unf)", 82, 0, ExperienceItem.NIGHTSHADE, Secondaries.COCONUT_MILK, new ItemStack(ItemID.UNFINISHED_WEAPON_POISON__, 1)),
	WEAPON_POISON_PLUS_PLUS(ItemID.WEAPON_POISON__, "Weapon poison(++)", 82, 190, ExperienceItem.WEAPON_POISON_PLUS_PLUS_UNF, Secondaries.WEAPON_POISON_PLUS_PLUS, new ItemStack(ItemID.WEAPON_POISON__, 1)),
	// Other
	AMYLASE_CRYSTAL(ItemID.AMYLASE, "Convert to crystals", 0, 0, ExperienceItem.MARK_OF_GRACE, null, new ItemStack(ItemID.AMYLASE, 10)),
	FORGOTTEN_BREW(ItemID._1DOSEFORGOTTENBREW, "Forgotten Brew", 91, 36, ExperienceItem.ANCIENT_BREW, Secondaries.FORGOTTEN_BREW, new ItemStack(ItemID._1DOSEFORGOTTENBREW, 1)),
	STAMINA_POTION(ItemID._1DOSESTAMINA, "Stamina potion", 77, 25.5, ExperienceItem.AMYLASE_CRYSTAL, Secondaries.STAMINA_POTION, new ItemStack(ItemID._1DOSESTAMINA, 1)),
	EXTENDED_ANTIFIRE(ItemID._1DOSE2ANTIDRAGON, "Extended antifire", 84, 27.5, ExperienceItem.LAVA_SCALE_SHARD, Secondaries.EXTENDED_ANTIFIRE, new ItemStack(ItemID._1DOSE2ANTIDRAGON, 1)),
	EXTENDED_SUPER_ANTIFIRE(ItemID._1DOSE4ANTIDRAGON, "Extended super antifire", 98, 40, ExperienceItem.LAVA_SCALE_SHARD, Secondaries.EXTENDED_SUPER_ANTIFIRE, new ItemStack(ItemID._1DOSE4ANTIDRAGON, 1)),
	EXTENDED_SUPER_ANTIFIRE_4_DOSE(ItemID._4DOSE2ANTIDRAGON, "Extended super antifire", 98, 180, ExperienceItem.EXTENDED_ANTIFIRE4, Secondaries.CRUSHED_SUPERIOR_DRAGON_BONES, new ItemStack(ItemID._4DOSE4ANTIDRAGON, 1)),
	ANTIVENOM(ItemID.ANTIVENOM1, "Antivenom", 87, 30, ExperienceItem.ANTIVENOM, Secondaries.ANTIVENOM, new ItemStack(ItemID.ANTIVENOM1, 1)),
	/**
	 * Construction
	 */
	PLANK(ItemID.WOODPLANK, "Regular Plank", 1, 0, ExperienceItem.LOGS, Secondaries.COINS_100, new ItemStack(ItemID.WOODPLANK, 1)),
	PLANKS(ItemID.WOODPLANK, "Regular plank products", 1, 29, ExperienceItem.PLANK, null, null),
	OAK_PLANK(ItemID.PLANK_OAK, "Oak Plank", 15, 0, ExperienceItem.OAK_LOGS, Secondaries.COINS_250, new ItemStack(ItemID.PLANK_OAK, 1)),
	OAK_PLANKS(ItemID.PLANK_OAK, "Oak products", 15, 60, ExperienceItem.OAK_PLANK, null, null),
	TEAK_PLANK(ItemID.PLANK_TEAK, "Teak Plank", 35, 0, ExperienceItem.TEAK_LOGS, Secondaries.COINS_500, new ItemStack(ItemID.PLANK_TEAK, 1)),
	TEAK_PLANKS(ItemID.PLANK_TEAK, "Teak products", 35, 90, ExperienceItem.TEAK_PLANK, null, null),
	MYTHICAL_CAPE(ItemID.MYTHICAL_CAPE, "Mythical cape racks", 47, 123.33, ExperienceItem.TEAK_PLANK, null, null),
	MAHOGANY_PLANK(ItemID.PLANK_MAHOGANY, "Mahogany Plank", 50, 0, ExperienceItem.MAHOGANY_LOGS, Secondaries.COINS_1500, new ItemStack(ItemID.PLANK_MAHOGANY, 1)),
	MAHOGANY_PLANKS(ItemID.PLANK_MAHOGANY, "Mahogany products", 50, 140, ExperienceItem.MAHOGANY_PLANK, null, null),
	PLANK_MAKE(ItemID.ASTRALRUNE, "Regular Plank Make", 1, 0, ExperienceItem.LOGS, Secondaries.PLANK_MAKE_REGULAR, new ItemStack(ItemID.WOODPLANK, 1)),
	OAK_PLANK_MAKE(ItemID.ASTRALRUNE, "Oak Plank Make", 15, 0, ExperienceItem.OAK_LOGS, Secondaries.PLANK_MAKE_OAK, new ItemStack(ItemID.PLANK_OAK, 1)),
	TEAK_PLANK_MAKE(ItemID.ASTRALRUNE, "Teak Plank Make", 35, 0, ExperienceItem.TEAK_LOGS, Secondaries.PLANK_MAKE_TEAK, new ItemStack(ItemID.PLANK_TEAK, 1)),
	MAHOGANY_PLANK_MAKE(ItemID.ASTRALRUNE, "Mahogany Plank Make", 50, 0, ExperienceItem.MAHOGANY_LOGS, Secondaries.PLANK_MAKE_MAHOGANY, new ItemStack(ItemID.PLANK_MAHOGANY, 1)),
	LONG_BONE(ItemID.DORGESH_CONSTRUCTION_BONE, "Long Bone", 30, 4500, ExperienceItem.LONG_BONE, null, null),
	CURVED_BONE(ItemID.DORGESH_CONSTRUCTION_BONE_CURVED, "Curved Bone", 30, 6750, ExperienceItem.CURVED_BONE, null, null),
	// Mahogany Homes XP rates are calculated utilizing the averages as generated by the wiki
	// https://oldschool.runescape.wiki/w/Mahogany_Homes
	MAHOGANY_HOMES_PLANK(ItemID.HOSIDIUS_BLUEPRINTS, "Mahogany Homes", 1, 93.650, true, ExperienceItem.PLANK, Secondaries.STEEL_BAR_PLANK, null),
	MAHOGANY_HOMES_OAK(ItemID.HOSIDIUS_BLUEPRINTS, "Mahogany Homes", 20, 200.003, true, ExperienceItem.OAK_PLANK, Secondaries.STEEL_BAR_OAK, null),
	MAHOGANY_HOMES_TEAK(ItemID.HOSIDIUS_BLUEPRINTS, "Mahogany Homes", 50, 287.852, true, ExperienceItem.TEAK_PLANK, Secondaries.STEEL_BAR_TEAK, null),
	MAHOGANY_HOMES_MAHOGANY(ItemID.HOSIDIUS_BLUEPRINTS, "Mahogany Homes", 70, 346.120, true, ExperienceItem.MAHOGANY_PLANK, Secondaries.STEEL_BAR_MAHOGANY, null),
	/**
	 * Prayer
	 */
	BONES(ItemID.BONES, "Bones", 1, 4.5, ExperienceItem.BONES, null, null),
	WOLF_BONES(ItemID.WOLF_BONES, "Wolf bones", 1, 4.5, ExperienceItem.WOLF_BONES, null, null),
	BURNT_BONES(ItemID.BONES_BURNT, "Burnt bones", 1, 4.5, ExperienceItem.BURNT_BONES, null, null),
	MONKEY_BONES(ItemID.MM_NORMAL_MONKEY_BONES, "Monkey bones", 1, 5.0, ExperienceItem.MONKEY_BONES, null, null),
	BAT_BONES(ItemID.BAT_BONES, "Bat bones", 1, 5.3, ExperienceItem.BAT_BONES, null, null),
	JOGRE_BONES(ItemID.TBWT_JOGRE_BONES, "Jogre bones", 1, 15.0, ExperienceItem.JOGRE_BONES, null, null),
	BIG_BONES(ItemID.BIG_BONES, "Big bones", 1, 15.0, ExperienceItem.BIG_BONES, null, null),
	ZOGRE_BONES(ItemID.ZOGRE_BONES, "Zogre bones", 1, 22.5, ExperienceItem.ZOGRE_BONES, null, null),
	SHAIKAHAN_BONES(ItemID.TBWT_BEAST_BONES, "Shaikahan bones", 1, 25.0, ExperienceItem.SHAIKAHAN_BONES, null, null),
	BABYDRAGON_BONES(ItemID.BABYDRAGON_BONES, "Babydragon bones", 1, 30.0, ExperienceItem.BABYDRAGON_BONES, null, null),
	WYVERN_BONES(ItemID.WYVERN_BONES, "Wyvern bones", 1, 72.0, ExperienceItem.WYVERN_BONES, null, null),
	DRAGON_BONES(ItemID.DRAGON_BONES, "Dragon bones", 1, 72.0, ExperienceItem.DRAGON_BONES, null, null),
	FAYRG_BONES(ItemID.ZOGRE_ANCESTRAL_BONES_FAYG, "Fayrg bones", 1, 84.0, ExperienceItem.FAYRG_BONES, null, null),
	LAVA_DRAGON_BONES(ItemID.LAVA_DRAGON_BONES, "Lava dragon bones", 1, 85.0, ExperienceItem.LAVA_DRAGON_BONES, null, null),
	RAURG_BONES(ItemID.ZOGRE_ANCESTRAL_BONES_RAURG, "Raurg bones", 1, 96.0, ExperienceItem.RAURG_BONES, null, null),
	DAGANNOTH_BONES(ItemID.DAGANNOTH_KING_BONES, "Dagannoth bones", 1, 125.0, ExperienceItem.DAGANNOTH_BONES, null, null),
	OURG_BONES(ItemID.ZOGRE_ANCESTRAL_BONES_OURG, "Ourg bones", 1, 140.0, ExperienceItem.OURG_BONES, null, null),
	SUPERIOR_DRAGON_BONES(ItemID.DRAGON_BONES_SUPERIOR, "Superior dragon bones", 70, 150.0, ExperienceItem.SUPERIOR_DRAGON_BONES, null, null),
	WYRM_BONES(ItemID.WYRM_BONES, "Wyrm bones", 1, 50.0, ExperienceItem.WYRM_BONES, null, null),
	DRAKE_BONES(ItemID.DRAKE_BONES, "Drake bones", 1, 80.0, ExperienceItem.DRAKE_BONES, null, null),
	HYDRA_BONES(ItemID.HYDRA_BONES, "Hydra bones", 1, 110.0, ExperienceItem.HYDRA_BONES, null, null),
	// Wyrmling bones behave slightly differently than other bones, so modifiers cannot apply correctly
	WYRMLING_BONES_BURY(ItemID.BABYWYRM_BONES, "Bury", 1, 21.0, ExperienceItem.WYRMLING_BONES, null, null),
	WYRMLING_BONES_SINISTER(ItemID.BABYWYRM_BONES, "Sinister offering", 1, 63.0, ExperienceItem.WYRMLING_BONES, null, null),
	WYRMLING_BONES_GILDED(ItemID.BABYWYRM_BONES, "Lit Gilded altar", 1, 73.5, ExperienceItem.WYRMLING_BONES, null, null),
	WYRMLING_BONES_ECTOFUNTUS(ItemID.BABYWYRM_BONES, "Ectofuntus", 1, 120.0, ExperienceItem.WYRMLING_BONES, null, null),
	WYRMLING_BONES_WILDERNESS(ItemID.BABYWYRM_BONES, "Wildy altar", 1, 147.0, ExperienceItem.WYRMLING_BONES, null, null),
	// Libation bowl (blessed bone shards + bless/sunfire wine)
	WYRMLING_BONES_TO_SHARDS(ItemID.BLESSED_BONE_SHARD, "Blessed bone shards", 0, 0.0, ExperienceItem.WYRMLING_BONES, null, new ItemStack(ItemID.BLESSED_BONE_SHARD, 21)),
	BLESSED_WYRMLING_BONES_TO_SHARDS(ItemID.BLESSED_BABYWYRM_BONES, "Blessed bone shards", 1, 0.0, ExperienceItem.BLESSED_BABYWYRM_BONES, null, new ItemStack(ItemID.BLESSED_BONE_SHARD, 21)),
	BONES_TO_SHARDS(ItemID.BLESSED_BONE_SHARD, "Blessed bone shards", 0, 0.0, ExperienceItem.BONES, null, new ItemStack(ItemID.BLESSED_BONE_SHARD, 4)),
	BLESSED_BONES_TO_SHARDS(ItemID.BLESSED_BONE_SHARD, "Blessed bone shards", 0, 0.0, ExperienceItem.BLESSED_BONES, null, new ItemStack(ItemID.BLESSED_BONE_SHARD, 4)),
	BAT_BONES_TO_SHARDS(ItemID.BLESSED_BONE_SHARD, "Blessed bone shards", 0, 0.0, ExperienceItem.BAT_BONES, null, new ItemStack(ItemID.BLESSED_BONE_SHARD, 5)),
	BLESSED_BAT_BONES_TO_SHARDS(ItemID.BLESSED_BONE_SHARD, "Blessed bone shards", 0, 0.0, ExperienceItem.BLESSED_BAT_BONES, null, new ItemStack(ItemID.BLESSED_BONE_SHARD, 5)),
	BIG_BONES_TO_SHARDS(ItemID.BLESSED_BONE_SHARD, "Blessed bone shards", 0, 0.0, ExperienceItem.BIG_BONES, null, new ItemStack(ItemID.BLESSED_BONE_SHARD, 12)),
	BLESSED_BIG_BONES_TO_SHARDS(ItemID.BLESSED_BONE_SHARD, "Blessed bone shards", 0, 0.0, ExperienceItem.BLESSED_BIG_BONES, null, new ItemStack(ItemID.BLESSED_BONE_SHARD, 12)),
	ZOGRE_BONES_TO_SHARDS(ItemID.BLESSED_BONE_SHARD, "Blessed bone shards", 0, 0.0, ExperienceItem.ZOGRE_BONES, null, new ItemStack(ItemID.BLESSED_BONE_SHARD, 18)),
	BLESSED_ZOGRE_BONES_TO_SHARDS(ItemID.BLESSED_BONE_SHARD, "Blessed bone shards", 0, 0.0, ExperienceItem.BLESSED_ZOGRE_BONES, null, new ItemStack(ItemID.BLESSED_BONE_SHARD, 18)),
	BABYDRAGON_BONES_TO_SHARDS(ItemID.BLESSED_BONE_SHARD, "Blessed bone shards", 0, 0.0, ExperienceItem.BABYDRAGON_BONES, null, new ItemStack(ItemID.BLESSED_BONE_SHARD, 24)),
	BLESSED_BABYDRAGON_BONES_TO_SHARDS(ItemID.BLESSED_BONE_SHARD, "Blessed bone shards", 0, 0.0, ExperienceItem.BLESSED_BABYDRAGON_BONES, null, new ItemStack(ItemID.BLESSED_BONE_SHARD, 24)),
	WYRM_BONES_TO_SHARDS(ItemID.BLESSED_BONE_SHARD, "Blessed bone shards", 0, 0.0, ExperienceItem.WYRM_BONES, null, new ItemStack(ItemID.BLESSED_BONE_SHARD, 42)),
	BLESSED_WYRM_BONES_TO_SHARDS(ItemID.BLESSED_BONE_SHARD, "Blessed bone shards", 0, 0.0, ExperienceItem.BLESSED_WYRM_BONES, null, new ItemStack(ItemID.BLESSED_BONE_SHARD, 42)),
	WYVERN_BONES_TO_SHARDS(ItemID.BLESSED_BONE_SHARD, "Blessed bone shards", 0, 0.0, ExperienceItem.WYVERN_BONES, null, new ItemStack(ItemID.BLESSED_BONE_SHARD, 58)),
	BLESSED_WYVERN_BONES_TO_SHARDS(ItemID.BLESSED_BONE_SHARD, "Blessed bone shards", 0, 0.0, ExperienceItem.BLESSED_WYVERN_BONES, null, new ItemStack(ItemID.BLESSED_BONE_SHARD, 58)),
	DRAGON_BONES_TO_SHARDS(ItemID.BLESSED_BONE_SHARD, "Blessed bone shards", 0, 0.0, ExperienceItem.DRAGON_BONES, null, new ItemStack(ItemID.BLESSED_BONE_SHARD, 58)),
	BLESSED_DRAGON_BONES_TO_SHARDS(ItemID.BLESSED_BONE_SHARD, "Blessed bone shards", 0, 0.0, ExperienceItem.BLESSED_DRAGON_BONES, null, new ItemStack(ItemID.BLESSED_BONE_SHARD, 58)),
	DRAKE_BONES_TO_SHARDS(ItemID.BLESSED_BONE_SHARD, "Blessed bone shards", 0, 0.0, ExperienceItem.DRAKE_BONES, null, new ItemStack(ItemID.BLESSED_BONE_SHARD, 64)),
	BLESSED_DRAKE_BONES_TO_SHARDS(ItemID.BLESSED_BONE_SHARD, "Blessed bone shards", 0, 0.0, ExperienceItem.BLESSED_DRAKE_BONES, null, new ItemStack(ItemID.BLESSED_BONE_SHARD, 64)),
	FAYRG_BONES_TO_SHARDS(ItemID.BLESSED_BONE_SHARD, "Blessed bone shards", 0, 0.0, ExperienceItem.FAYRG_BONES, null, new ItemStack(ItemID.BLESSED_BONE_SHARD, 67)),
	BLESSED_FAYRG_BONES_TO_SHARDS(ItemID.BLESSED_BONE_SHARD, "Blessed bone shards", 0, 0.0, ExperienceItem.BLESSED_FAYRG_BONES, null, new ItemStack(ItemID.BLESSED_BONE_SHARD, 67)),
	LAVA_DRAGON_BONES_TO_SHARDS(ItemID.BLESSED_BONE_SHARD, "Blessed bone shards", 0, 0.0, ExperienceItem.LAVA_DRAGON_BONES, null, new ItemStack(ItemID.BLESSED_BONE_SHARD, 68)),
	BLESSED_LAVA_DRAGON_BONES_TO_SHARDS(ItemID.BLESSED_BONE_SHARD, "Blessed bone shards", 0, 0.0, ExperienceItem.BLESSED_LAVA_DRAGON_BONES, null, new ItemStack(ItemID.BLESSED_BONE_SHARD, 68)),
	RAURG_BONES_TO_SHARDS(ItemID.BLESSED_BONE_SHARD, "Blessed bone shards", 0, 0.0, ExperienceItem.RAURG_BONES, null, new ItemStack(ItemID.BLESSED_BONE_SHARD, 77)),
	BLESSED_RAURG_BONES_TO_SHARDS(ItemID.BLESSED_BONE_SHARD, "Blessed bone shards", 0, 0.0, ExperienceItem.BLESSED_RAURG_BONES, null, new ItemStack(ItemID.BLESSED_BONE_SHARD, 77)),
	HYDRA_BONES_TO_SHARDS(ItemID.BLESSED_BONE_SHARD, "Blessed bone shards", 0, 0.0, ExperienceItem.HYDRA_BONES, null, new ItemStack(ItemID.BLESSED_BONE_SHARD, 93)),
	BLESSED_HYDRA_BONES_TO_SHARDS(ItemID.BLESSED_BONE_SHARD, "Blessed bone shards", 0, 0.0, ExperienceItem.BLESSED_HYDRA_BONES, null, new ItemStack(ItemID.BLESSED_BONE_SHARD, 93)),
	DAGANNOTH_BONES_TO_SHARDS(ItemID.BLESSED_BONE_SHARD, "Blessed bone shards", 0, 0.0, ExperienceItem.DAGANNOTH_BONES, null, new ItemStack(ItemID.BLESSED_BONE_SHARD, 100)),
	BLESSED_DAGANNOTH_BONES_TO_SHARDS(ItemID.BLESSED_BONE_SHARD, "Blessed bone shards", 0, 0.0, ExperienceItem.DAGANNOTH_BONES_29376, null, new ItemStack(ItemID.BLESSED_BONE_SHARD, 100)),
	OURG_BONES_TO_SHARDS(ItemID.BLESSED_BONE_SHARD, "Blessed bone shards", 0, 0.0, ExperienceItem.OURG_BONES, null, new ItemStack(ItemID.BLESSED_BONE_SHARD, 115)),
	BLESSED_OURG_BONES_TO_SHARDS(ItemID.BLESSED_BONE_SHARD, "Blessed bone shards", 0, 0.0, ExperienceItem.BLESSED_OURG_BONES, null, new ItemStack(ItemID.BLESSED_BONE_SHARD, 115)),
	SUPERIOR_DRAGON_BONES_TO_SHARDS(ItemID.BLESSED_BONE_SHARD, "Blessed bone shards", 0, 0.0, ExperienceItem.SUPERIOR_DRAGON_BONES, null, new ItemStack(ItemID.BLESSED_BONE_SHARD, 121)),
	BLESSED_SUPERIOR_DRAGON_BONES_TO_SHARDS(ItemID.BLESSED_BONE_SHARD, "Blessed bone shards", 0, 0.0, ExperienceItem.BLESSED_SUPERIOR_DRAGON_BONES, null, new ItemStack(ItemID.BLESSED_BONE_SHARD, 121)),
	SUNKISSED_BONES_TO_SHARDS(ItemID.BLESSED_BONE_SHARD, "Blessed bone shards", 0, 0.0, ExperienceItem.SUNKISSED_BONES, null, new ItemStack(ItemID.BLESSED_BONE_SHARD, 45)),
	BLESSED_BONE_STATUETTE(ItemID.VARLAMORE_BONE_STATUETTE01, "Bone statuette", 0, 0.0, ExperienceItem.BLESSED_BONE_STATUETTE, null, new ItemStack(ItemID.BLESSED_BONE_SHARD, 125)),
	BLESSED_BONE_STATUETTE_29340(ItemID.VARLAMORE_BONE_STATUETTE02, "Bone statuette", 0, 0.0, ExperienceItem.BLESSED_BONE_STATUETTE_29340, null, new ItemStack(ItemID.BLESSED_BONE_SHARD, 125)),
	BLESSED_BONE_STATUETTE_29342(ItemID.VARLAMORE_BONE_STATUETTE03, "Bone statuette", 0, 0.0, ExperienceItem.BLESSED_BONE_STATUETTE_29342, null, new ItemStack(ItemID.BLESSED_BONE_SHARD, 125)),
	BLESSED_BONE_SHARDS_WINE(ItemID.JUG_WINE_BLESSED, "Blessed wine", 30, 5.0, ExperienceItem.BLESSED_BONE_SHARDS, Secondaries.BLESSED_BONE_SHARDS_JUG_OF_BLESSED_WINE, null),
	BLESSED_BONE_SHARDS_SUNFIRE_WINE(ItemID.JUG_SUNFIRE_WINE_BLESSED, "Sunfire wine", 30, 6.0, ExperienceItem.BLESSED_BONE_SHARDS, Secondaries.BLESSED_BONE_SHARDS_JUG_OF_BLESSED_SUNFIRE_WINE, null),
	// Bonemeal
	BONEMEAL(ItemID.POT_BONEMEAL, "Bonemeal", 1, 18, ExperienceItem.BONEMEAL, Secondaries.BUCKET_OF_SLIME, null),
	WOLF_BONEMEAL(ItemID.POT_BONEMEAL_WOLF, "Wolf bonemeal", 1, 18, ExperienceItem.WOLF_BONEMEAL, Secondaries.BUCKET_OF_SLIME, null),
	BURNT_BONEMEAL(ItemID.POT_BONEMEAL_BURNT, "Burnt bonemeal", 1, 18, ExperienceItem.BURNT_BONEMEAL, Secondaries.BUCKET_OF_SLIME, null),
	MONKEY_BONEMEAL(ItemID.POT_BONEMEAL_NORMAL_MONKEY, "Monkey bonemeal", 1, 20, ExperienceItem.MONKEY_BONEMEAL, Secondaries.BUCKET_OF_SLIME, null),
	BAT_BONEMEAL(ItemID.POT_BONEMEAL_BAT, "Bat bonemeal", 1, 21.2, ExperienceItem.BAT_BONEMEAL, Secondaries.BUCKET_OF_SLIME, null),
	JOGRE_BONEMEAL(ItemID.POT_BONEMEAL_JOGRE, "Jogre bonemeal", 1, 60, ExperienceItem.JOGRE_BONEMEAL, Secondaries.BUCKET_OF_SLIME, null),
	BIG_BONEMEAL(ItemID.POT_BONEMEAL_BIG, "Big bonemeal", 1, 60, ExperienceItem.BIG_BONEMEAL, Secondaries.BUCKET_OF_SLIME, null),
	ZOGRE_BONEMEAL(ItemID.POT_BONEMEAL_ZOGRE, "Zogre bonemeal", 1, 90, ExperienceItem.ZOGRE_BONEMEAL, Secondaries.BUCKET_OF_SLIME, null),
	SHAIKAHAN_BONEMEAL(ItemID.POT_BONEMEAL_BEAST, "Shaikahan bonemeal", 1, 100, ExperienceItem.SHAIKAHAN_BONEMEAL, Secondaries.BUCKET_OF_SLIME, null),
	BABYDRAGON_BONEMEAL(ItemID.POT_BONEMEAL_BABYDRAGON, "Babydragon bonemeal", 1, 120, ExperienceItem.BABYDRAGON_BONEMEAL, Secondaries.BUCKET_OF_SLIME, null),
	WYVERN_BONEMEAL(ItemID.POT_BONEMEAL_WYVERN, "Wyvern bonemeal", 1, 288, ExperienceItem.WYVERN_BONEMEAL, Secondaries.BUCKET_OF_SLIME, null),
	DRAGON_BONEMEAL(ItemID.POT_BONEMEAL_DRAGON, "Dragon bonemeal", 1, 288, ExperienceItem.DRAGON_BONEMEAL, Secondaries.BUCKET_OF_SLIME, null),
	FAYRG_BONEMEAL(ItemID.POT_BONEMEAL_ANCESTRAL_FAYG, "Fayrg bonemeal", 1, 336, ExperienceItem.FAYRG_BONEMEAL, Secondaries.BUCKET_OF_SLIME, null),
	LAVA_DRAGON_BONEMEAL(ItemID.POT_BONEMEAL_LAVADRAGON, "Lava dragon bonemeal", 1, 340, ExperienceItem.LAVA_DRAGON_BONEMEAL, Secondaries.BUCKET_OF_SLIME, null),
	RAURG_BONEMEAL(ItemID.POT_BONEMEAL_ANCESTRAL_RAURG, "Raurg bonemeal", 1, 384, ExperienceItem.RAURG_BONEMEAL, Secondaries.BUCKET_OF_SLIME, null),
	DAGANNOTH_BONEMEAL(ItemID.POT_BONEMEAL_DAGANNOTH, "Dagannoth bonemeal", 1, 500, ExperienceItem.DAGANNOTH_BONEMEAL, Secondaries.BUCKET_OF_SLIME, null),
	OURG_BONEMEAL(ItemID.POT_BONEMEAL_ANCESTRAL_OURG, "Ourg bonemeal", 1, 560, ExperienceItem.OURG_BONEMEAL, Secondaries.BUCKET_OF_SLIME, null),
	SUPERIOR_DRAGON_BONEMEAL(ItemID.POT_BONEMEAL_DRAGON_SUPERIOR, "Superior dragon bonemeal", 70, 600, ExperienceItem.SUPERIOR_DRAGON_BONEMEAL, Secondaries.BUCKET_OF_SLIME, null),
	WYRM_BONEMEAL(ItemID.POT_BONEMEAL_WYRM, "Wyrm bonemeal", 1, 200, ExperienceItem.WYRM_BONEMEAL, Secondaries.BUCKET_OF_SLIME, null),
	DRAKE_BONEMEAL(ItemID.POT_BONEMEAL_DRAKE, "Drake bonemeal", 1, 320, ExperienceItem.DRAKE_BONEMEAL, Secondaries.BUCKET_OF_SLIME, null),
	HYDRA_BONEMEAL(ItemID.POT_BONEMEAL_HYDRA, "Hydra bonemeal", 1, 440, ExperienceItem.HYDRA_BONEMEAL, Secondaries.BUCKET_OF_SLIME, null),
	// Shade Remains (Pyre Logs)
	// TODO: Fix this for the different log types and the mory hard diary
	//	LOAR_REMAINS(ItemID.LOAR_REMAINS, "Loar remains", 1, 33.0,
	//		ExperienceItem.LOAR_REMAINS, null, null),
	//	PHRIN_REMAINS(ItemID.PHRIN_REMAINS, "Phrin remains", 1, 46.5,
	//		ExperienceItem.PHRIN_REMAINS, null, null),
	//	RIYL_REMAINS(ItemID.RIYL_REMAINS, "Riyl remains", 1, 59.5,
	//		ExperienceItem.RIYL_REMAINS, null, null),
	//	ASYN_REMAINS(ItemID.ASYN_REMAINS, "Asyn remains", 1, 82.5,
	//		ExperienceItem.ASYN_REMAINS, null, null),
	//	FIYR_REMAINS(ItemID.FIYR_REMAINS, "Fiyre remains", 1, 84.0,
	//		ExperienceItem.FIYR_REMAINS, null, null),
	// Ensouled Heads
	ENSOULED_GOBLIN_HEAD(ItemID.ARCEUUS_CORPSE_GOBLIN, "Ensouled goblin head", 1, 130.0, ExperienceItem.ENSOULED_GOBLIN_HEAD, Secondaries.BASIC_REANIMATION, null),
	ENSOULED_MONKEY_HEAD(ItemID.ARCEUUS_CORPSE_MONKEY, "Ensouled monkey head", 1, 182.0, ExperienceItem.ENSOULED_MONKEY_HEAD, Secondaries.BASIC_REANIMATION, null),
	ENSOULED_IMP_HEAD(ItemID.ARCEUUS_CORPSE_IMP, "Ensouled imp head", 1, 286.0, ExperienceItem.ENSOULED_IMP_HEAD, Secondaries.BASIC_REANIMATION, null),
	ENSOULED_MINOTAUR_HEAD(ItemID.ARCEUUS_CORPSE_MINOTAUR, "Ensouled minotaur head", 1, 364.0, ExperienceItem.ENSOULED_MINOTAUR_HEAD, Secondaries.BASIC_REANIMATION, null),
	ENSOULED_SCORPION_HEAD(ItemID.ARCEUUS_CORPSE_SCORPION, "Ensouled scorpion head", 1, 454.0, ExperienceItem.ENSOULED_SCORPION_HEAD, Secondaries.BASIC_REANIMATION, null),
	ENSOULED_BEAR_HEAD(ItemID.ARCEUUS_CORPSE_BEAR, "Ensouled bear head", 1, 480.0, ExperienceItem.ENSOULED_BEAR_HEAD, Secondaries.BASIC_REANIMATION, null),
	ENSOULED_UNICORN_HEAD(ItemID.ARCEUUS_CORPSE_UNICORN, "Ensouled unicorn head", 1, 494.0, ExperienceItem.ENSOULED_UNICORN_HEAD, Secondaries.BASIC_REANIMATION, null),
	ENSOULED_DOG_HEAD(ItemID.ARCEUUS_CORPSE_DOG, "Ensouled dog head", 1, 520.0, ExperienceItem.ENSOULED_DOG_HEAD, Secondaries.ADEPT_REANIMATION, null),
	ENSOULED_CHAOS_DRUID_HEAD(ItemID.ARCEUUS_CORPSE_CHAOSDRUID, "Ensouled druid head", 1, 584.0, ExperienceItem.ENSOULED_CHAOS_DRUID_HEAD, Secondaries.ADEPT_REANIMATION, null),
	ENSOULED_GIANT_HEAD(ItemID.ARCEUUS_CORPSE_GIANT, "Ensouled giant head", 1, 650.0, ExperienceItem.ENSOULED_GIANT_HEAD, Secondaries.ADEPT_REANIMATION, null),
	ENSOULED_OGRE_HEAD(ItemID.ARCEUUS_CORPSE_OGRE, "Ensouled ogre head", 1, 716.0, ExperienceItem.ENSOULED_OGRE_HEAD, Secondaries.ADEPT_REANIMATION, null),
	ENSOULED_ELF_HEAD(ItemID.ARCEUUS_CORPSE_ELF, "Ensouled elf head", 1, 754.0, ExperienceItem.ENSOULED_ELF_HEAD, Secondaries.ADEPT_REANIMATION, null),
	ENSOULED_TROLL_HEAD(ItemID.ARCEUUS_CORPSE_TROLL, "Ensouled troll head", 1, 780.0, ExperienceItem.ENSOULED_TROLL_HEAD, Secondaries.ADEPT_REANIMATION, null),
	ENSOULED_HORROR_HEAD(ItemID.ARCEUUS_CORPSE_HORROR, "Ensouled horror head", 1, 832.0, ExperienceItem.ENSOULED_HORROR_HEAD, Secondaries.ADEPT_REANIMATION, null),
	ENSOULED_KALPHITE_HEAD(ItemID.ARCEUUS_CORPSE_KALPHITE, "Ensouled kalphite head", 1, 884.0, ExperienceItem.ENSOULED_KALPHITE_HEAD, Secondaries.EXPERT_REANIMATION, null),
	ENSOULED_DAGANNOTH_HEAD(ItemID.ARCEUUS_CORPSE_DAGANNOTH, "Ensouled dagannoth head", 1, 936.0, ExperienceItem.ENSOULED_DAGANNOTH_HEAD, Secondaries.EXPERT_REANIMATION, null),
	ENSOULED_BLOODVELD_HEAD(ItemID.ARCEUUS_CORPSE_BLOODVELD, "Ensouled bloodveld head", 1, 1040.0, ExperienceItem.ENSOULED_BLOODVELD_HEAD, Secondaries.EXPERT_REANIMATION, null),
	ENSOULED_TZHAAR_HEAD(ItemID.ARCEUUS_CORPSE_TZHAAR, "Ensouled tzhaar head", 1, 1104.0, ExperienceItem.ENSOULED_TZHAAR_HEAD, Secondaries.EXPERT_REANIMATION, null),
	ENSOULED_DEMON_HEAD(ItemID.ARCEUUS_CORPSE_DEMON, "Ensouled demon head", 1, 1170.0, ExperienceItem.ENSOULED_DEMON_HEAD, Secondaries.EXPERT_REANIMATION, null),
	ENSOULED_HELLHOUND_HEAD(ItemID.ARCEUUS_CORPSE_HELLHOUND, "Ensouled hellhound head", 1, 1200, ExperienceItem.ENSOULED_HELLHOUND_HEAD, Secondaries.EXPERT_REANIMATION, null),
	ENSOULED_AVIANSIE_HEAD(ItemID.ARCEUUS_CORPSE_AVIANSIE, "Ensouled aviansie head", 1, 1234.0, ExperienceItem.ENSOULED_AVIANSIE_HEAD, Secondaries.MASTER_REANIMATION, null),
	ENSOULED_ABYSSAL_HEAD(ItemID.ARCEUUS_CORPSE_ABYSSAL, "Ensouled abyssal head", 1, 1300.0, ExperienceItem.ENSOULED_ABYSSAL_HEAD, Secondaries.MASTER_REANIMATION, null),
	ENSOULED_DRAGON_HEAD(ItemID.ARCEUUS_CORPSE_DRAGON, "Ensouled dragon head", 1, 1560.0, ExperienceItem.ENSOULED_DRAGON_HEAD, Secondaries.MASTER_REANIMATION, null),
	// Fossils
	SMALL_LIMBS(ItemID.FOSSIL_SMALL_1, "Small limbs", 1, 0, ExperienceItem.SMALL_LIMBS, null, new ItemStack(ItemID.FOSSIL_SMALL_UNID, 1)),
	SMALL_SPINE(ItemID.FOSSIL_SMALL_2, "Small spine", 1, 0, ExperienceItem.SMALL_SPINE, null, new ItemStack(ItemID.FOSSIL_SMALL_UNID, 1)),
	SMALL_RIBS(ItemID.FOSSIL_SMALL_3, "Small ribs", 1, 0, ExperienceItem.SMALL_RIBS, null, new ItemStack(ItemID.FOSSIL_SMALL_UNID, 1)),
	SMALL_PELVIS(ItemID.FOSSIL_SMALL_4, "Small pelvis", 1, 0, ExperienceItem.SMALL_PELVIS, null, new ItemStack(ItemID.FOSSIL_SMALL_UNID, 1)),
	SMALL_SKULL(ItemID.FOSSIL_SMALL_5, "Small skull", 1, 0, ExperienceItem.SMALL_SKULL, null, new ItemStack(ItemID.FOSSIL_SMALL_UNID, 1)),
	SMALL_FOSSIL(ItemID.FOSSIL_SMALL_UNID, "Small fossil", 1, 500, ExperienceItem.SMALL_FOSSIL, null, null),
	MEDIUM_LIMBS(ItemID.FOSSIL_MEDIUM_1, "Medium limbs", 1, 0, ExperienceItem.MEDIUM_LIMBS, null, new ItemStack(ItemID.FOSSIL_MEDIUM_UNID, 1)),
	MEDIUM_SPINE(ItemID.FOSSIL_MEDIUM_2, "Medium spine", 1, 0, ExperienceItem.MEDIUM_SPINE, null, new ItemStack(ItemID.FOSSIL_MEDIUM_UNID, 1)),
	MEDIUM_RIBS(ItemID.FOSSIL_MEDIUM_3, "Medium ribs", 1, 0, ExperienceItem.MEDIUM_RIBS, null, new ItemStack(ItemID.FOSSIL_MEDIUM_UNID, 1)),
	MEDIUM_PELVIS(ItemID.FOSSIL_MEDIUM_4, "Medium pelvis", 1, 0, ExperienceItem.MEDIUM_PELVIS, null, new ItemStack(ItemID.FOSSIL_MEDIUM_UNID, 1)),
	MEDIUM_SKULL(ItemID.FOSSIL_MEDIUM_5, "Medium skull", 1, 0, ExperienceItem.MEDIUM_SKULL, null, new ItemStack(ItemID.FOSSIL_MEDIUM_UNID, 1)),
	MEDIUM_FOSSIL(ItemID.FOSSIL_MEDIUM_UNID, "Medium fossil", 1, 1000, ExperienceItem.MEDIUM_FOSSIL, null, null),
	LARGE_LIMBS(ItemID.FOSSIL_LARGE_1, "Large limbs", 1, 0, ExperienceItem.LARGE_LIMBS, null, new ItemStack(ItemID.FOSSIL_LARGE_UNID, 1)),
	LARGE_SPINE(ItemID.FOSSIL_LARGE_2, "Large spine", 1, 0, ExperienceItem.LARGE_SPINE, null, new ItemStack(ItemID.FOSSIL_LARGE_UNID, 1)),
	LARGE_RIBS(ItemID.FOSSIL_LARGE_3, "Large ribs", 1, 0, ExperienceItem.LARGE_RIBS, null, new ItemStack(ItemID.FOSSIL_LARGE_UNID, 1)),
	LARGE_PELVIS(ItemID.FOSSIL_LARGE_4, "Large pelvis", 1, 0, ExperienceItem.LARGE_PELVIS, null, new ItemStack(ItemID.FOSSIL_LARGE_UNID, 1)),
	LARGE_SKULL(ItemID.FOSSIL_LARGE_5, "Large skull", 1, 0, ExperienceItem.LARGE_SKULL, null, new ItemStack(ItemID.FOSSIL_LARGE_UNID, 1)),
	LARGE_FOSSIL(ItemID.FOSSIL_LARGE_UNID, "Large fossil", 1, 1500, ExperienceItem.LARGE_FOSSIL, null, null),
	RARE_LIMBS(ItemID.FOSSIL_RARE_1, "Rare limbs", 1, 0, ExperienceItem.RARE_LIMBS, null, new ItemStack(ItemID.FOSSIL_RARE_UNID, 1)),
	RARE_SPINE(ItemID.FOSSIL_RARE_2, "Rare spine", 1, 0, ExperienceItem.RARE_SPINE, null, new ItemStack(ItemID.FOSSIL_RARE_UNID, 1)),
	RARE_RIBS(ItemID.FOSSIL_RARE_3, "Rare ribs", 1, 0, ExperienceItem.RARE_RIBS, null, new ItemStack(ItemID.FOSSIL_RARE_UNID, 1)),
	RARE_PELVIS(ItemID.FOSSIL_RARE_4, "Rare pelvis", 1, 0, ExperienceItem.RARE_PELVIS, null, new ItemStack(ItemID.FOSSIL_RARE_UNID, 1)),
	RARE_SKULL(ItemID.FOSSIL_RARE_5, "Rare skull", 1, 0, ExperienceItem.RARE_SKULL, null, new ItemStack(ItemID.FOSSIL_RARE_UNID, 1)),
	RARE_TUSK(ItemID.FOSSIL_RARE_6, "Rare tusk", 1, 0, ExperienceItem.RARE_TUSK, null, new ItemStack(ItemID.FOSSIL_RARE_UNID, 1)),
	RARE_FOSSIL(ItemID.FOSSIL_RARE_UNID, "Rare fossil", 1, 2500, ExperienceItem.RARE_FOSSIL, null, null),
	// Ashes
	FIENDISH_ASHES(ItemID.FIENDISH_ASHES, "Fiendish ashes", 1, 10, ExperienceItem.FIENDISH_ASHES, null, null),
	VILE_ASHES(ItemID.VILE_ASHES, "Vile ashes", 1, 25, ExperienceItem.VILE_ASHES, null, null),
	MALICIOUS_ASHES(ItemID.MALICIOUS_ASHES, "Malicious ashes", 1, 65, ExperienceItem.MALICIOUS_ASHES, null, null),
	ABYSSAL_ASHES(ItemID.ABYSSAL_ASHES, "Abyssal ashes", 1, 85, ExperienceItem.ABYSSAL_ASHES, null, null),
	INFERNAL_ASHES(ItemID.INFERNAL_ASHES, "Infernal ashes", 1, 110, ExperienceItem.INFERNAL_ASHES, null, null),
	// Bird Eggs
	BIRD_EGG_NEST_RED(ItemID.BIRD_NEST_EGG_RED, "Bird's egg (Red)", 1, 0, ExperienceItem.BIRD_EGG_NEST_RED, null, new ItemStack(ItemID.BIRD_EGG_RED, 1)),
	BIRD_EGG_NEST_BLUE(ItemID.BIRD_NEST_EGG_BLUE, "Bird's egg (Blue)", 1, 0, ExperienceItem.BIRD_EGG_NEST_BLUE, null, new ItemStack(ItemID.BIRD_EGG_BLUE, 1)),
	BIRD_EGG_NEST_GREEN(ItemID.BIRD_NEST_EGG_GREEN, "Bird's egg (Green)", 1, 0, ExperienceItem.BIRD_EGG_NEST_GREEN, null, new ItemStack(ItemID.BIRD_EGG_GREEN, 1)),
	BIRD_EGG_RED(ItemID.BIRD_EGG_RED, "Offer bird's egg", 1, 100, ExperienceItem.BIRD_EGG_RED, null, null),
	BIRD_EGG_BLUE(ItemID.BIRD_EGG_BLUE, "Offer bird's egg", 1, 100, ExperienceItem.BIRD_EGG_BLUE, null, null),
	BIRD_EGG_GREEN(ItemID.BIRD_EGG_GREEN, "Offer bird's egg", 1, 100, ExperienceItem.BIRD_EGG_GREEN, null, null),
	// Slayer Trophies
	CRAWLING_HAND(ItemID.POH_TROPHYDROP_CRAWLINGHAND, "Crawling hand", 1, 2500, ExperienceItem.CRAWLING_HAND, null, null),
	COCKATRICE_HEAD(ItemID.POH_TROPHYDROP_COCKATRICE, "Cockatrice head", 1, 2500, ExperienceItem.COCKATRICE_HEAD, null, null),
	BASILISK_HEAD(ItemID.POH_TROPHYDROP_BASILISK, "Basilisk head", 1, 2500, ExperienceItem.BASILISK_HEAD, null, null),
	KURASK_HEAD(ItemID.POH_TROPHYDROP_KURASK, "Kurask head", 1, 2500, ExperienceItem.KURASK_HEAD, null, null),
	ABYSSAL_HEAD(ItemID.POH_TROPHYDROP_ABYSSALDEMON, "Abyssal head", 1, 2500, ExperienceItem.ABYSSAL_HEAD, null, null),
	KBD_HEADS(ItemID.POH_TROPHYDROP_KBD, "Kbd heads", 1, 2500, ExperienceItem.KBD_HEADS, null, null),
	KQ_HEAD(ItemID.POH_TROPHYDROP_KALPHITEQUEEN, "Kq head", 1, 2500, ExperienceItem.KQ_HEAD, null, null),
	VORKATHS_HEAD(ItemID.VORKATH_HEAD, "Vorkaths head", 1, 1000, ExperienceItem.VORKATHS_HEAD, null, null),
	ALCHEMICAL_HYDRA_HEADS(ItemID.POH_ALCHEMICAL_HYDRA_HEAD, "Alchemical hydra heads", 1, 2500, ExperienceItem.ALCHEMICAL_HYDRA_HEADS, null, null),
	/**
	 * Cooking
	 */
	COOK_BEEF(ItemID.COOKED_MEAT, "Cooked meat", 1, 30.0, ExperienceItem.RAW_BEEF, null, new ItemStack(ItemID.COOKED_MEAT, 1)),
	COOK_RAT_MEAT(ItemID.COOKED_MEAT, "Cooked meat", 1, 30.0, ExperienceItem.RAW_RAT_MEAT, null, new ItemStack(ItemID.COOKED_MEAT, 1)),
	COOK_BEAR_MEAT(ItemID.COOKED_MEAT, "Cooked meat", 1, 30.0, ExperienceItem.RAW_BEAR_MEAT, null, new ItemStack(ItemID.COOKED_MEAT, 1)),
	COOK_YAK_MEAT(ItemID.COOKED_MEAT, "Cooked meat", 1, 40.0, ExperienceItem.RAW_YAK_MEAT, null, new ItemStack(ItemID.COOKED_MEAT, 1)),
	COOK_UGTHANKI_MEAT(ItemID.COOKED_UGTHANKI_MEAT, "Ugthanki meat", 1, 40.0, ExperienceItem.RAW_UGTHANKI_MEAT, null, new ItemStack(ItemID.COOKED_UGTHANKI_MEAT, 1)),
	COOK_CHICKEN(ItemID.COOKED_CHICKEN, "Cooked chicken", 1, 30.0, ExperienceItem.RAW_CHICKEN, null, new ItemStack(ItemID.COOKED_CHICKEN, 1)),
	COOK_RABBIT(ItemID.COOKED_RABBIT, "Cooked rabbit", 1, 30.0, ExperienceItem.RAW_RABBIT, null, new ItemStack(ItemID.COOKED_RABBIT, 1)),
	COOK_SHRIMPS(ItemID.SHRIMP, "Shrimps", 1, 30.0, ExperienceItem.RAW_SHRIMPS, null, new ItemStack(ItemID.SHRIMP, 1)),
	COOK_SARDINE(ItemID.SARDINE, "Sardine", 1, 40.0, ExperienceItem.RAW_SARDINE, null, new ItemStack(ItemID.SARDINE, 1)),
	COOK_ANCHOVIES(ItemID.ANCHOVIES, "Anchovies", 1, 30.0, ExperienceItem.RAW_ANCHOVIES, null, new ItemStack(ItemID.ANCHOVIES, 1)),
	COOK_HERRING(ItemID.HERRING, "Herring", 5, 50.0, ExperienceItem.RAW_HERRING, null, new ItemStack(ItemID.HERRING, 1)),
	COOK_MACKEREL(ItemID.MACKEREL, "Mackerel", 10, 60.0, ExperienceItem.RAW_MACKEREL, null, new ItemStack(ItemID.MACKEREL, 1)),
	COOK_BIRD_MEAT(ItemID.SPIT_ROASTED_BIRD_MEAT, "Roast bird meat", 11, 62.5, ExperienceItem.RAW_BIRD_MEAT, null, new ItemStack(ItemID.SPIT_ROASTED_BIRD_MEAT, 1)),
	COOK_THIN_SNAIL(ItemID.SNAIL_CORPSE_COOKED1, "Thin snail meat", 12, 70.0, ExperienceItem.THIN_SNAIL, null, new ItemStack(ItemID.SNAIL_CORPSE_COOKED1, 1)),
	COOK_TROUT(ItemID.TROUT, "Trout", 15, 70.0, ExperienceItem.RAW_TROUT, null, new ItemStack(ItemID.TROUT, 1)),
	COOK_LEAN_SNAIL(ItemID.SNAIL_CORPSE_COOKED2, "Lean snail meat", 17, 80.0, ExperienceItem.LEAN_SNAIL, null, new ItemStack(ItemID.SNAIL_CORPSE_COOKED2, 1)),
	COOK_COD(ItemID.COD, "Cod", 18, 75.0, ExperienceItem.RAW_COD, null, new ItemStack(ItemID.COD, 1)),
	COOK_PIKE(ItemID.PIKE, "Pike", 20, 80.0, ExperienceItem.RAW_PIKE, null, new ItemStack(ItemID.PIKE, 1)),
	COOK_FAT_SNAIL(ItemID.SNAIL_CORPSE_COOKED3, "Fat snail meat", 22, 95.0, ExperienceItem.FAT_SNAIL, null, new ItemStack(ItemID.SNAIL_CORPSE_COOKED3, 1)),
	COOK_BEAST_MEAT(ItemID.SPIT_ROASTED_BEAST_MEAT, "Roast beast meat", 21, 82.5, ExperienceItem.RAW_BEAST_MEAT, null, new ItemStack(ItemID.SPIT_ROASTED_BEAST_MEAT, 1)),
	COOK_SALMON(ItemID.SALMON, "Salmon", 25, 90.0, ExperienceItem.RAW_SALMON, null, new ItemStack(ItemID.SALMON, 1)),
	COOK_TUNA(ItemID.TUNA, "Tuna", 30, 100.0, ExperienceItem.RAW_TUNA, null, new ItemStack(ItemID.TUNA, 1)),
	COOK_RAINBOW_FISH(ItemID.HUNTING_FISH_SPECIAL, "Rainbow fish", 35, 110.0, ExperienceItem.RAW_RAINBOW_FISH, null, new ItemStack(ItemID.HUNTING_FISH_SPECIAL, 1)),
	COOK_KARAMBWAN(ItemID.TBWT_COOKED_KARAMBWAN, "Cooked Karambwan", 30, 190.0, ExperienceItem.RAW_KARAMBWAN, null, new ItemStack(ItemID.TBWT_COOKED_KARAMBWAN, 1)),
	COOK_LOBSTER(ItemID.LOBSTER, "Lobster", 40, 120.0, ExperienceItem.RAW_LOBSTER, null, new ItemStack(ItemID.LOBSTER, 1)),
	COOK_BASS(ItemID.BASS, "Bass", 43, 130.0, ExperienceItem.RAW_BASS, null, new ItemStack(ItemID.BASS, 1)),
	COOK_SWORDFISH(ItemID.SWORDFISH, "Swordfish", 45, 140.0, ExperienceItem.RAW_SWORDFISH, null, new ItemStack(ItemID.SWORDFISH, 1)),
	COOK_MONKFISH(ItemID.MONKFISH, "Monkfish", 62, 150.0, ExperienceItem.RAW_MONKFISH, null, new ItemStack(ItemID.MONKFISH, 1)),
	COOK_SHARK(ItemID.SHARK, "Shark", 80, 210.0, ExperienceItem.RAW_SHARK, null, new ItemStack(ItemID.SHARK, 1)),
	COOK_SEA_TURTLE(ItemID.SEATURTLE, "Sea turtle", 82, 211.3, ExperienceItem.RAW_SEA_TURTLE, null, new ItemStack(ItemID.SEATURTLE, 1)),
	COOK_ANGLERFISH(ItemID.ANGLERFISH, "Anglerfish", 84, 230.0, ExperienceItem.RAW_ANGLERFISH, null, new ItemStack(ItemID.ANGLERFISH, 1)),
	COOK_DARK_CRAB(ItemID.DARK_CRAB, "Dark crab", 90, 215.0, ExperienceItem.RAW_DARK_CRAB, null, new ItemStack(ItemID.DARK_CRAB, 1)),
	COOK_MANTA_RAY(ItemID.MANTARAY, "Manta ray", 91, 216.2, ExperienceItem.RAW_MANTA_RAY, null, new ItemStack(ItemID.MANTARAY, 1)),
	WINE(ItemID.JUG_WINE, "Jug of wine", 35, 200, ExperienceItem.GRAPES, Secondaries.JUG_OF_WATER, new ItemStack(ItemID.JUG_WINE, 1)),
	SWEETCORN(ItemID.SWEETCORN_COOKED, "Cooked sweetcorn", 28, 104, ExperienceItem.SWEETCORN, null, new ItemStack(ItemID.SWEETCORN_COOKED, 1)),
	STEW(ItemID.STEW, "Stew", 25, 117, ExperienceItem.UNCOOKED_STEW, null, new ItemStack(ItemID.STEW, 1)),
	CURRY(ItemID.CURRY, "Curry", 60, 280, ExperienceItem.UNCOOKED_CURRY, null, new ItemStack(ItemID.CURRY, 1)),
	COOK_WILD_KEBBIT(ItemID.WILDKEBBIT_COOKED, "Cooked wild kebbit", 23, 73.0, ExperienceItem.RAW_WILD_KEBBIT, null, new ItemStack(ItemID.WILDKEBBIT_COOKED, 1)),
	COOK_LARUPIA(ItemID.LARUPIA_COOKED, "Cooked larupia", 31, 92.0, ExperienceItem.RAW_LARUPIA, null, new ItemStack(ItemID.LARUPIA_COOKED, 1)),
	COOK_BARBTAILED_KEBBIT(ItemID.BARBKEBBIT_COOKED, "Cooked barb-tailed kebbit", 32, 106.0, ExperienceItem.RAW_BARBTAILED_KEBBIT, null, new ItemStack(ItemID.BARBKEBBIT_COOKED, 1)),
	COOK_GRAAHK(ItemID.GRAAHK_COOKED, "Cooked graahk", 41, 124, ExperienceItem.RAW_GRAAHK, null, new ItemStack(ItemID.GRAAHK_COOKED, 1)),
	COOK_KYATT(ItemID.KYATT_COOKED, "Cooked kyatt", 51, 143.0, ExperienceItem.RAW_KYATT, null, new ItemStack(ItemID.KYATT_COOKED, 1)),
	COOK_PYRE_FOX(ItemID.FENNECFOX_COOKED, "Cooked pyre fox", 59, 154.0, ExperienceItem.RAW_PYRE_FOX, null, new ItemStack(ItemID.FENNECFOX_COOKED, 1)),
	COOK_SUNLIGHT_ANTELOPE(ItemID.ANTELOPESUN_COOKED, "Cooked sunlight antelope", 68, 175.0, ExperienceItem.RAW_SUNLIGHT_ANTELOPE, null, new ItemStack(ItemID.ANTELOPESUN_COOKED, 1)),
	COOK_DASHING_KEBBIT(ItemID.DASHINGKEBBIT_COOKED, "Cooked dashing kebbit", 82, 215.0, ExperienceItem.RAW_DASHING_KEBBIT, null, new ItemStack(ItemID.DASHINGKEBBIT_COOKED, 1)),
	COOK_MOONLIGHT_ANTELOPE(ItemID.ANTELOPEMOON_COOKED, "Cooked moonlight antelope", 92, 220.0, ExperienceItem.RAW_MOONLIGHT_ANTELOPE, null, new ItemStack(ItemID.ANTELOPEMOON_COOKED, 1)),
	/**
	 * Crafting
	 */
	// Spinning
	BALL_OF_WOOL(ItemID.BALL_OF_WOOL, "Ball of wool", 1, 2.5, ExperienceItem.WOOL, null, new ItemStack(ItemID.BALL_OF_WOOL, 1)),
	BOW_STRING(ItemID.BOW_STRING, "Bow string", 1, 15, ExperienceItem.FLAX, null, new ItemStack(ItemID.BOW_STRING, 1)),
	// Glass Blowing
	BEER_GLASS(ItemID.BEER_GLASS, "Beer glass", 1, 17.5, ExperienceItem.MOLTEN_GLASS, null, new ItemStack(ItemID.BEER_GLASS, 1)),
	CANDLE_LANTERN(ItemID.CANDLE_LANTERN_UNLIT, "Candle lantern", 4, 19, ExperienceItem.MOLTEN_GLASS, null, new ItemStack(ItemID.CANDLE_LANTERN_UNLIT, 1)),
	OIL_LAMP(ItemID.OIL_LAMP_UNLIT, "Oil lamp", 12, 25, ExperienceItem.MOLTEN_GLASS, null, new ItemStack(ItemID.OIL_LAMP_UNLIT, 1)),
	VIAL(ItemID.VIAL_EMPTY, "Vial", 33, 35, ExperienceItem.MOLTEN_GLASS, null, new ItemStack(ItemID.VIAL_EMPTY, 1)),
	EMPTY_FISHBOWL(ItemID.FISHBOWL_EMPTY, "Empty fishbowl", 42, 42.5, ExperienceItem.MOLTEN_GLASS, null, new ItemStack(ItemID.FISHBOWL_EMPTY, 1)),
	UNPOWERED_ORB(ItemID.STAFFORB, "Unpowered orb", 46, 52.5, ExperienceItem.MOLTEN_GLASS, null, new ItemStack(ItemID.STAFFORB, 1)),
	LANTERN_LENS(ItemID.BULLSEYE_LANTERN_LENS, "Lantern lens", 49, 55, ExperienceItem.MOLTEN_GLASS, null, new ItemStack(ItemID.BULLSEYE_LANTERN_LENS, 1)),
	LIGHT_ORB(ItemID.DORGESH_LIGHT_BULB, "Light orb", 87, 70, ExperienceItem.MOLTEN_GLASS, null, new ItemStack(ItemID.DORGESH_LIGHT_BULB, 1)),
	// Regular Leather
	LEATHER(ItemID.LEATHER, "Tan Leather", 1, 0, ExperienceItem.COW_HIDE, Secondaries.COIN, new ItemStack(ItemID.LEATHER, 1)),
	LEATHER_GLOVES(ItemID.LEATHER_GLOVES, "Leather gloves", 1, 13.8, ExperienceItem.LEATHER, null, new ItemStack(ItemID.LEATHER_GLOVES, 1)),
	LEATHER_BOOTS(ItemID.LEATHER_BOOTS, "Leather boots", 7, 16.2, ExperienceItem.LEATHER, null, new ItemStack(ItemID.LEATHER_BOOTS, 1)),
	LEATHER_COWL(ItemID.LEATHER_COWL, "Leather cowl", 9, 18.5, ExperienceItem.LEATHER, null, new ItemStack(ItemID.LEATHER_COWL, 1)),
	LEATHER_VAMBRACES(ItemID.LEATHER_VAMBRACES, "Leather vambraces", 11, 22, ExperienceItem.LEATHER, null, new ItemStack(ItemID.LEATHER_VAMBRACES, 1)),
	LEATHER_BODY(ItemID.LEATHER_ARMOUR, "Leather body", 14, 25, ExperienceItem.LEATHER, null, new ItemStack(ItemID.LEATHER_ARMOUR, 1)),
	LEATHER_CHAPS(ItemID.LEATHER_CHAPS, "Leather chaps", 18, 27, ExperienceItem.LEATHER, null, new ItemStack(ItemID.LEATHER_CHAPS, 1)),
	COIF(ItemID.COIF, "Coif", 38, 37, ExperienceItem.LEATHER, null, new ItemStack(ItemID.COIF, 1)),
	// Hard Leather
	HARD_LEATHER(ItemID.HARD_LEATHER, "Tan Hard Leather", 1, 0, ExperienceItem.COW_HIDE, Secondaries.COIN_3, new ItemStack(ItemID.HARD_LEATHER, 1)),
	HARDLEATHER_BODY(ItemID.HARDLEATHER_BODY, "Hardleather body", 28, 35, ExperienceItem.HARD_LEATHER, null, new ItemStack(ItemID.HARDLEATHER_BODY, 1)),
	HARD_LEATHER_SHIELD(ItemID.LEATHER_SHIELD, "Hard leather shield", 41, 70, ExperienceItem.HARD_LEATHER, Secondaries.HARD_LEATHER_SHIELD, new ItemStack(ItemID.LEATHER_SHIELD, 1)),
	// Studded
	STUDDED_BODY(ItemID.STUDDED_BODY, "Studded chaps", 41, 40, ExperienceItem.STEEL_STUDS, Secondaries.LEATHER_BODY, new ItemStack(ItemID.STUDDED_BODY, 1)),
	STUDDED_CHAPS(ItemID.STUDDED_CHAPS, "Studded chaps", 44, 42, ExperienceItem.STEEL_STUDS, Secondaries.LEATHER_CHAPS, new ItemStack(ItemID.STUDDED_CHAPS, 1)),
	// D'hide/Dragon Leather
	GREEN_DRAGONHIDE(ItemID.DRAGON_LEATHER, "Tan Green D'hide", 57, 0, ExperienceItem.GREEN_DRAGONHIDE, null, new ItemStack(ItemID.DRAGON_LEATHER, 1)),
	BLUE_DRAGONHIDE(ItemID.DRAGON_LEATHER_BLUE, "Tan Blue D'hide", 66, 0, ExperienceItem.BLUE_DRAGONHIDE, null, new ItemStack(ItemID.DRAGON_LEATHER_BLUE, 1)),
	RED_DRAGONHIDE(ItemID.DRAGON_LEATHER_RED, "Tan Red D'hide", 73, 0, ExperienceItem.RED_DRAGONHIDE, null, new ItemStack(ItemID.DRAGON_LEATHER_RED, 1)),
	BLACK_DRAGONHIDE(ItemID.DRAGON_LEATHER_BLACK, "Tan Black D'hide", 79, 0, ExperienceItem.BLACK_DRAGONHIDE, null, new ItemStack(ItemID.DRAGON_LEATHER_BLACK, 1)),
	GREEN_DRAGON_LEATHER(ItemID.DRAGON_VAMBRACES, "Green D'hide product", 57, 62.0, ExperienceItem.GREEN_DRAGON_LEATHER, null, null),
	BLUE_DRAGON_LEATHER(ItemID.BLUE_DRAGON_VAMBRACES, "Blue D'hide product", 66, 70.0, ExperienceItem.BLUE_DRAGON_LEATHER, null, null),
	RED_DRAGON_LEATHER(ItemID.RED_DRAGON_VAMBRACES, "Red D'hide product", 73, 78.0, ExperienceItem.RED_DRAGON_LEATHER, null, null),
	BLACK_DRAGON_LEATHER(ItemID.BLACK_DRAGON_VAMBRACES, "Black D'hide product", 79, 86.0, ExperienceItem.BLACK_DRAGON_LEATHER, null, null),
	// Uncut Gems
	UNCUT_OPAL(ItemID.OPAL, "Cut opal", 1, 15.0, ExperienceItem.UNCUT_OPAL, null, new ItemStack(ItemID.OPAL, 1)),
	UNCUT_JADE(ItemID.JADE, "Cut jade", 13, 20.0, ExperienceItem.UNCUT_JADE, null, new ItemStack(ItemID.JADE, 1)),
	UNCUT_RED_TOPAZ(ItemID.RED_TOPAZ, "Cut red topaz", 16, 25.0, ExperienceItem.UNCUT_RED_TOPAZ, null, new ItemStack(ItemID.RED_TOPAZ, 1)),
	UNCUT_SAPPHIRE(ItemID.SAPPHIRE, "Cut sapphire", 20, 50.0, ExperienceItem.UNCUT_SAPPHIRE, null, new ItemStack(ItemID.SAPPHIRE, 1)),
	UNCUT_EMERALD(ItemID.EMERALD, "Cut emerald", 27, 67.5, ExperienceItem.UNCUT_EMERALD, null, new ItemStack(ItemID.EMERALD, 1)),
	UNCUT_RUBY(ItemID.RUBY, "Cut ruby", 34, 85, ExperienceItem.UNCUT_RUBY, null, new ItemStack(ItemID.RUBY, 1)),
	UNCUT_DIAMOND(ItemID.DIAMOND, "Cut diamond", 43, 107.5, ExperienceItem.UNCUT_DIAMOND, null, new ItemStack(ItemID.DIAMOND, 1)),
	UNCUT_DRAGONSTONE(ItemID.DRAGONSTONE, "Cut dragonstone", 55, 137.5, ExperienceItem.UNCUT_DRAGONSTONE, null, new ItemStack(ItemID.DRAGONSTONE, 1)),
	UNCUT_ONYX(ItemID.ONYX, "Cut onyx", 67, 167.5, ExperienceItem.UNCUT_ONYX, null, new ItemStack(ItemID.ONYX, 1)),
	UNCUT_ZENYTE(ItemID.ZENYTE, "Cut zenyte", 89, 200.0, ExperienceItem.UNCUT_ZENYTE, null, new ItemStack(ItemID.ZENYTE, 1)),
	// Silver Jewelery
	OPAL_RING(ItemID.OPAL_RING, "Opal ring", 1, 10, ExperienceItem.OPAL, Secondaries.SILVER_BAR, new ItemStack(ItemID.OPAL_RING, 1)),
	OPAL_NECKLACE(ItemID.OPAL_NECKLACE, "Opal necklace", 16, 35, ExperienceItem.OPAL, Secondaries.SILVER_BAR, new ItemStack(ItemID.OPAL_NECKLACE, 1)),
	UNSTRUNG_SYMBOL(ItemID.NOSTRINGSTAR, "Unstrung Symbol", 16, 50, ExperienceItem.SILVER_BAR, null, new ItemStack(ItemID.NOSTRINGSTAR, 1)),
	UNSTRUNG_EMBLEM(ItemID.NOSTRINGSNAKE, "Unstrung Emblem", 17, 50, ExperienceItem.SILVER_BAR, null, new ItemStack(ItemID.NOSTRINGSNAKE, 1)),
	SILVER_SICKLE(ItemID.SILVER_SICKLE, "Silver Sickel", 18, 50, ExperienceItem.SILVER_BAR, null, new ItemStack(ItemID.SILVER_SICKLE, 1)),
	OPAL_BRACELET(ItemID.OPAL_BRACELET, "Opal bracelet", 22, 45, ExperienceItem.OPAL, Secondaries.SILVER_BAR, new ItemStack(ItemID.OPAL_BRACELET, 1)),
	TIARA(ItemID.TIARA, "Tiara", 23, 52.5, ExperienceItem.SILVER_BAR, null, new ItemStack(ItemID.TIARA, 1)),
	OPAL_AMULET_U(ItemID.STRUNG_OPAL_AMULET, "Opal amulet (u)", 27, 55, ExperienceItem.OPAL, Secondaries.SILVER_BAR, new ItemStack(ItemID.UNSTRUNG_OPAL_AMULET, 1)),
	JADE_RING(ItemID.JADE_RING, "Jade ring", 13, 32, ExperienceItem.JADE, Secondaries.SILVER_BAR, new ItemStack(ItemID.JADE_RING, 1)),
	JADE_NECKLACE(ItemID.JADE_NECKLACE, "Jade necklace", 25, 54, ExperienceItem.JADE, Secondaries.SILVER_BAR, new ItemStack(ItemID.JADE_NECKLACE, 1)),
	JADE_BRACELET(ItemID.JADE_BRACELET, "Jade bracelet", 29, 60, ExperienceItem.JADE, Secondaries.SILVER_BAR, new ItemStack(ItemID.JADE_BRACELET, 1)),
	JADE_AMULET_U(ItemID.STRUNG_JADE_AMULET, "Jade amulet (u)", 34, 70, ExperienceItem.JADE, Secondaries.SILVER_BAR, new ItemStack(ItemID.UNSTRUNG_JADE_AMULET, 1)),
	TOPAZ_RING(ItemID.TOPAZ_RING, "Topaz ring", 16, 35, ExperienceItem.RED_TOPAZ, Secondaries.SILVER_BAR, new ItemStack(ItemID.TOPAZ_RING, 1)),
	TOPAZ_NECKLACE(ItemID.TOPAZ_NECKLACE, "Topaz necklace", 32, 70, ExperienceItem.RED_TOPAZ, Secondaries.SILVER_BAR, new ItemStack(ItemID.TOPAZ_NECKLACE, 1)),
	TOPAZ_BRACELET(ItemID.TOPAZ_BRACELET, "Topaz bracelet", 38, 75, ExperienceItem.RED_TOPAZ, Secondaries.SILVER_BAR, new ItemStack(ItemID.TOPAZ_BRACELET, 1)),
	TOPAZ_AMULET_U(ItemID.STRUNG_TOPAZ_AMULET, "Topaz amulet (u)", 45, 80, ExperienceItem.RED_TOPAZ, Secondaries.SILVER_BAR, new ItemStack(ItemID.UNSTRUNG_TOPAZ_AMULET, 1)),
	// Gold Jewelery
	SAPPHIRE_RING(ItemID.SAPPHIRE_RING, "Sapphire ring", 20, 40, ExperienceItem.SAPPHIRE, Secondaries.GOLD_BAR, new ItemStack(ItemID.SAPPHIRE_RING, 1)),
	SAPPHIRE_NECKLACE(ItemID.SAPPHIRE_NECKLACE, "Sapphire necklace", 22, 55, ExperienceItem.SAPPHIRE, Secondaries.GOLD_BAR, new ItemStack(ItemID.SAPPHIRE_NECKLACE, 1)),
	SAPPHIRE_BRACELET(ItemID.JEWL_SAPPHIRE_BRACELET, "Sapphire bracelet", 23, 60, ExperienceItem.SAPPHIRE, Secondaries.GOLD_BAR, new ItemStack(ItemID.JEWL_SAPPHIRE_BRACELET, 1)),
	SAPPHIRE_AMULET_U(ItemID.STRUNG_SAPPHIRE_AMULET, "Sapphire amulet (u)", 24, 65, ExperienceItem.SAPPHIRE, Secondaries.GOLD_BAR, new ItemStack(ItemID.UNSTRUNG_SAPPHIRE_AMULET, 1)),
	EMERALD_RING(ItemID.EMERALD_RING, "Emerald ring", 27, 55, ExperienceItem.EMERALD, Secondaries.GOLD_BAR, new ItemStack(ItemID.EMERALD_RING, 1)),
	EMERALD_NECKLACE(ItemID.EMERALD_NECKLACE, "Emerald necklace", 29, 60, ExperienceItem.EMERALD, Secondaries.GOLD_BAR, new ItemStack(ItemID.EMERALD_NECKLACE, 1)),
	EMERALD_BRACELET(ItemID.JEWL_EMERALD_BRACELET, "Emerald bracelet", 30, 65, ExperienceItem.EMERALD, Secondaries.GOLD_BAR, new ItemStack(ItemID.JEWL_EMERALD_BRACELET, 1)),
	EMERALD_AMULET_U(ItemID.STRUNG_EMERALD_AMULET, "Emerald amulet (u)", 31, 70, ExperienceItem.EMERALD, Secondaries.GOLD_BAR, new ItemStack(ItemID.UNSTRUNG_EMERALD_AMULET, 1)),
	RUBY_RING(ItemID.RUBY_RING, "Ruby ring", 34, 70, ExperienceItem.RUBY, Secondaries.GOLD_BAR, new ItemStack(ItemID.RUBY_RING, 1)),
	RUBY_NECKLACE(ItemID.RUBY_NECKLACE, "Ruby necklace", 40, 75, ExperienceItem.RUBY, Secondaries.GOLD_BAR, new ItemStack(ItemID.RUBY_NECKLACE, 1)),
	RUBY_BRACELET(ItemID.JEWL_RUBY_BRACELET, "Ruby bracelet", 42, 80, ExperienceItem.RUBY, Secondaries.GOLD_BAR, new ItemStack(ItemID.JEWL_RUBY_BRACELET, 1)),
	RUBY_AMULET_U(ItemID.STRUNG_RUBY_AMULET, "Ruby amulet (u)", 50, 85, ExperienceItem.RUBY, Secondaries.GOLD_BAR, new ItemStack(ItemID.UNSTRUNG_RUBY_AMULET, 1)),
	DIAMOND_RING(ItemID.DIAMOND_RING, "Diamond ring", 43, 85, ExperienceItem.DIAMOND, Secondaries.GOLD_BAR, new ItemStack(ItemID.DIAMOND_RING, 1)),
	DIAMOND_NECKLACE(ItemID.DIAMOND_NECKLACE, "Diamond necklace", 56, 90, ExperienceItem.DIAMOND, Secondaries.GOLD_BAR, new ItemStack(ItemID.DIAMOND_NECKLACE, 1)),
	DIAMOND_BRACELET(ItemID.JEWL_DIAMOND_BRACELET, "Diamond bracelet", 58, 95, ExperienceItem.DIAMOND, Secondaries.GOLD_BAR, new ItemStack(ItemID.JEWL_DIAMOND_BRACELET, 1)),
	DIAMOND_AMULET_U(ItemID.STRUNG_DIAMOND_AMULET, "Diamond amulet (u)", 70, 100, ExperienceItem.DIAMOND, Secondaries.GOLD_BAR, new ItemStack(ItemID.UNSTRUNG_DIAMOND_AMULET, 1)),
	DRAGONSTONE_RING(ItemID.DRAGONSTONE_RING, "Dragonstone ring", 55, 100, ExperienceItem.DRAGONSTONE, Secondaries.GOLD_BAR, new ItemStack(ItemID.DRAGONSTONE_RING, 1)),
	DRAGON_NECKLACE(ItemID.DRAGONSTONE_NECKLACE, "Dragon necklace", 72, 105, ExperienceItem.DRAGONSTONE, Secondaries.GOLD_BAR, new ItemStack(ItemID.DRAGONSTONE_NECKLACE, 1)),
	DRAGONSTONE_BRACELET(ItemID.JEWL_DRAGONSTONE_BRACELET, "Dragonstone bracelet", 74, 110, ExperienceItem.DRAGONSTONE, Secondaries.GOLD_BAR, new ItemStack(ItemID.JEWL_DRAGONSTONE_BRACELET, 1)),
	DRAGONSTONE_AMULET_U(ItemID.STRUNG_DRAGONSTONE_AMULET, "Dragonstone amulet (u)", 80, 150, ExperienceItem.DRAGONSTONE, Secondaries.GOLD_BAR, new ItemStack(ItemID.UNSTRUNG_DRAGONSTONE_AMULET, 1)),
	ONYX_RING(ItemID.ONYX_RING, "Onyx ring", 67, 115, ExperienceItem.ONYX, Secondaries.GOLD_BAR, new ItemStack(ItemID.ONYX_RING, 1)),
	ONYX_NECKLACE(ItemID.ONYX_NECKLACE, "Onyx necklace", 82, 120, ExperienceItem.ONYX, Secondaries.GOLD_BAR, new ItemStack(ItemID.ONYX_NECKLACE, 1)),
	REGEN_BRACELET(ItemID.JEWL_BRACELET_REGEN, "Regen bracelet", 84, 125, ExperienceItem.ONYX, Secondaries.GOLD_BAR, new ItemStack(ItemID.JEWL_BRACELET_REGEN, 1)),
	ONYX_AMULET_U(ItemID.STRUNG_ONYX_AMULET, "Onyx amulet (u)", 90, 165, ExperienceItem.ONYX, Secondaries.GOLD_BAR, new ItemStack(ItemID.UNSTRUNG_ONYX_AMULET, 1)),
	ZENYTE_RING(ItemID.ZENYTE_RING, "Zenyte ring", 89, 150, ExperienceItem.ZENYTE, Secondaries.GOLD_BAR, new ItemStack(ItemID.ZENYTE_RING, 1)),
	ZENYTE_NECKLACE(ItemID.ZENYTE_NECKLACE, "Zenyte necklace", 92, 165, ExperienceItem.ZENYTE, Secondaries.GOLD_BAR, new ItemStack(ItemID.ZENYTE_NECKLACE, 1)),
	ZENYTE_BRACELET(ItemID.ZENYTE_BRACELET, "Zenyte bracelet", 95, 180, ExperienceItem.ZENYTE, Secondaries.GOLD_BAR, new ItemStack(ItemID.ZENYTE_BRACELET, 1)),
	ZENYTE_AMULET_U(ItemID.ZENYTE_AMULET, "Zenyte amulet (u)", 98, 200, ExperienceItem.ZENYTE, Secondaries.GOLD_BAR, new ItemStack(ItemID.UNSTRUNG_ZENYTE_AMULET, 1)),
	// Battle Staves
	WATER_BATTLESTAFF(ItemID.WATER_BATTLESTAFF, "Water battlestaff", 54, 100, ExperienceItem.BATTLESTAFF, Secondaries.WATER_ORB, new ItemStack(ItemID.WATER_BATTLESTAFF, 1)),
	EARTH_BATTLESTAFF(ItemID.EARTH_BATTLESTAFF, "Earth battlestaff", 58, 112.5, ExperienceItem.BATTLESTAFF, Secondaries.EARTH_ORB, new ItemStack(ItemID.EARTH_BATTLESTAFF, 1)),
	FIRE_BATTLESTAFF(ItemID.FIRE_BATTLESTAFF, "Fire battlestaff", 62, 125, ExperienceItem.BATTLESTAFF, Secondaries.FIRE_ORB, new ItemStack(ItemID.FIRE_BATTLESTAFF, 1)),
	AIR_BATTLESTAFF(ItemID.AIR_BATTLESTAFF, "Air battlestaff", 66, 137.5, ExperienceItem.BATTLESTAFF, Secondaries.AIR_ORB, new ItemStack(ItemID.AIR_BATTLESTAFF, 1)),
	// Gold Jewelery
	GOLD_RING(ItemID.GOLD_RING, "Gold ring", 5, 15, ExperienceItem.GOLD_BAR, null, new ItemStack(ItemID.GOLD_RING, 1)),
	GOLD_NECKLACE(ItemID.GOLD_NECKLACE, "Gold necklace", 6, 20, ExperienceItem.GOLD_BAR, null, new ItemStack(ItemID.GOLD_NECKLACE, 1)),
	GOLD_BRACELET(ItemID.JEWL_GOLD_BRACELET, "Gold bracelet", 7, 25, ExperienceItem.GOLD_BAR, null, new ItemStack(ItemID.JEWL_GOLD_BRACELET, 1)),
	GOLD_AMULET_U(ItemID.UNSTRUNG_GOLD_AMULET, "Gold amulet (u)", 8, 30, ExperienceItem.GOLD_BAR, null, new ItemStack(ItemID.UNSTRUNG_GOLD_AMULET, 1)),
	GOLD_TIARA(ItemID.TIARA_GOLD, "Gold tiara", 42, 35, ExperienceItem.GOLD_BAR, null, new ItemStack(ItemID.TIARA_GOLD, 1)),
	//Amethyst
	AMETHYST_BOLT_TIPS(ItemID.XBOWS_BOLT_TIPS_AMETHYST, "Amethyst bolt tips", 83, 60, ExperienceItem.AMETHYST, null, new ItemStack(ItemID.XBOWS_BOLT_TIPS_AMETHYST, 15)),
	AMETHYST_ARROWTIPS(ItemID.AMETHYST_ARROWHEADS, "Amethyst arrowtips", 85, 60, ExperienceItem.AMETHYST, null, new ItemStack(ItemID.AMETHYST_ARROWHEADS, 15)),
	AMETHYST_JAVELIN_HEADS(ItemID.AMETHYST_JAVELIN_HEAD, "Amethyst javelin heads", 87, 60, ExperienceItem.AMETHYST, null, new ItemStack(ItemID.XBOWS_BOLT_TIPS_AMETHYST, 5)),
	AMETHYST_DART_TIP(ItemID.AMETHYST_DART_TIP, "Amethyst dart tips", 89, 60, ExperienceItem.AMETHYST, null, new ItemStack(ItemID.XBOWS_BOLT_TIPS_AMETHYST, 8)),
	// RNG section
	// Soda Ash
	MOLTEN_GLASS(ItemID.MOLTEN_GLASS, "Furnace", 1, 20, ExperienceItem.SODA_ASH, Secondaries.BUCKET_OF_SAND, new ItemStack(ItemID.MOLTEN_GLASS, 1)),
	MOLTEN_GLASS_SPELL(ItemID.MOLTEN_GLASS, "SGM [1.3x]", 1, 10, true, ExperienceItem.SODA_ASH, Secondaries.BUCKET_OF_SAND, new ItemStack(ItemID.MOLTEN_GLASS, 1.3)),
	// Seaweed
	SODA_ASH(ItemID.SODA_ASH, "Soda Ash", 1, 0, ExperienceItem.SEAWEED, null, new ItemStack(ItemID.SODA_ASH, 1)),
	S_MOLTEN_GLASS_SPELL(ItemID.MOLTEN_GLASS, "SGM [1.3x]", 1, 10, true, ExperienceItem.SEAWEED, Secondaries.BUCKET_OF_SAND, new ItemStack(ItemID.MOLTEN_GLASS, 1.3)),
	// Giant Seaweed
	G_SODA_ASH(ItemID.SODA_ASH, "Soda Ash", 1, 0, ExperienceItem.GIANT_SEAWEED, null, new ItemStack(ItemID.SODA_ASH, 6)),
	// XP per seaweed
	MOLTEN_GLASS_SPELL_18_PICKUP(ItemID.MOLTEN_GLASS, "SGM 18:3 Pickup [1.6x]", 1, 60, true, ExperienceItem.GIANT_SEAWEED, Secondaries.BUCKET_OF_SAND_6, new ItemStack(ItemID.MOLTEN_GLASS, 9.6)),
	MOLTEN_GLASS_SPELL_18(ItemID.MOLTEN_GLASS, "SGM 18:3 [1.45x]", 1, 60, true, ExperienceItem.GIANT_SEAWEED, Secondaries.BUCKET_OF_SAND_6, new ItemStack(ItemID.MOLTEN_GLASS, 8.7)),
	MOLTEN_GLASS_SPELL_12(ItemID.MOLTEN_GLASS, "SGM 12:2 [1.45x]", 1, 60, true, ExperienceItem.GIANT_SEAWEED, Secondaries.BUCKET_OF_SAND_6, new ItemStack(ItemID.MOLTEN_GLASS, 8.7)),
	// String Jewlery
	UNBLESSED_SYMBOL(ItemID.STRINGSTAR, "String blessed symbol", 1, 4, ExperienceItem.UNSTRUNG_SYMBOL, Secondaries.BALL_OF_WOOL, new ItemStack(ItemID.STRINGSTAR, 1)),
	UNPOWERED_SYMBOL(ItemID.STRINGSTAR, "String emblem", 17, 4, ExperienceItem.UNSTRUNG_EMBLEM, Secondaries.BALL_OF_WOOL, new ItemStack(ItemID.STRINGSTAR, 1)),
	GOLD_AMULET(ItemID.STRUNG_GOLD_AMULET, "String gold amulet", 1, 4, ExperienceItem.GOLD_AMULET_U, Secondaries.BALL_OF_WOOL, new ItemStack(ItemID.STRUNG_GOLD_AMULET, 1)),
	OPAL_AMULET(ItemID.STRUNG_OPAL_AMULET, "String opal amulet", 1, 4, ExperienceItem.OPAL_AMULET_U, Secondaries.BALL_OF_WOOL, new ItemStack(ItemID.STRUNG_OPAL_AMULET, 1)),
	JADE_AMULET(ItemID.STRUNG_JADE_AMULET, "String jade amulet", 1, 4, ExperienceItem.JADE_AMULET_U, Secondaries.BALL_OF_WOOL, new ItemStack(ItemID.STRUNG_JADE_AMULET, 1)),
	TOPAZ_AMULET(ItemID.STRUNG_TOPAZ_AMULET, "String topaz amulet", 1, 4, ExperienceItem.TOPAZ_AMULET_U, Secondaries.BALL_OF_WOOL, new ItemStack(ItemID.STRUNG_TOPAZ_AMULET, 1)),
	SAPPHIRE_AMULET(ItemID.STRUNG_SAPPHIRE_AMULET, "String sapphire amulet", 1, 4, ExperienceItem.SAPPHIRE_AMULET_U, Secondaries.BALL_OF_WOOL, new ItemStack(ItemID.STRUNG_SAPPHIRE_AMULET, 1)),
	EMERALD_AMULET(ItemID.STRUNG_EMERALD_AMULET, "String emerald amulet", 1, 4, ExperienceItem.EMERALD_AMULET_U, Secondaries.BALL_OF_WOOL, new ItemStack(ItemID.STRUNG_EMERALD_AMULET, 1)),
	RUBY_AMULET(ItemID.STRUNG_RUBY_AMULET, "String ruby amulet", 1, 4, ExperienceItem.RUBY_AMULET_U, Secondaries.BALL_OF_WOOL, new ItemStack(ItemID.STRUNG_RUBY_AMULET, 1)),
	DIAMOND_AMULET(ItemID.STRUNG_DIAMOND_AMULET, "String diamond amulet", 1, 4, ExperienceItem.DIAMOND_AMULET_U, Secondaries.BALL_OF_WOOL, new ItemStack(ItemID.STRUNG_DIAMOND_AMULET, 1)),
	DRAGONSTONE_AMULET(ItemID.STRUNG_DRAGONSTONE_AMULET, "String dragonstone amulet", 1, 4, ExperienceItem.DRAGONSTONE_AMULET_U, Secondaries.BALL_OF_WOOL, new ItemStack(ItemID.STRUNG_DRAGONSTONE_AMULET, 1)),
	ONYX_AMULET(ItemID.STRUNG_ONYX_AMULET, "String onyx amulet", 90, 165, ExperienceItem.ONYX_AMULET_U, Secondaries.BALL_OF_WOOL, new ItemStack(ItemID.STRUNG_ONYX_AMULET, 1)),
	ZENYTE_AMULET(ItemID.ZENYTE_AMULET, "String zenyte amulet", 98, 200, ExperienceItem.ZENYTE_AMULET_U, Secondaries.BALL_OF_WOOL, new ItemStack(ItemID.ZENYTE_AMULET, 1)),
	// Other
	CROSSBOW_STRING(ItemID.XBOWS_CROSSBOW_STRING, "Crossbow string", 10, 15, ExperienceItem.SINEW, null, new ItemStack(ItemID.XBOWS_CROSSBOW_STRING, 1)),
	PERFECT_SHELL(ItemID.DORGESH_TORTOISE_SHELL, "Perfect Shell", 1, 500, ExperienceItem.PERFECT_SHELL, null, null),
	/**
	 * Smithing
	 */
	// Smelting ores (Furnace)
	IRON_ORE(ItemID.IRON_BAR, "Iron bar", 15, 12.5, ExperienceItem.IRON_ORE, null, new ItemStack(ItemID.IRON_BAR, 1)),
	STEEL_ORE(ItemID.STEEL_BAR, "Steel bar", 30, 17.5, ExperienceItem.IRON_ORE, Secondaries.COAL_ORE_2, new ItemStack(ItemID.STEEL_BAR, 1)),
	SILVER_ORE(ItemID.SILVER_BAR, "Silver Bar", 20, 13.67, ExperienceItem.SILVER_ORE, null, new ItemStack(ItemID.SILVER_BAR, 1)),
	GOLD_ORE(ItemID.GOLD_BAR, "Gold bar", 40, 22.5, ExperienceItem.GOLD_ORE, null, new ItemStack(ItemID.GOLD_BAR, 1)),
	GOLD_ORE_GAUNTLETS(ItemID.GAUNTLETS_OF_GOLDSMITHING, "Goldsmith gauntlets", 40, 56.2, ExperienceItem.GOLD_ORE, null, new ItemStack(ItemID.GOLD_BAR, 1)),
	MITHRIL_ORE(ItemID.MITHRIL_BAR, "Mithril bar", 50, 30, ExperienceItem.MITHRIL_ORE, Secondaries.COAL_ORE_4, new ItemStack(ItemID.MITHRIL_BAR, 1)),
	ADAMANTITE_ORE(ItemID.ADAMANTITE_BAR, "Adamantite bar", 70, 37.5, ExperienceItem.ADAMANTITE_ORE, Secondaries.COAL_ORE_6, new ItemStack(ItemID.ADAMANTITE_BAR, 1)),
	RUNITE_ORE(ItemID.RUNITE_BAR, "Runite bar", 85, 50, ExperienceItem.RUNITE_ORE, Secondaries.COAL_ORE_8, new ItemStack(ItemID.RUNITE_BAR, 1)),
	// Blast Furnace
	BF_STEEL_ORE(ItemID.STEEL_BAR, "BF Steel Bar", 30, 17.5, ExperienceItem.IRON_ORE, Secondaries.COAL_ORE, new ItemStack(ItemID.STEEL_BAR, 1)),
	BF_MITHRIL_ORE(ItemID.MITHRIL_BAR, "BF Mithril Bar", 50, 30, ExperienceItem.MITHRIL_ORE, Secondaries.COAL_ORE_2, new ItemStack(ItemID.MITHRIL_BAR, 1)),
	BF_ADAMANTITE_ORE(ItemID.ADAMANTITE_BAR, "BF Adamantite bar", 70, 37.5, ExperienceItem.ADAMANTITE_ORE, Secondaries.COAL_ORE_3, new ItemStack(ItemID.ADAMANTITE_BAR, 1)),
	BF_RUNITE_ORE(ItemID.RUNITE_BAR, "BF Runite bar", 85, 50, ExperienceItem.RUNITE_ORE, Secondaries.COAL_ORE_4, new ItemStack(ItemID.RUNITE_BAR, 1)),
	// Smelting bars (Anvil)
	BRONZE_BAR(ItemID.BRONZE_BAR, "Bronze products", 1, 12.5, ExperienceItem.BRONZE_BAR, null, null),
	IRON_BAR(ItemID.IRON_BAR, "Iron products", 15, 25.0, ExperienceItem.IRON_BAR, null, null),
	STEEL_BAR(ItemID.STEEL_BAR, "Steel products", 30, 37.5, ExperienceItem.STEEL_BAR, null, null),
	CANNONBALLS(ItemID.MCANNONBALL, "Cannonballs", 35, 25.5, ExperienceItem.STEEL_BAR, null, new ItemStack(ItemID.MCANNONBALL, 4)),
	MITHRIL_BAR(ItemID.MITHRIL_BAR, "Mithril products", 50, 50.0, ExperienceItem.MITHRIL_BAR, null, null),
	ADAMANTITE_BAR(ItemID.ADAMANTITE_BAR, "Adamantite products", 70, 62.5, ExperienceItem.ADAMANTITE_BAR, null, null),
	RUNITE_BAR(ItemID.RUNITE_BAR, "Runite products", 85, 75.0, ExperienceItem.RUNITE_BAR, null, null),
	/**
	 * Farming
	 */
	ACORN(ItemID.PLANTPOT_OAK_SAPLING, "Oak sapling", 15, 0, ExperienceItem.ACORN, null, new ItemStack(ItemID.PLANTPOT_OAK_SAPLING, 1)),
	WILLOW_SEED(ItemID.PLANTPOT_WILLOW_SAPLING, "Willow sapling", 30, 0, ExperienceItem.WILLOW_SEED, null, new ItemStack(ItemID.PLANTPOT_WILLOW_SAPLING, 1)),
	MAPLE_SEED(ItemID.PLANTPOT_MAPLE_SAPLING, "Maple sapling", 45, 0, ExperienceItem.MAPLE_SEED, null, new ItemStack(ItemID.PLANTPOT_MAPLE_SAPLING, 1)),
	YEW_SEED(ItemID.PLANTPOT_YEW_SAPLING, "Yew sapling", 60, 0, ExperienceItem.YEW_SEED, null, new ItemStack(ItemID.PLANTPOT_YEW_SAPLING, 1)),
	MAGIC_SEED(ItemID.PLANTPOT_MAGIC_TREE_SAPLING, "Magic sapling", 75, 0, ExperienceItem.MAGIC_SEED, null, new ItemStack(ItemID.PLANTPOT_MAGIC_TREE_SAPLING, 1)),
	APPLE_TREE_SEED(ItemID.PLANTPOT_APPLE_SAPLING, "Apple sapling", 27, 0, ExperienceItem.APPLE_TREE_SEED, null, new ItemStack(ItemID.PLANTPOT_APPLE_SAPLING, 1)),
	BANANA_TREE_SEED(ItemID.PLANTPOT_BANANA_SAPLING, "Banana sapling", 33, 0, ExperienceItem.BANANA_TREE_SEED, null, new ItemStack(ItemID.PLANTPOT_BANANA_SAPLING, 1)),
	ORANGE_TREE_SEED(ItemID.PLANTPOT_ORANGE_SAPLING, "Orange sapling", 39, 0, ExperienceItem.ORANGE_TREE_SEED, null, new ItemStack(ItemID.PLANTPOT_ORANGE_SAPLING, 1)),
	CURRY_TREE_SEED(ItemID.PLANTPOT_CURRY_SAPLING, "Curry sapling", 42, 0, ExperienceItem.CURRY_TREE_SEED, null, new ItemStack(ItemID.PLANTPOT_CURRY_SAPLING, 1)),
	PINEAPPLE_SEED(ItemID.PLANTPOT_PINEAPPLE_SAPLING, "Pineapple sapling", 51, 0, ExperienceItem.PINEAPPLE_SEED, null, new ItemStack(ItemID.PLANTPOT_PINEAPPLE_SAPLING, 1)),
	PAPAYA_TREE_SEED(ItemID.PLANTPOT_PAPAYA_SAPLING, "Papaya sapling", 57, 0, ExperienceItem.PAPAYA_TREE_SEED, null, new ItemStack(ItemID.PLANTPOT_PAPAYA_SAPLING, 1)),
	PALM_TREE_SEED(ItemID.PLANTPOT_PALM_SAPLING, "Palm sapling", 68, 0, ExperienceItem.PALM_TREE_SEED, null, new ItemStack(ItemID.PLANTPOT_PALM_SAPLING, 1)),
	CALQUAT_TREE_SEED(ItemID.PLANTPOT_CALQUAT_SAPLING, "Calquat sapling", 72, 0, ExperienceItem.CALQUAT_TREE_SEED, null, new ItemStack(ItemID.PLANTPOT_CALQUAT_SAPLING, 1)),
	TEAK_SEED(ItemID.PLANTPOT_TEAK_SAPLING, "Teak sapling", 35, 0, ExperienceItem.TEAK_SEED, null, new ItemStack(ItemID.PLANTPOT_TEAK_SAPLING, 1)),
	MAHOGANY_SEED(ItemID.PLANTPOT_MAHOGANY_SAPLING, "Mahogany sapling", 55, 0, ExperienceItem.MAHOGANY_SEED, null, new ItemStack(ItemID.PLANTPOT_MAHOGANY_SAPLING, 1)),
	SPIRIT_SEED(ItemID.PLANTPOT_SPIRIT_TREE_SAPLING, "Spirit sapling", 83, 0, ExperienceItem.SPIRIT_SEED, null, new ItemStack(ItemID.PLANTPOT_SPIRIT_TREE_SAPLING, 1)),
	DRAGONFRUIT_TREE_SEED(ItemID.DRAGONFRUIT_TREE_SEED, "Dragonfruit sapling", 81, 0, ExperienceItem.DRAGONFRUIT_TREE_SEED, null, new ItemStack(ItemID.PLANTPOT_DRAGONFRUIT_SAPLING, 1)),
	CELASTRUS_SEED(ItemID.CELASTRUS_TREE_SEED, "Celastrus sapling", 85, 0, ExperienceItem.CELASTRUS_SEED, null, new ItemStack(ItemID.PLANTPOT_CELASTRUS_TREE_SAPLING, 1)),
	REDWOOD_TREE_SEED(ItemID.REDWOOD_TREE_SEED, "Redwood sapling", 90, 0, ExperienceItem.REDWOOD_TREE_SEED, null, new ItemStack(ItemID.PLANTPOT_REDWOOD_TREE_SAPLING, 1)),
	CRYSTAL_ACORN(ItemID.CRYSTAL_TREE_SEED, "Crystal sapling", 74, 0, ExperienceItem.CRYSTAL_ACORN, null, new ItemStack(ItemID.PLANTPOT_CRYSTAL_TREE_SAPLING, 1)),
	HESPORI_SEED(ItemID.HESPORI_SEED, "Hespori", 65, 12662, ExperienceItem.HESPORI_SEED, null, null),

	OAK_SAPPLING(ItemID.PLANTPOT_OAK_SAPLING, "Oak tree", 15, 481.3, ExperienceItem.OAK_SAPLING, null, null),
	WILLOW_SAPLING(ItemID.PLANTPOT_WILLOW_SAPLING, "Willow tree", 30, 1481.5, ExperienceItem.WILLOW_SAPLING, null, null),
	MAPLE_SAPLING(ItemID.PLANTPOT_MAPLE_SAPLING, "Maple tree", 45, 3448.4, ExperienceItem.MAPLE_SAPLING, null, null),
	YEW_SAPLING(ItemID.PLANTPOT_YEW_SAPLING, "Yew tree", 60, 7150.9, ExperienceItem.YEW_SAPLING, null, null),
	MAGIC_SAPLING(ItemID.PLANTPOT_MAGIC_TREE_SAPLING, "Magic tree", 75, 13913.8, ExperienceItem.MAGIC_SAPLING, null, null),
	APPLE_TREE_SAPLING(ItemID.PLANTPOT_APPLE_SAPLING, "Apple tree", 27, 1272.5, ExperienceItem.APPLE_TREE_SAPLING, null, null),
	BANANA_TREE_SAPLING(ItemID.PLANTPOT_BANANA_SAPLING, "Banana tree", 33, 1841.5, ExperienceItem.BANANA_TREE_SAPLING, null, null),
	ORANGE_TREE_SAPLING(ItemID.PLANTPOT_ORANGE_SAPLING, "Orange tree", 39, 2586.7, ExperienceItem.ORANGE_TREE_SAPLING, null, null),
	CURRY_TREE_SAPLING(ItemID.PLANTPOT_CURRY_SAPLING, "Curry tree", 42, 3036.9, ExperienceItem.CURRY_TREE_SAPLING, null, null),
	PINEAPPLE_SAPLING(ItemID.PLANTPOT_PINEAPPLE_SAPLING, "Pineapple tree", 51, 4791.7, ExperienceItem.PINEAPPLE_SAPLING, null, null),
	PAPAYA_TREE_SAPLING(ItemID.PLANTPOT_PAPAYA_SAPLING, "Papaya tree", 57, 6380.4, ExperienceItem.PAPAYA_TREE_SAPLING, null, null),
	PALM_TREE_SAPLING(ItemID.PLANTPOT_PALM_SAPLING, "Palm tree", 68, 10509.6, ExperienceItem.PALM_TREE_SAPLING, null, null),
	CALQUAT_TREE_SAPLING(ItemID.PLANTPOT_CALQUAT_SAPLING, "Calquat tree", 72, 12516.5, ExperienceItem.CALQUAT_TREE_SAPLING, null, null),
	TEAK_SAPLING(ItemID.PLANTPOT_TEAK_SAPLING, "Teak tree", 35, 7325, ExperienceItem.TEAK_SAPLING, null, null),
	MAHOGANY_SAPLING(ItemID.PLANTPOT_MAHOGANY_SAPLING, "Mahogany tree", 55, 15783, ExperienceItem.MAHOGANY_SAPLING, null, null),
	SPIRIT_SAPLING(ItemID.PLANTPOT_SPIRIT_TREE_SAPLING, "Spirit tree", 83, 19500, ExperienceItem.SPIRIT_SAPLING, null, null),
	DRAGONFRUIT_SAPLING(ItemID.PLANTPOT_DRAGONFRUIT_SAPLING, "Dragonfruit tree", 81, 17825, ExperienceItem.DRAGONFRUIT_SAPLING, null, null),
	CELASTRUS_SAPLING(ItemID.PLANTPOT_CELASTRUS_TREE_SAPLING, "Celastrus tree", 85, 14404.5, ExperienceItem.CELASTRUS_SAPLING, null, null),
	REDWOOD_SAPLING(ItemID.PLANTPOT_REDWOOD_TREE_SAPLING, "Redwood tree", 90, 22680, ExperienceItem.REDWOOD_SAPLING, null, null),
	CRYSTAL_SAPLING(ItemID.PLANTPOT_CRYSTAL_TREE_SAPLING, "Crystal tree", 74, 13366, ExperienceItem.CRYSTAL_SAPLING, null, null),

	/**
	 * Fletching
	 */
	// General
	F_HEADLESS_ARROWS(ItemID.HEADLESS_ARROW, "Headless arrow", 1, 1, ExperienceItem.F_ARROW_SHAFT, Secondaries.FEATHER, new ItemStack(ItemID.HEADLESS_ARROW, 1)),
	// Logs
	F_ARROW_SHAFT(ItemID.ARROW_SHAFT, "Arrow shaft", 1, 5, ExperienceItem.F_LOGS, null, new ItemStack(ItemID.ARROW_SHAFT, 15)),
	F_SHORTBOW_U(ItemID.UNSTRUNG_SHORTBOW, "Shortbow (u)", 5, 5, ExperienceItem.F_LOGS, null, new ItemStack(ItemID.UNSTRUNG_SHORTBOW, 1)),
	F_WOODEN_STOCK(ItemID.XBOWS_CROSSBOW_STOCK_WOOD, "Wooden stock", 9, 6, ExperienceItem.F_LOGS, null, new ItemStack(ItemID.XBOWS_CROSSBOW_STOCK_WOOD, 1)),
	F_LONGBOW_U(ItemID.UNSTRUNG_LONGBOW, "Longbow (u)", 10, 10, ExperienceItem.F_LOGS, null, new ItemStack(ItemID.UNSTRUNG_LONGBOW, 1)),
	// Oak Logs
	F_OAK_ARROW_SHAFT(ItemID.ARROW_SHAFT, "Arrow shaft", 15, 10, ExperienceItem.F_OAK_LOGS, null, new ItemStack(ItemID.ARROW_SHAFT, 30)),
	F_OAK_SHORTBOW_U(ItemID.UNSTRUNG_OAK_SHORTBOW, "Oak shortbow (u)", 20, 16.5, ExperienceItem.F_OAK_LOGS, null, new ItemStack(ItemID.UNSTRUNG_OAK_SHORTBOW, 1)),
	F_OAK_STOCK(ItemID.XBOWS_CROSSBOW_STOCK_OAK, "Oak stock", 24, 16, ExperienceItem.F_OAK_LOGS, null, new ItemStack(ItemID.XBOWS_CROSSBOW_STOCK_OAK, 1)),
	F_OAK_LONGBOW_U(ItemID.UNSTRUNG_OAK_LONGBOW, "Oak longbow (u)", 25, 25, ExperienceItem.F_OAK_LOGS, null, new ItemStack(ItemID.UNSTRUNG_OAK_LONGBOW, 1)),
	F_OAK_SHIELD(ItemID.OAK_SHIELD, "Oak shield", 27, 50 * 0.5, ExperienceItem.F_OAK_LOGS, null, new ItemStack(ItemID.OAK_SHIELD, 0.5)),
	// Willow Logs
	F_WILLOW_ARROW_SHAFT(ItemID.ARROW_SHAFT, "Arrow shaft", 30, 15, ExperienceItem.F_WILLOW_LOGS, null, new ItemStack(ItemID.ARROW_SHAFT, 45)),
	F_WILLOW_SHORTBOW_U(ItemID.UNSTRUNG_WILLOW_SHORTBOW, "Willow shortbow (u)", 35, 33.3, ExperienceItem.F_WILLOW_LOGS, null, new ItemStack(ItemID.UNSTRUNG_WILLOW_SHORTBOW, 1)),
	F_WILLOW_STOCK(ItemID.XBOWS_CROSSBOW_STOCK_WILLOW, "Willow stock", 39, 22, ExperienceItem.F_WILLOW_LOGS, null, new ItemStack(ItemID.XBOWS_CROSSBOW_STOCK_WILLOW, 1)),
	F_WILLOW_LONGBOW_U(ItemID.UNSTRUNG_WILLOW_LONGBOW, "Willow longbow (u)", 40, 41.5, ExperienceItem.F_WILLOW_LOGS, null, new ItemStack(ItemID.UNSTRUNG_WILLOW_LONGBOW, 1)),
	F_WILLOW_SHIELD(ItemID.OAK_SHIELD, "Willow shield", 42, 83 * 0.5, ExperienceItem.F_WILLOW_LOGS, null, new ItemStack(ItemID.WILLOW_SHIELD, 0.5)),
	// Teak Logs
	F_TEAK_STOCK(ItemID.XBOWS_CROSSBOW_STOCK_TEAK, "Teak stock", 46, 27, ExperienceItem.F_TEAK_LOGS, null, new ItemStack(ItemID.XBOWS_CROSSBOW_STOCK_TEAK, 1)),
	// Maple Logs
	F_MAPLE_ARROW_SHAFT(ItemID.ARROW_SHAFT, "Arrow shaft", 45, 20, ExperienceItem.F_MAPLE_LOGS, null, new ItemStack(ItemID.ARROW_SHAFT, 60)),
	F_MAPLE_SHORTBOW_U(ItemID.UNSTRUNG_MAPLE_SHORTBOW, "Maple shortbow (u)", 50, 50, ExperienceItem.F_MAPLE_LOGS, null, new ItemStack(ItemID.UNSTRUNG_MAPLE_SHORTBOW, 1)),
	F_MAPLE_STOCK(ItemID.XBOWS_CROSSBOW_STOCK_MAPLE, "Maple stock", 54, 32, ExperienceItem.F_MAPLE_LOGS, null, new ItemStack(ItemID.XBOWS_CROSSBOW_STOCK_MAPLE, 1)),
	F_MAPLE_LONGBOW_U(ItemID.UNSTRUNG_MAPLE_LONGBOW, "Maple longbow (u)", 55, 58.3, ExperienceItem.F_MAPLE_LOGS, null, new ItemStack(ItemID.UNSTRUNG_MAPLE_LONGBOW, 1)),
	F_MAPLE_SHIELD(ItemID.MAPLE_SHIELD, "Maple shield", 57, 116.5 * 0.5, ExperienceItem.F_MAPLE_LOGS, null, new ItemStack(ItemID.MAPLE_SHIELD, 0.5)),
	// Mahogany Logs
	F_MAHOGANY_STOCK(ItemID.XBOWS_CROSSBOW_STOCK_MAHOGANY, "Mahogany stock", 61, 41, ExperienceItem.F_MAHOGANY_LOGS, null, new ItemStack(ItemID.XBOWS_CROSSBOW_STOCK_MAHOGANY, 1)),
	// Yew Logs
	F_YEW_ARROW_SHAFT(ItemID.ARROW_SHAFT, "Arrow shaft", 60, 25, ExperienceItem.F_YEW_LOGS, null, new ItemStack(ItemID.ARROW_SHAFT, 75)),
	F_YEW_SHORTBOW_U(ItemID.UNSTRUNG_YEW_SHORTBOW, "Yew shortbow (u)", 65, 67.5, ExperienceItem.F_YEW_LOGS, null, new ItemStack(ItemID.UNSTRUNG_YEW_SHORTBOW, 1)),
	F_YEW_STOCK(ItemID.XBOWS_CROSSBOW_STOCK_YEW, "Yew stock", 69, 50, ExperienceItem.F_YEW_LOGS, null, new ItemStack(ItemID.XBOWS_CROSSBOW_STOCK_YEW, 1)),
	F_YEW_LONGBOW_U(ItemID.UNSTRUNG_YEW_LONGBOW, "Yew longbow (u)", 70, 75, ExperienceItem.F_YEW_LOGS, null, new ItemStack(ItemID.UNSTRUNG_YEW_LONGBOW, 1)),
	F_YEW_SHIELD(ItemID.YEW_SHIELD, "Yew shield", 72, 150 * 0.5, ExperienceItem.F_YEW_LOGS, null, new ItemStack(ItemID.YEW_SHIELD, 0.5)),
	// Magic Logs
	F_MAGIC_ARROW_SHAFT(ItemID.ARROW_SHAFT, "Arrow shaft", 75, 30, ExperienceItem.F_MAGIC_LOGS, null, new ItemStack(ItemID.ARROW_SHAFT, 90)),
	F_MAGIC_STOCK(ItemID.XBOWS_CROSSBOW_STOCK_MAGIC, "Magic stock", 78, 70, ExperienceItem.F_MAGIC_LOGS, null, new ItemStack(ItemID.XBOWS_CROSSBOW_STOCK_MAGIC, 1)),
	F_MAGIC_SHORTBOW_U(ItemID.UNSTRUNG_MAGIC_SHORTBOW, "Magic shortbow (u)", 80, 83.3, ExperienceItem.F_MAGIC_LOGS, null, new ItemStack(ItemID.UNSTRUNG_MAGIC_SHORTBOW, 1)),
	F_MAGIC_LONGBOW_U(ItemID.UNSTRUNG_MAGIC_LONGBOW, "Magic longbow (u)", 85, 91.5, ExperienceItem.F_MAGIC_LOGS, null, new ItemStack(ItemID.UNSTRUNG_MAGIC_LONGBOW, 1)),
	F_MAGIC_SHIELD(ItemID.MAGIC_SHIELD, "Magic shield", 87, 183 * 0.5, ExperienceItem.F_MAGIC_LOGS, null, new ItemStack(ItemID.MAGIC_SHIELD, 0.5)),
	// Redwood Logs
	F_REDWOOD_ARROW_SHAFT(ItemID.ARROW_SHAFT, "Arrow shaft", 90, 35, ExperienceItem.F_REDWOOD_LOGS, null, new ItemStack(ItemID.ARROW_SHAFT, 105)),
	F_REDWOOD_SHIELD(ItemID.REDWOOD_SHIELD, "Redwood shield", 92, 216 * 0.5, ExperienceItem.F_REDWOOD_LOGS, null, new ItemStack(ItemID.REDWOOD_SHIELD, 0.5)),
	// Strung Bows
	F_SHORTBOW(ItemID.SHORTBOW, "Shortbow", 5, 5, ExperienceItem.F_SHORTBOW_U, Secondaries.BOW_STRING, new ItemStack(ItemID.SHORTBOW, 1)),
	F_LONGBOW(ItemID.LONGBOW, "Longbow", 10, 10, ExperienceItem.F_LONGBOW_U, Secondaries.BOW_STRING, new ItemStack(ItemID.LONGBOW, 1)),
	F_OAK_SHORTBOW(ItemID.OAK_SHORTBOW, "Oak shortbow", 20, 16.5, ExperienceItem.F_OAK_SHORTBOW_U, Secondaries.BOW_STRING, new ItemStack(ItemID.OAK_SHORTBOW, 1)),
	F_OAK_LONGBOW(ItemID.OAK_LONGBOW, "Oak longbow", 25, 25, ExperienceItem.F_OAK_LONGBOW_U, Secondaries.BOW_STRING, new ItemStack(ItemID.OAK_LONGBOW, 1)),
	F_WILLOW_SHORTBOW(ItemID.WILLOW_SHORTBOW, "Willow shortbow", 35, 33.2, ExperienceItem.F_WILLOW_SHORTBOW_U, Secondaries.BOW_STRING, new ItemStack(ItemID.WILLOW_SHORTBOW, 1)),
	F_WILLOW_LONGBOW(ItemID.WILLOW_LONGBOW, "Willow longbow", 40, 41.5, ExperienceItem.F_WILLOW_LONGBOW_U, Secondaries.BOW_STRING, new ItemStack(ItemID.WILLOW_LONGBOW, 1)),
	F_MAPLE_SHORTBOW(ItemID.MAPLE_SHORTBOW, "Maple shortbow", 50, 50, ExperienceItem.F_MAPLE_SHORTBOW_U, Secondaries.BOW_STRING, new ItemStack(ItemID.MAPLE_SHORTBOW, 1)),
	F_MAPLE_LONGBOW(ItemID.MAPLE_LONGBOW, "Maple longbow", 55, 58.2, ExperienceItem.F_MAPLE_LONGBOW_U, Secondaries.BOW_STRING, new ItemStack(ItemID.MAPLE_LONGBOW, 1)),
	F_YEW_SHORTBOW(ItemID.YEW_SHORTBOW, "Yew shortbow", 65, 67.5, ExperienceItem.F_YEW_SHORTBOW_U, Secondaries.BOW_STRING, new ItemStack(ItemID.YEW_SHORTBOW, 1)),
	F_YEW_LONGBOW(ItemID.YEW_LONGBOW, "Yew longbow", 70, 75, ExperienceItem.F_YEW_LONGBOW_U, Secondaries.BOW_STRING, new ItemStack(ItemID.YEW_LONGBOW, 1)),
	F_MAGIC_SHORTBOW(ItemID.MAGIC_SHORTBOW, "Magic shortbow", 80, 83.2, ExperienceItem.F_MAGIC_SHORTBOW_U, Secondaries.BOW_STRING, new ItemStack(ItemID.MAGIC_SHORTBOW, 1)),
	F_MAGIC_LONGBOW(ItemID.MAGIC_LONGBOW, "Magic longbow", 85, 91.5, ExperienceItem.F_MAGIC_LONGBOW_U, Secondaries.BOW_STRING, new ItemStack(ItemID.MAGIC_LONGBOW, 1)),
	// Darts
	F_BRONZE_DARTS(ItemID.BRONZE_DART, "Bronze dart", 10, 1.8, ExperienceItem.F_BRONZE_DART_TIP, Secondaries.FEATHER, new ItemStack(ItemID.BRONZE_DART, 1)),
	F_IRON_DARTS(ItemID.IRON_DART, "Iron dart", 22, 3.8, ExperienceItem.F_IRON_DART_TIP, Secondaries.FEATHER, new ItemStack(ItemID.IRON_DART, 1)),
	F_STEEL_DARTS(ItemID.STEEL_DART, "Steel dart", 37, 7.5, ExperienceItem.F_STEEL_DART_TIP, Secondaries.FEATHER, new ItemStack(ItemID.STEEL_DART, 1)),
	F_MITHRIL_DARTS(ItemID.MITHRIL_DART, "Mithril dart", 52, 11.2, ExperienceItem.F_MITHRIL_DART_TIP, Secondaries.FEATHER, new ItemStack(ItemID.MITHRIL_DART, 1)),
	F_ADAMANT_DARTS(ItemID.ADAMANT_DART, "Adamant dart", 67, 15, ExperienceItem.F_ADAMANT_DART_TIP, Secondaries.FEATHER, new ItemStack(ItemID.ADAMANT_DART, 1)),
	F_RUNE_DARTS(ItemID.RUNE_DART, "Rune dart", 81, 18.8, ExperienceItem.F_RUNE_DART_TIP, Secondaries.FEATHER, new ItemStack(ItemID.RUNE_DART, 1)),
	F_AMETHYST_DARTS(ItemID.AMETHYST_DART, "Amethyst dart", 90, 21, ExperienceItem.F_AMETHYST_DART_TIP, Secondaries.FEATHER, new ItemStack(ItemID.AMETHYST_DART, 1)),
	F_DRAGON_DARTS(ItemID.DRAGON_DART, "Dragon dart", 95, 25, ExperienceItem.F_DRAGON_DART_TIP, Secondaries.FEATHER, new ItemStack(ItemID.DRAGON_DART, 1)),
	// Arrows
	F_BRONZE_ARROW(ItemID.BRONZE_ARROW, "Bronze arrow", 1, 1, ExperienceItem.F_BRONZE_ARROWTIPS, Secondaries.HEADLESS_ARROW, new ItemStack(ItemID.BRONZE_ARROW, 1)),
	F_IRON_ARROW(ItemID.IRON_ARROW, "Iron arrow", 1, 2.5, ExperienceItem.F_IRON_ARROWTIPS, Secondaries.HEADLESS_ARROW, new ItemStack(ItemID.IRON_ARROW, 1)),
	F_STEEL_ARROW(ItemID.STEEL_ARROW, "Steel arrow", 30, 5, ExperienceItem.F_STEEL_ARROWTIPS, Secondaries.HEADLESS_ARROW, new ItemStack(ItemID.STEEL_ARROW, 1)),
	F_MITHRIL_ARROW(ItemID.MITHRIL_ARROW, "Mithril arrow", 45, 7.5, ExperienceItem.F_MITHRIL_ARROWTIPS, Secondaries.HEADLESS_ARROW, new ItemStack(ItemID.MITHRIL_ARROW, 1)),
	F_BROAD_ARROW(ItemID.SLAYERGUIDE_BROAD_ARROWS, "Broad arrow", 52, 10, ExperienceItem.F_BROAD_ARROWHEADS, Secondaries.HEADLESS_ARROW, new ItemStack(ItemID.SLAYERGUIDE_BROAD_ARROWS, 1)),
	F_ADAMANT_ARROW(ItemID.ADAMANT_ARROW, "Adamant arrow", 60, 10, ExperienceItem.F_ADAMANT_ARROWTIPS, Secondaries.HEADLESS_ARROW, new ItemStack(ItemID.ADAMANT_ARROW, 1)),
	F_RUNE_ARROW(ItemID.RUNE_ARROW, "Rune arrow", 75, 12.5, ExperienceItem.F_RUNE_ARROWTIPS, Secondaries.HEADLESS_ARROW, new ItemStack(ItemID.RUNE_ARROW, 1)),
	F_AMETHYST_ARROW(ItemID.AMETHYST_ARROW, "Amethyst arrow", 82, 13.5, ExperienceItem.F_AMETHYST_ARROWTIPS, Secondaries.HEADLESS_ARROW, new ItemStack(ItemID.AMETHYST_ARROW, 1)),
	F_DRAGON_ARROW(ItemID.DRAGON_ARROW, "Dragon arrow", 90, 15, ExperienceItem.F_DRAGON_ARROWTIPS, Secondaries.HEADLESS_ARROW, new ItemStack(ItemID.DRAGON_ARROW, 1)),
	// Bolts
	BRONZE_BOLTS(ItemID.BOLT, "Bronze bolts", 9, 0.5, ExperienceItem.BRONZE_BOLTS, Secondaries.FEATHER, new ItemStack(ItemID.BOLT, 1)),
	BLURITE_BOLTS(ItemID.XBOWS_CROSSBOW_BOLTS_BLURITE, "Blurite bolts", 24, 1, ExperienceItem.BLURITE_BOLTS, Secondaries.FEATHER, new ItemStack(ItemID.XBOWS_CROSSBOW_BOLTS_IRON, 1)),
	KEBBIT_BOLTS(ItemID.HUNTINGBOW_BOLTS, "Kebbit bolts", 32, 5.8, ExperienceItem.KEBBIT_BOLTS, null, new ItemStack(ItemID.HUNTINGBOW_BOLTS, 12)),
	IRON_BOLTS(ItemID.XBOWS_CROSSBOW_BOLTS_IRON, "Iron bolts", 39, 1.5, ExperienceItem.IRON_BOLTS, Secondaries.FEATHER, new ItemStack(ItemID.XBOWS_CROSSBOW_BOLTS_IRON, 1)),
	SILVER_BOLTS(ItemID.XBOWS_CROSSBOW_BOLTS_SILVER, "Silver bolts", 43, 2.5, ExperienceItem.SILVER_BOLTS, Secondaries.FEATHER, new ItemStack(ItemID.XBOWS_CROSSBOW_BOLTS_SILVER, 1)),
	STEEL_BOLTS(ItemID.XBOWS_CROSSBOW_BOLTS_STEEL, "Steel bolts", 46, 3.5, ExperienceItem.STEEL_BOLTS, Secondaries.FEATHER, new ItemStack(ItemID.XBOWS_CROSSBOW_BOLTS_STEEL, 1)),
	MITHRIL_BOLTS(ItemID.XBOWS_CROSSBOW_BOLTS_MITHRIL, "Mithril bolts", 54, 5, ExperienceItem.MITHRIL_BOLTS, Secondaries.FEATHER, new ItemStack(ItemID.XBOWS_CROSSBOW_BOLTS_MITHRIL, 1)),
	BROAD_BOLTS(ItemID.SLAYER_BROAD_BOLT, "Broad bolts", 55, 3, ExperienceItem.BROAD_BOLTS, Secondaries.FEATHER, new ItemStack(ItemID.SLAYER_BROAD_BOLT, 1)),
	ADAMANT_BOLTS(ItemID.XBOWS_CROSSBOW_BOLTS_ADAMANTITE, "Adamant bolts", 61, 7, ExperienceItem.ADAMANT_BOLTS, Secondaries.FEATHER, new ItemStack(ItemID.XBOWS_CROSSBOW_BOLTS_ADAMANTITE, 1)),
	SUNLIGHT_ANTLER_BOLTS(ItemID.SUNLIGHT_ANTELOPE_BOLT, "Sunlight antler bolts", 62, 10, ExperienceItem.SUNLIGHT_ANTLER_BOLTS, null, new ItemStack(ItemID.SUNLIGHT_ANTELOPE_BOLT, 12)),
	RUNE_BOLTS(ItemID.XBOWS_CROSSBOW_BOLTS_RUNITE, "Runite bolts", 69, 10, ExperienceItem.RUNE_BOLTS, Secondaries.FEATHER, new ItemStack(ItemID.XBOWS_CROSSBOW_BOLTS_RUNITE, 1)),
	MOONLIGHT_ANTLER_BOLTS(ItemID.MOONLIGHT_ANTELOPE_BOLT, "Moonlight antler bolts", 72, 12, ExperienceItem.MOONLIGHT_ANTLER_BOLTS, null, new ItemStack(ItemID.MOONLIGHT_ANTELOPE_BOLT, 12)),
	DRAGON_BOLTS(ItemID.DRAGON_BOLTS, "Dragon bolts", 84, 12, ExperienceItem.DRAGON_BOLTS, Secondaries.FEATHER, new ItemStack(ItemID.DRAGON_BOLTS, 1)),
	// Unstrung Crossbows
	F_BRONZE_CROSSBOW_U(ItemID.XBOWS_CROSSBOW_STOCK_WOOD, "Bronze crossbow (u)", 9, 12, ExperienceItem.F_WOODEN_STOCK, Secondaries.BRONZE_LIMBS, new ItemStack(ItemID.XBOWS_CROSSBOW_UNSTRUNG_BRONZE, 1)),
	F_BLURITE_CROSSBOW_U(ItemID.XBOWS_CROSSBOW_STOCK_OAK, "Blurite crossbow (u)", 24, 32, ExperienceItem.F_OAK_STOCK, Secondaries.BLURITE_LIMBS, new ItemStack(ItemID.XBOWS_CROSSBOW_UNSTRUNG_BLURITE, 1)),
	F_IRON_CROSSBOW_U(ItemID.XBOWS_CROSSBOW_STOCK_WILLOW, "Iron crossbow (u)", 39, 44, ExperienceItem.F_WILLOW_STOCK, Secondaries.IRON_LIMBS, new ItemStack(ItemID.XBOWS_CROSSBOW_UNSTRUNG_IRON, 1)),
	F_STEEL_CROSSBOW_U(ItemID.XBOWS_CROSSBOW_STOCK_TEAK, "Steel crossbow (u)", 46, 54, ExperienceItem.F_TEAK_STOCK, Secondaries.STEEL_LIMBS, new ItemStack(ItemID.XBOWS_CROSSBOW_UNSTRUNG_STEEL, 1)),
	F_MITHRIL_CROSSBOW_U(ItemID.XBOWS_CROSSBOW_STOCK_MAPLE, "Mithril crossbow (u)", 54, 64, ExperienceItem.F_MAPLE_STOCK, Secondaries.MITHRIL_LIMBS, new ItemStack(ItemID.XBOWS_CROSSBOW_UNSTRUNG_MITHRIL, 1)),
	F_ADAMANTITE_CROSSBOW_U(ItemID.XBOWS_CROSSBOW_STOCK_MAHOGANY, "Adamantite crossbow (u)", 61, 82, ExperienceItem.F_MAHOGANY_STOCK, Secondaries.ADAMANTITE_LIMBS, new ItemStack(ItemID.XBOWS_CROSSBOW_UNSTRUNG_ADAMANTITE, 1)),
	F_RUNITE_CROSSBOW_U(ItemID.XBOWS_CROSSBOW_STOCK_YEW, "Runite crossbow (u)", 69, 100, ExperienceItem.F_YEW_STOCK, Secondaries.RUNITE_LIMBS, new ItemStack(ItemID.XBOWS_CROSSBOW_UNSTRUNG_RUNITE, 1)),
	F_DRAGON_CROSSBOW_U(ItemID.XBOWS_CROSSBOW_STOCK_MAGIC, "Dragon crossbow (u)", 78, 135, ExperienceItem.F_MAGIC_STOCK, Secondaries.DRAGON_LIMBS, new ItemStack(ItemID.XBOWS_CROSSBOW_UNSTRUNG_DRAGON, 1)),
	// Crossbows
	F_BRONZE_CROSSBOW(ItemID.XBOWS_CROSSBOW_UNSTRUNG_BRONZE, "Bronze crossbow", 9, 6, ExperienceItem.F_BRONZE_CROSSBOW_U, Secondaries.CROSSBOW_STRING, new ItemStack(ItemID.XBOWS_CROSSBOW_BRONZE, 1)),
	F_BLURITE_CROSSBOW(ItemID.XBOWS_CROSSBOW_UNSTRUNG_BLURITE, "Blurite crossbow", 24, 16, ExperienceItem.F_BLURITE_CROSSBOW_U, Secondaries.CROSSBOW_STRING, new ItemStack(ItemID.XBOWS_CROSSBOW_BLURITE, 1)),
	F_IRON_CROSSBOW(ItemID.XBOWS_CROSSBOW_UNSTRUNG_IRON, "Iron crossbow", 39, 22, ExperienceItem.F_IRON_CROSSBOW_U, Secondaries.CROSSBOW_STRING, new ItemStack(ItemID.XBOWS_CROSSBOW_IRON, 1)),
	F_STEEL_CROSSBOW(ItemID.XBOWS_CROSSBOW_UNSTRUNG_STEEL, "Steel crossbow", 46, 27, ExperienceItem.F_STEEL_CROSSBOW_U, Secondaries.CROSSBOW_STRING, new ItemStack(ItemID.XBOWS_CROSSBOW_STEEL, 1)),
	F_MITHRIL_CROSSBOW(ItemID.XBOWS_CROSSBOW_UNSTRUNG_MITHRIL, "Mithril crossbow", 54, 32, ExperienceItem.F_MITHRIL_CROSSBOW_U, Secondaries.CROSSBOW_STRING, new ItemStack(ItemID.XBOWS_CROSSBOW_MITHRIL, 1)),
	F_ADAMANTITE_CROSSBOW(ItemID.XBOWS_CROSSBOW_UNSTRUNG_ADAMANTITE, "Adamant crossbow", 61, 41, ExperienceItem.F_ADAMANTITE_CROSSBOW_U, Secondaries.CROSSBOW_STRING, new ItemStack(ItemID.XBOWS_CROSSBOW_ADAMANTITE, 1)),
	F_RUNITE_CROSSBOW(ItemID.XBOWS_CROSSBOW_UNSTRUNG_RUNITE, "Rune crossbow", 69, 50, ExperienceItem.F_RUNITE_CROSSBOW_U, Secondaries.CROSSBOW_STRING, new ItemStack(ItemID.XBOWS_CROSSBOW_RUNITE, 1)),
	F_DRAGON_CROSSBOW(ItemID.XBOWS_CROSSBOW_UNSTRUNG_DRAGON, "Dragon crossbow", 78, 70, ExperienceItem.F_DRAGON_CROSSBOW_U, Secondaries.CROSSBOW_STRING, new ItemStack(ItemID.XBOWS_CROSSBOW_DRAGON, 1)),
	// Other
	F_BATTLESTAFF(ItemID.BATTLESTAFF, "Battlestaff", 40, 80, ExperienceItem.F_CELASTRUS_BARK, null, new ItemStack(ItemID.BATTLESTAFF, 1)),
	// Javelin Heads
	BRONZE_JAVELINS(ItemID.BRONZE_JAVELIN, "Bronze javelins", 3, 1, ExperienceItem.BRONZE_JAVELIN_HEADS, Secondaries.JAVELIN_SHAFT, new ItemStack(ItemID.BRONZE_JAVELIN, 1)),
	IRON_JAVELINS(ItemID.IRON_JAVELIN, "Iron javelins", 17, 2, ExperienceItem.IRON_JAVELIN_HEADS, Secondaries.JAVELIN_SHAFT, new ItemStack(ItemID.IRON_JAVELIN, 1)),
	STEEL_JAVELINS(ItemID.STEEL_JAVELIN, "Steel javelins", 32, 5, ExperienceItem.STEEL_JAVELIN_HEADS, Secondaries.JAVELIN_SHAFT, new ItemStack(ItemID.STEEL_JAVELIN, 1)),
	MITHRIL_JAVELINS(ItemID.MITHRIL_JAVELIN, "Mithril javelins", 47, 8, ExperienceItem.MITHRIL_JAVELIN_HEADS, Secondaries.JAVELIN_SHAFT, new ItemStack(ItemID.MITHRIL_JAVELIN, 1)),
	ADAMANT_JAVELINS(ItemID.ADAMANT_JAVELIN, "Adamant javelins", 62, 10, ExperienceItem.ADAMANT_JAVELIN_HEADS, Secondaries.JAVELIN_SHAFT, new ItemStack(ItemID.ADAMANT_JAVELIN, 1)),
	RUNE_JAVELINS(ItemID.RUNE_JAVELIN, "Rune javelins", 77, 12.4, ExperienceItem.RUNE_JAVELIN_HEADS, Secondaries.JAVELIN_SHAFT, new ItemStack(ItemID.RUNE_JAVELIN, 1)),
	AMETHYST_JAVELINS(ItemID.AMETHYST_JAVELIN, "Amethyst javelins", 84, 13.5, ExperienceItem.AMETHYST_JAVELIN_HEADS, Secondaries.JAVELIN_SHAFT, new ItemStack(ItemID.AMETHYST_JAVELIN, 1)),
	DRAGON_JAVELINS(ItemID.DRAGON_JAVELIN, "Dragon javelins", 92, 15, ExperienceItem.DRAGON_JAVELIN_HEADS, Secondaries.JAVELIN_SHAFT, new ItemStack(ItemID.DRAGON_JAVELIN, 1)),
	// Carve Vale Totems
	// TODO: Create support for activities with variable xp yields to begin
	//  counting the construction XP from building a vale totem which generates
	//  1*(Construction lvl) xp per action
	F_VALE_TOTEM_OAK(ItemID.ENT_TOTEMS_LOOT, "Carve Oak Vale Totem", 20, 225, ExperienceItem.F_OAK_LOGS, Secondaries.OAK_VALE_TOTEM_OFFERING, new ItemStack(ItemID.ENT_TOTEMS_LOOT, 20)),
	F_VALE_TOTEM_WILLOW(ItemID.ENT_TOTEMS_LOOT, "Carve Willow Vale Totem", 35, 628, ExperienceItem.F_WILLOW_LOGS, Secondaries.WILLOW_VALE_TOTEM_OFFERING, new ItemStack(ItemID.ENT_TOTEMS_LOOT, 30)),
	F_VALE_TOTEM_MAPLE(ItemID.ENT_TOTEMS_LOOT, "Carve Maple Vale Totem", 50, 1008, ExperienceItem.F_MAPLE_LOGS, Secondaries.MAPLE_VALE_TOTEM_OFFERING, new ItemStack(ItemID.ENT_TOTEMS_LOOT, 40)),
	F_VALE_TOTEM_YEW(ItemID.ENT_TOTEMS_LOOT, "Carve Yew Vale Totem", 65, 1632, ExperienceItem.F_YEW_LOGS, Secondaries.YEW_VALE_TOTEM_OFFERING, new ItemStack(ItemID.ENT_TOTEMS_LOOT, 65)),
	F_VALE_TOTEM_MAGIC(ItemID.ENT_TOTEMS_LOOT, "Carve Magic Vale Totem", 80, 3104, ExperienceItem.F_MAGIC_LOGS, Secondaries.MAGIC_VALE_TOTEM_OFFERING, new ItemStack(ItemID.ENT_TOTEMS_LOOT, 90)),
	F_VALE_TOTEM_REDWOOD(ItemID.ENT_TOTEMS_LOOT, "Carve Redwood Vale Totem", 90, 3788, ExperienceItem.F_REDWOOD_LOGS, Secondaries.REDWOOD_VALE_TOTEM_OFFERING, new ItemStack(ItemID.ENT_TOTEMS_LOOT, 105)),

	/**
	 * Hunter
	 */
	BIRD_HOUSE(ItemID.BIRDHOUSE_NORMAL, "Bird house", 5, 280, ExperienceItem.H_LOGS, null, null),
	PREBUILT_BIRD_HOUSE(ItemID.BIRDHOUSE_NORMAL, "Bird house", 5, 280, ExperienceItem.BIRD_HOUSE, null, null),
	OAK_BIRD_HOUSE(ItemID.BIRDHOUSE_OAK, "Oak bird house", 14, 420, ExperienceItem.H_OAK_LOGS, null, null),
	PREBUILT_OAK_BIRD_HOUSE(ItemID.BIRDHOUSE_OAK, "Oak bird house", 14, 420, ExperienceItem.OAK_BIRD_HOUSE, null, null),
	WILLOW_BIRD_HOUSE(ItemID.BIRDHOUSE_WILLOW, "Willow bird house", 24, 560, ExperienceItem.H_WILLOW_LOGS, null, null),
	PREBUILT_WILLOW_BIRD_HOUSE(ItemID.BIRDHOUSE_WILLOW, "Willow bird house", 24, 560, ExperienceItem.WILLOW_BIRD_HOUSE, null, null),
	TEAK_BIRD_HOUSE(ItemID.BIRDHOUSE_TEAK, "Teak bird house", 34, 700, ExperienceItem.H_TEAK_LOGS, null, null),
	PREBUILT_TEAK_BIRD_HOUSE(ItemID.BIRDHOUSE_TEAK, "Teak bird house", 34, 700, ExperienceItem.TEAK_BIRD_HOUSE, null, null),
	MAPLE_BIRD_HOUSE(ItemID.BIRDHOUSE_MAPLE, "Maple bird house", 44, 820, ExperienceItem.H_MAPLE_LOGS, null, null),
	PREBUILT_MAPLE_BIRD_HOUSE(ItemID.BIRDHOUSE_MAPLE, "Maple bird house", 44, 820, ExperienceItem.MAPLE_BIRD_HOUSE, null, null),
	MAHOGANY_BIRD_HOUSE(ItemID.BIRDHOUSE_MAHOGANY, "Mahogany bird house", 49, 960, ExperienceItem.H_MAHOGANY_LOGS, null, null),
	PREBUILT_MAHOGANY_BIRD_HOUSE(ItemID.BIRDHOUSE_MAHOGANY, "Mahogany bird house", 49, 960, ExperienceItem.MAHOGANY_BIRD_HOUSE, null, null),
	YEW_BIRD_HOUSE(ItemID.BIRDHOUSE_YEW, "Yew bird house", 59, 1020, ExperienceItem.H_YEW_LOGS, null, null),
	PREBUILT_YEW_BIRD_HOUSE(ItemID.BIRDHOUSE_YEW, "Yew bird house", 59, 1020, ExperienceItem.YEW_BIRD_HOUSE, null, null),
	MAGIC_BIRD_HOUSE(ItemID.BIRDHOUSE_MAGIC, "Magic bird house", 74, 1140, ExperienceItem.H_MAGIC_LOGS, null, null),
	PREBUILT_MAGIC_BIRD_HOUSE(ItemID.BIRDHOUSE_MAGIC, "Magic bird house", 74, 1140, ExperienceItem.MAGIC_BIRD_HOUSE, null, null),
	REDWOOD_BIRD_HOUSE(ItemID.BIRDHOUSE_REDWOOD, "Redwood bird house", 89, 1200, ExperienceItem.H_REDWOOD_LOGS, null, null),
	PREBUILT_REDWOOD_BIRD_HOUSE(ItemID.BIRDHOUSE_REDWOOD, "Redwood bird house", 89, 1200, ExperienceItem.REDWOOD_BIRD_HOUSE, null, null),
	/**
	 * Firemaking
	 */
	BURN_LOGS(ItemID.LOGS, "Burn logs", 1, 40, ExperienceItem.FM_LOGS, null, null),
	BURN_ACHEY_TREE(ItemID.ACHEY_TREE_LOGS, "Burn Achey Tree logs", 1, 40, ExperienceItem.FM_ACHEY_TREE_LOGS, null, null),
	BURN_OAK(ItemID.OAK_LOGS, "Burn Oak logs", 15, 60, ExperienceItem.FM_OAK_LOGS, null, null),
	BURN_WILLOW(ItemID.WILLOW_LOGS, "Burn Willow Logs", 30, 90, ExperienceItem.FM_WILLOW_LOGS, null, null),
	BURN_TEAK(ItemID.TEAK_LOGS, "Burn Teak logs", 35, 105, ExperienceItem.FM_TEAK_LOGS, null, null),
	BURN_ARCTIC_PINE(ItemID.ARCTIC_PINE_LOG, "Burn Arctic Pine logs", 42, 125, ExperienceItem.FM_ARCTIC_PINE_LOGS, null, null),
	BURN_MAPLE(ItemID.MAPLE_LOGS, "Burn Maple logs", 45, 135, ExperienceItem.FM_MAPLE_LOGS, null, null),
	BURN_MAHOGANY(ItemID.MAHOGANY_LOGS, "Burn Mahogany logs", 50, 157.5, ExperienceItem.FM_MAHOGANY_LOGS, null, null),
	BURN_YEW(ItemID.YEW_LOGS, "Burn Yew logs", 60, 202.5, ExperienceItem.FM_YEW_LOGS, null, null),
	BURN_BLISTERWOOD(ItemID.BLISTERWOOD_LOGS, "Burn Blisterwood logs", 62, 96, ExperienceItem.FM_BLISTERWOOD_LOGS, null, null),
	BURN_MAGIC(ItemID.MAGIC_LOGS, "Burn Magic logs", 75, 303.8, ExperienceItem.FM_MAGIC_LOGS, null, null),
	BURN_REDWOOD(ItemID.REDWOOD_LOGS, "Burn Redwood logs", 90, 350, ExperienceItem.FM_REDWOOD_LOGS, null, null),
	/**
	 * Thieving
	 */
	WINTER_SQUIRK(ItemID.OSMAN_SQUIRK_J_WINTER, "Make sq'irkjuice", 1, 0, ExperienceItem.WINTER_SQUIRK, Secondaries.BEER_GLASS_5TH, new ItemStack(ItemID.OSMAN_SQUIRK_J_WINTER, 0.2)),
	WINTER_SQUIRKJUICE(ItemID.OSMAN_SQUIRK_J_WINTER, "Redeem sq'irkjuice", 1, 350, ExperienceItem.WINTER_SQUIRK, null, null),
	SPRING_SQUIRK(ItemID.OSMAN_SQUIRK_J_SPRING, "Make sq'irkjuice", 25, 0, ExperienceItem.SPRING_SQUIRK, Secondaries.BEER_GLASS_4TH, new ItemStack(ItemID.OSMAN_SQUIRK_J_SPRING, 0.25)),
	SPRING_SQUIRKJUICE(ItemID.OSMAN_SQUIRK_J_SPRING, "Redeem sq'irkjuice", 25, 1350, ExperienceItem.SPRING_SQUIRK, null, null),
	AUTUMN_SQUIRK(ItemID.OSMAN_SQUIRK_J_AUTUMN, "Make sq'irkjuice", 45, 0, ExperienceItem.AUTUMN_SQUIRK, Secondaries.BEER_GLASS_3RD, new ItemStack(ItemID.OSMAN_SQUIRK_J_AUTUMN, 1.0 / 3)),
	AUTUMN_SQUIRKJUICE(ItemID.OSMAN_SQUIRK_J_AUTUMN, "Redeem sq'irkjuice", 45, 2350, ExperienceItem.AUTUMN_SQUIRK, null, null),
	SUMMER_SQUIRK(ItemID.OSMAN_SQUIRK_J_SUMMER, "Make sq'irkjuice", 65, 0, ExperienceItem.SUMMER_SQUIRK, Secondaries.BEER_GLASS_HALF, new ItemStack(ItemID.OSMAN_SQUIRK_J_SUMMER, 0.5)),
	SUMMER_SQUIRKJUICE(ItemID.OSMAN_SQUIRK_J_SUMMER, "Redeem sq'irkjuice", 65, 3000, ExperienceItem.SUMMER_SQUIRK, null, null),;

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

	/*
	 * Used to define how close two quantities must be in order to be considered
	 * equal. Uses Math.ulp to get the minimum difference between two adjacent double 
	 * values.
	 */
	private static final double UPDATE_CHECK_EPSILON = 2.0 * Math.ulp(1.0);

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
	 *
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
	 *
	 * @param item       ExperienceItem to check for
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
	 *
	 * @param m ItemManager
	 */
	public static void prepareItemCompositions(ItemManager m)
	{
		for (Activity a : values())
		{
			final Secondaries activitySecondaries = a.getSecondaries();

			// Attach names to all ItemStacks (secondaries)
			if (activitySecondaries != null)
			{
				for (final ItemStack stack : activitySecondaries.getItems())
				{
					stack.updateItemInfo(m);
				}

				// If it has a custom handler those items need to be prepared as well
				final Secondaries.SecondaryHandler handler = activitySecondaries.getCustomHandler();
				if (handler != null)
				{
					for (final ItemStack stack : handler.getInfoItems())
					{
						stack.updateItemInfo(m);
					}
				}
			}

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

	public boolean shouldUpdateLinked(final Activity old)
	{
		if (old.getLinkedItem() != linkedItem)
		{
			return true;
		}

		final ItemStack oldOutput = old.getOutput();
		final ItemStack newOutput = output;
		
		// If both are null we can skip any expensive test because no update is needed
		if (oldOutput == null && newOutput == null)
		{
			return false;
		}
		
		// If exactly one was null, but not both, then an update should happen
		if ((oldOutput == null) != (newOutput == null))
		{
			return true;
		}
		
		// Re-check that neither are null. Technically we only need to check one variable
		// for nullness to know both are null at this point, but checking both
		// helps keep the compiler happy about null dereference warnings
		if ((oldOutput == null) || (newOutput == null))
		{
			return false;
		}
		
		// Return if the int id is equal and the double values are "equal"
		return oldOutput.getId() != newOutput.getId() || DoubleMath.fuzzyEquals(oldOutput.getQty(), newOutput.getQty(), UPDATE_CHECK_EPSILON);
	}
}
