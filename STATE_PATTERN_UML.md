classDiagram
%% State Pattern Diagram - Part 1

    class HeroState {
        <<interface>>
        +getName() String
        +modifyOutgoingDamage(basePower: int) int
        +modifyIncomingDamage(rawDamage: int) int
        +onTurnStart(hero: Hero) void
        +onTurnEnd(hero: Hero) void
        +canAct() boolean
    }

    class NormalState {
        +getName() String
        +modifyOutgoingDamage(basePower: int) int
        +modifyIncomingDamage(rawDamage: int) int
        +onTurnStart(hero: Hero) void
        +onTurnEnd(hero: Hero) void
        +canAct() boolean
    }

    class PoisonedState {
        -turnsRemaining: int
        -POISON_DURATION: int
        -POISON_DAMAGE: int
        +getName() String
        +modifyOutgoingDamage(basePower: int) int
        +modifyIncomingDamage(rawDamage: int) int
        +onTurnStart(hero: Hero) void
        +onTurnEnd(hero: Hero) void
        +canAct() boolean
    }

    class StunnedState {
        -turnsRemaining: int
        -STUN_DURATION: int
        +getName() String
        +modifyOutgoingDamage(basePower: int) int
        +modifyIncomingDamage(rawDamage: int) int
        +onTurnStart(hero: Hero) void
        +onTurnEnd(hero: Hero) void
        +canAct() boolean
    }

    class BerserkState {
        -turnsRemaining: int
        -BERSERK_DURATION: int
        +getName() String
        +modifyOutgoingDamage(basePower: int) int
        +modifyIncomingDamage(rawDamage: int) int
        +onTurnStart(hero: Hero) void
        +onTurnEnd(hero: Hero) void
        +canAct() boolean
    }

    class RegenerationState {
        -turnsRemaining: int
        -REGEN_DURATION: int
        -HEALING_PER_TURN: int
        +getName() String
        +modifyOutgoingDamage(basePower: int) int
        +modifyIncomingDamage(rawDamage: int) int
        +onTurnStart(hero: Hero) void
        +onTurnEnd(hero: Hero) void
        +canAct() boolean
    }

    class Hero {
        -name: String
        -hp: int
        -maxHp: int
        -attackPower: int
        -defense: int
        -state: HeroState
        +Hero(name: String, hp: int, attackPower: int, defense: int)
        +getName() String
        +getHp() int
        +getMaxHp() int
        +getAttackPower() int
        +getDefense() int
        +getState() HeroState
        +setState(newState: HeroState) void
        +isAlive() boolean
        +takeDamage(amount: int) void
        +heal(amount: int) void
        +calculateOutgoingDamage() int
        +calculateIncomingDamage(rawDamage: int) int
        +onTurnStart() void
        +onTurnEnd() void
        +canAct() boolean
    }

    %% Relationships
    HeroState <|.. NormalState
    HeroState <|.. PoisonedState
    HeroState <|.. StunnedState
    HeroState <|.. BerserkState
    HeroState <|.. RegenerationState

    Hero --> HeroState
