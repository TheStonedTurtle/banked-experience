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

import thestonedturtle.bankedexperience.BankedCalculator;
import thestonedturtle.bankedexperience.data.BankedItem;
import java.awt.GridLayout;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.JPanel;
import lombok.Getter;
import lombok.Setter;
import net.runelite.client.game.ItemManager;
import net.runelite.client.util.AsyncBufferedImage;

/**
 * A grid that supports mouse events
 */
public class SelectionGrid extends JPanel
{
	private static final int ITEMS_PER_ROW = 5;

	@Getter
	private final Map<BankedItem, GridItem> panelMap = new LinkedHashMap<>();

	@Getter
	private BankedItem selectedItem;

	@Setter
	private SelectionListener selectionListener;

	public SelectionGrid(final BankedCalculator calc, final Collection<BankedItem> items, final ItemManager itemManager)
	{
		// Create a panel for every item
		for (final BankedItem item : items)
		{
			final int qty = calc.getItemQty(item);
			final boolean stackable = item.getItem().isStackable() || qty > 1;
			final AsyncBufferedImage img = itemManager.getImage(item.getItem().getItemID(), qty, stackable);

			final GridItem gridItem = new GridItem(item, img, qty, calc.getEnabledModifiers(), calc.getIgnoredItems().contains(item.getItem().name()));

			gridItem.setSelectionListener(new SelectionListener()
			{
				@Override
				public boolean selected(BankedItem item)
				{
					if (selectionListener != null && !selectionListener.selected(item))
					{
						return false;
					}

					final GridItem gridItem = panelMap.get(selectedItem);
					if (gridItem != null)
					{
						gridItem.unselect();
					}

					selectedItem = item;
					return true;
				}

				@Override
				public boolean ignored(BankedItem item)
				{
					return selectionListener != null && selectionListener.ignored(item);
				}
			});
			panelMap.put(item, gridItem);
		}

		refreshGridDisplay();
	}

	public void refreshGridDisplay()
	{
		this.removeAll();

		final List<GridItem> items = panelMap.values().stream().filter(gi -> gi.getAmount() > 0).collect(Collectors.toList());

		// Calculates how many rows need to be display to fit all items
		final int rowSize = ((items.size() % ITEMS_PER_ROW == 0) ? 0 : 1) + items.size() / ITEMS_PER_ROW;
		setLayout(new GridLayout(rowSize, ITEMS_PER_ROW, 1, 1));

		for (final GridItem gridItem : items)
		{
			// Select the first option
			if (selectedItem == null)
			{
				gridItem.select();
				selectedItem = gridItem.getBankedItem();
			}

			this.add(gridItem);
		}
	}
}
