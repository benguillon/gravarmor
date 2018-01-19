package main.fr.epsi.gravarmor.model;

public enum UnitType {
    INFANTRY(2,3,2,7,3,WeaponType.MISSILES),
    TANK(4,4,6,4,4,WeaponType.MAGNETIC_BOLTS);

    private int movementPoints;
    private int electronicWarfare;
    private int attackValue;
    private int defenseValue;
    private int maximumRange;
    private WeaponType weaponType;

    UnitType(int movementPoints, int electronicWarfare, int attackValue, int defenseValue, int maximumRange, WeaponType weaponType) {
        this.movementPoints = movementPoints;
        this.electronicWarfare = electronicWarfare;
        this.attackValue = attackValue;
        this.defenseValue = defenseValue;
        this.maximumRange = maximumRange;
        this.weaponType = weaponType;
    }

    public int getMovementPoints() {
        return movementPoints;
    }

    public void setMovementPoints(int movementPoints) {
        this.movementPoints = movementPoints;
    }

    public int getElectronicWarfare() {
        return electronicWarfare;
    }

    public void setElectronicWarfare(int electronicWarfare) {
        this.electronicWarfare = electronicWarfare;
    }

    public int getAttackValue() {
        return attackValue;
    }

    public void setAttackValue(int attackValue) {
        this.attackValue = attackValue;
    }

    public int getDefenseValue() {
        return defenseValue;
    }

    public void setDefenseValue(int defenseValue) {
        this.defenseValue = defenseValue;
    }

    public int getMaximumRange() {
        return maximumRange;
    }

    public void setMaximumRange(int maximumRange) {
        this.maximumRange = maximumRange;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(WeaponType weaponType) {
        this.weaponType = weaponType;
    }
}
