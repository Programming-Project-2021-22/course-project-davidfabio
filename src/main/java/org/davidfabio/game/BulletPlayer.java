package org.davidfabio.game;

import com.badlogic.gdx.graphics.Color;

public class BulletPlayer extends Bullet implements Attacker {
    private int attackPower = 1;
    private float width = 8;
    private float height = 32;

    public int getAttackPower() { return this.attackPower; }
    public void setAttackPower(int newAttackPower) { this.attackPower = newAttackPower; }

    @Override
    public void init(float x, float y, float scale, float moveSpeed, float angle, Color color) {
        super.init(x, y, scale, moveSpeed, angle, color);

        float[] vertices = new float[] {
            -0.5f, -0.25f,
            -0.5f, 0.25f,
            0.5f, 0
        };
        short[] triangles = new short[] {
            0, 1, 2
        };
        shape = new PolygonShape(vertices, triangles, scale);
    }

    public void update(float deltaTime, World world) {
        super.update(deltaTime, world);

        if (!getIsActive())
            return;


        // ---------------- collision detection ----------------
        for (Enemy enemy : world.getEnemies()) {
            if (!enemy.getIsActive())
                continue;
            if (enemy.getIsSpawning())
                continue;


            if (Collision.circleCircle(getX(), getY(), getScale(), enemy.getX(), enemy.getY(), enemy.getScale())) {
                this.attack(enemy,world);
                if (!enemy.getIsActive())
                    world.getScore().setPoints(world.getScore().getPoints() + Enemy.POINT_VALUE);

                // we leave the bullet alive for 1 extra frame, so that we can draw it at the exact position where it touches the enemy
                // TODO (David): shape changed from circle to ellipsis; collision detection needs to be updated!
                setToDestroyNextFrame(true);
                while (Collision.circleCircle(getX(), getY(), getScale(), enemy.getX(), enemy.getY(), enemy.getScale())) {
                    setX(getX() - (float)Math.cos(getAngle()));
                    setY(getY() - (float)Math.sin(getAngle()));
                }
                break;
            }
        }
    }
}
