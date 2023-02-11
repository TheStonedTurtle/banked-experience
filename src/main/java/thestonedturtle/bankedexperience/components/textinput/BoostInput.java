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
package thestonedturtle.bankedexperience.components.textinput;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.text.ParseException;
import java.util.function.Consumer;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

public class BoostInput extends JPanel
{
	private final JSpinner spinner;

	public BoostInput(Consumer<Integer> callback)
	{
		setLayout(new GridLayout(0, 1, 7, 7));
		setBorder(new EmptyBorder(0, 0, 5, 0));

		final JPanel container = new JPanel();
		container.setLayout(new BorderLayout());

		final JLabel uiLabel = new JLabel("Temporary Boost:");
		uiLabel.setForeground(Color.WHITE);
		uiLabel.setToolTipText("Enable activities that are this many levels above your current level");

		container.add(uiLabel, BorderLayout.CENTER);

		final SpinnerModel model = new SpinnerNumberModel(0, 0, 99, 1);
		spinner = new JSpinner(model);
		final Component editor = spinner.getEditor();
		final JFormattedTextField spinnerTextField = ((JSpinner.DefaultEditor) editor).getTextField();
		spinnerTextField.setColumns(6);
		spinner.addChangeListener(ce -> callback.accept(getInputValue()));

		container.add(spinner, BorderLayout.EAST);

		add(container);
	}

	public int getInputValue()
	{
		try
		{
			spinner.commitEdit();
			return (Integer) spinner.getValue();
		}
		catch (NumberFormatException | ParseException e)
		{
			return 0;
		}
	}

	public void setInputValue(int value)
	{
		spinner.getModel().setValue(value);
		spinner.setValue(value);
	}
}
