package main.fr.epsi.gravarmor.controller;

import javafx.stage.Stage;
import main.fr.epsi.gravarmor.model.callback.IEmptyListener;

public class WindowController {

    private Stage stage;

    private IEmptyListener onNewGameCallback;
    private IEmptyListener onZoomMoreCallback;
    private IEmptyListener onZoomLessCallback;

    public void setParams(Stage stage, IEmptyListener onNewGameCallback, IEmptyListener onZoomMoreCallback, IEmptyListener onZoomLessCallback) {
        this.stage = stage;
        this.onNewGameCallback = onNewGameCallback;
        this.onZoomMoreCallback = onZoomMoreCallback;
        this.onZoomLessCallback = onZoomLessCallback;
    }

    public void handleMenuActionNewGame(){

        System.out.println("Menu New Game");

        if(onNewGameCallback != null) {
            onNewGameCallback.handleEvent();
        }
    }

    public void handleMenuActionZoomMore(){

        if(onZoomMoreCallback != null) {
            onZoomMoreCallback.handleEvent();
        }
    }

    public void handleMenuActionZoomLess(){

        if(onZoomLessCallback != null) {
            onZoomLessCallback.handleEvent();
        }
    }

    public void handleMenuActionQuit(){

        System.out.println("Menu Quit");
        stage.close();
    }

    public void stop() {

    }
}
