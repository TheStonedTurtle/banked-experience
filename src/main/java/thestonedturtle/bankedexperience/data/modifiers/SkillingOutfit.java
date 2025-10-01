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
package thestonedturtle.bankedexperience.data.modifiers;

import com.google.common.primitives.Booleans;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.Collection;
import java.util.function.BiConsumer;

import javax.annotation.Nonnull;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ItemComposition;
import net.runelite.api.Skill;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.DynamicGridLayout;
import thestonedturtle.bankedexperience.data.Activity;
import thestonedturtle.bankedexperience.data.modifiers.ui.SelectableLabel;

@Slf4j
public class SkillingOutfit extends ConsumptionModifier implements ModifierComponent
{
	private static final float HELM_BONUS = 0.004f;
	private static final float TOP_BONUS = 0.008f;
	private static final float BOTTOM_BONUS = 0.006f;
	private static final float BOOTS_BONUS = 0.002f;
	private static final float SET_BONUS = 0.025f; // Wearing the entire set will reward this much bonus XP in total

	@Setter
	private BiConsumer<Modifier, Boolean> modifierConsumer;
	private final Runnable callback = () ->
	{
		if (modifierConsumer == null)
		{
			log.warn("Toggling SkillingOutfit modifier wth no consumer: {}", this);
			return;
		}

		modifierConsumer.accept(this, isModifierEnabled());
	};

	/* 
		Helper class implementing method for creating Skilling Outfits from arrays of ItemCompositions. By placing
		this into an inner class, we stop subclasses of SkillingOutfit from inheriting this method
	*/
	public static final class FromArray 
	{
		
		public static final SkillingOutfit create(Skill skill,
				String name,
				Collection<Activity> included,
				Collection<Activity> ignored,
				ItemManager itemManager,
				@Nonnull ItemComposition[] items)
		{
			if (items == null || items.length != 4)
			{
				throw new AssertionError("Mis-sized or null array may not be used to construct a " + SkillingOutfit.class.getName());
			}
			return new SkillingOutfit(skill, name, included, ignored, itemManager, items[0], items[1], items[2], items[3]);
		}
	}

	final JPanel panel = new JPanel();
	final SelectableLabel helm;
	final SelectableLabel top;
	final SelectableLabel bottom;
	final SelectableLabel boots;

	SkillingOutfit(Skill skill,
			String name,
			Collection<Activity> included,
			Collection<Activity> ignored,
			ItemManager itemManager,
			@Nonnull ItemComposition helmComp,
			@Nonnull ItemComposition topComp,
			@Nonnull ItemComposition bottomComp,
			@Nonnull ItemComposition bootComp)
	{
		super(skill, name, 0f, included, ignored);

		final JPanel container = new JPanel();
		container.setLayout(new DynamicGridLayout(1, 0, 2, 0));

		helm = new SelectableLabel();
		helm.setToolTipText("<html>" + helmComp.getName() + "<br/>Increases xp gained by 0.4%</html>");
		helm.setCallback(callback);
		container.add(helm);
		itemManager.getImage(helmComp.getId()).addTo(helm);

		top = new SelectableLabel();
		top.setToolTipText("<html>" + topComp.getName() + "<br/>Increases xp gained by 0.8%</html>");
		top.setCallback(callback);
		container.add(top);
		itemManager.getImage(topComp.getId()).addTo(top);

		bottom = new SelectableLabel();
		bottom.setToolTipText("<html>" + bottomComp.getName() + "<br/>Increases xp gained by 0.6%</html>");
		bottom.setCallback(callback);
		container.add(bottom);
		itemManager.getImage(bottomComp.getId()).addTo(bottom);

		boots = new SelectableLabel();
		boots.setToolTipText("<html>" + bootComp.getName() + "<br/>Increases xp gained by 0.2%</html>");
		boots.setCallback(callback);
		container.add(boots);
		itemManager.getImage(bootComp.getId()).addTo(boots);
		
		panel.setLayout(new DynamicGridLayout(0, 1, 0, 2));
		panel.setToolTipText("<html>Increases xp gained while worn.<br/>The full set increases the bonus by 0.5% for 2.5% instead of 2% bonus xp</html>");
		panel.add(new JLabel(name));
		panel.add(container);

	}

	//TODO: Refactor ModifierComponent implementations to respect MVC principles; separate data from GUI representation
	@Override
	@SuppressFBWarnings(value = { "EI_EXPOSE_REP" }, justification = "The Swing components are not truly encapsulated data; no harmful internal state leaked")
	public JComponent getComponent()
	{
		return panel;
	}

	@Override
	public ModifierComponent generateModifierComponent() 
	{
		return this;	
	}

	
	int getEnabledButtonCount()
	{
		return Booleans.countTrue(
			helm.isSelected(), top.isSelected(), bottom.isSelected(), boots.isSelected()
		);
	}

	@Override
	public Modifier getModifier()
	{
		return this;
	}

	@Override
	public Boolean isModifierEnabled()
	{
		return getEnabledButtonCount() > 0;
	}

	@Override
	public void setModifierEnabled(boolean enabled)
	{
		helm.setSelected(enabled, false);
		top.setSelected(enabled, false);
		bottom.setSelected(enabled, false);
		boots.setSelected(enabled, false);

		callback.run();
	}

	protected double calculateBonusXPMultiplier()
	{
		float bonusXP = 1f; // Default XP rate
		if (getEnabledButtonCount() == 4)
		{
			bonusXP += SET_BONUS;
		}
		else
		{
			bonusXP += helm.isSelected() ? HELM_BONUS : 0f;
			bonusXP += top.isSelected() ? TOP_BONUS : 0f;
			bonusXP += bottom.isSelected() ? BOTTOM_BONUS : 0f;
			bonusXP += boots.isSelected() ? BOOTS_BONUS : 0f;
		}
		return bonusXP;
	}

	@Override
	public double appliedXpRate(final Activity activity)
	{
		double bonusXP = calculateBonusXPMultiplier();

		return super.appliedXpRate(activity) * bonusXP;
	}

	// Used to add additional text to the tooltip text
	public void setTooltip(String tooltip)
	{
		panel.setToolTipText("<html>Increases xp gained while worn."
			+ "<br/>The full set increases the bonus by 0.5% for 2.5% instead of 2% bonus xp"
			+ "<br/>" + tooltip
			+ "</html>");
	}
}
