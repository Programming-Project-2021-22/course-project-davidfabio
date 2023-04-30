package org.davidfabio.game;

import org.davidfabio.utils.Settings;

public class Score implements Comparable {
    /**
     * Stores the Username that this Score instance belongs to.
     */
    private String username;
    /**
     * Stores the points a user has collected in a single game.
     */
    private int points;
    /**
     * Stores the pickups a user has collected in a single game.
     */
    private int pickups;
    /**
     * Stores the Starting Time of a game in milliseconds.
     */
    private long startTime;
    /**
     * Stores the Ending Time of a game in milliseconds.
     */
    private long endTime;
    /**
     * Stores the Duration (in ms) of a game, calculated using the Start Time and End Time.
     */
    private long duration;

    /**
     * Creates a new score instance using the {@link Settings#username} as username and current System Time as a {@link Score#startTime}.
     */
    public Score() {
        startTime = System.currentTimeMillis();
        username = Settings.username;
    }

    /**
     * Marks a score as completed by setting the {@link Score#endTime} and calculates the {@link Score#duration} based on the {@link Score#startTime}
     * and {@link Score#endTime}. The Number of pickups that a player has gained in a single game session is stored in {@link Score#pickups}.
     * @param totalPickups The number of Pickups a player has collected during the game.
     */
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

    /**
     * This method is implemented to sort the list of scores for high scores.
     * @param o the object to be compared.
     * @return 0 if uncomparable, otherwise the difference between other and this {@link Score#getPoints()}.
     */
    @Override
    public int compareTo(Object o) {
        if (o instanceof Score) {
            return ((Score) o).getPoints() - this.getPoints();
        } else {
            return 0;
        }
    }
}
