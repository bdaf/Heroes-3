package pl.sdk.converter;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.sdk.creatures.*;
import pl.sdk.gui.BattleMapController;
import pl.sdk.hero.EconomyHero;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

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
        Factory factory = getProperFactoryForFraction(aEcoHero);
        List<Creature> ret = new LinkedList<Creature>();
        aEcoHero.getHeroArmy().forEach(c -> ret.add(factory.Create(c.isUpgraded(),c.getTier(),c.getAmount())));
        return ret;
    }

    public static Factory getProperFactoryForFraction(EconomyHero aEcoHero) {
        Factory factory;
        if (aEcoHero.getFraction() == EconomyHero.Fraction.NECROPOLIS)
            factory = new NecropolisFactory();
        else if (aEcoHero.getFraction() == EconomyHero.Fraction.CASTLE)
            factory = new CastleFactory();
        else
            throw new NullPointerException("Factory which is used to convert economy creatures to creatures is null!");
        return factory;
    }

    public static EconomyFactory getProperEconomyFactoryForFraction(EconomyHero aEcoHero) {
        EconomyFactory factory;
        if (aEcoHero.getFraction() == EconomyHero.Fraction.NECROPOLIS)
            factory = new EconomyNecropolisFactory();
        else if (aEcoHero.getFraction() == EconomyHero.Fraction.CASTLE)
            factory = new EconomyCastleFactory();
        else
            throw new NullPointerException("Economy factory which is used to convert economy creatures to creatures is null!");
        return factory;
    }

}

