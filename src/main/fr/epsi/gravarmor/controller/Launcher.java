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
    private GameLogic gameLogic;

    static final double HEXA_WIDTH = 30;
    static final double HEXA_HEIGHT = Math.sqrt(3)/2*HEXA_WIDTH;


    public void start(Stage stage) {

        HexaLand land = new HexaLand();


        try {
            FXMLLoader windowLoader = new FXMLLoader(getClass().getClassLoader().getResource("main/fr/epsi/gravarmor/view/fxml/windowView.fxml"));
            VBox windowView = windowLoader.load();
            windowController = windowLoader.getController();
            windowController.setStage(stage);

            ScrollPane menuPane = (ScrollPane) windowView.lookup("#menuPane");
            new MenuController(menuPane);

            Scene scene = new Scene(windowView, (land.getWidth()*HEXA_WIDTH*3/4)+HEXA_WIDTH/4+5+320, land.getHeight()*HEXA_HEIGHT+28+2);

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

            ScrollPane landPane = (ScrollPane)windowView.lookup("#landPane");
            gameLogic = new GameLogic(landPane, land);
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
