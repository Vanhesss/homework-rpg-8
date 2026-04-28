package com.narxoz.rpg.state;

import com.narxoz.rpg.combatant.Hero;

/**
 * The baseline neutral state where the hero is unaffected by any special condition.
 * Damage and action are unmodified.
 */
public class NormalState implements HeroState {

    @Override
    public String getName() {
        return "Normal";
    }

    @Override
    public int modifyOutgoingDamage(int basePower) {
        return basePower;
    }

    @Override
    public int modifyIncomingDamage(int rawDamage) {
        return rawDamage;
    }

    @Override
    public void onTurnStart(Hero hero) {
        // No special effects in normal state
    }

    @Override
    public void onTurnEnd(Hero hero) {
        // No special effects in normal state
    }

    @Override
    public boolean canAct() {
        return true;
    }
}
