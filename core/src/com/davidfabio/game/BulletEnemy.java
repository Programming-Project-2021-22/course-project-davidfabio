package com.davidfabio.game;

public class BulletEnemy extends Bullet {



    @Override public void init(float x, float y, float scale, Polarity polarity, float moveSpeed, float direction) {
        super.init(x, y, scale, polarity, moveSpeed, direction);

        float[] vertices = new float[] {
            0, -0.5f,
            -0.5f, 0,
            0, 0.5f,
            0.5f, 0,
            -0.3675f, -0.3675f,
            -0.3675f, 0.3675f,
            0.3675f, 0.3675f,
            0.3675f, -0.3675f
        };
        short[] triangles = new short[] {
            0, 1, 2,
            2, 3, 0,
            0, 4, 1,
            1, 5, 2,
            2, 6, 3,
            3, 7, 0
        };
        shape = new PolygonShape(vertices, triangles, scale);
    }




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