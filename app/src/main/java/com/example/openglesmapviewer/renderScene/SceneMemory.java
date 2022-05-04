package com.example.openglesmapviewer.renderScene;

import com.example.openglesmapviewer.renderScene.primitives.LightSource;
import com.example.openglesmapviewer.renderScene.primitives.Model;
import com.example.openglesmapviewer.renderScene.primitives.ComplexModel;
import com.example.openglesmapviewer.renderScene.primitives.Texture;

import java.util.ArrayList;

public class SceneMemory {
    //---------------------------------------НАЗВАНИЕ КАРТЫ-----------------------------------------

    private static String mapName;

    public static String getMapName() {
        return mapName;
    }

    public static void setMapName(String mapName) {
        SceneMemory.mapName = mapName;
    }

    //------------------------------------НАБОР ОБЪЕКТОВ СЦЕНЫ--------------------------------------

    private static ArrayList<ComplexModel> complexModels;

    public static ArrayList<ComplexModel> getComplexModels() {
        return complexModels;
    }

    public static void setComplexModels(ArrayList<ComplexModel> complexModels) {
        SceneMemory.complexModels = complexModels;
    }

    //-----------------------------------------ТЕКСТУРЫ---------------------------------------------

    private static ArrayList<Texture> textures;

    public static ArrayList<Texture> getTextures() {
        return textures;
    }

    public static void setTextures(ArrayList<Texture> textures) {
        SceneMemory.textures = textures;
    }

    //------------------------------------------МОДЕЛИ----------------------------------------------

    private static ArrayList<Model> models;

    public static ArrayList<Model> getModels() {
        return models;
    }

    public static void setModels(ArrayList<Model> models) {
        SceneMemory.models = models;
    }

    //-------------------------------------ИСТОЧНИКИ СВЕТА------------------------------------------

    private static ArrayList<LightSource> lightSources;

    public static ArrayList<LightSource> getLightSources() {
        return lightSources;
    }

    public static void setLightSources(ArrayList<LightSource> lightSources) {
        SceneMemory.lightSources = lightSources;
    }

}
