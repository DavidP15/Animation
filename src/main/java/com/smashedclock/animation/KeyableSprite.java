package com.smashedclock.animation;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * An abstract super class of a keyable sprite.
 * provides keyEvents access to properties that will be manipulated.
 *
 */
public abstract class KeyableSprite implements Sprite {

    protected int cycleIndex = 0;
    protected Bitmap[] images;

    protected float xLoc;
    protected float yLoc;
    private ArrayList<KeyEvent> events;


    public void cycleImages(){
        if(cycleIndex >= images.length - 1){
            cycleIndex = 0;
        }else{
            cycleIndex++;
        }
    }

    public void preUpdate(int frame){
        if(events != null){
            for(int i = 0; i < events.size(); i++){
                events.get(i).update(frame);
            }
        }
    }

    //@Override
    public abstract void draw(Canvas canvas);

    //@Override
    public abstract void update();

    public void eventFinished(KeyEvent event){
        events.remove(event);
    }

    public void attachEvent(KeyEvent event){
        if(events == null){
            events = new ArrayList<KeyEvent>();
        }
        events.add(event);
        event.setSprite(this);
    }

    public Float getXLoc(){
        return xLoc;
    }

    public Float getYLoc(){
        return yLoc;
    }

    public void setXLoc(float loc){
        xLoc = loc;
    }

    public void setYLoc(float loc){
        yLoc = loc;
    }

    public Bitmap[] getImages() {
        return images;
    }

    public int getCurrentImgIndex(){
        return cycleIndex;
    }

    public void setBitmap(int index, Bitmap bmp){
        images[index] = bmp;
    }

}
