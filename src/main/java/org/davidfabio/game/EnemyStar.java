package org.davidfabio.game;

import com.badlogic.gdx.graphics.Color;


public class EnemyStar extends Enemy {

    private float scaleCounter = 0;
    private float scalingStopsAfter = 0.2f;
    private boolean scaleIncreasing = true;
    private float scaleInitial;

    private boolean isBlowingUp = false;

    public boolean getIsBlowingUp() { return isBlowingUp; }


    @Override
    public void init(float x, float y, float scale, float moveSpeed, int newInitialHealth, Color color) {
        setType(Type.STAR); // NOTE (David): type needs to be set BEFORE calling the super constructor!
        super.init(x, y, scale, moveSpeed, newInitialHealth, color);

        scaleInitial = scale;
    }


    public void update(float deltaTime, World world) {
        super.update(deltaTime, world);

        if (!getIsActive())
            return;
        if (getIsSpawning())
            return;

        if (isBlowingUp) {
            scaleCounter += (deltaTime * scaleCounter * 5);
            if (Collision.polygonFullyOusideLevel(this, world)) {
                setHealth(0);
                setIsActive(false);
            }
        }
        else {
            if (scaleIncreasing)
                scaleCounter += deltaTime;
            else
                scaleCounter -= deltaTime;

            if (scaleCounter > scalingStopsAfter || scaleCounter < -scalingStopsAfter)
                scaleIncreasing = !scaleIncreasing;
            scaleCounter = Math.min(scaleCounter, scalingStopsAfter);
            scaleCounter = Math.max(scaleCounter, -scalingStopsAfter);

            setTransparency(scaleCounter + 0.5f);
        }

        setShape(PolygonShape.getEnemyShape(getType(), scaleInitial + (scaleCounter * getScale())));
        getShape().resetPosition();
        getShape().translatePosition(this);
    }


    @Override
    public void destroy(World world) {
        isBlowingUp = true;
        scaleCounter = Math.abs(scaleCounter);
        setTransparency(0.5f);
        Sounds.playExplosionEnemyStarSfx();
    }

}
