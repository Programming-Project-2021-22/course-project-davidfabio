package com.davidfabio.game;

import java.util.ArrayList;

public class BulletPlayer extends Bullet implements Attacker {
    private float attackPower = 1.0f;
    private float width = 8;
    private float height = 32;

    public float getAttackPower() { return this.attackPower; }
    public void setAttackPower(float newAttackPower) { this.attackPower = newAttackPower; }

    @Override public void init(float x, float y, float scale, Polarity polarity, float moveSpeed, float direction) {
        super.init(x, y, scale, polarity, moveSpeed, direction);

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

    public void update(float deltaTime, ArrayList<Enemy> enemies, Score score) {
        super.update(deltaTime);

        if (!getIsActive())
            return;


        // ---------------- collision detection ----------------
        for (Enemy enemy : enemies) {
            if (!enemy.getIsActive())
                continue;
            if (enemy.getIsSpawning())
                continue;

            if (Collision.circleCircle(getX(), getY(), getScale(), enemy.getX(), enemy.getY(), enemy.getScale())) {
                this.attack(enemy);
                if (!enemy.getIsActive())
                    score.setPoints(score.getPoints() + Enemy.POINT_VALUE);

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
