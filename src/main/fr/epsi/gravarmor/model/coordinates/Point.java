package main.fr.epsi.gravarmor.model.coordinates;

public class Point {

    private int x;
    private int y;

    public Point(int x, int y) {

        this.x = x;
        this.y = y;
    }

    public int getX() {

        return x;
    }

    public int getY() {

        return y;
    }

    static public Point from(Cube cube) {

        int x = cube.getX();
        int y = cube.getZ() + (cube.getX() - (cube.getX()&1))/2;

        return new Point(x, y);
    }

    @Override
    public String toString() {

        return "Point(x: " + x + ", y: " + y + ")";
    }
}
