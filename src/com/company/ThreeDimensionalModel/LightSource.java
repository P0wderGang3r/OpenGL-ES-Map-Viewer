package com.company.ThreeDimensionalModel;

public class LightSource {

    private double x;
    private double y;
    private double z;

    private double xSpeed;
    private double ySpeed;
    private double zSpeed;

    private final double radius;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double[] getXYZ() {return new double[]{x, y, z};}

    public void moveLightSource() {
        x += xSpeed;
        y += ySpeed;
        z += zSpeed;


        //Неадекватная функция, но я хочу зациклить движение
        if (Math.abs(x) > radius) {
            xSpeed = -xSpeed;
        }

        if (Math.abs(y) > radius) {
            ySpeed = -ySpeed;
        }

        if (Math.abs(z) > radius) {
            zSpeed = -zSpeed;
        }
    }

    public double gouraudLightEquasion(double[] verticleCoords) {
        double lightDensity = (radius - Math.sqrt((
                (x - verticleCoords[0]) * (x - verticleCoords[0])
                        + (y - verticleCoords[1]) * (y - verticleCoords[1])
                        + (z - verticleCoords[2]) * (z - verticleCoords[2])
        ))) / radius;

        return lightDensity > 0 ? lightDensity : 0;
    }

    LightSource(double[] coords, double[] vecSpeed, double radius) {
        this.x = coords[0];
        this.y = coords[1];
        this.z = coords[2];

        this.xSpeed = vecSpeed[0];
        this.ySpeed = vecSpeed[1];
        this.zSpeed = vecSpeed[2];

        this.radius = radius;
    }
}
