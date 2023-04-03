package com.davidfabio.game;

public class EnemyStatic extends Enemy {


    @Override public void init(float x, float y, float scale, float direction, Polarity polarity, float moveSpeed, float healthInitial) {
        super.init(x, y, scale, direction, polarity, moveSpeed, healthInitial);

        verticesInitial = new float[]{
                0, -0.5f, // top
                -0.5f, 0, // left
                0, 0.5f,  // bottom
                0.5f, 0   // right
        };

        for (int i = 0; i < verticesInitial.length; i += 1) {
            verticesInitial[i] *= getScale();
        }

        vertices = new float[verticesInitial.length];

        triangles = new short[]{
                0, 1, 2,
                2, 3, 0
        };
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
            shoot();
    }

}
