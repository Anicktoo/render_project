package scene;

import tools.Vector;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Camera{
    private Vector location;
    private Vector direction;
    private Screen screen;
    private Vector filter;

    static class Screen{
        private Vector[][] pixels;
        private int height;
        private int width;
        private int distance;
        private double maxL;

        Screen(int d, int w, int h) {
            distance = d;
            width = w;
            height = h;
            pixels = new Vector[width][height];
            maxL = 0; //max освещенность в кадре,
        }
    }

    public Camera(Vector location, Vector direction, int distance, int width, int height) {
        this.location = location;
        this.direction = direction.makeUnit();
        this.screen = new Screen(distance, width, height);
        filter = new Vector(1, 1, 1);
    }

    public Ray getRay(int x, int y) {
        Vector coords = new Vector(x - screen.width / 2.0 + 0.5, screen.height / 2.0 - y - 0.5, 0); //направление к пикселю от центра экрана в базисе экрана
        coords = coords.vectorInNewBasis(direction); //направление к пикселю от центра экрана в базисе системы координат
        coords = coords.add(direction.multiply(screen.distance)).makeUnit(); //направление от камеры к пикселю экрана
        Ray ray = new Ray(location, coords);
        ray.setColor(filter);
        return ray;
    }

    public BufferedImage getImage(int scale) {
        int w = screen.width;
        int h = screen.height;

        BufferedImage img = new BufferedImage(w * scale, h * scale, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < h; i++)
            for (int q = 0; q < w; q++)
                for (int j = 0; j < scale; j++) //делает "большие" пиксели
                    for (int jj = 0; jj < scale; jj++)
                        img.setRGB(q * scale + jj, i * scale + j, vector_to_RGB(screen.pixels[q][i]));
        return img;
    }

    public void setPixel(Vector L, int x, int y) {
        if (L.x > screen.maxL)
            screen.maxL = L.x;
        if (L.y > screen.maxL)
            screen.maxL = L.y;
        if (L.z > screen.maxL)
            screen.maxL = L.z;
        screen.pixels[x][y] = L;
    }


    public int getScreenHeight() {
        return screen.height;
    }
    public int getScreenWidth() {
        return screen.width;
    }

    public void setFilter(Vector filter) {
        this.filter = filter;
    }



    public void setScreen(int distance, int width, int height) {
        screen.height = height;
        screen.width = width;
        screen.distance = distance;
    }

    public void setLocationAndDirection(Vector location, Vector direction) {
        this.location = location;
        this.direction = direction.makeUnit();
    }

    public void nullMaxL() {
        screen.maxL = 0;
    }

    private int vector_to_RGB(Vector c) {
        if (c == null)
            return 0xff << 24;
        int rgb = 0;
//        if (c.x == -1) { //под вопросом, бэкграунд не получается
//            rgb += (int)(background.z * filter.z);
//            rgb += (int)(background.y * filter.y) << 8;
//            rgb += (int)(background.x * filter.x) << 16;
//        }
//        else
        if (screen.maxL != 0) {
            double ed = 255 / screen.maxL;
            rgb += (int) (c.z * ed);
            rgb += (int) (c.y * ed) << 8;
            rgb += (int) (c.x * ed) << 16;
        }
        rgb += 0xff << 24;
        return rgb;
    }
}
