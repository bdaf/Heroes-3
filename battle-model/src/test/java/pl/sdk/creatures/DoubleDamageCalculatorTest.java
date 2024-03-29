package pl.sdk.creatures;

import com.google.common.collect.Range;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DoubleDamageCalculatorTest {
    public static final Integer THE_SAME_FOR_BOTH_CREATURES = 1;
    private Creature attacker;
    private Creature defender;
    Random rand;

    @BeforeEach
    void init(){
        attacker = new Creature.BuilderForTesting()
                .attack(THE_SAME_FOR_BOTH_CREATURES)
                .armor(THE_SAME_FOR_BOTH_CREATURES)
                .damage(Range.closed(10,12))
                .amount(10)
                .build();
        defender = new Creature.BuilderForTesting()
                .attack(THE_SAME_FOR_BOTH_CREATURES)
                .armor(THE_SAME_FOR_BOTH_CREATURES)
                .maxHp(1000)
                .build();
        rand = mock(Random.class);
        when(rand.nextInt(anyInt())).thenReturn(0);
    }
    @Test
    void shouldDealDoubleDmgWhenRandomIsPositive(){
        when(rand.nextDouble()).thenReturn(0.19);
        when(rand.nextInt(anyInt())).thenReturn(0);
        CalculateDamageStrategy calc = new DamageIncreaseInRandomChanceCalculator(new DefaultDamageCalculator(rand),0.2,2);

        int result = calc.calculateDamage(attacker, defender);

        assertEquals(200,result);
        when(rand.nextDouble()).thenReturn(0.2);
        assertEquals(200,result);
    }

    @Test
    void shouldDealNormalDmgWhenRandomIsNegative(){
        when(rand.nextDouble()).thenReturn(0.21);
        CalculateDamageStrategy calc = new DamageIncreaseInRandomChanceCalculator(new DefaultDamageCalculator(rand),0.2,2);

        int result = calc.calculateDamage(attacker, defender);

        assertEquals(100,result);
    }
}
