package com.davidfabio.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Camera extends OrthographicCamera {
    private static final float MOVEMENT_SPEED = 200f;
    public Camera() {
        super();
        this.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.position.set(this.viewportWidth / 2f, this.viewportHeight / 2f, 0);
        this.zoom = 0.5f;   // We start zoomed in
    }

    public void updateCameraPosition(float deltaTime, Player player) {
        // First we zoom out
        if (this.zoom <= 1.0f) {
            this.zoom += 0.2f * deltaTime;
        }
        // Then we move towards the player
        this.moveTowards(player.getX(), player.getY(), deltaTime);
        this.update();
    }

    private void moveTowards(float otherX, float otherY, float deltaTime) {
        float dir = getAngleTowards(otherX, otherY);
        // Increase camera speed when distance to player is large, so that the player never goes
        // out of view.
        float distanceToPlayer = getDistanceTo(otherX, otherY);
        float speedMultiplier = distanceToPlayer > 240 ? (distanceToPlayer / 240 * 2) : 1.0f;
        float speed = Camera.MOVEMENT_SPEED * deltaTime * speedMultiplier;
        moveTowardsWSpeed(dir, deltaTime, speed);
    }

    private float getAngleTowards(float otherX, float otherY) {
        return ((float)Math.atan2(otherY - this.position.y, otherX - this.position.x));
    }

    public float getDistanceTo(float otherX, float otherY) {
        float distanceX = this.position.x - otherX;
        float distanceY = this.position.y - otherY;
        return (float)Math.sqrt((distanceX * distanceX) + (distanceY * distanceY));
    }

    public void moveTowardsWSpeed(float direction, float deltaTime, float speed) {
        float deltaX = (float)Math.cos(direction) * speed;
        float deltaY = (float)Math.sin(direction) * speed;

        this.position.set(this.position.x + deltaX, this.position.y + deltaY, 0);
    }
}
