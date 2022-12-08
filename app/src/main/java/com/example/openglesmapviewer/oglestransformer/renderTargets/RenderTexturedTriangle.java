package com.example.openglesmapviewer.oglestransformer.renderTargets;

import static com.example.openglesmapviewer.oglestransformer.renderParameters.DefaultRenderParameters.defaultFloatLength;
import static com.example.openglesmapviewer.oglestransformer.renderParameters.GlobalRenderParameters.getFragmentColor;
import static com.example.openglesmapviewer.oglestransformer.renderParameters.GlobalRenderParameters.getMvpMatrix;

import android.opengl.GLES20;

import com.example.openglesmapviewer.OGLESRenderer;
import com.example.openglesmapviewer.oglestransformer.renderParameters.GlobalRenderParameters;
import com.example.openglesmapviewer.oglestransformer.Shaders;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class RenderTexturedTriangle implements RenderTarget {

    //---------------------------------------ПАРАМЕТРЫ------------------------------------------

    private FloatBuffer vertexBuffer;

    static final int COORDS_PER_VERTEX = 3;
    static final int COORDS_PER_TEX = 2;
    static final int COLORS_PER_VERTEX = 4;

    static final int NUM_OF_VERTEX = 3;

    int currentNumOfCoords = 0;
    int currentNumOfTextures = 0;

    // X, Y, Z, Xtex, Ytex, R, G, B, A
    final float[] triangleCoords = {   // in counterclockwise order:
            0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 0.0f, 0.0f
    };

    private final int vertexCount = triangleCoords.length / NUM_OF_VERTEX;
    private final int vertexStride = (COORDS_PER_VERTEX + COORDS_PER_TEX) * defaultFloatLength;

    private int vertexShader;
    private int fragmentShader;

    private int mProgram;

    private String vertexShaderCode = Shaders.TEXTURED_VERTEX_SHADER.shaderCode();
    private String fragmentShaderCode = Shaders.TEXTURED_FRAGMENT_SHADER.shaderCode();

    //----------------------------------------ШЕЙДЕРЫ-------------------------------------------

    /**
     * Создание кодовой базы шейдера
     * @param type == GL_VERTEX_SHADER || GL_FRAGMENT_SHADER
     * @param shader Получение кодовой базы шейдера
     */
    @Override
    public void setShaderCode(int type, Shaders shader) {
        assert (type == GLES20.GL_VERTEX_SHADER || type == GLES20.GL_FRAGMENT_SHADER);

        switch (type) {
            case GLES20.GL_VERTEX_SHADER: vertexShaderCode = shader.shaderCode();
            case GLES20.GL_FRAGMENT_SHADER: fragmentShaderCode = shader.shaderCode();
        }
    }

    /**
     * Запрос содержимого шейдера
     * @param type == GL_VERTEX_SHADER || GL_FRAGMENT_SHADER
     * @return String Кодовая база шейдера
     */
    public String getShaderCode(int type) {
        assert (type == GLES20.GL_VERTEX_SHADER || type == GLES20.GL_FRAGMENT_SHADER);

        switch (type) {
            case GLES20.GL_VERTEX_SHADER: return vertexShaderCode;
            case GLES20.GL_FRAGMENT_SHADER: return fragmentShaderCode;
            default: return "Error";
        }
    }

    /**
     * Функция, предоставляющая возможность предварительной
     * подгрузки и компиляции требуемых типов шейдеров
     */
    @Override
    public void preCompileShader() {
        vertexShader = OGLESRenderer.loadShader(GLES20.GL_VERTEX_SHADER, getShaderCode(GLES20.GL_VERTEX_SHADER));
        fragmentShader = OGLESRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, getShaderCode(GLES20.GL_FRAGMENT_SHADER));
    }

    //------------------------------------ЛОКАЛЬНЫЕ ПОЛЯ----------------------------------------

    /**
     * Функция, создающая новую программу вывода на экран новой фигуры
     */
    private void createProgram() {
        // инициализируем вершинный байт-буффер для координат фигуры
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // Количество значений координат * 4 байт на каждый float
                triangleCoords.length * defaultFloatLength);

        // используем нативный для устройства порядок байт
        bb.order(ByteOrder.nativeOrder());

        // создаём из байт-буффера float буффер
        vertexBuffer = bb.asFloatBuffer();

        // добавляем координаты к двумерному буфферу
        vertexBuffer.put(triangleCoords);

        // создаём пустую OGLES подпрограмму
        mProgram = GLES20.glCreateProgram();

        // добавляем вертексный шейдер в подпрограмму
        GLES20.glAttachShader(mProgram, vertexShader);

        // добавляем фрагментный шейдер в подпрограмму
        GLES20.glAttachShader(mProgram, fragmentShader);

        // компилируем OGLES подпрограмму
        GLES20.glLinkProgram(mProgram);
    }

    /**
     * Функция вывода новой фигуры на экран в соответствии с текущей программой
     */
    private void draw() {
        // добавляем OGLES подпрограмму в стек вызовов OGLES
        GLES20.glUseProgram(mProgram);

        int positionHandle = GLES20.glGetAttribLocation(mProgram, "v_Position");
        vertexBuffer.position(0);
        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        int texturePositionHandle = GLES20.glGetAttribLocation(mProgram, "a_Texture");
        vertexBuffer.position(COORDS_PER_VERTEX);
        GLES20.glEnableVertexAttribArray(texturePositionHandle);
        GLES20.glVertexAttribPointer(texturePositionHandle, COORDS_PER_TEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        int colorHandle = GLES20.glGetUniformLocation(mProgram, "v_Color");
        GLES20.glUniform4fv(colorHandle, 1, getFragmentColor(), 0);

        int textureUnitHandle = GLES20.glGetUniformLocation(mProgram, "u_TextureUnit");
        GLES20.glUniform1i(textureUnitHandle, 0);

        // get handle to shape's transformation matrix
        int vPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");

        // Pass the projection and view transformation to the shader
        GLES20.glUniformMatrix4fv(vPMatrixHandle, 1, false, getMvpMatrix(), 0);

        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(positionHandle);
    }

    /**
     * Сброс текущего количества координат в стеке,
     * очистка буфера цвета и удаление программы из памяти
     */
    private void clearBuffers() {
        currentNumOfCoords = 0;
        currentNumOfTextures = 0;
        GlobalRenderParameters.clearFragmentColor();
        GLES20.glDeleteProgram(mProgram);
    }

    //--------------------------------------РИСОВАЛОЧКА-----------------------------------------

    /**
     * Добавление следующей координаты фигуры в стек
     * @param X координата X
     * @param Y координата Y
     * @param Z координата Z
     */
    @Override
    public void addVertexInStack(float X, float Y, float Z) {
        if (currentNumOfCoords < NUM_OF_VERTEX) {
            triangleCoords[vertexCount * currentNumOfCoords] = X;
            triangleCoords[vertexCount * currentNumOfCoords + 1] = Y;
            triangleCoords[vertexCount * currentNumOfCoords + 2] = Z;

            /*
            for (int i = 0; i < vertexCount; i++) {
                System.out.print(triangleCoords[i] + " ");
            }
             */
            //System.out.println();

            currentNumOfCoords += 1;
        }

        if (NUM_OF_VERTEX == currentNumOfCoords && NUM_OF_VERTEX == currentNumOfTextures) {
            createProgram();
            draw();
            clearBuffers();
        }
    }

    @Override
    public void addTextureInStack(float X, float Y) {
        if (currentNumOfTextures < NUM_OF_VERTEX) {
            triangleCoords[vertexCount * currentNumOfCoords + 3] = X;
            triangleCoords[vertexCount * currentNumOfCoords + 4] = Y;

            currentNumOfTextures += 1;
        }

        if (NUM_OF_VERTEX == currentNumOfCoords && NUM_OF_VERTEX == currentNumOfTextures) {
            createProgram();
            draw();
            clearBuffers();
        }
    }
}
