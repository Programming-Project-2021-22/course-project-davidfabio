package org.davidfabio.game;

import com.badlogic.gdx.graphics.Color;

public class Bullet extends Entity implements Attacker {

    private int attackPower = 1;

    public int getAttackPower() { return this.attackPower; }


    public void init(float x, float y, float scale, float moveSpeed, float angle, Color color, PolygonShape shape) {
        super.init(x, y, scale, color);
        setMoveSpeed(moveSpeed);
        setAngle(angle);
        setShape(shape);
    }

    public void update(float deltaTime, World world) {
        if (!getIsActive())
            return;
        if (!isInView(world))
            setIsActive(false);

        moveTowards(getAngle(), deltaTime);

        getShape().resetPosition();
        getShape().rotate(getAngle());
        getShape().translatePosition(this);
    }

}
