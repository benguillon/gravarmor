package main.fr.epsi.gravarmor.controller;

import javafx.stage.Stage;
import main.fr.epsi.gravarmor.model.callback.OnNewGameCallback;

public class WindowController {

    private Stage stage;

    private OnNewGameCallback onNewGameCallback;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setOnNewGameCallback(OnNewGameCallback onNewGameCallback) {
        this.onNewGameCallback = onNewGameCallback;
    }

    public void handleMenuActionNewGame(){

        System.out.println("Menu New Game");

        if(onNewGameCallback != null) {
            onNewGameCallback.handleEvent();
        }
    }

    public void handleMenuActionQuit(){

        System.out.println("Menu Quit");
        stage.close();
    }

    public void stop() {

    }
}
