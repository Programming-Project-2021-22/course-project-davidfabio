package org.davidfabio.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Camera extends OrthographicCamera {
    /**
     * Speed by which the Camera moves towards the player.
     */
    private static final float MOVEMENT_SPEED = 200f;
    /**
     * This means that we start closer to the player.
     * Normal Zoom is 1.0f, anything lower is zoomed in, anything higher is zoomed out.
     */
    private static final float INITIAL_ZOOM = 0.5f;
    /**
     * This indicates how quickly we approach the optimal zoom.
     */
    private static final float ZOOM_VELOCITY = 0.2f;
    /**
     * This indicates how much of the stage should be visible once optimal zoom is reached.
     * It compares stage width/height to window width/height and takes the lowest value. This
     * Value is multiplied by FINAL_LEVEL_VISIBILITY to calculate the optimal Zoom.
     */
    private static final float FINAL_LEVEL_VISIBILITY = 0.9f;
    /**
     * Minimum Distance to Move should be as low as possible to ensure responsive camera handling.
     * This is actually just required to avoid jiggling when the player stands still.
     */
    private static final float MINIMUM_DISTANCE_TO_MOVE = 0.5f;
    /**
     * Once the distance to the player exceeds this value, the camera speeds up to catch up.
     */
    private static final float MINIMUM_DISTANCE_TO_SPEED_UP = 240f;
    /**
     * This is the zoom we need in order to show FINAL_LEVEL_VISIBILITY-Percent of the stage.
     */
    private float finalZoom;

    /**
     * Initializes the Camera Object. On initialization the Camera is centered on the Level passed in the parameters.
     * The Initial Zoom is defined by {@link Camera#INITIAL_ZOOM}. Optimal zoom for the game is calculated using
     * {@link Camera#calcOptimalZoom(Level)}.
     * @param level
     */
    public Camera(Level level) {
        super();
        this.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.position.set(level.getWidth() / 2f, level.getHeight() / 2f, 0);
        this.finalZoom = calcOptimalZoom(level);    // calculates the optimal zoom to show a little less than the whole level
        this.zoom = INITIAL_ZOOM * this.finalZoom;   // We start zoomed in
    }

    /**
     * This method moves the center of the Camera towards the player passed as an argument.
     * If the camera is currently zoomed in, the zoom is reduced in order to reach optimal zoom for the game.
     *
     * @param deltaTime Delta by which the game loop updated
     * @param player The Player on which the Camera should center on
     */
    public void updateCameraPosition(float deltaTime, Player player) {
        // First we zoom out
        if (this.zoom < finalZoom) {
            this.zoom += ZOOM_VELOCITY * deltaTime;
        }
        // Then we move towards the player
        this.moveTowards(player.getX(), player.getY(), deltaTime);
        this.update();
    }

    /**
     * This method moves the camera focus in the direction of another entity (=player).
     * The camera only moves when necessary.
     * @param otherX X-Position of the other Entity
     * @param otherY Y-Position of the other Entity
     * @param deltaTime Delta by which the game loop updated
     */
    private void moveTowards(float otherX, float otherY, float deltaTime) {
        float dir = this.getAngleTowards(otherX, otherY);
        // Increase camera speed when distance to player is large, so that the player never goes
        // out of view.
        float distanceToOther = this.getDistanceTo(otherX, otherY);
        if (distanceToOther * deltaTime > MINIMUM_DISTANCE_TO_MOVE) {
            // We only move the camera if the distance to the player is large enough.
            // This avoids unnecessary jiggle when the player stands still.
            float speedMultiplier = distanceToOther > MINIMUM_DISTANCE_TO_SPEED_UP ? (distanceToOther / MINIMUM_DISTANCE_TO_SPEED_UP * 2) : 1.0f;
            float speed = Camera.MOVEMENT_SPEED * deltaTime * speedMultiplier;
            this.moveTowardsWSpeed(dir, speed);
        }
    }

    /**
     * This method calculates the optimal Zoom for the game.
     * It requires a Level as we only want to show {@link Camera#FINAL_LEVEL_VISIBILITY} percent of the Level at any one time.
     * In order to calculate the Zoom we take the width and height of both the level and window/screen. The lowest value is
     * calculated and then used to calculate the optimal zoom.
     *
     * @param level The level which is being used to calculate the optimal zoom.
     * @return The optimal zoom to use in order to show {@link Camera#FINAL_LEVEL_VISIBILITY} percent of the Level.
     */
    private float calcOptimalZoom(Level level) {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float levelWidth = level.getWidth();
        float levelHeight = level.getHeight();

        float optimalXZoom = levelWidth / screenWidth * FINAL_LEVEL_VISIBILITY;
        float optimalYZoom = levelHeight / screenHeight * FINAL_LEVEL_VISIBILITY;
        return Math.min(optimalXZoom,optimalYZoom);
    }

    private float getAngleTowards(float otherX, float otherY) {
        return ((float)Math.atan2(otherY - this.position.y, otherX - this.position.x));
    }

    public float getDistanceTo(float otherX, float otherY) {
        float distanceX = this.position.x - otherX;
        float distanceY = this.position.y - otherY;
        return (float)Math.sqrt((distanceX * distanceX) + (distanceY * distanceY));
    }

    public void moveTowardsWSpeed(float direction, float speed) {
        float deltaX = (float)Math.cos(direction) * speed;
        float deltaY = (float)Math.sin(direction) * speed;

        this.position.set(this.position.x + deltaX, this.position.y + deltaY, 0);
    }
}
