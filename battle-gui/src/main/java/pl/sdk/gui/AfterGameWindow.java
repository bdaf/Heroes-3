package pl.sdk.gui;

import javafx.scene.control.Label;

public class AfterGameWindow extends AfterBattleWindow {

    AfterGameWindow(BattleMapController aBattleMapController) {
        super(aBattleMapController);
    }

    @Override
    protected void setLabelsToMainVBox() {}

    @Override
    protected int getHeightOfWindow() {
        return 200;
    }

    @Override
    protected String getTitle() {
        return "End Of The Game!";
    }

    void addCaption(String aCaption){
        mainVBox.getChildren().add(new Label(aCaption));
    }
}
