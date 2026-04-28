package com.narxoz.rpg.state;

import com.narxoz.rpg.combatant.Hero;

public class RegenerationState implements HeroState {

    private int turnsRemaining;
    private final int healAmount;

    public RegenerationState(int duration, int healAmount) {
        this.turnsRemaining = duration;
        this.healAmount = healAmount;
    }

    @Override
    public String getName() {
        return "Regeneration";
    }

    @Override
    public int modifyOutgoingDamage(int basePower) {
        return basePower;
    }

    @Override
    public int modifyIncomingDamage(int rawDamage) {
        return (int) (rawDamage * 0.9);
    }

    @Override
    public void onTurnStart(Hero hero) {
        int before = hero.getHp();
        hero.heal(healAmount);
        System.out.println(hero.getName() + " regenerates " + (hero.getHp() - before) + " HP! (HP: " + before + " -> " + hero.getHp() + ")");
    }

    @Override
    public void onTurnEnd(Hero hero) {
        turnsRemaining--;
        if (turnsRemaining <= 0) {
            System.out.println(hero.getName() + "'s regeneration fades.");
            hero.setState(new NormalState());
        } else {
            System.out.println(hero.getName() + " continues regenerating for " + turnsRemaining + " more turn(s).");
        }
    }

    @Override
    public boolean canAct() {
        return true;
    }
}
