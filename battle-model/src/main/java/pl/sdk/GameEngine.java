package pl.sdk;

import java.util.ArrayList;
import java.util.List;

public class GameEngine {


    private final Board board;
    private final CreatureTurnQueue queue;

    public GameEngine(List<Creature> creaturesOnLeftSide, List<Creature> creaturesOnRightSide) {
        this.board = new Board();
        putCreaturesToBoard(creaturesOnLeftSide, creaturesOnRightSide);

        List<Creature> creaturesOnBothSides = new ArrayList<>();
        creaturesOnBothSides.addAll(creaturesOnLeftSide);
        creaturesOnBothSides.addAll(creaturesOnRightSide);

        this.queue = new CreatureTurnQueue(creaturesOnBothSides);
    }

    private void putCreaturesToBoard(List<Creature> creaturesOnLeftSide, List<Creature> creaturesOnRightSide) {
        putCreatureFromOneSideToBoard(creaturesOnLeftSide, 0);
        putCreatureFromOneSideToBoard(creaturesOnRightSide, Board.WIDTH-1);
    }

    private void putCreatureFromOneSideToBoard(List<Creature> creaturesOnRightSide, int x) {
        for (int i = 0; i < creaturesOnRightSide.size(); i++) {
            board.add(new Point(x, i * 2), creaturesOnRightSide.get(i));
        }
    }

    public void move(Point targetPoint){
        board.move(queue.getActiveCreature(),targetPoint);
        pass();
    }

    public void pass(){
        queue.next();
    }

    public void attack(int x, int y){
        queue.getActiveCreature().attack(board.get(x,y));
        pass();
    }

    public Creature get(int x, int y) {
        return board.get(x,y);
    }

    public Creature getActiveCreature() {
        return queue.getActiveCreature();
    }
}
