package scene.lights;

import tools.Vector;

public class LightLambertHalf extends LightLambert {
    public LightLambertHalf(Vector location, Vector norm, Vector F) {
        super(location, norm, F);
    }


    //передается вектор К источнику света

    protected Vector getI(Vector vector) {
        double cos = vector.getCosBetween(norm); //светит в одну сторону
        if (cos > 0)
            return new Vector(0, 0, 0);
        return new Vector(F.divide(Math.PI).multiply(-cos));
    }
}
