package pl.sdk.creatures;

import com.google.common.collect.Range;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HealSelfWithCounterAttackCalculatorTest {
    public static final Integer THE_SAME_FOR_BOTH_CREATURES = 1;
    private Creature attacker;
    private Creature defender;
    Random rand;

    @BeforeEach
    void init(){
        rand = mock(Random.class);
        when(rand.nextInt(anyInt())).thenReturn(0);
        attacker = new Creature.BuilderForTesting()
                .attack(THE_SAME_FOR_BOTH_CREATURES)
                .armor(THE_SAME_FOR_BOTH_CREATURES)
                .maxHp(100)
                .damage(Range.closed(10,12))
                .damageCalculator(new DefaultDamageCalculator(rand))
                .amount(10)
                .build();
        attacker = new SelfHealingCreatureDecoratorW(attacker,-0.5);
        defender = new Creature.BuilderForTesting()
                .attack(THE_SAME_FOR_BOTH_CREATURES)
                .armor(THE_SAME_FOR_BOTH_CREATURES)
                .maxHp(Integer.MAX_VALUE)
                .damage(Range.closed(10,12))
                .damageCalculator(new DefaultDamageCalculator(rand))
                .amount(1)
                .build();
    }

    @Test
    void attackerWhichIsMonkShouldLost10HpAndAdditionally50HpFromHisAttack(){
        attacker.meleeAttack(defender);

        assertEquals(40,attacker.getCurrentHp());
    }

    @Test
    void attackerWhichIsMonkShouldLost10HpAndAdditionally50HpFromHisCounterAttack(){
        defender.meleeAttack(attacker);

        assertEquals(40,attacker.getCurrentHp());
    }
}
