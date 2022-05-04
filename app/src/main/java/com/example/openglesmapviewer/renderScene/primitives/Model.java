package com.example.openglesmapviewer.renderScene.primitives;

import com.example.openglesmapviewer.renderScene.SceneGlobals;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Model {
    private ArrayList<String> inpModel = new ArrayList<>();

    public int saveF;
    public int lastF = -1;
    private float normalizedSize = 0;

    private final String modelName;

    //---------------------Get-теры------------------------

    public String getModelName() {
        return modelName;
    }

    public List<String> getInpModel() {
        return inpModel;
    }

    public float getNormalizedSize() {
        return normalizedSize;
    }

    //---------------------Остальное-----------------------

    private void modelNormalize() {
        int i = 0;
        float[] nextCoord = {0, 0, 0};

        while (inpModel.get(i).split(" ")[0].equals("v")) {
            nextCoord[0] = Float.parseFloat(inpModel.get(i).split(" ")[1]);
            nextCoord[1] = Float.parseFloat(inpModel.get(i).split(" ")[2]);
            nextCoord[2] = Float.parseFloat(inpModel.get(i).split(" ")[3]);
            for (int j = 0; j < 3; j++)
                normalizedSize = Math.max(normalizedSize, Math.abs(nextCoord[j]));
            i++;
        }
        normalizedSize = 1 / normalizedSize;
        //System.out.println(normalizedSize);
    }

    public Model(String modelName, String pathToModel) {
        this.modelName = modelName;


        try(InputStream inputStream = SceneGlobals.getContext().getAssets().open(pathToModel)){
            Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
            String[] fileData = (scanner.hasNext() ? scanner.next() : "").split("\\r\\n|\\n");

            inpModel.addAll(Arrays.asList(fileData));

            int i = 0;

            while (lastF == -1) {
                lastF = inpModel.get(i).indexOf("f");
                i++;
            }

            lastF = i - 1;
            saveF = lastF;

            modelNormalize();
        }
        catch (IOException e){
            e.printStackTrace();
            inpModel = new ArrayList<>();
            inpModel.add("v 0 0 0");
            inpModel.add("f 0 0 0");
            normalizedSize = 1;
            lastF = 1;
            saveF = 1;
        }
    }

}
