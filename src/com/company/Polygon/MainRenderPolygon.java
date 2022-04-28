package com.company.Polygon;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class MainRenderPolygon {
    private long window;

    public void run() {
        System.out.println("LWJGL " + Version.getVersion());

        init();
        loop();

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
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
        window = glfwCreateWindow(500, 500, "MainRenderer", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true);
        });

        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            glfwGetWindowSize(window, pWidth, pHeight);

            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        }

        glfwMakeContextCurrent(window);
        glfwSwapInterval(10);

        glfwShowWindow(window);
    }

    private void loop() {
        GL.createCapabilities();

        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        Polygon polygon = new Polygon();

        double deltaMoveX = 0;
        double deltaMoveY = 0;

        double[] xy = new double[2];

        polygon.setVertex_num(8);
        polygon.setRadius(1);
        try {
            polygon.initArgs();

            while (!glfwWindowShouldClose(window)) {

                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

                for (int i = 0; i < polygon.getVertex_num(); i++) {

                    glColor3d(Math.random(), Math.random(), Math.random());

                    glBegin(GL_TRIANGLES);

                    xy[0] = polygon.getX(deltaMoveX);
                    xy[1] = polygon.getY(deltaMoveY);

                    //System.out.println(xy[0] + " " + xy[1]);

                    glVertex2d(xy[0], xy[1]);

                    xy = polygon.getNextXY(deltaMoveX, deltaMoveY);

                    glVertex2d(xy[0], xy[1]);

                    xy = polygon.getOppositeXY(deltaMoveX, deltaMoveY);

                    glVertex2d(xy[0], xy[1]);

                    glEnd();
                }

                glfwSwapBuffers(window);

                glfwPollEvents();
            }
        } catch(Exception e) {
            System.out.println("Dumb sh*t, fix the problems");
        }
    }
}
