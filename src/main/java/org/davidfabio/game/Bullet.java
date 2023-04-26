package org.davidfabio.game;

import com.badlogic.gdx.graphics.Color;

public class Bullet extends Entity {

    public void init(float x, float y, float scale, float moveSpeed, float angle, Color color) {
        super.init(x, y, scale, color);
        setMoveSpeed(moveSpeed);
        setAngle(angle);
    }

    public void update(float deltaTime, World world) {
        if (!isInView(world.getLevel()))
            setIsActive(false);
        if (!getIsActive())
            return;

        moveTowards(getAngle(), deltaTime);

        shape.resetPosition();
        shape.rotate(getAngle());
        shape.translatePosition(this);
    }
}
