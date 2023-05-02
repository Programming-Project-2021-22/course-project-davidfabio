package org.davidfabio.game.enemies;

import com.badlogic.gdx.graphics.Color;
import org.davidfabio.game.BulletEnemySpawner;
import org.davidfabio.game.PolygonShape;
import org.davidfabio.game.World;
import org.davidfabio.utils.Transform2D;

/**
 * Doesn't move. Shoots bullets that kill the player when he collides with them.
 */

public class EnemyTurret extends Enemy {

    BulletEnemySpawner bulletSpawner;


    public EnemyTurret() {
        setType(Type.TURRET);
    }

    @Override
    public void init(float x, float y, float scale, float moveSpeed, int newInitialHealth, Color color) {
        super.init(x, y, scale, moveSpeed, newInitialHealth, color);

        setShape(new PolygonShape(4, scale));
        bulletSpawner = new BulletEnemySpawner(false, 0.04f, 1.33f, 25, 200, 16, 20, true);
    }


    @Override
    public void update(float deltaTime, World world) {
        super.update(deltaTime, world);

        if (!getIsActive())
            return;
        if (getIsSpawning())
            return;

        bulletSpawner.update(deltaTime, world, this);
    }


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
