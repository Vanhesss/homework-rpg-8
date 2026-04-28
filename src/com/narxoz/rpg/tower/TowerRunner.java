package com.narxoz.rpg.tower;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.floor.FloorResult;
import com.narxoz.rpg.floor.TowerFloor;

import java.util.List;

public class TowerRunner {

    private final List<TowerFloor> floors;

    public TowerRunner(List<TowerFloor> floors) {
        this.floors = floors;
    }

    public TowerRunResult run(List<Hero> party) {
        int floorsCleared = 0;

        for (int i = 0; i < floors.size(); i++) {
            System.out.println("\n========== FLOOR " + (i + 1) + " of " + floors.size() + " ==========");

            boolean anyAlive = false;
            for (Hero h : party) {
                if (h.isAlive()) { anyAlive = true; break; }
            }
            if (!anyAlive) {
                System.out.println("All heroes have fallen! The tower run ends.");
                break;
            }

            System.out.println("Party status:");
            for (Hero hero : party) {
                if (hero.isAlive()) {
                    System.out.println("  " + hero.getName() + " - HP: " + hero.getHp() + "/" + hero.getMaxHp() + " [" + hero.getState().getName() + "]");
                } else {
                    System.out.println("  " + hero.getName() + " - FALLEN");
                }
            }

            FloorResult result = floors.get(i).explore(party);

            if (result.isCleared()) {
                floorsCleared++;
            } else {
                System.out.println("Floor not cleared. The tower run ends.");
                break;
            }
        }

        int surviving = 0;
        for (Hero h : party) {
            if (h.isAlive()) surviving++;
        }

        boolean reachedTop = floorsCleared == floors.size();
        return new TowerRunResult(floorsCleared, surviving, reachedTop);
    }
}
