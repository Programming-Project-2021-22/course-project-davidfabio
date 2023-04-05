package com.davidfabio.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class BulletPlayer extends Bullet {

    private float firePower = 1.0f;

    @Override public void init(float x, float y, float scale, Polarity polarity, float moveSpeed, float direction) {
        super.init(x, y, scale, polarity, moveSpeed, direction);

        float[] vertices = new float[] {
            -0.5f, -0.25f,
            -0.5f, 0.25f,
            0.5f, 0
        };
        short[] triangles = new short[] {
            0, 1, 2
        };
        shape = new PolygonShape(vertices, triangles, scale);
    }


    public void update(float deltaTime) {
        super.update(deltaTime);

        if (!getIsActive())
            return;


        // ---------------- collision detection ----------------
        for (Enemy enemy : GameScreen.enemies) {
            if (!enemy.getIsActive())
                continue;
            if (enemy.getIsSpawning())
                continue;

            if (Collision.circleCircle(getX(), getY(), getScale(), enemy.getX(), enemy.getY(), enemy.getScale())) {

                float _firePower = firePower;
                if (!getPolarity().equals(enemy.getPolarity())) {
                    _firePower *= 2;
                }
                enemy.hit(_firePower);

                // we leave the bullet alive for 1 extra frame, so that we can draw it at the exact position where it touches the enemy
                // TODO (David): shape changed from circle to ellipsis; collision detection needs to be updated!
                setToDestroyNextFrame(true);
                while (Collision.circleCircle(getX(), getY(), getScale(), enemy.getX(), enemy.getY(), enemy.getScale())) {
                    setX(getX() - (float)Math.cos(getDirection()));
                    setY(getY() - (float)Math.sin(getDirection()));
                }
                break;
            }
        }
    }


}
