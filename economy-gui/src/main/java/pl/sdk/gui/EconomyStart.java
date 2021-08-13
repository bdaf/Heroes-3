package pl.sdk.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import pl.sdk.hero.EconomyHero;

public class EconomyStart extends Application {

    private String VERSION = "1.0.0-SNAPSHOT";

    public static void main(String[] aArgs) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("fxml/economy.fxml"));
        loader.setController(new EconomyController(new EconomyHero(EconomyHero.Fraction.NECROPOLIS,3000),new EconomyHero(EconomyHero.Fraction.CASTLE,3000)));
        stage.getIcons().add(new Image("jpg/icon.jpg"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("Heroes "+VERSION);
        stage.setScene(scene);
        stage.show();
    }
}
