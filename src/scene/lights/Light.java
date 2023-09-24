package scene.lights;
import tools.Vector;


public abstract class Light {
    private Vector location;
    protected Vector F; //поток света

    Light(Vector location, Vector F) {
        this.location = location;
        this.F = F;
    }

    protected abstract Vector getI(Vector v);

    //считает освещенность по формуле E = I/R^2 * cosFi, где I - сила света (интенсивность), R - расстояние от точки до источника, Fi угол между нормалью и направлением к источнику света
    public Vector getE(Vector N, Vector V) {
        double R = V.scalar(V);
        Vector normV = V.makeUnit();
        double cos = normV.scalar(N);
        if (cos < 0)
            return new Vector(0, 0, 0);
        Vector E = getI(normV);
        E = E.divide(R).multiply(cos);
        return E;
    }

    public Vector getV(Vector P) {
        return location.substract(P);
    }
}
