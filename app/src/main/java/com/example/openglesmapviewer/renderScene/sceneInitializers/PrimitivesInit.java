package com.example.openglesmapviewer.renderScene.sceneInitializers;

import com.example.openglesmapviewer.renderScene.SceneGlobals;
import com.example.openglesmapviewer.renderScene.SceneMemory;
import com.example.openglesmapviewer.renderScene.primitives.Model;
import com.example.openglesmapviewer.renderScene.primitives.Texture;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class PrimitivesInit {


    private static int initTextureWidth(String pathToTexture) {
        try (InputStream inputStream = SceneGlobals.getContext().getAssets().open(pathToTexture)) {
            Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
            String[] textureData = (scanner.hasNext() ? scanner.next() : "").split(" ");

            return Integer.parseInt(textureData[0]);
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static int initTextureHeight(String pathToTexture) {
        try (InputStream inputStream = SceneGlobals.getContext().getAssets().open(pathToTexture)) {
            Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
            String[] textureData = (scanner.hasNext() ? scanner.next() : "").split(" ");

            return Integer.parseInt(textureData[1]);
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static void initTextures(String pathToPreLoad) {
        ArrayList<Texture> textureList = new ArrayList<>();

        try(InputStream inputStream = SceneGlobals.getContext().getAssets().open(pathToPreLoad)){
            Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
            String[] textureData = (scanner.hasNext() ? scanner.next() : "").split("\\r\\n|\\n");

            for (String texture: textureData) {
                textureList.add(new Texture(texture, "drawable/" + texture));
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

        SceneMemory.setTextures(textureList);
    }

    private static void initModels(String pathToPreLoad) {
        ArrayList<Model> modelList = new ArrayList<>();

        try(InputStream inputStream = SceneGlobals.getContext().getAssets().open(pathToPreLoad)){
            Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
            String[] modelData = (scanner.hasNext() ? scanner.next() : "").split("\\r\\n|\\n");

            for (String model: modelData) {
                modelList.add(new Model(model, "models/" + model + ".txt"));
            }

        }
        catch (IOException e){

            e.printStackTrace();
        }

        SceneMemory.setModels(modelList);
    }


    public static void initPrimitiveObjects() {
        initModels("maps/" + SceneMemory.getMapName() + "/preload/modelsPath.txt");
        initTextures("maps/" + SceneMemory.getMapName() + "/preload/texturesPath.txt");
    }
}
