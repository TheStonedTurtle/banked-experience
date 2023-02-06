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

public class CarpentersOutfit extends SkillingOutfit {

	private final Map<Activity, XpPerTask> xpPerMahoganyHomesActivityMap;


	private static final Collection<Activity> EXCLUDED = ImmutableSet.of(
			Activity.LONG_BONE, Activity.CURVED_BONE
	);

	CarpentersOutfit(ItemManager itemManager, ItemComposition... items) {
		super(Skill.CONSTRUCTION, "Carpenter's Outfit",
				null, EXCLUDED, itemManager, items);

		xpPerMahoganyHomesActivityMap = ImmutableMap.<Activity, XpPerTask>builder()
				.put(Activity.MAHOGANY_HOMES_PLANK, new XpPerTask(39.955f, 53.695f))
				.put(Activity.MAHOGANY_HOMES_OAK, new XpPerTask(66.163f, 133.840f))
				.put(Activity.MAHOGANY_HOMES_TEAK, new XpPerTask(87.735f, 200.117f))
				.put(Activity.MAHOGANY_HOMES_MAHOGANY, new XpPerTask(127.259f, 218.861f))
				.build();
	}

	@Override
	public double appliedXpRate(Activity activity) {
		XpPerTask xpPerTask = xpPerMahoganyHomesActivityMap.get(activity);
		if (xpPerTask != null) {
			double bonusXP = calculateBonusXPMultiplier();

			double averagePlankXp = xpPerTask.averagePlankXp;
			double averageCompletionXpPerPlank = xpPerTask.averageCompletionXpPerPlank;

			return averagePlankXp * bonusXP + averageCompletionXpPerPlank;
		} else {
			return super.appliedXpRate(activity);
		}
	}

	@AllArgsConstructor
	private static class XpPerTask {
		private float averagePlankXp;
		private float averageCompletionXpPerPlank;
	}
}
