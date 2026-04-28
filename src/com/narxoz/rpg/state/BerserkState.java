package com.narxoz.rpg.state;

import com.narxoz.rpg.combatant.Hero;

public class BerserkState implements HeroState {

    @Override
    public String getName() {
        return "Berserk";
    }

    @Override
    public int modifyOutgoingDamage(int basePower) {
        return (int) (basePower * 1.5);
    }

    @Override
    public int modifyIncomingDamage(int rawDamage) {
        return (int) (rawDamage * 1.3);
    }

    @Override
    public void onTurnStart(Hero hero) {
        System.out.println(hero.getName() + " is in a berserker rage! (+50% attack, +30% damage taken)");
    }

    @Override
    public void onTurnEnd(Hero hero) {
        double hpPercent = (double) hero.getHp() / hero.getMaxHp();
        if (hpPercent > 0.5) {
            System.out.println(hero.getName() + " calms down as HP is above 50%.");
            hero.setState(new NormalState());
        } else {
            System.out.println(hero.getName() + " remains berserk (HP at " + (int) (hpPercent * 100) + "%).");
        }
    }

    @Override
    public boolean canAct() {
        return true;
    }
}
