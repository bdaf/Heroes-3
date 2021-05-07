package pl.sdk.gui;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import pl.sdk.creatures.Creature;

public class CreatureButtonInArmy extends Button {

    public CreatureButtonInArmy(EconomyController aController, Creature aCreature) {
        super(aCreature.getName());
        setText(aCreature.getName()+"("+aCreature.getAmount()+")");
        addEventHandler(MouseEvent.MOUSE_CLICKED, x -> {
            aController.sell(aCreature);
            aController.refreshGui();
        });
    }
}
