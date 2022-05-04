package com.example.openglesmapviewer.renderScene.sceneInitializers;

import com.example.openglesmapviewer.renderScene.SceneGlobals;
import com.example.openglesmapviewer.renderScene.SceneMemory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class SceneParametersInit {

    private static ArrayList<String[]> initAllParameters(String pathToParameters) {
        ArrayList<String[]> parameters = new ArrayList<>();

        try (InputStream inputStream = SceneGlobals.getContext().getAssets().open(pathToParameters)) {
            Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");

            String[] lines = (scanner.hasNext() ? scanner.next() : "").split("\\r\\n|\\n");


            for (String line : lines) {
                parameters.add(line.split(" "));
            }

            return parameters;
        } catch (IOException e) {
            e.printStackTrace();
            return parameters;
        }
    }

    private static void initScene(String pathToParameters) {
        ArrayList<String[]> allParameters = initAllParameters(pathToParameters);

        //Устанавливаем размер сцены
        SceneGlobals.setMapSizeStatus(Float.parseFloat(allParameters.get(0)[0]));

        //Устанавливаем смещение сцены
        SceneGlobals.setPositionStatus(
                new float[]{Float.parseFloat(allParameters.get(1)[0]),
                        Float.parseFloat(allParameters.get(1)[1]),
                        Float.parseFloat(allParameters.get(1)[2])
                }
        );

        //Устанавливаем поворот сцены
        SceneGlobals.setRotationStatus(
                new float[]{Float.parseFloat(allParameters.get(2)[0]),
                        Float.parseFloat(allParameters.get(2)[1]),
                        Float.parseFloat(allParameters.get(2)[2])
                }
        );

        //Устанавливаем фон сцены
        SceneGlobals.setSceneColorStatus(
                new float[]{Float.parseFloat(allParameters.get(3)[0]),
                        Float.parseFloat(allParameters.get(3)[1]),
                        Float.parseFloat(allParameters.get(3)[2])
                }
        );
    }


    public static void initSceneParameters() {
        initScene("maps/" + SceneMemory.getMapName() + "/mapParameters.txt");

    }
}
