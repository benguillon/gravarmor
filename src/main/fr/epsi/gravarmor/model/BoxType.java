package main.fr.epsi.gravarmor.model;

public enum BoxType {
    CITY(false, 3),
    OUT(false, 10000),
    SAND(true, 1),
    QUICKSAND(true, 1),
    FOREST(true, 2),
    MONTAINS(false, 3),
    HIDDEN(false, 10000),
    WATER(false, 1);

    private boolean isAvailableForInterraction;
    private int movementPoints;

    BoxType(boolean isAvailableForInterraction, int movementPoints) {

        this.isAvailableForInterraction = isAvailableForInterraction;
        this.movementPoints = movementPoints;
    }

    public boolean isAvailableForInterraction() {

        return isAvailableForInterraction;
    }

    public void setMovementPoints(int movementPoints) {
        this.movementPoints = movementPoints;
    }

    public int getMovementPoints() {
        return movementPoints;
    }

    public static BoxType fromChar(char car) {

        switch(car) {
            case 'S':
                return BoxType.SAND;

            case 'Q':
                return BoxType.QUICKSAND;

            case 'F':
                return BoxType.FOREST;

            case 'M':
                return BoxType.MONTAINS;

            case 'W':
                return BoxType.WATER;

            case 'H':
                return BoxType.HIDDEN;

            default:
                return BoxType.OUT;
        }
    }
}
