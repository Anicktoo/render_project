package scene.lights;
import tools.Vector;

//равноинтенсивный источник
public class LightSimple extends Light {
    public LightSimple(Vector location, Vector F) {
        super(location, F);
    }

    protected Vector getI(Vector v) {
        return new Vector(F.divide(4 * Math.PI));
    }
}
