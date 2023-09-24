package scene.objects;

import scene.Ray;
import scene.optical.Optical;
import tools.Vector;

public abstract class Geometry {
    private Optical opt;

    public Geometry(Optical opt) {
        this.opt = opt;
    }

    public boolean intersect(Ray ray) {
        return false;
    }

    public Vector getN(Vector P) {
        return null;
    }

    //меняет P, возвращает N
    public Vector getP_N(Ray ray, Vector P) {
        return null;
    }

    public Optical getOpt() {
        return opt;
    }
}
