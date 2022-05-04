package com.example.openglesmapviewer.oglestransformer;

import com.example.openglesmapviewer.oglestransformer.renderTargets.RenderTarget;
import com.example.openglesmapviewer.oglestransformer.renderTargets.RenderTexturedTriangle;
import com.example.openglesmapviewer.oglestransformer.renderTargets.RenderTriangle;

/**
 * Перечисление всех возможных вариаций фигур, доступных для рендера
 */
public enum OGLESRenderTypes {
    GL_NULL {
        @Override
        public void setShaderCode(int unused_type, Shaders shader) {

        }

        @Override
        public void addVertexInStack(float unused_X, float unused_Y, float unused_Z) {

        }

        @Override
        public void preCompileShader() {

        }

        @Override
        public void addTexturesInStack(float X, float Y) {

        }
    },
    GL_LINES {
        @Override
        public void setShaderCode(int type, Shaders shader) {

        }

        @Override
        public void addVertexInStack(float X, float Y, float Z) {

        }

        @Override
        public void preCompileShader() {

        }

        @Override
        public void addTexturesInStack(float X, float Y) {

        }
    },
    GL_TRIANGLES {
        private final RenderTarget renderTarget = new RenderTriangle();

        @Override
        public void setShaderCode(int type, Shaders shader) {
            renderTarget.setShaderCode(type, shader);
        }

        @Override
        public void addVertexInStack(float X, float Y, float Z) {
            renderTarget.addVertexInStack(X, Y, Z);
        }

        @Override
        public void preCompileShader() {
            renderTarget.preCompileShader();
        }

        @Override
        public void addTexturesInStack(float X, float Y) {
            renderTarget.addTextureInStack(X, Y);
        }
    },
    GL_TEXTURED_TRIANGLES {
        private final RenderTarget renderTarget = new RenderTexturedTriangle();

        @Override
        public void setShaderCode(int type, Shaders shader) {
            renderTarget.setShaderCode(type, shader);
        }

        @Override
        public void addVertexInStack(float X, float Y, float Z) {
            renderTarget.addVertexInStack(X, Y, Z);
        }

        @Override
        public void preCompileShader() {
            renderTarget.preCompileShader();
        }

        @Override
        public void addTexturesInStack(float X, float Y) {
            renderTarget.addTextureInStack(X, Y);
        }
    };


    public abstract void preCompileShader();

    public abstract void setShaderCode(int type, Shaders shader);

    public abstract void addVertexInStack(float X, float Y, float Z);

    public abstract void addTexturesInStack(float X, float Y);

}
