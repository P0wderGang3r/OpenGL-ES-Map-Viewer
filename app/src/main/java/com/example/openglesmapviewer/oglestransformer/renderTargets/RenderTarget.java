package com.example.openglesmapviewer.oglestransformer.renderTargets;

import com.example.openglesmapviewer.oglestransformer.Shaders;

/**
 * Интерфейс, связывающий типы фигур рендера с непосредственными реализациями функций для их рендера
 */
public interface RenderTarget {

    /**
     * Функция, предоставляющая возможность предварительной
     * подгрузки и компиляции требуемых типов шейдеров
     */
    public void preCompileShader();

    /**
     * Создание кодовой базы шейдера
     * @param type == GL_VERTEX_SHADER || GL_FRAGMENT_SHADER
     * @param shader Получение кодовой базы шейдера
     */
    public void setShaderCode(int type, Shaders shader);

    /**
     * Добавление следующей координаты фигуры в стек
     * @param X координата X
     * @param Y координата Y
     * @param Z координата Z
     */
    public void addVertexInStack(float X, float Y, float Z);

    public void addTextureInStack(float X, float Y);
}
