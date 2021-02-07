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
import com.google.common.primitives.Booleans;
import java.awt.event.ItemListener;
import java.util.Set;
import java.util.function.BiConsumer;
import javax.swing.JComponent;
import javax.swing.JPanel;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Skill;
import net.runelite.client.ui.DynamicGridLayout;
import thestonedturtle.bankedexperience.components.LabeledCheckbox;
import thestonedturtle.bankedexperience.data.Activity;

@Slf4j
public class ZealotsRobes extends ConsumptionModifier implements ModifierComponent
{
	private static final float CONSUME_AMT = 0.0125f;
	private static final String TOOLTIP = "<html>1.25% chance to prevent bones and ensouled heads from being consumed</html>";
	private static final Set<Activity> EXCLUDED = ImmutableSet.of(
		Activity.LOAR_REMAINS, Activity.PHRIN_REMAINS, Activity.RIYL_REMAINS, Activity.ASYN_REMAINS, Activity.FIYR_REMAINS,
		Activity.SMALL_LIMBS, Activity.SMALL_SPINE, Activity.SMALL_RIBS, Activity.SMALL_PELVIS, Activity.SMALL_SKULL, Activity.SMALL_FOSSIL,
		Activity.MEDIUM_LIMBS, Activity.MEDIUM_SPINE, Activity.MEDIUM_RIBS, Activity.MEDIUM_PELVIS, Activity.MEDIUM_SKULL, Activity.MEDIUM_FOSSIL,
		Activity.LARGE_LIMBS, Activity.LARGE_SPINE, Activity.LARGE_RIBS, Activity.LARGE_PELVIS, Activity.LARGE_SKULL, Activity.LARGE_FOSSIL,
		Activity.RARE_LIMBS, Activity.RARE_SPINE, Activity.RARE_RIBS, Activity.RARE_PELVIS, Activity.RARE_SKULL, Activity.RARE_TUSK, Activity.RARE_FOSSIL
	);

	private final JPanel container;
	private final LabeledCheckbox helm;
	private final LabeledCheckbox top;
	private final LabeledCheckbox bottom;
	private final LabeledCheckbox boots;

	@Setter
	private BiConsumer<Modifier, Boolean> modifierConsumer;

	ZealotsRobes()
	{
		super(Skill.PRAYER, "Zealot's robes", 0, null, EXCLUDED);

		final ItemListener listener = (l) ->
		{
			if (modifierConsumer == null)
			{
				log.warn("Toggling ZealotsRobes modifier wth no consumer: {}", this);
				return;
			}

			modifierConsumer.accept(this, isModifierEnabled());
		};

		helm = new LabeledCheckbox("Zealot's helm (1.25% Save)");
		helm.setToolTipText(TOOLTIP);
		helm.getButton().addItemListener(listener);

		top = new LabeledCheckbox("Zealot's robe top (1.25% Save)");
		top.setToolTipText(TOOLTIP);
		top.getButton().addItemListener(listener);

		bottom = new LabeledCheckbox("Zealot's robe bottom (1.25% Save)");
		bottom.setToolTipText(TOOLTIP);
		bottom.getButton().addItemListener(listener);

		boots = new LabeledCheckbox("Zealot's boots (1.25% Save)");
		boots.setToolTipText(TOOLTIP);
		boots.getButton().addItemListener(listener);

		container = new JPanel();
		container.setLayout(new DynamicGridLayout(0, 1, 0, 5));
		container.add(helm);
		container.add(top);
		container.add(bottom);
		container.add(boots);
	}

	private int getEnabledButtonCount()
	{
		return Booleans.countTrue(
			helm.getButton().isSelected(), top.getButton().isSelected(),
			bottom.getButton().isSelected(), boots.getButton().isSelected()
		);
	}

	@Override
	public float getConsumptionModifier()
	{
		return CONSUME_AMT * getEnabledButtonCount();
	}

	@Override
	public Modifier getModifier()
	{
		return this;
	}

	@Override
	public Boolean isModifierEnabled()
	{
		return getEnabledButtonCount() > 0;
	}

	@Override
	public void setModifierEnabled(boolean enabled)
	{
		helm.getButton().setSelected(enabled);
		top.getButton().setSelected(enabled);
		bottom.getButton().setSelected(enabled);
		boots.getButton().setSelected(enabled);
	}

	@Override
	public JComponent getComponent()
	{
		return container;
	}

	@Override
	public ModifierComponent generateModifierComponent()
	{
		return this;
	}
}
