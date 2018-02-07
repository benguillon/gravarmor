package main.fr.epsi.gravarmor.model;


import main.fr.epsi.gravarmor.model.coordinates.HexaCoordinates;

public abstract class Entity {

    private Boolean isGraphicallyHighlighted;

    private HexaCoordinates coordinates;

    private Team team;

    public Entity() {
        isGraphicallyHighlighted = false;
    }

    public void setCoordinates(HexaCoordinates coordinates) {

        this.coordinates = coordinates;
    }

    public HexaCoordinates getCoordinates() {

        return coordinates;
    }

    public Boolean isGraphicallyHighlighted() {
        return isGraphicallyHighlighted;
    }

    public void isGraphicallyHighlighted(Boolean isGraphicallyHighlighted) {
        this.isGraphicallyHighlighted = isGraphicallyHighlighted;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
