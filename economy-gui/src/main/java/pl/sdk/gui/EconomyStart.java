package pl.sdk.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EconomyStart extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(FXMLLoader.load(getClass().getClassLoader().getResource("fxml/economy.fxml")));
        stage.setTitle("FXML Welcome");
        stage.setScene(scene);
        stage.show();
    }
}
