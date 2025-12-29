/*
 * Copyright (c) 2021, TheStonedTurtle <https://github.com/TheStonedTurtle>
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
package thestonedturtle.bankedexperience.data.modifiers;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import net.runelite.api.ItemComposition;
import net.runelite.api.Skill;
import net.runelite.api.gameval.ItemID;
import net.runelite.client.game.ItemManager;
import thestonedturtle.bankedexperience.data.Activity;

import javax.swing.SwingUtilities;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A utility class that contains all XP modifiers
 */
public final class Modifiers
{
	private static final String ASHES_TOOLTIP_TEXT = "Only applies to ashes";
	private static final String BONES_TOOLTIP_TEXT = "Only applies to bones";
	private static final Multimap<Skill, Modifier> modifiers = ArrayListMultimap.create();

	static final Set<Activity> BONES = ImmutableSet.of(
		Activity.BONES, Activity.WOLF_BONES, Activity.BURNT_BONES, Activity.MONKEY_BONES, Activity.BAT_BONES,
		Activity.JOGRE_BONES, Activity.BIG_BONES, Activity.ZOGRE_BONES, Activity.SHAIKAHAN_BONES,
		Activity.BABYDRAGON_BONES, Activity.WYVERN_BONES, Activity.DRAGON_BONES, Activity.FAYRG_BONES,
		Activity.LAVA_DRAGON_BONES, Activity.RAURG_BONES, Activity.DAGANNOTH_BONES, Activity.OURG_BONES,
		Activity.SUPERIOR_DRAGON_BONES, Activity.WYRM_BONES, Activity.DRAKE_BONES, Activity.HYDRA_BONES,
		Activity.FROST_DRAGON_BONES, Activity.STRYKEWYRM_BONES_BURY
	);

	static final Set<Activity> ASHES = ImmutableSet.of(
		Activity.FIENDISH_ASHES, Activity.VILE_ASHES, Activity.MALICIOUS_ASHES, Activity.ABYSSAL_ASHES,
		Activity.INFERNAL_ASHES
	);

	private static final Collection<Activity> CONSTRUCTION_BONES = ImmutableSet.of(
		Activity.LONG_BONE, Activity.CURVED_BONE
	);

	private static final Collection<Activity> SALVAGE = ImmutableSet.of(
			Activity.SORT_SMALL_SALVAGE, Activity.SORT_FISHY_SALVAGE, Activity.SORT_BARRACUDA_SALVAGE,
			Activity.SORT_LARGE_SALVAGE, Activity.SORT_PIRATE_SALVAGE, Activity.SORT_MARTIAL_SALVAGE,
			Activity.SORT_FREMENNIK_SALVAGE, Activity.SORT_OPULENT_SALVAGE
	);

	public static void prepare(ItemManager manager)
	{
		assert modifiers.isEmpty();

		final Map<Integer, ItemComposition[]> compositions = new HashMap<>();

		final ItemComposition[] zealots = asCompositions(manager, ItemID.SHADES_PRAYER_HELM, ItemID.SHADES_PRAYER_TOP, ItemID.SHADES_PRAYER_BOTTOM, ItemID.SHADES_PRAYER_BOOTS);
		compositions.put(zealots[0].getId(), zealots);

		final ItemComposition[] farmers = asCompositions(manager, ItemID.TITHE_REWARD_HAT_MALE, ItemID.TITHE_REWARD_TORSO_MALE, ItemID.TITHE_REWARD_LEGS_MALE, ItemID.TITHE_REWARD_FEET_MALE);
		compositions.put(farmers[0].getId(), farmers);

		final ItemComposition[] carpenters = asCompositions(manager, ItemID.CONSTRUCTION_HAT, ItemID.CONSTRUCTION_SHIRT, ItemID.CONSTRUCTION_TROUSERS, ItemID.CONSTRUCTION_BOOTS);
		compositions.put(carpenters[0].getId(), carpenters);

		final ItemComposition[] pyromancer = asCompositions(manager, ItemID.PYROMANCER_HOOD, ItemID.PYROMANCER_TOP, ItemID.PYROMANCER_BOTTOM, ItemID.PYROMANCER_BOOTS);
		compositions.put(pyromancer[0].getId(), pyromancer);

		// Create modifiers on EDT thread as the UI components are created now
		SwingUtilities.invokeLater(() -> createModifiers(manager, compositions));
	}

	private static ItemComposition[] asCompositions(ItemManager manager, int... itemIds)
	{
		final ItemComposition[] results = new ItemComposition[itemIds.length];

		for (int i = 0; i < itemIds.length; i++)
		{
			results[i] = manager.getItemComposition(itemIds[i]);
		}

		return results;
	}

	private static void createModifiers(final ItemManager manager, final Map<Integer, ItemComposition[]> compositions)
	{
		// Prayer Modifiers
		addModifier(new ZealotsRobes(manager, compositions.get(ItemID.SHADES_PRAYER_HELM)));
		addModifier(new StaticModifier(Skill.PRAYER, "Demonic Offering (300% xp)", 3, ASHES, null, ASHES_TOOLTIP_TEXT));
		addModifier(new StaticModifier(Skill.PRAYER, "Sinister Offering (300% xp)", 3, BONES, null, BONES_TOOLTIP_TEXT));
		addModifier(new StaticModifier(Skill.PRAYER, "Lit Gilded Altar (350% xp)", 3.5f, BONES, null, BONES_TOOLTIP_TEXT));
		addModifier(new StaticModifier(Skill.PRAYER, "Ectofuntus (400% xp)", 4, BONES, Set.of(Activity.STRYKEWYRM_BONES_BURY), BONES_TOOLTIP_TEXT));
		addModifier(new ConsumptionModifier(Skill.PRAYER, "Wildy Altar (350% xp & 50% Save)", 0.5f, BONES, null, BONES_TOOLTIP_TEXT)
		{
			@Override
			public double appliedXpRate(final Activity activity)
			{
				return activity.getXp() * 3.5f;
			}

			@Override
			public boolean compatibleWith(final Modifier modifier)
			{
				if (modifier instanceof StaticModifier)
				{
					return !this.touchesSameActivity(modifier);
				}

				return super.compatibleWith(modifier);
			}
		});

		// Farming
		addModifier(new SkillingOutfit(Skill.FARMING, "Farmer's Outfit", null, null,
			manager, compositions.get(ItemID.TITHE_REWARD_HAT_MALE)));

		// Construction
		addModifier(new SkillingOutfit(Skill.CONSTRUCTION, "Carpenter's Outfit", null, CONSTRUCTION_BONES, manager, compositions.get(ItemID.CONSTRUCTION_HAT)));

		// Firemaking
		addModifier(new SkillingOutfit(Skill.FIREMAKING, "Pyromancer Outfit",
			null, null, manager, compositions.get(ItemID.PYROMANCER_HOOD)));

		// Sailing
		addModifier(new StaticModifier(Skill.SAILING, "Horizon's Lure (102.5% xp)", 1.025f, SALVAGE, null, null));
	}

	private static void addModifier(final Modifier modifier)
	{
		// Add on EDT thread since component creation takes place when the modifiers are created
		modifiers.put(modifier.getSkill(), modifier);
	}

	public static Collection<Modifier> getBySkill(final Skill skill)
	{
		return modifiers.get(skill);
	}
}
