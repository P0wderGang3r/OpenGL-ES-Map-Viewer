package com.company.ThreeDimensionalModel;

public class PhongGlossTexture {
    private final float[] pixels;
    private final int width = 1000;
    private final int height = 1000;

    private float lastCorner = (float) (0.5);
    private final double[][][] corners = new double[2][3][2];


    //---------------------Get-теры------------------------
    public float[] getPixels() {
        return pixels;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void resetPixels() {
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++) {
                setPixel(pixels, i, j,
                        0,
                        0,
                        0
                );
            }

    }

    public float[] computeShading(double[] lightSource, double[][] polygon) {

        return pixels;
    }

    public double[][] getNextCorners() {
        lastCorner = -lastCorner;
        return corners[(int) (0.5 + lastCorner)];
    }

    //---------------------Остальное-----------------------

    private void setPixel(float[] pixels, int i, int j, float r, float g, float b) {
        pixels[3 * i + 3 * height * j] = r;
        pixels[3 * i + 3 * height * j + 1] = g;
        pixels[3 * i + 3 * height * j + 2] = b;
    }

    private void initFirstCorners() {
        corners[0][0][0] = 0;
        corners[0][0][1] = 1;

        corners[0][1][0] = 0;
        corners[0][1][1] = 0;

        corners[0][2][0] = 1;
        corners[0][2][1] = 0;

    }


    private void initSecondCorners() {
        corners[1][0][0] = 1;
        corners[1][0][1] = 0;

        corners[1][1][0] = 1;
        corners[1][1][1] = 1;

        corners[1][2][0] = 0;
        corners[1][2][1] = 1;
    }


    public PhongGlossTexture() {
        initFirstCorners();
        initSecondCorners();

        pixels = new float[width * height * 3];

        resetPixels();
    }
}
