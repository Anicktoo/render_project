package scene.lights;

import tools.Vector;

public class LightLambert extends Light{
    protected Vector norm;

    public LightLambert(Vector location, Vector norm, Vector F) {
        super(location, F);
        this.norm = new Vector(norm, true);
    }

    protected Vector getI(Vector vector) {
        double cos = Math.abs(vector.getCosBetween(norm)); //светит в обе стороны
        return new Vector(F.divide(2 * Math.PI).multiply(cos));
    }
}
