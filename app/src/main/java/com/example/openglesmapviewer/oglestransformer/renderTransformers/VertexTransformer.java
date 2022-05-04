package com.example.openglesmapviewer.oglestransformer.renderTransformers;

import static com.example.openglesmapviewer.oglestransformer.renderParameters.DefaultRenderParameters.*;

import com.example.openglesmapviewer.oglestransformer.OGLESRenderTypes;

public class VertexTransformer {
    static OGLESRenderTypes currentRenderTargetType = OGLESRenderTypes.GL_NULL;

    //------------------------------------------ВЕРШИНЫ---------------------------------------------

    public static void glVertex2f(float X, float Y) {
        currentRenderTargetType.addVertexInStack(X, Y, defaultDepth);
    }

    public static void glVertex2d(double X, double Y) {
        currentRenderTargetType.addVertexInStack((float) X, (float) Y, defaultDepth);
    }

    public static void glVertex3f(float X, float Y, float Z) {
        currentRenderTargetType.addVertexInStack(X, Y, Z);
    }

    public static void glVertex3d(double X, double Y, double Z) {
        currentRenderTargetType.addVertexInStack((float) X, (float) Y, (float) Z);
    }

    //------------------------------------------ФИГУРЫ----------------------------------------------

    public static void glBegin(OGLESRenderTypes newRenderTargetType) {
        currentRenderTargetType = newRenderTargetType;
    }

    public static void glEnd() {
        currentRenderTargetType = OGLESRenderTypes.GL_NULL;
    }

    //-----------------------------------------ТЕКСТУРЫ---------------------------------------------

    public static void glTexCoord2d(double X, double Y) {
        currentRenderTargetType.addTexturesInStack((float) X, (float) Y);
    }
}
