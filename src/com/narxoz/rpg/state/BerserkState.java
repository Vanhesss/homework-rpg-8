package com.narxoz.rpg.state;

import com.narxoz.rpg.combatant.Hero;

/**
 * A buff state where the hero enters a berserk rage.
 * - Increases outgoing damage by 50%
 * - Increases incoming damage by 20% (less defense while raging)
 * - Self-transitions to normal after 2 turns
 * This demonstrates a powerful buff/debuff with both positive and negative modifiers.
 */
public class BerserkState implements HeroState {

    private int turnsRemaining;
    private static final int BERSERK_DURATION = 2;

    public BerserkState() {
        this.turnsRemaining = BERSERK_DURATION;
    }

    @Override
    public String getName() {
        return "Berserk";
    }

    @Override
    public int modifyOutgoingDamage(int basePower) {
        // Berserking increases attack power dramatically
        return (int) (basePower * 1.5);
    }

    @Override
    public int modifyIncomingDamage(int rawDamage) {
        // But berserk heroes are less defensive
        return (int) (rawDamage * 1.2);
    }

    @Override
    public void onTurnStart(Hero hero) {
        System.out.println("  [" + hero.getName() + " rages with berserk fury!]");
    }

    @Override
    public void onTurnEnd(Hero hero) {
        turnsRemaining--;
        if (turnsRemaining <= 0) {
            System.out.println("  [" + hero.getName() + "'s berserk rage subsides.]");
            hero.setState(new NormalState());
        }
    }

    @Override
    public boolean canAct() {
        return true;
    }
}
