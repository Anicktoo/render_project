package scene;

import scene.lights.Light;
import scene.objects.*;
import tools.Vector;

import java.util.LinkedList;

public class Scene {
    private LinkedList<Geometry> objects;
    private LinkedList<Light> lights;
    private Vector background;

    public Scene() {
        objects = new LinkedList<>();
        lights = new LinkedList<>();
        background = new Vector(0, 0, 0);
    }

    public void add(Geometry obj) {
        objects.add(obj);
    }

    public void add(Light light) {
        lights.add(light);
    }

    public LinkedList<Geometry> getGeometry() {
        return objects;
    }

    public LinkedList<Light> getLights() {
        return lights;
    }

    public Vector getBackground() {
        return background;
    }

    public void setBackground(double r, double g, double b) {
        this.background = new Vector(r, g, b);
    }
}
