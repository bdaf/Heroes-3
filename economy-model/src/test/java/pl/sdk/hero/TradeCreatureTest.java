package pl.sdk.hero;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.sdk.EconomyEngine;
import pl.sdk.creatures.EconomyCastleFactory;
import pl.sdk.settings.KindOfGame;

import static org.junit.jupiter.api.Assertions.*;
import static pl.sdk.hero.Fraction.CASTLE;

public class TradeCreatureTest {

    private EconomyEngine engine;
    private EconomyCastleFactory factory;
    private EconomyHero leftHero;
    private EconomyHero rightHero;

    @BeforeEach
    void init() {
        KindOfGame kind = KindOfGame.TOURNAMENT;
        factory = new EconomyCastleFactory();
        leftHero = new EconomyHero(CASTLE,10000);
        rightHero = new EconomyHero(CASTLE,10000);
        engine = new EconomyEngine(leftHero,rightHero, kind);
    }

    @Test
    void heroShouldBuyOneUnitInOneStackFor60GoldAndSellIt() {
        engine.buy(factory.Create(false,1,1));
        assertEquals(9940,leftHero.getGold());

        engine.sell(factory.Create(false,1,1));
        assertEquals(10000,leftHero.getGold());
        assertEquals(0,engine.getActiveHero().getHeroArmy().size());
    }

    @Test
    void heroShouldBuyTwoUnitsInOneStackFor120GoldAndSellOneOfIt() {
        engine.buy(factory.Create(false,1,2));
        assertEquals(9880,leftHero.getGold());

        engine.sell(factory.Create(false,1,1));
        assertEquals(9940,leftHero.getGold());
        assertEquals(1,engine.getActiveHero().getHeroArmy().size());
    }

    @Test
    void heroShouldBuyFewUnitsInTwoStacksFor600GoldAndSellOneWholeStackAndOneUnitFromSecond() {
        engine.buy(factory.Create(false,3,2));
        engine.buy(factory.Create(false,2,2));
        assertEquals(9400,leftHero.getGold());
        engine.sell(factory.Create(false,3,2));
        assertEquals(9800,leftHero.getGold());
        assertEquals(1,leftHero.getHeroArmy().size());
        engine.sell(factory.Create(false,2,1));
        assertEquals(9900,leftHero.getGold());
        assertEquals(1,leftHero.getHeroArmy().size());
        assertEquals(factory.Create(false,2,1),leftHero.getHeroArmy().get(0));
    }
    @Test
    void shouldThrowExceptionBecause7FieldsForStacksAreBusyAndEightsStackCannotBeBought() {
        for (int i = 0; i < 7; i++) {
            engine.buy(factory.Create(false,i+1,1));
        }

        assertThrows(IllegalStateException.class, () -> engine.buy(factory.Create(true,1,1)));
        assertEquals(4940,leftHero.getGold());
        assertEquals(7,leftHero.getHeroArmy().size());
    }
    @Test
    void shouldThrowExceptionBecauseOfMinusValueOfHeroGold() {
        assertThrows(IllegalStateException.class, () ->engine.buy(factory.Create(false,7,4)));
        assertEquals(10000, leftHero.getGold());
        assertEquals(0, leftHero.getHeroArmy().size());
    }
    @Test
    void shouldThrowExceptionBecauseOfSellingCreaturesWhichAreNotInArmy() {
        assertThrows(IllegalStateException.class, () ->engine.sell(factory.Create(false,2,5)));
    }

    @Test
    void heroShouldSellArchersFromHisArmy(){
        assertThrows(IllegalStateException.class, () -> engine.sell(factory.Create(false,1,1)));

        engine.buy(factory.Create(false,1,2));
        engine.buy(factory.Create(false,2,2));
        engine.buy(factory.Create(false,3,2));

        engine.buy(factory.Create(false,4,2));
        engine.buy(factory.Create(false,5,2));
        engine.buy(factory.Create(false,6,2));

        assertEquals(5880,engine.getActiveHero().getGold());

        assertTrue(engine.sell(factory.Create(false,2,2)));
        assertThrows(IllegalStateException.class, () -> engine.sell(factory.Create(false,2,2)));

        assertEquals(6080,engine.getActiveHero().getGold());
        assertEquals(5,engine.getActiveHero().getHeroArmy().size());
    }
    @Test
    void shouldThrowExceptionBecauseOfMinusValueOfAmountOfCreaturesInStack() {
        engine.buy(factory.Create(false,2,4));
        assertEquals(9600, leftHero.getGold());
        assertEquals(1, leftHero.getHeroArmy().size());
        assertThrows(IllegalArgumentException.class, () ->engine.sell(factory.Create(false,2,5)));
    }
    @Test
    void whenBuyingTheSameCreatureRepeatedlyShouldIncreaseNumberOfCreaturesInStackAndNotAddNewStack(){
        engine.buy(factory.Create(false,1,1));
        engine.buy(factory.Create(false,1,2));

        assertEquals(factory.Create(false,1,3), engine.getActiveHero().getHeroArmy().get(0));
        assertThrows(IndexOutOfBoundsException.class, () -> engine.getActiveHero().getHeroArmy().get(1));
    }

    @Test
    void whenBuyingTheSameCreatureRepeatedlyShouldIncreaseNumberOfCreaturesInStackAndNotAddNewStackInOrderFromPrevious(){
        engine.buy(factory.Create(false,1,1));
        engine.buy(factory.Create(false,2,1));
        engine.buy(factory.Create(false,1,2));

        assertEquals(factory.Create(false,1,3), engine.getActiveHero().getHeroArmy().get(0));
        assertEquals(factory.Create(false,2,1), engine.getActiveHero().getHeroArmy().get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> engine.getActiveHero().getHeroArmy().get(2));
    }

}
