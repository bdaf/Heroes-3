package pl.sdk.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.sdk.hero.EconomyHero;

public class EconomyStart extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("fxml/economy.fxml"));
        loader.setController(new EconomyController(new EconomyHero(EconomyHero.Fraction.NECROPOLIS,3000),new EconomyHero(EconomyHero.Fraction.CASTLE,3000)));
        Scene scene = new Scene(loader.load());
        stage.setTitle("FXML Welcome");
        stage.setScene(scene);
        stage.show();
    }
}
