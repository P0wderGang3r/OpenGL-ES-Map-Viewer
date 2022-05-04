package com.example.openglesmapviewer.renderScene.sceneComputations;

public class MapRotation {

    private static final float[][] rotationMatrix = new float[3][3];

    private static void rotMatrix(float alpha, float beta, float gamma) {
        rotationMatrix[0][0] = (float) (Math.cos(alpha) * Math.cos(beta));
        rotationMatrix[1][0] = (float) (Math.cos(alpha) * Math.sin(beta) * Math.sin(gamma) - Math.sin(alpha) * Math.cos(gamma));
        rotationMatrix[2][0] = (float) (Math.cos(alpha) * Math.sin(beta) * Math.cos(gamma) + Math.sin(alpha) * Math.sin(gamma));


        rotationMatrix[0][1] = (float) (Math.sin(alpha) * Math.cos(beta));
        rotationMatrix[1][1] = (float) (Math.sin(alpha) * Math.sin(beta) * Math.sin(gamma) + Math.cos(alpha) * Math.cos(gamma));
        rotationMatrix[2][1] = (float) (Math.sin(alpha) * Math.sin(beta) * Math.cos(gamma) - Math.cos(alpha) * Math.sin(gamma));

        rotationMatrix[0][2] = (float) (-Math.sin(beta));
        rotationMatrix[1][2] = (float) (Math.cos(beta) * Math.sin(gamma));
        rotationMatrix[2][2] = (float) (Math.cos(beta) * Math.cos(gamma));
    }

    public static float[] rotate(float[] coordinates, float[] rotationStatus) {

        rotMatrix(rotationStatus[0], rotationStatus[1], rotationStatus[2]);

        float[] xyz = {0, 0, 0};

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                xyz[i] += coordinates[j] * rotationMatrix[i][j];
            }

        return new float[]{xyz[0], xyz[1], xyz[2]};
    }
}

