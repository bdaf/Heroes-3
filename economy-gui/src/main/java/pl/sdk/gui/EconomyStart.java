package pl.sdk.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import pl.sdk.hero.EconomyHero;

import java.io.File;

import static pl.sdk.GameEngine.VERSION;

public class EconomyStart extends Application {


    public static void main(String[] aArgs) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        turnOnMusic();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("fxml/economy.fxml"));
        loader.setController(new EconomyController(new EconomyHero(EconomyHero.Fraction.NECROPOLIS,3000),new EconomyHero(EconomyHero.Fraction.CASTLE,3000)));
        stage.getIcons().add(new Image("jpg/icon.jpg"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("Heroes "+ VERSION);
        stage.setScene(scene);
        stage.show();
    }

    private void turnOnMusic() {
        Media media = new Media(new File("economy-gui/src/main/resources/music/economyMusic.mp3").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(0.1);
        mediaPlayer.play();
    }
}
