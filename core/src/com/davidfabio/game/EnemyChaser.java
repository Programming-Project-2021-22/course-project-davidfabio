package com.davidfabio.game;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;

public class EnemyChaser extends Enemy {
    private float xScaleCounter = 0;
    private float xScalingStopsAfter = 0.1f;
    private boolean xScaleIncreasing = true;


    @Override public void init(float x, float y, float scale, float direction, Polarity polarity, float moveSpeed, float newInitialHealth) {
        super.init(x, y, scale, direction, polarity, moveSpeed, newInitialHealth);

        float[] vertices = new float[] {
                0, -0.5f,
                -0.25f, 0,
                0, 0.5f,
                0.25f, 0
        };
        short[] triangles = new short[] {
                0, 1, 2,
                2, 3, 0
        };
        shape = new PolygonShape(vertices, triangles, scale);
    }


    @Override
    public void update(float deltaTime, BulletEnemy[] enemyBullets, Player player) {
        super.update(deltaTime, enemyBullets, player);

        if (!getIsActive())
            return;
        if (getIsSpawning())
            return;

        float angle = getAngleTowards(player.getX(), player.getY());
        setDirection(Movable.radiansToDegrees(angle));


        // stretching/squashing width
        float stretchSpeed = deltaTime / 3;
        if (xScaleIncreasing)
            xScaleCounter += stretchSpeed;
        else
            xScaleCounter -= stretchSpeed;

        if (xScaleCounter > xScalingStopsAfter || xScaleCounter < -xScalingStopsAfter)
            xScaleIncreasing = !xScaleIncreasing;

        xScaleCounter = Math.min(xScaleCounter, xScalingStopsAfter);
        xScaleCounter = Math.max(xScaleCounter, -xScalingStopsAfter);


        moveTowards(player.getX(), player.getY(), deltaTime);
    }

    @Override public void render(PolygonSpriteBatch polygonSpriteBatch) {
        float[] vertices = shape.getVerticesInitial();
        vertices[2] -= xScaleCounter * getScale();
        vertices[6] += xScaleCounter * getScale();

        shape.render(polygonSpriteBatch, this, vertices);
    }
}
