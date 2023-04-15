package org.davidfabio.game;

import org.davidfabio.utils.Settings;

public class Score implements Comparable {
    private String username;
    private int points;
    private long startTime;
    private long endTime;
    private long duration;

    public Score() {
        this.startTime = System.currentTimeMillis();
        this.username = Settings.username;
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

    public String getUsername() {
        return this.username;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Score) {
            return ((Score) o).getPoints() - this.getPoints();
        } else {
            return 0;
        }
    }
}
