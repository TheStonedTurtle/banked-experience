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
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.swing.SwingUtilities;
import net.runelite.api.ItemComposition;
import net.runelite.api.ItemID;
import net.runelite.api.Skill;
import net.runelite.client.game.ItemManager;
import thestonedturtle.bankedexperience.data.Activity;

/**
 * A utility class that contains all XP modifiers
 */
public final class Modifiers
{
	private static final Multimap<Skill, Modifier> modifiers = ArrayListMultimap.create();

	static final Set<Activity> BONES = ImmutableSet.of(
		Activity.BONES, Activity.WOLF_BONES, Activity.BURNT_BONES, Activity.MONKEY_BONES, Activity.BAT_BONES,
		Activity.JOGRE_BONES, Activity.BIG_BONES, Activity.ZOGRE_BONES, Activity.SHAIKAHAN_BONES,
		Activity.BABYDRAGON_BONES, Activity.WYVERN_BONES, Activity.DRAGON_BONES, Activity.FAYRG_BONES,
		Activity.LAVA_DRAGON_BONES, Activity.RAURG_BONES, Activity.DAGANNOTH_BONES, Activity.OURG_BONES,
		Activity.SUPERIOR_DRAGON_BONES, Activity.WYRM_BONES, Activity.DRAKE_BONES, Activity.HYDRA_BONES
	);

	static final Set<Activity> ASHES = ImmutableSet.of(
		Activity.FIENDISH_ASHES, Activity.VILE_ASHES, Activity.MALICIOUS_ASHES, Activity.ABYSSAL_ASHES,
		Activity.INFERNAL_ASHES
	);

	public static void prepare(ItemManager manager)
	{
		assert modifiers.size() == 0;

		final Map<Integer, ItemComposition[]> compositions = new HashMap<>();

		final ItemComposition[] zealots = asCompositions(manager, ItemID.ZEALOTS_HELM, ItemID.ZEALOTS_ROBE_TOP, ItemID.ZEALOTS_ROBE_BOTTOM, ItemID.ZEALOTS_BOOTS);
		compositions.put(zealots[0].getId(), zealots);

		final ItemComposition[] farmers = asCompositions(manager, ItemID.FARMERS_STRAWHAT, ItemID.FARMERS_JACKET, ItemID.FARMERS_BORO_TROUSERS, ItemID.FARMERS_BOOTS);
		compositions.put(farmers[0].getId(), farmers);

		final ItemComposition[] carpenters = asCompositions(manager, ItemID.CARPENTERS_HELMET, ItemID.CARPENTERS_SHIRT, ItemID.CARPENTERS_TROUSERS, ItemID.CARPENTERS_BOOTS);
		compositions.put(carpenters[0].getId(), carpenters);

		final ItemComposition[] pyromancer = asCompositions(manager, ItemID.PYROMANCER_HOOD, ItemID.PYROMANCER_GARB, ItemID.PYROMANCER_ROBE, ItemID.PYROMANCER_BOOTS);
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
		addModifier(new ZealotsRobes(manager, compositions.get(ItemID.ZEALOTS_HELM)));
		addModifier(new StaticModifier(Skill.PRAYER, "Demonic Offering (300% xp)", 3, ASHES, null));
		addModifier(new StaticModifier(Skill.PRAYER, "Lit Gilded Altar (350% xp)", 3.5f, BONES, null));
		addModifier(new StaticModifier(Skill.PRAYER, "Ectofuntus (400% xp)", 4, BONES, null));
		addModifier(new ConsumptionModifier(Skill.PRAYER, "Wildy Altar (350% xp & 50% Save)", 0.5f, BONES, null)
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
			manager, compositions.get(ItemID.FARMERS_STRAWHAT)));

		// Construction
		addModifier(new CarpentersOutfit(manager, compositions.get(ItemID.CARPENTERS_HELMET)));

		// Firemaking
		addModifier(new SkillingOutfit(Skill.FIREMAKING, "Pyromancer Outfit",
			null, null, manager, compositions.get(ItemID.PYROMANCER_HOOD)));
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
