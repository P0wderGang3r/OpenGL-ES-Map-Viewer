package com.example.openglesmapviewer.renderScene.primitives;

public class ComplexModel {
    public Model model;
    public Texture texture;

    public ComplexModel(Model model, Texture texture, float size, float[] posOfMdl) {
        this.model = model;
        this.texture = texture;
        this.size = size;
        this.posOfMdl = posOfMdl;
    }

    //-------------------------------------Работа с моделью-----------------------------------------

    private final float size;
    private final float[] posOfMdl;

    private String getVStr(int fNum, int vNumToFind) {
        //System.out.println(fNum + " " + vNumToFind);
        int localNumOfLine = Integer.parseInt(model.getInpModel().get(fNum).split(" ")[vNumToFind]);
        return model.getInpModel().get(localNumOfLine);
    }

    public float[][] getNextFace() {

        float[][] res = new float[3][3];
        String vLine;

        if ( model.lastF < model.getInpModel().size() && model.getInpModel().get(model.lastF).split(" ")[0].equals("f")) {
            //System.out.println(inpModel.get(lastF).split(" ")[1]);

            for (int i = 0; i < 3; i++) {
                vLine = getVStr(model.lastF, i + 1);
                res[i][0] = Float.parseFloat(vLine.split(" ")[1]) * size * model.getNormalizedSize() + posOfMdl[0];
                res[i][1] = Float.parseFloat(vLine.split(" ")[2]) * size * model.getNormalizedSize() + posOfMdl[1];
                res[i][2] = Float.parseFloat(vLine.split(" ")[3]) * size * model.getNormalizedSize() + posOfMdl[2];
            }
            model.lastF += 1;
        } else {
            model.lastF = model.saveF;
            return null;
        }

        /*
         * for (int i = 0; i < 3; i++)
         * System.out.println(res[0][i] + " " + res[1][i] + " " + res[2][i]);
         */

        return res;
    }
}
