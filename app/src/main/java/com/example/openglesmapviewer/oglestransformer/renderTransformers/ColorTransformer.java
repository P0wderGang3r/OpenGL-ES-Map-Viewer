package com.example.openglesmapviewer.oglestransformer.renderTransformers;

import static com.example.openglesmapviewer.oglestransformer.renderParameters.DefaultRenderParameters.defaultAlpha;
import static com.example.openglesmapviewer.oglestransformer.renderParameters.GlobalRenderParameters.setFragmentColor;

public class ColorTransformer {
    //-------------------------------------------ЦВЕТ-----------------------------------------------

    public static void glColor3f(float R, float G, float B) {
        setFragmentColor(R, G, B, defaultAlpha);
    }

    public static void glColor3d(double R, double G, double B) {
        setFragmentColor((float) R, (float) G, (float) B, defaultAlpha);
    }

    public static void glColor4f(float R, float G, float B, float A) {
        setFragmentColor(R, G, B, A);
    }

    public static void glColor4d(double R, double G, double B, double A) {
        setFragmentColor((float) R, (float) G, (float) B, (float) A);
    }

}
