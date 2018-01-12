package fr.epsi.gravarmor.model.coordinates;

public class Cube {

    private int x;
    private int y;
    private int z;

    private static Cube DIRECTIONS[]  = {
            new Cube(+1, -1, 0),
            new Cube(+1, 0, -1),
            new Cube(0, +1, -1),
            new Cube(-1, +1, 0),
            new Cube(-1, 0, +1),
            new Cube(0, -1, +1)
    };

    public Cube(int x, int y, int z) {

        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {

        return x;
    }

    public int getY() {

        return y;
    }

    public int getZ() {

        return z;
    }

    static public Cube from(Point point) {

        int x = point.getX();
        int z = point.getY() - (point.getX() - (point.getX()&1))/2;
        int y = -x-z;

        return new Cube(x, y, z);
    }

    private static int lerp(int a, int b, double t) {

        //return (int)(a + (b - a)*t);
        return (int) Math.round(a * (1-t) + b * t);
    }

    public static Cube lerp(Cube a, Cube b, double t) {

        int x = lerp(a.getX(), b.getX(), t);
        int y = lerp(a.getY(), b.getY(), t);
        int z = lerp(a.getZ(), b.getZ(), t);

        if(x+y+z != 0) {
            System.err.println("!= 0 " + a + " " + b + " : " + x + "-" + y + "-" + z);

            //x = -y-z;
        }

        return new Cube(x, y, z);
    }

    private static Cube direction(int direction) {

        return DIRECTIONS[direction];
    }

    public static Cube neighbor(Cube cube, int direction) {

        return add(cube, direction(direction));
    }

    public static Cube add(Cube a, Cube b) {

        return new Cube(
                a.getX() + b.getX(),
                a.getY() + b.getY(),
                a.getZ()+ b.getZ()
        );
    }

    public static Cube round(Cube cube) {

        int rx = Math.round(cube.getX());
        int ry = Math.round(cube.getY());
        int rz = Math.round(cube.getZ());

        int x_diff = Math.abs(rx - cube.getX());
        int y_diff = Math.abs(ry - cube.getY());
        int z_diff = Math.abs(rz - cube.getZ());

        if(x_diff > y_diff && x_diff > z_diff) {

            rx = -ry-rz;
        }
        else if(y_diff > z_diff) {

            ry = -rx-rz;
        }
        else {

            rz = -rx-ry;
        }

        return new Cube(rx, ry, rz);
    }

    @Override
    public String toString() {

        return "Cube(x: " + x + ", y: " + y + ", z: " + z + ")";
    }
}
