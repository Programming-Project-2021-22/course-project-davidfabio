package com.davidfabio.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Camera extends OrthographicCamera {
    private static final float MOVEMENT_SPEED = 80f;
    public Camera() {
        super();
        this.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.position.set(this.viewportWidth / 2f, this.viewportHeight / 2f, 0);
        this.zoom = 0.5f;   // We start zoomed in
    }

    public void updateCameraPosition(float deltaTime, Player player) {
        // First we zoom out
        if (this.zoom <= 1.0f) {
            this.zoom += 0.2f * deltaTime;
        }
        // Then we move towards the player
        // TODO-Fabio: This needs to be fixed
        //this.moveTowards(player.getX(), player.getY(), deltaTime);
        this.update();
    }

    private void moveTowards(float otherX, float otherY, float deltaTime) {
        float dir = getAngleTowards(otherX, -otherY);
        moveTowards(dir, deltaTime);
    }

    private float getAngleTowards(float otherX, float otherY) {
        return ((float)Math.atan2(otherY - this.position.y, otherX - this.position.x));
    }

    public void moveTowards(float direction, float deltaTime) {
        float speed = Camera.MOVEMENT_SPEED * deltaTime;
        float deltaX = (float)Math.cos(direction) * speed;
        float deltaY = (float)Math.sin(direction) * speed;

        this.position.set(this.position.x + deltaX, this.position.y + deltaY, 0);
    }
}
