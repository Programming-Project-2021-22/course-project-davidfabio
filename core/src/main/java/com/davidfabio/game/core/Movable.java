package com.davidfabio.game.core;

public interface Movable {
    public float getX();
    public void setX(float newX);
    public float getY();
    public void setY(float newY);
    public float getScale();
    public void setScale(float newScale);
    public float getMoveSpeed();


    public default boolean isInView() {
        float halfScale = getScale() / 2;
        if (this.getX() - halfScale < 0)
            return false;
        else if (this.getX() + halfScale > Settings.windowWidth)
            return false;
        else if (this.getY() - halfScale < 0)
            return false;
        else if (this.getY() + halfScale > Settings.windowHeight)
            return false;
        return true;
    }

    public default void restrictToLevel() {
        float x = getX();
        float y = getY();
        float halfScale = getScale() / 2;
        x = Math.max(x, halfScale); // set x minimum to halfScale
        x = Math.min(x, Settings.windowWidth - halfScale); // set x maximum to game width - halfScale
        y = Math.max(y, halfScale); // set y minimum to halfScale
        y = Math.min(y, Settings.windowHeight - halfScale); // set y maximum to game height - halfScale
        setX(x);
        setY(y);
    }

    public default void moveTowards(float angle, float deltaTime) {
        float speed = getMoveSpeed() * deltaTime;
        float deltaX = (float)Math.cos(angle) * speed;
        float deltaY = (float)Math.sin(angle) * speed;

        this.setX(getX() + deltaX);
        this.setY(getY() + deltaY);
    }

    public default void moveTowards(float otherX, float otherY, float deltaTime) {
        float dir = getAngleTowards(otherX, otherY);
        moveTowards(dir, deltaTime);
    }

    public default float getAngleTowards(float otherX, float otherY) {
        return ((float)Math.atan2(otherY - this.getY(), otherX - this.getX()));
    }

    public default float getDistanceTo(float otherX, float otherY) {
        float distanceX = this.getX() - otherX;
        float distanceY = this.getY() - otherY;
        return (float)Math.sqrt((distanceX * distanceX) + (distanceY * distanceY));
    }
}
