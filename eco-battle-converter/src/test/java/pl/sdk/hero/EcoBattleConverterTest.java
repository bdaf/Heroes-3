package pl.sdk.hero;

import org.junit.jupiter.api.Test;
import pl.sdk.converter.EcoBattleConverter;
import pl.sdk.creatures.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EcoBattleConverterTest {

    @Test
    void shouldConvertProperly() {
        EconomyHero ecoHero = new EconomyHero(EconomyHero.Fraction.CASTLE, 1000);
        EconomyFactory ecoFactory = new EconomyCastleFactory();
        Factory factory = new CastleFactory();

        ecoHero.addCreature(ecoFactory.Create(false, 1, 1));
        ecoHero.addCreature(ecoFactory.Create(false, 2, 2));
        ecoHero.addCreature(ecoFactory.Create(false, 3, 3));
        ecoHero.addCreature(ecoFactory.Create(false, 4, 4));
        ecoHero.addCreature(ecoFactory.Create(false, 5, 5));
        ecoHero.addCreature(ecoFactory.Create(false, 6, 6));
        ecoHero.addCreature(ecoFactory.Create(false, 7, 7));

        List<Creature> convertedCreatures = EcoBattleConverter.convert(ecoHero);

        assertEquals(7, convertedCreatures.size());
        assertEquals("Pickman", convertedCreatures.get(0).getName());
        assertEquals("Archer", convertedCreatures.get(1).getName());
        assertEquals("Griffin", convertedCreatures.get(2).getName());
        assertEquals("Swordsman", convertedCreatures.get(3).getName());
        assertEquals("Monk", convertedCreatures.get(4).getName());
        assertEquals("Cavalier", convertedCreatures.get(5).getName());
        assertEquals("Angel", convertedCreatures.get(6).getName());

        for (int i = 0; i < 7; i++) {
            assertEquals(i+1, convertedCreatures.get(i).getAmount());
        }
    }
}
