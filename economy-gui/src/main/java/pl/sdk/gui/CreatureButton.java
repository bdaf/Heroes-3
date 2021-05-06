package pl.sdk.gui;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import pl.sdk.creatures.Creature;


public class CreatureButton extends Button {

    private Creature creature;

    public CreatureButton(EconomyController aController, Creature aCreature, boolean ifIsInPlayersArmy){
        super(aCreature.getName());
        creature = aCreature;
        if(ifIsInPlayersArmy){
            setText(getName()+"("+getAmount()+")");
            addEventHandler(MouseEvent.MOUSE_CLICKED, x -> {
                aController.sell(aCreature);
                aController.refreshGui();
            });
        } else {
            addEventHandler(MouseEvent.MOUSE_CLICKED, x -> {
                aController.displayChoosingAmountOfCreatures(this);
                aController.buy(aCreature);
            });
        }
    }

    void setAmount(int aAmount){
        creature.setAmount(aAmount);
    }

    String getName() {
        return creature.getName();
    }

    int getAmount() {
        return creature.getAmount();
    }
}
