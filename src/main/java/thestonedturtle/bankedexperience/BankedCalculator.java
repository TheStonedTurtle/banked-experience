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
package thestonedturtle.bankedexperience;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Experience;
import net.runelite.api.Skill;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.DynamicGridLayout;
import net.runelite.client.ui.FontManager;
import net.runelite.client.util.AsyncBufferedImage;
import thestonedturtle.bankedexperience.components.GridItem;
import thestonedturtle.bankedexperience.components.ModifyPanel;
import thestonedturtle.bankedexperience.components.SelectionGrid;
import thestonedturtle.bankedexperience.components.SelectionListener;
import thestonedturtle.bankedexperience.components.textinput.UICalculatorInputArea;
import thestonedturtle.bankedexperience.data.Activity;
import thestonedturtle.bankedexperience.data.BankedItem;
import thestonedturtle.bankedexperience.data.ExperienceItem;
import thestonedturtle.bankedexperience.data.ItemStack;
import thestonedturtle.bankedexperience.data.XpModifiers;

@Slf4j
public class BankedCalculator extends JPanel
{
	public static final DecimalFormat XP_FORMAT_COMMA = new DecimalFormat("#,###.#");

	private final Client client;
	@Getter
	private final BankedExperienceConfig config;
	private final UICalculatorInputArea uiInput;
	private final ItemManager itemManager;

	// Some activities output a ExperienceItem and may need to be included in the calculable qty
	// Using multimap for cases where there are multiple items linked directly to one item, use recursion for otherwise
	private final Multimap<ExperienceItem, BankedItem> linkedMap = ArrayListMultimap.create();

	private final Map<ExperienceItem, BankedItem> bankedItemMap = new LinkedHashMap<>();
	private final JLabel totalXpLabel = new JLabel();
	private final ModifyPanel modifyPanel;
	private SelectionGrid itemGrid;

	// Store items from all sources in the same map
	private final Map<Integer, Integer> currentMap = new HashMap<>();
	// keep sources separate for recreating currentMap when one updates
	private Map<Integer, Integer> bankMap = new HashMap<>();
	private Map<Integer, Integer> vaultMap = new HashMap<>();

	@Getter
	private Skill currentSkill;

	@Getter
	private int skillLevel, skillExp, endLevel, endExp;

	private final Collection<JCheckBox> xpModifierButtons = new ArrayList<>();
	@Getter
	private float xpFactor = 1.0f;

	BankedCalculator(UICalculatorInputArea uiInput, Client client, BankedExperienceConfig config, ItemManager itemManager)
	{
		this.uiInput = uiInput;
		this.client = client;
		this.config = config;
		this.itemManager = itemManager;

		setLayout(new DynamicGridLayout(0, 1, 0, 5));

		// Panel used to modify banked item values
		this.modifyPanel = new ModifyPanel(this, itemManager);
	}

	/**
	 * opens the Banked Calculator for this skill
	 */
	void open(final Skill newSkill)
	{
		if (newSkill.equals(currentSkill))
		{
			return;
		}

		this.currentSkill = newSkill;
		removeAll();
		xpFactor = 1.0f;

		if (currentMap.size() <= 0)
		{
			add(new JLabel( "Please visit a bank!", JLabel.CENTER));
			revalidate();
			repaint();
			return;
		}

		skillLevel = client.getRealSkillLevel(currentSkill);
		skillExp =  client.getSkillExperience(currentSkill);
		endLevel = skillLevel;
		endExp = skillExp;

		uiInput.setCurrentLevelInput(skillLevel);
		uiInput.setCurrentXPInput(skillExp);
		uiInput.setTargetLevelInput(endLevel);
		uiInput.setTargetXPInput(endExp);

		recreateBankedItemMap();

		for (final XpModifiers modifier : XpModifiers.getModifiersBySkill(this.currentSkill))
		{
			JPanel uiOption = new JPanel(new BorderLayout());
			JLabel uiLabel = new JLabel(modifier.getName());
			JCheckBox btn = new JCheckBox();

			uiLabel.setForeground(Color.WHITE);
			uiLabel.setFont(FontManager.getRunescapeSmallFont());
			uiLabel.setHorizontalAlignment(SwingConstants.CENTER);

			uiOption.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 0));
			uiOption.setBackground(ColorScheme.DARKER_GRAY_COLOR);

			btn.addItemListener((event) ->
			{
				switch (event.getStateChange())
				{
					case ItemEvent.DESELECTED:
						xpFactor = 1.0f;
						break;
					case ItemEvent.SELECTED:
						// Deselects all but the current item
						final JCheckBox box = (JCheckBox) event.getItem();
						xpModifierButtons.forEach(b -> b.setSelected(b.equals(box)));

						xpFactor = modifier.getModifier();
						break;
					default:
						return;

				}

				modifierUpdated();
			});
			xpModifierButtons.add(btn);

			uiOption.add(uiLabel, BorderLayout.WEST);
			uiOption.add(btn, BorderLayout.EAST);
			add(uiOption);
		}

		recreateItemGrid();

		// This should only be null if there are no items in their bank for this skill
		if (itemGrid.getSelectedItem() == null)
		{
			add(new JLabel( "Couldn't find any items for this skill.", JLabel.CENTER));
		}
		else
		{
			add(totalXpLabel);
			add(modifyPanel);
			add(itemGrid);
		}

		revalidate();
		repaint();
	}

	private void recreateBankedItemMap()
	{
		bankedItemMap.clear();
		linkedMap.clear();

		final Collection<ExperienceItem> items = ExperienceItem.getBySkill(currentSkill);
		log.debug("Experience items for the {} Skill: {}", currentSkill.getName(), items);

		for (final ExperienceItem item : items)
		{
			final BankedItem banked = new BankedItem(item, currentMap.getOrDefault(item.getItemID(), 0));
			bankedItemMap.put(item, banked);

			Activity a = item.getSelectedActivity();
			final int level = config.limitToCurrentLevel() ? skillLevel : -1;
			if (a == null || (level > 0 && level < a.getLevel()))
			{
				final List<Activity> activities = Activity.getByExperienceItem(item, level);
				if (activities.size() == 0)
				{
					item.setSelectedActivity(null);
					continue;
				}

				item.setSelectedActivity(activities.get(0));
				a = activities.get(0);
			}

			if (a.getLinkedItem() != null)
			{
				linkedMap.put(a.getLinkedItem(), banked);
			}
		}
		log.debug("Banked Item Map: {}", bankedItemMap);
		log.debug("Linked Map: {}", linkedMap);
	}

	/**
	 * Populates the detailContainer with the necessary BankedItemPanels
	 */
	private void recreateItemGrid()
	{
		// Selection grid will only display values with > 0 items
		itemGrid = new SelectionGrid(this, bankedItemMap.values(), itemManager);
		itemGrid.setSelectionListener(new SelectionListener()
		{
			@Override
			public boolean selected(BankedItem item)
			{
				modifyPanel.setBankedItem(item);
				return true;
			}

			@Override
			public boolean ignored(BankedItem item)
			{
				updateLinkedItems(item.getItem().getSelectedActivity());
				calculateBankedXpTotal();
				return true;
			}
		});

		// Select the first item in the list
		modifyPanel.setBankedItem(itemGrid.getSelectedItem());

		calculateBankedXpTotal();
	}

	public double getItemXpRate(final BankedItem bankedItem)
	{
		final Activity selected = bankedItem.getItem().getSelectedActivity();
		if (selected == null)
		{
			return 0;
		}

		return selected.getXpRate(xpFactor);
	}

	/**
	 * Calculates total item quantity accounting for backwards linked items
	 * @param item starting item
	 * @return item qty including linked items
	 */
	public int getItemQty(final BankedItem item)
	{
		int qty = item.getQty();

		if (!config.cascadeBankedXp())
		{
			return qty;
		}

		final Map<ExperienceItem, Integer> linked = createLinksMap(item);
		final int linkedQty = linked.entrySet().stream().mapToInt((entry) ->
		{
			// Account for activities that output multiple of a specific item per action
			final ItemStack output = entry.getKey().getSelectedActivity().getOutput();
			return entry.getValue() * (output != null ? output.getQty() : 1);
		}).sum();

		return qty + linkedQty;
	}

	private void calculateBankedXpTotal()
	{
		double total = 0.0;
		for (final GridItem i : itemGrid.getPanelMap().values())
		{
			if (i.isIgnored())
			{
				continue;
			}

			final BankedItem bi = i.getBankedItem();
			total += getItemQty(bi) * getItemXpRate(bi);
		}

		endExp = (int) (skillExp + total);
		endLevel = Experience.getLevelForXp(endExp);

		totalXpLabel.setText("Total Banked xp: " + XP_FORMAT_COMMA.format(total));
		uiInput.setTargetLevelInput(endLevel);
		uiInput.setTargetXPInput(Math.min(Experience.MAX_SKILL_XP, endExp));

		revalidate();
		repaint();
	}

	/**
	 * Used to select an Activity for an item
	 * @param i BankedItem item the activity is tied to
	 * @param a Activity the selected activity
	 */
	public void activitySelected(final BankedItem i, final Activity a)
	{
		final ExperienceItem item = i.getItem();
		final Activity old = item.getSelectedActivity();
		if (a.equals(old))
		{
			return;
		}

		item.setSelectedActivity(a);

		// Cascade activity changes if necessary.
		if (config.cascadeBankedXp() && (old.getLinkedItem() != a.getLinkedItem()))
		{
			// Update Linked Map
			linkedMap.remove(old.getLinkedItem(), i);
			linkedMap.put(a.getLinkedItem(), i);
			// Update all items the old activity effects
			updateLinkedItems(old);
			// Update all the items the new activity effects
			updateLinkedItems(a);
		}

		modifyPanel.setBankedItem(i);
		itemGrid.getPanelMap().get(i).updateToolTip(xpFactor);

		// recalculate total xp
		calculateBankedXpTotal();
	}

	/**
	 * Updates the item quantities of all forward linked items
	 * @param activity the starting {@link Activity} to start the cascade from
	 */
	private void updateLinkedItems(final Activity activity)
	{
		if (activity == null)
		{
			return;
		}

		boolean foundSelected = false;
		boolean panelAmountChange = false;

		ExperienceItem i = activity.getLinkedItem();
		while (i != null)
		{
			final BankedItem bi = bankedItemMap.get(i);
			if (bi == null)
			{
				break;
			}

			final int qty = getItemQty(bi);
			final boolean stackable = bi.getItem().getItemInfo().isStackable() || qty > 1;
			final AsyncBufferedImage img = itemManager.getImage(bi.getItem().getItemID(), qty, stackable);

			final GridItem gridItem = itemGrid.getPanelMap().get(bi);
			final int oldQty = gridItem.getAmount();
			panelAmountChange = panelAmountChange || ( (oldQty == 0 && qty > 0) || (oldQty > 0 && qty == 0) );
			gridItem.updateIcon(img, qty);
			gridItem.updateToolTip(xpFactor);

			foundSelected = foundSelected || itemGrid.getSelectedItem().equals(bi);

			final Activity a = bi.getItem().getSelectedActivity();
			if (a == null)
			{
				break;
			}

			i = a.getLinkedItem();
		}

		if (panelAmountChange)
		{
			itemGrid.refreshGridDisplay();
		}

		if (foundSelected)
		{
			// Refresh current modify panel if the cascade effects it
			modifyPanel.setBankedItem(itemGrid.getSelectedItem());
		}
	}

	/**
	 * Creates a Map of ExperienceItem to bank qty for all items that are being linked to this one
	 * @param item starting item
	 * @return Map of ExperienceItem to bank qty
	 */
	public Map<ExperienceItem, Integer> createLinksMap(final BankedItem item)
	{
		final Map<ExperienceItem, Integer> qtyMap = new HashMap<>();

		final Activity a = item.getItem().getSelectedActivity();
		if (a == null)
		{
			return qtyMap;
		}

		final Collection<BankedItem> linkedBank = linkedMap.get(item.getItem());
		if (linkedBank == null || linkedBank.size() == 0)
		{
			return qtyMap;
		}

		for (final BankedItem linked : linkedBank)
		{
			// Check if the item is ignored in the grid
			if (itemGrid != null)
			{
				final GridItem grid = itemGrid.getPanelMap().get(linked);
				if (grid != null && grid.isIgnored())
				{
					continue;
				}
			}

			final int qty = linked.getQty();
			if (qty > 0)
			{
				qtyMap.put(linked.getItem(), qty);
			}
			qtyMap.putAll(createLinksMap(linked));
		}

		return qtyMap;
	}

	private void modifierUpdated()
	{
		itemGrid.getPanelMap().values().forEach(item -> item.updateToolTip(xpFactor));
		modifyPanel.setBankedItem(modifyPanel.getBankedItem());
		calculateBankedXpTotal();
	}

	public int getItemQtyFromBank(final int id)
	{
		return bankMap.getOrDefault(id, 0);
	}

	void setBankMap(Map<Integer, Integer> bankMap)
	{
		this.bankMap = bankMap;
		updateCurrentMap();
	}

	void setVaultMap(Map<Integer, Integer> vaultMap)
	{
		this.vaultMap = vaultMap;
		updateCurrentMap();
	}

	private void updateCurrentMap()
	{
		currentMap.clear();
		currentMap.putAll(bankMap);
		currentMap.putAll(vaultMap);
	}
}
