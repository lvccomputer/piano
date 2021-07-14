package com.yyyyy.piano;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class PianoView extends View {
    private static final String TAG = "cuong";

    public static final int NOTE = 53;
    public int keyCount = 13;
    private Paint black, white, yellow;
    private Paint blue;
    private ArrayList<Key> whites = new ArrayList<>();// keys

    private ArrayList<Key> blacks = new ArrayList<>();//diese

    private ArrayList<Key> allKey = new ArrayList<>();
    private HashMap<Integer, Key> keyMap = new HashMap<>();

    private float keyWidth, keyHeight;

    private float timeLimit;
//
//    private float blackKeyWidthRatioToWhiteKeyWidth = 0.8f;
//    private float blackKeyHeightRatio = 0.5f;

    private AudioSoundPlayer soundPlayer;

    private Tracker trackLeft;
    private Tracker trackRight;
    private float xOffset;

    public PianoView(Context context, SparseArray<String> sound) {
        super(context);
        soundPlayer = new AudioSoundPlayer(context, sound);
        black = new Paint();
        black.setColor(Color.BLACK);
        white = new Paint();
        white.setColor(Color.WHITE);
        white.setStyle(Paint.Style.FILL);
        yellow = new Paint();
        yellow.setColor(Color.RED);
        yellow.setStyle(Paint.Style.FILL);
        blue = new Paint();
        blue.setColor(Color.GREEN);
        blue.setStyle(Paint.Style.FILL);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        whites.clear();
        blacks.clear();
        keyWidth = w / (keyCount);
        keyHeight = h;

        int count = NOTE;

        for (int i = 0; i < NOTE; i++) {
            float left = i * keyWidth;
            float right = left + keyWidth;
            RectF rect = new RectF(left, 0, right, h);
            whites.add(new Key(i + 1, rect));

            if (i != 0 && i != 2 && i != 5 && i != 9 && i != 12 && i != 16 && i != 19 && i != 23 && i != 26 && i != 30 && i != 33
                    && i != 37 && i != 40 && i != 44 && i != 47 && i != 51 && i != 52) {
                rect = new RectF((float) (i - 1) * keyWidth + 0.5f * keyWidth + 0.25f * keyWidth, 0,
                        (float) i * keyWidth + 0.25f * keyWidth, 0.67f * keyHeight);
                blacks.add(new Key(count, rect));
                count++;
            }
            allKey.clear();
            allKey.addAll(whites);
            allKey.addAll(blacks);
            Collections.reverse(allKey);
//            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(xOffset, 0);

        for (Key k : whites) {
            RectF rectF = k.rectF;
            Paint paint = k.down ? this.yellow : k.selected ? this.blue : this.white;
            canvas.drawRect(rectF, paint);

        }

        for (int i = 1; i < NOTE; i++) {
            canvas.drawLine(i * keyWidth, 0, i * keyWidth, keyHeight, black);
        }

        for (Key k : blacks) {
            RectF rectF2 = k.rectF;
            Paint paint2 = k.down ? this.yellow : k.selected ? this.blue : this.black;
            canvas.drawRect(rectF2, paint2);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        onNewTouchEvent(event);
        return true;
    }

    private void selectKey(int i) {//change color key to yellow when add tracker
        Key key = null;
        if (i <= 53 && i > 0) {
            key = (Key) this.whites.get(i - 1);

        } else if (i > 54 && i <= 88) {
            key = (Key) this.blacks.get(i - 54);
        } else if (i == 0) {
            releaseKey(key);
        }
        if (key != null) {
            key.selected = true;
        }
    }

    private void selectKeyArray(ArrayList<Integer> list) {// change color  array key to yellow when has more key
        Log.e("tracker", "selectKeyArray: " + list);
        for (Integer integer : list) {
            if (integer > 53) {
                selectKey(integer + 1);
            } else
                selectKey(integer);
            invalidate();
        }
    }

    private void unSelectKey(int i) {
        Key key = null;
        if (i == 0) releaseKey(key);
        if (i > 0 && i <= 53) {
            key = (Key) this.whites.get(i - 1);

        } else if (i > 54 && i <= 89) {
            key = (Key) this.blacks.get(i - 54);
        }
        if (key != null) {
            key.selected = false;
        }
    }


    public ArrayList<Integer> currentNoteFromTrackLeft() {// return current key when next key
        if (this.trackLeft != null) {
            return this.trackLeft.current();
        }
        return null;
    }

    public ArrayList<Integer> currentNoteFromTrackRight() { //return current key when next key
        if (this.trackRight != null) {
            return this.trackRight.current();
        }
        return null;
    }

    public void nextNoteFromTrackLeft() {// next key chords when click on keyboard
        if (this.trackLeft != null) {
            this.trackLeft.next();
            invalidate();
        }
    }

    private boolean deff;

    private boolean nextDeff;

    public void nextNoteFromTrackRight() {
        if (this.trackRight != null) {
            this.trackRight.next();
            invalidate();
        }
    }

    public void razTrack() {
        this.trackLeft = null;
        razKeys();
        invalidate();
    }

    public void setTrackLeft(Tracker track) {
        this.trackLeft = new Tracker();
        this.trackLeft = track;
        initTrackLeft();
    }

    public void setTrackRight(Tracker track) {
        this.trackRight = new Tracker();
        this.trackRight = track;
        initTrackRight();
    }

    private boolean isSelect = true;

    public void initTrackRight() {// init tracker to piano keyboard
        if (this.trackRight != null) {
            this.trackRight.reset();
            unSelectKeys();
            selectKeyArray(this.trackRight.current());
            moveToPosition(this.trackRight.current().get(this.trackRight.current().size() - 1) / 11);
            invalidate();
        }
    }

    public void initTrackLeft() {
        if (this.trackLeft != null) {
            this.trackLeft.reset();
            unSelectKeys();
            invalidate();
        }
    }

    public void unSelectKeys() {
        for (Key key : this.blacks) {
            key.selected = false;
        }
        for (Key key2 : this.whites) {
            key2.selected = false;
        }
    }

    public void razKeys() {
        for (Key key : this.blacks) {
            key.selected = false;
            key.down = false;
        }
        for (Key key2 : this.whites) {
            key2.selected = false;
            key2.down = false;
        }
    }

    public void setKeyCount(int keyCount) {
        this.keyCount = keyCount;
    }

    public int getMaxMovePosition() {
        return 3;
    }

    public void setKeyListener(KeyListener listener) {
        this.keyListener = listener;
    }

    public void moveToPosition(int position) {
        if (position >= 3) {
            position = 3;
        }
        this.xOffset = -keyWidth * 13 * position;
        updateListenerPosition();
        invalidate();
    }

    private void updateListenerPosition() {
        if (keyWidth != 0) {
            int position = (int) (-xOffset / keyWidth / 13);
            if (keyListener != null) {
                keyListener.currentFirstKeyPosition(position);
            }
        }

    }

    private KeyListener keyListener;

    public interface KeyListener {

        //get key current click
        void onKeyPressed(Key key);

        //get key after click
        void onKeyUp(Key key);

        //return key current of position
        void currentFirstKeyPosition(int position);
    }


    private void onFingerUp(int index, float x, float y) {
        Key key = this.keyMap.get(index);
        if (key != null) {
            fireKeyUp(key);
            this.keyMap.remove(index);
        } else {
            Key key1 = pointerInWhichKey(x, y);
            fireKeyUp(key1);
        }
    }

    private void onFingerDown(int index, float x, float y) {
        Key key = pointerInWhichKey(x, y);
        fireKeyDown(key);
        this.keyMap.put(index, key);

    }


    private void onAllFingersUp() {
        Iterator<Key> localIterator = this.keyMap.values().iterator();
        while (localIterator.hasNext()) {
            fireKeyUp(localIterator.next());
        }
        this.keyMap.clear();
    }

    private void onFingerMove(int index, float x, float y) {
        Key key = this.keyMap.get(index);
        Key currentKey = pointerInWhichKey(x, y);
        if (key != null) {
            if ((currentKey != null) && (currentKey != key)) {
                fireKeyDown(currentKey);
                fireKeyUp(key);
                this.keyMap.put(index, currentKey);
            }
        }
    }


    private void fireKeyUp(Key key) {
        if (keyListener != null) {
            keyListener.onKeyUp(key);
        }
        key.setDown(false);
        invalidate();
    }

    private void fireKeyDown(Key key) {
        if (keyListener != null) {
            keyListener.onKeyPressed(key);
        }
        key.setDown(true);
        invalidate();
    }


    private Key pointerInWhichKey(float x, float y) {
        Key currentKey = null;
        for (Key key : allKey) {
            if (key.rectF.contains(x - xOffset, y)) {
                currentKey = key;
                break;
            }
        }
        return currentKey;
    }

    private Key keyForCoords(float x, float y) {
        for (Key k : blacks) {
            if (k.rectF.contains(x, y)) {
                return k;
            }
        }
        for (Key k : whites) {
            if (k.rectF.contains(x, y)) {
                return k;
            }
        }
        return null;
    }

    private void releaseKey(final Key k) {

        if (k == null) {
            invalidate();
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    k.down = false;
                    handler.sendEmptyMessage(0);
                }
            }, 100);
        }

    }

    private void onNewTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        int pointerIndex = motionEvent.getActionIndex();
        int pointerId = motionEvent.getPointerId(pointerIndex);
        float x = motionEvent.getX(pointerIndex);
        float y = motionEvent.getY(pointerIndex);
        ArrayList<Key> tmp = new ArrayList<>(whites);
//
        tmp.addAll(blacks);
        Iterator it = tmp.iterator();

        switch (actionMasked) {
            case MotionEvent.ACTION_DOWN:

            case MotionEvent.ACTION_POINTER_DOWN:
                onFingerDown(pointerId, x, y);
                playNoteClick(it);

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                onFingerUp(pointerId, x, y);
                playNoteClick(it);

                break;
            case MotionEvent.ACTION_MOVE:

                int j = motionEvent.getPointerCount();
                for (int i = 0; i < j; i++) {
                    onFingerMove(motionEvent.getPointerId(i), motionEvent.getX(i), motionEvent.getY(i));
                }
                move(it);

                break;
//
            case MotionEvent.ACTION_CANCEL:
                onAllFingersUp();
                break;

        }
    }

    private void playNoteClick(Iterator it) {
        while (it.hasNext()) {
            final Key key = (Key) it.next();
            if (!key.down) {
                this.soundPlayer.stopNote(key.sound);
                releaseKey(key);
            } else if (!this.soundPlayer.isNotePlaying(key.sound)) {

                ArrayList<Integer> currentNoteFromTrackLeft = currentNoteFromTrackLeft();
                ArrayList<Integer> currentNoteFromTrackRight = currentNoteFromTrackRight();

                if (currentNoteFromTrackLeft != null && currentNoteFromTrackRight != null) {
                    currentNoteFromTrackLeft.size();
                    ArrayList<Integer> nexts = new ArrayList<>();
                    nexts.addAll(currentNoteFromTrackLeft);
                    nexts.addAll(currentNoteFromTrackRight);
                    long timeLeft = (long) (this.trackLeft.timeLimit * 1000);
                    final long timeRight = (long) (this.trackRight.timeLimit * 1000);
                    Log.e(TAG, "playNoteClick: " + timeRight / 4);

                    for (final Integer integer : nexts) {
                        this.soundPlayer.playNote(integer);

                        if (key.sound == integer) {
                            unSelectKeys();
                            nextNoteFromTrackRight();
                            nextNoteFromTrackLeft();

                            ArrayList<Integer> noteAfterNextRight = currentNoteFromTrackRight();
                            ArrayList<Integer> noteAfterNextLeft = currentNoteFromTrackLeft();

                            boolean next = false;
                            if (noteAfterNextRight != null) {
                                if (noteAfterNextRight.size() > 0) {
                                    if (noteAfterNextRight.get(0) == 0) {
                                        next = true;
                                    } else next = false;
                                } else next = true;
                            }
                            boolean go = false;
                            if (next) {
                                if (noteAfterNextLeft != null) {
                                    if (noteAfterNextLeft.size() > 0) {
                                        if (noteAfterNextLeft.get(0) == 0) {
                                            go = true;
                                        } else go = false;
                                    } else go = true;
                                } else go = true;
                            }
                            if (go) {

                                nextNoteFromTrackLeft();
                                nextNoteFromTrackRight();
                            }
                            ArrayList<Integer> currentNoteFromTrackRight1 = currentNoteFromTrackRight();
                            if (currentNoteFromTrackRight1 != null && currentNoteFromTrackRight1.size() > 0) {
                                Log.e("tracker", "currentNoteFromTrackRight1: " + currentNoteFromTrackRight1.get(0));
                                int right = currentNoteFromTrackRight1.get(0);
                                if (right > 0 && right < 52) {
                                    if (right < 9) {
                                        moveToPosition(right / 10);
                                    } else if (right <= 13) {
                                        moveToPosition(right / 10 - 1);
                                    } else if (right <= 19) {
                                        moveToPosition(right / 10);
                                    } else if (right <= 26) {
                                        moveToPosition(right / 10 - 1);
                                    } else if (right <= 29) {
                                        moveToPosition(right / 10);
                                    } else if (right <= 39) {
                                        moveToPosition(right / 10 - 1);
                                    } else if (right <= 49) {
                                        moveToPosition(right / 10 - 1);
                                    } else {
                                        moveToPosition(right / 10 - 2);
                                    }
                                } else if (right > 65) {
                                    if (right <= 69) {
                                        moveToPosition(right / 10 - 5);
                                    } else if (right == 70) {
                                        moveToPosition(right / 10 - 6);
                                    } else if (right <= 79) {
                                        moveToPosition(right / 10 - 5);
                                    } else if (right <= 88) {
                                        moveToPosition(right / 10 - 5);
                                    }
                                } else deff = true;
                                selectKeyArray(currentNoteFromTrackRight1);

                            } else
                                nextDeff = true;

                            ArrayList<Integer> currentNoteFromTrackLeft1 = currentNoteFromTrackLeft();


                            if (currentNoteFromTrackLeft1 != null && currentNoteFromTrackLeft1.size() > 0) {
                                Log.e("tracker", "currentNoteFromTrackLeft1: " + currentNoteFromTrackLeft1.get(0));
                                int left = currentNoteFromTrackLeft1.get(0);
                                if (deff || nextDeff) {
                                    if (left > 0 && left <= 88) {
                                        if (left <= 13) {
                                            moveToPosition(left / 10 - 1);
                                        } else if (left <= 19) {
                                            moveToPosition(left / 10);
                                        } else if (left <= 26) {
                                            moveToPosition(left / 10 - 1);
                                        } else if (left <= 29) {
                                            moveToPosition(left / 10);
                                        } else if (left <= 39) {
                                            moveToPosition(left / 10 - 1);
                                        } else if (left <= 49) {
                                            moveToPosition(left / 10 - 1);
                                        } else if (left <= 59) {
                                            moveToPosition(left / 10 - 5);
                                        } else if (left == 60) {
                                            moveToPosition(left / 10 - 6);
                                        } else if (left <= 69) {
                                            moveToPosition(left / 10 - 5);
                                        } else if (left == 70) {
                                            moveToPosition(left / 10 - 6);
                                        } else if (left <= 79) {
                                            moveToPosition(left / 10 - 5);
                                        } else {
                                            moveToPosition(left / 10 - 5);
                                        }

                                    }
                                }
                                selectKeyArray(currentNoteFromTrackLeft1);

                            }

                        }


                    }

                }
                this.soundPlayer.playNote(key.sound);

                invalidate();

            } else {
                releaseKey(key);
            }
        }
    }

    private void move(Iterator it) {
        while (it.hasNext()) {
            Key key = (Key) it.next();
            if (!key.down) {
                this.soundPlayer.stopNote(key.sound);
                releaseKey(key);
            } else if (!this.soundPlayer.isNotePlaying(key.sound)) {
                this.soundPlayer.playNote(key.sound);

                invalidate();

            } else {
                releaseKey(key);
            }
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            invalidate();
        }
    };
}
