package com.example.lchen.catmemory.domain.model;

/**
 * Created by Lei Chen on 2018/2/25.
 */

public class User {

    private int index;
    private int score;

    private float progress;

    public User(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void increaseScore() {
        score++;
    }

    public float getProgress() {
        return progress;
    }

    public void updateProgress(float progress) {
        this.progress = progress;
    }
}
