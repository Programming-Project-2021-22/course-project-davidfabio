package org.davidfabio.game;

import com.badlogic.gdx.graphics.Color;

public class BulletEnemy extends Bullet implements Attacker {
    private int attackPower = 1;

    public int getAttackPower() { return this.attackPower; }
    public void setAttackPower(int newAttackPower) { this.attackPower = newAttackPower; }

    @Override
    public void init(float x, float y, float scale, float moveSpeed, float angle, Color color) {
        super.init(x, y, scale, moveSpeed, angle, color);
        shape = new PolygonShape(12, scale);
    }


}
