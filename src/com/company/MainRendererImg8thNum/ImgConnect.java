package com.company.MainRendererImg8thNum;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImgConnect {

    private float[] pixels;
    private int width = 1;
    private int height = 1;

    private final float[][] ditherMatrix = new float[2][2];
    private final float[][] stuckiMatrix = new float[5][3];


    //--------------Изначальное изображение----------------
    public float[] getPixels() {
        return pixels;
    }


    //-------------------ЧБ изображение--------------------
    private float getBWSubPixel(int x, int y) {
        return ((pixels[3 * x + 3 * height * y] / 3) + (pixels[3 * x + 3 * height * y + 1] / 3) + (pixels[3 * x + 3 * height * y + 2] / 3));
    }


    public float[] getBWPixels() {
        float[] localPixels = genLocalPixels(pixels);

        for (int j = 0; j < height; j++)
            for (int i = 0; i < width; i++) {
                setPixel(localPixels, i, j,
                        getBWSubPixel(i, j),
                        getBWSubPixel(i, j),
                        getBWSubPixel(i, j)
                );
            }

        return localPixels;
    }


    //-----------Изображение с порогом цветности-----------
    public float[] getThresholdPixels() {

        float[] localPixels = genLocalPixels(pixels);

        float threshold = (float) 0.6;

        for (int j = 0; j < height; j++)
            for (int i = 0; i < width; i++) {

                setPixel(localPixels, i, j,
                        (localPixels[3 * i + 3 * height * j] > threshold) ? 1 : (float) 0.1,
                        (localPixels[3 * i + 3 * height * j + 1] > threshold) ? 1 : (float) 0.1,
                        (localPixels[3 * i + 3 * height * j + 2] > threshold) ? 1 : (float) 0.1
                );

            }

        return localPixels;
    }


    //------Изображение со случайным порогом цветности-----
    public float[] getRandThresholdPixels() {
        float[] localPixels = genLocalPixels(pixels);

        for (int j = 0; j < height; j++)
            for (int i = 0; i < width; i++) {

                setPixel(localPixels, i, j,
                        (localPixels[3 * i + 3 * height * j] > Math.random()) ? 1 : (float) 0.1,
                        (localPixels[3 * i + 3 * height * j + 1] > Math.random()) ? 1 : (float) 0.1,
                        (localPixels[3 * i + 3 * height * j + 2] > Math.random()) ? 1 : (float) 0.1
                );

            }

        return localPixels;
    }


    //-------------------ЧБ Дизеринг-----------------------
    public float[] getDitheredPixels() {
        float[] localPixels = genLocalPixels(pixels);

        for (int j = 0; j < height; j++)
            for (int i = 0; i < width; i++) {

                if ((getBWSubPixel(i, j) * 5) > ditherMatrix[j % 2][i % 2]) {
                    setPixel(localPixels, i, j,
                            1,
                            1,
                            1
                    );
                }
                else {
                    setPixel(localPixels, i, j,
                            0,
                            0,
                            0
                    );
                }
            }

        return localPixels;
    }


    //-----------------Цветной Дизеринг--------------------
    public float[] getColoredDitheredPixels() {
        float[] localPixels = genLocalPixels(pixels);

        for (int j = 0; j < height; j++)
            for (int i = 0; i < width; i++) {
                setPixel(localPixels, i, j,
                        (localPixels[3 * i + 3 * height * j] * 4 > ditherMatrix[j % 2][i % 2]) ? 1 : (float) 0.1,
                        (localPixels[3 * i + 3 * height * j + 1] * 4 > ditherMatrix[j % 2][i % 2]) ? 1 : (float) 0.1,
                        (localPixels[3 * i + 3 * height * j + 2] * 4 > ditherMatrix[j % 2][i % 2]) ? 1 : (float) 0.1
                );

            }

        return localPixels;
    }


    //---------------Распространение ошибки----------------
    public float[] getStuckiPixels() {
        float[] localPixels = genLocalPixels(pixels);

        float newR;
        float newG;
        float newB;


        float quantErrorR;
        float quantErrorG;
        float quantErrorB;

        for (int j = 0; j < height - 2; j++)
            for (int i = 2; i < width - 2; i++) {
                newR = Math.round((getPixel(localPixels, i, j))[0] * (float) 256 / (float) 512);
                newG = Math.round((getPixel(localPixels, i, j))[1] * (float) 256 / (float) 512);
                newB = Math.round((getPixel(localPixels, i, j))[2] * (float) 256 / (float) 512);

                quantErrorR = (getPixel(localPixels, i, j))[0] - newR;
                quantErrorG = (getPixel(localPixels, i, j))[1] - newG;
                quantErrorB = (getPixel(localPixels, i, j))[2] - newB;

                setPixel(localPixels, i, j, newR, newG, newB);

                for( int l = 1; l <= 2; l++) {
                    for ( int m = -2; m <= 2; m++) {
                        setPixel(localPixels, i + m, j + l,
                                (getPixel(localPixels, i + m, j + l))[0] + quantErrorR * stuckiMatrix[m + 2][l],
                                (getPixel(localPixels, i + m, j + l))[1] + quantErrorG * stuckiMatrix[m + 2][l],
                                (getPixel(localPixels, i + m, j + l))[2] + quantErrorB * stuckiMatrix[m + 2][l]
                        );
                    }
                }

                setPixel(localPixels, i + 1, j,
                        (getPixel(localPixels, i + 1, j))[0] + quantErrorR * stuckiMatrix[3][0],
                        (getPixel(localPixels, i + 1, j))[1] + quantErrorG * stuckiMatrix[3][0],
                        (getPixel(localPixels, i + 1, j))[2] + quantErrorB * stuckiMatrix[3][0]
                        );

                setPixel(localPixels, i + 2, j,
                        (getPixel(localPixels, i + 2, j))[0] + quantErrorR * stuckiMatrix[4][0],
                        (getPixel(localPixels, i + 2, j))[1] + quantErrorG * stuckiMatrix[4][0],
                        (getPixel(localPixels, i + 2, j))[2] + quantErrorB * stuckiMatrix[4][0]
                );
            }

        return localPixels;
    }


    //---------------------Остальное-----------------------
    public void imgInit(String inp) {
        initDitherMatrix();
        initStuckiMatrix();

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
        }
    }

    private float[] genLocalPixels(float[] pixels) {
        float[] localPixels = new float[width * height * 3];

        if (width * height * 3 >= 0) System.arraycopy(pixels, 0, localPixels, 0, width * height * 3);
        return localPixels;
    }

    private void setPixel(float[] pixels, int i, int j, float r, float g, float b) {
        pixels[3 * i + 3 * height * j] = r;
        pixels[3 * i + 3 * height * j + 1] = g;
        pixels[3 * i + 3 * height * j + 2] = b;
    }

    private float[] getPixel(float[] pixels, int i, int j) {
        float[] rgb = new float[3];
        rgb[0] = pixels[3 * i + 3 * height * j];
        rgb[1] = pixels[3 * i + 3 * height * j + 1];
        rgb[2] = pixels[3 * i + 3 * height * j + 2];

        return rgb;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    private void initDitherMatrix() {
        ditherMatrix[0][0] = 0;
        ditherMatrix[1][0] = 2;
        ditherMatrix[0][1] = 3;
        ditherMatrix[1][1] = 1;
    }

    private void initStuckiMatrix() {
        stuckiMatrix[0][0] = 0;
        stuckiMatrix[1][0] = 0;
        stuckiMatrix[2][0] = 0;
        stuckiMatrix[3][0] = ((float)8 / (float)42);
        stuckiMatrix[4][0] = ((float)4 / (float)42);
        stuckiMatrix[0][1] = ((float)2 / (float)42);
        stuckiMatrix[1][1] = ((float)4 / (float)42);
        stuckiMatrix[2][1] = ((float)8 / (float)42);
        stuckiMatrix[3][1] = ((float)4 / (float)42);
        stuckiMatrix[4][1] = ((float)2 / (float)42);
        stuckiMatrix[0][2] = ((float)1 / (float)42);
        stuckiMatrix[1][2] = ((float)2 / (float)42);
        stuckiMatrix[2][2] = ((float)4 / (float)42);
        stuckiMatrix[3][2] = ((float)2 / (float)42);
        stuckiMatrix[4][2] = ((float)1 / (float)42);
    }

    public float[] getImage(byte num) {
        return switch (num) {
            case 1 -> getBWPixels();
            case 2 -> getThresholdPixels();
            case 3 -> getRandThresholdPixels();
            case 4 -> getDitheredPixels();
            case 5 -> getColoredDitheredPixels();
            case 6 -> getStuckiPixels();
            default -> getPixels();
        };
    }
}