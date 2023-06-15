package org.davidfabio.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.davidfabio.utils.Settings;

/**
 * Almost anything in our Game inherits from the Entity class. This is the Base for Players, Enemies, Bullets and more.
 */
public class Entity implements Movable {
    /**
     * X and Y Position for the Entity in the World.
     */
    private float x, y;
    /**
     * Scale for the entity, used for drawing the shape.
     */
    private float scale;
    /**
     * Movement speed for the entity, decides how fast an entity may move across the World.
     */
    private float moveSpeed;
    /**
     * Angle in radians, used to determine the direction in which the Entity is headed.
     */
    private float angle;
    /**
     * Whether the Entity is currently active (exists in the World). This is used as we do not instance new Entities
     * during the game, but we simply "reset" the existing Entities.
     */
    private boolean isActive = false;
    /**
     * The shape used to display the Entity in the World. This is always initialized from the child class.
     */
    private PolygonShape shape;

    /**
     * @return X-Position for the Entity
     */
    public float getX() {
        return x;
    }

    /**
     * @param x The new x-position for the Entity.
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * @return Y-Position for the Entity
     */
    public float getY() {
        return y;
    }

    /**
     * @param y The new y-position for the Entity.
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * @return Scale of the Entity.
     */
    public float getScale() {
        return scale;
    }

    /**
     * @param scale the new scale for the Movable object. (Used for drawing)
     */
    public void setScale(float scale) {
        this.scale = scale;
    }

    /**
     * @return Movement-speed for the Entity
     */
    public float getMoveSpeed() {
        return moveSpeed;
    }

    /**
     * @return Direction angle for the Entity (Radians).
     */
    public float getAngle() {
        return angle;
    }

    /**
     * @param angle New direction/angle for the Entity (Radians).
     */
    public void setAngle(float angle) {
        this.angle = angle;
    }

    /**
     * @param moveSpeed New movement-speed for the Entity
     */
    public void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    /**
     * @return true if the Entity is active and should be drawn, false if it's not active.
     */
    public boolean getIsActive() {
        return isActive;
    }

    /**
     * @param isActive New activity-status.
     */
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * @return Shape of the Entity, which defines how it should be drawn.
     */
    public PolygonShape getShape() {
        return shape;
    }

    /**
     * @param shape New shape for the Entity, which defines how it should be drawn.
     */
    public void setShape(PolygonShape shape) {
        this.shape = shape;
    }

    /**
     * Current Color which is used to draw the {@link Entity#shape}. Initialized with {@link Entity#colorInitial}.
     */
    private Color color;
    /**
     * First color which was used to draw the {@link Entity#shape}.
     */
    private Color colorInitial;

    /**
     * @param transparency new transparency which is used to draw the Entity. This is applied to {@link Entity#color}.
     */
    public void setTransparency(float transparency) {
        color.a = transparency;
    }

    /**
     * @return the {@link Entity#color}.
     */
    public Color getColor() {
        return color;
    }

    /**
     * @return a new color object used to draw the Entity. The variable {@link Entity#colorInitial} is used for initialization.
     */
    public Color getColorInitial() {
        return new Color(colorInitial.r, colorInitial.g, colorInitial.b, colorInitial.a);
    }

    /**
     * Sets the current color to the color passed as a parameter. The object values are copied, no reference is stored.
     * @param color new color settings.
     */
    public void setColor(Color color) {
        this.color.r = color.r;
        this.color.g = color.g;
        this.color.b = color.b;
        this.color.a = color.a;
    }

    // the reason for using this method to setup the entity instead of using constructor is the following:
    // we want to create all entities before the game begins to minimize garbage collection as much as possible

    /**
     * This method initializes or resets the Entity object. We use this method instead of creating a new Object to reduce
     * the memory allocation during gameplay.
     *
     * This method stores the color parameter in the {@link Entity#color} and {@link Entity#colorInitial}. This method also
     * activates the Entity (see {@link Entity#isActive}).
     * @param x new X-position
     * @param y new Y-position
     * @param scale new scale for drawing
     * @param color new color for drawing
     */
    public void init(float x, float y, float scale, Color color) {
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.colorInitial = color;
        this.color = getColorInitial();

        this.angle = 0;
        this.isActive = true;
    }

    /**
     * This method actually draws the Entity on the Screen.
     * @param polygonSpriteBatch rendering batch for anything that is drawn using Sprites
     * @param shapeRenderer rendering batch for anything that is drawn using Shapes
     */
    public void render(PolygonSpriteBatch polygonSpriteBatch, ShapeRenderer shapeRenderer) {
        if (!isActive)
            return;

        shape.render(polygonSpriteBatch, color);
    }

    /**
     * Spawns particles at the position of the Entity.
     * @param particleScale defines the size of the single particle
     * @param particleCount defines the number of particles to spawn
     * @param world defines the world in which these particles should be spawned
     * @param type defines the type of particles to spawn
     */
    public void spawnParticles(float particleScale, int particleCount, World world, Particle.Type type) {
        for (int i = 0; i < Settings.MAX_PARTICLES; i += 1) {
            Particle particle = world.getParticles()[i];
            if (!particle.getIsActive()) {
                particle.init(getX(), getY(), particleScale, getColorInitial(), new PolygonShape(particleCount, particleScale), type);
                break;
            }
        }
    }
}
