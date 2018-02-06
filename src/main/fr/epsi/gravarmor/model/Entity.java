package main.fr.epsi.gravarmor.model;


import main.fr.epsi.gravarmor.model.coordinates.HexaCoordinates;

public abstract class Entity {

    private Boolean isSelected;

    private HexaCoordinates coordinates;

    private Team team;

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

    public void isSelected(Boolean selected) {
        isSelected = selected;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
