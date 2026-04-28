# Homework 8 Implementation Summary

## Overview

This assignment implements two behavioral design patterns in a tower-climbing RPG: **State Pattern** and **Template Method Pattern**.

---

## Part 1: State Pattern ✓

### Implementation

Located in: `src/com/narxoz/rpg/state/`

**5 HeroState Implementations:**

1. **NormalState**
   - Neutral baseline state
   - No damage modifications
   - Hero can act freely

2. **PoisonedState** (Self-transitioning)
   - Reduces outgoing damage by 30% (debuff)
   - Applies 3 poison damage per turn (passive effect)
   - Self-transitions to NormalState after 3 turns
   - Demonstrates: State lifecycle management, passive effects

3. **StunnedState** (Critical: canAct = false)
   - Returns `false` from `canAct()` — prevents hero action
   - Reduces incoming damage by 25%
   - Self-transitions after 1 turn
   - Demonstrates: State-based control flow changes

4. **BerserkState** (Buff/Debuff)
   - Increases outgoing damage by 50% (buff)
   - Increases incoming damage by 20% (debuff)
   - Self-transitions after 2 turns
   - Demonstrates: Combined beneficial and harmful modifiers

5. **RegenerationState** (Buff)
   - Heals 5 HP per turn (passive effect)
   - No damage modifiers
   - Self-transitions after 4 turns
   - Demonstrates: Positive state with healing effects

### Key Feature: Runtime State Transitions

- States manage their own lifecycle via `onTurnEnd()`
- Heroes transition between states **at runtime**, not set-once
- Example from output:
  ```
  [Aragorn takes 3 poison damage]  (turn start effect)
  [Aragorn's poison wears off!]    (self-transition)
  ```

---

## Part 2: Template Method Pattern ✓

### Implementation

Located in: `src/com/narxoz/rpg/floor/`

**4 TowerFloor Subclasses:**

1. **CombatFloor**
   - Standard combat against monsters
   - Uses template skeleton: announce → setup → resolveChallenge → awardLoot → cleanup
   - Non-trivial combat loop with state interactions

2. **PoisonTrapFloor**
   - Environmental hazard with state transitions
   - Applies poison/stun states to heroes during challenge
   - Demonstrates state transitions driven by floor events

3. **RestFloor** (Hook Override)
   - **Overrides `shouldAwardLoot()`** to return `false`
   - Prevents loot award phase
   - **Overrides `cleanup()`** for custom healing behavior
   - Demonstrates hook mechanism

4. **BossFloor** (Hook Override + Drama)
   - **Overrides `announce()`** for dramatic boss introduction
   - Features powerful enemy with high damage
   - Heroes may transition to Berserk/Regeneration states
   - Demonstrates hook customization for UX

### Critical Features

- **`explore()` is final** — Never overridden by subclasses
- **Fixed skeleton** — All floors follow announce → setup → resolveChallenge → awardLoot → cleanup
- **Abstract methods** — Each subclass implements: setup(), resolveChallenge(), awardLoot(), getFloorName()
- **Optional hooks** — Subclasses may override: announce(), shouldAwardLoot(), cleanup()
- **Visible in output** — Each step is logged showing the template method at work

---

## Part 3: Tower Runner & Demo ✓

### TowerRunner (Tower Management)

Located in: `src/com/narxoz/rpg/tower/TowerRunner.java`

- Manages floor sequence execution
- Tracks cleared floors
- Removes dead heroes after each floor
- Returns `TowerRunResult` with completion stats

### Main.java Demo

**Heroes Created:**

- Aragorn (Normal state) - 30 HP, 10 ATK, 5 DEF
- Legolas (Regenerating state) - 25 HP, 12 ATK, 3 DEF

**Tower Structure:**

- Floor 1: CombatFloor (Goblins & Orc)
- Floor 2: PoisonTrapFloor (Gas + state transitions)
- Floor 3: RestFloor (Full heal, no loot)
- Floor 4: CombatFloor (Another combat)
- Floor 5: BossFloor (Ancient Dragon, dramatic intro)

**Visible State Transitions in Output:**

1. Floor 2: Aragorn transitions Normal → Poisoned, Legolas transitions Regenerating → Stunned
2. Floor 2 cleanup: Legolas recovers from stun
3. Floor 4: Aragorn's poison wears off mid-combat
4. Floor 5: Both heroes enter Berserk state for boss fight

---

## Demonstration Results

```
Floors Cleared: 5 / 5
Heroes Surviving: 1 / 2
Reached Top: YES - TOWER CONQUERED! 🏆

Surviving Heroes:
  ✓ Legolas: 25/25 HP, State: Normal
```

### Output Shows:

✓ Hero state transitions visible (Poisoned → Normal, Stun recovery, Berserk entry)
✓ Template Method skeleton (each step logged in sequence)
✓ Hook overrides working (RestFloor's custom healing, BossFloor's dramatic intro)
✓ State pattern affecting control flow (Stunned hero cannot act)
✓ State damage modifiers working (Poison reduced attack, Berserk boosted then debuffed)

---

## File Structure

```
src/com/narxoz/rpg/
├── Main.java                          # Demo entry point
├── state/
│   ├── HeroState.java                # Provided interface
│   ├── NormalState.java              # Neutral state
│   ├── PoisonedState.java            # Self-transitioning debuff
│   ├── StunnedState.java             # Prevents action (canAct=false)
│   ├── BerserkState.java             # Buff/debuff with damage modifiers
│   └── RegenerationState.java        # Healing buff
├── floor/
│   ├── TowerFloor.java               # Provided abstract class
│   ├── FloorResult.java              # Provided result class
│   ├── CombatFloor.java              # Combat implementation
│   ├── PoisonTrapFloor.java          # Trap with state transitions
│   ├── RestFloor.java                # Hook overrides (shouldAwardLoot, cleanup)
│   └── BossFloor.java                # Hook override (announce)
├── combatant/
│   ├── Hero.java                     # Updated with state management
│   └── Monster.java                  # Provided
└── tower/
    ├── TowerRunResult.java           # Provided result class
    └── TowerRunner.java              # Tower climb manager
```

---

## Compilation & Execution

**Compile:**

```bash
cd homework-rpg-8
mkdir -p out
find src -name "*.java" | xargs javac -d out
```

**Run:**

```bash
java -cp out com.narxoz.rpg.Main
```

---

## Grading Checklist

| Requirement                    | Status | Evidence                                |
| ------------------------------ | ------ | --------------------------------------- |
| **State:** 3+ implementations  | ✓      | 5 states created                        |
| **State:** Self-transitioning  | ✓      | PoisonedState transitions after 3 turns |
| **State:** canAct() = false    | ✓      | StunnedState prevents action            |
| **State:** Runtime transitions | ✓      | Visible in Floor 2 & 4 output           |
| **Template:** ≥3 subclasses    | ✓      | 4 subclasses created                    |
| **Template:** explore() final  | ✓      | Not overridden anywhere                 |
| **Template:** Hook override    | ✓      | RestFloor & BossFloor override hooks    |
| **Template:** Skeleton visible | ✓      | Output shows all template steps         |
| **Demo:** 2+ heroes            | ✓      | Aragorn & Legolas                       |
| **Demo:** ≥4 floors            | ✓      | 5 floors total                          |
| **Demo:** State transitions    | ✓      | Multiple visible transitions            |
| **Demo:** TowerRunResult       | ✓      | Printed with summary stats              |
