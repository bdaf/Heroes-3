package pl.sdk.creatures;

import com.google.common.collect.Range;
import org.junit.jupiter.api.Test;
import pl.sdk.GameEngine;
import pl.sdk.Point;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AttackWithSplashDamageTest {

    @Test
    void splashDamageShouldAttackEnemiesButNotAllies(){
        NecropolisFactory factory = new NecropolisFactory();
        Creature splashDamageCreature = new SplashDamageCreature(new Creature.BuilderForTesting()
                .attack(Integer.MAX_VALUE)
                .damage(Range.closed(Integer.MAX_VALUE,Integer.MAX_VALUE))
                .build(),factory.getSplashForLich());

        splashDamageCreature.setTeam(Creature.Team.LEFT_TEAM);
        List<Creature> creaturesOnLeft = new ArrayList<>();
        creaturesOnLeft.add(splashDamageCreature);
        for (int i = 0; i < 3; i++) {
            Creature arrayCreature = new Creature.BuilderForTesting()
                    .moveRange(Integer.MAX_VALUE)
                    .maxHp(1)
                    .build();
            arrayCreature.setTeam(Creature.Team.LEFT_TEAM);
            creaturesOnLeft.add(arrayCreature);
        }
        List<Creature> creaturesOnRight = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Creature creature = new Creature.BuilderForTesting()
                    .moveRange(Integer.MAX_VALUE)
                    .maxHp(1)
                    .build();
            creature.setTeam(Creature.Team.RIGHT_TEAM);
            creaturesOnRight.add(creature);
        }

        GameEngine engine = new GameEngine(creaturesOnLeft,creaturesOnRight);
        engine.pass();

        engine.move(new Point(4,4)); engine.pass();
        engine.move(new Point(5,4)); engine.pass();
        engine.move(new Point(6,4)); engine.pass();

        engine.move(new Point(5,5)); engine.pass();
        engine.move(new Point(4,5)); engine.pass();
        engine.move(new Point(4,6)); engine.pass();
        engine.move(new Point(5,6)); engine.pass();
        engine.move(new Point(6,6)); engine.pass();
        engine.move(new Point(6,5)); engine.pass();


        assertEquals(splashDamageCreature, engine.getActiveCreature());
        engine.attack(5,5);

        creaturesOnRight.forEach(c ->{
                assertFalse(c.isAlive());
        });

        creaturesOnLeft.forEach(c ->{
            assertTrue(c.isAlive());
        });
    }
}
