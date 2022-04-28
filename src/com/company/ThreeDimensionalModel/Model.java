package com.company.ThreeDimensionalModel;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Model {

    List<String> inpModel;
    private int saveF;
    private int lastF = -1;
    private final double size;
    private final double[] posOfMdl;
    private final int texNum;
    private double normalizedSize = 0;

    public int getTexNum() {
        return texNum;
    }

    private String getVStr(int fNum, int vNumToFind) {
        //System.out.println(fNum + " " + vNumToFind);
        int localNumOfLine = Integer.parseInt(inpModel.get(fNum).split(" ")[vNumToFind]);
        return inpModel.get(localNumOfLine);
    }

    public double[][] getNextFace() {
        double[][] res = new double[3][3];
        String vLine;

        if ( lastF < inpModel.size() && inpModel.get(lastF).split(" ")[0].equals("f")) {
            //System.out.println(inpModel.get(lastF).split(" ")[1]);

            for (int i = 0; i < 3; i++) {
                vLine = getVStr(lastF, i + 1);
                res[i][0] = Double.parseDouble(vLine.split(" ")[1]) * size * normalizedSize + posOfMdl[0];
                res[i][1] = Double.parseDouble(vLine.split(" ")[2]) * size * normalizedSize + posOfMdl[1];
                res[i][2] = Double.parseDouble(vLine.split(" ")[3]) * size * normalizedSize + posOfMdl[2];
            }
            lastF += 1;
        } else {
            lastF = saveF;
            return null;
        }

        /*
            * for (int i = 0; i < 3; i++)
            * System.out.println(res[0][i] + " " + res[1][i] + " " + res[2][i]);
        */

        return res;
    }

    private void modelNormalize() {
        int i = 0;
        double[] nextCoord = {0, 0, 0};

        while (inpModel.get(i).split(" ")[0].equals("v")) {
            nextCoord[0] = Double.parseDouble(inpModel.get(i).split(" ")[1]);
            nextCoord[1] = Double.parseDouble(inpModel.get(i).split(" ")[2]);
            nextCoord[2] = Double.parseDouble(inpModel.get(i).split(" ")[3]);
            for (int j = 0; j < 3; j++)
                normalizedSize = Math.max(normalizedSize, Math.abs(nextCoord[j]));
            i++;
        }
        normalizedSize = 1 / normalizedSize;
        //System.out.println(normalizedSize);
    }

    Model(String inp, double size, double[] posOfMdl, int texNum) {

        Path path = Paths.get(inp);
        this.size = size;
        this.posOfMdl = posOfMdl;
        this.texNum = texNum;

        try {
            inpModel = Files.readAllLines(path);

            int i = 0;

            while (lastF == -1) {
                lastF = inpModel.get(i).indexOf("f");
                i++;
            }

            lastF = i - 1;
            saveF = lastF;

            modelNormalize();

            /*
                * for (String s : inpModel) System.out.println(s);
                * System.out.println(lastF);
            */

        } catch (Exception e) {
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
