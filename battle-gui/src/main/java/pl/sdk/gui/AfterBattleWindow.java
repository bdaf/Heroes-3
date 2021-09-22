package pl.sdk.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.sdk.hero.Fraction;

public class AfterBattleWindow {
    private final Stage window;
    private final BattleMapController battleMapController;
    protected final VBox mainVBox;

    AfterBattleWindow(BattleMapController aBattleMapController) {
        battleMapController = aBattleMapController;
        window = new Stage();
        HBox top = new HBox();
        mainVBox = new VBox(0);
        HBox bottom = new HBox();
        preparingWindow(bottom, top);
        prepareCloseButtonAndTop(bottom, top);
        MusicInGame.MUSIC_IN_BATTLE.stop();
        MusicInGame.MUSIC_IN_ECONOMY.play();
    }

    private void preparingWindow(HBox aBottom, HBox aTop) {
        window.setTitle(getTitle());
        window.getIcons().add(new Image("jpg/icon.jpg"));
        BorderPane pane = new BorderPane();
        Scene scene = new Scene(pane, getWidthOfWindow(), getHeightOfWindow());
        scene.getStylesheets().add("fxml/main.css");
        window.setScene(scene);
        window.initOwner(battleMapController.getGridMap().getScene().getWindow());
        window.initModality(Modality.APPLICATION_MODAL);
        window.setResizable(false);
        aBottom.setAlignment(Pos.CENTER);
        aTop.setAlignment(Pos.CENTER);
        pane.setBottom(aBottom);
        pane.setTop(aTop);
    }

    private void prepareCloseButtonAndTop(HBox aBottom, HBox aTop) {
        setLabelsToMainVBox();
        aTop.getChildren().add(mainVBox);
        mainVBox.setAlignment(Pos.CENTER);
        Button closeButton = new Button("Close");
        closeButton.setPrefWidth(200);
        closeButton.setAlignment(Pos.CENTER);
        closeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (e.getButton() == MouseButton.PRIMARY) window.close();
        });
        aBottom.getChildren().add(closeButton);
    }

    protected void setLabelsToMainVBox() {
        Fraction leftFraction = battleMapController.getLeftTeamFraction();
        Fraction rightFraction = battleMapController.getRightTeamFraction();

        mainVBox.getChildren().add(new Label("Congratulations!"));
        Text text = new Text("If You are the " + battleMapController.getWinnerOfTheBattle().name() + ", you won battle!\nIf You are not, sorry, train better.");
        text.setFont(Font.font(20));
        mainVBox.getChildren().add(text);
        mainVBox.getChildren().add(new Label(leftFraction.name()+" | "+leftFraction.getPoints()+" - "+rightFraction.getPoints()+" | "+rightFraction.name()));
    }

    private int getHeightOfWindow() {
        return 225;
    }

    private int getWidthOfWindow() {
        return 450;
    }

    protected String getTitle(){
        return "End Of The Battle!";
    }

    void showAndWait(){
        window.showAndWait();
    }
}
