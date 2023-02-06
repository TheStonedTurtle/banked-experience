package thestonedturtle.bankedexperience.data.modifiers;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ItemComposition;
import net.runelite.api.Skill;
import net.runelite.client.game.ItemManager;
import thestonedturtle.bankedexperience.data.Activity;

import java.util.Collection;
import java.util.Map;

@Slf4j
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

		final float PRECISION_LEVEL = 0.0001f;
		for (Activity activity : xpPerMahoganyHomesActivityMap.keySet()){
			XpPerTask xpPerTask = xpPerMahoganyHomesActivityMap.get(activity);

			float baseXp = xpPerTask.averagePlankXp + xpPerTask.averageCompletionXpPerPlank;
			boolean isBaseXpSame = Math.abs(baseXp - activity.getXp()) < PRECISION_LEVEL;
			if (!isBaseXpSame){
				log.warn(String.format("xp of Activity %s is %.3f but calculated base xp of carpenters Outfit is calculated to be %.3f", activity.getName(), activity.getXp(), baseXp));
			}
		}
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
