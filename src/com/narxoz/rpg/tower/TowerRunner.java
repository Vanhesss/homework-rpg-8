package com.narxoz.rpg.tower;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.floor.TowerFloor;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the execution of a tower climb.
 * Tracks which floors have been cleared and determines tower completion status.
 */
public class TowerRunner {

    private final List<TowerFloor> floors;
    private int floorsCleared;
    private List<Hero> survivors;

    public TowerRunner(List<TowerFloor> floors) {
        this.floors = new ArrayList<>(floors);
        this.floorsCleared = 0;
        this.survivors = new ArrayList<>();
    }

    /**
     * Executes the tower climb, exploring floors in sequence until party fails or tower is completed.
     *
     * @param party the initial party of heroes
     * @return a TowerRunResult with summary statistics
     */
    public TowerRunResult climb(List<Hero> party) {
        survivors = new ArrayList<>(party);
        floorsCleared = 0;

        for (int i = 0; i < floors.size(); i++) {
            TowerFloor floor = floors.get(i);
            
            // Remove dead heroes from the party
            survivors.removeIf(hero -> !hero.isAlive());
            
            // Check if party is wiped
            if (survivors.isEmpty()) {
                System.out.println("\nTower climb FAILED: Party wiped on floor " + (i + 1));
                break;
            }
            
            System.out.println("\n========== FLOOR " + (i + 1) + " / " + floors.size() + " ==========");
            
            // Execute floor
            var result = floor.explore(survivors);
            
            if (result.isCleared()) {
                floorsCleared++;
                System.out.println("✓ Floor cleared: " + result.getSummary());
            } else {
                System.out.println("✗ Floor failed: " + result.getSummary());
                // Party failed, continue to next floor anyway for demo purposes
                System.out.println("Party pushes forward despite the setback...");
            }
            
            System.out.println("Party status after floor: " + survivors.size() + " heroes alive");
            for (Hero hero : survivors) {
                System.out.println("  - " + hero.getName() + ": " + hero.getHp() + "/" + hero.getMaxHp() + " HP, State: " + hero.getState().getName());
            }
        }

        // Final summary
        System.out.println("\n========== TOWER CLIMB SUMMARY ==========");
        boolean reachedTop = floorsCleared == floors.size() && !survivors.isEmpty();
        return new TowerRunResult(floorsCleared, survivors.size(), reachedTop);
    }
}
