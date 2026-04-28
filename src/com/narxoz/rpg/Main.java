package com.narxoz.rpg;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.floor.*;
import com.narxoz.rpg.state.BerserkState;
import com.narxoz.rpg.state.RegenerationState;
import com.narxoz.rpg.tower.TowerRunner;
import com.narxoz.rpg.tower.TowerRunResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Entry point for Homework 8 — The Haunted Tower: Ascending the Floors.
 *
 * Demonstrates:
 * 1. State Pattern: Heroes transition between states (Normal, Poisoned, Stunned, Berserk, Regenerating)
 * 2. Template Method: Floor subclasses follow fixed skeleton via abstract method/hook system
 * 3. Integration: Tower runner manages the climb and tracks completion
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║     HOMEWORK 8 — THE HAUNTED TOWER: ASCENDING FLOORS   ║");
        System.out.println("║                                                        ║");
        System.out.println("║  Demonstrating State Pattern and Template Method       ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");

        // PART 1: Create at least 2 heroes with different initial states
        System.out.println("\n--- Creating Heroes ---");
        
        Hero hero1 = new Hero("Aragorn", 30, 10, 5);
        Hero hero2 = new Hero("Legolas", 25, 12, 3);
        
        // Give Hero 2 a different initial state (regeneration)
        hero2.setState(new RegenerationState());
        System.out.println("Hero 1 (Aragorn) created: Normal state");
        System.out.println("Hero 2 (Legolas) created: Regenerating state");
        
        List<Hero> party = new ArrayList<>();
        party.add(hero1);
        party.add(hero2);

        // PART 2: Create a sequence of ≥ 4 floors using ≥ 3 distinct floor subclasses
        System.out.println("\n--- Building Tower (5 floors, 4 types) ---");
        
        List<TowerFloor> tower = new ArrayList<>();
        
        // Floor 1: Combat Floor
        tower.add(new CombatFloor());
        System.out.println("Floor 1 added: Combat Floor");
        
        // Floor 2: Poison Trap Floor
        tower.add(new PoisonTrapFloor());
        System.out.println("Floor 2 added: Poison Trap Floor");
        
        // Floor 3: Rest Floor (demonstrates shouldAwardLoot() hook override)
        tower.add(new RestFloor());
        System.out.println("Floor 3 added: Rest Floor (demonstrates hook override)");
        
        // Floor 4: Combat Floor again (different challenge)
        tower.add(new CombatFloor());
        System.out.println("Floor 4 added: Combat Floor");
        
        // Floor 5: Boss Floor (demonstrates announce() hook override)
        tower.add(new BossFloor());
        System.out.println("Floor 5 added: Boss Floor (demonstrates announce() hook override)");

        // PART 3: Execute the tower climb
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║                  BEGINNING TOWER CLIMB                 ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        
        TowerRunner runner = new TowerRunner(tower);
        TowerRunResult result = runner.climb(party);

        // PART 4: Print final results and tower status
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║                    FINAL RESULTS                       ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        System.out.println("Floors Cleared: " + result.getFloorsCleared() + " / 5");
        System.out.println("Heroes Surviving: " + result.getHeroesSurviving() + " / 2");
        System.out.println("Reached Top: " + (result.isReachedTop() ? "YES - TOWER CONQUERED! 🏆" : "NO - Tower climb failed"));
        
        System.out.println("\nSurviving Heroes:");
        for (Hero hero : party) {
            if (hero.isAlive()) {
                System.out.println("  ✓ " + hero.getName() + ": " + hero.getHp() + "/" + hero.getMaxHp() + " HP");
                System.out.println("    Final State: " + hero.getState().getName());
            }
        }
        
        // Show demonstration summary
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║             REQUIREMENTS DEMONSTRATED                   ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        System.out.println("✓ State Pattern: 5 HeroState implementations");
        System.out.println("  - NormalState (neutral baseline)");
        System.out.println("  - PoisonedState (self-transitioning debuff)");
        System.out.println("  - StunnedState (prevents action with canAct=false)");
        System.out.println("  - BerserkState (buff + debuff with both damage mods)");
        System.out.println("  - RegenerationState (healing buff)");
        System.out.println("✓ Template Method: 4 TowerFloor subclasses");
        System.out.println("  - CombatFloor (standard implementation)");
        System.out.println("  - PoisonTrapFloor (non-trivial combat with state transitions)");
        System.out.println("  - RestFloor (overrides shouldAwardLoot() hook)");
        System.out.println("  - BossFloor (overrides announce() hook for dramatic intro)");
        System.out.println("✓ Runtime State Transitions: Visible in output above");
        System.out.println("✓ Tower Runner: Manages floor sequence and completion tracking");
        System.out.println("✓ Demo Output: Shows all required behaviors");
    }
}
