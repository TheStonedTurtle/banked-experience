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

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

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
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.gameval.ItemID;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.ColorScheme;
import thestonedturtle.bankedexperience.BankedCalculator;
import thestonedturtle.bankedexperience.data.Activity;
import thestonedturtle.bankedexperience.data.BankedItem;
import thestonedturtle.bankedexperience.data.ItemInfo;
import thestonedturtle.bankedexperience.data.ItemStack;
import thestonedturtle.bankedexperience.data.Secondaries;

@Slf4j
@SuppressFBWarnings(value = { "SE_BAD_FIELD" }, justification = "Plugin usage does not involve serialization")
public class SecondaryGrid extends JPanel
{
	@Value
	private static class SecondaryInfo
	{
		BankedItem bankedItem;
		double qty;
	}

	@Getter
	private final Multimap<Integer, SecondaryInfo> secMap = LinkedListMultimap.create();
	private final Map<Integer, ItemInfo> infoMap = new HashMap<>();
	private final Map<Integer, Integer> availableMap = new HashMap<>();

	public SecondaryGrid(final BankedCalculator calc, final Collection<GridItem> items)
	{
		setLayout(new GridLayout(0, 5, 1, 1));

		updateSecMap(calc, items);
	}

	private void refreshUI(final ItemManager items)
	{
		removeAll();
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

				qty += info.getQty();
				resources.append("<br/>");
				resources.append(BankedCalculator.XP_FORMAT_COMMA.format(info.getQty()))
					.append(" x ")
					.append(info.getBankedItem().getItem().getItemInfo().getName());
			}
			
			items.getImage(itemID, (int) Math.round(qty), qty > 0).addTo(label);

			final ItemInfo info = infoMap.get(itemID);
			final String itemName = info == null ? "" : info.getName();
			final int available = availableMap.getOrDefault(itemID, 0);
			final double result = available - qty;

			final String tooltip = "<html>" + itemName
				+ "<br/>Banked: " + BankedCalculator.XP_FORMAT_COMMA.format(available)
				+ "<br/>Result: " + (result > 0 ? "+" : "") + BankedCalculator.XP_FORMAT_COMMA.format(result)
				+ "<br/>" + resources.toString() + "</html>";
			label.setToolTipText(tooltip);

			label.setBackground(result >= 0 ? GridItem.SELECTED_BACKGROUND : GridItem.IGNORED_BACKGROUND);
			add(label);
		}
	}

	// calculates the total required secondaries and links each secondary item by id to the banked items they come from
	public void updateSecMap(final BankedCalculator calc, final Collection<GridItem> items)
	{
		secMap.clear();
		infoMap.clear();
		availableMap.clear();
		for (final GridItem item : items)
		{
			if (item.isIgnored())
			{
				continue;
			}

			// Check if the selected activity for the current item in the grid has any secondaries
			final BankedItem banked =  item.getBankedItem();
			final Activity a = banked.getItem().getSelectedActivity();
			if (a == null || a.getSecondaries() == null)
			{
				continue;
			}

			final int bankedQty = calc.getItemQty(item.getBankedItem());
			if (bankedQty == 0)
			{
				continue;
			}

			// Ensure all items are stacked properly
			final Secondaries secondaries = a.getSecondaries();
			final Map<Integer, Double> qtyMap = new HashMap<>();
			
			if (secondaries == null) 
			{
				continue;
			}
			else if (secondaries.getCustomHandler() instanceof Secondaries.ByDose)
			{
				final Secondaries.ByDose byDose = ((Secondaries.ByDose) secondaries.getCustomHandler());
				final int firstId = byDose.getItems()[0];
				int available = 0;
				for (int i = 0; i < byDose.getItems().length; i++)
				{
					final int id = byDose.getItems()[i];
					available += (calc.getItemQtyFromBank(id) * (i + 1));
				}
				availableMap.put(firstId, available);
				qtyMap.merge(firstId, (double) bankedQty, Double::sum);
				infoMap.put(firstId, byDose.getInfoItems()[0].getInfo());
			}
			else if (secondaries.getCustomHandler() instanceof Secondaries.Degrime)
			{
				Secondaries.Degrime handler = (Secondaries.Degrime) secondaries.getCustomHandler();
				qtyMap.merge(ItemID.NATURERUNE, (double) handler.getTotalNaturesRequired(bankedQty), Double::sum);
				infoMap.put(ItemID.NATURERUNE, new ItemInfo("Nature rune", true));
			}
			else if (secondaries.getCustomHandler() instanceof Secondaries.Crushable)
			{
				final Secondaries.Crushable crushable = (Secondaries.Crushable) secondaries.getCustomHandler();
				final int crushedItemId = crushable.getInfoItems()[0].getId();
				int available = 0;
				for (final int itemId : crushable.getItems()) 
				{
					available += calc.getItemQtyFromBank(itemId);
				}
				availableMap.put(crushedItemId, available);
				qtyMap.merge(crushedItemId, (double) bankedQty, Double::sum);
				infoMap.put(crushedItemId, crushable.getInfoItems()[0].getInfo());
			}
			else if (secondaries.getCustomHandler() instanceof Secondaries.ValeTotemOfferable)
			{
				final Secondaries.ValeTotemOfferable offerable = (Secondaries.ValeTotemOfferable) secondaries.getCustomHandler();
				int total_offerable_items = 0;
				final int infoItemID = offerable.getInfoItems()[0].getId();
				for (final int offerable_item : offerable.getItems()) 
				{
					total_offerable_items += calc.getItemQtyFromBank(offerable_item);
				}
				availableMap.put(infoItemID, total_offerable_items);
				qtyMap.merge(infoItemID, (double) bankedQty * 4.0, Double::sum);
				infoMap.put(infoItemID, new ItemInfo("Vale Totem Resources: " + offerable.getWoodType().getDisplayName(), true));
			}
			else if (secondaries.getCustomHandler() != null)
			{
				log.warn("Unhandled secondaries custom handler: {}", secondaries.getCustomHandler().getClass());
			}
			else
			{
				for (final ItemStack stack : secondaries.getItems())
				{
					qtyMap.merge(stack.getId(), stack.getQty() * bankedQty, Double::sum);
					infoMap.put(stack.getId(), stack.getInfo());
				}
			}

			// Map this quantity to this activity through the banked item
			for (final Map.Entry<Integer, Double> entry : qtyMap.entrySet())
			{
				if (!availableMap.containsKey(entry.getKey()))
				{
					availableMap.put(entry.getKey(), calc.getItemQtyFromBank(entry.getKey()));
				}

				secMap.put(entry.getKey(), new SecondaryInfo(banked, entry.getValue()));
			}
		}

		refreshUI(calc.getItemManager());
	}
}
