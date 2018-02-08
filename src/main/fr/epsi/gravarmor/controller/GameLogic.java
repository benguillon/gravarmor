package main.fr.epsi.gravarmor.controller;

import javafx.scene.control.ScrollPane;
import main.fr.epsi.gravarmor.model.*;
import main.fr.epsi.gravarmor.model.coordinates.HexaCoordinates;
import main.fr.epsi.gravarmor.model.coordinates.NumeroEquipe;
import main.fr.epsi.gravarmor.model.coordinates.Point;

import java.util.ArrayList;
import java.util.List;

import static main.fr.epsi.gravarmor.model.BoxType.CITY;
import static main.fr.epsi.gravarmor.model.BoxType.OUT;
import static main.fr.epsi.gravarmor.model.GameMode.*;

public class GameLogic {

    private LandController landController;
    private MenuController menuController;
    private HexaLand land;

    private boolean hasAnimationRunning;

    private Entity selectedEntity;
    private Entity selectedFire;

    private int compteurLeague = 0;
    private int compteurImperial = 0;
    private boolean imperialPlacement = false;
    private boolean leaguePlacement = false;
    private boolean imperialTurn = true;

    private HexaCoordinates centerCoordinates;
    private HexaCoordinates cityCoordinates;

    private Team imperialTeam;
    private Team leagueTeam;

    private GameMode gameMode;

    private int nbTours;

    GameLogic(ScrollPane landPane, MenuController menuController, HexaLand land) {
        this.landController = new LandController(landPane, land);
        this.menuController = menuController;
        this.land = land;

        centerCoordinates = new HexaCoordinates(new Point((land.getHeight()-1) /2, (land.getWidth()-1)/2));
    }

    public void start() {

        this.gameMode = GameMode.SET_CITY;
        hasAnimationRunning = false;
        nbTours = 1;

        // CREATION DES TEAMS
        List<Unit> listImperial = new ArrayList<>();
        listImperial.add(new Unit(UnitType.INFANTRY));
        listImperial.add(new Unit(UnitType.INFANTRY));
        listImperial.add(new Unit(UnitType.INFANTRY));
        listImperial.add(new Unit(UnitType.TANK));
        listImperial.add(new Unit(UnitType.TANK));
        listImperial.add(new Unit(UnitType.AIRPLANE));
        imperialTeam = new Team("Imperial", listImperial);

        List<Unit> listLeague = new ArrayList<>();
        listLeague.add(new Unit(UnitType.INFANTRY));
        listLeague.add(new Unit(UnitType.INFANTRY));
        listLeague.add(new Unit(UnitType.INFANTRY));
        listLeague.add(new Unit(UnitType.TANK));
        listLeague.add(new Unit(UnitType.TANK));
        listLeague.add(new Unit(UnitType.AIRPLANE));
        leagueTeam = new Team("League", listLeague);


        menuController.log("Veuillez placer la ville dans le centre de la map");
        menuController.setEquipe(NumeroEquipe.EQUIPE_BLEU);
        menuController.setEntityDescription(null);


        // GESTION DES CLICKS
        menuController.getBoutonPasserLeTour().setOnAction(e -> {

            if(gameMode == SET_UNIT || gameMode == SET_CITY) {
                return;
            }

            if(imperialTurn){
                imperialTurn = false;
                menuController.setEquipe(NumeroEquipe.EQUIPE_ROUGE);

                for (Unit aListImperial : listImperial) {
                    aListImperial.reinitMovementPoints();
                    aListImperial.reinitCanFire();
                }
            }
            else {
                imperialTurn = true;
                menuController.setEquipe(NumeroEquipe.EQUIPE_BLEU);

                for (Unit aListLeague : listLeague) {
                    aListLeague.reinitMovementPoints();
                    aListLeague.reinitCanFire();
                }

                nbTours++;
            }

            if(nbTours >= 10) {
                menuController.log("LA PARTIE EST TERMINEE");
                draw();
                return;
            }

            menuController.log("TOUR N°" + nbTours + "-" + ((imperialTurn)? "1": "2") + " : C'est au tour de l'équie " + ((imperialTurn)? "Imperial (bleu)" : "League (rouge)"));

            selectedEntity = null;
            draw();
        });
        menuController.getBoutonPasserLeTour().setDisable(true);

        menuController.getBoutonChangerMode().setOnAction(e -> {

            if(gameMode == MOVE) {
                gameMode = FIRE;
                menuController.getBoutonChangerMode().setText("Passer en mode tire (F4)");
                menuController.log("l'équie Imperial (rouge) peut tirer Vous pouvez maintenant tirer avec les unités");

                menuController.setEquipe(NumeroEquipe.EQUIPE_ROUGE);
            } else if(gameMode == FIRE) {
                gameMode = MOVE;
                menuController.getBoutonChangerMode().setText("Passer en mode déplacement (F4)");
                menuController.log("l'équie League peut se déplacer (bleu) Vous pouvez maintenant déplacer les unités");

                menuController.setEquipe(NumeroEquipe.EQUIPE_BLEU);
            }

            draw();
        });
        menuController.getBoutonChangerMode().setDisable(true);

        landController.setOnBoxClickCallback(coordinates -> {

            if(gameMode == GameMode.SET_CITY) {
                setCity(coordinates);
                return;
            }
            if (gameMode == GameMode.SET_UNIT) {
                setUnit(coordinates);
                return;
            }

            if(hasAnimationRunning) return;


            System.out.println("Click  " + coordinates);

            if (selectedEntity == null) {
                return;
            }

            if(gameMode == MOVE) {
                int nbMovementsPoints = land.nbPoints(selectedEntity.getCoordinates(), coordinates);
                System.out.println("Points -> " + nbMovementsPoints);
                if (selectedEntity instanceof Unit &&
                        ((Unit) selectedEntity).canMove(nbMovementsPoints) &&
                        ((imperialTurn && selectedEntity.getTeam() == imperialTeam) || (!imperialTurn && selectedEntity.getTeam() == leagueTeam))) {

                    land.moveEntity(selectedEntity, coordinates);
                    ((Unit) selectedEntity).decreaseMovementPoints(nbMovementsPoints);
                } else {
                    menuController.log("Déplacement impossible");
                }
            }

            if(gameMode == FIRE && selectedFire != null) {
                menuController.log("Tire impossible");
            }

            menuController.setEntityDescription(null);
            selectedEntity = null;
            selectedFire = null;

            draw();
        });

        landController.setOnEntityClickCallback((coordinates, entity) -> {

            if (gameMode == GameMode.SET_UNIT) {
                setUnit(coordinates);
                return;
            }

            if(hasAnimationRunning) return;

            System.out.println("Click on Entity " + coordinates + " : " + entity);

            selectedEntity = entity;
            menuController.setEntityDescription(selectedEntity);

            if(gameMode == FIRE) {
                if (selectedEntity instanceof Unit) {
                    if ((imperialTurn && selectedEntity.getTeam() == leagueTeam) || (!imperialTurn && selectedEntity.getTeam() == imperialTeam)) {
                        selectedFire = selectedEntity;
                    } else {
                        if (selectedFire != null) {
                            int distanceShoot = HexaCoordinates.distance(selectedFire.getCoordinates(), selectedEntity.getCoordinates());
                            if (((Unit) selectedFire).canFire(distanceShoot)) {
                                menuController.log("Tire effectué");
                                ((Unit) selectedEntity).decreaseDefensePoints(1);
                                selectedFire = null;
                            } else {
                                menuController.log("Tire impossible");
                            }
                        }
                    }
                }
            }

            draw();
        });

        draw();
    }

    private void setCity(HexaCoordinates coordinates) {

        if(HexaCoordinates.distance(coordinates, centerCoordinates) > 2) {
            menuController.log("Veuillez placer la ville dans la zone mise en évidence");
            return;
        }

        land.getBox(coordinates).setType(CITY);
        draw();

        menuController.log("Veuillez placer les " + imperialTeam.getListEntity().size() + " unités de la team Imperial (Bleu)");
        menuController.setEntityDescription(imperialTeam.getListEntity().get(0));
        menuController.setEquipe(NumeroEquipe.EQUIPE_BLEU);

        imperialPlacement = true;

        gameMode = GameMode.SET_UNIT;
        draw();
        return;
    }

    private void setUnit(HexaCoordinates coordinates) {

        if (imperialPlacement) {
            System.out.println("Imperial place piece : " + compteurImperial);

            Entity entity = imperialTeam.getListEntity().get(compteurImperial);
            entity.setTeam(imperialTeam);
            land.addEntity(entity, coordinates);
            compteurImperial++;

            if (compteurImperial == imperialTeam.getListEntity().size()) {
                imperialPlacement = false;
                leaguePlacement = true;
                menuController.log("Veuillez maintenant placer les " + leagueTeam.getListEntity().size() + " unités de la team League (Rouge) à distance des unités adverses");
                menuController.setEntityDescription(leagueTeam.getListEntity().get(0));
                menuController.setEquipe(NumeroEquipe.EQUIPE_ROUGE);
            } else {
                menuController.log("Il reste " + (imperialTeam.getListEntity().size()-compteurImperial) + " unité(s) à placer");
                menuController.setEntityDescription(imperialTeam.getListEntity().get(compteurImperial));
            }
            draw();
            return;
        }
        else if (leaguePlacement) {
            System.out.println("League place piece : " + compteurLeague);

            for (Unit unit : imperialTeam.getListEntity()) {
                if(HexaCoordinates.distance(unit.getCoordinates(), coordinates) <= 4) {
                    menuController.log("L'unité est trop proche des unités adverses");
                    return;
                }
            }

            Entity entity = leagueTeam.getListEntity().get(compteurLeague);
            entity.setTeam(leagueTeam);
            land.addEntity(entity, coordinates);
            compteurLeague++;

            if (compteurLeague == leagueTeam.getListEntity().size()) {
                leaguePlacement = false;
                menuController.log("-----");
                menuController.log("TOUR N°1-1 : L'équipe Imperial (bleu) commence\nElle peut déplacer ses unités et les faire tirer sur les unités adverses");
                menuController.getBoutonPasserLeTour().setDisable(false);
                menuController.getBoutonChangerMode().setDisable(false);
                gameMode = MOVE;
                menuController.setEntityDescription(null);
                menuController.setEquipe(NumeroEquipe.EQUIPE_BLEU);
            } else {
                menuController.log("Il reste " + (leagueTeam.getListEntity().size()-compteurLeague) + " unité(s) à placer");
                menuController.setEntityDescription(leagueTeam.getListEntity().get(compteurLeague));
            }

            draw();
            return;
        }
    }

    public void stop() {

    }

    public void draw() {

        if(gameMode == SET_CITY) {

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

        if(gameMode == SET_UNIT && leaguePlacement) {
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

        if(gameMode == MOVE && selectedEntity != null && selectedEntity instanceof Unit) {

            int movementsPoints = ((Unit) selectedEntity).getMovementPoints();
            if((imperialTurn && !selectedEntity.getTeam().getName().equals("Imperial")) ||
                    (!imperialTurn && !selectedEntity.getTeam().getName().equals("League"))) {
                movementsPoints = 0;
            }

            if(movementsPoints < 9999) {

                HexaCoordinates[] positions = HexaCoordinates.range(selectedEntity.getCoordinates(), movementsPoints);
                for (HexaCoordinates position : positions) {

                    try {
                        LandBox box = land.getBox(position);

                        if(((Unit) selectedEntity).canMove(land.nbPoints(position, selectedEntity.getCoordinates())) && box.getType() != OUT) {

                                box.isGraphicallyHighlighted(true);
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        /**/
                    }
                }

            } else {

                for(int y = 0; y < HexaLand.getHeight(); y++) {

                    for (int x = 0; x < HexaLand.getWidth(); x++) {

                        HexaCoordinates coordinates = new HexaCoordinates(new Point(x, y));
                        LandBox box = land.getBox(coordinates);

                        if(((Unit) selectedEntity).canMove(land.nbPoints(coordinates, selectedEntity.getCoordinates())) && box.getType() != OUT) {
                            box.isGraphicallyHighlighted(true);
                        }
                    }
                }
            }
        }

        if(gameMode == FIRE && selectedEntity != null && selectedEntity instanceof Unit) {

            int firepoint = ((Unit) selectedEntity).getType().getMaximumRange();
            if((imperialTurn && selectedEntity.getTeam().getName().equals("Imperial")) ||
                    (!imperialTurn && selectedEntity.getTeam().getName().equals("League"))) {
                firepoint = 0;
            }

            HexaCoordinates[] positions = HexaCoordinates.range(selectedEntity.getCoordinates(), firepoint);
            for (HexaCoordinates position : positions) {

                try {
                    LandBox box = land.getBox(position);
                    box.isGraphicallyHighlighted(true);
                } catch (ArrayIndexOutOfBoundsException e) {
                    /**/
                }
            }
        }

        if(selectedEntity != null) {
            selectedEntity.isGraphicallyHighlighted(true);
        } else if(gameMode == MOVE || gameMode == FIRE) {

            List<Unit> unitList;
            if(imperialTurn) {
                unitList = imperialTeam.getListEntity();
            } else {
                unitList = leagueTeam.getListEntity();
            }

            for (Unit unit: unitList) {
                unit.isGraphicallyHighlighted(true);
            }
        }

        landController.drawLand();
    }
}
