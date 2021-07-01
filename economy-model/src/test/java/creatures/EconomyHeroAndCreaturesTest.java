package creatures;

import org.junit.jupiter.api.Test;
import pl.sdk.creatures.EconomyCreature;
import pl.sdk.hero.EconomyHero;

import java.util.LinkedList;
import java.util.List;

public class EconomyHeroAndCreaturesTest {

    @Test
    void throwExceptionWhenAttemptingToBuySeventhCreature(){
        EconomyCastleFactory factory = new EconomyCastleFactory();
        List<EconomyCreature> army = new LinkedList<EconomyCreature>();
        EconomyHero hero = new EconomyHero(EconomyHero.Fraction.CASTLE,army,1000 );
        hero.addCreature();
    }

    @Test
    void throwExceptionWhenAttemptingToSubtractGoldToMinusValue(){

    }
}
