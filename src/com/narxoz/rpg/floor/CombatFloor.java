package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.Monster;

import java.util.ArrayList;
import java.util.List;

public class CombatFloor extends TowerFloor {

    private final String floorName;
    private final String monsterName;
    private final int monsterHp;
    private final int monsterAttack;
    private Monster monster;

    public CombatFloor(String floorName, String monsterName, int monsterHp, int monsterAttack) {
        this.floorName = floorName;
        this.monsterName = monsterName;
        this.monsterHp = monsterHp;
        this.monsterAttack = monsterAttack;
    }

    @Override
    protected String getFloorName() {
        return floorName;
    }

    @Override
    protected void setup(List<Hero> party) {
        monster = new Monster(monsterName, monsterHp, monsterAttack);
        System.out.println("A " + monster.getName() + " appears! (HP: " + monster.getHp() + ", ATK: " + monster.getAttackPower() + ")");
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        int totalDamage = 0;
        int round = 1;

        while (monster.isAlive()) {
            System.out.println("\n  -- Round " + round + " --");

            for (Hero hero : party) {
                if (!hero.isAlive()) continue;

                hero.onTurnStart();

                if (hero.canAct()) {
                    int damage = hero.getModifiedAttack();
                    monster.takeDamage(damage);
                    System.out.println("  " + hero.getName() + " [" + hero.getState().getName() + "] attacks " + monster.getName() + " for " + damage + " damage! (Monster HP: " + monster.getHp() + ")");
                } else {
                    System.out.println("  " + hero.getName() + " [" + hero.getState().getName() + "] cannot act this turn!");
                }

                hero.onTurnEnd();

                if (!monster.isAlive()) break;
            }

            if (monster.isAlive()) {
                List<Hero> alive = new ArrayList<>();
                for (Hero h : party) {
                    if (h.isAlive()) alive.add(h);
                }
                if (alive.isEmpty()) break;

                Hero target = alive.get(round % alive.size());
                int before = target.getHp();
                int rawDmg = Math.max(1, monster.getAttackPower() - 2);
                target.applyDamage(rawDmg);
                int actualDmg = before - target.getHp();
                totalDamage += actualDmg;
                System.out.println("  " + monster.getName() + " attacks " + target.getName() + " for " + actualDmg + " damage! (HP: " + target.getHp() + ")");
            }

            round++;
            if (round > 20) break;
        }

        boolean cleared = !monster.isAlive();
        String summary = cleared
                ? "The party defeated " + monster.getName() + "!"
                : "The party failed to defeat " + monster.getName() + ".";
        System.out.println(summary);
        return new FloorResult(cleared, totalDamage, summary);
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        if (result.isCleared()) {
            int healAmount = 5;
            for (Hero hero : party) {
                if (hero.isAlive()) {
                    hero.heal(healAmount);
                    System.out.println(hero.getName() + " receives a small potion and heals " + healAmount + " HP. (HP: " + hero.getHp() + ")");
                }
            }
        }
    }
}
