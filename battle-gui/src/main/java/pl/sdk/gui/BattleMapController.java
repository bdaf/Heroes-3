package pl.sdk.gui;

import com.google.common.collect.Range;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import pl.sdk.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class BattleMapController implements PropertyChangeListener {

    @FXML
    private GridPane gridMap;
    private GameEngine gameEngine;
    @FXML
    private Button passButton;
    @FXML
    private Button escapeButton;

    public BattleMapController(){
        List<Creature> notUpgradedCreatures = new ArrayList<Creature>();
        Creature c;
        c = new BlockingCounterAttackCreature.Builder()
                .name("Skeleton")
                .maxHp(6)
                .attack(5)
                .moveRange(4)
                .damage(Range.closed(1,3))
                .armor(6)
                .build();
        notUpgradedCreatures.add(c);
        c = new Creature.Builder()
                .name("WalkingDead")
                .maxHp(15)
                .attack(5)
                .moveRange(3)
                .damage(Range.closed(2,3))
                .armor(5)
                .build();
        notUpgradedCreatures.add(c);
        c = new Creature.Builder()
                .name("Wight")
                .maxHp(18)
                .attack(7)
                .moveRange(5)
                .damage(Range.closed(3,5))
                .armor(7)
                .build();
        notUpgradedCreatures.add(c);
        c = new Creature.Builder()
                .name("Vampire")
                .maxHp(30)
                .attack(10)
                .moveRange(6)
                .damage(Range.closed(5,8))
                .armor(9)
                .build();
        notUpgradedCreatures.add(c);
        c = new ShootingCreature.Builder()
                .name("Lich")
                .maxHp(30)
                .attack(13)
                .moveRange(6)
                .damage(Range.closed(11,13))
                .armor(10)
                .build();
        notUpgradedCreatures.add(c);
        c = new Creature.Builder()
                .name("BlackKnight")
                .maxHp(120)
                .attack(16)
                .moveRange(7)
                .damage(Range.closed(15,30))
                .armor(16)
                .build();
        notUpgradedCreatures.add(c);
        c = new Creature.Builder()
                .name("BoneDragon")
                .maxHp(150)
                .attack(17)
                .moveRange(9)
                .damage(Range.closed(25,50))
                .armor(15)
                .build();
        notUpgradedCreatures.add(c);

        List<Creature> upgradedCreatures = new ArrayList<Creature>();
        c = new Creature.Builder()
                .name("SkeletonWarrior")
                .maxHp(6)
                .attack(6)
                .moveRange(5)
                .damage(Range.closed(1,3))
                .armor(6)
                .build();
        upgradedCreatures.add(c);
        c = new Creature.Builder()
                .name("Zombie")
                .maxHp(20)
                .attack(5)
                .moveRange(4)
                .damage(Range.closed(2,3))
                .armor(5)
                .build();
        upgradedCreatures.add(c);
        c = new Creature.Builder()
                .name("Wraith")
                .maxHp(18)
                .attack(7)
                .moveRange(7)
                .damage(Range.closed(3,5))
                .armor(7)
                .build();
        upgradedCreatures.add(c);
        c = new Creature.Builder()
                .name("VampireLord")
                .maxHp(40)
                .attack(10)
                .moveRange(9)
                .damage(Range.closed(5,8))
                .armor(10)
                .build();
        upgradedCreatures.add(c);
        c = new ShootingCreature.Builder()
                .name("PowerLich")
                .maxHp(40)
                .attack(13)
                .moveRange(7)
                .damage(Range.closed(11,15))
                .armor(10)
                .build();
        upgradedCreatures.add(c);
        c = new Creature.Builder()
                .name("DreadKnight")
                .maxHp(120)
                .attack(16)
                .moveRange(7)
                .damage(Range.closed(15,30))
                .armor(16)
                .build();
        upgradedCreatures.add(c);
        c = new Creature.Builder()
                .name("GhostDragon")
                .maxHp(200)
                .attack(19)
                .moveRange(14)
                .damage(Range.closed(25,50))
                .armor(17)
                .build();
        upgradedCreatures.add(c);

        gameEngine = new GameEngine(notUpgradedCreatures,upgradedCreatures);
    }


    @FXML
    void initialize(){
        gameEngine.addObserver(gameEngine.CURRENT_CREATURE_CHANGED , this);
        gameEngine.addObserver(gameEngine.CREATURE_MOVED , this);
        gameEngine.addObserver(gameEngine.CURRENT_CREATURE_ATTACKED , this);
        passButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) ->{
            gameEngine.pass();
        });


        refreshGui();
    }

    private void refreshGui() {
        for (int x = 0; x < 20; x++) {
            for (int y = 0; y < 15; y++) {
                MapTile mapTile = new MapTile();
                gridMap.add(mapTile,x,y);

                Creature c = gameEngine.get(x,y);
                if(c!=null) {
                    mapTile.addCreature(c.getStringOfCurrentHp(), c.getName());
                    if(c == gameEngine.getActiveCreature())
                        mapTile.setBackgroundColor(Color.GREEN);
                    else if(gameEngine.canAttack(x,y)){
                        final int FX = x;
                        final int FY = y;
                        mapTile.setBackgroundColor((Color.RED));
                        mapTile.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> gameEngine.attack(new Point(FX,FY)));
                    }
                }
                else if(gameEngine.canMove(x,y)){
                    final int FX = x;
                    final int FY = y;
                    mapTile.setBackgroundColor((Color.GRAY));
                    mapTile.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> gameEngine.move(new Point(FX,FY)));
                }
            }
        }
    }


    @Override
    public void propertyChange(PropertyChangeEvent aPropertyChangeEvent) {
        refreshGui();
    }
}
