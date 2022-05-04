package com.example.openglesmapviewer.renderScene;

import static android.opengl.GLES20.GL_TEXTURE_2D;

import com.example.openglesmapviewer.OGLESRenderer;
import com.example.openglesmapviewer.oglestransformer.OGLESRenderTypes;
import com.example.openglesmapviewer.oglestransformer.OGLESTransformer;
import com.example.openglesmapviewer.renderScene.primitives.ComplexModel;
import com.example.openglesmapviewer.renderScene.sceneComputations.MapMovement;
import com.example.openglesmapviewer.renderScene.sceneComputations.MapResize;
import com.example.openglesmapviewer.renderScene.sceneComputations.MapRotation;

import static com.example.openglesmapviewer.oglestransformer.OGLESTransformer.*;
import static com.example.openglesmapviewer.oglestransformer.OGLESRenderTypes.*;
import static com.example.openglesmapviewer.renderScene.SceneGlobals.*;

import android.opengl.GLES20;
import android.opengl.GLES30;

public class SceneLoop {
    
    public static void mapLoop() {

        float[][] currentPolygon;
        float[][] textureCorners;

        GLES30.glClearColor(getSceneColorR(), getSceneColorG(), getSceneColorB(), 1f);

        //Проходимся по все комплексным моделям сцены
        for (ComplexModel object: SceneMemory.getComplexModels()) {
            //Запрашиваем следующий ПОЛИГОН в комплексной модели сцены

            //System.out.println(object.texture.getNumInMemory());

            while ((currentPolygon = object.getNextFace()) != null) {
                textureCorners = object.texture.getNextCorners();

                glBegin(GL_TEXTURED_TRIANGLES);
                //OGLESTransformer.glColor3d(0.5, 0.5, 0.5);

                //Проходимся по каждой ВЕРШИНЕ полигона
                for (int index = 0; index < 3; index++) {
                    GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
                    GLES20.glBindTexture(GL_TEXTURE_2D, SceneGlobals.oglTextures[object.texture.getNumInMemory()]);

                    //Применяем матрицу вращения к полигону
                    currentPolygon[index] = MapRotation.rotate(currentPolygon[index], getRotationStatus());

                    //Применяем смещение к полигону
                    currentPolygon[index] = MapMovement.move(currentPolygon[index], getPositionStatus());

                    //Применяем изменение в размерах к полигону
                    currentPolygon[index] = MapResize.resize(currentPolygon[index], getMapSizeStatus());

                    //Закрепляю за следующей координатой полигона один из углов текстуры
                    glTexCoord2d(textureCorners[index][0], textureCorners[index][1]);

                    //Добавляем в стек вершину
                    glVertex3d(currentPolygon[index][0], currentPolygon[index][1], currentPolygon[index][2]);
                }

                glEnd();

                GLES20.glBindTexture(GL_TEXTURE_2D, 0);
            }
        }
    }
}
