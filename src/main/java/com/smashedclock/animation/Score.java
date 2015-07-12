package com.smashedclock.animation;

import android.graphics.Canvas;
import java.util.ArrayList;
import java.util.List;

/**
 * create a score which manages animation channels
 */
public class Score {
    private List <Channel> channels;

    private ScoreCallback callback;

    private int length, frameNumber;

    private boolean finished = false;
    /**
     * creates a score and initializes a channel ArrayList.
     */
    public Score(int length) {
        this.channels = new ArrayList<Channel>();
        this.length = length;
        frameNumber = 0;
    }

    /**
     *
     * @param channel
     * adds a new channel to this score
     */
    public void addChannel(Channel channel){
        this.channels.add(channel);
    }

    /**
     *
     * @param channel
     * removes the given channel from this score
     */
    public void removeChannel(Channel channel){
        this.channels.remove(channel);
    }

    /**
     * @param frameNumber
     */
    public void action(int frameNumber){

    }

    /**
     *
     * @param c
     */
    public void draw(Canvas c){
        for(int i = 0; i < channels.size(); i++){
            channels.get(i).draw(c);
        }
        if(callback != null){
            if(frameNumber >= length){
                callback.scoreFinished();
            }
        }
    }

    public void update() {
        for(int i = 0; i < channels.size(); i++){
            channels.get(i).action();
        }
        frameNumber++;
    }

    public void addCallback(ScoreCallback callback){
        this.callback = callback;
    }

}