package main.fr.epsi.gravarmor.model.coordinates;

public class HexaCoordinates {

    private Point point;

    public HexaCoordinates(Point point) {

        this.point = point;
    }

    public HexaCoordinates(Cube cube) {

        this.point = Point.from(cube);
    }

    public Point getPoint() {

        return point;
    }

    public Cube getCube() {

        return Cube.from(point);
    }

    public boolean equals(HexaCoordinates coordinates) {

        return coordinates.getPoint().getX() == point.getX() && coordinates.getPoint().getY() == point.getY();
    }

    public static int distance(HexaCoordinates from, HexaCoordinates to) {

        Cube fromCube = from.getCube();
        Cube toCube = to.getCube();

        return  Math.max(Math.max(Math.abs(fromCube.getX() - toCube.getX()), Math.abs(fromCube.getY() - toCube.getY())), Math.abs(fromCube.getZ() - toCube.getZ()));
    }

    public static HexaCoordinates[] line(HexaCoordinates from, HexaCoordinates to) {

        int distance = distance(from, to);

        HexaCoordinates result[] = new HexaCoordinates[distance+1];

        for(int i = 0; i <= distance; i++) {

            Cube lerpResult = Cube.lerp(from.getCube(), to.getCube(), 1f/distance * i);
            result[i] = new HexaCoordinates(lerpResult);
        }

        return result;
    }

    public static HexaCoordinates[] range(HexaCoordinates center,int taille) {
        HexaCoordinates resultRange[] = new HexaCoordinates[taille+1];
        int x = center.getCube().getX();
        int y = center.getCube().getY();
        int z = center.getCube().getZ();
        int xmin = x-taille;
        int xmax = x+taille;
        int ymin = y-taille;
        int ymax = y+taille;
        int zmin = z-taille;
        int zmax = z+taille;
        for (int i=x;xmin < i && i < xmax;i++) {
            for (int iy=y;Math.max(ymin,-x-zmax)<iy && iy<Math.min(ymax,-x-zmin);iy++) {
                z = -x-y;
                Cube rangeResult = new Cube(x, y, z);
            }

        }
        return resultRange;
    }

    public String toString() {

        return "HexCoordinates(" + point + ", " + Cube.from(point) + ")";
    }
}
