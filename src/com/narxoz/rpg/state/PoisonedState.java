package com.narxoz.rpg.state;

import com.narxoz.rpg.combatant.Hero;

/**
 * A debuff state where the hero is poisoned.
 * - Reduces outgoing damage by 30%
 - Applies 3 poison damage per turn (onTurnStart)
 * - Self-transitions after 3 turns (decrement counter each turn)
 * This demonstrates a self-transitioning state that manages its own lifecycle.
 */
public class PoisonedState implements HeroState {

    private int turnsRemaining;
    private static final int POISON_DURATION = 3;
    private static final int POISON_DAMAGE = 3;

    public PoisonedState() {
        this.turnsRemaining = POISON_DURATION;
    }

    @Override
    public String getName() {
        return "Poisoned";
    }

    @Override
    public int modifyOutgoingDamage(int basePower) {
        // Poison weakens the hero's attacks
        return (int) (basePower * 0.7);
    }

    @Override
    public int modifyIncomingDamage(int rawDamage) {
        // Poison doesn't modify incoming damage
        return rawDamage;
    }

    @Override
    public void onTurnStart(Hero hero) {
        // Apply poison damage
        System.out.println("  [" + hero.getName() + " takes " + POISON_DAMAGE + " poison damage]");
        hero.takeDamage(POISON_DAMAGE);
    }

    @Override
    public void onTurnEnd(Hero hero) {
        // Decrement poison duration counter
        turnsRemaining--;
        
        // Self-transition: if poison wears off, revert to normal state
        if (turnsRemaining <= 0) {
            System.out.println("  [" + hero.getName() + "'s poison wears off!]");
            hero.setState(new NormalState());
        }
    }

    @Override
    public boolean canAct() {
        return true;
    }
}
