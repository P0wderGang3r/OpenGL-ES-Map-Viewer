package com.company.Rotating_Triangle;

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

public class Rotating_Triangle {
    private long window;

    double r, g, b;

    double x, y;
    Dot[] dots = new Dot[3];

    double degDelta = 0.1;

    public void run() {
        System.out.println("LWJGL " + Version.getVersion());

        initArgs();
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
        window = glfwCreateWindow(500, 500, "Rotating_Triangle", NULL, NULL);
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
        glfwSwapInterval(1);

        glfwShowWindow(window);
    }

    private void loop() {
        GL.createCapabilities();

        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            r = 1;
            g = 1;
            b = 1;

            glBegin(GL_TRIANGLES);

            glColor3d(r, g, b);

            for (int i = 0; i < 3; i++) {

                dots[i].getNewXY();
                x = dots[i].getX();
                y = dots[i].getY();

                glVertex2d(x, y);
            }

            glEnd();

            glfwSwapBuffers(window);

            glfwPollEvents();
        }
    }

    void initArgs() {

        double x = 0;
        double y = -0.5;
        dots[0] = new Dot();
        dots[0].setXYR(x, y, degDelta);

        x = -0.5;
        y = 0.5;
        dots[1] = new Dot();
        dots[1].setXYR(x, y, degDelta);

        x = 0.0;
        y = 0.5;
        dots[2] = new Dot();
        dots[2].setXYR(x, y, degDelta);
    }


}
