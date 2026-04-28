package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.Monster;
import com.narxoz.rpg.state.BerserkState;
import com.narxoz.rpg.state.StunnedState;

import java.util.ArrayList;
import java.util.List;

public class BossFloor extends TowerFloor {

    private final String bossName;
    private final int bossHp;
    private final int bossAttack;
    private Monster boss;

    public BossFloor(String bossName, int bossHp, int bossAttack) {
        this.bossName = bossName;
        this.bossHp = bossHp;
        this.bossAttack = bossAttack;
    }

    @Override
    protected String getFloorName() {
        return "Boss Chamber: " + bossName;
    }

    @Override
    protected void announce() {
        System.out.println("\n===================================");
        System.out.println("  BOSS FLOOR: " + bossName.toUpperCase());
        System.out.println("  Prepare for a deadly battle!");
        System.out.println("===================================");
    }

    @Override
    protected void setup(List<Hero> party) {
        boss = new Monster(bossName, bossHp, bossAttack);
        System.out.println(boss.getName() + " emerges from the shadows! (HP: " + boss.getHp() + ", ATK: " + boss.getAttackPower() + ")");

        for (Hero hero : party) {
            if (hero.isAlive()) {
                double hpPercent = (double) hero.getHp() / hero.getMaxHp();
                if (hpPercent <= 0.5) {
                    hero.setState(new BerserkState());
                }
            }
        }
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        int totalDamage = 0;
        int round = 1;

        while (boss.isAlive()) {
            System.out.println("\n  -- Boss Round " + round + " --");

            for (Hero hero : party) {
                if (!hero.isAlive()) continue;

                hero.onTurnStart();

                if (hero.canAct()) {
                    int damage = hero.getModifiedAttack();
                    boss.takeDamage(damage);
                    System.out.println("  " + hero.getName() + " [" + hero.getState().getName() + "] strikes " + boss.getName() + " for " + damage + "! (Boss HP: " + boss.getHp() + ")");
                } else {
                    System.out.println("  " + hero.getName() + " [" + hero.getState().getName() + "] is unable to act!");
                }

                hero.onTurnEnd();

                if (!boss.isAlive()) break;
            }

            if (boss.isAlive()) {
                List<Hero> alive = new ArrayList<>();
                for (Hero h : party) {
                    if (h.isAlive()) alive.add(h);
                }
                if (alive.isEmpty()) break;

                Hero target = alive.get(round % alive.size());
                int before = target.getHp();
                int rawDmg = Math.max(1, boss.getAttackPower() - 2);
                target.applyDamage(rawDmg);
                int actualDmg = before - target.getHp();
                totalDamage += actualDmg;
                System.out.println("  " + boss.getName() + " smashes " + target.getName() + " for " + actualDmg + " damage! (HP: " + target.getHp() + ")");

                if (target.isAlive() && actualDmg > 15) {
                    target.setState(new StunnedState(1));
                }
            }

            round++;
            if (round > 30) break;
        }

        boolean cleared = !boss.isAlive();
        String summary = cleared
                ? "The mighty " + boss.getName() + " has been slain!"
                : "The party was overwhelmed by " + boss.getName() + "...";
        System.out.println(summary);
        return new FloorResult(cleared, totalDamage, summary);
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        if (result.isCleared()) {
            int healAmount = 20;
            for (Hero hero : party) {
                if (hero.isAlive()) {
                    hero.heal(healAmount);
                    System.out.println(hero.getName() + " finds a legendary potion and heals " + healAmount + " HP! (HP: " + hero.getHp() + ")");
                }
            }
        }
    }

    @Override
    protected void cleanup(List<Hero> party) {
        System.out.println("The boss chamber crumbles as the party advances...");
    }
}
