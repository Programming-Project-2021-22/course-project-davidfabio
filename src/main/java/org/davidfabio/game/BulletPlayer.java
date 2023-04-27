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
        setShape(new PolygonShape(vertices, triangles, scale));
    }

}
