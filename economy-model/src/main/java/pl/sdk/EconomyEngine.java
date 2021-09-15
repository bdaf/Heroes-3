package pl.sdk;

import pl.sdk.creatures.EconomyCreature;
import pl.sdk.creatures.RandomizeAmountOfCreaturesInShop;
import pl.sdk.hero.CopyHeroMaker;
import pl.sdk.hero.CreatureShop;
import pl.sdk.hero.EconomyHero;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class EconomyEngine {

    public static final String HERO_BOUGHT_CREATURE = "HERO_BOUGHT_CREATURE";
    public static final String ACTIVE_HERO_CHANGED = "ACTIVE_HERO_CHANGED";
    public static final String END_OF_ROUND = "END_OF_TURN";
    public static final String NEXT_ROUND_STARTED = "NEXT_ROUND_STARTED";
    public static final String HERO_SOLD_CREATURE = "HERO_SOLD_CREATURE";
    public static final int AMOUNT_OF_ROUNDS = 1;
    public static final int AMOUNT_OF_TURNS = 3;
    public static final int FACTOR_OF_GOLD_AMOUNT_PER_ROUND = 1000;
    private final CreatureShop creatureShop = new CreatureShop();
    private final EconomyHero leftHero;
    private final EconomyHero rightHero;
    private EconomyHero activeHero;
    private int turnNumber;
    private int roundNumber;
    private RandomizeAmountOfCreaturesInShop randomize;
    private PropertyChangeSupport observerSupport;

    public EconomyEngine(EconomyHero aLeftHero, EconomyHero aRightHero) {
        leftHero = aLeftHero;
        rightHero = aRightHero;
        activeHero = leftHero;
        turnNumber = 1;
        roundNumber = 1;
        randomize = new RandomizeAmountOfCreaturesInShop();
        observerSupport = new PropertyChangeSupport(this);
    }

    public void buy(EconomyCreature aCreature) {
        creatureShop.buy(activeHero, aCreature);
        observerSupport.firePropertyChange(HERO_BOUGHT_CREATURE, null, null);
    }

    public boolean sell(EconomyCreature aCreature) {
        boolean result = creatureShop.sell(activeHero, aCreature);
        if (result) observerSupport.firePropertyChange(HERO_SOLD_CREATURE, null, null);
        return result;
    }

    public void pass()   {
        if (activeHero == leftHero) {
            activeHero = rightHero;
            observerSupport.firePropertyChange(ACTIVE_HERO_CHANGED, leftHero, activeHero);
        } else {
            if(isLastChooseBeforeBattle() && isAnyTeamEmpty()) throw new IllegalStateException("None of army cannot be empty!");
            activeHero = leftHero;
            observerSupport.firePropertyChange(ACTIVE_HERO_CHANGED, rightHero, activeHero);
            endTurn();
        }
    }

    private void endTurn() {
        turnNumber += 1;
        if (turnNumber > AMOUNT_OF_ROUNDS) {
            nextRound();
        } else {
            randomize = new RandomizeAmountOfCreaturesInShop();
            leftHero.addGold(FACTOR_OF_GOLD_AMOUNT_PER_ROUND * turnNumber);
            rightHero.addGold(FACTOR_OF_GOLD_AMOUNT_PER_ROUND * turnNumber);
            observerSupport.firePropertyChange(NEXT_ROUND_STARTED, turnNumber - 1, turnNumber);
        }
    }

    private void nextRound() {
        turnNumber = 1;
        roundNumber += 1;
        observerSupport.firePropertyChange(END_OF_ROUND, roundNumber - 1, roundNumber);
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public EconomyHero getActiveHero() {
        return CopyHeroMaker.copyOf(activeHero);
    }

    public void addObserver(String aChangedProperty, PropertyChangeListener aObserver) {
        observerSupport.addPropertyChangeListener(aChangedProperty, aObserver);
    }

    public void removeObserver(String aPropertyName, PropertyChangeListener aObserver) {
        observerSupport.removePropertyChangeListener(aPropertyName, aObserver);
    }

    public EconomyHero getLeftHero() {
        return CopyHeroMaker.copyOf(leftHero);
    }

    public EconomyHero getRightHero() {
        return CopyHeroMaker.copyOf(rightHero);
    }

    public boolean isAnyTeamEmpty() {
        return getLeftHero().getHeroArmy().isEmpty() || getRightHero().getHeroArmy().isEmpty();
    }

    int getRoundNumber() {
        return roundNumber;
    }

    public boolean isThisTheLastBattle() {
        return getRoundNumber() > AMOUNT_OF_ROUNDS;
    }

    public RandomizeAmountOfCreaturesInShop getRandomizer() {
        return randomize;
    }

    public boolean isLastChooseBeforeBattle() {
        return getTurnNumber() == AMOUNT_OF_TURNS && activeHero == rightHero;

    }
}
