package main.fr.epsi.gravarmor.model;


import main.fr.epsi.gravarmor.model.coordinates.HexaCoordinates;

public class Entity {

    private HexaCoordinates coordinates;

    public Entity() {

    }

    public void setCoordinates(HexaCoordinates coordinates) {

        this.coordinates = coordinates;
    }

    public HexaCoordinates getCoordinates() {

        return coordinates;
    }


}
