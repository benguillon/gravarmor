package main.fr.epsi.gravarmor.model;

public enum BoxType {
    CITY(false, 0),
    OUT(false, 0),
    SAND(true, 0),
    QUICKSAND(true, 2),
    FOREST(true, 1),
    MONTAINS(false, 1),
    HIDDEN(false, 1),
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
