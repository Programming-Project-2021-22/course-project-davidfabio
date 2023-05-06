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
