package com.narxoz.rpg.state;

import com.narxoz.rpg.combatant.Hero;

public class PoisonedState implements HeroState {

    private int turnsRemaining;
    private final int poisonDamage;

    public PoisonedState(int duration, int poisonDamage) {
        this.turnsRemaining = duration;
        this.poisonDamage = poisonDamage;
    }

    @Override
    public String getName() {
        return "Poisoned";
    }

    @Override
    public int modifyOutgoingDamage(int basePower) {
        return (int) (basePower * 0.7);
    }

    @Override
    public int modifyIncomingDamage(int rawDamage) {
        return rawDamage;
    }

    @Override
    public void onTurnStart(Hero hero) {
        System.out.println(hero.getName() + " takes " + poisonDamage + " poison damage! (HP: " + hero.getHp() + " -> " + Math.max(0, hero.getHp() - poisonDamage) + ")");
        hero.takeDamage(poisonDamage);
    }

    @Override
    public void onTurnEnd(Hero hero) {
        turnsRemaining--;
        if (turnsRemaining <= 0) {
            System.out.println(hero.getName() + "'s poison wears off.");
            hero.setState(new NormalState());
        } else {
            System.out.println(hero.getName() + " is still poisoned for " + turnsRemaining + " more turn(s).");
        }
    }

    @Override
    public boolean canAct() {
        return true;
    }
}
