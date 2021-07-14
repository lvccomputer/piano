package com.yyyyy.piano;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.SeekBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "cuong";
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    private FrameLayout frameView;

    private SparseArray<String> soundArrayList;

    private ArrayList<Note> playSongRight;

    private ArrayList<Note> playSongLeft;

    private Button btnLaLung,btnFaded,btnPerfect,btnCanon,btnGot;

    private PianoView pianoView;

    private SeekBar seekBar;

    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    PianoView.KeyListener keyListener = new PianoView.KeyListener() {
        @Override
        public void onKeyPressed(Key key) {
        }

        @Override
        public void onKeyUp(Key key) {

        }

        @Override
        public void currentFirstKeyPosition(int position) {
            seekBar.setMax(pianoView.getMaxMovePosition());
            seekBar.setProgress(position);

        }

    };

    private void init() {
        seekBar = findViewById(R.id.seek);
        frameView = findViewById(R.id.pianoView);
        soundArrayList = new SparseArray<>();
        btnLaLung = findViewById(R.id.lalung);
        btnCanon = findViewById(R.id.canon);
        btnFaded = findViewById(R.id.faded);
        btnGot = findViewById(R.id.got);
        btnPerfect = findViewById(R.id.perfect);
        playSongRight = new ArrayList<>();
        playSongLeft = new ArrayList<>();

        pianoView = new PianoView(this, UtilsMusic.soundArrayList);
        frameView.addView(pianoView);

        seekBar.setMax(pianoView.getMaxMovePosition());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pianoView.moveToPosition(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                seekBar.setMax(pianoView.getMaxMovePosition());

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        pianoView.setKeyListener(keyListener);

        btnLaLung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData("lalung");

            }
        });
        btnPerfect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData("perfect");
            }
        });
        btnGot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData("got");

            }
        });
        btnFaded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData("faded");

            }
        });
        btnCanon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getData("canonind");
            }
        });
    }

    private void getData(final String nameJson) {

        playSongRight.clear();
        playSongLeft.clear();
        try {

            JSONObject jsonObject = new JSONObject(loadJSONFromAssets(MainActivity.this, nameJson));
            JSONArray jsonArray = jsonObject.getJSONArray("notes");

            for (int i = 0; i < jsonArray.length(); i++) {
                int index = jsonArray.getJSONArray(i).length();
                ArrayList<Integer> numberOfPosition = new ArrayList<>();
                for (int position = 0; position < index; position++) {
                    numberOfPosition.add(position);
                }
                for (int pos = 0; pos < numberOfPosition.size(); pos++) {
                    if (pos % 2 == 0) {
                        int sizeLeft = jsonArray.getJSONArray(i).getJSONArray(numberOfPosition.get(pos)).length();

//                        Log.d(TAG, "left: " + jsonArray.getJSONArray(i).getJSONArray(numberOfPosition.get(pos)));
                        Note note = new Note();

                        ArrayList<NoteItem> noteItems = new ArrayList<>();

                        for (int ind = 0; ind < sizeLeft; ind++) {
                            String noteLeft = jsonArray.getJSONArray(i).getJSONArray(numberOfPosition.get(pos)).get(ind).toString().split("_")[1];
                            float time = Float.parseFloat(jsonArray.getJSONArray(i).getJSONArray(numberOfPosition.get(pos)).get(ind).toString().split("_")[0]);
                            noteItems.add(new NoteItem(noteLeft, time));
                        }
                        note.setNamePlay(noteItems);
                        playSongRight.add(note);

                    } else {
                        Note note = new Note();

                        ArrayList<NoteItem> noteItems = new ArrayList<>();
                        int sizeRight = jsonArray.getJSONArray(i).getJSONArray(numberOfPosition.get(pos)).length();
//                        Log.d(TAG, "right: " + jsonArray.getJSONArray(i).getJSONArray(numberOfPosition.get(pos)));
                        for (int ind = 0; ind < sizeRight; ind++) {
                            String noteRight = jsonArray.getJSONArray(i).getJSONArray(numberOfPosition.get(pos)).get(ind).toString().split("_")[1];
                            float time = Float.parseFloat(jsonArray.getJSONArray(i).getJSONArray(numberOfPosition.get(pos)).get(ind).toString().split("_")[0]);
                            noteItems.add(new NoteItem(noteRight, time));
                        }
                        note.setNamePlay(noteItems);
                        playSongLeft.add(note);

                    }

                }
//                Log.d(TAG, "--------------------- ");
            }
        } catch (JSONException e) {
            Log.e("cuong", "JSONException: " + e);
        }

        pianoView.setTrackLeft(new Tracker(playSongLeft));
        pianoView.setTrackRight(new Tracker(playSongRight));
    }

    public static String loadJSONFromAssets(Context context, String nameJson) {
        String json = null;
        try {
            InputStream inputStream = context.getAssets().open(nameJson + ".json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, StandardCharsets.UTF_8);


        } catch (IOException e) {
            e.printStackTrace();
            return null;

        }
        return json;
    }
}