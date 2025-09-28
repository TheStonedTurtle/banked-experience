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
package thestonedturtle.bankedexperience.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Collection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.plugins.config.ConfigPlugin;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.DynamicGridLayout;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.util.SwingUtil;

/**
 * Copy of RuneLite's ConfigPanel Sections
 */
public class ExpandableSection extends JPanel
{

	@Slf4j
	private static final class ExpandableSectionIconConstants 
	{

		private final ImageIcon EXPAND = new ImageIcon();
		private final ImageIcon EXPAND_HOVER = new ImageIcon();

		private final ImageIcon RETRACT = new ImageIcon();
		private final ImageIcon RETRACT_HOVER = new ImageIcon();

		@Nullable
		private static final BufferedImage loadArrowRightImage() 
		{
			try
			{
				final BufferedImage baseImage = ImageUtil.loadImageResource(ConfigPlugin.class, "/util/arrow_right.png");
				final BufferedImage modifiedImage = ImageUtil.luminanceOffset(baseImage, -121);

				if (baseImage == null || modifiedImage == null) {
					throw new AssertionError("The icon failed to load!");
				}

				return modifiedImage;
			}
			catch (final Exception e) 
			{
				log.debug("Unable to generate/find expandable section icon data ", e);
				return null;
			}
		}

		@Nonnull
		private static final BufferedImage generateHoverImage(@Nonnull BufferedImage image) 
		{
			return ImageUtil.alphaOffset(image, -100);
		}

		ExpandableSectionIconConstants() 
		{
			final BufferedImage arrow_right_image = loadArrowRightImage();
			if (arrow_right_image != null) 
			{
				EXPAND.setImage(arrow_right_image);
				EXPAND_HOVER.setImage(generateHoverImage(arrow_right_image));

				final BufferedImage arrow_left_image = ImageUtil.rotateImage(arrow_right_image, Math.PI / 2.0);
				RETRACT.setImage(arrow_left_image);
				RETRACT_HOVER.setImage(generateHoverImage(arrow_left_image));
			}
		}
	}
	
	private static final ExpandableSectionIconConstants ICON = new ExpandableSectionIconConstants();

	private static final String EXPAND_TOOLTIP_TEXT = "Expand";
	private static final String RETRACT_TOOLTIP_TEXT = "Retract";
	
	@Nonnull
	private static final JPanel generateSectionHeader() 
	{
		final JPanel sectionHeader = new JPanel();
		sectionHeader.setLayout(new BorderLayout());
		sectionHeader.setMinimumSize(new Dimension(PluginPanel.PANEL_WIDTH, 0));
		// For whatever reason, the header extends out by a single pixel when closed.
		// Adding a single pixel of
		// border on the right only affects the width when closed, fixing the issue.
		sectionHeader.setBorder(new CompoundBorder(
				new MatteBorder(0, 0, 1, 0, ColorScheme.MEDIUM_GRAY_COLOR),
				new EmptyBorder(0, 0, 3, 1)));

		return sectionHeader;
	}

	@Nonnull
	private static JButton generateSectionToggle(final boolean currently_open)
	{
		final JButton sectionToggle = new JButton();
		sectionToggle.setIcon(currently_open ? ICON.RETRACT : ICON.EXPAND);
		sectionToggle.setRolloverIcon(currently_open ? ICON.RETRACT_HOVER : ICON.EXPAND_HOVER);
		sectionToggle.setPreferredSize(new Dimension(18, 0));
		sectionToggle.setBorder(new EmptyBorder(0, 0, 0, 5));
		sectionToggle.setToolTipText(currently_open ? RETRACT_TOOLTIP_TEXT : EXPAND_TOOLTIP_TEXT);
		SwingUtil.removeButtonDecorations(sectionToggle);

		return sectionToggle;
	}

	@Nonnull
	private static JLabel generateSectionName(final @Nonnull String header, final @Nullable String description) 
	{
		final JLabel sectionName = new JLabel(header);
		sectionName.setForeground(ColorScheme.BRAND_ORANGE);
		sectionName.setFont(FontManager.getRunescapeBoldFont());
		if (description != null) 
		{
			sectionName.setToolTipText("<html>" + header + ":<br>" + description + "</html>");
		}

		return sectionName;
	}
	
	@Nonnull
	private static final JPanel generateSectionContents(final boolean currently_open, final @Nullable Collection<JComponent> components)
	{
		final JPanel sectionContents = new JPanel();
		sectionContents.setLayout(new DynamicGridLayout(0, 1, 0, 5));
		sectionContents.setMinimumSize(new Dimension(PluginPanel.PANEL_WIDTH, 0));
		sectionContents.setBorder(new CompoundBorder(
				new MatteBorder(0, 0, 1, 0, ColorScheme.MEDIUM_GRAY_COLOR),
				new EmptyBorder(PluginPanel.BORDER_OFFSET, 0, PluginPanel.BORDER_OFFSET, 0))
		);
		sectionContents.setVisible(true);

		if (components != null)
		{
			for (final JComponent c : components) {
				sectionContents.add(c);
			}
		}

		return sectionContents;
	}

	private final MouseAdapter toggleMouseAdapter = new MouseAdapter() 
	{
		@Override
		public void mouseClicked(final MouseEvent e) 
		{
			toggle();
		}
	};

	private final JButton sectionToggle;
	private final JPanel sectionContents;

	@Getter
	private boolean isOpen = true;

	public ExpandableSection(@Nonnull final String header, @Nullable final String description, @Nullable Collection<JComponent> components)
	{
		//TODO: Refactor Section header and section construction into child classes
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setMinimumSize(new Dimension(PluginPanel.PANEL_WIDTH, 0));

		// Initialize components
		sectionToggle = generateSectionToggle(isOpen);

		final JLabel sectionName = generateSectionName(header, description);

		final JPanel sectionHeader = generateSectionHeader();
		sectionHeader.add(sectionToggle, BorderLayout.WEST);
		sectionHeader.add(sectionName, BorderLayout.CENTER);

		sectionContents = generateSectionContents(isOpen, components);

		//Layout
		add(sectionHeader, BorderLayout.NORTH);
		add(sectionContents, BorderLayout.SOUTH);

		// Add listeners to each part of the header so that it's easier to toggle them
		sectionToggle.addActionListener(actionEvent -> toggle());
		sectionName.addMouseListener(toggleMouseAdapter);
		sectionHeader.addMouseListener(toggleMouseAdapter);
	}

	private void toggle()
	{
		isOpen = !isOpen;
		sectionToggle.setIcon(isOpen ? ICON.RETRACT : ICON.EXPAND);
		sectionToggle.setRolloverIcon(isOpen ? ICON.RETRACT_HOVER : ICON.EXPAND_HOVER);
		sectionToggle.setToolTipText(isOpen ? RETRACT_TOOLTIP_TEXT : EXPAND_TOOLTIP_TEXT);
		sectionContents.setVisible(isOpen);
	}

	public void setOpen(final boolean open)
	{
		if (isOpen != open)
		{
			toggle();
		}
	}
}
