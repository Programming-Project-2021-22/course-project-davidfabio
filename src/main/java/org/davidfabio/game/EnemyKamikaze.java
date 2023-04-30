package org.davidfabio.game;

import com.badlogic.gdx.graphics.Color;

/**
 * While spawning it rotates towards player.
 * When it's spawned it moves straight towards last rotation at a high speed.
 * Get's destroyed when it leaves the level area.
 */


public class EnemyKamikaze extends Enemy {


    @Override
    public void init(float x, float y, float scale, float moveSpeed, int newInitialHealth, Color color) {
        setType(Type.KAMIKAZE); // NOTE (David): type needs to be set BEFORE calling the super constructor!
        super.init(x, y, scale, moveSpeed, newInitialHealth, color);
    }


    @Override
    public void update(float deltaTime, World world) {
        super.update(deltaTime, world);

        if (!getIsActive())
            return;
        if (getIsSpawning()) {
            float angleTowardsPlayer = getAngleTowards(world.getPlayer().getX(), world.getPlayer().getY());
            setAngle(angleTowardsPlayer);
        }
        else {
            moveTowards(getAngle(), deltaTime);
            setMoveSpeed(getMoveSpeed() + (deltaTime * 600.0f));
        }

        if (!isInView(world))
            destroy(world);
    }
}
