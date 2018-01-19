package main.fr.epsi.gravarmor.model;


public class Unit extends Entity {
    private boolean actif;
    private boolean destroyed;
    private int usedMovementPoints;
    private UnitType type;

    public Unit(UnitType type) {
        super();
        actif = true;
        destroyed = false;
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

    public int getMovementPoints() {
        return usedMovementPoints;
    }

    public void setMovementPoints(int movementPoints) {
        this.usedMovementPoints = movementPoints;
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

    public void move(LandBox boxD, LandBox boxA){
        int pmRestant = this.type.getMovementPoints() - this.getMovementPoints();
        if (pmRestant >= boxA.getType().getMovementPoints()) {
            boxD.getEntities().remove(this);
            boxA.getEntities().add(this);
        }

    }
}
