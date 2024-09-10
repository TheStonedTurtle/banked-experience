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

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import lombok.Getter;
import lombok.Setter;
import net.runelite.client.ui.ColorScheme;

public class SelectableLabel extends JLabel
{
	private static final Color SELECTED_BACKGROUND = new Color(0, 70, 0);
	private static final Color SELECTED_HOVER_BACKGROUND =  new Color(0, 100, 0);

	private static final Color BACKGROUND = ColorScheme.DARK_GRAY_COLOR;
	private static final Color BACKGROUND_HOVER = ColorScheme.DARK_GRAY_HOVER_COLOR;

	@Getter
	private boolean selected = false;

	@Setter
	private Runnable callback = null;

	public SelectableLabel()
	{
		this.setOpaque(true);
		this.setVerticalAlignment(SwingConstants.CENTER);
		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.setBackground(BACKGROUND);

		this.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				if (e.getButton() != MouseEvent.BUTTON1)
				{
					return;
				}

				setSelected(!selected);
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
				final SelectableLabel label = (SelectableLabel) e.getSource();
				if (selected)
				{
					label.setBackground(SELECTED_HOVER_BACKGROUND);
				}
				else
				{
					label.setBackground(BACKGROUND_HOVER);
				}
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				final SelectableLabel label = (SelectableLabel) e.getSource();
				if (selected)
				{
					label.setBackground(SELECTED_BACKGROUND);
				}
				else
				{
					label.setBackground(BACKGROUND);
				}
			}
		});
	}

	public void setSelected(boolean selected)
	{
		setSelected(selected, true);
	}

	public void setSelected(boolean selected, boolean runCallback)
	{
		if (this.selected == selected)
		{
			return;
		}

		this.selected = selected;
		this.setBackground(selected ? SELECTED_BACKGROUND : BACKGROUND);

		if (runCallback && callback != null)
		{
			callback.run();
		}
	}
}
