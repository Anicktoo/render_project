package tools;

public class Vector {
    public double x;
    public double y;
    public double z;

    public Vector(Vector r) {
        this.x = r.x;
        this.y = r.y;
        this.z = r.z;
    }

    public Vector(Vector r, boolean unit) {
        if (unit) {
            double l = Math.sqrt(r.x*r.x + r.y*r.y + r.z*r.z);
            this.x = r.x / l;
            this.y = r.y / l;
            this.z = r.z / l;
        }
        else {
            this.x = r.x;
            this.y = r.y;
            this.z = r.z;
        }
    }

    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector(double x, double y, double z, boolean unit) {
        if (unit) {
            double l = Math.sqrt(x*x + y*y + z*z);
            this.x = x / l;
            this.y = y / l;
            this.z = z / l;
        }
        else {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    public Vector makeUnit() {
        double l = Math.sqrt(x*x + y*y + z*z);
        return new Vector(x/l, y/l, z/l);
    }

    public double getLength() {
        return Math.sqrt(x*x + y*y + z*z);
    }

    public Vector multiply(double r) {
        return new Vector(x * r, y * r, z * r);
    }
    public Vector multiply(Vector r) {
        return new Vector(x * r.x, y * r.y, z * r.z);
    }
    public Vector divide(double r) {
        return new Vector(x / r, y / r, z / r);
    }

    public Vector substract(Vector r) {
        return new Vector(x - r.x, y - r.y, z - r.z);
    }
    public Vector add(Vector r) {
        return new Vector(x + r.x, y + r.y, z + r.z);
    }

    public double scalar(Vector r) {
        return x * r.x + y * r.y + z * r.z;
    }
    public Vector vector(Vector r) {
        double tmpX = y * r.z - z * r.y;
        double tmpY = z*r.x - x*r.z;
        double tmpZ = x*r.y - y*r.x;
        return new Vector(tmpX, tmpY, tmpZ);
    }

    //Сделано под задачу так, что камера может поворачиваться влево-вправо, вверх-вниз, но не может крутиться
    public Vector vectorInNewBasis(Vector norm) {
        double cosA, sinA, cosFi, sinFi;
        if (norm.z == 0) {  //смторит в плоскости XY
            if (norm.x == 0) { //смотрит строго вверх или вниз
                cosA = 1;
                sinA = 0;
                cosFi = 0;
                sinFi = 1;
            }
            else {
                cosA = 0;
                sinA = 1;
                cosFi = Math.abs(norm.x) / Math.sqrt(norm.y * norm.y + norm.x * norm.x);
                sinFi = Math.sqrt(1 - cosFi * cosFi);
            }
        }
        else {
            cosA = norm.z / Math.sqrt(norm.x * norm.x + norm.z * norm.z);
            sinA = Math.sqrt(1 - cosA * cosA);
            cosFi = Math.abs(norm.z) / Math.sqrt(norm.y * norm.y + norm.z * norm.z);
            sinFi = Math.sqrt(1 - cosFi * cosFi);
        }
        if (norm.x < 0)
            sinA *= -1;
        if (norm.y < 0)
            sinFi *= -1;
        return new Vector(this.x * cosA - this.y * sinFi * sinA, this.y * cosFi, -this.x * sinA - this.y * sinFi * cosA);
    }

    public double getCosBetween(Vector vector) {
        return ((x * vector.x + y * vector.y + z * vector.z) /
                (Math.sqrt(x * x + y * y + z * z) * Math.sqrt(vector.x * vector.x + vector.y * vector.y + vector.z * vector.z)));
    }
}
