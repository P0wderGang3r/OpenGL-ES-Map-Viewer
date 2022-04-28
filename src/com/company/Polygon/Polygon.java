package com.company.Polygon;

public class Polygon {
    private int vertex_num = 3;
    double radius = 1;
    int currentDot = 0;
    Dot[] dots;

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setVertex_num(int vertex_num) {
        this.vertex_num = vertex_num;
        dots = new Dot[vertex_num];
    }

    public int getVertex_num() {
        return vertex_num;
    }


/*
    public double[] getOppositeXY(double deltaMoveX, double deltaMoveY) {
        double[] xy = new double[2];
        int localCurrentDot = currentDot;

        localCurrentDot += vertex_num / 2;

        if (localCurrentDot >= vertex_num) {
            localCurrentDot -= vertex_num;
        }

        xy[0] = dots[localCurrentDot].getX() + deltaMoveX;
        xy[1] = dots[localCurrentDot].getY() + deltaMoveY;

        return xy;
    }
 */


    public double[] getOppositeXY(double deltaMoveX, double deltaMoveY) {
        double[] xy = new double[2];

        xy[0] = deltaMoveX;
        xy[1] = deltaMoveY;

        return xy;
    }


    public double[] getNextXY(double deltaMoveX, double deltaMoveY) {
        double[] xy = new double[2];
        currentDot++;

        if (currentDot == vertex_num) {
            currentDot = 0;
        }

        xy[0] = dots[currentDot].getX() + deltaMoveX;
        xy[1] = dots[currentDot].getY() + deltaMoveY;

        return xy;
    }


    public double getX(double deltaMoveX) {
        return dots[currentDot].getX() + deltaMoveX;
    }


    public double getY(double deltaMoveY) {
        return dots[currentDot].getY() + deltaMoveY;
    }


    public double getZ() {
        return 0;
    }


    double[][] rotationMatrix = new double[3][3];
    public void initMatrix(double degDelta) {
        rotationMatrix[0][0] = Math.cos(degDelta);
        rotationMatrix[1][0] = Math.sin(degDelta);
        rotationMatrix[2][0] = 0;

        rotationMatrix[0][1] = -Math.sin(degDelta);
        rotationMatrix[1][1] = Math.cos(degDelta);
        rotationMatrix[2][1] = 0;

        rotationMatrix[0][2] = 0;
        rotationMatrix[1][2] = 0;
        rotationMatrix[2][2] = 1;
    }


    double[] xyr = new double[3];
    public void setXY(double x, double y) {
        xyr[0] = x;
        xyr[1] = y;
        xyr[2] = 1;
    }


    public void setNewXY() {
        double[] xyrBuf = new double[3];

        xyrBuf[0] = xyr[0];
        xyrBuf[1] = xyr[1];
        xyrBuf[2] = xyr[2];

        xyr[0] = 0;
        xyr[1] = 0;
        xyr[2] = 0;

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                xyr[i] += xyrBuf[j] * rotationMatrix[i][j];
            }
    }


    public void initArgs() {
        double degDelta = Math.PI * 2 / vertex_num;
        initMatrix(degDelta);
        setXY(0, radius);

        for (int i = 0; i < vertex_num - 1; i++) {
            dots[i] = new Dot();
            setNewXY();

            dots[i].setX(xyr[0]);
            dots[i].setY(xyr[1]);
        }

        dots[vertex_num - 1] = new Dot();
        dots[vertex_num - 1].setX(0);
        dots[vertex_num - 1].setY(radius);
    }
}
