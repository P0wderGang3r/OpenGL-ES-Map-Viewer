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

public class RenderTriangle implements RenderTarget {

    //---------------------------------------ПАРАМЕТРЫ------------------------------------------

    private FloatBuffer vertexBuffer;

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;

    int currentNumOfCoords = 0;

    final float[] triangleCoords = {   // in counterclockwise order:
            0.0f, 0.0f, 0.0f, // top
            0.0f, 0.0f, 0.0f, // bottom left
            0.0f, 0.0f, 0.0f  // bottom right
    };

    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * defaultFloatLength;

    private int vertexShader;
    private int fragmentShader;

    private int mProgram;

    private String vertexShaderCode = Shaders.DEFAULT_VERTEX_SHADER.shaderCode();
    private String fragmentShaderCode = Shaders.DEFAULT_FRAGMENT_SHADER.shaderCode();

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
     *
     * @param type == GL_VERTEX_SHADER || GL_FRAGMENT_SHADER
     * @return String Кодовая база шейдера
     */
    public String getShaderCode(int type) {
        assert (type == GLES20.GL_VERTEX_SHADER || type == GLES20.GL_FRAGMENT_SHADER);

        switch (type) {
            case GLES20.GL_VERTEX_SHADER: return vertexShaderCode;
            case GLES20.GL_FRAGMENT_SHADER: return fragmentShaderCode;
            default: return "Ты как через assert пролез??";
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
        // initialize vertex byte buffer for shape coordinates
        // инициализируем вершинный байт-буффер для координат фигуры
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // (number of coordinate values * 4 bytes per float)
                // Количество значений координат * 4 байт на каждый float
                triangleCoords.length * defaultFloatLength);

        // use the device hardware's native byte order
        // используем нативный для устройства порядок байт
        bb.order(ByteOrder.nativeOrder());

        // create a float point buffer from the ByteBuffer
        // создаём из байт-буффера float буффер
        vertexBuffer = bb.asFloatBuffer();

        // add the coordinates to the FloatBuffer
        // добавляем координаты к двумерному буфферу
        vertexBuffer.put(triangleCoords);

        // set the buffer to read the first coordinate
        // устанавливаем буфер на чтение первой координаты
        vertexBuffer.position(0);

        // create empty OpenGL ES Program
        // создаём пустую OGLES подпрограмму
        mProgram = GLES20.glCreateProgram();

        // add the vertex shader to program
        // добавляем вертексный шейдер в подпрограмму
        GLES20.glAttachShader(mProgram, vertexShader);

        // add the fragment shader to program
        // добавляем фрагментный шейдер в подпрограмму
        GLES20.glAttachShader(mProgram, fragmentShader);

        // creates OpenGL ES program executables
        // компилируем OGLES подпрограмму
        GLES20.glLinkProgram(mProgram);
    }

    /**
     * Функция вывода новой фигуры на экран в соответствии с текущей программой
     */
    private void draw() {
        // Add program to OpenGL ES environment
        // добавляем OGLES подпрограмму в стек вызовов OGLES
        GLES20.glUseProgram(mProgram);

        // get handle to vertex shader's v_Position member
        int positionHandle = GLES20.glGetAttribLocation(mProgram, "v_Position");

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(positionHandle);

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        // get handle to fragment shader's v_Color member
        int colorHandle = GLES20.glGetUniformLocation(mProgram, "v_Color");

        // Set color for drawing the triangle
        GLES20.glUniform4fv(colorHandle, 1, getFragmentColor(), 0);

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
        triangleCoords[3 * currentNumOfCoords] = X;
        triangleCoords[3 * currentNumOfCoords + 1] = Y;
        triangleCoords[3 * currentNumOfCoords + 2] = Z;

        currentNumOfCoords += 1;

        if (3 == currentNumOfCoords) {
            createProgram();
            draw();
            clearBuffers();
        }
    }

    @Override
    public void addTextureInStack(float X, float Y) {

    }
}
