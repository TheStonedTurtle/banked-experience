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

	public final static double[] MAHOGANY_HOMES_PLANK_PARAMETERS = new double[]{39.92d, 53.54d};
	public final static double[] MAHOGANY_HOMES_OAK_PARAMETERS = new double[]{66.16d, 133.84d};
	public final static double[] MAHOGANY_HOMES_TEAK_PARAMETERS = new double[]{87.73d, 200.12d};
	public final static double[] MAHOGANY_HOMES_MAHOGANY_PARAMETERS = new double[]{127.26d, 218.86d};
	private final Map<Activity, double[]> table;


	private static final Collection<Activity> EXCLUDED = ImmutableSet.of(
			Activity.LONG_BONE, Activity.CURVED_BONE
	);

	CarpentersOutfit(ItemManager itemManager, ItemComposition... items) {
		super(Skill.CONSTRUCTION, "Carpenter's Outfit",
				null, EXCLUDED, itemManager, items);

		table = new HashMap<>(4);
		table.put(Activity.MAHOGANY_HOMES_PLANK, MAHOGANY_HOMES_PLANK_PARAMETERS);
		table.put(Activity.MAHOGANY_HOMES_OAK, MAHOGANY_HOMES_OAK_PARAMETERS);
		table.put(Activity.MAHOGANY_HOMES_TEAK, MAHOGANY_HOMES_TEAK_PARAMETERS);
		table.put(Activity.MAHOGANY_HOMES_MAHOGANY, MAHOGANY_HOMES_MAHOGANY_PARAMETERS);
	}

	@Override
	public double appliedXpRate(Activity activity) {
		double[] parameters = table.get(activity);
		if (parameters != null && parameters.length == 2) {
			double bonusXP = calculateBonusXPMultiplier();
			double m = parameters[0];
			double b = parameters[1];

			return m * bonusXP + b;
		} else {
			return super.appliedXpRate(activity);
		}
	}
}
