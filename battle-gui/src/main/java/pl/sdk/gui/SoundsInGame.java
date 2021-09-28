package pl.sdk.gui;

import javafx.scene.media.AudioClip;

public enum SoundsInGame {

    PASS_VOICE("endOfPassVoice.mp3", 0.5);

    private final AudioClip audio;

    SoundsInGame(String aNameOfSound, double aVolume) {
        audio = new AudioClip(getClass().getResource("/audio/sounds/"+aNameOfSound).toExternalForm());
        audio.setVolume(aVolume);
    }
    public void play(){
        audio.play();
    }
}
