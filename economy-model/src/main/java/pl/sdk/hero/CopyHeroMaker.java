package pl.sdk.hero;

public class CopyHeroMaker {
    public static EconomyHero copyOf(EconomyHero aHero){
        EconomyHero result = new EconomyHero(aHero.getFraction(),aHero.getGold());
        aHero.getHeroArmy().forEach( x -> result.addCreature(x));
        return result;
    }
}
