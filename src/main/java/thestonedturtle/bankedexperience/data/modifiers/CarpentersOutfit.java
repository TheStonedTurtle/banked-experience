package thestonedturtle.bankedexperience.data.modifiers;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.AllArgsConstructor;
import net.runelite.api.ItemComposition;
import net.runelite.api.Skill;
import net.runelite.client.game.ItemManager;
import thestonedturtle.bankedexperience.data.Activity;

import java.util.Collection;
import java.util.Map;

public class CarpentersOutfit extends SkillingOutfit
{
	private final Map<Activity, XpPerTask> xpPerMahoganyHomesActivityMap;

	private static final Collection<Activity> EXCLUDED = ImmutableSet.of(
			Activity.LONG_BONE, Activity.CURVED_BONE
	);

	CarpentersOutfit(ItemManager itemManager, ItemComposition... items)
	{
		super(Skill.CONSTRUCTION, "Carpenter's Outfit",
				null, EXCLUDED, itemManager, items);

		// The average xp rates for a plank in the Activity class is based upon the total exp received from completing a contract
		// Because the outfit does not apply to the bonus xp rewarded for completing the contract we need to separate the xp values
		// The averagePlankXp is the average amount each plank rewards for fixing a hotspot within a contract
		// The averageCompletionXpPerPlank is the average amount each plank is worth of the contract completion bonus xp
		// These were calculated by @SeaifanAladdin <https://github.com/SeaifanAladdin> in PR#101
		// REF: https://docs.google.com/spreadsheets/d/1573_E5suKS7OY2E3utGpBkuaReiF86hTh0p8PLqi2II/edit?usp=sharing
		xpPerMahoganyHomesActivityMap = ImmutableMap.<Activity, XpPerTask>builder()
				.put(Activity.MAHOGANY_HOMES_PLANK, new XpPerTask(39.955f, 53.695f))
				.put(Activity.MAHOGANY_HOMES_OAK, new XpPerTask(66.163f, 133.840f))
				.put(Activity.MAHOGANY_HOMES_TEAK, new XpPerTask(87.735f, 200.117f))
				.put(Activity.MAHOGANY_HOMES_MAHOGANY, new XpPerTask(127.259f, 218.861f))
				.build();
	}

	@Override
	public double appliedXpRate(Activity activity)
	{
		XpPerTask xpPerTask = xpPerMahoganyHomesActivityMap.get(activity);
		if (xpPerTask == null)
		{
			return super.appliedXpRate(activity);
		}

		double bonusXP = calculateBonusXPMultiplier();

		double averagePlankXp = xpPerTask.averagePlankXp;
		double averageCompletionXpPerPlank = xpPerTask.averageCompletionXpPerPlank;

		return (averagePlankXp * bonusXP) + averageCompletionXpPerPlank;
	}

	@AllArgsConstructor
	private static class XpPerTask
	{
		private float averagePlankXp;
		private float averageCompletionXpPerPlank;
	}
}
