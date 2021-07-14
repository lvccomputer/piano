package com.yyyyy.piano;

public class NoteItem {
    private String name;
    private float timeLimit;

    public NoteItem(String name, float timeLimit) {
        this.name = name;
        this.timeLimit = timeLimit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(float time) {
        this.timeLimit = time;
    }
}
