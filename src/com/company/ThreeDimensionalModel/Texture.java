package com.company.ThreeDimensionalModel;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Texture {

    private final float[] pixels;
    private int width = 1;
    private int height = 1;

    private float lastCorner = (float)(0.5);
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

    public double[][] getNextCorners() {
        lastCorner = -lastCorner;
        return corners[(int)(0.5 + lastCorner)];
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


    public Texture(String inp, int width, int height) {
        initFirstCorners();
        initSecondCorners();

        this.width = width;
        this.height = height;

        pixels = new float[width * height * 3];

        Path path = Paths.get(inp);

        byte[] bytes;

        try {
            bytes = Files.readAllBytes(path);

            for (int j = 0; j < height; j++) {
                for (int i = 0; i < width; i++) {
                    setPixel(pixels, i, j,
                            ((float) ((short) bytes[3 * i + 3 * height * j] + (bytes[3 * i + 3 * height * j] >= 0 ? 0 : 256)) / (float) 255),
                            ((float) ((short) bytes[3 * i + 3 * height * j + 1] + (bytes[3 * i + 3 * height * j + 1] >= 0 ? 0 : 256)) / (float) 255),
                            ((float) ((short) bytes[3 * i + 3 * height * j + 2] + (bytes[3 * i + 3 * height * j + 2] >= 0 ? 0 : 256)) / (float) 255)
                    );
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
            for (int j = 0; j < height; j++) {
                for (int i = 0; i < width; i++) {
                    setPixel(pixels, j, i, 1, 1, 1);
                }
            }
        }

    }
}