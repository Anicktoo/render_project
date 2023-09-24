import scene.Scene;
import scene.Camera;
import scene.objects.Geometry;
import scene.Ray;
import scene.lights.Light;
import scene.optical.Optical;
import tools.Vector;

import java.util.LinkedList;

public class Renderer {
    private Scene scene;
    private Camera camera;
    private int maxTraceNumber;

    public Renderer(Scene scene, Camera camera, int maxTraceNumber) {
        this.camera = camera;
        this.scene = scene;
        this.maxTraceNumber = maxTraceNumber;
    }

    public void render() {
        camera.nullMaxL();
        for (int i = 0; i < camera.getScreenHeight(); i++) { //y
            for (int q = 0; q < camera.getScreenWidth(); q++) { //x
                camera.setPixel(sendRay(camera.getRay(q, i)), q, i);
            }
        }
    }

    private Vector sendRay(Ray ray) {
        Geometry closest;
        LinkedList<Geometry> objects = scene.getGeometry();

        closest = closestIntersection(ray, objects);

        if (closest == null) {
            return new Vector(scene.getBackground().multiply(ray.getColor()));
        }

        LinkedList<Light> lights = scene.getLights();                                   //все источники света
        Vector P = new Vector(0, 0, 0);                                         //координата точки
        Vector N = closest.getP_N(ray, P);                                               //нормаль объекта в этой точке
        Vector V;                                                                       //вектор по направлению от точки к источнику света
        Vector E;                                                                       //освещенность
        Vector L = new Vector(0, 0, 0);                                        //яркость
        Optical opt = closest.getOpt();                                                 //оптичекие сво-ва ближайшего пересеченного объекта

        for (Light l : lights) {
            V = l.getV(P);
            Ray shadowRay = new Ray(new Vector(P), new Vector(V).makeUnit());
            shadowRay.setTMAX(V.getLength());
            shadowRay.setTMIN(Ray.TMINSTANDART);
            if (closestIntersection(shadowRay, objects) == null) {
                E = l.getE(N, V);
                L = L.add(opt.getL(ray, N, V, E));                         //если пересечений нет (ничего не затеняет) - добавляем освещенность и яркость
            }
        }

        L = L.multiply(ray.getColor()); //умножаем на цвет луча

        Vector ks = opt.getKs();
        if ((ks.x == 0 && ks.y == 0 && ks.z == 0) || ray.getNumber() == maxTraceNumber)
            return L;

        Vector reflection = ray.getDirection().substract(N.multiply(N.scalar(ray.getDirection()) * 2)).makeUnit(); //Отраженный вектор
        ray.setColor(ray.getColor().multiply(ks));
        ray.setOrigin(P);
        ray.setDirection(reflection);
        ray.incNumber();
        ray.setTMAX(Ray.TMAXSTANDART);
        Vector tracedL = sendRay(ray);

        L = L.add(tracedL);
        return L;
    }

    private Geometry closestIntersection(Ray ray, LinkedList<Geometry> objects) {
        Geometry closest = null;

        for (Geometry g : objects) {
            if (g.intersect(ray)) {
                closest = g;
            }
        }

        return closest;
    }
}
