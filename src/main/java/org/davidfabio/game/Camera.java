package org.davidfabio.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Camera extends OrthographicCamera {
    private static final float MOVEMENT_SPEED = 200f;
    private float finalZoom;

    public Camera(Level level) {
        super();
        this.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.position.set(this.viewportWidth / 2f, this.viewportHeight / 2f, 0);
        this.finalZoom = calcOptimalZoom(level);    // calculates the optimal zoom to show a little less than the whole level
        this.zoom = 0.5f * this.finalZoom;   // We start zoomed in
    }

    public void updateCameraPosition(float deltaTime, Player player) {
        // First we zoom out
        if (this.zoom < finalZoom) {
            this.zoom += 0.2f * deltaTime;
        }
        // Then we move towards the player
        this.moveTowards(player.getX(), player.getY(), deltaTime);
        this.update();
    }

    private void moveTowards(float otherX, float otherY, float deltaTime) {
        float dir = this.getAngleTowards(otherX, otherY);
        // Increase camera speed when distance to player is large, so that the player never goes
        // out of view.
        float distanceToPlayer = this.getDistanceTo(otherX, otherY);
        if (distanceToPlayer * deltaTime > 0.5f) {
            float speedMultiplier = distanceToPlayer > 240 ? (distanceToPlayer / 240 * 2) : 1.0f;
            float speed = Camera.MOVEMENT_SPEED * deltaTime * speedMultiplier;
            this.moveTowardsWSpeed(dir, speed);
        }
    }

    private float calcOptimalZoom(Level level) {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float levelWidth = level.getWidth();
        float levelHeight = level.getHeight();

        float optimalXZoom = levelWidth / screenWidth * 0.9f;
        float optimalYZoom = levelHeight / screenHeight * 0.9f;
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
