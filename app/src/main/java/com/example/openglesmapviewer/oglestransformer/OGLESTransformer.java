package com.example.openglesmapviewer.oglestransformer;

import com.example.openglesmapviewer.oglestransformer.renderTransformers.ColorTransformer;
import com.example.openglesmapviewer.oglestransformer.renderTransformers.TextureTransformer;
import com.example.openglesmapviewer.oglestransformer.renderTransformers.VertexTransformer;

public class OGLESTransformer {

    //VertexTransformer.java-----------------------------------------------------------------ВЕРШИНЫ

    public static void glVertex2f(float X, float Y) {
        VertexTransformer.glVertex2f(X, Y);
    }

    public static void glVertex2d(double X, double Y) {
        VertexTransformer.glVertex2d(X, Y);
    }

    public static void glVertex3f(float X, float Y, float Z) {
        VertexTransformer.glVertex3f(X, Y, Z);
    }

    public static void glVertex3d(double X, double Y, double Z) {
        VertexTransformer.glVertex3d(X, Y, Z);
    }

    public static void glBegin(OGLESRenderTypes newRenderTargetType) {
        VertexTransformer.glBegin(newRenderTargetType);
    }

    public static void glEnd() {
        VertexTransformer.glEnd();
    }

    //TextureTransformer.java---------------------------------------------------------------ТЕКСТУРЫ

    public static void glTexImage2D(int target, int level, int internalFormat, int width, int height, int border, int format, int type, float[] data) {
        TextureTransformer.glTexImage2D(target, level, internalFormat, width, height, border, format, type, data);
    }

    public static void glTexCoord2d(double X, double Y) {
        VertexTransformer.glTexCoord2d(X, Y);
    }

    //ColorTransformer.java---------------------------------------------------------------------ЦВЕТ

    public static void glColor3f(float R, float G, float B) {
        ColorTransformer.glColor3f(R, G, B);
    }

    public static void glColor3d(double R, double G, double B) {
        ColorTransformer.glColor3d(R, G, B);
    }

    public static void glColor4f(float R, float G, float B, float A) {
        ColorTransformer.glColor4f(R, G, B, A);
    }

    public static void glColor4d(double R, double G, double B, double A) {
        ColorTransformer.glColor4d(R, G, B, A);
    }

}
