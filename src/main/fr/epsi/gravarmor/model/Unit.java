package main.fr.epsi.gravarmor.model;


public class Unit extends Entity {
    private boolean actif;
    private boolean destroyed;
    private int movementPoints;
    private int life;
    private boolean fire;
    private UnitType type;

    public Unit(UnitType type) {
        super();
        actif = true;
        destroyed = false;
        setMovementPoints(type.getMovementPoints());
        setLife(type.getDefenseValue());
        setFire(true);
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
        if(destroyed) {
            setFire(false);
            this.setMovementPoints(0);
        }
    }

    public void reinitMovementPoints() {
        setMovementPoints(type.getMovementPoints());
    }

    public void reinitCanFire() {
        setFire(true);
    }

    public void decreaseMovementPoints(int points) {

        if(type == UnitType.AIRPLANE) points = 9999;

        setMovementPoints(getMovementPoints() - points);
        if(getMovementPoints() < 0) setMovementPoints(0);
    }

    public void decreaseDefensePoints(int points) {
        setLife(getLife() - points);
        if(getLife() < 0) {
            setLife(0);
            setDestroyed(true);
        }
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

    public boolean canFire(int distance){
        boolean canFire = false;
        if(isFire() && !isDestroyed()) {
            canFire = type.getMaximumRange() >= distance;
            setFire(false);
        }
        return canFire;
    }

    public boolean canMove(int points){
        if(isDestroyed()) {
            return false;
        } else {
            return getMovementPoints() >= points;
        }
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public boolean isFire() {
        return fire;
    }

    public void setFire(boolean fire) {
        this.fire = fire;
    }

    public void setMovementPoints(int movementPoints) {
        this.movementPoints = movementPoints;
    }
}
