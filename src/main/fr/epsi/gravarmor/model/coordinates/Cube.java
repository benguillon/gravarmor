package main.fr.epsi.gravarmor.model.coordinates;

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

    public static Cube from(float x, float y, float z) {

        int rx = Math.round(x);
        int ry = Math.round(y);
        int rz = Math.round(z);

        int x_diff = Math.abs((int)(rx - x));
        int y_diff = Math.abs((int)(ry - y));
        int z_diff = Math.abs((int)(rz - z));

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

    private static float lerp(int a, int b, float t) {

        return a * (1-t) + b * t;
    }

    public static Cube lerp(Cube a, Cube b, float t) {

        float x = lerp(a.getX(), b.getX(), t);
        float y = lerp(a.getY(), b.getY(), t);
        float z = lerp(a.getZ(), b.getZ(), t);

        if(x+y+z != 0) {
            System.out.println("Coucou");
        }

        return Cube.from(x, y, z);
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

    @Override
    public String toString() {

        return "Cube(x: " + x + ", y: " + y + ", z: " + z + ")";
    }
}
