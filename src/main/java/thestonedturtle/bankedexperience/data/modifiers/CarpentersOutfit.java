package thestonedturtle.bankedexperience.data.modifiers;

import com.google.common.collect.ImmutableSet;
import net.runelite.api.ItemComposition;
import net.runelite.api.Skill;
import net.runelite.client.game.ItemManager;
import thestonedturtle.bankedexperience.data.Activity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CarpentersOutfit extends SkillingOutfit {

	/**
	 * parameters calculated from here
	 * https://docs.google.com/spreadsheets/d/1573_E5suKS7OY2E3utGpBkuaReiF86hTh0p8PLqi2II/edit?usp=sharing
	 */
	public final static double[] MAHOGANY_HOMES_PLANK_PARAMETERS = new double[]{39.95d, 53.69d};
	public final static double[] MAHOGANY_HOMES_OAK_PARAMETERS = new double[]{66.16d, 133.84d};
	public final static double[] MAHOGANY_HOMES_TEAK_PARAMETERS = new double[]{87.73d, 200.12d};
	public final static double[] MAHOGANY_HOMES_MAHOGANY_PARAMETERS = new double[]{127.26d, 218.86d};
	private final Map<Activity, double[]> mahoganyHomesActivityToParametersMap;


	private static final Collection<Activity> EXCLUDED = ImmutableSet.of(
			Activity.LONG_BONE, Activity.CURVED_BONE
	);

	CarpentersOutfit(ItemManager itemManager, ItemComposition... items) {
		super(Skill.CONSTRUCTION, "Carpenter's Outfit",
				null, EXCLUDED, itemManager, items);

		mahoganyHomesActivityToParametersMap = new HashMap<>(4);
		mahoganyHomesActivityToParametersMap.put(Activity.MAHOGANY_HOMES_PLANK, MAHOGANY_HOMES_PLANK_PARAMETERS);
		mahoganyHomesActivityToParametersMap.put(Activity.MAHOGANY_HOMES_OAK, MAHOGANY_HOMES_OAK_PARAMETERS);
		mahoganyHomesActivityToParametersMap.put(Activity.MAHOGANY_HOMES_TEAK, MAHOGANY_HOMES_TEAK_PARAMETERS);
		mahoganyHomesActivityToParametersMap.put(Activity.MAHOGANY_HOMES_MAHOGANY, MAHOGANY_HOMES_MAHOGANY_PARAMETERS);
	}

	@Override
	public double appliedXpRate(Activity activity) {
		double[] parameters = mahoganyHomesActivityToParametersMap.get(activity);
		if (parameters != null && parameters.length == 2) {
			double bonusXP = calculateBonusXPMultiplier();
			//m is the average xp per plank excluding completion xp
			//b is the average completion xp per plank
			double m = parameters[0];
			double b = parameters[1];

			return m * bonusXP + b;
		} else {
			return super.appliedXpRate(activity);
		}
	}
}
