package main.fr.epsi.gravarmor.controller;

import javafx.scene.control.ScrollPane;
import main.fr.epsi.gravarmor.model.*;
import main.fr.epsi.gravarmor.model.coordinates.HexaCoordinates;

import java.util.ArrayList;
import java.util.List;

public class GameLogic {

    private LandController landController;
    private MenuController menuController;
    private HexaLand land;

    private boolean hasAnimationRunning;

    private Entity selectedEntity;

    private int compteurLeague = 0;
    private int compteurImperial = 0;
    private boolean leaguePlacement = false;
    private boolean imperialPlacement = true;

    private Team imperialTeam;
    private Team leagueTeam;

    GameLogic(ScrollPane landPane, MenuController menuController, HexaLand land) {
        this.landController = new LandController(landPane, land);
        this.menuController = menuController;
        this.land = land;

        hasAnimationRunning = false;

        draw();
    }

    public void start() {

        // CREATION DES TEAMS
        List<Entity> listImperial = new ArrayList<>();
        listImperial.add(new Unit(UnitType.INFANTRY));
        listImperial.add(new Unit(UnitType.INFANTRY));
        listImperial.add(new Unit(UnitType.INFANTRY));
        listImperial.add(new Unit(UnitType.TANK));
        listImperial.add(new Unit(UnitType.TANK));
        imperialTeam = new Team("Imperial", listImperial);

        List<Entity> listLeague = new ArrayList<>();
        listLeague.add(new Unit(UnitType.INFANTRY));
        listLeague.add(new Unit(UnitType.INFANTRY));
        listLeague.add(new Unit(UnitType.INFANTRY));
        listLeague.add(new Unit(UnitType.TANK));
        listLeague.add(new Unit(UnitType.TANK));
        leagueTeam = new Team("League", listLeague);

        menuController.log("Veuillez placer 5 points de la team IMPERIAL (Bleu)");
        menuController.setEntityDescription(listImperial.get(0));


        // GESTION DES CLICKS
        landController.setOnBoxClickCallback(coordinates -> {

            if(hasAnimationRunning) return;

            if (imperialPlacement || leaguePlacement) {
                initGame(coordinates);
                return;
            }

            System.out.println("Click  " + coordinates);

            if (selectedEntity == null) {
                return;
            }

            if (selectedEntity instanceof Unit) {

                if (((Unit) selectedEntity).canMoveTo(coordinates)) {
                    land.moveEntity(selectedEntity, coordinates);
                }

                selectedEntity = null;
                menuController.setEntityDescription(null);
            }

            draw();
        });

        landController.setOnEntityClickCallback((coordinates, entity) -> {

            if(hasAnimationRunning) return;

            if (imperialPlacement || leaguePlacement) {
                initGame(coordinates);
                return;
            }

            System.out.println("Click on Entity " + coordinates + " : " + entity);

            selectedEntity = entity;
            menuController.setEntityDescription(selectedEntity);

            draw();
        });

        draw();
    }

    private void initGame(HexaCoordinates coordinates) {

        if (imperialPlacement) {
            System.out.println("Imperial place piece : " + compteurImperial);

            Entity entity = imperialTeam.getListEntity().get(compteurImperial);
            entity.setTeam(imperialTeam);
            land.addEntity(entity, coordinates);
            compteurImperial++;

            if (compteurImperial == 5) {
                imperialPlacement = false;
                leaguePlacement = true;
                menuController.log("Veuillez placer 5 points de la team LEAGUE (Rouge)");
                menuController.setEntityDescription(leagueTeam.getListEntity().get(0));
            } else {
                menuController.log("Veuillez placer " + (5-compteurImperial) + " points de la team IMPERIAL (Bleu)");
                menuController.setEntityDescription(imperialTeam.getListEntity().get(compteurImperial));
            }

            draw();
            return;
        }
        else if (leaguePlacement) {
            System.out.println("League place piece : " + compteurLeague);

            Entity entity = leagueTeam.getListEntity().get(compteurLeague);
            entity.setTeam(leagueTeam);
            land.addEntity(entity, coordinates);
            compteurLeague++;

            if (compteurLeague == 5) {
                leaguePlacement = false;
                menuController.log("Vous pouvez commencer à jouer");
                menuController.setEntityDescription(null);
            } else {
                menuController.log("Veuillez placer " + (5-compteurLeague) + " points de la team LEAGUE (Rouge)");
                menuController.setEntityDescription(leagueTeam.getListEntity().get(compteurLeague));
            }

            draw();
            return;
        }
    }

    public void stop() {

    }

    private void draw() {

        if(selectedEntity != null) {
            HexaCoordinates[] positions = HexaCoordinates.range(selectedEntity.getCoordinates(), ((Unit) selectedEntity).getType().getMovementPoints());
            for (HexaCoordinates position : positions) {

                try {
                    LandBox box = land.getBox(position);
                    box.isGraphicallyHighlighted(true);
                } catch (ArrayIndexOutOfBoundsException e) {
                    /**/
                }
            }

            selectedEntity.isGraphicallyHighlighted(true);
        }

        landController.drawLand();
    }
}
