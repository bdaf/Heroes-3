package pl.sdk;

import com.google.common.collect.Range;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HealSelfCalculatorTest {
    public static final Integer THE_SAME_FOR_BOTH_CREATURES = 1;
    private static final Integer INF = 999;
    private Creature attacker;
    private Creature defender;
    Random rand;

    @BeforeEach
    void init(){
        rand = mock(Random.class);
        when(rand.nextInt(anyInt())).thenReturn(0);
        attacker = new CreatureWithSelfHealing.Builder()
                .selfHealingPercentage(0.5)
                .attack(THE_SAME_FOR_BOTH_CREATURES)
                .armor(THE_SAME_FOR_BOTH_CREATURES)
                .maxHp(100)
                .damage(Range.closed(10,12))
                .damageCalculator(new DefaultDamageCalculator(rand))
                .amount(10)
                .build();
        defender = new Creature.Builder()
                .attack(THE_SAME_FOR_BOTH_CREATURES)
                .armor(THE_SAME_FOR_BOTH_CREATURES)
                .maxHp(INF)
                .damage(Range.closed(0,0))
                .damageCalculator(new DefaultDamageCalculator(rand))
                .amount(1)
                .build();
    }
    @Test
    void shouldHeal50WhenHeDeal100Dmg(){
        attacker.setCurrentHP(40);
        attacker.attack(defender);

        assertEquals(90,attacker.getCurrentHp());
    }

    @Test
    void shouldHeal160AndItShouldBeJustMaxHpAndAmountShouldNotChangeWhenHeDeal360Dmg(){
        attacker = new CreatureWithSelfHealing.Builder()
                .selfHealingPercentage(0.5)
                .attack(THE_SAME_FOR_BOTH_CREATURES)
                .armor(THE_SAME_FOR_BOTH_CREATURES)
                .maxHp(100)
                .damage(Range.closed(32,33))
                .damageCalculator(new DefaultDamageCalculator(rand))
                .amount(10)
                .build();

        attacker.setCurrentHP(40);
        attacker.attack(defender);

        assertEquals(100,attacker.getCurrentHp());
        assertEquals(10,attacker.getAmount());
    }

    @Test
    void shouldHeal30AndHaveMaxHpWhenHeDeal100DmgButHasCurrently70Hp(){
        attacker.setCurrentHP(70);
        attacker.attack(defender);

        assertEquals(100,attacker.getCurrentHp());
    }
    @Test
    void shouldHeal50WhenHeDeal100DmgV2(){
        attacker.setCurrentHP(40);
        attacker.attack(defender);

        assertEquals(90,attacker.getCurrentHp());
    }

    @Test
    void shouldHeal30AndHaveMaxHpWhenHeDeal100DmgButHasCurrently70HpV2(){
        attacker.setCurrentHP(60);
        attacker.attack(defender);

        assertEquals(100,attacker.getCurrentHp());
    }
}
