package com.davidfabio.game;

public class Bullet extends Entity {

    private boolean toDestroyNextFrame = false;
    public boolean getToDestroyNextFrame() { return toDestroyNextFrame; }
    public void setToDestroyNextFrame(boolean toDestroyNextFrame) { this.toDestroyNextFrame = toDestroyNextFrame; }


    public void init(float x, float y, float radius, float direction, Polarity polarity, float moveSpeed) {
        super.init(x, y, radius, direction, polarity);
        setMoveSpeed(moveSpeed);
    }


    public void update(float deltaTime) {
        if (!isInView())
            setActive(false);

        if (toDestroyNextFrame) {
            toDestroyNextFrame = false;
            setActive(false);
        }
        if (!getActive())
            return;

        moveTowards(getDirection(), deltaTime);
    }

}
