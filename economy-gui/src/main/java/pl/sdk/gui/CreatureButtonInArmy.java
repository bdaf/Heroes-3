package pl.sdk.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.sdk.creatures.Creature;
import pl.sdk.creatures.EconomyCreature;

public class CreatureButtonInArmy extends Button {

    private Stage windowForSellingCreature;

    private String nameOfCreature;
    private boolean ifSell;

    public CreatureButtonInArmy(EconomyController aController, EconomyCreature aCreature) {
        super(aCreature.getName()+" ("+aCreature.getAmount()+")");
        nameOfCreature = getText();
        setAppearance(aCreature.getName());
        addEventHandler(MouseEvent.MOUSE_CLICKED, x -> {
            if(displayWindowForSellingAndReturnIfSell())
                aController.sell(aCreature);
            aController.refreshGui();
        });
    }

    private void setAppearance(String aNameOfCreature) {
        ImageView image = GraphicsOfCreaturesMaker.getGraphicsOf(aNameOfCreature, Creature.Team.LEFT_TEAM,95,100);
        setGraphic(image);
        setPrefWidth(300);
    }

    private boolean displayWindowForSellingAndReturnIfSell() {
        HBox top = new HBox();
        HBox bottom = new HBox();
        preparingWindow(bottom,top);
        prepareSellingAndCloseButtonsAndTop(bottom, top);
        windowForSellingCreature.showAndWait();
        return ifSell;
    }

    private void prepareSellingAndCloseButtonsAndTop(HBox aBottom, HBox aTop) {
        VBox vBox = new VBox();
        vBox.getChildren().add(new Label("Are you sure you want"));
        vBox.getChildren().add(new Label("to sell it?"));
        aTop.getChildren().add(vBox);
        vBox.setAlignment(Pos.CENTER);
        Button sellButton = new Button("Sell");
        Button cancelButton = new Button("Cancel");
        sellButton.setPrefWidth(200);
        cancelButton.setPrefWidth(200);
        sellButton.setAlignment(Pos.CENTER);
        cancelButton.setAlignment(Pos.CENTER);
        cancelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, x -> windowForSellingCreature.close());
        sellButton.addEventHandler(MouseEvent.MOUSE_CLICKED, x -> {
            ifSell = true;
            windowForSellingCreature.close();
        });
        aBottom.getChildren().add(sellButton);
        aBottom.getChildren().add(cancelButton);
    }

    private void preparingWindow(HBox aBottom, HBox aTop) {
        windowForSellingCreature = new Stage();
        windowForSellingCreature.getIcons().add(new Image("jpg/icon.jpg"));
        BorderPane pane = new BorderPane();
        Scene scene = new Scene(pane, 300, 200);
        scene.getStylesheets().add("fxml/main.css");
        windowForSellingCreature.setScene(scene);
        windowForSellingCreature.initOwner(this.getScene().getWindow());
        windowForSellingCreature.initModality(Modality.APPLICATION_MODAL);
        windowForSellingCreature.setTitle("Selling " + getName());
        windowForSellingCreature.setResizable(false);
        aBottom.setAlignment(Pos.CENTER);
        aTop.setAlignment(Pos.CENTER);
        pane.setBottom(aBottom);
        pane.setTop(aTop);
    }

    String getName() {
        return nameOfCreature;
    }
}
