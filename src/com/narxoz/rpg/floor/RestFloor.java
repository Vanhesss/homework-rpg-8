package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.state.NormalState;
import com.narxoz.rpg.state.RegenerationState;

import java.util.List;

public class RestFloor extends TowerFloor {

    private final int healAmount;

    public RestFloor(int healAmount) {
        this.healAmount = healAmount;
    }

    @Override
    protected String getFloorName() {
        return "Sanctuary Rest Room";
    }

    @Override
    protected void setup(List<Hero> party) {
        System.out.println("A warm glow fills the room. A healing fountain stands in the center.");
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        for (Hero hero : party) {
            if (!hero.isAlive()) continue;

            int before = hero.getHp();
            hero.heal(healAmount);
            System.out.println(hero.getName() + " drinks from the fountain and heals " + (hero.getHp() - before) + " HP. (HP: " + hero.getHp() + ")");

            hero.setState(new RegenerationState(2, 5));
        }

        return new FloorResult(true, 0, "The party rests and recovers at the sanctuary.");
    }

    @Override
    protected boolean shouldAwardLoot(FloorResult result) {
        System.out.println("[Hook] shouldAwardLoot() overridden: no loot at the sanctuary.");
        return false;
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
    }

    @Override
    protected void cleanup(List<Hero> party) {
        System.out.println("The fountain's glow fades as the party leaves the sanctuary.");
    }
}
