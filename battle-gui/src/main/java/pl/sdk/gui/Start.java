package pl.sdk.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Start extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("fxml/battleMap.fxml"));
        loader.setController(new BattleMapController());
        Scene scene = new Scene(loader.load());
        stage.setTitle("FXML Welcome");
        stage.setScene(scene);
        stage.show();
    }
}
