package pl.sdk.hero;

import org.junit.jupiter.api.Test;
import pl.sdk.creatures.EconomyCastleFactory;
import pl.sdk.creatures.EconomyFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CopyHeroMakerTest {

    @Test
    void shouldCopyHeroProperly(){
        EconomyHero hero = new EconomyHero(EconomyHero.Fraction.CASTLE,2021);
        EconomyFactory factory = new EconomyCastleFactory();
        hero.addCreature(factory.Create(true,1,1));
        hero.addCreature(factory.Create(true,2,2));
        hero.addCreature(factory.Create(true,3,3));
        hero.addCreature(factory.Create(true,4,4));
        hero.addCreature(factory.Create(true,5,5));
        hero.addCreature(factory.Create(true,6,6));

        EconomyHero copyOfHero = CopyHeroMaker.copyOf(hero);

        assertEquals(6,copyOfHero.getHeroArmy().size());

        assertTrue(copyOfHero.getHeroArmy().get(0).isUpgraded());
        assertTrue(copyOfHero.getHeroArmy().get(1).isUpgraded());
        assertTrue(copyOfHero.getHeroArmy().get(2).isUpgraded());
        assertTrue(copyOfHero.getHeroArmy().get(3).isUpgraded());
        assertTrue(copyOfHero.getHeroArmy().get(4).isUpgraded());
        assertTrue(copyOfHero.getHeroArmy().get(5).isUpgraded());

        assertEquals("Halberdier",copyOfHero.getHeroArmy().get(0).getName());
        assertEquals("Marksman",copyOfHero.getHeroArmy().get(1).getName());
        assertEquals("RoyalGriffin",copyOfHero.getHeroArmy().get(2).getName());
        assertEquals("Crusader",copyOfHero.getHeroArmy().get(3).getName());
        assertEquals("Zealot",copyOfHero.getHeroArmy().get(4).getName());
        assertEquals("Champion",copyOfHero.getHeroArmy().get(5).getName());

        assertEquals(1,copyOfHero.getHeroArmy().get(0).getAmount());
        assertEquals(2,copyOfHero.getHeroArmy().get(1).getAmount());
        assertEquals(3,copyOfHero.getHeroArmy().get(2).getAmount());
        assertEquals(4,copyOfHero.getHeroArmy().get(3).getAmount());
        assertEquals(5,copyOfHero.getHeroArmy().get(4).getAmount());
        assertEquals(6,copyOfHero.getHeroArmy().get(5).getAmount());
    }
}
