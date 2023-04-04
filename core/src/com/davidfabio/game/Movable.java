package com.davidfabio.game;

public interface Movable {
    public float getX();
    public void setX(float newX);
    public float getY();
    public void setY(float newY);
    public float getScale();
    public void setScale(float newScale);
    public float getMoveSpeed();

    public default boolean isInView() {
        if (this.getX() + this.getScale() < 0)
            return false;
        else if (this.getX() - this.getScale() > Settings.windowWidth)
            return false;
        else if (this.getY() + this.getScale() < 0)
            return false;
        else if (this.getY() - this.getScale() > Settings.windowHeight)
            return false;
        return true;
    }

    public default void moveTowards(float direction, float deltaTime) {
        float speed = getMoveSpeed() * deltaTime;
        float deltaX = (float)Math.cos(direction) * speed;
        float deltaY = (float)Math.sin(direction) * speed;

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

    public static float radiansToDegrees(float angleRadians) {
        return angleRadians * (float)(180 / Math.PI);
    }

    public static float degreesToRadians(float angleDegrees) {
        return angleDegrees * (float)(Math.PI / 180);
    }
}
