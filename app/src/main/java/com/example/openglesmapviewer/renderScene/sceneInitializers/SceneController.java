package com.example.openglesmapviewer.renderScene.sceneInitializers;

import android.opengl.GLES20;

import com.example.openglesmapviewer.renderScene.SceneMemory;

public class SceneController {
    //todo: управление созданием сцены через выбор карты
    public static void mapInitialize(String mapName) {
        SceneMemory.setMapName(mapName);
        PrimitivesInit.initPrimitiveObjects();
        ComplexInit.initComplexObjects();
        LightSourcesInit.initLightSources();
        SceneParametersInit.initSceneParameters();
        bindTextures.bind();
    }
}
