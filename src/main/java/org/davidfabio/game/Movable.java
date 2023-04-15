package org.davidfabio.game;


public interface Movable {
    float getX();
    void setX(float newX);
    float getY();
    void setY(float newY);
    float getScale();
    void setScale(float newScale);
    float getMoveSpeed();


    default boolean isInView(Level level) {
        float halfScale = getScale() / 2;
        if (this.getX() - halfScale < 0)
            return false;
        else if (this.getX() + halfScale > level.getWidth())
            return false;
        else if (this.getY() - halfScale < 0)
            return false;
        else return !(this.getY() + halfScale > level.getHeight());
    }

    default void restrictToLevel(Level level) {
        float x = getX();
        float y = getY();
        float halfScale = getScale() / 2;
        x = Math.max(x, halfScale); // set x minimum to halfScale
        x = Math.min(x, level.getWidth() - halfScale); // set x maximum to game width - halfScale
        y = Math.max(y, halfScale); // set y minimum to halfScale
        y = Math.min(y, level.getHeight() - halfScale); // set y maximum to game height - halfScale
        setX(x);
        setY(y);
    }

    default void moveTowards(float angle, float deltaTime) {
        float speed = getMoveSpeed() * deltaTime;
        float deltaX = (float)Math.cos(angle) * speed;
        float deltaY = (float)Math.sin(angle) * speed;

        this.setX(getX() + deltaX);
        this.setY(getY() + deltaY);
    }

    default void moveTowards(float otherX, float otherY, float deltaTime) {
        float dir = getAngleTowards(otherX, otherY);
        moveTowards(dir, deltaTime);
    }

    default float getAngleTowards(float otherX, float otherY) {
        return ((float)Math.atan2(otherY - this.getY(), otherX - this.getX()));
    }

    default float getDistanceTo(float otherX, float otherY) {
        float distanceX = this.getX() - otherX;
        float distanceY = this.getY() - otherY;
        return (float)Math.sqrt((distanceX * distanceX) + (distanceY * distanceY));
    }
}
