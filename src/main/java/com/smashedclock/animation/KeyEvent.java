package com.smashedclock.animation;

/**
 *
 */
public abstract class KeyEvent {
    protected KeyableSprite sprite;
    protected int startFrame = 0, frameLength;

    public abstract void update(int frame);

    public void setSprite(KeyableSprite sprite){
        this.sprite = sprite;
    }

    public void setStartFrame(int frame){
        this.startFrame = frame;
    }

    public void setFrameLength(int length){
        this.frameLength = length;
    }
}
