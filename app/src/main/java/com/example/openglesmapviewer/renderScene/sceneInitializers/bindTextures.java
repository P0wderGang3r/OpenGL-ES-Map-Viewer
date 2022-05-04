package com.example.openglesmapviewer.renderScene.sceneInitializers;

import static android.opengl.GLES20.GL_CLAMP_TO_EDGE;
import static android.opengl.GLES20.GL_MIRRORED_REPEAT;
import static android.opengl.GLES20.GL_NEAREST;
import static android.opengl.GLES20.GL_REPEAT;
import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TEXTURE_MAG_FILTER;
import static android.opengl.GLES20.GL_TEXTURE_MIN_FILTER;
import static android.opengl.GLES20.GL_TEXTURE_WRAP_S;
import static android.opengl.GLES20.GL_TEXTURE_WRAP_T;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glTexParameteri;
import static com.example.openglesmapviewer.oglestransformer.OGLESTransformer.*;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import com.example.openglesmapviewer.renderScene.SceneGlobals;
import com.example.openglesmapviewer.renderScene.SceneMemory;

public class bindTextures {
    public static void bind() {
        GLES20.glActiveTexture(GL_TEXTURE0);
        SceneGlobals.oglTextures = new int[SceneMemory.getTextures().size()];

        for (byte index = 0; index < SceneMemory.getTextures().size(); index++) {
            GLES20.glGenTextures(1, SceneGlobals.oglTextures, index);

            glBindTexture(GL_TEXTURE_2D, SceneGlobals.oglTextures[index]);

            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

            //GL_LINEAR
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, SceneMemory.getTextures().get(index).getBitmap(), 0);

            SceneMemory.getTextures().get(index).setNumInMemory(index);

            glBindTexture(GL_TEXTURE_2D, 0);
        }

    }
}
