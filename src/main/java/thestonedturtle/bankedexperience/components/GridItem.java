/*
 * Copyright (c) 2019, TheStonedTurtle <https://github.com/TheStonedTurtle>
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
package thestonedturtle.bankedexperience.components;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.function.Consumer;

import javax.annotation.Nonnull;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lombok.Setter;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.util.AsyncBufferedImage;
import thestonedturtle.bankedexperience.BankedCalculator;
import thestonedturtle.bankedexperience.data.Activity;
import thestonedturtle.bankedexperience.data.BankedItem;
import thestonedturtle.bankedexperience.data.modifiers.Modifier;

@Getter
@SuppressFBWarnings(value = { "SE_BAD_FIELD" }, justification = "Plugin usage does not involve serialization")
public class GridItem extends JLabel
{
	private final static String IGNORE_TEXT = "Ignore Item";
	private final static String INCLUDE_TEXT = "Include Item";
	private final static String IGNORE_ALL_TEXT = "Ignore All Items";
	private final static String INCLUDE_ALL_TEXT = "Include All Items";

	private static final Color UNSELECTED_BACKGROUND = ColorScheme.DARKER_GRAY_COLOR;
	private static final Color UNSELECTED_HOVER_BACKGROUND = ColorScheme.DARKER_GRAY_HOVER_COLOR;

	public static final Color SELECTED_BACKGROUND = new Color(0, 70, 0);
	private static final Color SELECTED_HOVER_BACKGROUND = new Color(0, 100, 0);

	public static final Color IGNORED_BACKGROUND = new Color(90, 0, 0);
	private static final Color IGNORED_HOVER_BACKGROUND = new Color(120, 0, 0);

	private static final Color RNG_BACKGROUND = new Color(140, 90, 0);
	private static final Color RNG_HOVER_BACKGROUND = new Color(186, 120, 0);

	@Nonnull
	private static final JMenuItem setupMenuItem(@Nonnull JMenuItem menuItem, @Nonnull final String text, @Nonnull final ActionListener listener)
	{
		menuItem.setText(text);
		menuItem.addActionListener(listener);
		
		return menuItem;
	}

	private final MouseAdapter defaultMouseAdapter = new MouseAdapter()
	{
		public void mousePressed(final MouseEvent mouseEvent)
		{
			if (mouseEvent.getButton() == MouseEvent.BUTTON1)
			{
				if (selectionListener != null && !selectionListener.selected(bankedItem))
				{
					return;
				}
				select();
			}
		}

		public void mouseEntered(final MouseEvent e)
		{
			final GridItem source = (GridItem) e.getSource();
			source.setBackground(getHoverBackgroundColor());
		}

		public void mouseExited(final MouseEvent e)
		{
			final GridItem source = (GridItem) e.getSource();
			source.setBackground(getBackgroundColor());
		}
	};

	private final JMenuItem IGNORE_OPTION = new JMenuItem(), IGNORE_ALL_OPTION = new JMenuItem(), INCLUDE_ALL_OPTION = new JMenuItem();
	private final BankedItem bankedItem;
	private final int xpRateModifier;

	@Setter
	private SelectionListener selectionListener;

	private int amount;

	private boolean selected = false;
	private boolean ignored;
	private boolean rng;

	GridItem(@Nonnull final BankedItem item, final AsyncBufferedImage icon, final int amount,
			final Collection<Modifier> modifiers, final boolean ignore, Consumer<Boolean> bulkIgnoreCallback, final int xpRateModifier)
	{
		super("");
		this.bankedItem = item;

		setupMenuItem(IGNORE_OPTION, IGNORE_TEXT, e -> 
		{
			ignored = !ignored;
			if (selectionListener != null && !selectionListener.ignored(item)) 
			{
				ignored = !ignored;
				return;
			}

			setIgnore(ignored);
		});

		setupMenuItem(IGNORE_ALL_OPTION, IGNORE_ALL_TEXT, e -> 
		{
			bulkIgnoreCallback.accept(true);
		});

		setupMenuItem(INCLUDE_ALL_OPTION, INCLUDE_ALL_TEXT, e -> 
		{
			bulkIgnoreCallback.accept(false);
		});

		setIgnore(ignore);

		this.setOpaque(true);
		this.setBorder(BorderFactory.createEmptyBorder(5, 0, 2, 0));

		this.setVerticalAlignment(SwingConstants.CENTER);
		this.setHorizontalAlignment(SwingConstants.CENTER);

		this.xpRateModifier = xpRateModifier;

		updateIcon(icon, amount);
		updateToolTip(modifiers);

		this.addMouseListener(defaultMouseAdapter);

		final JPopupMenu popupMenu = new JPopupMenu();
		popupMenu.setBorder(new EmptyBorder(5, 5, 5, 5));
		popupMenu.add(IGNORE_OPTION);
		popupMenu.add(INCLUDE_ALL_OPTION);
		popupMenu.add(IGNORE_ALL_OPTION);

		this.setComponentPopupMenu(popupMenu);
	}

	void select()
	{
		selected = true;
		setBackground(getBackgroundColor());
	}

	void unselect()
	{
		selected = false;
		setBackground(getBackgroundColor());
	}

	public void updateIcon(final AsyncBufferedImage icon, final int amount)
	{
		icon.addTo(this);
		this.amount = amount;
	}

	public void updateToolTip(final Collection<Modifier> modifiers)
	{
		this.setToolTipText(buildToolTip(modifiers));
		final Activity selectedActivity = bankedItem.getItem().getSelectedActivity();
		if (selectedActivity != null)
		{
			this.rng = selectedActivity.isRngActivity();
			this.setBackground(getBackgroundColor());
		}
	}

	public void setIgnore(boolean ignored) 
	{
		this.ignored = ignored;
		IGNORE_OPTION.setText(ignored ? INCLUDE_TEXT : IGNORE_TEXT);
		this.setBackground(this.getBackgroundColor());
	}

	private Color getBackgroundColor() 
	{
		return ignored ? IGNORED_BACKGROUND
				: (rng ? RNG_BACKGROUND
				: (selected ? SELECTED_BACKGROUND
				: UNSELECTED_BACKGROUND));
	}

	private Color getHoverBackgroundColor() 
	{
		return ignored ? IGNORED_HOVER_BACKGROUND
				: (rng ? RNG_HOVER_BACKGROUND
				: (selected ? SELECTED_HOVER_BACKGROUND
				: UNSELECTED_HOVER_BACKGROUND));
	}

	private String buildToolTip(final Collection<Modifier> modifiers)
	{
		final StringBuilder tooltipBuilder = new StringBuilder("<html>");

		tooltipBuilder.append(bankedItem.getItem().getItemInfo().getName());

		final Activity a = bankedItem.getItem().getSelectedActivity();
		if (a != null)
		{
			final double xp = a.getXpRate(modifiers) * (double) xpRateModifier;
			
			tooltipBuilder.append("<br/>Activity: ");
			tooltipBuilder.append(a.getName());

			tooltipBuilder.append("<br/>Xp/Action: ");
			tooltipBuilder.append(BankedCalculator.XP_FORMAT_COMMA.format(xp));

			tooltipBuilder.append("<br/>Total Xp: ");
			tooltipBuilder.append(BankedCalculator.XP_FORMAT_COMMA.format(xp * amount));
		}
		else
		{
			tooltipBuilder.append("<br/>Unusable at current level");
		}

		tooltipBuilder.append("</html>");

		return tooltipBuilder.toString();
	}
}
