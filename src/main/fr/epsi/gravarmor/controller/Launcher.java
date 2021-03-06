package main.fr.epsi.gravarmor.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import main.fr.epsi.gravarmor.model.HexaLand;

import java.io.IOException;

public class Launcher extends Application {

    private WindowController windowController;

    private ScrollPane menuPane;
    private ScrollPane landPane;

    private GameLogic gameLogic;

    static double HEXA_WIDTH = 45;
    static double HEXA_HEIGHT = Math.sqrt(3)/2*HEXA_WIDTH;


    public void start(Stage stage) {

        try {
            FXMLLoader windowLoader = new FXMLLoader(getClass().getClassLoader().getResource("main/fr/epsi/gravarmor/view/fxml/windowView.fxml"));
            VBox windowView = windowLoader.load();

            Scene scene = new Scene(windowView, (HexaLand.getWidth()*HEXA_WIDTH*3/4)+HEXA_WIDTH/4+5+320, HexaLand.getHeight()*HEXA_HEIGHT+28+2);

            windowController = windowLoader.getController();
            windowController.setParams(stage, this::initMap, () -> {

                HEXA_WIDTH *= 1.2;
                HEXA_HEIGHT = Math.sqrt(3)/2*HEXA_WIDTH;
                gameLogic.draw();
            }, () -> {

                HEXA_WIDTH *= 1/1.2;
                HEXA_HEIGHT = Math.sqrt(3)/2*HEXA_WIDTH;
                gameLogic.draw();
            });
            scene.setOnZoom(event -> {
                HEXA_WIDTH *= event.getZoomFactor();
                HEXA_HEIGHT = Math.sqrt(3)/2*HEXA_WIDTH;
                gameLogic.draw();
            });

            menuPane = (ScrollPane) windowView.lookup("#menuPane");
            landPane = (ScrollPane)windowView.lookup("#landPane");


            if(Screen.getScreens().size() > 1) {
                Screen screen = Screen.getScreens().get(1);
                Rectangle2D bounds = screen.getVisualBounds();

                stage.setX(bounds.getMinX() + 100);
                stage.setY(bounds.getMinY() + 100);
            }

            stage.setScene(scene);
            stage.setTitle("Gravarmor");
            stage.getIcons().add(new Image("main/fr/epsi/gravarmor/icons/appIcon.png"));
            stage.show();

            initMap();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initMap() {

        if(gameLogic != null) {
            gameLogic.stop();
        }

        try {
            MenuController menuController = new MenuController(menuPane);

            HexaLand land = new HexaLand();
            gameLogic = new GameLogic(landPane, menuController, land);
            gameLogic.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop(){

        System.out.println("Stopping!");

        if(gameLogic != null) {
            gameLogic.stop();
        }

        if(windowController != null) {
            windowController.stop();
        }
    }

    public static void main (String args[]){
        launch(args);
    }
}
