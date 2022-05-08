package com.example.openglesmapviewer;

import static android.opengl.GLES20.GL_DEPTH_TEST;
import static com.example.openglesmapviewer.oglestransformer.renderParameters.GlobalRenderParameters.*;

import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.example.openglesmapviewer.oglestransformer.OGLESRenderTypes;
import com.example.openglesmapviewer.renderScene.SceneLoop;
import com.example.openglesmapviewer.renderScene.sceneInitializers.SceneController;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class OGLESRenderer implements GLSurfaceView.Renderer {

// vPMatrix is an abbreviation for "Model View Projection Matrix"
    private static final float[] vPMatrix = new float[16];
    private static final float[] projectionMatrix = new float[16];
    private static final float[] viewMatrix = new float[16];
    private static void calculateMatrix() {

        // Set the camera position (View matrix)
        Matrix.setLookAtM(viewMatrix, 0, 0, 0, -4f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0);

        setMvpMatrix(vPMatrix);
    }

    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    public void onDrawFrame(GL10 unused) {
        // Сброс фона до текущего цвета
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);

        calculateMatrix();

        SceneLoop.mapLoop();
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES30.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 5);
    }


    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Устанавливаем цвета фона
        GLES30.glClearColor(0f, 0f, 0f, 0f);
        GLES20.glEnable(GL_DEPTH_TEST);

        SceneController.mapInitialize("test_map");

        OGLESRenderTypes.GL_TRIANGLES.preCompileShader();
        OGLESRenderTypes.GL_TEXTURED_TRIANGLES.preCompileShader();
    }
}
