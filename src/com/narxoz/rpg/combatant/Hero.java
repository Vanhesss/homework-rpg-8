package com.narxoz.rpg.combatant;

import com.narxoz.rpg.state.HeroState;
import com.narxoz.rpg.state.NormalState;

/**
 * Represents a player-controlled hero participating in the tower climb.
 *
 * Students: you may extend this class as needed for your implementation.
 * You will need to add a HeroState field and related methods.
 */
public class Hero {

    private final String name;
    private int hp;
    private final int maxHp;
    private final int attackPower;
    private final int defense;
    private HeroState state;

    public Hero(String name, int hp, int attackPower, int defense) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.attackPower = attackPower;
        this.defense = defense;
        this.state = new NormalState();  // Start in normal state
    }

    public String getName()        { return name; }
    public int getHp()             { return hp; }
    public int getMaxHp()          { return maxHp; }
    public int getAttackPower()    { return attackPower; }
    public int getDefense()        { return defense; }
    public boolean isAlive()       { return hp > 0; }
    
    public HeroState getState()    { return state; }
    public void setState(HeroState newState) { this.state = newState; }

    /**
     * Reduces this hero's HP by the given amount, clamped to zero.
     *
     * @param amount the damage to apply; must be non-negative
     */
    public void takeDamage(int amount) {
        hp = Math.max(0, hp - amount);
    }

    /**
     * Restores this hero's HP by the given amount, clamped to maxHp.
     *
     * @param amount the HP to restore; must be non-negative
     */
    public void heal(int amount) {
        hp = Math.min(maxHp, hp + amount);
    }
    
    /**
     * Calculates the actual outgoing damage for this hero based on base power
     * and the current state's modifiers.
     */
    public int calculateOutgoingDamage() {
        return state.modifyOutgoingDamage(attackPower);
    }
    
    /**
     * Calculates the actual incoming damage based on raw damage and the
     * current state's modifiers.
     */
    public int calculateIncomingDamage(int rawDamage) {
        return state.modifyIncomingDamage(rawDamage);
    }
    
    /**
     * Triggers state callbacks at turn start.
     */
    public void onTurnStart() {
        state.onTurnStart(this);
    }
    
    /**
     * Triggers state callbacks at turn end.
     */
    public void onTurnEnd() {
        state.onTurnEnd(this);
    }
    
    /**
     * Checks if hero can act in the current state.
     */
    public boolean canAct() {
        return state.canAct();
    }
}
