package pl.sdk.gui;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public enum MusicInGame {
    MUSIC_IN_ECONOMY("economyMusic.mp3",0.1),
    MUSIC_IN_BATTLE("battleMusic.mp3",0.3);

    private MediaPlayer mediaPlayer;

    MusicInGame(String aNameOfMusic, double aVolume) {
        Media media = new Media(new File("battle-gui/src/main/resources/audio/music/"+aNameOfMusic).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        /* I don't know why this code below doesn't work, it's more readable for me, but it points always null
        mediaPlayer = new MediaPlayer(new Media(getClass().getResource("audio/music/"+aNameOfMusic).toString())); */
        setVolume(aVolume);
        mediaPlayer.setCycleCount(AudioClip.INDEFINITE);
    }

    public void play() { mediaPlayer.play(); }

    public void pause() { if(mediaPlayer !=null) mediaPlayer.pause(); }

    public void stop() { if (mediaPlayer != null) mediaPlayer.stop(); }

    public void setVolume(double aValue) { if (mediaPlayer != null) mediaPlayer.setVolume(aValue); }
}
