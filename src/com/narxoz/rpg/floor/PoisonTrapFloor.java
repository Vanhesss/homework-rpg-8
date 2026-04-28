package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.state.PoisonedState;
import com.narxoz.rpg.state.StunnedState;

import java.util.List;

/**
 * A trap floor filled with poison gas and poisoned enemies.
 * This floor demonstrates state transitions during challenge resolution.
 * Heroes take poison damage and may transition to poisoned/stunned states.
 */
public class PoisonTrapFloor extends TowerFloor {

    private int trapDamage;

    @Override
    protected String getFloorName() {
        return "Poison Trap Chamber";
    }

    @Override
    protected void setup(List<Hero> party) {
        System.out.println("Setting up poison trap floor...");
        trapDamage = 5 + (party.size() * 2);
        System.out.println("Poison gas fills the chamber!");
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        System.out.println("Navigating through poison traps...");
        int totalDamage = 0;
        int heroesAffected = 0;
        
        for (Hero hero : party) {
            // Each hero takes poison damage
            System.out.println("  " + hero.getName() + " inhales poison gas!");
            hero.takeDamage(trapDamage);
            totalDamage += trapDamage;
            
            // Random chance to apply poison or stun state
            double chance = Math.random();
            if (chance < 0.5 && !(hero.getState() instanceof PoisonedState)) {
                System.out.println("  " + hero.getName() + " becomes poisoned!");
                hero.setState(new PoisonedState());
                heroesAffected++;
            } else if (chance < 0.75 && !(hero.getState() instanceof StunnedState)) {
                System.out.println("  " + hero.getName() + " is stunned by the fumes!");
                hero.setState(new StunnedState());
                heroesAffected++;
            }
        }
        
        final int affected = heroesAffected;
        boolean cleared = party.stream().anyMatch(hero -> affected > 0); // Cleared if state changes occurred
        String summary = "Floor cleared. " + heroesAffected + " heroes affected by poison/stun.";
        return new FloorResult(cleared, totalDamage, summary);
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        System.out.println("Loot: Antidote found! Each hero heals 8 HP");
        for (Hero hero : party) {
            hero.heal(8);
        }
    }
}
