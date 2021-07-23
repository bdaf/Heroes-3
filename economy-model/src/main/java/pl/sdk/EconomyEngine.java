package pl.sdk;

import pl.sdk.creatures.Creature;
import pl.sdk.creatures.EconomyCreature;
import pl.sdk.hero.CreatureShop;
import pl.sdk.hero.EconomyHero;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class EconomyEngine {
    public static final String HERO_BOUGHT_CREATURE = "HERO_BOUGHT_CREATURE";
    public static final String ACTIVE_HERO_CHANGED = "ACTIVE_HERO_CHANGED";
    public static final String NEXT_ROUND_STARTED = "NEXT_ROUND_STARTED";
    private final CreatureShop creatureShop = new CreatureShop();
    private final EconomyHero leftHero;
    private final EconomyHero rightHero;
    private EconomyHero activeHero;
    private int roundNumber;
    private PropertyChangeSupport observerSupport;

    public EconomyEngine(EconomyHero aLeftHero, EconomyHero aRightHero) {
        leftHero = aLeftHero;
        rightHero = aRightHero;
        activeHero = leftHero;
        roundNumber = 1;
        observerSupport = new PropertyChangeSupport(this);
    }

    public void buy(EconomyCreature aCreate) {
        creatureShop.buy(activeHero,aCreate);
        observerSupport.firePropertyChange(HERO_BOUGHT_CREATURE,null, null);
    }

    public void pass() {
        if(activeHero == leftHero){
            activeHero = rightHero;
            observerSupport.firePropertyChange(ACTIVE_HERO_CHANGED,leftHero, activeHero);
        } else {
            activeHero = leftHero;
            observerSupport.firePropertyChange(ACTIVE_HERO_CHANGED,rightHero, activeHero);
            endTurn();
        }
    }

    private void endTurn() {
        roundNumber+=1;
        observerSupport.firePropertyChange(NEXT_ROUND_STARTED,roundNumber-1, roundNumber);
        leftHero.addGold(2000*roundNumber);
        rightHero.addGold(2000*roundNumber);
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public EconomyHero getActiveHero() {
        return activeHero;
    }

    public void addObserver(String aChangedProperty, PropertyChangeListener aObserver) {
        observerSupport.addPropertyChangeListener(aChangedProperty,aObserver);
    }
    public void removeObserver(String aPropertyName, PropertyChangeListener aObserver){
        observerSupport.removePropertyChangeListener(aPropertyName,aObserver);
    }
}
