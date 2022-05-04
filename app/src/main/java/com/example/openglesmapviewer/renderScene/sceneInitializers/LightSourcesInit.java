package com.example.openglesmapviewer.renderScene.sceneInitializers;

import com.example.openglesmapviewer.renderScene.SceneGlobals;
import com.example.openglesmapviewer.renderScene.SceneMemory;
import com.example.openglesmapviewer.renderScene.primitives.LightSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class LightSourcesInit {
    //todo: инициализация списка источников света


    private static ArrayList<String[]> initLightPositions(String pathToLightPosition) {
        ArrayList<String[]> modelPositions = new ArrayList<>();

        try(InputStream inputStream = SceneGlobals.getContext().getAssets().open(pathToLightPosition)){
            Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");

            String[] lines = (scanner.hasNext() ? scanner.next() : "").split("\\r\\n|\\n");


            for (String line: lines) {
                modelPositions.add(line.split(" "));
            }

            return modelPositions;
        }
        catch (IOException e){
            e.printStackTrace();
            return modelPositions;
        }
    }


    private static ArrayList<String[]> initLightSpeeds(String pathToLightSpeed) {
        ArrayList<String[]> modelPositions = new ArrayList<>();

        try(InputStream inputStream = SceneGlobals.getContext().getAssets().open(pathToLightSpeed)){
            Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");

            String[] lines = (scanner.hasNext() ? scanner.next() : "").split("\\r\\n|\\n");


            for (String line: lines) {
                modelPositions.add(line.split(" "));
            }

            return modelPositions;
        }
        catch (IOException e){
            e.printStackTrace();
            return modelPositions;
        }
    }

    private static String[] initLightRadiuses(String pathToLightRadius) {
        ArrayList<String[]> modelPositions = new ArrayList<>();

        try(InputStream inputStream = SceneGlobals.getContext().getAssets().open(pathToLightRadius)){
            Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");

            return (scanner.hasNext() ? scanner.next() : "").split("\\r\\n|\\n");
        }
        catch (IOException e){
            e.printStackTrace();
            return new String[]{""};
        }
    }



    private static void initLights(String pathToLightSources) {
        ArrayList<String[]> lightPositions = initLightPositions(pathToLightSources + "lightPositions.txt");
        ArrayList<String[]> lightSpeeds = initLightSpeeds(pathToLightSources + "lightSpeeds.txt");
        String[] lightRadiuses = initLightRadiuses(pathToLightSources + "lightRadiuses.txt");

        assert (lightPositions.size() == lightRadiuses.length &&
                lightRadiuses.length == lightSpeeds.size());

        ArrayList<LightSource> lightSourcesList = new ArrayList<>();

        for (int index = 0; index < lightPositions.size(); index++) {
            LightSource lightSource = new LightSource(
                    new float[]{Float.parseFloat(lightPositions.get(index)[0]),
                            Float.parseFloat(lightPositions.get(index)[1]),
                            Float.parseFloat(lightPositions.get(index)[2])},

                    new float[]{Float.parseFloat(lightSpeeds.get(index)[0]),
                            Float.parseFloat(lightSpeeds.get(index)[1]),
                            Float.parseFloat(lightSpeeds.get(index)[2])},

                    Float.parseFloat(lightRadiuses[index])
            );

            lightSourcesList.add(lightSource);
        }

        SceneMemory.setLightSources(lightSourcesList);
    }


    public static void initLightSources() {
        initLights("maps/" + SceneMemory.getMapName() + "/lightSources/");
    }
}
