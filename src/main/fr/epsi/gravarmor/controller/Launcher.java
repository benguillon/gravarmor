package main.fr.epsi.gravarmor.controller;

import main.fr.epsi.gravarmor.model.HexaLand;
import main.fr.epsi.gravarmor.model.LandBox;
import main.fr.epsi.gravarmor.model.Unit;
import main.fr.epsi.gravarmor.model.UnitType;
import main.fr.epsi.gravarmor.model.coordinates.HexaCoordinates;
import main.fr.epsi.gravarmor.model.coordinates.Point;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class Launcher extends Application {

    private HexaLand land;
    private WindowController windowController;

    static final double HEXA_WIDTH = 30;
    static final double HEXA_HEIGHT = Math.sqrt(3)/2*HEXA_WIDTH;

    public void start(Stage stage) {

        land = new HexaLand();

        try {
            FXMLLoader windowLoader = new FXMLLoader(getClass().getClassLoader().getResource("main/fr/epsi/gravarmor/view/fxml/windowView.fxml"));
            VBox windowView = windowLoader.load();
            windowController = windowLoader.getController();
            windowController.setStage(stage);

            Scene scene = new Scene(windowView, (land.getWidth()*HEXA_WIDTH*3/4)+HEXA_WIDTH/4+5+200, land.getHeight()*HEXA_HEIGHT+28+2);

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


            HexaCoordinates startUnitCoordinates = new HexaCoordinates(new Point(5,11));
            Unit unit = new Unit(UnitType.INFANTRY);
            unit.setCoordinates(startUnitCoordinates);
            LandBox box = land.getBox(startUnitCoordinates);
            box.getEntities().add(unit);

            ScrollPane landPane = (ScrollPane)windowView.lookup("#landPane");

            LandController landController = new LandController(landPane, land);
            landController.setOnBoxClickCallback(coordinates -> {
                System.out.println("Click  " + coordinates);

                HexaCoordinates from = unit.getCoordinates();
                HexaCoordinates to = coordinates;

                System.out.println("Distance " + from + " -> " + to + " : " + HexaCoordinates.distance(from, to));

                land.moveEntity(unit, coordinates);

                HexaCoordinates path[] = HexaCoordinates.line(from, to);
                System.out.println("Path " + from + " -> " + to + " : ");
                for(int i = 0; i < path.length; i++) {
                    System.out.println(path[i]);
                    land.getBox(path[i]).isSelected(true);
                }
                landController.drawLand();
            });
            landController.drawLand();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop(){

        System.out.println("Stopping!");
        if(windowController != null) {
            windowController.stop();
        }
    }

    public static void main (String args[]){
        launch(args);
    }
}
