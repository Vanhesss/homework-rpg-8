package com.narxoz.rpg;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.floor.BossFloor;
import com.narxoz.rpg.floor.CombatFloor;
import com.narxoz.rpg.floor.PoisonTrapFloor;
import com.narxoz.rpg.floor.RestFloor;
import com.narxoz.rpg.floor.TowerFloor;
import com.narxoz.rpg.state.BerserkState;
import com.narxoz.rpg.state.NormalState;
import com.narxoz.rpg.tower.TowerRunResult;
import com.narxoz.rpg.tower.TowerRunner;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== THE HAUNTED TOWER: ASCENDING THE FLOORS ===\n");

        List<Hero> party = new ArrayList<>();
        party.add(new Hero("Aragorn", 100, 18, 8, new NormalState()));
        party.add(new Hero("Luna", 80, 22, 5, new BerserkState()));

        System.out.println("Heroes assembled:");
        for (Hero hero : party) {
            System.out.println("  " + hero.getName() + " - HP: " + hero.getHp() + "/" + hero.getMaxHp()
                    + ", ATK: " + hero.getAttackPower() + ", DEF: " + hero.getDefense()
                    + " [" + hero.getState().getName() + "]");
        }

        List<TowerFloor> floors = new ArrayList<>();
        floors.add(new CombatFloor("Skeleton Crypt", "Skeleton Warrior", 40, 12));
        floors.add(new PoisonTrapFloor(8, 3, 4));
        floors.add(new RestFloor(25));
        floors.add(new CombatFloor("Haunted Gallery", "Wraith", 55, 15));
        floors.add(new BossFloor("Dragon Lord Ignatus", 90, 20));

        TowerRunner runner = new TowerRunner(floors);
        TowerRunResult result = runner.run(party);

        System.out.println("\n=== TOWER RUN COMPLETE ===");
        System.out.println("Floors cleared: " + result.getFloorsCleared() + " / " + floors.size());
        System.out.println("Heroes surviving: " + result.getHeroesSurviving() + " / " + party.size());
        System.out.println("Reached the top: " + (result.isReachedTop() ? "YES" : "NO"));

        System.out.println("\nFinal party status:");
        for (Hero hero : party) {
            if (hero.isAlive()) {
                System.out.println("  " + hero.getName() + " - HP: " + hero.getHp() + "/" + hero.getMaxHp() + " [" + hero.getState().getName() + "]");
            } else {
                System.out.println("  " + hero.getName() + " - FALLEN");
            }
        }
    }
}
