package org.davidfabio.game;

import org.davidfabio.utils.Settings;

public class Score implements Comparable {
    private String username;
    private int points;
    private int pickups;
    private long startTime;
    private long endTime;
    private long duration;

    public Score() {
        startTime = System.currentTimeMillis();
        username = Settings.username;
    }

    public void end(int totalPickups) {
        endTime = System.currentTimeMillis();
        duration = endTime - startTime;
        pickups = totalPickups;
    }

    public int getPoints() {
        return points;
    }
    public int getPickups() { return pickups; }

    public void addPoints(int points) {
        this.points = this.points + points;
    }

    public long getDuration() {
        return duration;
    }

    public String getUsername() {
        return username;
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
