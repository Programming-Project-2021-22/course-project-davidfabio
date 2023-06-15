package org.davidfabio.game;

import org.davidfabio.utils.Transform2D;

/**
 * This interface is used for all Entities that are moving in the game.
 * This mainly includes any class that extends {@link Entity}.
 */
public interface Movable {
    /**
     * @return The x-position of the Movable object.
     */
    float getX();

    /**
     * @param newX The new x-position for the Movable object.
     */
    void setX(float newX);

    /**
     * @return The y-position of the Movable object.
     */
    float getY();

    /**
     * @param newY The new y-position for the Movable object.
     */
    void setY(float newY);

    /**
     * @return the scale for the Movable object. (Used for drawing)
     */
    float getScale();

    /**
     * @param newScale the new scale for the Movable object. (Used for drawing)
     */
    void setScale(float newScale);

    /**
     * @return The Movable object's movement speed/velocity.
     */
    float getMoveSpeed();

    /**
     * @return The {@link PolygonShape} used to draw this Movable object.
     */
    PolygonShape getShape();

    /**
     * @param world world object reference, needed for side-effects or references of other objects in the world.
     * @return true if at least 1 vertex of the shape is inside level
     */
    default boolean isInView(World world) {
        float[] vertices = getShape().getVertices();
        for (int i = 0; i < vertices.length; i += 2) {
            float x = vertices[i];
            float y = vertices[i + 1];

            if (Collision.pointIsInLevel(x, y, world))
                return true;
        }

        return false;
    }

    /**
     * @param world world object reference, needed for side-effects or references of other objects in the world.
     * @return true if all vertices of the shape are inside level
     */
    default boolean isCompletelyInView(World world) {
        float[] vertices = getShape().getVertices();
        for (int i = 0; i < vertices.length; i += 2) {
            float x = vertices[i];
            float y = vertices[i + 1];

            if (!Collision.pointIsInLevel(x, y, world))
                return false;
        }

        return true;
    }

    /**
     * This method is used to ensure that the Movable object remains within the boundaries of the level.
     * In order to achieve this, the x and y position is modified, should the object be outside the level.
     *
     * @param level Level where the object has to stay in
     */
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

    /**
     * Moves the Movable object in the direction of the passed angle. The movement-speed is multiplied with the
     * deltaTime parameter in order to determine the new position.
     *
     * @param angle direction in which we need to move in
     * @param deltaTime Delta by which the game loop updated
     */
    default void moveTowards(float angle, float deltaTime) {
        float speed = getMoveSpeed() * deltaTime;
        float deltaX = (float)Math.cos(angle) * speed;
        float deltaY = (float)Math.sin(angle) * speed;

        setX(getX() + deltaX);
        setY(getY() + deltaY);
    }

    /**
     * Moves the Movable object in the direction of the passed point (otherX, otherY). The movement-speed is multiplied
     * with the deltaTime parameter in order to determine the new position.
     *
     * @param otherX target point's x-position
     * @param otherY target point's y-position
     * @param deltaTime Delta by which the game loop updated
     */
    default void moveTowards(float otherX, float otherY, float deltaTime) {
        float dir = getAngleTowards(otherX, otherY);
        moveTowards(dir, deltaTime);
    }

    /**
     * Calculates the angle between the movable object and the other point (otherX, otherY).
     *
     * @param otherX target point's x-position
     * @param otherY target point's y-position
     * @return angle between this and target point.
     */
    default float getAngleTowards(float otherX, float otherY) {
        return Transform2D.getAngleTowards(getX(), getY(), otherX, otherY);
    }

    /**
     * Calculates the distance between the movable object and the other point (otherX, otherY).
     * @param otherX target point's x-position
     * @param otherY target point's y-position
     * @return distance between this and target point
     */
    default float getDistanceTo(float otherX, float otherY) {
        return Transform2D.getDistance(getX(),getY(),otherX,otherY);
    }
}
