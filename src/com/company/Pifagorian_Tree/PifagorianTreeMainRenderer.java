package com.company.Pifagorian_Tree;


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

public class PifagorianTreeMainRenderer {
    private long window;

    public void run() {
        System.out.println("LWJGL " + Version.getVersion());

        init();

        while (!glfwWindowShouldClose(window)) {
            loop_run(0, -0.75, 0.75, -Math.PI / 2);
            glfwSwapBuffers(window);
        }

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
        window = glfwCreateWindow(500, 500, "Pifagorian_Tree", NULL, NULL);
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

    double xNew;
    double yNew;

    double angle_left = Math.PI / 3;
    double angle_right = Math.PI / 6;
    double num = 0.02;

    private void loop_run(double x, double y, double length, double angle) {
        if (length > num) {
            length /= Math.sqrt(2);

            double fx = x + length * Math.cos(angle);
            double fy = y - length * Math.sin(angle);

            xNew = fx;
            yNew = fy;

            loop(x, y, xNew, yNew);

            x = fx;
            y = fy;

            loop_run(x, y, length, angle + this.angle_left);
            loop_run(x, y, length, angle - this.angle_right);
        }
    }

    private void loop(double xPrev, double yPrev, double xNext, double yNext) {
        GL.createCapabilities();
        Pifagorian_Tree pt = new Pifagorian_Tree();


        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glColor3d(1, 1, 1);

        //while (!glfwWindowShouldClose(window)) {
            glBegin(GL_LINES);

            glVertex2d(xPrev, yPrev);
            glVertex2d(xNext, yNext);

            glEnd();
            glfwPollEvents();

            if (xPrev == yPrev && xNext == yNext) {
                System.out.println("End");
            }
        //}
    }

}
