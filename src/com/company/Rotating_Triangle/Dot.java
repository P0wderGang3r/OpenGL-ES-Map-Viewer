package com.company.Rotating_Triangle;

public class Dot {
    double[] xyr = new double[3];
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

    public void setXYR(double x, double y, double degDelta) {
        xyr[0] = x;
        xyr[1] = y;
        xyr[2] = 1;
        initMatrix(degDelta);
    }

    public double getX() {
        return xyr[0];
    }

    public double getY() {
        return xyr[1];
    }

    public double getR() {
        return xyr[2];
    }

    public void getNewXY() {
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
}
