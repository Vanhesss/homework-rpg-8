package com.narxoz.rpg.state;

import com.narxoz.rpg.combatant.Hero;

/**
 * A debuff state where the hero is stunned.
 * - Cannot act (canAct returns false)
 - Reduces incoming damage modifier by 25% (takes less damage while stunned)
 * - Lasts for 1 turn, then transitions to normal
 * This demonstrates a state that prevents action and has self-transition logic.
 */
public class StunnedState implements HeroState {

    private int turnsRemaining;
    private static final int STUN_DURATION = 1;

    public StunnedState() {
        this.turnsRemaining = STUN_DURATION;
    }

    @Override
    public String getName() {
        return "Stunned";
    }

    @Override
    public int modifyOutgoingDamage(int basePower) {
        // Stunned heroes cannot attack
        return 0;
    }

    @Override
    public int modifyIncomingDamage(int rawDamage) {
        // Stunned heroes are harder to hit
        return (int) (rawDamage * 0.75);
    }

    @Override
    public void onTurnStart(Hero hero) {
        System.out.println("  [" + hero.getName() + " is stunned and cannot act!]");
    }

    @Override
    public void onTurnEnd(Hero hero) {
        turnsRemaining--;
        if (turnsRemaining <= 0) {
            System.out.println("  [" + hero.getName() + " recovers from stun!]");
            hero.setState(new NormalState());
        }
    }

    @Override
    public boolean canAct() {
        // CRITICAL: This returns false, demonstrating state-based control flow changes
        return false;
    }
}
