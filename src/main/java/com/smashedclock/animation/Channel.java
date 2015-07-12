package com.smashedclock.animation;

import android.graphics.Canvas;
import java.util.HashMap;

/**
 *
 */
public class Channel {
    private KeyableSprite currentSprite;
    //private List <KeyableSprite> sprites;
    private HashMap<Integer,KeyEvent> keyFrames;

    private int frame = 0;

    /**
     *
     * @param currentSprite
     */
    public Channel(KeyableSprite currentSprite) {
        this.currentSprite = currentSprite;
        this.keyFrames = new HashMap<Integer,KeyEvent>();
    }

    /**
     *
     * @param currentSprite
     * @param sprites
     */
    /*public Channel(Sprite currentSprite, List<Sprite> sprites) {
        this.currentSprite = currentSprite;
        this.sprites = sprites;
        this.keyFrames = new HashMap();
    }*/

    /**
     *
     * @param frameNumber
     * @param keyEvent
     */
    public void addKeyFrame(int frameNumber, KeyEvent keyEvent){
        this.keyFrames.put(frameNumber, keyEvent);
    }

    /**
     *
     * @param sprite
     */

    /*public void addSprite(Sprite sprite){
        sprites.add(sprite);
    }*/

    /**
     *
     * @param c
     */
    public void draw(Canvas c){
        currentSprite.draw(c);
    }

    public void action(){
        frame++;
        if(keyFrames.containsKey(frame)){
            currentSprite.attachEvent(keyFrames.get(frame));
        }
        currentSprite.preUpdate(frame);
        currentSprite.update();
    }

}
