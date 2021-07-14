package com.yyyyy.piano;

import android.content.Context;
import android.os.Handler;
import android.util.SparseArray;

public class AudioSoundPlayer {
    private SparseArray<PlayThread> threadMap = null;
    private Context context;
    private  SparseArray<String> soundName;

    public AudioSoundPlayer(Context context, SparseArray<String> soundName){
        this.context = context;
        this.threadMap =new SparseArray<>(  );
        this.soundName = soundName;


    }

    public void playNote(int note) {
        if (!isNotePlaying( note )) {
            PlayThread thread = new PlayThread( note ,context,soundName);
            thread.start();
            threadMap.put( note, thread );
        }
    }

    public void stopNote(int note) {
         PlayThread thread = threadMap.get( note );

        if (thread != null) {
            threadMap.remove( note );
        }
    }


    public boolean isNotePlaying(int note) {
        return threadMap.get( note ) != null;
    }

}
