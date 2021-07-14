package com.yyyyy.piano;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Tracker {
    public int current;
    public List<Note> notes;
    public float timeLimit;

    public Tracker() {
        this.notes = new ArrayList();
    }

    public Tracker( List<Note> list) {
        this();
        this.notes = list;
    }

    public ArrayList<Integer> current() {
        if (hasNext()) {
            ArrayList<Integer> listCurrent = new ArrayList<>();
            for (NoteItem noteItem : notes.get(this.current).getNamePlay()) {

                listCurrent.add(UtilsMusic.list.get(noteItem.getName()));
                this.timeLimit = noteItem.getTimeLimit();
//                Log.e("tracker", "name: "+noteItem.getName());
//                Log.e("tracker", "number: "+UtilsMusic.list.get(noteItem.getName()));
            }
            return listCurrent;
            //return number 1-88
        }
        return null;
    }


    public void next() {
        if (hasNext()) {
            this.current++;
        }
    }

    public void reset() {
        this.current = 0;
    }

    private boolean hasNext() {
        return this.current < this.notes.size();
    }

}
