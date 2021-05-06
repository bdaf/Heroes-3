package pl.sdk.gui;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import pl.sdk.creatures.Creature;


public class CreatureButton extends Button {
    public CreatureButton(EconomyController aController, Creature aCreature) {
        super(aCreature.getName());
        addEventHandler(MouseEvent.MOUSE_CLICKED, x -> {
            aController.buyOrSell(aCreature);
            aController.refreshGui();
        });
    }
}
