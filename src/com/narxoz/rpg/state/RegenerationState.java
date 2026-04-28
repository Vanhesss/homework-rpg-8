package com.narxoz.rpg.state;

import com.narxoz.rpg.combatant.Hero;

/**
 * A buff state where the hero regenerates health each turn.
 * - Healing triggers at turn start
 * - No damage modifiers (hero maintains normal offense/defense)
 * - Lasts for 4 turns, then transitions to normal
 * This demonstrates a positive state with passive healing effects.
 */
public class RegenerationState implements HeroState {

    private int turnsRemaining;
    private static final int REGEN_DURATION = 4;
    private static final int HEALING_PER_TURN = 5;

    public RegenerationState() {
        this.turnsRemaining = REGEN_DURATION;
    }

    @Override
    public String getName() {
        return "Regenerating";
    }

    @Override
    public int modifyOutgoingDamage(int basePower) {
        // Regeneration doesn't affect offense
        return basePower;
    }

    @Override
    public int modifyIncomingDamage(int rawDamage) {
        // Regeneration doesn't affect defense
        return rawDamage;
    }

    @Override
    public void onTurnStart(Hero hero) {
        System.out.println("  [" + hero.getName() + " heals " + HEALING_PER_TURN + " HP]");
        hero.heal(HEALING_PER_TURN);
    }

    @Override
    public void onTurnEnd(Hero hero) {
        turnsRemaining--;
        if (turnsRemaining <= 0) {
            System.out.println("  [" + hero.getName() + "'s regeneration ends.]");
            hero.setState(new NormalState());
        }
    }

    @Override
    public boolean canAct() {
        return true;
    }
}
