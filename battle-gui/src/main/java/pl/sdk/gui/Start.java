package pl.sdk.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.sdk.creatures.CastleFactory;
import pl.sdk.creatures.Factory;

import java.util.List;

import static pl.sdk.GameEngine.VERSION;

public class Start extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Factory castle = new CastleFactory();
        Factory necropolis = new CastleFactory();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("fxml/battleMap.fxml"));
        loader.setController(new BattleMapController(List.of(castle.Create(true,1,1)),List.of(necropolis.Create(true,1,1))));
        Scene scene = new Scene(loader.load());
        stage.setTitle("Heroes "+ VERSION);
        stage.setScene(scene);
        stage.show();
    }
}
