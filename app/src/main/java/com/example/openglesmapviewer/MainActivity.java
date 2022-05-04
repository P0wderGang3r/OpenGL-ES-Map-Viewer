package com.example.openglesmapviewer;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.content.Context;
import android.view.MotionEvent;

import com.example.openglesmapviewer.renderScene.SceneGlobals;

class MyGLSurfaceView extends GLSurfaceView {

    private final float TOUCH_SCALE_FACTOR = 180.0f / 32000;
    private float previousX;
    private float previousY;

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:

                float dx = x - previousX;
                float dy = y - previousY;

                float[] currentRotationStatus = SceneGlobals.getRotationStatus();

                currentRotationStatus[0] -= dx * TOUCH_SCALE_FACTOR;
                currentRotationStatus[2] += dy * TOUCH_SCALE_FACTOR;

                SceneGlobals.setRotationStatus(currentRotationStatus);
                requestRender();
        }

        previousX = x;
        previousY = y;
        return true;
    }
    
    public MyGLSurfaceView(Context context){
        super(context);

        // Создание OpenGL ES 3.0 контекста
        setEGLContextClientVersion(3);

        OGLESRenderer renderer = new OGLESRenderer();

        // Выбираем рендер для рисования на GLSurfaceView
        setRenderer(renderer);
    }
}

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_main);

        SceneGlobals.setContext(this);

        GLSurfaceView gLView = new MyGLSurfaceView(this);

        // Render the view only when there is a change in the drawing data
        gLView.setRenderMode(MyGLSurfaceView.RENDERMODE_WHEN_DIRTY);

        setContentView(gLView);
    }
}