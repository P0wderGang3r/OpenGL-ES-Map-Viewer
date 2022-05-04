package com.example.openglesmapviewer.oglestransformer.renderParameters;

public class GlobalRenderParameters {

    //--------------------------------------ЦВЕТ ОБЪЕКТА--------------------------------------------

    /**
     *  Set color with red, green, blue and alpha (opacity) values
     */
    private static final float[] fragmentColor = { 1f, 1f, 1f, 1f };

    /**
     * Получение цвета фрагмента
     * @return {float, float, float, float}
     */
    public static float[] getFragmentColor() {
        return fragmentColor;
    }

    /**
     * Добавление нового цвета фрагмента
     * @param R параметр R
     * @param G параметр G
     * @param B параметр B
     * @param A параметр прозрачности A
     */
    public static void setFragmentColor(float R, float G, float B, float A) {
        fragmentColor[0] = R;
        fragmentColor[1] = G;
        fragmentColor[2] = B;
        fragmentColor[3] = A;
    }

    /**
     * Очистка цвета фрагмента - приведение к цвету по умолчанию
     */
    public static void clearFragmentColor() {
        fragmentColor[0] = 1f;
        fragmentColor[1] = 1f;
        fragmentColor[2] = 1f;
        fragmentColor[3] = 1f;
    }

    //----------------------------------------МАТРИЦЫ-----------------------------------------------

    /**
     * Матрица позиции/поворота/растяжения относительно кадра
     */
    private static float[] mvpMatrix;

    /**
     * Установка параметров матрицы кадра
     * @param mvpMatrix float[]
     */
    public static void setMvpMatrix(float[] mvpMatrix) {
        GlobalRenderParameters.mvpMatrix = mvpMatrix;
    }

    /***
     * Получение матрицы кадра
     * @return float[]
     */
    public static float[] getMvpMatrix() {
        return mvpMatrix;
    }
}
