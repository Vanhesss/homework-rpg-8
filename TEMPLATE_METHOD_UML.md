classDiagram
%% Template Method Pattern Diagram - Part 2

    class TowerFloor {
        <<abstract>>
        #announce() void*
        #setup(party: List~Hero~) void*
        #resolveChallenge(party: List~Hero~) FloorResult*
        #shouldAwardLoot(result: FloorResult) boolean
        #awardLoot(party: List~Hero~, result: FloorResult) void*
        #cleanup(party: List~Hero~) void
        #getFloorName() String*
        +explore(party: List~Hero~) final FloorResult
    }

    class CombatFloor {
        -monsters: List~Monster~
        #getFloorName() String
        #setup(party: List~Hero~) void
        #resolveChallenge(party: List~Hero~) FloorResult
        #awardLoot(party: List~Hero~, result: FloorResult) void
    }

    class PoisonTrapFloor {
        -trapDamage: int
        #getFloorName() String
        #setup(party: List~Hero~) void
        #resolveChallenge(party: List~Hero~) FloorResult
        #awardLoot(party: List~Hero~, result: FloorResult) void
    }

    class RestFloor {
        #getFloorName() String
        #setup(party: List~Hero~) void
        #resolveChallenge(party: List~Hero~) FloorResult
        #shouldAwardLoot(result: FloorResult) boolean*
        #awardLoot(party: List~Hero~, result: FloorResult) void
        #cleanup(party: List~Hero~) void*
    }

    class BossFloor {
        -boss: Monster
        #getFloorName() String
        #announce() void*
        #setup(party: List~Hero~) void
        #resolveChallenge(party: List~Hero~) FloorResult
        #awardLoot(party: List~Hero~, result: FloorResult) void
    }

    class FloorResult {
        -cleared: boolean
        -damageTaken: int
        -summary: String
        +FloorResult(cleared: boolean, damageTaken: int, summary: String)
        +isCleared() boolean
        +getDamageTaken() int
        +getSummary() String
    }

    class Monster {
        -name: String
        -hp: int
        -attackPower: int
        +Monster(name: String, hp: int, attackPower: int)
        +getName() String
        +getHp() int
        +getAttackPower() int
        +isAlive() boolean
        +takeDamage(amount: int) void
        +attack(hero: Hero) void
    }

    %% Relationships
    TowerFloor <|-- CombatFloor
    TowerFloor <|-- PoisonTrapFloor
    TowerFloor <|-- RestFloor
    TowerFloor <|-- BossFloor

    TowerFloor ..> FloorResult
    CombatFloor --> Monster
    BossFloor --> Monster

    note for RestFloor "Overrides shouldAwardLoot() hook:<br/>returns false to prevent loot award"
    note for BossFloor "Overrides announce() hook:<br/>provides dramatic introduction"
