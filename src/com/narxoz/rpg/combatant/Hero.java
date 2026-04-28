package com.narxoz.rpg.combatant;

import com.narxoz.rpg.state.HeroState;

public class Hero {

    private final String name;
    private int hp;
    private final int maxHp;
    private final int attackPower;
    private final int defense;
    private HeroState state;

    public Hero(String name, int hp, int attackPower, int defense, HeroState initialState) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.attackPower = attackPower;
        this.defense = defense;
        this.state = initialState;
    }

    public String getName()        { return name; }
    public int getHp()             { return hp; }
    public int getMaxHp()          { return maxHp; }
    public int getAttackPower()    { return attackPower; }
    public int getDefense()        { return defense; }
    public boolean isAlive()       { return hp > 0; }

    public HeroState getState()    { return state; }

    public void setState(HeroState newState) {
        System.out.println(name + " state changed: " + state.getName() + " -> " + newState.getName());
        this.state = newState;
    }

    public boolean canAct() {
        return state.canAct();
    }

    public int getModifiedAttack() {
        return state.modifyOutgoingDamage(attackPower);
    }

    public void applyDamage(int rawDamage) {
        int modified = state.modifyIncomingDamage(rawDamage);
        takeDamage(modified);
    }

    public void onTurnStart() {
        state.onTurnStart(this);
    }

    public void onTurnEnd() {
        state.onTurnEnd(this);
    }

    public void takeDamage(int amount) {
        hp = Math.max(0, hp - amount);
    }

    public void heal(int amount) {
        hp = Math.min(maxHp, hp + amount);
    }
}
