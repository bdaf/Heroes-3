package pl.sdk.converter;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.sdk.creatures.*;
import pl.sdk.gui.BattleMapController;
import pl.sdk.hero.EconomyHero;
import pl.sdk.music.MusicInGame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static pl.sdk.GameEngine.VERSION;
import static pl.sdk.converter.ProperFractionConverter.getProperFactoryForFractionOf;


public class EcoBattleConverter {
    public static void start(EconomyHero aLeftHero, EconomyHero aRightHero, Stage aWindow) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(EcoBattleConverter.class.getClassLoader().getResource("fxml/battleMap.fxml"));
            List<Creature> leftArmy = convert(aLeftHero);
            List<Creature> rightArmy = convert(aRightHero);
            loader.setController(new BattleMapController(leftArmy, rightArmy));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Heroes "+ VERSION);
            stage.setScene(scene);
            aWindow.close();
            stage.show();
            MusicInGame.MUSIC_IN_ECONOMY.pause();
            MusicInGame.MUSIC_IN_BATTLE.play();

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

