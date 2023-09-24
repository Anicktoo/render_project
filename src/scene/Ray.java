package scene;

import tools.Vector;

public class Ray {
    public static final double TMINSTANDART = 0.000000001;
    public static final double TMAXSTANDART = 50000;
    private Vector origin;
    private Vector direction;
    private double TMIN = TMINSTANDART;
    private double TMAX = TMAXSTANDART;
    private Vector color;
    private int number;

    public Ray(Vector origin, Vector direction) {
        this.origin = origin;
        this.direction = direction;
        this.color = new Vector(1, 1, 1);
        this.number = 0;
    }

    public Vector getOrigin() {
        return origin;
    }

    public void setOrigin(Vector v) {
        origin = v;
    }

    public Vector getDirection() {
        return direction;
    }

    public void setDirection(Vector v) {
        direction = v;
    }

    public double getTMIN() {
        return TMIN;
    }

    public void setTMIN(double TMIN) {
        this.TMIN = TMIN;
    }

    public double getTMAX() {
        return TMAX;
    }

    public void setTMAX(double TMAX) {
        this.TMAX = TMAX;
    }

    public Vector getColor() {
        return color;
    }

    public void setColor(Vector color) {
        this.color = color;
    }

    public int getNumber() {
        return number;
    }

    public void incNumber() {
        number++;
    }
}
