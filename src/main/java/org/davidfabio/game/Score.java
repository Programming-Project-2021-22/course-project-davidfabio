package org.davidfabio.game;

public class Score {
    private int points;
    private final long startTime;
    private long endTime;
    private long duration;

    public Score() {
        this.startTime = System.currentTimeMillis();
    }

    public void end() {
        this.endTime = System.currentTimeMillis();
        this.duration = this.endTime - this.startTime;
    }

    public int getPoints() {
        return this.points;
    }

    public void setPoints(int newScore) {
        this.points = newScore;
    }

    public long getDuration() {
        return this.duration;
    }
}
