package com.example.openglesmapviewer.renderScene.primitives;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.openglesmapviewer.renderScene.SceneGlobals;
import java.io.InputStream;

public class Texture {

    private final String textureName;

    private final Bitmap bitmap;

    private int numInMemory;

    private float lastCorner = (float) (0.5);
    private final float[][][] corners = new float[2][3][2];


    //---------------------Get-теры------------------------

    public Bitmap getBitmap() {
        return bitmap;
    }

    public float[][] getNextCorners() {
        lastCorner = -lastCorner;
        return corners[(int) (0.5 + lastCorner)];
    }

    public String getTextureName() {
        return textureName;
    }

    public int getNumInMemory() {
        return numInMemory;
    }

    //---------------------Остальное-----------------------

    public void setNumInMemory(int numInMemory) {
        this.numInMemory = numInMemory;
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


    public Texture(String textureName, String pathToTexture) {
        initFirstCorners();
        initSecondCorners();

        this.textureName = textureName;

        int id = SceneGlobals.getContext().getResources()
                .getIdentifier(pathToTexture, null, SceneGlobals.getContext()
                        .getPackageName());
        this.bitmap = BitmapFactory.decodeResource(SceneGlobals.getContext().getResources(), id);
    }
}