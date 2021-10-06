package pl.sdk.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import pl.sdk.hero.EconomyHero;
import pl.sdk.hero.Fraction;
import pl.sdk.settings.ModeOfGame;

import static pl.sdk.GameEngine.VERSION;

public class EconomyStart extends Application {

    private ModeOfGame modeOfGame;

    public static void main(String[] aArgs) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        MusicInGame.MUSIC_IN_ECONOMY.play();
        ModeOfGame modeOfGame = chooseKindOfGame();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("fxml/economy.fxml"));
        int startGold = modeOfGame.getStartGold();
        loader.setController(new EconomyController(new EconomyHero(Fraction.NECROPOLIS,startGold),new EconomyHero(Fraction.CASTLE,startGold), modeOfGame));
        stage.getIcons().add(new Image("jpg/icon.jpg"));

        Scene scene = new Scene(loader.load());
        stage.setTitle("Herociples "+ VERSION);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

    }

    private ModeOfGame chooseKindOfGame() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("fxml/choose.fxml"));
        loader.setController(new ChooseModeOfGameController(this));
        Stage stage = new Stage();
        stage.getIcons().add(new Image("jpg/icon.jpg"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("Herociples "+ VERSION + " - choosing game mode!");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();
        if(modeOfGame == null) {
            modeOfGame = ModeOfGame.NOT_SETTLED;
        }
        return modeOfGame;
    }


    void setModeOfGame(ModeOfGame aModeOfGame) {
        modeOfGame = aModeOfGame;
    }
}
