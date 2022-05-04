package com.example.openglesmapviewer.renderScene.sceneComputations;

public class MapResize {
    public static float[] resize(float[] coordinates, float size) {
        return new float[] {
                coordinates[0] * size,
                coordinates[1] * size,
                coordinates[2] * size
        };
    }
}
