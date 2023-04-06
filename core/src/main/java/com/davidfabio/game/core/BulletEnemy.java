package com.davidfabio.game.core;

public class BulletEnemy extends Bullet implements Attacker {
    private float attackPower = 1.0f;

    public float getAttackPower() { return this.attackPower; }
    public void setAttackPower(float newAttackPower) { this.attackPower = newAttackPower; }

    @Override
    public void init(float x, float y, float scale, Polarity polarity, float moveSpeed, float direction) {
        super.init(x, y, scale, polarity, moveSpeed, direction);

        float[] vertices = new float[] {
            0, -0.5f,
            -0.5f, 0,
            0, 0.5f,
            0.5f, 0,
            -0.3675f, -0.3675f,
            -0.3675f, 0.3675f,
            0.3675f, 0.3675f,
            0.3675f, -0.3675f
        };
        short[] triangles = new short[] {
            0, 1, 2,
            2, 3, 0,
            0, 4, 1,
            1, 5, 2,
            2, 6, 3,
            3, 7, 0
        };
        shape = new PolygonShape(vertices, triangles, scale);
    }

    public void update(float deltaTime, World world) {
        super.update(deltaTime);

        if (!getIsActive())
            return;

        // ---------------- collision detection ----------------
        if (Collision.circleCircle(getX(), getY(), getScale(), world.getPlayer().getX(), world.getPlayer().getY(), world.getPlayer().getScale())) {

            // we leave the bullet alive for 1 extra frame, so that we can draw it at the exact position where it touches the player
            setToDestroyNextFrame(true);
            while (Collision.circleCircle(getX(), getY(), getScale(), world.getPlayer().getX(), world.getPlayer().getY(), world.getPlayer().getScale())) {
                setX(getX() - (float)Math.cos(getAngle()));
                setY(getY() - (float)Math.sin(getAngle()));
            }
            this.attack(world.getPlayer(),world);
        }
    }
}
