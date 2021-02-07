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

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import java.util.Collection;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Skill;
import thestonedturtle.bankedexperience.data.Activity;

/**
 * A utility class that contains all XP modifiers
 */
@Slf4j
public final class Modifiers
{
	private static final ImmutableMultimap<Skill, Modifier> modifiers;

	static final Set<Activity> BONES = ImmutableSet.of(
		Activity.BONES, Activity.WOLF_BONES, Activity.BURNT_BONES, Activity.MONKEY_BONES, Activity.BAT_BONES,
		Activity.JOGRE_BONES, Activity.BIG_BONES, Activity.ZOGRE_BONES, Activity.SHAIKAHAN_BONES,
		Activity.BABYDRAGON_BONES, Activity.WYVERN_BONES, Activity.DRAGON_BONES, Activity.FAYRG_BONES,
		Activity.LAVA_DRAGON_BONES, Activity.RAURG_BONES, Activity.DAGANNOTH_BONES, Activity.OURG_BONES,
		Activity.SUPERIOR_DRAGON_BONES, Activity.WYRM_BONES, Activity.DRAKE_BONES, Activity.HYDRA_BONES
	);

	static
	{
		final ImmutableMultimap.Builder<Skill, Modifier> m = ImmutableMultimap.builder();

		m.put(Skill.PRAYER, new StaticModifier(Skill.PRAYER, "Lit Gilded Altar (350% xp)", 3.5f));
		m.put(Skill.PRAYER, new StaticModifier(Skill.PRAYER, "Ectofuntus (400% xp)", 4, BONES, null));
		// TODO: Create a generic ComboModifier and combine a Static and Consumption modifier instead of doing this?
		m.put(Skill.PRAYER, new ConsumptionModifier(Skill.PRAYER, "Wildy Altar (350% xp/50% Save)", 0.5f, BONES, null)
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
					return false;
				}

				return super.compatibleWith(modifier);
			}
		});
		m.put(Skill.PRAYER, new ZealotsRobes());

		m.put(Skill.FARMING, new StaticModifier(Skill.FARMING, "Farmer's Outfit (2.5% xp)", 1.025f));

		m.put(Skill.CONSTRUCTION, new StaticModifier(Skill.CONSTRUCTION, "Carpenter's Outfit (2.5% xp)", 1.025f));

		modifiers = m.build();
	}

	public static Collection<Modifier> getBySkill(final Skill skill)
	{
		return modifiers.get(skill);
	}
}
