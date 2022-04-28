package com.company.ThreeDimensionalModel;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class mapInitializers {


    public static double[][] init2DParams(String inp, int numOfParams) {
        Path path = Paths.get(inp);
        double[][] finalParams = new double[numOfParams][3];

        for (int i = 0; i < numOfParams; i++) {
            finalParams[i][0] = 0;
            finalParams[i][1] = 0;
            finalParams[i][2] = 0;
        }

        try {
            List<String> params = Files.readAllLines(path);

            for (int i = 0; i < numOfParams; i++) {
                finalParams[i][0] = Double.parseDouble(params.get(i).split(" ")[0]);
                finalParams[i][1] = Double.parseDouble(params.get(i).split(" ")[1]);
                finalParams[i][2] = Double.parseDouble(params.get(i).split(" ")[2]);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return finalParams;
    }

    public static List<String> init1DParams(String inp, int numOfParams) {
        List<String> params = new ArrayList<>(numOfParams);
        Path path = Paths.get(inp);

        try {
            params = Files.readAllLines(path);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return params;
    }


    //------------Инициализация моделей-------------

    public static Model[] initModels(List<String> modelsPath, List<String> modelsSize, double[][] modelsPos, List<String> texNum, int numOfModels) {
        Model[] models = new Model[numOfModels];

        for (int i = 0; i < numOfModels; i++) {
            try {
                models[i] = new Model(modelsPath.get(i), Double.parseDouble(modelsSize.get(i)), modelsPos[i], Integer.parseInt(texNum.get(i)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return models;
    }


    //----------Инициализация света-----------------

    public static LightSource[] initLightSources(double[][] lightsPos, double[][] lightsSpeed, List<String> lightsRadius, int numOfLights) {
        LightSource[] lightSources = new LightSource[numOfLights];

        for (int i = 0; i < numOfLights; i++) {
            try {
                lightSources[i] = new LightSource(lightsPos[i], lightsSpeed[i], Double.parseDouble(lightsRadius.get(i)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return lightSources;
    }


    //------------Инициализация текстур-------------

    public static Texture[] initTextures(List<String> texturesPath, List<String> texturesWidth, List<String> texturesHeight, int numOfTextures) {
        Texture[] textures = new Texture[numOfTextures];

        for (int i = 0; i < numOfTextures; i++) {
            try {
                textures[i] = new Texture(texturesPath.get(i), Integer.parseInt(texturesWidth.get(i)), Integer.parseInt(texturesHeight.get(i)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return textures;
    }
}
