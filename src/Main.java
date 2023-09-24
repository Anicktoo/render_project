import scene.Scene;
import scene.lights.*;
import scene.Camera;
import scene.objects.InfPlane;
import scene.objects.Sphere;
import scene.objects.Triangle;
import scene.optical.Optical;
import tools.Vector;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("Render");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocation(0,0);

        Scene scene = new Scene();
        Camera camera = new Camera(new Vector(0,0,0),new Vector(0, 0, 1), 400, 800, 800);

        scene.setBackground(135, 206, 235);
//        scene.setBackground(0.0001, 0.0001, 0.0001);
//        scene.setBackground(0, 0, 0);
//        camera.setFilter(new Vector(0.32, 0.23, 0.92));
      //  camera.setFilter(new Vector(1, 1, 1));

        Optical opt = new Optical(new Vector(0.2, 0.2, 0.8), new Vector(0, 0, 0), new Vector(0, 0, 0), new Vector(0 ,0 ,0), 1);
        Optical opt1 = new Optical(new Vector(0.3, 0.7, 0.2), new Vector(0.1, 0.8, 0.1), new Vector(0, 0, 0), new Vector(0 ,0 ,0), 5);
        Optical opt2 = new Optical(new Vector(0.6, 0.3, 0.3), new Vector(0, 0, 0), new Vector(0.9, 0.9, 0.1), new Vector(0 ,0 ,0), 30);
        Optical opt3 = new Optical(new Vector(0.4, 0.4, 0.4), new Vector(0, 0, 0), new Vector(0.7, 0.7, 0.7), new Vector(0.5 ,0.5 ,0.5), 15);
        Optical opt4 = new Optical(new Vector(0,0,0), new Vector(0, 0, 0), new Vector(0,0,0), new Vector(1 ,1 ,1), 15);


        scene.add(new Sphere(opt4, new Vector(0, 0 , 420), 150));
        scene.add(new Sphere(opt2, new Vector(200, -100 , 300), 60));
        scene.add(new Sphere(opt1, new Vector(100, 180, 100), 80));
        scene.add(new InfPlane(opt2, new Vector(0, -300, 0), new Vector(0, 1, 0)));

//        scene.add(new Triangle(new Vector(-100, -290, 650),new Vector(-300, -290, 450),new Vector(-300, 290, 450), opt4));
//        scene.add(new Triangle(new Vector(-100, -290, 650),new Vector(-100, 290, 650),new Vector(-300, 290, 450), opt4));

//        scene.add(new Triangle(new Vector(-105, -300, 655),new Vector(-305, -295, 455),new Vector(-305, 300, 455), opt2));
//        scene.add(new Triangle(new Vector(-105, -300, 655),new Vector(-105, 300, 655),new Vector(-305, 300, 455), opt2));


//        scene.add(new LightSimple(new Vector(400, 400, 800), new Vector(1840, 1000, 1800)));
//        scene.add(new LightSimple(new Vector(0, 0, 0), new Vector(1540, 1540, 1540))); //из 000 координаты

//          scene.add(new LightSimple(new Vector(400, 0, 100), new Vector(10,  10, 10))); //слева сверху от камеры координаты
          scene.add(new LightParallel(new Vector(-4000, 4000, 200), new Vector(90000, 85000, 70000)));

//        scene.add(new LightLambert(new Vector(0, -200 , 200), new Vector(0, 0, 1), new Vector(2000, 2000, 2000))); //ламбертовский, перед шаром, нормалью вправо
//        scene.add(new LightLambertHalf(new Vector(0, -200 , 200), new Vector(0, 0, 1), new Vector(2000, 2000, 1))); //ламбертовский в одну сторону, перед шаром, нормалью вправо

        Renderer renderer = new Renderer(scene, camera, 1);
        int scale = 1;

        renderer.render();
        BufferedImage img = camera.getImage(scale);
        ImageIcon imageIcon = new ImageIcon(img);
        JLabel jl = new JLabel(imageIcon);

        frame.add(jl);
        frame.pack();
        frame.setVisible(true);

        while (true)
            makeCoolRotation(jl, imageIcon, renderer, camera, 100, scale);
    }

    private static void makeCoolRotation(JLabel jl, ImageIcon imageIcon, Renderer renderer, Camera camera, int points, int scale) {
        double x, z, angle;
        angle = Math.PI * 2 / points;
        Vector loc = new Vector(0, 0, 0);
        Vector dir = new Vector(0 ,0,1);
        for (int i = 0; i < points; i++) {
            x = Math.sin(angle * i + Math.PI) * 400;
            z = Math.cos(angle * i + Math.PI) * 400 + 400;
            loc.x = x;
            loc.z = z;
            dir.x = loc.x * -1;
            dir.z = 400 - loc.z;
            camera.setLocationAndDirection(loc, dir);
            renderer.render();
            imageIcon.setImage(camera.getImage(scale));
            jl.updateUI();
        }
    }
}
