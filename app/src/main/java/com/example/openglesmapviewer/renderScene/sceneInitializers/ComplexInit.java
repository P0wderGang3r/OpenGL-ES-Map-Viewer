package com.example.openglesmapviewer.renderScene.sceneInitializers;

import com.example.openglesmapviewer.renderScene.SceneGlobals;
import com.example.openglesmapviewer.renderScene.SceneMemory;
import com.example.openglesmapviewer.renderScene.primitives.ComplexModel;
import com.example.openglesmapviewer.renderScene.primitives.Model;
import com.example.openglesmapviewer.renderScene.primitives.Texture;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class ComplexInit {

    private static Model findModel(String modelName) {
        for (Model model: SceneMemory.getModels()) {
            if (model.getModelName().equals(modelName)) {
                return model;
            }
        }
        return new Model("", "");
    }

    private static Texture findTexture(String textureName) {
        for (Texture texture: SceneMemory.getTextures()) {
            if (texture.getTextureName().equals(textureName)) {
                return texture;
            }
        }
        return SceneMemory.getTextures().get(0);
    }

    private static String[] initModelNames(String pathToModelNames) {

        try(InputStream inputStream = SceneGlobals.getContext().getAssets().open(pathToModelNames)){
            Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");

            return (scanner.hasNext() ? scanner.next() : "").split("\\r\\n|\\n");
            }
        catch (IOException e){
            e.printStackTrace();
            return new String[]{""};
        }
    }

    private static ArrayList<String[]> initModelPositions(String pathToModelPositions) {
        ArrayList<String[]> modelPositions = new ArrayList<>();

        try(InputStream inputStream = SceneGlobals.getContext().getAssets().open(pathToModelPositions)){
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

    private static String[] initModelSizes(String pathToModelSizes) {

        try(InputStream inputStream = SceneGlobals.getContext().getAssets().open(pathToModelSizes)){
            Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");

            return (scanner.hasNext() ? scanner.next() : "").split("\\r\\n|\\n");
        }
        catch (IOException e){
            e.printStackTrace();
            return new String[]{""};
        }
    }

    private static String[] initModelTextures(String pathToModelTextures) {

        try(InputStream inputStream = SceneGlobals.getContext().getAssets().open(pathToModelTextures)){
            Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");

            return (scanner.hasNext() ? scanner.next() : "").split("\\r\\n|\\n");
        }
        catch (IOException e){
            e.printStackTrace();
            return new String[]{""};
        }
    }

    private static void initComplexObject(String pathToObjectProperties) {
        String[] modelNames = initModelNames(pathToObjectProperties + "modelNames.txt");
        ArrayList<String[]> modelPositions = initModelPositions(pathToObjectProperties + "modelPositions.txt");
        String[] modelSizes = initModelSizes(pathToObjectProperties + "modelSizes.txt");
        String[] modelTextures = initModelTextures(pathToObjectProperties + "modelTextures.txt");

        assert (modelNames.length == modelPositions.size() &&
                modelPositions.size() == modelSizes.length &&
                modelSizes.length == modelTextures.length);

        ArrayList<ComplexModel> complexObjectList = new ArrayList<>();

        for (int index = 0; index < modelNames.length; index++) {
            ComplexModel complexObject = new ComplexModel(
                    findModel(modelNames[index]),
                    findTexture(modelTextures[index]),
                    Float.parseFloat(modelSizes[index]),
                    new float[]{Float.parseFloat(modelPositions.get(index)[0]),
                            Float.parseFloat(modelPositions.get(index)[1]),
                            Float.parseFloat(modelPositions.get(index)[2])}
            );

            complexObjectList.add(complexObject);
        }

        SceneMemory.setComplexModels(complexObjectList);
    }

    public static void initComplexObjects() {
        initComplexObject("maps/" + SceneMemory.getMapName() + "/complexObjects/");
    }
}
