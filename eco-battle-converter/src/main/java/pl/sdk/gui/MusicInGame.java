package pl.sdk.gui;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class MusicInGame {

    private static MediaPlayer mediaPlayer;

    public static void turnOnMusicForEconomy() {
        Media media = new Media(new File("economy-gui/src/main/resources/music/economyMusic.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(0.1);
        mediaPlayer.play();
    }

    public static void turnOnMusicForBattle() {
        Media media = new Media(new File("economy-gui/src/main/resources/music/battleMusic.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(0.3);
        mediaPlayer.play();
    }

    public static void stopCurrentMusic(){
        mediaPlayer.stop();
    }
    public static void setVolume(double aValue) {
        mediaPlayer.setVolume(aValue);
    }
}
