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
import lombok.Getter;
import net.runelite.api.Skill;
import thestonedturtle.bankedexperience.data.Activity;

/**
 * Modifies the rate at which items are consumed. The same item can be saved multiple times so these modifiers must be
 * combined with other each other to properly calculate the average xp
 */
public class ConsumptionModifier extends Modifier
{
	// Percentage based scale where positive values reduce consumption and negative values increase consumption
	@Getter
	private final float consumptionModifier;

	ConsumptionModifier(Skill skill, String name, float consumptionModifier)
	{
		super(skill, name);
		this.consumptionModifier = consumptionModifier;
	}

	ConsumptionModifier(Skill skill, String name, float consumptionModifier, Collection<Activity> included, Collection<Activity> ignored)
	{
		super(skill, name, included, ignored);
		this.consumptionModifier = consumptionModifier;
	}
}
