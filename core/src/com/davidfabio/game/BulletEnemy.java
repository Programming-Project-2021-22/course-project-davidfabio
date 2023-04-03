package com.davidfabio.game;

public class BulletEnemy extends Bullet {

    public void update(float deltaTime) {
        super.update(deltaTime);

        if (!getIsActive())
            return;

        // ---------------- collision detection ----------------
        if (Collision.circleCircle(getX(), getY(), getScale(), GameScreen.player.getX(), GameScreen.player.getY(), GameScreen.player.getScale())) {

            // we leave the bullet alive for 1 extra frame, so that we can draw it at the exact position where it touches the player
            setToDestroyNextFrame(true);
            while (Collision.circleCircle(getX(), getY(), getScale(), GameScreen.player.getX(), GameScreen.player.getY(), GameScreen.player.getScale())) {
                setX(getX() - (float)Math.cos(getDirection()));
                setY(getY() - (float)Math.sin(getDirection()));
            }
        }
    }


}