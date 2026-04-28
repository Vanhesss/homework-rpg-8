package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;

import java.util.List;

/**
 * A rest floor where heroes recover and gain buffs.
 * This floor OVERRIDES shouldAwardLoot() to return false, demonstrating the hook mechanism.
 * Instead of loot, heroes receive healing and regeneration.
 */
public class RestFloor extends TowerFloor {

    @Override
    protected String getFloorName() {
        return "Sanctuary of Rest";
    }

    @Override
    protected void setup(List<Hero> party) {
        System.out.println("Setting up rest sanctuary...");
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        System.out.println("Party rests and recovers...");
        // No challenge here, just healing
        return new FloorResult(true, 0, "Party recovered completely");
    }

    /**
     * HOOK OVERRIDE: This floor does NOT award loot after clearing.
     * Instead, awardLoot() is NOT called because shouldAwardLoot returns false.
     * This demonstrates the hook mechanism.
     */
    @Override
    protected boolean shouldAwardLoot(FloorResult result) {
        System.out.println("  [No loot here, just rest and recovery]");
        return false;  // This prevents awardLoot() from being called
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        // This won't be called due to shouldAwardLoot() override
        System.out.println("Loot award (should not print!)");
    }

    @Override
    protected void cleanup(List<Hero> party) {
        // Custom cleanup: heroes fully heal and gain regeneration
        System.out.println("  Rest floor cleanup: All heroes fully healed");
        for (Hero hero : party) {
            int fullHeal = hero.getMaxHp() - hero.getHp();
            if (fullHeal > 0) {
                hero.heal(fullHeal);
                System.out.println("  " + hero.getName() + " heals " + fullHeal + " HP!");
            }
        }
    }
}
