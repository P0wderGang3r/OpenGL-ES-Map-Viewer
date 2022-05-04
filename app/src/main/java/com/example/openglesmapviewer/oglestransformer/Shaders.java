package com.example.openglesmapviewer.oglestransformer;

import android.opengl.GLES20;

//----------------------------------------ШЕЙДЕРЫ-------------------------------------------

public enum Shaders {

    DEFAULT_VERTEX_SHADER {
        @Override
        public final String shaderCode() {
            return
                    "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 v_Position;" +
                        "void main() {" +
                            "gl_Position = uMVPMatrix * v_Position;" +
                        "}";
        }
    },
    DEFAULT_FRAGMENT_SHADER {
        @Override
        public final String shaderCode() {
            return
                    "precision mediump float;" +
                    "uniform vec4 v_Color;" +
                        "void main() {" +
                            "gl_FragColor = v_Color;" +
                        "}";
        }
    },
    TEXTURED_VERTEX_SHADER {
        @Override
        public final String shaderCode() {
            return
                    "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 v_Position;" +

                    "attribute vec2 a_Texture;" +
                    "varying vec2 v_Texture;" +

                    "attribute vec4 a_Gradient;" +
                    "varying vec4 v_Gradient;" +

                        "void main() {" +
                            "gl_Position = uMVPMatrix * v_Position;" +
                            "v_Texture = a_Texture;" +
                            "v_Gradient = a_Gradient;" +
                        "}";
        }
    },
    TEXTURED_FRAGMENT_SHADER {
        @Override
        public final String shaderCode() {
            return
                    "precision mediump float;" +
                    "uniform sampler2D u_TextureUnit;" +

                    "varying vec2 v_Texture;" +
                    "uniform vec4 v_Color;" +

                    "varying vec4 v_Gradient;" +

                        "void main() {" +
                            "gl_FragColor = texture2D(u_TextureUnit, v_Texture) * v_Color;" +
                        "}";
        }
    };

    public abstract String shaderCode();
}
