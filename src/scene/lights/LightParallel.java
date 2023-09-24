package scene.lights;

import tools.Vector;

public class LightParallel extends Light {
    public LightParallel(Vector location, Vector E) {
        super(location, E);
    }

    @Override
    protected Vector getI(Vector v) {
        return null;
    }

    public Vector getE(Vector N, Vector V) {
        Vector normV = V.makeUnit();
        double cos = normV.scalar(N); //т.к вектора нормированы
        if (cos < 0)
            return new Vector(0, 0, 0);
        return F.multiply(cos);
    }
}
