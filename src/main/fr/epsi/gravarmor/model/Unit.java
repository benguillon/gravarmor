package main.fr.epsi.gravarmor.model;


import main.fr.epsi.gravarmor.model.coordinates.HexaCoordinates;

public class Unit extends Entity {
    private boolean actif;
    private boolean destroyed;
    private int movementPoints;
    private UnitType type;

    public Unit(UnitType type) {
        super();
        actif = true;
        destroyed = false;
        movementPoints = type.getMovementPoints();
        this.type = type;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public void reinitMovementPoints() {
        movementPoints = type.getMovementPoints();
    }

    public void decreaseMovementPoints(int points) {
        movementPoints -= points;
        if(movementPoints < 0) movementPoints = 0;
    }

    public int getMovementPoints() {
        return movementPoints;
    }

    public UnitType getType() {
        return type;
    }

    public void setType(UnitType type) {
        this.type = type;
    }

    public String toString(){
        return type.toString();
    }

    public boolean canMove(int distance){

        return movementPoints >= distance;
    }

    public boolean canMoveTo(HexaCoordinates to){

        int distance = HexaCoordinates.distance(getCoordinates(), to);

        return canMove(distance);
    }
}
