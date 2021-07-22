package pl.sdk;

import pl.sdk.creatures.Creature;
import pl.sdk.creatures.EconomyCreature;
import pl.sdk.hero.CreatureShop;
import pl.sdk.hero.EconomyHero;

public class EconomyEngine {
    private final EconomyHero leftHero;
    private final EconomyHero rightHero;
    private final CreatureShop creatureShop = new CreatureShop();;

    public EconomyEngine(EconomyHero aLeftHero, EconomyHero aRightHero) {
        leftHero = aLeftHero;
        rightHero = aRightHero;
    }

    public void buy(EconomyCreature aCreate) {
        creatureShop.buy(leftHero,aCreate);
    }
}
