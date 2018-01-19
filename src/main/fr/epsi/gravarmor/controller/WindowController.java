package main.fr.epsi.gravarmor.controller;

import javafx.stage.Stage;

public class WindowController {

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void handleMenuActionNewGame(){

        System.out.println("Menu New Game");
    }

    public void handleMenuActionQuit(){

        System.out.println("Menu Quit");
        stage.close();
    }

    public void stop() {

    }
}
