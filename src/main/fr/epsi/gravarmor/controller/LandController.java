package main.fr.epsi.gravarmor.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;
import main.fr.epsi.gravarmor.model.*;
import main.fr.epsi.gravarmor.model.callback.ICoordinatesEntityListener;
import main.fr.epsi.gravarmor.model.callback.ICoordinatesListener;
import main.fr.epsi.gravarmor.model.coordinates.HexaCoordinates;
import main.fr.epsi.gravarmor.model.coordinates.Point;

import static main.fr.epsi.gravarmor.controller.Launcher.HEXA_HEIGHT;
import static main.fr.epsi.gravarmor.controller.Launcher.HEXA_WIDTH;

class LandController {

    private final ScrollPane pane;
    private final HexaLand land;

    private boolean isFirstDrawing;

    private ICoordinatesListener boxClickCallback;
    private ICoordinatesEntityListener entityClickCallback;

    LandController(ScrollPane pane, final HexaLand land) {

        this.pane = pane;
        this.land = land;

        this.isFirstDrawing = true;
    }

    public void drawLand() {

        Group g = new Group();
        Image imageAvionRouge = new Image("main/fr/epsi/gravarmor/icons/AvionRouge.png");
        Image imageAvionBlue = new Image("main/fr/epsi/gravarmor/icons/AvionBlue.png");
        Image imageCharRouge = new Image("main/fr/epsi/gravarmor/icons/CharRouge.png");
        Image imageCharBlue = new Image("main/fr/epsi/gravarmor/icons/CharBlue.png");
        Image imageInfantryRougeImage = new Image("main/fr/epsi/gravarmor/icons/InfantryRouge.png");
        Image imageInfantryBlue = new Image("main/fr/epsi/gravarmor/icons/InfantryBlue.png");

        for(int yl = 0; yl < land.getHeight(); yl++) {

            for (int xl = 0; xl < land.getWidth(); xl++) {

                final HexaCoordinates coordinates = new HexaCoordinates(new Point(xl, yl));
                final LandBox box = land.getBox(coordinates);

                if (box.getType() == BoxType.HIDDEN) {
                    continue;
                }

                double x = -HEXA_WIDTH / 3 + xl * HEXA_WIDTH - xl * (HEXA_WIDTH / 4);
                double y = -HEXA_HEIGHT / 2 + yl * HEXA_HEIGHT + (xl % 2 == 1 ? HEXA_HEIGHT / 2 : 0);


                Polygon polygonNode = new Polygon();
                polygonNode.getPoints().addAll(x + HEXA_WIDTH / 4, y + 0,
                        x + HEXA_WIDTH / 4 * 3, y + 0,
                        x + HEXA_WIDTH, y + HEXA_HEIGHT / 2,
                        x + HEXA_WIDTH / 4 * 3, y + HEXA_HEIGHT,
                        x + HEXA_WIDTH / 4, y + HEXA_HEIGHT,
                        x + 0, y + HEXA_HEIGHT / 2);
                g.getChildren().add(polygonNode);

                polygonNode.setStroke(Color.WHITE);
                polygonNode.setFill(getColorForType(box.getType()));

                if (box.isGraphicallyHighlighted()) {
                    polygonNode.setOpacity(0.5);
                    box.isGraphicallyHighlighted(false);
                }

                polygonNode.setOnMouseClicked(event -> {
                    //System.out.println(coordinates + " : " + box + " " + coordinates.getCube());

                    if (boxClickCallback != null) boxClickCallback.handleEvent(coordinates);
                });

                Double duration = 1000 + xl * Math.random() * 200;
                if (isFirstDrawing) {
                    polygonNode.setVisible(false);
                    new Timeline(new KeyFrame(
                            Duration.millis(duration),
                            ae -> polygonNode.setVisible(true))
                    ).play();
                }

                int entityQuantity = box.getEntities().size();
                if(entityQuantity <= 4) {

                    for (int i = 0; i < entityQuantity; i++) {

                        Entity entity = box.getEntities().get(i);

                        Polygon entityNode = new Polygon();

                        double tmpX = x;
                        double tmpY = y;

                        if(entityQuantity > 1) {
                            if(i == 0 || i == 2) {
                                tmpX = x - HEXA_WIDTH*0.3;
                            } else {
                                tmpX = x + HEXA_WIDTH*0.3;
                            }
                        }

                        if(entityQuantity > 2) {
                            if(i == 0 || i == 1) {
                                tmpY = y - HEXA_WIDTH*0.3;
                            } else {
                                tmpY = y + HEXA_WIDTH*0.3;
                            }
                        }

                        entityNode.toFront();

                        entityNode.getPoints().addAll(tmpX + HEXA_WIDTH / 2 - HEXA_WIDTH/4, tmpY + HEXA_HEIGHT / 2 - HEXA_WIDTH/4,
                                tmpX + HEXA_WIDTH / 2 + HEXA_WIDTH/4, tmpY + HEXA_HEIGHT / 2 - HEXA_WIDTH/4,
                                tmpX + HEXA_WIDTH / 2 + HEXA_WIDTH/4, tmpY + HEXA_HEIGHT / 2 + HEXA_WIDTH/4,
                                tmpX + HEXA_WIDTH / 2 - HEXA_WIDTH/4, tmpY + HEXA_HEIGHT / 2 + HEXA_WIDTH/4);

                        g.getChildren().add(entityNode);

                        if(entity instanceof Unit && entity.getTeam() != null) {

                            switch (UnitType.INFANTRY){

                            }

                            if (entity.getTeam().getName().equals("League")) {
                                entityNode.setStroke(Color.RED);
                                entityNode.setFill(new ImagePattern(imageAvionRouge));
                            } else {
                                entityNode.setStroke(Color.BLUE);
                                entityNode.setFill(new ImagePattern(imageAvionBlue));
                            }
                        }

                        entity.isGraphicallyHighlighted(false);

                        if (isFirstDrawing) {
                            entityNode.setVisible(false);
                            new Timeline(new KeyFrame(
                                    Duration.millis(duration + 200),
                                    ae -> entityNode.setVisible(true))
                            ).play();
                        }

                        entityNode.setOnMouseClicked(event -> {
                            System.out.println(" Click unit : " + coordinates);
                            if (entityClickCallback != null) entityClickCallback.handleEvent(coordinates, entity);
                        });
                    }
                }
            }
        }

        isFirstDrawing = false;

        pane.setContent(g);
    }

    private Color getColorForType(BoxType type) {

        switch(type) {
            case SAND:
                return Color.rgb(209, 178, 40);

            case QUICKSAND:
                return Color.rgb(200, 119, 20);

            case FOREST:
                return Color.rgb(107, 129, 28);

            case MONTAINS:
                return Color.rgb(59, 30, 22);

            case WATER:
                return Color.rgb(105, 142, 142);

            case CITY:

                return Color.rgb(255, 50, 50);

            default:
                return getColorForType(BoxType.WATER);
        }
    }

    public void setOnBoxClickCallback(ICoordinatesListener eventListener) {

        this.boxClickCallback = eventListener;
    }

    public void setOnEntityClickCallback(ICoordinatesEntityListener eventListener) {

        this.entityClickCallback = eventListener;
    }
}
