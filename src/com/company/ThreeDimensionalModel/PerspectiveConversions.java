package com.company.ThreeDimensionalModel;

public class PerspectiveConversions {

    private final double aspect;
    private final double near;
    private final double far;
    private final double fov;


    /* *
    private final double[][] matrix = new double[4][4];

    private void initMatrix() {
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                matrix[j][i] = 0;

        double convXY = 1 / (Math.tan(fov * Math.PI / (2 * 180)) * aspect);

        matrix[0][0] = convXY;
        matrix[1][1] = convXY;
        matrix[2][2] = -(far) / (far - near);
        matrix[3][2] = -1;
        matrix[2][3] = -(2 * far * near) / (far - near);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(matrix[j][i] + " ");
            }
            System.out.println("");
        }
    }
    */

    public double[] getPerspectiveConversions(double[] coords) {
        double[] newCoords = {coords[0], coords[1], coords[2]};

        double dist = (Math.abs(near) + Math.abs(far)) / 2;
        double perspectiveFunc = (dist - coords[2]) / (dist) * 1 / (Math.tan(fov * Math.PI / (2 * 180)));
        newCoords[0] *= perspectiveFunc;
        newCoords[1] *= perspectiveFunc * aspect;

        return newCoords;
    }

    PerspectiveConversions(int width, int height, double near, double far, double fov) {
        this.aspect = ((double) width) / ((double) height);
        this.near = near;
        this.far = far;
        this.fov = fov;

        //initMatrix();
    }
}
