package main.fr.epsi.gravarmor.model;

public enum WeaponType {
    MISSILES(4, false),
    PARTICLE_BEAM(4, false),
    GATLING_LASERS(4, false),
    MAGNETIC_BOLTS(4, false);

    private int value;
    private boolean multiTarget;

    WeaponType(int value, boolean multiTarget) {
        this.value = value;
        this.multiTarget = multiTarget;
    }
}
