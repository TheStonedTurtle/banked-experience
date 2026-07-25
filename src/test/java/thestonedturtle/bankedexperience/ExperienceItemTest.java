package thestonedturtle.bankedexperience;

import net.runelite.api.Skill;
import org.junit.Test;
import thestonedturtle.bankedexperience.data.ExperienceItem;

import java.util.Collection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ExperienceItemTest {
	private final BankedExperienceConfig BATTLESTAVES_AS_SECONDARY = new BankedExperienceConfig() {
		@Override
		public void ignoredItems(String val) {
			throw new UnsupportedOperationException("Unused?");
		}

		@Override
		public boolean showBattlestavesAsSecondary() {
			return true;
		}
	};

	private final BankedExperienceConfig BATTLESTAVES_AS_PRIMARY = new BankedExperienceConfig() {
		@Override
		public void ignoredItems(String val) {
			throw new UnsupportedOperationException("Unused?");
		}

		@Override
		public boolean showBattlestavesAsSecondary() {
			return false;
		}
	};

	@Test
	public void whenShowBattlestavesAsSecondaryShouldIncludeOrbs() {
		Collection<ExperienceItem> items =
				ExperienceItem.getBySkillAndConfig(Skill.CRAFTING, BATTLESTAVES_AS_SECONDARY);

		assertTrue(items.contains(ExperienceItem.WATER_ORB));
		assertTrue(items.contains(ExperienceItem.EARTH_ORB));
		assertTrue(items.contains(ExperienceItem.FIRE_ORB));
		assertTrue(items.contains(ExperienceItem.AIR_ORB));
		assertFalse(items.contains(ExperienceItem.BATTLESTAFF));
	}

	@Test
	public void whenShowBattlestavesAsPrimaryShouldIncludeBattlestaff() {
		Collection<ExperienceItem> items =
				ExperienceItem.getBySkillAndConfig(Skill.CRAFTING, BATTLESTAVES_AS_PRIMARY);

		assertFalse(items.contains(ExperienceItem.WATER_ORB));
		assertFalse(items.contains(ExperienceItem.EARTH_ORB));
		assertFalse(items.contains(ExperienceItem.FIRE_ORB));
		assertFalse(items.contains(ExperienceItem.AIR_ORB));
		assertTrue(items.contains(ExperienceItem.BATTLESTAFF));
	}
}
