package pl.sdk.converter;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.sdk.creatures.*;
import pl.sdk.gui.BattleMapController;
import pl.sdk.hero.EconomyHero;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static pl.sdk.converter.ProperFractionConverter.getProperFactoryForFractionOf;

public class EcoBattleConverter {
    public static void start(EconomyHero aLeftHero, EconomyHero aRightHero) {
        Scene scene = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(EcoBattleConverter.class.getClassLoader().getResource("fxml/battleMap.fxml"));
            List<Creature> leftArmy = convert(aLeftHero);
            List<Creature> rightArmy = convert(aRightHero);
            loader.setController(new BattleMapController(leftArmy, rightArmy));
            scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("FXML Welcome");
            stage.setScene(scene);
            stage.show();
        } catch (IOException aE) {
            aE.printStackTrace();
        }
    }

    public static List<Creature> convert(EconomyHero aEcoHero) {
        Factory factory = getProperFactoryForFractionOf(aEcoHero);
        List<Creature> ret = new ArrayList<>();
        aEcoHero.getHeroArmy().forEach(c -> ret.add(factory.Create(c.isUpgraded(),c.getTier(),c.getAmount())));
        return ret;
    }

}

