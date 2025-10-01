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
package thestonedturtle.bankedexperience.data.modifiers.ui;

import java.util.function.BiConsumer;
import javax.swing.JComponent;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import thestonedturtle.bankedexperience.data.modifiers.Modifier;
import thestonedturtle.bankedexperience.data.modifiers.ModifierComponent;

/**
 * The default UI component for enable/disabling a {@link Modifier}
 */
@Slf4j
public class ModifierPanel extends LabeledCheckbox implements ModifierComponent
{
	@Getter
	private final Modifier modifier;

	@Setter
	private BiConsumer<Modifier, Boolean> modifierConsumer;

	public ModifierPanel(final Modifier modifier)
	{
		super(modifier.getName());
		this.modifier = modifier;

		button.addItemListener((l) ->
		{
			if (modifierConsumer == null)
			{
				log.warn("Toggling a modifier wth no consumer: {}", modifier);
				return;
			}

			modifierConsumer.accept(modifier, getButton().isSelected());
		});

		if (modifier.getTooltip() != null)
		{
			this.setToolTipText(modifier.getTooltip());
		}
	}

	@Override
	public Boolean isModifierEnabled()
	{
		return getButton().isSelected();
	}

	@Override
	public void setModifierEnabled(boolean enabled)
	{
		getButton().setSelected(enabled);
	}

	@Override
	public JComponent getComponent()
	{
		return this;
	}
}
