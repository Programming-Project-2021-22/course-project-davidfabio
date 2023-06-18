package org.davidfabio.game.enemies;

import org.davidfabio.game.World;

/**
 * While spawning it rotates towards player.
 * When it's spawned it moves straight towards last rotation at a high speed.
 * Get's destroyed when it leaves the level area.
 */
public class EnemyKamikaze extends Enemy {
    /**
     * Constructor of the EnemyKamikaze-class. It sets the EnemyType to KAMIKAZE.
     */
    public EnemyKamikaze() {
        setType(Type.KAMIKAZE);
    }

    /**
     * This Enemy updates the Angle towards the Player only during the "spawn" time.
     * Once the Enemy is fully spawned, therefore active, the Enemy takes the direction and quickly moves in that direction.
     * If the Enemy does not hit anything, it dies once outside the Level.
     *
     * @param deltaTime Delta by which the game loop updated
     * @param world World object reference used for side-effects
     */
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
