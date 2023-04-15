package org.davidfabio.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;

public class EnemyChaser extends Enemy {
    private float xScaleCounter = 0;
    private float xScalingStopsAfter = 0.1f;
    private boolean xScaleIncreasing = true;


    @Override
    public void init(float x, float y, float scale, float moveSpeed, int newInitialHealth, Color color) {
        super.init(x, y, scale, moveSpeed, newInitialHealth, color);

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
    public void update(float deltaTime, World world) {
        super.update(deltaTime, world);

        if (!getIsActive())
            return;
        if (getIsSpawning())
            return;

        float angle = getAngleTowards(world.getPlayer().getX(), world.getPlayer().getY());
        setAngle(angle);


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


        moveTowards(world.getPlayer().getX(), world.getPlayer().getY(), deltaTime);
    }


    @Override
    public void render(PolygonSpriteBatch polygonSpriteBatch) {
        float[] vertices = shape.getVerticesInitial();
        vertices[2] -= xScaleCounter * getScale();
        vertices[6] += xScaleCounter * getScale();

        shape.render(polygonSpriteBatch, this, vertices, getColor());
    }
}
