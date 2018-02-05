package main.fr.epsi.gravarmor.model;


import main.fr.epsi.gravarmor.model.coordinates.HexaCoordinates;

public class Entity {

    private Boolean isSelected;

    private HexaCoordinates coordinates;

    public Entity() {
        isSelected = false;
    }

    public void setCoordinates(HexaCoordinates coordinates) {

        this.coordinates = coordinates;
    }

    public HexaCoordinates getCoordinates() {

        return coordinates;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

}
