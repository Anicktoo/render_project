package scene.objects;

import scene.Ray;
import scene.optical.Optical;
import tools.Vector;

public class InfPlane extends Geometry{
    private Vector normal;
    private Vector point;

    public InfPlane(Optical opt, Vector point, Vector normal) {
        super(opt);
        this.point = point;
        this.normal = new Vector(normal, true);
    }

    @Override
    public boolean intersect(Ray ray) {
        double d = normal.scalar(point); //-d

        double denom = normal.scalar(ray.getDirection());

        if (denom == 0)
            return false;       //прямая параллельна плоскости

        double t = (d - normal.scalar(ray.getOrigin())) / denom;

        if (t < ray.getTMIN() || t > ray.getTMAX())
            return false;

        ray.setTMAX(t);
        return true;
    }

    //меняет P, возвращает N
    public Vector getP_N(Ray ray, Vector P) {
        Vector tmp = ray.getDirection().multiply(ray.getTMAX()).add(ray.getOrigin());
        P.x = tmp.x;
        P.y = tmp.y;
        P.z = tmp.z;

        if (normal.scalar(ray.getDirection()) > 0)
            return normal.multiply(-1);

        return normal;
    }
}
