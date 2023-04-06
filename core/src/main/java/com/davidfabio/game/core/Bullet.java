package com.davidfabio.game.core;

public class Bullet extends Entity {
    private boolean toDestroyNextFrame = false;
    public boolean getToDestroyNextFrame() { return toDestroyNextFrame; }
    public void setToDestroyNextFrame(boolean toDestroyNextFrame) { this.toDestroyNextFrame = toDestroyNextFrame; }


    public void init(float x, float y, float scale, float moveSpeed, float direction) {
        super.init(x, y, scale);
        setMoveSpeed(moveSpeed);
        setAngle(direction);
    }


    public void update(float deltaTime) {
        if (!isInView())
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
