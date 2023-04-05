package com.davidfabio.game;

public class EnemyStatic extends Enemy {


    @Override public void init(float x, float y, float scale, float direction, Polarity polarity, float moveSpeed, float healthInitial) {
        super.init(x, y, scale, direction, polarity, moveSpeed, healthInitial);

        float[] vertices = new float[]{
                0, -0.5f,
                -0.5f, 0,
                0, 0.5f,
                0.5f, 0
        };
        short[] triangles = new short[] {
                0, 1, 2,
                2, 3, 0
        };

        shape = new PolygonShape(vertices, triangles, scale);
    }


    @Override public void update(float deltaTime) {
        super.update(deltaTime);

        if (!getIsActive())
            return;
        if (getIsSpawning())
            return;

        if (getFireRateCooldown() > 0)
            setFireRateCooldown(getFireRateCooldown() - deltaTime);

        if (getFireRateCooldown() <= 0)
            shootTowardsPlayer();
    }

    @Override public void hit(float attackPower)  {
        super.hit(attackPower);

        // spawn bullets in all direction
        if (getHealth() <= 0) {
            for (int i = 0; i < 36; i += 1)
                shoot(Helper.degreesToRadians(i * 10));
        }
    }



}
