package scene.objects;

import scene.Ray;
import scene.optical.Optical;
import tools.Vector;

public class Triangle extends Geometry {
    private Vector p1;
    private Vector p2;
    private Vector p3;
    private Vector normal;


    public Triangle(Vector p1, Vector p2, Vector p3,  Optical opt) {
        super(opt);
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        normal = p2.substract(p1).vector(p3.substract(p1)).makeUnit();
    }

    public boolean intersect(Ray ray) {
        Vector N = normal;

        double d = N.scalar(p1); //-d

        double denom = N.scalar(ray.getDirection());

        if (denom == 0)
            return false;       //прямая параллельна плоскости

        double t = (d - N.scalar(ray.getOrigin())) / denom;

        if (t < ray.getTMIN() || t > ray.getTMAX())
            return false;

        Vector P = ray.getDirection().multiply(t).add(ray.getOrigin());     //координата точки (!)

        /*Способ, которым мы воспользуемся, опирается на то, что сумма внутренних углов вида вершина-точка-вершина
        равна 2pi, если точка внутри треугольника. Для точки вне треугольника эта сумма будет меньше.
        Очевидно, сумма берется для одной точки - точки пересечения линии и плоскости и по всем сочетаниям вершин*/

        Vector pa1 = p1.substract(P).makeUnit();
        Vector pa2 = p2.substract(P).makeUnit();
        Vector pa3 = p3.substract(P).makeUnit();

        double a1 = pa1.scalar(pa2);
        double a2 = pa2.scalar(pa3);
        double a3 = pa3.scalar(pa1);
        double total = Math.acos(a1) + Math.acos(a2) + Math.acos(a3);
        double error = 0.000000001;

        if (Math.abs(total - Math.PI * 2) > error)
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
