package org.davidfabio.game.enemies;

import com.badlogic.gdx.graphics.Color;
import org.davidfabio.game.World;
import org.davidfabio.utils.Pulsation;

/**
 * Moves towards player.
 */

public class EnemyChaser extends Enemy {

    private Pulsation pulsation;


    public EnemyChaser() {
        setType(Type.CHASER);
    }

    @Override
    public void init(float x, float y, float scale, float moveSpeed, int newInitialHealth, Color color) {
        super.init(x, y, scale, moveSpeed, newInitialHealth, color);

        pulsation = new Pulsation(0.33f, 0.1f);
    }


    @Override
    public void update(float deltaTime, World world) {
        super.update(deltaTime, world);

        if (!getIsActive())
            return;
        if (getIsSpawning())
            return;

        pulsation.update(deltaTime);

        getShape().resetPosition();
        getShape().getVertices()[2] -= pulsation.getCounter() * getScale();
        getShape().getVertices()[6] += pulsation.getCounter() * getScale();
        getShape().rotate(getAngle());
        getShape().translatePosition(this);

        float angle = getAngleTowards(world.getPlayer().getX(), world.getPlayer().getY());
        setAngle(angle);
        moveTowards(world.getPlayer().getX(), world.getPlayer().getY(), deltaTime);
    }
}
