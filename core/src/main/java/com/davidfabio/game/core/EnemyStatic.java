package com.davidfabio.game.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;

public class EnemyStatic extends Enemy {

    @Override
    public void init(float x, float y, float scale, float moveSpeed, float newInitialHealth, Color color) {
        super.init(x, y, scale, moveSpeed, newInitialHealth, color);

        shape = new PolygonShape(4, scale);
    }


    @Override
    public void update(float deltaTime, World world) {
        super.update(deltaTime, world);

        if (!getIsActive())
            return;
        if (getIsSpawning())
            return;

        if (getFireRateCooldown() > 0)
            setFireRateCooldown(getFireRateCooldown() - deltaTime);

        if (getFireRateCooldown() <= 0)
            shootTowardsPlayer(world);
    }


    @Override
    public void destroy(World world) {
        this.setHealth(0f);
        this.setIsActive(false);
        this.playDestructionSound();

        // spawn bullets in all direction
        if (getHealth() <= 0) {
            for (int i = 0; i < 12; i += 1)
                shoot(world, Transform2D.degreesToRadians(i * 30));
        }
    }
}
