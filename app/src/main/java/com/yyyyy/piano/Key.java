package com.yyyyy.piano;

import android.graphics.RectF;

public class Key {

    public int sound;
    public RectF rectF;
    public boolean down;
    public boolean selected;



    public Key(int sound, RectF rectF) {
        this.sound = sound;
        this.rectF = rectF;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isDown() {
        return down;
    }
}
