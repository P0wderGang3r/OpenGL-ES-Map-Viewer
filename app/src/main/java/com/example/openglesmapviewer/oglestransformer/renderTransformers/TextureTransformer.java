package com.example.openglesmapviewer.oglestransformer.renderTransformers;

import static com.example.openglesmapviewer.oglestransformer.renderParameters.DefaultRenderParameters.defaultFloatLength;

import android.opengl.GLES20;

import com.example.openglesmapviewer.oglestransformer.OGLESRenderTypes;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class TextureTransformer {

    public static void glTexImage2D(int target, int level, int internalFormat, int width, int height, int border, int format, int type, float[] data) {
        ByteBuffer bb = ByteBuffer.allocateDirect(
                data.length * defaultFloatLength);

        bb.order(ByteOrder.nativeOrder());

        FloatBuffer dataBuffer = bb.asFloatBuffer();

        // add the coordinates to the FloatBuffer
        // добавляем координаты к двумерному буфферу
        dataBuffer.put(data);

        GLES20.glTexImage2D(target, level, internalFormat, width, height, border, format, type, dataBuffer);
    }
}
