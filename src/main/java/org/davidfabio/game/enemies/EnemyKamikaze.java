package org.davidfabio.game.enemies;

import org.davidfabio.game.World;

/**
 * While spawning it rotates towards player.
 * When it's spawned it moves straight towards last rotation at a high speed.
 * Get's destroyed when it leaves the level area.
 */
public class EnemyKamikaze extends Enemy {


    public EnemyKamikaze() {
        setType(Type.KAMIKAZE);
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
