package com.davidfabio.game;

public class EnemyBullet extends Bullet {

    public void update(float deltaTime) {
        super.update(deltaTime);

        // ---------------- collision detection ----------------
        if (Collision.circleCircle(getX(), getY(), getRadius(), Game.player.getX(), Game.player.getY(), Game.player.getRadius())) {

            // we leave the bullet alive for 1 extra frame, so that we can draw it at the exact position where it touches the player
            setToDestroyNextFrame(true);
            while (Collision.circleCircle(getX(), getY(), getRadius(), Game.player.getX(), Game.player.getY(), Game.player.getRadius())) {
                setX(getX() - (float)Math.cos(getDirection()));
                setY(getY() - (float)Math.sin(getDirection()));
            }
        }
    }


}