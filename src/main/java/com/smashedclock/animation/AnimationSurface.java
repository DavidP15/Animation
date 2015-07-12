package com.smashedclock.animation;

/**
 * Abstract Class that holds our main thread that calls update and draw on each sprite.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public abstract class AnimationSurface extends SurfaceView implements SurfaceHolder.Callback {

    /**
     * Nested class for the thread itself
     */
    class AnimationThread extends Thread {

        private final SurfaceHolder surfaceHolder;
        private Context context;
        private int fps = 0;
        private volatile boolean running = false;
        private boolean startAnimation = false;

        public AnimationThread(SurfaceHolder surfaceHolder, Context context) {
            this.surfaceHolder = surfaceHolder;
            this.context = context;
        }

        public void doStart() {
            synchronized(surfaceHolder) {
                //start game
                startAnimation = true;
            }
        }

        public void pause() {
            synchronized(surfaceHolder) {
                //set state to paused
                startAnimation = false;
            }
        }

        public void setRunning(boolean b) {
            running = b;
        }

        @Override
        public void run() {
            long time, sleepTime;
            while(running) {
                if(startAnimation) {
                    if(fps == 0) {
                        sleepTime = 20;
                    }
                    else {
                        sleepTime = 1000/fps;
                    }
                    Canvas c = null;
                    try {
                        c = surfaceHolder.lockCanvas(null);
                        synchronized(surfaceHolder) {
                            //figure out how much time it takes to execute the drawing so we can get an accurate fps.
                            time = System.nanoTime();
                            update();
                            doDraw(c);
                            time = (System.nanoTime() - time)/1000000L; //have to convert from nano to ms
                            sleepTime = sleepTime - time;
                            if(sleepTime > 0) {
                                try {
                                    Thread.sleep(sleepTime);
                                } catch (InterruptedException ex) {}
                            }
                            else {
                                Thread.yield();
                            }
                        }
                    }
                    finally {
                        if(c != null) {
                            surfaceHolder.unlockCanvasAndPost(c);
                        }
                    }
                }
            }
        }



        public void setFps(int fps) {
            this.fps = fps;
        }
    }

    private volatile AnimationThread thread;
    private Context context;

    /**
     *
     * @param context
     * @param attrs
     */
    public AnimationSurface(Context context, AttributeSet attrs){
        super(context, attrs);
        this.context = context;
        setFocusable(true);

    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //mainThread.setSurfaceSize(width, height);
        //screenWidth = width;
        //screenHeight = height;
    }

    public void surfaceCreated(SurfaceHolder holder) {

    }

    /**
     * When the surface is destroyed, just kill the thread
     * @param holder
     */
    public void surfaceDestroyed(SurfaceHolder holder) {
        killThread();
    }

    /**
     * sets the speed at which the animation occurs
     * @param fps
     */
    public void setFps(int fps) {
        thread.setFps(fps);

    }

    /**
     * begins the thread.
     */
    public void startThread() {
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        thread = new AnimationThread(holder, context);
        thread.setDaemon(true);
        thread.setRunning(true);
        thread.start();
        thread.doStart();
    }

    /**
     * stops the thread
     */
    public void killThread(){
        if(thread != null){
            boolean retry = true;
            thread.setRunning(false);
            thread = null;
        }
    }

    protected abstract void update();

    protected abstract void doDraw(Canvas c);
}