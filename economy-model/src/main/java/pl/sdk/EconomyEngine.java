package pl.sdk;

import pl.sdk.creatures.EconomyCreature;
import pl.sdk.creatures.RandomizeAmountOfCreatures;
import pl.sdk.hero.CopyHeroMaker;
import pl.sdk.hero.CreatureShop;
import pl.sdk.hero.EconomyHero;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class EconomyEngine {

    public static final String HERO_BOUGHT_CREATURE = "HERO_BOUGHT_CREATURE";
    public static final String ACTIVE_HERO_CHANGED = "ACTIVE_HERO_CHANGED";
    public static final String END_OF_TURN = "NEXT_TOUR_STARTED";
    public static final String NEXT_ROUND_STARTED = "NEXT_ROUND_STARTED";
    public static final String HERO_SOLD_CREATURE = "HERO_SOLD_CREATURE";
    public static final int AMOUNT_OF_ROUNDS = 3;
    public static final int AMOUNT_OF_TOURS = 5;
    public static final int FACTOR_OF_GOLD_AMOUNT_PER_ROUND = 1000;
    private final CreatureShop creatureShop = new CreatureShop();
    private final EconomyHero leftHero;
    private final EconomyHero rightHero;
    private EconomyHero activeHero;
    private int roundNumber;
    private int tourNumber;
    private RandomizeAmountOfCreatures randomize;
    private PropertyChangeSupport observerSupport;

    public EconomyEngine(EconomyHero aLeftHero, EconomyHero aRightHero) {
        leftHero = aLeftHero;
        rightHero = aRightHero;
        activeHero = leftHero;
        roundNumber = 1;
        tourNumber = 1;
        randomize = new RandomizeAmountOfCreatures();
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
        roundNumber += 1;
        if (roundNumber > AMOUNT_OF_ROUNDS) {
            nextTour();
        } else {
            randomize = new RandomizeAmountOfCreatures();
            leftHero.addGold(FACTOR_OF_GOLD_AMOUNT_PER_ROUND * roundNumber);
            rightHero.addGold(FACTOR_OF_GOLD_AMOUNT_PER_ROUND * roundNumber);
            observerSupport.firePropertyChange(NEXT_ROUND_STARTED, roundNumber - 1, roundNumber);
        }
    }

    private void nextTour() {
        roundNumber = 1;
        tourNumber += 1;
        //rightHero.
        observerSupport.firePropertyChange(END_OF_TURN, tourNumber - 1, tourNumber);
    }

    public int getRoundNumber() {
        return roundNumber;
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

    int getTourNumber() {
        return tourNumber;
    }

    public boolean isThisTheLastBattle() {
        return getTourNumber() > AMOUNT_OF_TOURS;
    }

    public RandomizeAmountOfCreatures getRandomizer() {
        return randomize;
    }

    public boolean isLastChooseBeforeBattle() {
        return getRoundNumber() == 3 && activeHero == rightHero;
    }
}
