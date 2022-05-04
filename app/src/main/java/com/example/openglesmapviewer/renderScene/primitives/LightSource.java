package com.example.openglesmapviewer.renderScene.primitives;

public class LightSource {

    private float x;
    private float y;
    private float z;

    private float xSpeed;
    private float ySpeed;
    private float zSpeed;

    private final float radius;

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public float[] getXYZ() {return new float[]{x, y, z};}

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

    public float gouraudLightEquasion(float[] verticleCoords) {
        float lightDensity = (radius - (float) Math.sqrt((
                (x - verticleCoords[0]) * (x - verticleCoords[0])
                        + (y - verticleCoords[1]) * (y - verticleCoords[1])
                        + (z - verticleCoords[2]) * (z - verticleCoords[2])
        ))) / radius;

        return lightDensity > 0 ? lightDensity : 0;
    }

    public LightSource(float[] coords, float[] vecSpeed, float radius) {
        this.x = coords[0];
        this.y = coords[1];
        this.z = coords[2];

        this.xSpeed = vecSpeed[0];
        this.ySpeed = vecSpeed[1];
        this.zSpeed = vecSpeed[2];

        this.radius = radius;
    }
}
