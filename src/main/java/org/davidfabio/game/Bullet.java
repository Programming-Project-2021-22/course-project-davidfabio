package org.davidfabio.game;

import com.badlogic.gdx.graphics.Color;

/**
 * This class represents the projectiles that an {@link org.davidfabio.game.enemies.Enemy} or {@link Player} may create
 * and shoot.
 */
public class Bullet extends Entity implements Attacker {
    /**
     * This is the damage that is applied upon hitting an {@link Attackable}.
     */
    private int attackPower = 1;

    /**
     * Returns the damage that is applies upon hitting an {@link Attackable}.
     * @return damage to apply
     */
    public int getAttackPower() {
        return attackPower;
    }

    /**
     * This method initializes or resets the Entity object. We use this method instead of creating a new Object to reduce
     * the memory allocation during gameplay.
     *
     * @param x x-position for the Bullet
     * @param y y-position for the Bullet
     * @param scale size for the Bullet (or the shape)
     * @param moveSpeed Bullet velocity
     * @param angle angle in which the Bullet is shot
     * @param color color for the display of the Bullet
     * @param shape shape for the display of the Bullet
     */
    public void init(float x, float y, float scale, float moveSpeed, float angle, Color color, PolygonShape shape) {
        super.init(x, y, scale, color);
        setMoveSpeed(moveSpeed);
        setAngle(angle);
        setShape(shape);
    }

    /**
     * This method updates the Bullet state periodically. It moves the Bullet in it's angle until it's outside the Level
     * or the {@link Collision} class detects a collision.
     *
     * @param deltaTime Delta by which the game loop updated
     * @param world World object reference used to verify if the Bullet is in Level
     */
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
