package main.fr.epsi.gravarmor.controller;

import javafx.scene.control.ScrollPane;
import main.fr.epsi.gravarmor.model.*;
import main.fr.epsi.gravarmor.model.coordinates.HexaCoordinates;
import main.fr.epsi.gravarmor.model.coordinates.NumeroEquipe;
import main.fr.epsi.gravarmor.model.coordinates.Point;

import java.util.ArrayList;
import java.util.List;

import static main.fr.epsi.gravarmor.model.BoxType.CITY;

public class GameLogic {

    private LandController landController;
    private MenuController menuController;
    private HexaLand land;

    private boolean hasAnimationRunning;

    private Entity selectedEntity;

    private int compteurLeague = 0;
    private int compteurImperial = 0;
    private boolean imperialPlacement = false;
    private boolean leaguePlacement = false;
    private boolean imperialTurn = true;

    private HexaCoordinates centerCoordinates;
    private HexaCoordinates cityCoordinates;

    private Team imperialTeam;
    private Team leagueTeam;

    GameLogic(ScrollPane landPane, MenuController menuController, HexaLand land) {
        this.landController = new LandController(landPane, land);
        this.menuController = menuController;
        this.land = land;

        hasAnimationRunning = false;

        centerCoordinates = new HexaCoordinates(new Point((land.getHeight()-1) /2, (land.getWidth()-1)/2));

        draw();
    }

    public void start() {

        // CREATION DES TEAMS
        List<Unit> listImperial = new ArrayList<>();
        listImperial.add(new Unit(UnitType.INFANTRY));
        listImperial.add(new Unit(UnitType.INFANTRY));
        listImperial.add(new Unit(UnitType.INFANTRY));
        listImperial.add(new Unit(UnitType.TANK));
        listImperial.add(new Unit(UnitType.TANK));
        imperialTeam = new Team("Imperial", listImperial);

        List<Unit> listLeague = new ArrayList<>();
        listLeague.add(new Unit(UnitType.INFANTRY));
        listLeague.add(new Unit(UnitType.INFANTRY));
        listLeague.add(new Unit(UnitType.INFANTRY));
        listLeague.add(new Unit(UnitType.TANK));
        listLeague.add(new Unit(UnitType.TANK));
        leagueTeam = new Team("League", listLeague);


        menuController.log("Veuillez placer la ville dans le centre de la map");
        menuController.setEquipe(NumeroEquipe.EQUIPE_ROUGE);
        menuController.setEntityDescription(null);


        // GESTION DES CLICKS
        menuController.getBoutonPasserLeTour().setOnAction(e -> {
            if(imperialTurn == true){
                imperialTurn = false;
                menuController.setEquipe(NumeroEquipe.EQUIPE_ROUGE);

                for(int i = 0; i < listImperial.size(); i++){
                    listImperial.get(i).reinitMovementPoints();
                }
            } else if (imperialTurn == false){
                imperialTurn = true;
                menuController.setEquipe(NumeroEquipe.EQUIPE_BLEU);

                for(int i = 0; i < listLeague.size(); i++){
                    listLeague.get(i).reinitMovementPoints();
                }
            }
        });

        landController.setOnBoxClickCallback(coordinates -> {

            if(cityCoordinates == null) {

                if(HexaCoordinates.distance(coordinates, centerCoordinates) > 2) {
                    menuController.log("Veuillez placer la ville dans la zone mise en évidence");
                    return;
                }

                cityCoordinates = coordinates;
                land.getBox(coordinates).setType(CITY);
                draw();

                menuController.log("Veuillez placer les 5 pionts de la team IMPERIAL (Bleu)");
                menuController.setEntityDescription(listImperial.get(0));
                menuController.setEquipe(NumeroEquipe.EQUIPE_BLEU);

                imperialPlacement = true;
                return;
            }

            if(hasAnimationRunning) return;

            if (imperialPlacement || leaguePlacement) {
                initGame(coordinates);
                return;
            }

            System.out.println("Click  " + coordinates);

            if (selectedEntity == null) {
                return;
            }

            int distance = HexaCoordinates.distance(selectedEntity.getCoordinates(), coordinates);
            if (selectedEntity instanceof Unit &&
                    ((Unit) selectedEntity).canMove(distance) &&
                    ((imperialTurn && selectedEntity.getTeam() == imperialTeam) || (!imperialTurn && selectedEntity.getTeam() == leagueTeam))) {

                    land.moveEntity(selectedEntity, coordinates);
                    ((Unit) selectedEntity).decreaseMovementPoints(distance);
            }

            menuController.setEntityDescription(null);
            selectedEntity = null;

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
                menuController.log("Veuillez maintenant placer les 5 pionts de la team LEAGUE (Rouge) à distance des pionts adverses");
                menuController.setEntityDescription(leagueTeam.getListEntity().get(0));
                menuController.setEquipe(NumeroEquipe.EQUIPE_ROUGE);
            } else {
                menuController.log("Reste " + (5-compteurImperial) + " pionts à placer");
                menuController.setEntityDescription(imperialTeam.getListEntity().get(compteurImperial));
            }
            draw();
            return;
        }
        else if (leaguePlacement) {
            System.out.println("League place piece : " + compteurLeague);

            for (Unit unit : imperialTeam.getListEntity()) {
                if(HexaCoordinates.distance(unit.getCoordinates(), coordinates) <= 4) {
                    menuController.log("Le piont est trop prêt des pionts adverses");
                    return;
                }
            }

            Entity entity = leagueTeam.getListEntity().get(compteurLeague);
            entity.setTeam(leagueTeam);
            land.addEntity(entity, coordinates);
            compteurLeague++;

            if (compteurLeague == 5) {
                leaguePlacement = false;
                menuController.log("Vous pouvez commencer à jouer");
                menuController.setEntityDescription(null);
                menuController.setEquipe(NumeroEquipe.EQUIPE_BLEU);
            } else {
                menuController.log("Reste " + (5-compteurLeague) + " pionts à placer");
                menuController.setEntityDescription(leagueTeam.getListEntity().get(compteurLeague));
            }

            draw();
            return;
        }
    }

    public void stop() {

    }

    public void draw() {

        if(cityCoordinates == null) {

            HexaCoordinates[] positions = HexaCoordinates.range(centerCoordinates, 2);
            for (HexaCoordinates position : positions) {

                try {
                    LandBox box = land.getBox(position);
                    box.isGraphicallyHighlighted(true);
                } catch (ArrayIndexOutOfBoundsException e) {
                    /**/
                }
            }
        }

        if(leaguePlacement) {
            for (Unit unit: imperialTeam.getListEntity()) {

                HexaCoordinates[] positions = HexaCoordinates.range(unit.getCoordinates(), 4);
                for (HexaCoordinates position : positions) {

                    try {
                        LandBox box = land.getBox(position);
                        box.isGraphicallyHighlighted(true);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        /**/
                    }
                }
            }
        }

        if(selectedEntity != null && selectedEntity instanceof Unit) {

            int movementsPoints = ((Unit) selectedEntity).getMovementPoints();
            if((imperialTurn && !selectedEntity.getTeam().getName().equals("Imperial")) ||
                    (!imperialTurn && !selectedEntity.getTeam().getName().equals("League"))) {
                movementsPoints = 0;
            }

            HexaCoordinates[] positions = HexaCoordinates.range(selectedEntity.getCoordinates(), movementsPoints);
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
