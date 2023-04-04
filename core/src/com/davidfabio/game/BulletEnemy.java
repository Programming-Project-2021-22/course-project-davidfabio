package com.davidfabio.game;

public class BulletEnemy extends Bullet {



    @Override public void init(float x, float y, float scale, Polarity polarity, float moveSpeed, float direction) {
        super.init(x, y, scale, polarity, moveSpeed, direction);

        verticesInitial = new float[] {
            0, -0.5f, // top
            -0.5f, 0, // left
            0, 0.5f,  // bottom
            0.5f, 0,  // right
            -0.3675f, -0.3675f,
            -0.3675f, 0.3675f,
            0.3675f, 0.3675f,
            0.3675f, -0.3675f
        };

        for (int i = 0; i < verticesInitial.length; i += 1) {
            verticesInitial[i] *= getScale();
        }

        vertices = new float[verticesInitial.length];

        triangles = new short[] {
            0, 1, 2,
            2, 3, 0,
            0, 4, 1,
            1, 5, 2,
            2, 6, 3,
            3, 7, 0
        };
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