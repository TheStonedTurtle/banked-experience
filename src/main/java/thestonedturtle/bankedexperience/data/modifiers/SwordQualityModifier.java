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

import net.runelite.api.Skill;
import thestonedturtle.bankedexperience.data.Activity;

/**
 * Used to calculate the XP of a sword made in the foundry, based on whether the player has unlocked all moulds. 
 * This modifier is applied to the average base score of swords made in the foundry, resulting in a different amount of
 * experience per bar.
 */
public class SwordQualityModifier extends Modifier
{
	//Represents the average mould score of a sword made in the foundry, when all moulds are unlocked, and 
	//the optimal moulds are used for a given sword.
	private static final int AVERAGE_MOULD_SCORE = 59;

	SwordQualityModifier(final Skill skill, final String name, final String tooltip)
	{
		super(skill, name, null, null, tooltip);
	}

	@Override
	public boolean appliesTo(final Activity activity)
	{
		return activity.isFoundryActivity() && super.appliesTo(activity);
	}

	@Override
	public double appliedXpRate(final Activity activity)
	{
		if (!appliesTo(activity))
		{
			return activity.getXp();
		}

		return activity.getFoundryXpPerBar(AVERAGE_MOULD_SCORE);
	}

}
