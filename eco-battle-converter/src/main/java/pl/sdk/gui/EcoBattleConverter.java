package pl.sdk.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.sdk.creatures.*;
import pl.sdk.hero.EconomyHero;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class EcoBattleConverter {
    static void start(EconomyHero aLeftHero, EconomyHero aRightHero){
        Scene scene = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(EcoBattleConverter.class.getClassLoader().getResource("fxml/battleMap.fxml"));
            List<Creature> leftArmy = convert(aLeftHero.getHeroArmy(),aLeftHero.getFraction());
            List<Creature> rightArmy = convert(aRightHero.getHeroArmy(), aRightHero.getFraction());
            loader.setController(new BattleMapController(leftArmy, rightArmy));
            scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("FXML Welcome");
            stage.setScene(scene);
            stage.show();
            //pane.setStyle("-fx-border-color: black");
        } catch (IOException aE) {
            aE.printStackTrace();
        }
    }

    static List<Creature> convert(List<EconomyCreature> aHeroArmy, EconomyHero.Fraction aFraction) {
        Factory factory;
        if(aFraction == EconomyHero.Fraction.NECROPOLIS)
            factory = new NecropolisFactory();
        else if(aFraction == EconomyHero.Fraction.CASTLE)
            factory = new CastleFactory();
        else
            throw new NullPointerException("Factory used to convert economy creatures in creatures is null!");
        List<Creature> result = new LinkedList<Creature>();
        //aHeroArmy.forEach(c -> result.add(factory.Create(c.isUpgraded(),c,c.getAmount())));
        return null;
    }
}
