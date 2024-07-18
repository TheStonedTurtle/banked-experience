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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ItemEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import lombok.Getter;
import net.runelite.api.Constants;
import net.runelite.api.ItemID;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.components.shadowlabel.JShadowedLabel;
import net.runelite.client.util.AsyncBufferedImage;
import thestonedturtle.bankedexperience.BankedCalculator;
import thestonedturtle.bankedexperience.components.combobox.ComboBoxIconEntry;
import thestonedturtle.bankedexperience.components.combobox.ComboBoxIconListRenderer;
import thestonedturtle.bankedexperience.data.Activity;
import thestonedturtle.bankedexperience.data.BankedItem;
import thestonedturtle.bankedexperience.data.ExperienceItem;
import thestonedturtle.bankedexperience.data.ItemStack;
import thestonedturtle.bankedexperience.data.Secondaries;

public class ModifyPanel extends JPanel
{
	private static final Dimension ICON_SIZE = new Dimension(Constants.ITEM_SPRITE_WIDTH + 4, Constants.ITEM_SPRITE_HEIGHT);
	private static final DecimalFormat FORMAT_COMMA = new DecimalFormat("#,###.##");

	private static final Border PANEL_BORDER = new EmptyBorder(3, 0, 3, 0);
	private static final Color BACKGROUND_COLOR = ColorScheme.DARKER_GRAY_COLOR;

	private final BankedCalculator calc;
	private final ItemManager itemManager;

	@Getter
	private BankedItem bankedItem;
	private Map<ExperienceItem, Integer> linkedMap;
	@Getter
	private int amount = 0;
	@Getter
	private double total = 0;

	// Banked item information display
	private final JPanel labelContainer;
	private final JLabel image;
	private final JShadowedLabel labelName;
	private final JShadowedLabel labelValue;

	// Elements used to adjust banked item
	private final JPanel adjustContainer;

	public ModifyPanel(final BankedCalculator calc, final ItemManager itemManager)
	{
		this.calc = calc;
		this.itemManager = itemManager;

		this.setLayout(new GridBagLayout());
		this.setBorder(PANEL_BORDER);
		this.setBackground(ColorScheme.DARK_GRAY_COLOR);

		// Banked item information display
		labelContainer = new JPanel();
		labelContainer.setLayout(new BorderLayout());
		labelContainer.setBackground(BACKGROUND_COLOR);
		labelContainer.setBorder(new EmptyBorder(5, 0, 5, 0));

		// Icon
		image = new JLabel();
		image.setMinimumSize(ICON_SIZE);
		image.setMaximumSize(ICON_SIZE);
		image.setPreferredSize(ICON_SIZE);
		image.setHorizontalAlignment(SwingConstants.CENTER);
		image.setBorder(new EmptyBorder(0, 8, 0, 0));

		// Wrapper panel for the shadowed labels
		final JPanel uiInfo = new JPanel(new GridLayout(2, 1));
		uiInfo.setBorder(new EmptyBorder(0, 5, 0, 0));
		uiInfo.setBackground(BACKGROUND_COLOR);

		labelName = new JShadowedLabel();
		labelName.setForeground(Color.WHITE);
		labelName.setVerticalAlignment(SwingUtilities.BOTTOM);

		labelValue = new JShadowedLabel();
		labelValue.setFont(FontManager.getRunescapeSmallFont());
		labelValue.setVerticalAlignment(SwingUtilities.TOP);

		uiInfo.add(labelName);
		uiInfo.add(labelValue);

		// Append elements to item info panel
		labelContainer.add(image, BorderLayout.LINE_START);
		labelContainer.add(uiInfo, BorderLayout.CENTER);

		// Container for tools to adjust banked calculation for this item
		adjustContainer = new JPanel();
		adjustContainer.setLayout(new GridBagLayout());
		adjustContainer.setBackground(BACKGROUND_COLOR);

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.gridx = 0;
		c.gridy = 0;
		c.ipady = 0;

		this.add(labelContainer, c);
		c.gridy++;
		this.add(adjustContainer, c);
	}

	// Updates the UI for the selected item
	public void setBankedItem(final BankedItem bankedItem)
	{
		if (bankedItem == null)
		{
			return;
		}

		this.bankedItem = bankedItem;
		this.amount = this.calc.getItemQty(bankedItem);
		this.linkedMap = this.calc.getConfig().cascadeBankedXp() ? this.calc.createLinksMap(bankedItem) : new HashMap<>();

		updateImageTooltip();
		updateLabelContainer();
		updateAdjustContainer();
	}

	private void updateImageTooltip()
	{
		final StringBuilder b = new StringBuilder("<html>");
		b.append(bankedItem.getQty()).append(" x ").append(bankedItem.getItem().getItemInfo().getName());

		for (final Map.Entry<ExperienceItem, Integer> e : this.linkedMap.entrySet())
		{
			b.append("<br/>").append(e.getValue()).append(" x ").append(e.getKey().getItemInfo().getName());
			final ItemStack output = e.getKey().getSelectedActivity().getOutput();
			if (output != null && output.getQty() > 1)
			{
				b.append(" x ").append(output.getQty());
			}
		}

		b.append("</html>");
		this.image.setToolTipText(b.toString());
	}

	private void updateLabelContainer()
	{
		final ExperienceItem item = bankedItem.getItem();

		// Update image icon
		final boolean stackable = item.getItemInfo().isStackable() || amount > 1;
		final AsyncBufferedImage icon = itemManager.getImage(item.getItemID(), amount, stackable);
		final Runnable resize = () -> image.setIcon(new ImageIcon(icon.getScaledInstance(Constants.ITEM_SPRITE_WIDTH, ICON_SIZE.height, Image.SCALE_SMOOTH)));
		icon.onLoaded(resize);
		resize.run();

		final String itemName = item.getItemInfo().getName();
		labelName.setText(itemName);

		final double xp = calc.getItemXpRate(bankedItem);
		// Round to two decimal places
		total = BigDecimal.valueOf(amount * xp).setScale(2, RoundingMode.HALF_UP).doubleValue();

		final String value = FORMAT_COMMA.format(total) + "xp";
		labelValue.setText(value);

		labelContainer.setToolTipText("<html>" + itemName
			+ "<br/>xp: " +  xp
			+ "<br/>Total: " + total +	"</html>");

		labelContainer.revalidate();
		labelContainer.repaint();
	}

	private void updateAdjustContainer()
	{
		adjustContainer.removeAll();

		final JLabel label = new JLabel("Activity:");
		label.setVerticalAlignment(JLabel.CENTER);
		label.setHorizontalAlignment(JLabel.CENTER);

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.gridx = 0;
		c.gridy = 0;
		c.ipady = 4;

		adjustContainer.add(label, c);
		c.gridy++;

		final int level = calc.getConfig().limitToCurrentLevel() ? (calc.getSkillLevel() + calc.getBoostInput().getInputValue()) : -1;
		final List<Activity> activities = Activity.getByExperienceItem(bankedItem.getItem(), level);
		if (activities == null || activities.size() == 0)
		{
			final JLabel unusable = new JLabel("Unusable at current level");
			unusable.setVerticalAlignment(JLabel.CENTER);
			unusable.setHorizontalAlignment(JLabel.CENTER);

			adjustContainer.removeAll();
			adjustContainer.add(unusable, c);
			return;
		}
		else if (activities.size() == 1)
		{
			final Activity a = activities.get(0);

			final int qty =  a.getOutput() == null ? 1 : (int) a.getOutput().getQty();
			final boolean stackable = a.getOutputItemInfo() == null ? qty > 1 : a.getOutputItemInfo().isStackable();
			final AsyncBufferedImage img = itemManager.getImage(a.getIcon(), qty, stackable);
			final ImageIcon icon = new ImageIcon(img);
			final double xp = a.getXpRate(calc.getEnabledModifiers()) * calc.getXpRateModifier();
			final JPanel container = createShadowedLabel(icon, a.getName(), FORMAT_COMMA.format(xp) + "xp");

			img.onLoaded(() ->
			{
				icon.setImage(img);
				container.repaint();
			});

			adjustContainer.add(container, c);
			c.gridy++;
		}
		else
		{
			final JComboBox<ComboBoxIconEntry> dropdown = new JComboBox<>();
			dropdown.setPreferredSize(new Dimension(PluginPanel.PANEL_WIDTH - 25, 40));
			dropdown.setFocusable(false); // To prevent an annoying "focus paint" effect
			dropdown.setForeground(Color.WHITE);
			dropdown.setBorder(new EmptyBorder(2, 0, 0, 0));

			final ComboBoxIconListRenderer renderer = new ComboBoxIconListRenderer();
			dropdown.setRenderer(renderer);

			for (final Activity option : activities)
			{
				final double xp = option.getXpRate(calc.getEnabledModifiers()) * calc.getXpRateModifier();
				String name = option.getName();
				if (xp > 0)
				{
					name += " (" + FORMAT_COMMA.format(xp) + "xp)";
				}

				// Use the output quantity if its stackable
				final int iconQty = option.getOutput() != null && option.getOutput().getId() == option.getIcon() ? (int) option.getOutput().getQty() : 1;
				final boolean iconStackable = option.getOutputItemInfo() == null ? iconQty > 1 : option.getOutputItemInfo().isStackable();
				final AsyncBufferedImage img = itemManager.getImage(option.getIcon(), iconQty, iconStackable);
				final ImageIcon icon = new ImageIcon(img);
				final ComboBoxIconEntry entry = new ComboBoxIconEntry(icon, name, option);
				dropdown.addItem(entry);

				img.onLoaded(() ->
				{
					icon.setImage(img);
					dropdown.revalidate();
					dropdown.repaint();
				});

				final Activity selected = bankedItem.getItem().getSelectedActivity();
				if (option.equals(selected))
				{
					dropdown.setSelectedItem(entry);
				}
			}

			// Add click event handler now to prevent above code from triggering it.
			dropdown.addItemListener(e ->
			{
				if (e.getStateChange() == ItemEvent.SELECTED && e.getItem() instanceof ComboBoxIconEntry)
				{
					final ComboBoxIconEntry source = (ComboBoxIconEntry) e.getItem();
					if (source.getData() instanceof Activity)
					{
						final Activity selectedActivity = ((Activity) source.getData());
						calc.activitySelected(bankedItem, selectedActivity);
						updateLabelContainer();
					}
				}
			});

			adjustContainer.add(dropdown, c);
			c.gridy++;
		}

		final Activity a = bankedItem.getItem().getSelectedActivity();
		if (a == null)
		{
			return;
		}

		if (a.getOutput() != null && a.getOutput().getQty() != 1)
		{
			final JLabel secondaryLabel = new JLabel("Outputs:");
			secondaryLabel.setVerticalAlignment(JLabel.CENTER);
			secondaryLabel.setHorizontalAlignment(JLabel.CENTER);

			adjustContainer.add(secondaryLabel, c);
			c.gridy++;

			// Create Icon
			final double qty = amount * a.getOutput().getQty();
			final boolean stackable = qty > 1 || (a.getOutputItemInfo() != null && a.getOutputItemInfo().isStackable());
			final AsyncBufferedImage img = itemManager.getImage(a.getIcon(), (int) qty, stackable);
			final ImageIcon icon = new ImageIcon(img);
			final JLabel iconLabel = createImageLabel(icon);
			iconLabel.setToolTipText(FORMAT_COMMA.format((int) qty) + " x " + a.getOutputItemInfo().getName());

			final JPanel container = createShadowedLabel(iconLabel, a.getOutputItemInfo().getName(), null);

			img.onLoaded(() ->
			{
				icon.setImage(img);
				container.repaint();
			});

			adjustContainer.add(container, c);
			c.gridy++;
		}

		final Secondaries secondaries = a.getSecondaries();
		if (secondaries != null && this.calc.getConfig().showSecondaries())
		{
			final JLabel secondaryLabel = new JLabel("Secondaries:");
			secondaryLabel.setVerticalAlignment(JLabel.CENTER);
			secondaryLabel.setHorizontalAlignment(JLabel.CENTER);

			adjustContainer.add(secondaryLabel, c);
			c.gridy++;

			final JPanel container = new JPanel();
			container.setLayout(new GridLayout(1, 6, 1, 1));
			container.setBackground(BACKGROUND_COLOR);

			for (final ItemStack s : secondaries.getItems())
			{
				final int required = (int) (s.getQty() * amount);
				final int available = this.calc.getItemQtyFromBank(s.getId());
				container.add(createSecondaryItemLabel(s, available, required));
			}

			if (secondaries.getCustomHandler() instanceof Secondaries.ByDose)
			{
				final Secondaries.ByDose byDose = ((Secondaries.ByDose) secondaries.getCustomHandler());
				final int required = amount;
				int available = 0;
				for (int i = 0; i < byDose.getItems().length; i++)
				{
					final int id = byDose.getItems()[i];
					available += (this.calc.getItemQtyFromBank(id) * (i + 1));
				}

				assert byDose.getInfoItems().length > 0;
				container.add(createSecondaryItemLabel(byDose.getInfoItems()[0], available, required));
			}

			if (secondaries.getCustomHandler() instanceof Secondaries.Degrime)
			{
				Secondaries.Degrime handler = (Secondaries.Degrime) secondaries.getCustomHandler();
				final int available = this.calc.getItemQtyFromBank(ItemID.NATURE_RUNE);
				final int required = handler.getTotalNaturesRequired(amount);
				container.add(createSecondaryItemLabel(new ItemStack(ItemID.NATURE_RUNE, 0), available, required));
			}

			if (secondaries.getCustomHandler() instanceof Secondaries.Crushable)
			{
				Secondaries.Crushable crushable = (Secondaries.Crushable) secondaries.getCustomHandler();
				int available = 0;
				for (final int itemId : crushable.getItems())
				{
					available += this.calc.getItemQtyFromBank(itemId);
				}
				container.add(createSecondaryItemLabel(crushable.getInfoItems()[0], available, amount));
			}

			adjustContainer.add(container, c);
			c.gridy++;
		}
	}

	private JLabel createSecondaryItemLabel(ItemStack stack, int available, int required)
	{
		final JLabel l = new JLabel();
		final AsyncBufferedImage img = itemManager.getImage(stack.getId(), required, required > 1);
		final ImageIcon icon = new ImageIcon(img);
		img.onLoaded(() ->
		{
			icon.setImage(img);
			l.repaint();
		});

		l.setIcon(icon);
		l.setHorizontalAlignment(JLabel.CENTER);

		final int result = (available - required);
		final String itemName = stack.getInfo() == null ? "" : stack.getInfo().getName();
		final String tooltip = "<html>" + itemName +
			"<br/>Banked: " + FORMAT_COMMA.format(available) +
			"<br/>Needed: " + FORMAT_COMMA.format(required) +
			"<br/>Result: " + (result > 0 ? "+" : "") + FORMAT_COMMA.format(result) +
			"</html>";
		l.setToolTipText(tooltip);

		return l;
	}

	private JLabel createImageLabel(final ImageIcon icon)
	{
		final JLabel image = new JLabel();
		image.setMinimumSize(ICON_SIZE);
		image.setMaximumSize(ICON_SIZE);
		image.setPreferredSize(ICON_SIZE);
		image.setHorizontalAlignment(SwingConstants.CENTER);
		image.setBorder(new EmptyBorder(0, 8, 0, 0));

		image.setIcon(icon);

		return image;
	}

	private JPanel createShadowedLabel(final ImageIcon icon, final String name, final String value)
	{
		final JLabel imageLabel = createImageLabel(icon);
		return createShadowedLabel(imageLabel, name, value);
	}

	private JPanel createShadowedLabel(final JLabel icon, final String name, final String value)
	{
		// Wrapper panel for the shadowed labels
		final JPanel wrapper = new JPanel(new GridLayout(value == null ? 1 : 2, 1));
		wrapper.setBorder(new EmptyBorder(0, 5, 0, 0));
		wrapper.setBackground(BACKGROUND_COLOR);

		final JShadowedLabel nameLabel = new JShadowedLabel(name);
		nameLabel.setForeground(Color.WHITE);
		wrapper.add(nameLabel);

		if (value != null)
		{
			nameLabel.setVerticalAlignment(SwingUtilities.BOTTOM);

			final JShadowedLabel valueLabel = new JShadowedLabel(value);
			valueLabel.setFont(FontManager.getRunescapeSmallFont());
			valueLabel.setVerticalAlignment(SwingUtilities.TOP);
			wrapper.add(valueLabel);
		}

		final JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		container.setBackground(BACKGROUND_COLOR);
		container.setBorder(new EmptyBorder(5, 0, 5, 0));

		container.add(icon, BorderLayout.LINE_START);
		container.add(wrapper, BorderLayout.CENTER);

		return container;
	}
}
