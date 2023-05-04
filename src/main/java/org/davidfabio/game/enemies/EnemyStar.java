package org.davidfabio.game.enemies;

import com.badlogic.gdx.graphics.Color;
import org.davidfabio.game.*;
import org.davidfabio.utils.Pulsation;


/**
 * Doesn't move.
 * When destroyed it grows larger (until it fills the whole level) and kills all enemies it touches.
 */

public class EnemyStar extends Enemy {

    private Pulsation pulsation;
    private float blowingUpCounter;
    private boolean isBlowingUp = false;

    public boolean getIsBlowingUp() { return isBlowingUp; }

    public EnemyStar() {
        setType(Type.STAR);
    }

    @Override
    public void init(float x, float y, float scale, float moveSpeed, int newInitialHealth, Color color) {
        super.init(x, y, scale, moveSpeed, newInitialHealth, color);
        isBlowingUp = false;
        pulsation = new Pulsation(1, 0.2f);
    }


    public void update(float deltaTime, World world) {
        super.update(deltaTime, world);

        if (!getIsActive())
            return;
        if (getIsSpawning())
            return;

        float newScale = getScale();

        if (!isBlowingUp) {
            pulsation.update(deltaTime);
            newScale += pulsation.getCounter() * getScale();
            setTransparency(pulsation.getCounter() + 0.5f);
        }
        else {
            setColor(new Color(1, 0, 0, 0.5f)); // TODO: should only be called once in destroy method, but for some reason that is not working
            blowingUpCounter += deltaTime * blowingUpCounter * 5;
            newScale += blowingUpCounter * getScale();
            if (Collision.polygonFullyOusideLevel(this, world)) {
                setHealth(0);
                setIsActive(false);
            }
        }

        setShape(PolygonShape.getEnemyShape(getType(), newScale));
        getShape().resetPosition();
        getShape().translatePosition(this);
    }


    @Override
    public void destroy(World world) {
        isBlowingUp = true;
        blowingUpCounter = Math.abs(pulsation.getCounter());
        Sounds.playExplosionEnemyStarSfx();
    }

}
