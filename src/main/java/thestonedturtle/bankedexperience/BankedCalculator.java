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

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Experience;
import net.runelite.api.Skill;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.DynamicGridLayout;
import net.runelite.client.util.AsyncBufferedImage;
import net.runelite.client.util.Text;
import thestonedturtle.bankedexperience.components.ExpandableSection;
import thestonedturtle.bankedexperience.components.GridItem;
import thestonedturtle.bankedexperience.components.ModifyPanel;
import thestonedturtle.bankedexperience.components.SecondaryGrid;
import thestonedturtle.bankedexperience.components.SelectionGrid;
import thestonedturtle.bankedexperience.components.SelectionListener;
import thestonedturtle.bankedexperience.components.textinput.SpinnerInput;
import thestonedturtle.bankedexperience.components.textinput.UICalculatorInputArea;
import thestonedturtle.bankedexperience.data.Activity;
import thestonedturtle.bankedexperience.data.BankedItem;
import thestonedturtle.bankedexperience.data.ExperienceItem;
import thestonedturtle.bankedexperience.data.ItemStack;
import thestonedturtle.bankedexperience.data.modifiers.Modifier;
import thestonedturtle.bankedexperience.data.modifiers.ModifierComponent;
import thestonedturtle.bankedexperience.data.modifiers.Modifiers;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@SuppressFBWarnings(value = { "SE_BAD_FIELD" }, justification = "Plugin usage does not involve serialization")
public class BankedCalculator extends JPanel
{
	public static final DecimalFormat XP_FORMAT_COMMA = new DecimalFormat("#,###.#");
	
	private final SelectionListener createItemGridSelectionListener()
	{
		final BankedCalculator outerThis = this;

		return new SelectionListener() 
		{
			@Override
			public boolean selected(BankedItem item) 
			{
				modifyPanel.setBankedItem(item, outerThis);
				return true;
			}

			@Override
			public boolean ignored(BankedItem item) 
			{
				toggleIgnoreBankedItem(item);

				// Update Config
				config.ignoredItems(Text.toCSV(ignoredItems));

				// Update UI
				calculateBankedXpTotal();

				return true;
			}
		};
	}
	
	private final MouseListener createRefreshButtonMouseListener() 
	{
		return new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				if (e.getButton() == MouseEvent.BUTTON1)
				{
					open(currentSkill, true);
				}
			}
		};
	}

	private final Client client;
	@Getter
	private final BankedExperienceConfig config;
	private final UICalculatorInputArea uiInput;
	@Getter
	private final ItemManager itemManager;
	private final ConfigManager configManager;

	/** 
	 * Some activities output a ExperienceItem and may need to be included in the calculable qty.
	 * Using multimap for cases where there are multiple items linked directly to one item, use
	 * recursion for otherwise
	*/
	private final Multimap<ExperienceItem, BankedItem> linkedMap = ArrayListMultimap.create();
	
	private final LinkedHashMap<ExperienceItem, BankedItem> bankedItemMap = new LinkedHashMap<>();
	private final JLabel totalXpLabel = new JLabel();
	private final JLabel xpToNextLevelLabel = new JLabel();
	private final ModifyPanel modifyPanel;
	private final SelectionGrid itemGrid = new SelectionGrid();
	private SecondaryGrid secondaryGrid;
	private ExpandableSection modifierSection;
	private ExpandableSection secondarySection;
	private final JButton refreshBtn;
	
	// Store items from all sources in the same map
	private final Map<Integer, Integer> currentMap = new HashMap<>();
	// keep sources separate for recreating currentMap when one updates
	private final Map<Integer, Map<Integer, Integer>> inventoryMap = new HashMap<>();
	
	// Keep a reference to enabled modifiers so recreating tooltips is faster.
	@Getter
	private final HashSet<Modifier> enabledModifiers = new HashSet<>();
	private final ArrayList<ModifierComponent> modifierComponents = new ArrayList<>();
	
	@Getter
	private final Set<String> ignoredItems;
	
	@Getter
	private Skill currentSkill;
	
	@Getter
	private int skillLevel, skillExp, endLevel, endExp;
	
	@Getter
	private final SpinnerInput boostInput = new SpinnerInput(
			"Temporary Boost:",
			"Enables activities that are this many levels above your current level",
			this::updateBoost
	);
	
	@Getter
	private final SpinnerInput xpRateModifierInput = new SpinnerInput(
			"XP Rate Multiplier:",
			"Used for alternative game modes such as DMM and leagues. 1 = default OSRS experience rates.",
			1,
			1,
			this::updateXpRateModifier
	);
	
	BankedCalculator(UICalculatorInputArea uiInput, Client client, BankedExperienceConfig config,
			ItemManager itemManager, ConfigManager configManager)
	{
		this.uiInput = uiInput;
		this.client = client;
		this.config = config;
		this.itemManager = itemManager;
		this.configManager = configManager;
	
		this.ignoredItems = new HashSet<>(Text.fromCSV(config.ignoredItems()));
	
		setLayout(new DynamicGridLayout(0, 1, 0, 5));
	
		// Panel used to modify banked item values
		this.modifyPanel = new ModifyPanel(this, itemManager);
	
		this.refreshBtn = new JButton("Refresh Calculator");
		refreshBtn.setFocusable(false);
		refreshBtn.addMouseListener(createRefreshButtonMouseListener());
	
		itemGrid.setSelectionListener(createItemGridSelectionListener());
	}
	
	/**
	 * opens the Banked Calculator for this skill
	 */
	void open(final Skill newSkill)
	{
		open(newSkill, false);
	}

	/**
	 * opens the Banked Calculator for this skill
	 */
	void open(final Skill newSkill, final boolean refresh)
	{
		if (!refresh && newSkill.equals(currentSkill))
		{
			return;
		}

		if (!newSkill.equals(currentSkill))
		{
			boostInput.setInputValue(0);
			itemGrid.setSelectedItem(null);
			// intentionally not resetting the xp rate modifier
		}

		this.currentSkill = newSkill;
		removeAll();
		if (!refresh)
		{
			modifierComponents.clear();
			enabledModifiers.clear();
		}
		refreshBtn.setVisible(false);
		secondaryGrid = null; // prevents the Secondaries section from being added early by recreateItemGrid

		if (currentMap.isEmpty())
		{
			add(new JLabel("Please visit a bank!", JLabel.CENTER));
			add(refreshBtn);
			revalidate();
			repaint();
			return;
		}

		skillLevel = client.getRealSkillLevel(currentSkill);
		skillExp = client.getSkillExperience(currentSkill);
		endLevel = skillLevel;
		endExp = skillExp;

		uiInput.setCurrentLevelInput(skillLevel);
		uiInput.setCurrentXPInput(skillExp);
		uiInput.setTargetLevelInput(endLevel);
		uiInput.setTargetXPInput(endExp);

		recreateBankedItemMap();

		// to preserve modifiers between refreshes only attempt to populate if no modifiers are currently displayed
		if (!refresh || modifierComponents.isEmpty())
		{
			// Add XP modifiers
			populateModifierComponents();
		}

		if (!modifierComponents.isEmpty())
		{
			boolean wasClosed = modifierSection != null && !modifierSection.isOpen();
			modifierSection = new ExpandableSection(
					"Modifiers",
					"Toggles the different ways activity/experience gains can be modified",
					modifierComponents.stream()
							.map(ModifierComponent::getComponent)
							.collect(Collectors.toList())
			);
			modifierSection.setOpen(!wasClosed);
			add(modifierSection);
		}

		recreateItemGrid();

		// This should only be null if there are no items in their bank for this skill
		if (itemGrid.getSelectedItem() == null)
		{
			add(new JLabel("Couldn't find any items for this skill.", JLabel.CENTER));
		}
		else
		{
			if (config.limitToCurrentLevel())
			{
				add(boostInput);
			}
			add(xpRateModifierInput);
			add(totalXpLabel);
			add(xpToNextLevelLabel);
			add(modifyPanel);
			add(itemGrid);

			if (config.showSecondaries())
			{
				secondaryGrid = new SecondaryGrid(this, itemGrid.getPanelMap().values());
				boolean wasClosed = secondarySection != null && !secondarySection.isOpen();
				secondarySection = new ExpandableSection(
						"Secondaries",
						"Shows a breakdown of how many secondaries are required for all enabled activities",
						List.of(secondaryGrid)
				);

				secondarySection.setOpen(!wasClosed);
				
				if (!secondaryGrid.getSecMap().isEmpty())
				{
					add(secondarySection);
				}
			}
		}

		add(refreshBtn);

		revalidate();
		repaint();
	}

	private void populateModifierComponents()
	{
		for (final Modifier modifier : Modifiers.getBySkill(this.currentSkill))
		{
			final ModifierComponent c = modifier.generateModifierComponent();
			c.setModifierConsumer((mod, newState) ->
			{
				// Only need to check other modifiers if this one is enabled
				if (newState)
				{
					// Disable any non-compatible modifications
					modifierComponents.forEach(component ->
					{
						// Modifier not enabled or modifiers are compatible with each other
						if (!component.isModifierEnabled() || (component.getModifier().compatibleWith(mod) && mod.compatibleWith(component.getModifier())))
						{
							return;
						}

						component.setModifierEnabled(false);
					});
				}

				modifierUpdated();
			});
			modifierComponents.add(c);
		}
	}

	private void recreateBankedItemMap()
	{
		bankedItemMap.clear();
		linkedMap.clear();

		final Collection<ExperienceItem> items = ExperienceItem.getBySkill(currentSkill);
		log.debug("Experience items for the {} Skill: {}", currentSkill.getName(), items);

		for (final ExperienceItem item : items)
		{
			int qty = 0;
			for (int i = 0; i < item.getItemIds().length; i++)
			{
				final int multiplier = item.isByDose() ? i + 1 : 1;
				qty += getItemQtyFromBank(item.getItemIds()[i]) * multiplier;
			}

			// Convert to bankedItems
			final BankedItem banked = new BankedItem(item, qty);
			bankedItemMap.put(item, banked);

			Activity a = item.getSelectedActivity();
			if (a == null || (config.limitToCurrentLevel() && (skillLevel + boostInput.getInputValue()) < a.getLevel()))
			{
				final List<Activity> activities = Activity.getByExperienceItem(item, config.limitToCurrentLevel() ? (skillLevel + boostInput.getInputValue()) : -1);
				if (activities.isEmpty())
				{
					item.setSelectedActivity(null);
					continue;
				}

				item.setSelectedActivity(activities.get(0));
				a = activities.get(0);
			}

			// If this activity outputs another experienceItem they should be linked
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
		itemGrid.recreateGrid(this, bankedItemMap.values(), itemManager);

		// Select the first item in the list
		modifyPanel.setBankedItem(itemGrid.getSelectedItem(), this);

		calculateBankedXpTotal();
	}

	public double getItemXpRate(final BankedItem bankedItem)
	{
		final Activity selected = bankedItem.getItem().getSelectedActivity();
		if (selected == null)
		{
			return 0;
		}

		return selected.getXpRate(enabledModifiers) * getXpRateModifier();
	}

	private int getConsolidatedTotal(Map<ExperienceItem, Integer> original, ExperienceItem goalItem)
	{
		// Create a copy to enable deleting entries during the loop without concurrent errors
		final Map<ExperienceItem, Integer> linked = new HashMap<>(original);

		final Set<ExperienceItem> original_keys = original.keySet();
		
		double runningCascadeTotal = 0.0;
		for (final ExperienceItem experienceItem : original_keys)
		{
			final Activity a = experienceItem.getSelectedActivity();
			assert a != null;

			// subTotal should always include this item regardless of activity.
			// If the activity for this item is the goalActivity than the while loop will never run
			double subTotal = 0.0;
			ExperienceItem linkedItem = experienceItem;
			while (linkedItem != null && !linkedItem.equals(goalItem))
			{
				final Activity linkedActivity = linkedItem.getSelectedActivity();
				final ItemStack output = linkedActivity.getOutput();
				// We update instead of concat to this value as items can be mapped to multiples of another
				// Example: Item 1 -> 5 of Item 2 -> 10 of Item 3
				// So if we have 5, 10, and 50 of each item we should end up with
				// 5 * 2 = 10 of Item 2 added to `subTotal.
				// Next, when Item 2 is processed we should calculate `10 + the 10 original` items for 20 * 3 for 60 of Item 3 in the subtotal
				// Finally, we should get `60 + 50 of the original` of Item 3 for 110 total.
				subTotal = (subTotal + linked.getOrDefault(linkedItem, 0)) * (output == null ? 1.0 : output.getQty());
				linked.remove(linkedItem);

				linkedItem = linkedActivity.getLinkedItem();
			}
			
			// Once we get here linkedItem should be equal to goalItem and will never be null
			assert linkedItem != null;
			final Activity linkedActivity = linkedItem.getSelectedActivity();
			final ItemStack output = linkedActivity.getOutput();
			subTotal += linked.getOrDefault(linkedItem, 0) * (output == null ? 1.0 : output.getQty());
			linked.remove(linkedItem);

			runningCascadeTotal += subTotal;
		}

		return (int) runningCascadeTotal;
	}

	/**
	 * Calculates total item quantity accounting for backwards linked items
	 *
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
		final int cascadeTotal = getConsolidatedTotal(linked, item.getItem());

		return qty + cascadeTotal;
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

		endExp = Math.min(Experience.MAX_SKILL_XP, (int) (skillExp + total));
		endLevel = Experience.getLevelForXp(endExp);

		totalXpLabel.setText("Total Banked: " + XP_FORMAT_COMMA.format(total) + "xp");
		uiInput.setTargetLevelInput(endLevel);
		uiInput.setTargetXPInput(endExp);

		final int nextLevel = Math.min(endLevel + 1, 126);
		final int nextLevelXp = Experience.getXpForLevel(nextLevel) - endExp;
		xpToNextLevelLabel.setText("Level " + nextLevel + " requires: " + XP_FORMAT_COMMA.format(nextLevelXp) + "xp");

		// Refresh secondaries whenever the exp is updated
		refreshSecondaries();

		revalidate();
		repaint();
	}

	/**
	 * Used to select an Activity for an item
	 *
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
		saveActivity(i.getItem());

		// Cascade activity changes if necessary.
		if (config.cascadeBankedXp() && a.shouldUpdateLinked(old))
		{
			// Update Linked Map
			linkedMap.remove(old.getLinkedItem(), i);
			linkedMap.put(a.getLinkedItem(), i);
			// Update all items the old activity effects
			updateLinkedItems(old);
			// Update all the items the new activity effects
			updateLinkedItems(a);
		}

		modifyPanel.setBankedItem(i, this);
		itemGrid.getPanelMap().get(i).updateToolTip(enabledModifiers);

		// recalculate total xp
		calculateBankedXpTotal();
	}

	/**
	 * Updates the item quantities of all forward linked items
	 *
	 * @param activity the starting {@link Activity} to start the cascade from
	 */
	private void updateLinkedItems(final Activity activity)
	{
		if (activity == null)
		{
			return;
		}

		boolean foundSelected = false;        // Found an item currently being displayed in the ModifyPanel
		boolean gridCountChanged = false;

		ExperienceItem i = activity.getLinkedItem();
		while (i != null)
		{
			final BankedItem bi = bankedItemMap.get(i);
			if (bi == null)
			{
				break;
			}

			final int qty = getItemQty(bi);
			final boolean stackable = qty > 1 || bi.getItem().getItemInfo().isStackable();
			final AsyncBufferedImage img = itemManager.getImage(bi.getItem().getItemID(), qty, stackable);

			final GridItem gridItem = itemGrid.getPanelMap().get(bi);
			final int oldQty = gridItem.getAmount();
			gridCountChanged |= ((oldQty == 0 && qty > 0) || (oldQty > 0 && qty == 0));
			gridItem.updateIcon(img, qty);
			gridItem.updateToolTip(enabledModifiers);

			foundSelected |= itemGrid.getSelectedItem().equals(bi);

			final Activity a = bi.getItem().getSelectedActivity();
			if (a == null)
			{
				break;
			}

			i = a.getLinkedItem();
		}

		if (gridCountChanged)
		{
			itemGrid.refreshGridDisplay();
		}

		if (foundSelected)
		{
			// Refresh current modify panel if the cascade effects it
			modifyPanel.setBankedItem(itemGrid.getSelectedItem(), this);
		}
	}

	/**
	 * Creates a Map of ExperienceItem to bank qty for all items that are being linked to this one
	 *
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
		if (linkedBank == null || linkedBank.isEmpty())
		{
			return qtyMap;
		}

		for (final BankedItem linked : linkedBank)
		{
			// Check if the item is ignored in the grid
			if (ignoredItems.contains(linked.getItem().name()))
			{
				continue;
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
		enabledModifiers.clear();
		enabledModifiers.addAll(modifierComponents.stream()
				.filter(ModifierComponent::isModifierEnabled)
				.map(ModifierComponent::getModifier)
				.collect(Collectors.toSet())
		);

		itemGrid.getPanelMap().values().forEach(item -> item.updateToolTip(enabledModifiers));
		modifyPanel.setBankedItem(modifyPanel.getBankedItem(), this);
		calculateBankedXpTotal();
	}

	public int getItemQtyFromBank(final int id)
	{
		return currentMap.getOrDefault(id, 0);
	}

	public void resetInventoryMaps()
	{
		inventoryMap.clear();
		updateCurrentMap();
		if (currentSkill == null)
		{
			return;
		}
		open(currentSkill, true);

		// Reset experience level stuff
		uiInput.setCurrentLevelInput(1);
		uiInput.setCurrentXPInput(0);
		uiInput.setTargetLevelInput(1);
		uiInput.setTargetXPInput(0);
	}

	void setInventoryMap(final int inventoryId, final Map<Integer, Integer> map)
	{
		inventoryMap.put(inventoryId, map);
		updateCurrentMap();
		refreshBtn.setVisible(true);
	}

	private void updateCurrentMap()
	{
		currentMap.clear();
		for (final Map<Integer, Integer> map : inventoryMap.values())
		{
			for (final Entry<Integer, Integer> entry : map.entrySet())
			{
				final int id = entry.getKey();
				final int qty = entry.getValue() + currentMap.getOrDefault(id, 0);
				currentMap.put(id, qty);
			}
		}
	}

	private void refreshSecondaries()
	{
		if (secondarySection == null || secondaryGrid == null)
		{
			return;
		}

		final boolean wasVisible = !secondaryGrid.getSecMap().isEmpty();
		secondaryGrid.updateSecMap(this, itemGrid.getPanelMap().values());
		final boolean shouldBeVisible = !secondaryGrid.getSecMap().isEmpty();

		if (shouldBeVisible != wasVisible)
		{
			if (shouldBeVisible)
			{
				add(secondarySection, getComponentCount() - 1);
			}
			else
			{
				remove(secondarySection);
			}
		}
	}

	private void saveActivity(final ExperienceItem item)
	{
		final String itemSpecificConfigKey = BankedExperiencePlugin.ACTIVITY_CONFIG_KEY_PREFIX + item.name();
		configManager.setConfiguration(BankedExperienceConfig.CONFIG_GROUP, itemSpecificConfigKey, item.getSelectedActivity().name());
	}

	private void updateBoost(Integer value)
	{
		// If the item grid wasn't added then the boost input is not visible
		recreateBankedItemMap();
		recreateItemGrid();
	}

	private void ignoreBankedItem(BankedItem item, boolean ignored)
	{
		final String name = item.getItem().name();
		if (ignored)
		{
			ignoredItems.add(name);
		}
		else
		{
			ignoredItems.remove(name);
		}

		updateLinkedItems(item.getItem().getSelectedActivity());
	}

	private void toggleIgnoreBankedItem(BankedItem item)
	{
		boolean ignore = !ignoredItems.contains(item.getItem().name());
		ignoreBankedItem(item, ignore);
	}

	public void setIgnoreAllItems(boolean ignored)
	{
		itemGrid.getPanelMap().values().forEach((i) ->
		{
			ignoreBankedItem(i.getBankedItem(), ignored);
			i.setIgnore(ignored);
		});

		// Update Config
		config.ignoredItems(Text.toCSV(ignoredItems));

		// Update UI
		calculateBankedXpTotal();
	}

	public int getXpRateModifier()
	{
		return xpRateModifierInput.getInputValue();
	}

	private void updateXpRateModifier(Integer value)
	{
		recreateItemGrid();
	}
}
