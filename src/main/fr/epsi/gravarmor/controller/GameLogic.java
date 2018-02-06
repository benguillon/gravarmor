package main.fr.epsi.gravarmor.controller;

import javafx.scene.control.ScrollPane;
import main.fr.epsi.gravarmor.model.*;

import java.util.ArrayList;
import java.util.List;

public class GameLogic {

    private ScrollPane landPane;
    private LandController landController;
    private HexaLand land;

    private Entity selectedEntity;

    private int compteurLeague = 0;
    private int compteurImperial = 0;
    private boolean leaguePlacement = false;
    private boolean imperialPlacement = true;

    public GameLogic(ScrollPane landPane, HexaLand land) {

        this.landPane = landPane;
        this.landController = new LandController(landPane, land);
        this.land = land;

        draw();
    }

    public void start() {

        // CREATION DES TEAMS
        Unit unit1 = new Unit(UnitType.INFANTRY);
        Unit unit2 = new Unit(UnitType.INFANTRY);
        Unit unit3 = new Unit(UnitType.INFANTRY);
        Unit unit4 = new Unit(UnitType.TANK);
        Unit unit5 = new Unit(UnitType.TANK);
        List<Entity> listImperial = new ArrayList<Entity>();
        listImperial.add(unit1);
        listImperial.add(unit2);
        listImperial.add(unit3);
        listImperial.add(unit4);
        listImperial.add(unit5);
        Team imperial = new Team("Imperial",listImperial);

        Unit unit6 = new Unit(UnitType.INFANTRY);
        Unit unit7 = new Unit(UnitType.INFANTRY);
        Unit unit8 = new Unit(UnitType.INFANTRY);
        Unit unit9 = new Unit(UnitType.TANK);
        Unit unit10 = new Unit(UnitType.TANK);
        List<Entity> listLeague = new ArrayList<Entity>();
        listLeague.add(unit6);
        listLeague.add(unit7);
        listLeague.add(unit8);
        listLeague.add(unit9);
        listLeague.add(unit10);
        Team league = new Team("League",listLeague);

        landController.setOnBoxClickCallback(coordinates -> {

            if(imperialPlacement){
                System.out.println("Imperial place piece : " + compteurImperial);
                listImperial.get(compteurImperial).setTeam(imperial);
                land.placeEntity(listImperial.get(compteurImperial), coordinates);
                compteurImperial ++;
                if(compteurImperial == 5){
                    imperialPlacement = false;
                    leaguePlacement = true;
                }
                draw();
                return;
            }else if(leaguePlacement){
                System.out.println("League place piece : " + compteurLeague);
                listLeague.get(compteurLeague).setTeam(league);
                land.placeEntity(listLeague.get(compteurLeague), coordinates);
                compteurLeague ++;
                if(compteurLeague == 5){
                    leaguePlacement = false;
                }
                draw();
                return;
            }


            System.out.println("Click  " + coordinates);

            if (selectedEntity == null) {

                List<Entity> entities = land.getBox(coordinates).getEntities();
                if (entities.size() > 0) {
                    selectedEntity = entities.get(0);
                    selectedEntity.setSelected(true);
                }
            } else {

                land.moveEntity(selectedEntity, coordinates);
                selectedEntity.setSelected(false);
                    selectedEntity = null;
                }

            draw();
        });
    }

    public void stop() {

    }

    public void draw() {
        landController.drawLand();
    }
}
