package pl.sdk.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import pl.sdk.hero.EconomyHero;
import pl.sdk.hero.Fraction;
import pl.sdk.settings.EconomySettings;

import static javafx.application.Platform.exit;
import static pl.sdk.GameEngine.VERSION;

public class EconomyStart extends Application {

    private EconomySettings settings;

    public static void main(String[] aArgs) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        MusicInGame.MUSIC_IN_ECONOMY.play();
        EconomySettings settings = chooseSettings();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("fxml/economy.fxml"));
        loader.setController(new EconomyController(new EconomyHero(Fraction.NECROPOLIS,3000),new EconomyHero(Fraction.CASTLE,3000),settings));
        stage.getIcons().add(new Image("jpg/icon.jpg"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("Herociples "+ VERSION);
        stage.setScene(scene);
        stage.show();
    }

    private EconomySettings chooseSettings() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("fxml/choose.fxml"));
        loader.setController(new ChooseKindOfGameController(this));
        Stage stage = new Stage();
        stage.getIcons().add(new Image("jpg/icon.jpg"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("Herociples "+ VERSION + "choosing kind of game!");
        stage.setScene(scene);
        stage.showAndWait();
        if(settings == null) exit();
        return settings;
    }


    void setKindOfGame(EconomySettings aSettings) {
        settings = aSettings;
    }
}
