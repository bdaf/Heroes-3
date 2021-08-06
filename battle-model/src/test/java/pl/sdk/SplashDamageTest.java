package pl.sdk;

import org.junit.jupiter.api.Test;
import pl.sdk.Board;
import pl.sdk.creatures.Creature;
import pl.sdk.creatures.NecropolisFactory;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

public class SplashDamageTest {


    @Test
    void SplashDamageCreatureShouldDoSplashDmgButOthersCreatureShouldNot(){
        NecropolisFactory factory = new NecropolisFactory();
        Creature splashCreature = factory.Create(true,5,1);
        Creature defender = spy(Creature.class);
        Creature splashingCreature = spy(Creature.class);
        Creature splashingCreature1 = spy(Creature.class);
        Creature splashingCreature2 = spy(Creature.class);
        Creature splashingCreature3 = spy(Creature.class);
        Creature splashingCreature4 = spy(Creature.class);
        Creature splashingCreature5 = spy(Creature.class);
        Creature splashingCreature6 = spy(Creature.class);
        Creature splashingCreature7 = spy(Creature.class);

        Board board = new Board();
        board.add(new Point(5,5),defender);

        board.add(new Point(4,5),splashingCreature);
        board.add(new Point(5,4),splashingCreature1);
        board.add(new Point(5,6),splashingCreature2);
        board.add(new Point(6,5),splashingCreature3);
        board.add(new Point(4,4),splashingCreature4);
        board.add(new Point(6,6),splashingCreature5);
        board.add(new Point(4,6),splashingCreature6);
        board.add(new Point(6,4),splashingCreature7);

        GameEngine engine = new GameEngine(List.of(splashCreature), Collections.emptyList(), board);

        engine.attack(5,5);

        verify(defender).applyDamage(anyInt());

        verify(splashingCreature).applyDamage(anyInt());
        verify(splashingCreature1).applyDamage(anyInt());
        verify(splashingCreature2).applyDamage(anyInt());
        verify(splashingCreature3).applyDamage(anyInt());
        verify(splashingCreature4).applyDamage(anyInt());
        verify(splashingCreature5).applyDamage(anyInt());
        verify(splashingCreature6).applyDamage(anyInt());
        verify(splashingCreature7).applyDamage(anyInt());
    }

    @Test
    void UnitWithoutSplashDamageCreatureShouldNotDoSplashDmg(){
        Creature notSplashCreature = NecropolisFactory.CreateDefaultCreatureForTests();
        Creature defender = spy(Creature.class);

        Creature notSplashingCreature = spy(Creature.class);
        Creature notSplashingCreature0 = spy(Creature.class);
        Creature notSplashingCreature1 = spy(Creature.class);
        Creature notSplashingCreature2 = spy(Creature.class);
        Creature notSplashingCreature3 = spy(Creature.class);
        Creature notSplashingCreature4 = spy(Creature.class);
        Creature notSplashingCreature5 = spy(Creature.class);
        Creature notSplashingCreature6 = spy(Creature.class);

        Board board = new Board();
        board.add(new Point(5,5),defender);

        board.add(new Point(4,4),notSplashingCreature);
        board.add(new Point(4,5),notSplashingCreature0);
        board.add(new Point(6,6),notSplashingCreature1);
        board.add(new Point(4,6),notSplashingCreature2);
        board.add(new Point(6,4),notSplashingCreature3);
        board.add(new Point(5,4),notSplashingCreature4);
        board.add(new Point(5,6),notSplashingCreature5);
        board.add(new Point(6,5),notSplashingCreature6);

        GameEngine engine = new GameEngine(List.of(notSplashCreature), Collections.emptyList(), board);

        engine.attack(5,5);

        verify(defender).applyDamage(anyInt());

        verify(notSplashingCreature,never()).applyDamage(anyInt());
        verify(notSplashingCreature0,never()).applyDamage(anyInt());
        verify(notSplashingCreature1,never()).applyDamage(anyInt());
        verify(notSplashingCreature2,never()).applyDamage(anyInt());
        verify(notSplashingCreature3,never()).applyDamage(anyInt());
        verify(notSplashingCreature4,never()).applyDamage(anyInt());
        verify(notSplashingCreature5,never()).applyDamage(anyInt());
        verify(notSplashingCreature6,never()).applyDamage(anyInt());
    }
}





















