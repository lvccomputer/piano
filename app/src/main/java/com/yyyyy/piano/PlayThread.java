package com.yyyyy.piano;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;
import android.util.SparseArray;

import java.io.InputStream;

public class PlayThread extends Thread {
    private int note;

    public AudioTrack audioTrack;

    private Context context;

    private SparseArray<String> soundName;

    public static final int MAX_VOLUME = 100, CURRENT_VOLUME = 90;

    public PlayThread(int note, Context context, SparseArray<String> soundName) {
        this.note = note;
        this.context = context;
        this.soundName = soundName;
    }



    @Override
    public void run() {
        try {
            String path = soundName.get( note ) + ".wav";
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor ad = assetManager.openFd( path );
            long fileSize = ad.getLength();
            int bufferSize = 4096;
            Log.e( "cuong", "run: " + path );

            byte[] buffer = new byte[bufferSize];

            audioTrack = new AudioTrack( AudioManager.STREAM_MUSIC, 88200, AudioFormat.CHANNEL_CONFIGURATION_MONO,
                    AudioFormat.ENCODING_PCM_16BIT, bufferSize, AudioTrack.MODE_STREAM );

            audioTrack.setStereoVolume(1000,10000  );
            audioTrack.play();
            InputStream audioStream = context.getResources().getAssets().open( path );
            int headerOffset = 4096;
            long bytesWritten = 0;
            int bytesRead = 0;

            audioStream.read( buffer, 0, headerOffset );

            while (bytesWritten < fileSize - headerOffset) {
                bytesRead = audioStream.read( buffer, 0, bufferSize );
                bytesWritten += audioTrack.write( buffer, 0, bytesRead );
            }

            audioTrack.stop();
            audioTrack.release();
        } catch (Exception e) {
            Log.e( "", "run: " + e );
        } finally {
            if (audioTrack != null) {
                audioTrack.release();
            }
        }
    }
}
