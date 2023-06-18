package org.davidfabio.game.enemies;

import com.badlogic.gdx.graphics.Color;
import org.davidfabio.game.BulletEnemySpawner;
import org.davidfabio.game.PolygonShape;
import org.davidfabio.game.World;
import org.davidfabio.utils.Transform2D;

/**
 * Doesn't move. Shoot's bullets that kill the player when he collides with them.
 */
public class EnemyTurret extends Enemy {
    /**
     * This {@link BulletEnemySpawner} is used to periodically shoot bullets in a circular motion.
     */
    BulletEnemySpawner bulletSpawner;

    /**
     * Constructor of the EnemyTurret-class. It sets the EnemyType to TURRET.
     */
    public EnemyTurret() {
        setType(Type.TURRET);
    }

    /**
     * Initializes the EnemyTurret object. It also initializes the {@link EnemyTurret#bulletSpawner}.
     *
     * @param x x-position for the Enemy
     * @param y y-position for the Enemy
     * @param scale size for the Enemy (or the shape)
     * @param moveSpeed Enemy velocity
     * @param newInitialHealth initial health, if 0 {@link Enemy#initialHealth} is used
     * @param color color for the display of the Enemy
     */
    @Override
    public void init(float x, float y, float scale, float moveSpeed, int newInitialHealth, Color color) {
        super.init(x, y, scale, moveSpeed, newInitialHealth, color);

        setShape(new PolygonShape(4, scale));
        bulletSpawner = new BulletEnemySpawner(false, 0.04f, 1.33f, 25, 200, 16, 20, true);
    }

    /**
     * Updates the EnemyTurret's state. This also updates the Bullet Spawner's state using {@link BulletEnemySpawner#update(float, World, Enemy)}.
     *
     * @param deltaTime Delta by which the game loop updated
     * @param world World object reference used for side-effects
     */
    @Override
    public void update(float deltaTime, World world) {
        super.update(deltaTime, world);

        if (!getIsActive())
            return;
        if (getIsSpawning())
            return;

        bulletSpawner.update(deltaTime, world, this);
    }

    /**
     * Kills the EnemyTurret. When the EnemyTurret dies, it fires Bullets in all directions.
     * @param world World object reference, used for side-effects
     */
    @Override
    public void destroy(World world) {
        // spawn bullets in all direction (suicide bullets)
        int bulletCount = 128;
        float angleDelta = 360f / bulletCount;
        if (getHealth() <= 0) {
            for (int i = 0; i < bulletCount; i += 1)
                bulletSpawner.shoot(world, this, Transform2D.degreesToRadians(i * angleDelta));
        }

        super.destroy(world);
    }
}
