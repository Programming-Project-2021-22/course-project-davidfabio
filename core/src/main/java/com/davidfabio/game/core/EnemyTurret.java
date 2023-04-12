package com.davidfabio.game.core;

import com.badlogic.gdx.graphics.Color;

public class EnemyTurret extends Enemy {

    BulletEnemySpawner bulletSpawner;

    @Override
    public void init(float x, float y, float scale, float moveSpeed, float newInitialHealth, Color color) {
        super.init(x, y, scale, moveSpeed, newInitialHealth, color);

        shape = new PolygonShape(4, scale);
        bulletSpawner = new BulletEnemySpawner(false, 0.04f, 0.25f, 6, 400, 16, 10, true);
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
        if (getHealth() <= 0) {
            for (int i = 0; i < 12; i += 1)
                bulletSpawner.shoot(world, this, Transform2D.degreesToRadians(i * 30));
        }

        super.destroy(world);
    }
}
