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

import java.util.Collection;
import net.runelite.api.Skill;
import thestonedturtle.bankedexperience.data.Activity;

/**
 * Modifies an {@link Activity} by a static {@link #xpModifier}. StaticModifiers are not compatible with other StaticModifiers
 */
public class StaticModifier extends Modifier
{
	/**
	 * A multiplier that controls how much the activities xp is modified. 0=none, 0.5=half, 1=default, 2=double, etc
	 */
	final float xpModifier;

	StaticModifier(Skill skill, String name, final float xpModifier)
	{
		super(skill, name, null, null);
		this.xpModifier = xpModifier;
	}

	StaticModifier(Skill skill, String name, final float xpModifier, Collection<Activity> included, Collection<Activity> ignored)
	{
		super(skill, name, included, ignored);
		this.xpModifier = xpModifier;
	}

	/**
	 * Applies the {@link #xpModifier} to the default xp rate of the passed activity, if applicable.
	 * @param activity the {@link Activity} to apply this modifier to.
	 * @return The adjusted xp rate for the activity, or 0 if the activity and modifier are not compatible
	 */
	public double appliedXpRate(final Activity activity)
	{
		return super.appliedXpRate(activity) * xpModifier;
	}

	@Override
	public boolean compatibleWith(final Modifier modifier)
	{
		// Compatible with itself
		if (this.equals(modifier))
		{
			return true;
		}

		return !(modifier instanceof StaticModifier);
	}
}
