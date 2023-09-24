package scene.objects;

import scene.Ray;
import scene.optical.Optical;
import tools.*;

public class Sphere extends Geometry {
    private int radius;
    private Vector location;

    public Sphere(Optical opt, Vector location, int radius) {
        super(opt);
        this.location = location;
        this.radius = radius;
    }

    @Override
    public boolean intersect(Ray ray) {
        Vector p_ = ray.getOrigin().substract(location);
        double b = 2 * p_.scalar(ray.getDirection());
        double c = p_.scalar(p_) - radius * radius;
        double D = b * b - 4 * c;

        if (D < 0)
            return false;

        double t1 = (-1 * b - Math.sqrt(D))/2, t2 = (-1 * b + Math.sqrt(D))/2;
        t1 = Math.min(t1, t2);

        if (t1 < ray.getTMIN() || t1 > ray.getTMAX())
            return false;

        ray.setTMAX(t1);
        return true;
    }

    //меняет P, возвращает N
    public Vector getP_N(Ray ray, Vector P) {
        Vector tmp = ray.getDirection().multiply(ray.getTMAX()).add(ray.getOrigin());
        P.x = tmp.x;
        P.y = tmp.y;
        P.z = tmp.z;
        return P.substract(location).makeUnit();
    }
}
