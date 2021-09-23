package pl.sdk.creatures;

import com.google.common.collect.Range;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.sdk.GameEngine;
import pl.sdk.Point;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static pl.sdk.GameEngine.BOARD_WIDTH;

public class WeaknessesTest {

    private Random randomizer;
    private Creature attacker;
    private Creature defender;
    private GameEngine engine;

    @BeforeEach
    void init() {
        randomizer = new Random();
        randomizer = mock(Random.class);
        when(randomizer.nextDouble()).thenReturn(0.1);
        when(randomizer.nextInt(anyInt())).thenReturn(0);

        attacker = new InfectsWithWeaknessCreatureDecorator(new BlockingCounterAttackCreatureDecorator(new Creature.BuilderForTesting()
                .maxHp(100)
                .attack(3)
                .armor(3)
                .moveRange(100)
                .build()), new Weakness(2,2,0.2,3), randomizer);
        defender = new Creature.BuilderForTesting()
                .damage(Range.closed(10, 12))
                .damageCalculator(new DefaultDamageCalculator(randomizer))
                .moveRange(9)
                .attack(5)
                .armor(5)
                .maxHp(100).build();
        engine = new GameEngine(List.of(attacker), List.of(defender));
    }

    @Test
    void defenderShouldHave2PointsLessAttackAndDefenseFor3Rounds() {
        engine.move(new Point(BOARD_WIDTH - 2, 0));
        assertTrue(engine.canAttack(BOARD_WIDTH - 1, 0));
        engine.attack(BOARD_WIDTH - 1, 0); // attacker

        assertEquals(3, defender.getAttack());
        assertEquals(3, defender.getArmor());

        engine.pass(); // defender // 1 round is over, 2th starts

        engine.pass(); // attacker
        engine.pass(); // defender // 2 round is over, 3th starts

        assertEquals(3, defender.getAttack());
        assertEquals(3, defender.getArmor());

        engine.pass(); // attacker
        engine.pass(); // defender // 3 round is over, 4th starts

        assertEquals(5, defender.getAttack());
        assertEquals(5, defender.getArmor());

    }

    @Test
    void defenderShouldHave2PointsLessAttackAndArmorFor3RoundsAndThenAttacksLess() {
        engine.move(new Point(BOARD_WIDTH - 2, 0));
        assertTrue(engine.canAttack(BOARD_WIDTH - 1, 0));
        engine.attack(BOARD_WIDTH - 1, 0); // attacker

        assertEquals(3, defender.getAttack());
        assertEquals(3, defender.getArmor());

        engine.pass(); // defender // 1 round is over, 2th starts

        engine.pass(); // attacker
        engine.pass(); // defender // 2 round is over, 3th starts

        assertEquals(3, defender.getAttack());
        assertEquals(3, defender.getArmor());

        engine.pass(); // attacker
        engine.attack(BOARD_WIDTH - 2, 0); // defender // 3 round is over, 4th starts

        assertEquals(90, attacker.getCurrentHp());
        assertEquals(3, defender.getAttack());
        assertEquals(3, defender.getArmor());
        assertEquals(2, defender.getWeaknesses().get(0).getDuration());

        // w8ing for weakness to get over
        engine.pass(); // attacker
        engine.pass(); // defender // 4 round is over, 5th starts

        engine.pass(); // attacker
        engine.pass(); // defender // 5 round is over, 6th starts

        assertEquals(0, defender.getWeaknesses().size());

        engine.pass(); // attacker
        engine.attack(BOARD_WIDTH - 2, 0); // defender

        assertEquals(79, attacker.getCurrentHp());

    }

    @Test
    void defenderShouldWeaknessFor3RoundsButWhenHeGetsWeaknessItShouldReset() {
        engine.move(new Point(BOARD_WIDTH - 2, 0));
        assertTrue(engine.canAttack(BOARD_WIDTH - 1, 0));
        engine.attack(BOARD_WIDTH - 1, 0); // attacker

        assertEquals(3, defender.getAttack());
        assertEquals(3, defender.getArmor());

        engine.pass(); // defender // 1 round is over, 2th starts

        engine.pass(); // attacker
        engine.pass(); // defender // 2 round is over, 3th starts

        assertEquals(1, defender.getWeaknesses().get(0).getDuration());
        assertEquals(3, defender.getArmor());

        engine.attack(BOARD_WIDTH - 1, 0); // attacker
        assertEquals(3, defender.getWeaknesses().get(0).getDuration());
        engine.pass(); // defender // 3 round is over, 4th starts

        assertEquals(3, defender.getAttack());
        assertEquals(3, defender.getArmor());

        engine.pass(); // attacker
        engine.pass(); // defender // 2 round is over, 3th starts

        assertEquals(3, defender.getAttack());
        assertEquals(3, defender.getArmor());

        engine.pass(); // attacker
        engine.pass(); // defender

        assertEquals(5, defender.getAttack());
        assertEquals(5, defender.getArmor());

    }

    @Test
    void defenderShouldWeaknessFor3RoundsAndAfterThatWhenGetsWeaknessShouldGetItAgain() {
        engine.move(new Point(BOARD_WIDTH - 2, 0));
        assertTrue(engine.canAttack(BOARD_WIDTH - 1, 0));
        engine.attack(BOARD_WIDTH - 1, 0); // attacker

        assertEquals(3, defender.getAttack());
        assertEquals(3, defender.getArmor());

        engine.pass(); // defender // 1 round is over, 2th starts

        engine.pass(); // attacker
        engine.pass(); // defender // 2 round is over, 3th starts

        assertEquals(1, defender.getWeaknesses().get(0).getDuration());
        assertEquals(3, defender.getArmor());

        engine.pass();
        engine.pass(); // defender // 3 round is over, 4th starts

        assertEquals(0,defender.getWeaknesses().size());
        assertEquals(5, defender.getAttack());
        assertEquals(5, defender.getArmor());

        engine.attack(BOARD_WIDTH - 1, 0); // attacker
        assertEquals(1,defender.getWeaknesses().size());
        assertEquals(3, defender.getWeaknesses().get(0).getDuration());
        engine.pass(); // defender // 2 round is over, 3th starts

        assertEquals(3, defender.getAttack());
        assertEquals(3, defender.getArmor());
        assertEquals(2, defender.getWeaknesses().get(0).getDuration());

        engine.pass(); // attacker
        engine.pass(); // defender

        assertEquals(1, defender.getWeaknesses().get(0).getDuration());

        engine.pass(); // attacker
        engine.pass(); // defender

        assertEquals(0, defender.getWeaknesses().size());
        assertEquals(5, defender.getAttack());
        assertEquals(5, defender.getArmor());

    }

    @Test
    void defenderShouldGetWeaknessFor3RoundsBecauseOfAttackersCounterAttack() {
        engine.move(new Point(BOARD_WIDTH - 2, 0));
        assertTrue(engine.canAttack(BOARD_WIDTH - 1, 0));
        engine.pass();

        assertEquals(5, defender.getAttack());
        assertEquals(5, defender.getArmor());
        assertEquals(0, defender.getWeaknesses().size());

        engine.attack(BOARD_WIDTH-2,0); // defender // 1 round is over, 2th starts

        assertEquals(3, defender.getAttack());
        assertEquals(3, defender.getArmor());
        assertEquals(1, defender.getWeaknesses().size());
        assertEquals(2, defender.getWeaknesses().get(0).getDuration());

        engine.pass(); // attacker
        engine.pass(); // defender // 2 round is over, 3th starts

        assertEquals(1, defender.getWeaknesses().get(0).getDuration());
        assertEquals(3, defender.getArmor());

        engine.pass();
        engine.pass(); // defender // 3 round is over, 4th starts

        assertEquals(0,defender.getWeaknesses().size());
        assertEquals(5, defender.getAttack());
        assertEquals(5, defender.getArmor());


    }

}
