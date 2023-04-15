package org.davidfabio.game;

import com.badlogic.gdx.graphics.Color;

public class EnemyKamikaze extends Enemy {


    @Override
    public void init(float x, float y, float scale, float moveSpeed, float newInitialHealth, Color color) {
        super.init(x, y, scale, moveSpeed, newInitialHealth, color);

        float[] vertices = new float[] {
                0.5f, 0,
                -0.5f, -0.5f,
                -0.25f, 0,
                -0.5f, 0.5f
        };
        short[] triangles = new short[] {
                0, 1, 2,
                2, 3, 0
        };
        shape = new PolygonShape(vertices, triangles, scale);
    }


    @Override
    public void update(float deltaTime, World world) {
        super.update(deltaTime, world);

        if (!getIsActive())
            return;
        if (getIsSpawning()) {
            float angleTowardsPlayer = getAngleTowards(world.getPlayer().getX(), world.getPlayer().getY());
            setAngle(angleTowardsPlayer);
            return;
        }

        moveTowards(getAngle(), deltaTime);
        setMoveSpeed(getMoveSpeed() + (deltaTime * 600.0f));

        if (!isInView(world.getLevel()))
            destroy(world);
    }
}
