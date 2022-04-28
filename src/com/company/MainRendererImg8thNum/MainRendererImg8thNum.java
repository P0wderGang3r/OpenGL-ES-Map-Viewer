package com.company.MainRendererImg8thNum;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class MainRendererImg8thNum {

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
        window = glfwCreateWindow(512, 512, "MainRenderer", NULL, NULL);
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
        glfwSwapInterval(30);

        glfwShowWindow(window);
    }

    private void loop() {
        GL.createCapabilities();

        ImgConnect imgConnect = new ImgConnect();

        int width = 512;
        int height = 512;
        imgConnect.setWidth(width);
        imgConnect.setHeight(height);
        imgConnect.imgInit("C:\\Users\\P0wde\\IdeaProjects\\TestLVJGL\\src\\com\\company\\MainRendererImg8thNum\\texrick.data");

        byte numOfTex = 7;
        int[] tex = new int[numOfTex];

        glGenTextures(tex);

        for (byte i = 0; i < numOfTex; i++) {
            glBindTexture(GL_TEXTURE_2D, tex[i]);

            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);

            //GL_LINEAR
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_FLOAT, imgConnect.getImage(i));
        }

        glBindTexture(GL_TEXTURE_2D, 0);

        int currTex = 6;

        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        glMatrixMode(GL_PROJECTION);
        glFrustum(-1, 1, -1, 1, -1, 1);
        //glOrtho(-1, 1, -1, 1, -1, 1);
        //glViewpoint
        glMatrixMode(GL_MODELVIEW);

        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            //currTex++;

            //if (currTex == numOfTex)
              //  currTex = 0;

            glBindTexture(GL_TEXTURE_2D, tex[currTex]);

            glEnable(GL_TEXTURE_2D);

            glBegin((GL_QUADS));

            glTexCoord2d(0, 1); glVertex2d(-0.5, -0.5);
            glTexCoord2d(0, 0); glVertex2d(-1, 1);
            glTexCoord2d(1, 0); glVertex2d(0.5, 0.5);
            glTexCoord2d(1, 1); glVertex2d(1, -1);

            glEnd();

            glDisable(GL_TEXTURE_2D);

            glBindTexture(GL_TEXTURE_2D, 0);

            glfwSwapBuffers(window);

            glfwPollEvents();
        }
    }
}
