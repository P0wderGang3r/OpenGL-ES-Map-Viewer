package com.example.openglesmapviewer.renderScene.sceneComputations;

public class MapMovement {

    public static float[] move(float[] coords, float[] positionStatus) {
        return new float[]{
                coords[0] + positionStatus[0],
                coords[1] + positionStatus[1],
                coords[2] + positionStatus[2]
        };
    }
}
