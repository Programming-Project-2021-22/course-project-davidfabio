package org.davidfabio.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * This class represents a Pickup that is left behind by dead enemies.
 * The Player may collect these Pickups to gain a Points Multiplier.
 */
public class Pickup extends Entity implements Movable {
    /**
     * Indicates the maximum distance at which the Pickup stops moving towards the Player.
     */
    private float stopMovingDistance = 80;
    /**
     * Contains the time passed since the Pickup was created.
     */
    private float lifespanCounter;
    /**
     * Indicates the lifespan for the Pickup, or after how much time it will be destroyed.
     */
    private float lifespan = 3.0f;
    /**
     * Indicates the time after which the Pickup will start blinking, indicating that it will be destroyed soon.
     */
    private float startBlinkingAfter = 2.0f;
    /**
     * Variable used to indicate whether the transparency of the Pickup should increase (true) or if it should
     * decrease (false). This variable is only used while the Pickup is blinking.
     */
    private boolean transparencyWhileBlinkingIncreasing;
    /**
     * Indicates the transparency of the Pickups, which is used while the Pickup is blinking.
     */
    private float transparencyWhileBlinking;

    /**
     * Initializes a Pickup instance. We use this instead of Constructors to avoid memory-reallocation.
     * @param x x-position for the Pickup
     * @param y y-position for the Pickup
     * @param scale scale for the Pickup
     * @param color color for the Pickup
     */
    public void init(float x, float y, float scale, Color color) {
        super.init(x, y, scale, color);
        setShape(new PolygonShape(4, scale));
        lifespanCounter = 0f;
    }

    /**
    * This method handles the actual drawing of the Pickup on the screen.
    * @param polygonSpriteBatch rendering batch for anything that is drawn using Sprites
    * @param shapeRenderer rendering batch for anything that is drawn using Shapes
    */
    public void render(PolygonSpriteBatch polygonSpriteBatch, ShapeRenderer shapeRenderer) {
        if (!getIsActive())
            return;

        if (lifespanCounter > startBlinkingAfter) {
            Color _color = getColorInitial();
            _color.a = transparencyWhileBlinking;
            getShape().render(polygonSpriteBatch, _color);
        }
        else
            super.render(polygonSpriteBatch, shapeRenderer);
    }

    /**
     * This method updates the Pickup's state. It is called periodically in the {@link World}-class.
     * This method requires a world-instance in order to understand where the Player is located and determine if the
     * Pickup should move towards the Player.
     *
     * @param deltaTime time passed since the last update in ms
     * @param world world object reference, needed for side-effects or references of other points in the world.
     */
    public void update(float deltaTime, World world) {
        if (!getIsActive())
            return;

        if (lifespanCounter > startBlinkingAfter) {
            if (transparencyWhileBlinkingIncreasing)
                transparencyWhileBlinking += deltaTime * 4;
            else
                transparencyWhileBlinking -= deltaTime * 4;

            if (transparencyWhileBlinking > 0.6f)
                transparencyWhileBlinkingIncreasing = false;
            else if (transparencyWhileBlinking < 0.1f)
                transparencyWhileBlinkingIncreasing = true;
        }

        lifespanCounter += deltaTime;
        if (lifespanCounter > lifespan)
            setIsActive(false);

        float playerX = world.getPlayer().getX();
        float playerY = world.getPlayer().getY();
        float distanceToPlayer = getDistanceTo(playerX, playerY);

        if (distanceToPlayer < stopMovingDistance) {
            float speed = (stopMovingDistance - distanceToPlayer + world.getPlayer().getMoveSpeed());

            setMoveSpeed(speed);
            moveTowards(playerX, playerY, deltaTime);
        }

        getShape().resetPosition();
        getShape().translatePosition(this);
    }
}
