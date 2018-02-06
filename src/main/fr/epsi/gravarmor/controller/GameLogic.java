package main.fr.epsi.gravarmor.controller;

import javafx.scene.control.ScrollPane;
import main.fr.epsi.gravarmor.model.Entity;
import main.fr.epsi.gravarmor.model.HexaLand;

import java.util.List;

public class GameLogic {

    ScrollPane landPane;
    LandController landController;
    HexaLand land;

    Entity selectedEntity;

    public GameLogic(ScrollPane landPane, HexaLand land) {

        this.landPane = landPane;
        this.landController = new LandController(landPane, land);
        this.land = land;

        draw();
    }

    public void start() {

        /*
        HexaCoordinates startUnitCoordinates = new HexaCoordinates(new Point(5,11));
        Unit unit = new Unit(UnitType.INFANTRY);
        unit.setCoordinates(startUnitCoordinates);
        LandBox box = land.getBox(startUnitCoordinates);
        box.getEntities().add(unit);

        HexaCoordinates startUnitCoordinates2 = new HexaCoordinates(new Point(8,11));
        Unit unit2 = new Unit(UnitType.INFANTRY);
        unit2.setCoordinates(startUnitCoordinates2);
        LandBox box2 = land.getBox(startUnitCoordinates2);
        box2.getEntities().add(unit2);

        landPane.setBackground(new Background(new BackgroundImage(new Image("main/fr/epsi/gravarmor/view/background.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        */

        landController.setOnBoxClickCallback(coordinates -> {

            System.out.println("Click  " + coordinates);

            if(selectedEntity == null) {

                List<Entity> entities = land.getBox(coordinates).getEntities();
                if(entities.size() > 0) {
                    selectedEntity = entities.get(0);
                    selectedEntity.setSelected(true);
                    }
            }
            else {

                land.moveEntity(selectedEntity, coordinates);
                selectedEntity.setSelected(false);
                selectedEntity = null;
            }

            /*
            HexaCoordinates from = unit.getCoordinates();
            HexaCoordinates to = coordinates;

            System.out.println("Distance " + from + " -> " + to + " : " + HexaCoordinates.distance(from, to));


            if (unit.canMove(land.getBox(to).getType().getMovementPoints()) && unit.getSelected()) {
                land.moveEntity(unit, coordinates);
                unit.setSelected(false);
            }

            scene.setOnKeyReleased(event -> {
                if(event.getCode() == KeyCode.SPACE) {
                    System.out.println("gg");
                }
            });

            HexaCoordinates path[] = HexaCoordinates.line(from, to);
            System.out.println("Path " + from + " -> " + to + " : ");
            for(int i = 0; i < path.length; i++) {
                System.out.println(path[i]);
                land.getBox(path[i]).isSelected(true);
            }
            */

            draw();
        });
    }

    public void stop() {

    }

    public void draw() {
        landController.drawLand();
    }
}
