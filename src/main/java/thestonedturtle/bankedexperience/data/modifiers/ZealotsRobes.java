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

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import net.runelite.api.ItemComposition;
import net.runelite.api.Skill;
import net.runelite.client.game.ItemManager;
import thestonedturtle.bankedexperience.data.Activity;

public class ZealotsRobes extends SkillingOutfit
{
	private static final float CONSUME_AMT = 0.0125f;
	private static final String TOOLTIP = "1.25% chance to prevent bones and ensouled heads from being consumed";
	private static final Set<Activity> EXCLUDED = ImmutableSet.of(
		Activity.LOAR_REMAINS, Activity.PHRIN_REMAINS, Activity.RIYL_REMAINS, Activity.ASYN_REMAINS, Activity.FIYR_REMAINS,
		Activity.SMALL_LIMBS, Activity.SMALL_SPINE, Activity.SMALL_RIBS, Activity.SMALL_PELVIS, Activity.SMALL_SKULL, Activity.SMALL_FOSSIL,
		Activity.MEDIUM_LIMBS, Activity.MEDIUM_SPINE, Activity.MEDIUM_RIBS, Activity.MEDIUM_PELVIS, Activity.MEDIUM_SKULL, Activity.MEDIUM_FOSSIL,
		Activity.LARGE_LIMBS, Activity.LARGE_SPINE, Activity.LARGE_RIBS, Activity.LARGE_PELVIS, Activity.LARGE_SKULL, Activity.LARGE_FOSSIL,
		Activity.RARE_LIMBS, Activity.RARE_SPINE, Activity.RARE_RIBS, Activity.RARE_PELVIS, Activity.RARE_SKULL, Activity.RARE_TUSK, Activity.RARE_FOSSIL
	);

	ZealotsRobes(ItemManager itemManager, ItemComposition... items)
	{
		super(Skill.PRAYER, "Zealot's robes", null, EXCLUDED, itemManager, items);
		additive = false;

		helm.setToolTipText("<html>" + items[0].getName() + "<br/>" + TOOLTIP + "</html>");
		top.setToolTipText("<html>" + items[1].getName() + "<br/>" + TOOLTIP + "</html>");
		bottom.setToolTipText("<html>" + items[2].getName() + "<br/>" + TOOLTIP + "</html>");
		boots.setToolTipText("<html>" + items[3].getName() + "<br/>" + TOOLTIP + "</html>");

		panel.setToolTipText(TOOLTIP);
	}

	@Override
	public float getSavePercentage()
	{
		return CONSUME_AMT * getEnabledButtonCount();
	}

	// Zealots Robes don't provide bonus xp so the SkillingOutfit xp multiplier is incorrect
	@Override
	protected double calculateBonusXPMultiplier()
	{
		return 1d;
	}
}
