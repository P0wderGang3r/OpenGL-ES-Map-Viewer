package com.example.openglesmapviewer.renderScene;

import android.content.Context;

//-5 5 90 #near_far_fov

public class SceneGlobals {

    //----------------------ТЕКУЩИЙ КОНТЕКСТ, В КОТОРОМ СУЩЕСТВУЕТ ПРИЛОЖЕНИЕ-----------------------

    private static Context context;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        SceneGlobals.context = context;
    }

    //----------------------------------ТЕКУЩИЙ ПОВОРОТ СЦЕНЫ---------------------------------------

    public static float[] rotationStatus = {0f, 0f, 0f};

    public static float[] getRotationStatus() {
        return rotationStatus;
    }

    public static void setRotationStatus(float[] rotationStatus) {
        SceneGlobals.rotationStatus = rotationStatus;
    }

    //----------------------------------ТЕКУЩЕЕ СМЕЩЕНИЕ СЦЕНЫ--------------------------------------

    private static float[] positionStatus = {0f, 0f, 0f};

    public static float[] getPositionStatus() {
        return positionStatus;
    }

    public static void setPositionStatus(float[] positionStatus) {
        SceneGlobals.positionStatus = positionStatus;
    }

    //----------------------------------ТЕКУЩИЙ РАЗМЕР СЦЕНЫ----------------------------------------

    private static float mapSizeStatus = 0.5f;

    public static float getMapSizeStatus() {
        return mapSizeStatus;
    }

    public static void setMapSizeStatus(float mapSizeStatus) {
        SceneGlobals.mapSizeStatus = mapSizeStatus;
    }

    //-----------------------------------ТЕКУЩИЙ ЦВЕТ СЦЕНЫ-----------------------------------------

    private static float[] sceneColorStatus = {0f, 0f, 0f};

    public static float getSceneColorR() {
        return sceneColorStatus[0];
    }

    public static float getSceneColorG() {
        return sceneColorStatus[0];
    }

    public static float getSceneColorB() {
        return sceneColorStatus[0];
    }

    public static float[] getSceneColorStatus() {
        return sceneColorStatus;
    }

    public static void setSceneColorStatus(float[] sceneColorStatus) {
        SceneGlobals.sceneColorStatus = sceneColorStatus;
    }

    //-----------------------------------ТЕКСТУРЫ В ПАМЯТИ------------------------------------------

    public static int[] oglTextures;
}
