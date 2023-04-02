package com.davidfabio.game;

public class Score {
    private int score;
    private long startTime;
    private long endTime;
    private long duration;

    public Score() {
        this.startTime = System.currentTimeMillis();
    }

    public void end() {
        this.endTime = System.currentTimeMillis();
        this.duration = this.endTime - this.startTime;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int newScore) {
        this.score = newScore;
    }
}
