package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.Monster;
import com.narxoz.rpg.state.BerserkState;
import com.narxoz.rpg.state.RegenerationState;

import java.util.List;

/**
 * A boss floor with a powerful boss monster.
 * This floor:
 * - Overrides announce() to provide a dramatic introduction (hook override)
 * - Has non-trivial combat with hero state transitions
 * - Demonstrates the template method with a challenging scenario
 */
public class BossFloor extends TowerFloor {

    private Monster boss;

    @Override
    protected String getFloorName() {
        return "Dragon's Peak";
    }

    /**
     * HOOK OVERRIDE: Custom announcement for the boss floor.
     * This demonstrates how subclasses can override default hooks.
     */
    @Override
    protected void announce() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║  FINAL FLOOR — " + getFloorName() + "  ║");
        System.out.println("║  A terrible roar echoes through the tower... ║");
        System.out.println("╚════════════════════════════════════════╝");
    }

    @Override
    protected void setup(List<Hero> party) {
        System.out.println("Setting up boss encounter...");
        boss = new Monster("Ancient Dragon", 40, 12);
        System.out.println("A massive " + boss.getName() + " appears! (HP: " + boss.getHp() + ")");
        
        // Heroes may trigger berserker mode when facing the boss
        for (Hero hero : party) {
            if (Math.random() < 0.3) {
                System.out.println("  " + hero.getName() + " enters a battle trance, becoming BERSERK!");
                hero.setState(new BerserkState());
            } else if (Math.random() < 0.3) {
                System.out.println("  " + hero.getName() + " enters a healing focus, gaining REGENERATION!");
                hero.setState(new RegenerationState());
            }
        }
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        System.out.println("Epic boss battle begins!");
        int totalDamage = 0;
        int round = 0;
        
        while (boss.isAlive() && !party.isEmpty()) {
            round++;
            System.out.println("\n  [Round " + round + "] Boss HP: " + boss.getHp());
            
            // Remove dead heroes
            party.removeIf(hero -> !hero.isAlive());
            if (party.isEmpty()) break;
            
            // Heroes attack
            for (Hero hero : party) {
                if (!hero.canAct()) {
                    System.out.println("  " + hero.getName() + " is stunned, cannot act!");
                    hero.onTurnEnd();
                    continue;
                }
                
                hero.onTurnStart();
                
                int damage = hero.calculateOutgoingDamage();
                boss.takeDamage(damage);
                System.out.println("  " + hero.getName() + " hits the boss for " + damage + " damage! (Boss HP: " + boss.getHp() + ")");
                
                hero.onTurnEnd();
            }
            
            if (!boss.isAlive()) break;
            
            // Boss attacks all heroes
            for (Hero hero : party) {
                int rawDamage = boss.getAttackPower();
                int actualDamage = hero.calculateIncomingDamage(rawDamage);
                hero.takeDamage(actualDamage);
                totalDamage += actualDamage;
                System.out.println("  Boss attacks " + hero.getName() + " for " + actualDamage + " damage! (Hero HP: " + hero.getHp() + ")");
            }
        }
        
        boolean cleared = boss.isAlive() == false;
        String summary = cleared ? "BOSS DEFEATED! Tower conquered!" : "Party fell to the boss...";
        return new FloorResult(cleared, totalDamage, summary);
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        if (result.isCleared()) {
            System.out.println("VICTORY! Legendary treasure: Every hero gains maximum healing and a blessing!");
            for (Hero hero : party) {
                hero.heal(hero.getMaxHp());
                System.out.println("  " + hero.getName() + " fully healed!");
            }
        }
    }
}
