package com.company.ThreeDimensionalModel;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;
import java.util.List;
import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class MainRenderThreeDModel {
    private long window;

    private final int width = 800;
    private final int height = 600;

    public void run() {
        System.out.println("LWJGL " + Version.getVersion());

        init();
        loop();

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
        window = glfwCreateWindow(width, height, "MainRenderer", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true);
            if (key == GLFW_KEY_LEFT && action == GLFW_REPEAT)
                rotationStatus[1] -= 0.02;
            if (key == GLFW_KEY_RIGHT && action == GLFW_REPEAT)
                rotationStatus[1] += 0.02;
            if (key == GLFW_KEY_W && action == GLFW_REPEAT)
                rotationStatus[2] -= 0.02;
            if (key == GLFW_KEY_S && action == GLFW_REPEAT)
                rotationStatus[2] += 0.02;
            if (key == GLFW_KEY_A && action == GLFW_REPEAT)
                rotationStatus[0] -= 0.02;
            if (key == GLFW_KEY_D && action == GLFW_REPEAT)
                rotationStatus[0] += 0.02;
        });

        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            glfwGetWindowSize(window, pWidth, pHeight);

            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            assert vidmode != null;
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        }

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);

        glfwShowWindow(window);
    }

    //-----------------------------Основная часть кода-----------------------------------

    final void getLightingColor(double[] nextPolygon, double[] ambientLightning, LightSource[] lightSources, byte lightType) {
        double color = 0;
        switch (lightType) {
            case 0:
                color = Math.random() % 0.33 + 0.33;
                break;
            case 1:
                for (LightSource lightSource : lightSources) color += lightSource.gouraudLightEquasion(nextPolygon);
                break;
            case 2:

                break;
            default: color = 0.5;
                break;
        }

        glColor3d(color + ambientLightning[0],
                color + ambientLightning[1],
                color + ambientLightning[2]
        );
    }

    private void bindTextures(Texture[] textures, int[] tex, int numOfTextures) {
        glGenTextures(tex);

        for (byte i = 0; i < numOfTextures; i++) {
            glBindTexture(GL_TEXTURE_2D, tex[i]);

            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);

            //GL_LINEAR
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, textures[i].getWidth(), textures[i].getHeight(), 0, GL_RGB, GL_FLOAT, textures[i].getPixels());
        }

        glBindTexture(GL_TEXTURE_2D, 0);
    }

    private String[] initParamsPath() {
        String[] paramsPath = new String[11];

        paramsPath[0] = "/home/anasko/github-projects/TestLWJGL/src/com/company/ThreeDimensionalModel/mapinfo/mapParams.txt";

        paramsPath[1] = "/home/anasko/github-projects/TestLWJGL/src/com/company/ThreeDimensionalModel/mapinfo/models/modelsPath.txt";
        paramsPath[2] = "/home/anasko/github-projects/TestLWJGL/src/com/company/ThreeDimensionalModel/mapinfo/models/modelsSize.txt";
        paramsPath[3] = "/home/anasko/github-projects/TestLWJGL/src/com/company/ThreeDimensionalModel/mapinfo/models/modelsPosition.txt";
        paramsPath[4] = "/home/anasko/github-projects/TestLWJGL/src/com/company/ThreeDimensionalModel/mapinfo/models/modelsTexNum.txt";

        paramsPath[5] = "/home/anasko/github-projects/TestLWJGL/src/com/company/ThreeDimensionalModel/mapinfo/lightSources/lightsPosition.txt";
        paramsPath[6] = "/home/anasko/github-projects/TestLWJGL/src/com/company/ThreeDimensionalModel/mapinfo/lightSources/lightsSpeed.txt";
        paramsPath[7] = "/home/anasko/github-projects/TestLWJGL/src/com/company/ThreeDimensionalModel/mapinfo/lightSources/lightsRadius.txt";

        paramsPath[8] = "/home/anasko/github-projects/TestLWJGL/src/com/company/ThreeDimensionalModel/mapinfo/textures/texturesPath.txt";
        paramsPath[9] = "/home/anasko/github-projects/TestLWJGL/src/com/company/ThreeDimensionalModel/mapinfo/textures/texturesWidth.txt";
        paramsPath[10] = "/home/anasko/github-projects/TestLWJGL/src/com/company/ThreeDimensionalModel/mapinfo/textures/texturesHeight.txt";

        return paramsPath;
    }

    private final double[] rotationStatus = {1.4, 0, 1.7};

    private void loop() {

        GL.createCapabilities();

        //----------переменные, используемые в ходе работы------------

        //Переменная, получающая координаты следующего "face" - полигона
        double[][] nextPolygon;

        //Переменная, получающая координаты треугольной части текстуры, которые нужно использовать
        double[][] textureCorners;

        //Массивы переменных, позволяющие собственноручно вращать сцену
        double[] rotXYZ;
        double[] resXYZ = new double[3];

        //Инициализация расположения параметров карты
        String[] paramsPath = initParamsPath();

        //Список параметров карты
        double[][] mapParams = mapInitializers.init2DParams(paramsPath[0], 4);

        //Переменная, задающая размеры карты относительно самой себя
        double mapSize = mapParams[0][0];

        //Массив, отвечающий за сдвиг координат всей карты
        double[] sceneDisplacement = mapParams[1];

        //Массив, задающий яркость фонового освещения
        double[] ambientLightning = mapParams[2];

        //Инициализация блока вращения карты
        MapRotation mapRotation = new MapRotation();

        //Инициализация перспективных преобразований **КОСТЫЛЬНОЕ**
        double near = mapParams[3][0];
        double far = mapParams[3][1];
        double fov = mapParams[3][2];

        PerspectiveConversions perspectiveConversions = new PerspectiveConversions(width, height, near, far, fov);

        //-------------Инициализация моделей--------------

        int numOfModels = 7;

        List<String> modelsPath = mapInitializers.init1DParams(paramsPath[1], numOfModels);

        List<String> modelsSize = mapInitializers.init1DParams(paramsPath[2], numOfModels);

        double[][] modelsPos = mapInitializers.init2DParams(paramsPath[3], numOfModels);

        List<String> texNum = mapInitializers.init1DParams(paramsPath[4], numOfModels);

        Model[] models = mapInitializers.initModels(modelsPath, modelsSize, modelsPos, texNum, numOfModels);


        //-------------Инициализация света----------------

        byte lightType = 1;

        int numOfLights = 2;

        double[][] lightsPos = mapInitializers.init2DParams(paramsPath[5], numOfLights);

        double[][] lightsSpeed = mapInitializers.init2DParams(paramsPath[6], numOfLights);

        List<String> lightsRadius =mapInitializers.init1DParams(paramsPath[7], numOfLights);

        LightSource[] lightSources = mapInitializers.initLightSources(lightsPos, lightsSpeed, lightsRadius, numOfLights);



        //------------Инициализация текстур--------------

        int numOfTextures = 6;

        int[] tex = new int[numOfTextures];

        List<String> texturesPath = mapInitializers.init1DParams(paramsPath[8], numOfTextures);

        List<String> texturesWidth = mapInitializers.init1DParams(paramsPath[9], numOfTextures);

        List<String> texturesHeight = mapInitializers.init1DParams(paramsPath[10], numOfTextures);

        Texture[] textures = mapInitializers.initTextures(texturesPath, texturesWidth, texturesHeight, numOfTextures);

        bindTextures(textures, tex, numOfTextures);


        //-------------Инициализация окна----------------

        GL.createCapabilities();

        glClearColor((float) ambientLightning[0], (float) ambientLightning[1], (float) ambientLightning[2], 0.0f);

        glMatrixMode(GL_PROJECTION);

        /*
         *glCullFace(GL_BACK);
         *glEnable(GL_CULL_FACE);
        */

        glFrustum(-1, 1, -1, 1, near,far);

        glRotated(0, 1,0, 0);
        glRotated(0, 0, 1, 0);
        glRotated(0, 0, 0, 1);

        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LESS);


        //----------Основной цикл работы с окном----------

        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            //Поворот глобальной матрицы объектов в кадре
            //System.out.println(rotationStatus[0] + " " + rotationStatus[1] + " " + rotationStatus[2]);

            glMatrixMode(GL_MODELVIEW);

            //Движение всех источников света
            for (int i = 0; i < numOfLights; i++)
                lightSources[i].moveLightSource();


            //Рисую все источники света
            for (int i = 0; i < numOfLights; i++) {
                glBegin(GL_TRIANGLES);
                    glColor3d(1, 1, 1);
                    glVertex3d(lightSources[i].getX() + 0.05, lightSources[i].getY(), lightSources[i].getZ());
                    glVertex3d(lightSources[i].getX() + 0.1, lightSources[i].getY() + 0.1, lightSources[i].getZ() + 0.1);
                    glVertex3d(lightSources[i].getX(), lightSources[i].getY() + 0.1, lightSources[i].getZ() + 0.1);
                    //System.out.println(lightSources[i].getX() + " " + lightSources[i].getY() + " " + lightSources[i].getZ());
                glEnd();
            }


            //Рисую все модели на экран, накладывая текстуры и освещение
            for (int mdl = 0; mdl < numOfModels; mdl++) {
                try {
                    while ((nextPolygon = models[mdl].getNextFace()) != null) {

                        glBindTexture(GL_TEXTURE_2D, tex[models[mdl].getTexNum()]);
                        glEnable(GL_TEXTURE_2D);
                        textureCorners = textures[models[mdl].getTexNum()].getNextCorners();

                        glBegin(GL_TRIANGLES);

                        for (int i = 0; i < 3; i++) {

                            //Вращаю курту
                            rotXYZ = mapRotation.getNewRot(nextPolygon[i][0], nextPolygon[i][1], nextPolygon[i][2], rotationStatus[0], rotationStatus[1], rotationStatus[2]);

                            //Применяю перспективу
                            rotXYZ = perspectiveConversions.getPerspectiveConversions(rotXYZ);

                            //Изменяю размеры карты и смещаю по осям XYZ
                            resXYZ[0] = (rotXYZ[0]) * mapSize + sceneDisplacement[0];
                            resXYZ[1] = (rotXYZ[1]) * mapSize + sceneDisplacement[1];
                            resXYZ[2] = (rotXYZ[2]) * mapSize + sceneDisplacement[2];

                            //Получаю освещенность каждой вершины
                            getLightingColor(resXYZ, ambientLightning, lightSources, lightType);

                            //Закрепляю за следующей координатой полигона один из углов текстуры
                            glTexCoord2d(textureCorners[i][0], textureCorners[i][1]);
                            //Кидаю в кадр точку полигона
                            glVertex3d(resXYZ[0],
                                    resXYZ[1],
                                    resXYZ[2]

                            );
                        }
                        glEnd();


                        glDisable(GL_TEXTURE_2D);
                        glBindTexture(GL_TEXTURE_2D, 0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            glMatrixMode(GL_PROJECTION);

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }
}
