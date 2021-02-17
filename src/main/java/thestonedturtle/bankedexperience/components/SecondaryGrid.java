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

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import lombok.Getter;
import lombok.Value;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.ColorScheme;
import thestonedturtle.bankedexperience.BankedCalculator;
import thestonedturtle.bankedexperience.data.Activity;
import thestonedturtle.bankedexperience.data.BankedItem;
import thestonedturtle.bankedexperience.data.ItemStack;

public class SecondaryGrid extends JPanel
{
	@Value
	private static class SecondaryInfo
	{
		BankedItem bankedItem;
		double qty;
	}

	@Getter
	private final Multimap<Integer, SecondaryInfo> secMap = ArrayListMultimap.create();
	private final BankedCalculator calc;

	public SecondaryGrid(final BankedCalculator calc, final Collection<GridItem> items, final ItemManager itemManager)
	{
		this.calc = calc;
		setLayout(new GridLayout(0, 5, 1, 1));

		createSecMap(items);
		for (final int itemID : secMap.keySet())
		{
			final JLabel label = new JLabel();
			label.setOpaque(true);
			label.setMinimumSize(new Dimension(16, 16));
			label.setBackground(ColorScheme.DARKER_GRAY_COLOR);
			label.setBorder(BorderFactory.createEmptyBorder(5, 0, 2, 0));

			label.setVerticalAlignment(SwingConstants.CENTER);
			label.setHorizontalAlignment(SwingConstants.CENTER);

			double qty = 0;
			final StringBuilder resources = new StringBuilder();
			for (final SecondaryInfo info : secMap.get(itemID))
			{
				if (info.getQty() == 0)
				{
					continue;
				}

				resources.append("<br/>");
				qty += info.getQty();
				resources.append(BankedCalculator.XP_FORMAT_COMMA.format(info.getQty()))
					.append(" x ")
					.append(info.getBankedItem().getItem().getItemInfo().getName());
			}
			itemManager.getImage(itemID, (int) Math.round(qty),qty > 0).addTo(label);

			final int available = calc.getItemQtyFromBank(itemID);
			final double result = available - qty;

			final String tooltip = "<html>Banked: " + BankedCalculator.XP_FORMAT_COMMA.format(available)
				+ "<br/>Result: " + (result > 0 ? "+" : "") + BankedCalculator.XP_FORMAT_COMMA.format(result)
				+ "<br/>" + resources.toString() + "</html>";
			label.setToolTipText(tooltip);

			add(label);
		}
	}

	// calculates the total required secondaries and links each secondary item by id to the banked items they come from
	private void createSecMap(final Collection<GridItem> items)
	{
		secMap.clear();
		for (final GridItem i : items)
		{
			if (i.isIgnored())
			{
				continue;
			}

			// Check if the selected activity for the current item in the grid has any secondaries
			final BankedItem banked =  i.getBankedItem();
			final Activity a = banked.getItem().getSelectedActivity();
			if (a == null || a.getSecondaries() == null)
			{
				continue;
			}

			// Ensure all items are stacked properly
			final Map<Integer, Double> qtyMap = new HashMap<>();
			for (final ItemStack stack : a.getSecondaries().getItems())
			{
				qtyMap.merge(stack.getId(), stack.getQty() * calc.getItemQty(banked), Double::sum);
			}

			// Map this quantity to this activity through the banked item
			for (final Map.Entry<Integer, Double> entry : qtyMap.entrySet())
			{
				secMap.put(entry.getKey(), new SecondaryInfo(banked, entry.getValue()));
			}
		}
	}
}
