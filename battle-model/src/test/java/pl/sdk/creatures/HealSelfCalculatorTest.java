package pl.sdk.creatures;

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
        attacker = new Creature.BuilderForTesting()
                .attack(THE_SAME_FOR_BOTH_CREATURES)
                .armor(THE_SAME_FOR_BOTH_CREATURES)
                .maxHp(100)
                .damage(Range.closed(10,12))
                .damageCalculator(new DefaultDamageCalculator(rand))
                .amount(10)
                .build();
        attacker = new SelfHealingCreatureDecoratorW(new BlockingCounterAttackCreatureDecoratorW(attacker),0.5);
        defender = new Creature.BuilderForTesting()
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
        attacker.meleeAttack(defender);

        assertEquals(90,attacker.getCurrentHp());
    }

    @Test
    void shouldHeal160AndItShouldBeJustMaxHpAndAmountShouldNotChangeWhenHeDeal360Dmg(){
        attacker = new SelfHealingCreatureDecoratorW(new Creature.BuilderForTesting()
                .attack(THE_SAME_FOR_BOTH_CREATURES)
                .armor(THE_SAME_FOR_BOTH_CREATURES)
                .maxHp(100)
                .damage(Range.closed(32,33))
                .damageCalculator(new DefaultDamageCalculator(rand))
                .amount(10)
                .build(),0.5);

        attacker.setCurrentHP(40);
        attacker.meleeAttack(defender);

        assertEquals(100,attacker.getCurrentHp());
        assertEquals(10,attacker.getAmount());
    }

    @Test
    void shouldHeal30AndHaveMaxHpWhenHeDeal100DmgButHasCurrently70Hp(){
        attacker.setCurrentHP(70);
        attacker.meleeAttack(defender);

        assertEquals(100,attacker.getCurrentHp());
        assertEquals(10,attacker.getAmount());
    }

    @Test
    void shouldHeal40AndHaveMaxHpWhenHeDeal100DmgButHasCurrently60HpV2(){
        attacker.setCurrentHP(60);
        attacker.meleeAttack(defender);

        assertEquals(100,attacker.getCurrentHp());
        assertEquals(10,attacker.getAmount());
    }

    @Test
    void shouldRestoreWholeStack(){
        attacker.setCurrentHP(60);
        attacker.setAmount(9);
        attacker.meleeAttack(defender);

        assertEquals(5,attacker.getCurrentHp());
        assertEquals(10,attacker.getAmount());
    }

    @Test
    void shouldRestoreWholeStackButNoMore(){
        attacker = new Creature.BuilderForTesting()
                .attack(THE_SAME_FOR_BOTH_CREATURES)
                .armor(THE_SAME_FOR_BOTH_CREATURES)
                .maxHp(100)
                .damage(Range.closed(110,120))
                .damageCalculator(new DefaultDamageCalculator(rand))
                .amount(15)
                .build();
        attacker = new SelfHealingCreatureDecoratorW(new BlockingCounterAttackCreatureDecoratorW(attacker),0.5);
        attacker.setCurrentHP(60);
        attacker.setAmount(5);
        attacker.meleeAttack(defender);

        assertEquals(35,attacker.getCurrentHp());
        assertEquals(8,attacker.getAmount());
    }
}
