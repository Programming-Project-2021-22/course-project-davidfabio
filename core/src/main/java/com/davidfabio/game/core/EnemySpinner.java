package com.davidfabio.game.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;

public class EnemySpinner extends Enemy {


    private float rotationSpeed = 600; //1200f;
    private float rotationAngle;


    @Override
    public void init(float x, float y, float scale, float moveSpeed, float newInitialHealth, Color color) {
        super.init(x, y, scale, moveSpeed, newInitialHealth, color);

        float[] vertices = new float[] {
                0.5f, 0,
                0, -0.125f,
                -0.5f, 0,
                0, 0.125f
        };
        short[] triangles = new short[] {
                0, 1, 2,
                2, 3, 0
        };
        shape = new PolygonShape(vertices, triangles, scale);

        setFireRate(0.0003f);
    }



    @Override
    public void render(PolygonSpriteBatch polygonSpriteBatch) {
        shape.render(polygonSpriteBatch, getX(), getY(), rotationAngle, getColor());
    }


    @Override
    public void update(float deltaTime, World world) {
        super.update(deltaTime, world);

        if (!getIsActive())
            return;
        if (getIsSpawning())
            return;

        rotationAngle += rotationSpeed * deltaTime;


        if (getFireRateCooldown() > 0)
            setFireRateCooldown(getFireRateCooldown() - deltaTime);

        if (getFireRateCooldown() <= 0)
            shoot(world, rotationAngle);


        float angleTowardsPlayer = getAngleTowards(world.getPlayer().getX(), world.getPlayer().getY());
        setAngle(angleTowardsPlayer);

        //moveTowards(getAngle(), deltaTime);
    }

}
