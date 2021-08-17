package pl.sdk.gui;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public enum MusicInGame {
    MUSIC_IN_ECONOMY("economyMusic.mp3",0.1),
    MUSIC_IN_BATTLE("BattleMusic.mp3",0.3);

    private MediaPlayer mediaPlayer;

    MusicInGame(String aNameOfMusic, double aVolume) {
        Media media = new Media(new File("economy-gui/src/main/resources/music/"+aNameOfMusic).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        setVolume(aVolume);
    }

    public void play() { mediaPlayer.play(); }

    public void pause() { if(mediaPlayer !=null) mediaPlayer.pause(); }

    public void stop() { if (mediaPlayer != null) mediaPlayer.stop(); }

    public void setVolume(double aValue) { if (mediaPlayer != null) mediaPlayer.setVolume(aValue); }
}
