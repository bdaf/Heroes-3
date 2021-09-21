package pl.sdk.converter;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.sdk.EconomyEngine;
import pl.sdk.creatures.*;
import pl.sdk.gui.BattleMapController;
import pl.sdk.gui.MusicInGame;
import pl.sdk.hero.EconomyHero;
import pl.sdk.hero.Fraction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static pl.sdk.GameEngine.VERSION;
import static pl.sdk.converter.ProperFractionConverter.getProperFactoryBasedOnFraction;

public class EcoBattleConverter {
    public static void start(EconomyEngine aEconomyEngine, Stage aWindow) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(EcoBattleConverter.class.getClassLoader().getResource("fxml/battleMap.fxml"));
            List<Creature> leftArmy = convert(aEconomyEngine.getLeftHero());
            List<Creature> rightArmy = convert(aEconomyEngine.getRightHero());
            loader.setController(new BattleMapController(leftArmy, rightArmy, aEconomyEngine.isThisTheLastBattle()));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Herociples " + VERSION);
            stage.getIcons().add(new Image("jpg/icon.jpg"));
            stage.setScene(scene);
            stage.initOwner(aWindow);
            stage.initModality(Modality.APPLICATION_MODAL);
            if (aEconomyEngine.isThisTheLastBattle()) aWindow.close();
            stage.show();
            MusicInGame.MUSIC_IN_ECONOMY.pause();
            MusicInGame.MUSIC_IN_BATTLE.play();

        } catch (IOException aE) {
            aE.printStackTrace();
        }
    }

    public static List<Creature> convert(EconomyHero aEcoHero) {
        Factory factory = getProperFactoryBasedOnFraction(aEcoHero.getFraction());
        List<Creature> listOfCreatures = new ArrayList<>();
        aEcoHero.getHeroArmy().forEach(c -> listOfCreatures.add(factory.create(c.isUpgraded(), c.getTier(), c.getAmount())));
        return listOfCreatures;
    }

    public static Creature convert(EconomyCreature aEconomyCreature, Fraction aFraction) {
        Factory factory = getProperFactoryBasedOnFraction(aFraction);
        EconomyCreature c = aEconomyCreature;
        return factory.create(c.isUpgraded(), c.getTier(), c.getAmount());
    }
}

