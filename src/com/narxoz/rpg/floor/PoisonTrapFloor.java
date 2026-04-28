package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.state.PoisonedState;

import java.util.List;

public class PoisonTrapFloor extends TowerFloor {

    private final int trapDamage;
    private final int poisonDuration;
    private final int poisonDamage;

    public PoisonTrapFloor(int trapDamage, int poisonDuration, int poisonDamage) {
        this.trapDamage = trapDamage;
        this.poisonDuration = poisonDuration;
        this.poisonDamage = poisonDamage;
    }

    @Override
    protected String getFloorName() {
        return "Poison Trap Room";
    }

    @Override
    protected void setup(List<Hero> party) {
        System.out.println("The floor is covered in toxic vents and bubbling pools of venom...");
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        int totalDamage = 0;

        for (Hero hero : party) {
            if (!hero.isAlive()) continue;

            int before = hero.getHp();
            hero.takeDamage(trapDamage);
            totalDamage += (before - hero.getHp());
            System.out.println(hero.getName() + " triggers a poison trap! Takes " + trapDamage + " damage. (HP: " + hero.getHp() + ")");

            if (hero.isAlive()) {
                hero.setState(new PoisonedState(poisonDuration, poisonDamage));
            }
        }

        boolean anyAlive = false;
        for (Hero h : party) {
            if (h.isAlive()) { anyAlive = true; break; }
        }

        String summary = anyAlive
                ? "The party survived the poison trap but everyone is poisoned!"
                : "The poison trap wiped out the entire party!";
        System.out.println(summary);
        return new FloorResult(anyAlive, totalDamage, summary);
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        if (result.isCleared()) {
            System.out.println("An antidote vial is found, but it's cracked and useless. No loot this time.");
        }
    }
}
