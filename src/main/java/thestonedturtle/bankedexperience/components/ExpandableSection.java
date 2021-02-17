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

import com.google.common.collect.ImmutableList;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Collection;
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
import lombok.Setter;
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
	private static final ImageIcon SECTION_EXPAND_ICON;
	private static final ImageIcon SECTION_EXPAND_ICON_HOVER;
	private static final ImageIcon SECTION_RETRACT_ICON;
	private static final ImageIcon SECTION_RETRACT_ICON_HOVER;
	static
	{
		BufferedImage sectionRetractIcon = ImageUtil.loadImageResource(ConfigPlugin.class, "/util/arrow_right.png");
		sectionRetractIcon = ImageUtil.luminanceOffset(sectionRetractIcon, -121);
		SECTION_EXPAND_ICON = new ImageIcon(sectionRetractIcon);
		SECTION_EXPAND_ICON_HOVER = new ImageIcon(ImageUtil.alphaOffset(sectionRetractIcon, -100));
		final BufferedImage sectionExpandIcon = ImageUtil.rotateImage(sectionRetractIcon, Math.PI / 2);
		SECTION_RETRACT_ICON = new ImageIcon(sectionExpandIcon);
		SECTION_RETRACT_ICON_HOVER = new ImageIcon(ImageUtil.alphaOffset(sectionExpandIcon, -100));
	}
	private final JButton sectionToggle;
	private final JPanel sectionContents;

	@Setter
	@Getter
	private boolean isOpen = false;

	public ExpandableSection(final String header, final String description, JComponent... components)
	{
		this(header, description, ImmutableList.copyOf(components));
	}

	public ExpandableSection(final String header, final String description, Collection<JComponent> components)
	{
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setMinimumSize(new Dimension(PluginPanel.PANEL_WIDTH, 0));

		final JPanel sectionHeader = new JPanel();
		sectionHeader.setLayout(new BorderLayout());
		sectionHeader.setMinimumSize(new Dimension(PluginPanel.PANEL_WIDTH, 0));
		// For whatever reason, the header extends out by a single pixel when closed. Adding a single pixel of
		// border on the right only affects the width when closed, fixing the issue.
		sectionHeader.setBorder(new CompoundBorder(
			new MatteBorder(0, 0, 1, 0, ColorScheme.MEDIUM_GRAY_COLOR),
			new EmptyBorder(0, 0, 3, 1)));
		this.add(sectionHeader, BorderLayout.NORTH);

		sectionToggle = new JButton();
		sectionToggle.setIcon(isOpen ? SECTION_RETRACT_ICON : SECTION_EXPAND_ICON);
		sectionToggle.setRolloverIcon(isOpen ? SECTION_RETRACT_ICON_HOVER : SECTION_EXPAND_ICON_HOVER);
		sectionToggle.setPreferredSize(new Dimension(18, 0));
		sectionToggle.setBorder(new EmptyBorder(0, 0, 0, 5));
		sectionToggle.setToolTipText(isOpen ? "Retract" : "Expand");
		SwingUtil.removeButtonDecorations(sectionToggle);
		sectionHeader.add(sectionToggle, BorderLayout.WEST);

		final JLabel sectionName = new JLabel(header);
		sectionName.setForeground(ColorScheme.BRAND_ORANGE);
		sectionName.setFont(FontManager.getRunescapeBoldFont());
		if (description != null)
		{
			sectionName.setToolTipText("<html>" + header + ":<br>" + description + "</html>");
		}
		sectionHeader.add(sectionName, BorderLayout.CENTER);

		sectionContents = new JPanel();
		sectionContents.setLayout(new DynamicGridLayout(0, 1, 0, 5));
		sectionContents.setMinimumSize(new Dimension(PluginPanel.PANEL_WIDTH, 0));
		sectionContents.setBorder(new CompoundBorder(
			new MatteBorder(0, 0, 1, 0, ColorScheme.MEDIUM_GRAY_COLOR),
			new EmptyBorder(PluginPanel.BORDER_OFFSET, 0, PluginPanel.BORDER_OFFSET, 0)));
		sectionContents.setVisible(isOpen);
		for (final JComponent c : components)
		{
			sectionContents.add(c);
		}
		this.add(sectionContents, BorderLayout.SOUTH);

		// Add listeners to each part of the header so that it's easier to toggle them
		final MouseAdapter adapter = new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				toggle();
			}
		};
		sectionToggle.addActionListener(actionEvent -> toggle());
		sectionName.addMouseListener(adapter);
		sectionHeader.addMouseListener(adapter);
	}

	private void toggle()
	{
		isOpen = !isOpen;
		sectionToggle.setIcon(isOpen ? SECTION_RETRACT_ICON : SECTION_EXPAND_ICON);
		sectionToggle.setRolloverIcon(isOpen ? SECTION_RETRACT_ICON_HOVER : SECTION_EXPAND_ICON_HOVER);
		sectionToggle.setToolTipText(isOpen ? "Retract" : "Expand");
		sectionContents.setVisible(isOpen);
	}
}
