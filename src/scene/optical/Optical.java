package scene.optical;

import scene.Ray;
import tools.Vector;

public class Optical {
    private Vector kd;
    private Vector kp;
    private Vector kbp;
    private Vector ks;
    private double alpha;

    public Optical(Vector kd, Vector kp, Vector kbp, Vector ks, double alpha) {
        this.alpha = alpha;
        this.kbp = kbp;
        this.kd = kd;
        this.kp = kp;
        this.ks = ks;
    }


    public Vector getL(Ray ray, Vector N, Vector V, Vector E) {
        Vector L = kd;
        L = L.add(getKp(ray, N, V));
        L = L.add(getKbp(ray, N, V));
        return L.multiply(1 / Math.PI).multiply(E);
    }

    private Vector getKbp(Ray ray, Vector N, Vector V) {
        Vector H = V.add(ray.getDirection().multiply(-1)).makeUnit();

        double cos = H.scalar(N); //косинус между средним вектором и нормалью

        if (cos < 0)
            return new Vector(0, 0, 0);

        return kbp.multiply(Math.pow(cos, alpha));
    }

    private Vector getKp(Ray ray, Vector N, Vector V) {

        Vector b = V.add(N.multiply(N.scalar(V) * -2)).makeUnit(); //Отраженный вектор (* -1)

        double cos = ray.getDirection().scalar(b);  //косинус угла между отраженным и лучем от камеры

        if (cos < 0)
            return new Vector(0, 0, 0);

        return kp.multiply(Math.pow(cos, alpha));
    }

    public Vector getKs() {
        return ks;
    }
}
