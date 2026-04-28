package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.Monster;

import java.util.ArrayList;
import java.util.List;

/**
 * A standard combat floor where heroes fight monsters.
 * Demonstrates the Template Method pattern with a typical combat implementation.
 */
public class CombatFloor extends TowerFloor {

    private List<Monster> monsters;

    @Override
    protected String getFloorName() {
        return "Monster Lair";
    }

    @Override
    protected void setup(List<Hero> party) {
        System.out.println("Setting up combat floor...");
        monsters = new ArrayList<>();
        // Create monsters based on party size
        monsters.add(new Monster("Goblin", 8, 5));
        monsters.add(new Monster("Goblin Scout", 6, 4));
        if (party.size() > 1) {
            monsters.add(new Monster("Orc", 10, 6));
        }
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        System.out.println("Combat starts!");
        int totalDamageToParty = 0;
        
        // Combat loop: continue while both party and monsters have members alive
        int round = 0;
        while (!party.isEmpty() && !monsters.isEmpty()) {
            round++;
            System.out.println("\n  [Round " + round + "]");
            
            // Check for dead party members
            party.removeIf(hero -> !hero.isAlive());
            if (party.isEmpty()) break;
            
            // Heroes attack
            for (Hero hero : party) {
                if (!hero.canAct()) {
                    System.out.println("  " + hero.getName() + " cannot act!");
                    hero.onTurnEnd();
                    continue;
                }
                
                hero.onTurnStart();
                
                if (!monsters.isEmpty()) {
                    Monster target = monsters.get(0);
                    int damage = hero.calculateOutgoingDamage();
                    target.takeDamage(damage);
                    System.out.println("  " + hero.getName() + " attacks " + target.getName() + " for " + damage + " damage!");
                    
                    if (!target.isAlive()) {
                        monsters.remove(0);
                        System.out.println("  " + target.getName() + " is defeated!");
                    }
                }
                
                hero.onTurnEnd();
            }
            
            // Check for dead monsters
            monsters.removeIf(monster -> !monster.isAlive());
            if (monsters.isEmpty()) break;
            
            // Monsters attack back
            for (Monster monster : monsters) {
                Hero target = party.get(0);
                int rawDamage = monster.getAttackPower() - 1;
                int actualDamage = target.calculateIncomingDamage(rawDamage);
                target.takeDamage(actualDamage);
                totalDamageToParty += actualDamage;
                System.out.println("  " + monster.getName() + " attacks " + target.getName() + " for " + actualDamage + " damage!");
            }
        }
        
        boolean cleared = monsters.isEmpty();
        String summary = cleared ? "Party defeated all monsters!" : "Party was overwhelmed!";
        return new FloorResult(cleared, totalDamageToParty, summary);
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        if (result.isCleared()) {
            System.out.println("Loot awarded: Each hero gains 10 HP");
            for (Hero hero : party) {
                hero.heal(10);
            }
        }
    }
}
