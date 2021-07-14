package com.yyyyy.piano;

import java.util.ArrayList;

public class Note  {
    private ArrayList<NoteItem> noteItems;

    public Note() {
    }

    public ArrayList<NoteItem> getNamePlay() {
        return noteItems;
    }

    public void setNamePlay(ArrayList<NoteItem> namePlay) {
        this.noteItems = namePlay;
    }
}
