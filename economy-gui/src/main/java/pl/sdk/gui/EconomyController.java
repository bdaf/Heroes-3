package pl.sdk.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pl.sdk.creatures.CastleFactory;
import pl.sdk.creatures.Creature;
import pl.sdk.creatures.Factory;
import pl.sdk.creatures.NecropolisFactory;

import java.util.LinkedList;
import java.util.List;

public class EconomyController {
    @FXML
    Button passButton;
    @FXML
    Button escapeButton;
    @FXML
    VBox hBoxForUserArmy;
    @FXML
    VBox hBoxForArmyShop;
    private List<Creature> creaturesInShop;
    private List<Creature> creaturesInArmy;

    @FXML
    void initialize(){
        Factory factory = new CastleFactory();
        creaturesInShop = new LinkedList<Creature>();
        creaturesInArmy = new LinkedList<Creature>();
        for (int i = 0; i < 14; i++) {
            if(i == 7)
                factory = new NecropolisFactory();
            creaturesInShop.add(factory.Create(true,(i%7)+1));
        }
        refreshGui();
    }

    void buyOrSell(Creature aCreatureButton) {
        if(creaturesInShop.contains(aCreatureButton)){
            creaturesInArmy.add(aCreatureButton);
            creaturesInShop.remove(aCreatureButton);
        } else if(creaturesInArmy.contains(aCreatureButton)){
            creaturesInShop.add(aCreatureButton);
            creaturesInArmy.remove(aCreatureButton);
        }
        refreshGui();
    }

    void refreshGui() {
        hBoxForArmyShop.getChildren().clear();
        hBoxForUserArmy.getChildren().clear();
        hBoxForArmyShop.getChildren().add(new Button("SHOP"));
        hBoxForUserArmy.getChildren().add(new Button("YOUR ARMY"));
        creaturesInShop.forEach(x -> hBoxForArmyShop.getChildren().add(new CreatureButton(this,x)));
        creaturesInArmy.forEach(x -> hBoxForUserArmy.getChildren().add(new CreatureButton(this,x)));
    }
}
