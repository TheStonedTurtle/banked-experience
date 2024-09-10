/*
 * Copyright (c) 2020, TheStonedTurtle <https://github.com/TheStonedTurtle>
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

import com.google.common.collect.ImmutableSet;
import java.util.Collection;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Skill;
import thestonedturtle.bankedexperience.data.Activity;
import thestonedturtle.bankedexperience.data.modifiers.ui.ModifierPanel;

/**
 * A modifier to the experience an {@link Activity} should reward
 */
@Getter
@Slf4j
public abstract class Modifier
{
	/**
	 * The {@link Skill} this modifier applies to
	 */
	private final Skill skill;
	/**
	 * The name of this modifier
	 */
	private final String name;
	/**
	 * A set of activities this modifier will apply to.
	 */
	private final ImmutableSet<Activity> includedActivities;
	/**
	 * A set of activities this modifier should not apply to. Overrides {@link #includedActivities}
	 */
	private final ImmutableSet<Activity> ignoredActivities;

	private final String tooltip;

	Modifier(Skill skill, String name)
	{
		this(skill, name, null, null, null);
	}

	Modifier(Skill skill, String name, String tooltip)
	{
		this(skill, name, null, null, tooltip);
	}
	Modifier(Skill skill, String name, Collection<Activity> included, Collection<Activity> ignored)
	{
		this(skill, name, included, ignored, null);
	}

	Modifier(Skill skill, String name, Collection<Activity> included, Collection<Activity> ignored, String tooltip)
	{
		this.skill = skill;
		this.name = name;
		this.includedActivities = included == null ? ImmutableSet.of() : ImmutableSet.copyOf(included);
		this.ignoredActivities = ignored == null ? ImmutableSet.of() : ImmutableSet.copyOf(ignored);
		this.tooltip = tooltip;
	}

	/**
	 * Checks if the modifier applies to the passed activity
	 * @param activity the {@link Activity} to check
	 * @return if the modifier applies to the activity
	 */
	public boolean appliesTo(final Activity activity)
	{
		return skill.equals(activity.getSkill())
			&& !ignoredActivities.contains(activity)
			&& (includedActivities.size() == 0 || includedActivities.contains(activity));
	}

	/**
	 * Applies the modifier to the default xp rate of the passed activity if applicable.
	 * @param activity the {@link Activity} to apply this modifier to.
	 * @return The adjusted xp rate for the activity. Returns 0 if the activity and modifier are not compatible
	 */
	public double appliedXpRate(final Activity activity)
	{
		if (!appliesTo(activity))
		{
			log.debug("Tried to modify an incompatible activity. Activity: {} | Modifier: {}", activity, this);
			return 0;
		}

		return activity.getXp();
	}

	/**
	 * Generates the UI component for use in the side panel for modifiers with complex settings.
	 * Returning null will generate a default UI components (generally a checkbox)
	 * @return A ModifierComponent to be added to the UI to control this modifier.
	 */
	public ModifierComponent generateModifierComponent()
	{
		return new ModifierPanel(this);
	}

	/**
	 * Determines if the current modifier is compatible with the passed one
	 * @param modifier Other modifier to check compatibility against
	 * @return Whether the modifiers are compatible
	 */
	public boolean compatibleWith(final Modifier modifier)
	{
		return true;
	}

	/**
	 * Determines if the current modifier and the passed modifier touch any of the same activities
	 * @param modifier Other modifier to check included activities against
	 * @return Whether the modifiers touch any of the same activities
	 */
	public boolean touchesSameActivity(final Modifier modifier)
	{
		if (!modifier.getSkill().equals(this.getSkill()))
		{
			return false;
		}

		// If either modifier includes all activities for this skill they are not compatible with any other modifier for the same skill
		if (modifier.getIncludedActivities().size() == 0 || this.getIncludedActivities().size() == 0)
		{
			return true;
		}

		// If they touch the same activity they are not compatible
		for (Activity a : modifier.getIncludedActivities())
		{
			if (this.getIncludedActivities().contains(a))
			{
				return true;
			}
		}

		return false;
	}
}
