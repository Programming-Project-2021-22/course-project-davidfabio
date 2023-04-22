package org.davidfabio.game;

import com.badlogic.gdx.graphics.Color;

public class Bullet extends Entity {
    private boolean toDestroyNextFrame = false;
    public boolean getToDestroyNextFrame() { return toDestroyNextFrame; }
    public void setToDestroyNextFrame(boolean toDestroyNextFrame) { this.toDestroyNextFrame = toDestroyNextFrame; }


    public void init(float x, float y, float scale, float moveSpeed, float angle, Color color) {
        super.init(x, y, scale, color);
        setMoveSpeed(moveSpeed);
        setAngle(angle);
    }

    public void update(float deltaTime, World world) {
        if (!isInView(world.getLevel()))
            setIsActive(false);

        if (toDestroyNextFrame) {
            toDestroyNextFrame = false;
            setIsActive(false);
        }
        if (!getIsActive())
            return;

        moveTowards(getAngle(), deltaTime);
    }
}
