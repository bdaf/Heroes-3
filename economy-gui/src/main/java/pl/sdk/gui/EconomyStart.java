package pl.sdk.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pl.sdk.hero.EconomyHero;
import pl.sdk.hero.Fraction;
import pl.sdk.settings.KindOfGame;

import static pl.sdk.GameEngine.VERSION;

public class EconomyStart extends Application {

    private KindOfGame kindOfGame;

    public static void main(String[] aArgs) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        MusicInGame.MUSIC_IN_ECONOMY.play();
        KindOfGame kindOfGame = chooseKindOfGame();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("fxml/economy.fxml"));
        loader.setController(new EconomyController(new EconomyHero(Fraction.NECROPOLIS,3000),new EconomyHero(Fraction.CASTLE,3000),kindOfGame));
        stage.getIcons().add(new Image("jpg/icon.jpg"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("Herociples "+ VERSION);
        stage.setScene(scene);
        stage.show();
        if(kindOfGame == KindOfGame.NOT_SETTLED) Platform.exit();
    }

    private KindOfGame chooseKindOfGame() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("fxml/choose.fxml"));
        loader.setController(new ChooseKindOfGameController(this));
        Stage stage = new Stage();
        stage.getIcons().add(new Image("jpg/icon.jpg"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("Herociples "+ VERSION + " - choosing kind of game!");
        stage.setScene(scene);
        stage.showAndWait();
        if(kindOfGame == null) {
            kindOfGame = KindOfGame.NOT_SETTLED;
        }
        return kindOfGame;
    }


    void setKindOfGame(KindOfGame aKindOfGame) {
        kindOfGame = aKindOfGame;
    }
}
