package org.davidfabio.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import org.davidfabio.utils.Transform2D;

public class EnemyChaser extends Enemy {
    private float xScaleCounter = 0;
    private float xScalingStopsAfter = 0.1f;
    private boolean xScaleIncreasing = true;


    @Override
    public void init(float x, float y, float scale, float moveSpeed, int newInitialHealth, Color color) {
        setType(Type.CHASER); // NOTE (David): type needs to be set BEFORE calling the super constructor!
        super.init(x, y, scale, moveSpeed, newInitialHealth, color);
    }


    @Override
    public void update(float deltaTime, World world) {
        super.update(deltaTime, world);

        if (!getIsActive())
            return;
        if (getIsSpawning())
            return;

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

        shape.resetPosition();
        float[] newVertices = shape.getVertices();
        newVertices[2] -= xScaleCounter * getScale();
        newVertices[6] += xScaleCounter * getScale();
        shape.setVertices(newVertices);
        shape.rotate(getAngle());
        shape.translatePosition(this);

        float angle = getAngleTowards(world.getPlayer().getX(), world.getPlayer().getY());
        setAngle(angle);
        moveTowards(world.getPlayer().getX(), world.getPlayer().getY(), deltaTime);
    }
}
