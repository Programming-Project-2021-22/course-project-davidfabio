package org.davidfabio.game.enemies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.davidfabio.game.*;
import org.davidfabio.utils.Settings;

/**
 * The Enemy class provides all the Base functionality for any Type of Enemy.
 * An enemy can damage the player by colliding with them, shooting them or by exploding.
 * When that happens the player loses health.
 */
public class Enemy extends Entity implements Attackable, Attacker {
    /**
     * This Enum contains all the different Enemy types that the Game implements.
     */
    public enum Type {
        CHASER,
        BUBBLE,
        KAMIKAZE,
        TURRET,
        STAR
    }

    /**
     * Identifies the Type of this Enemy-Entity.
     */
    private Type type;

    /**
     * Starting Health
     */
    private int initialHealth;
    /**
     * Current Health. Once zero, the Entity dies.
     */
    private int health;
    /**
     * Damage caused by this Enemy on collision. (When the Enemy shoots, the Enemy {@link Bullet} actually collides with
     * the Player, and thus the Bullet causes the Damage.)
     */
    private int attackPower = 1;

    /**
     * True if the Enemy is currently spawning, so on the map, but not yet active.
     */
    private boolean isSpawning;
    /**
     * Default duration for a spawn. This is the time between spawn and activation.
     */
    private float spawnDuration = 2.0f;
    /**
     * Current timer for a spawn. Once this hits {@link Enemy#spawnDuration}, the Enemy will be activated.
     */
    private float spawnCounter;
    /**
     * Indicates the transparency of the Enemy Shape (rendering) while spawning.
     */
    private float transparencyWhileSpawning;
    /**
     * Indicates whether {@link Enemy#transparencyWhileSpawning} is currently increasing (true) or not (false).
     * This is used for a Pulsation effect while spawning.
     */
    private boolean transparencyWhileSpawningIncreasing;
    /**
     * Indicates whether this Enemy should be spawned in the next Frame.
     */
    private boolean spawnNextFrame;

    /**
     * Indicates that this Enemy was recently hit and is now in a Hit state for the duration of {@link Enemy#hitDuration}.
     */
    private boolean isInHitState;
    /**
     * Indicates the duration in which an Enemy is "invincible" after being hit.
     */
    private float hitDuration = 0.03f;
    /**
     * Used as a timer to keep track of the current hit duration.
     */
    private float hitCooldown;

    /**
     * The amount of Points that will be added to the {@link Score} once the Player defeats this Enemy.
     */
    public static final int POINT_VALUE = 1;

    /**
     * @return the current Enemy health
     */
    public int getHealth() {
        return health;
    }

    /**
     * @param newHealth the new health for the Enemy
     */
    public void setHealth(int newHealth) {
        health = newHealth;
    }

    /**
     * @return the initial Enemy health
     */
    public int getInitialHealth() {
        return initialHealth;
    }

    /**
     * @param newInitialHealth the new starting health for the Enemy
     */
    public void setInitialHealth(int newInitialHealth) {
        initialHealth = newInitialHealth;
    }

    /**
     * @return the Enemies {@link Enemy#attackPower}.
     */
    public int getAttackPower() {
        return attackPower;
    }

    /**
     * @param isInHitState set to true, to enable invincibility, false otherwise
     */
    public void setIsInHitState(boolean isInHitState) {
        this.isInHitState = isInHitState;
    }

    /**
     * @return returns true if invincible, false otherwise
     */
    public boolean getIsInHitState() {
        return isInHitState;
    }

    /**
     * @param hitCooldown sets the current hit-state (invincibility) duration
     */
    public void setHitCooldown(float hitCooldown) {
        this.hitCooldown = hitCooldown;
    }

    /**
     * @return the current hit-state (invincibility) duration
     */
    public float getHitDuration() {
        return hitDuration;
    }

    /**
     * @return true if the Enemy is on the map, but not yet active (spawning)
     */
    public boolean getIsSpawning() {
        return isSpawning;
    }

    /**
     * @param isSpawning set to true, to indicate that the Enemy is on the map, but not yet active (spawning)
     */
    public void setIsSpawning(boolean isSpawning) {
        this.isSpawning = isSpawning;
    }

    /**
     * @return initial spawning duration, time between spawn and being active
     */
    public float getSpawnDuration() {
        return spawnDuration;
    }

    /**
     * @return remaining spawn duration, time between spawn and being active
     */
    public float getSpawnCounter() {
        return spawnCounter;
    }

    /**
     * @param spawnCounter new spawn duration, time between spawn and being active
     */
    public void setSpawnCounter(float spawnCounter) {
        this.spawnCounter = spawnCounter;
    }

    /**
     * @param type new Enemy Type, defines behaviour
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * @return Enemy Type, defines behaviour
     */
    public Type getType() {
        return type;
    }

    /**
     * @return true if the Enemy should be spawned the next frame
     */
    public boolean getSpawnNextFrame() {
        return spawnNextFrame;
    }

    /**
     * @param spawnNextFrame set to true, if the Enemy should be spawned the next frame
     */
    public void setSpawnNextFrame(boolean spawnNextFrame) {
        this.spawnNextFrame = spawnNextFrame;
    }

    /**
     * This method initializes or resets the Enemy object. We use this method instead of creating a new Object to reduce
     * the memory allocation during gameplay.
     *
     * @param x x-position for the Enemy
     * @param y y-position for the Enemy
     * @param scale size for the Enemy (or the shape)
     * @param moveSpeed Enemy velocity
     * @param health initial health, if 0 {@link Enemy#initialHealth} is used
     * @param color color for the display of the Enemy
     */
    public void init(float x, float y, float scale, float moveSpeed, int health, Color color) {
        super.init(x, y, scale, color);
        setShape(PolygonShape.getEnemyShape(getType(), scale));

        setMoveSpeed(moveSpeed);
        setColor(new Color(getColorInitial().r, getColorInitial().g, getColorInitial().b, 0.33f));
        if (initialHealth == 0)
            initialHealth = health;
        initializeHealth();

        isInHitState = false;
        hitCooldown = 0;
        isSpawning = true;
        spawnCounter = 0;
        transparencyWhileSpawningIncreasing = true;
        spawnNextFrame = false;
    }

    /**
     * This method actually draws the Enemy on the Screen.
     * @param polygonSpriteBatch rendering batch for anything that is drawn using Sprites
     * @param shapeRenderer rendering batch for anything that is drawn using Shapes
     */
    @Override
    public void render(PolygonSpriteBatch polygonSpriteBatch, ShapeRenderer shapeRenderer) {
        if (!getIsActive())
            return;

        if (getIsSpawning()) {
            getShape().renderOutline(shapeRenderer, getColor());
        } else
            super.render(polygonSpriteBatch, shapeRenderer);
    }

    /**
     * This method updates the Enemy state periodically.
     * This method simply updates the {@link Enemy#hitCooldown} and {@link Enemy#spawnCounter} timers and anything related to those.
     *
     * @param deltaTime Delta by which the game loop updated
     * @param world World object reference used for side-effects
     */
    public void update(float deltaTime, World world) {
        if (!getIsActive())
            return;

        getShape().resetPosition();
        getShape().rotate(getAngle());
        getShape().translatePosition(this);

        if (isInHitState) {
            hitCooldown -= deltaTime;

            if (hitCooldown < 0) {
                setColor(getColorInitial());
                isInHitState = false;
            }
        }

        if (isSpawning) {
            setTransparency(transparencyWhileSpawning);
            setSpawnCounter(getSpawnCounter() + deltaTime);

            if (getSpawnCounter() > getSpawnDuration()) {
                setColor(getColorInitial());
                setIsSpawning(false);
            }

            if (transparencyWhileSpawningIncreasing)
                transparencyWhileSpawning += deltaTime;
            else
                transparencyWhileSpawning -= deltaTime;

            if (transparencyWhileSpawning >= 0.75f)
                transparencyWhileSpawningIncreasing = false;
            else if (transparencyWhileSpawning < 0.25f)
                transparencyWhileSpawningIncreasing = true;
        }
    }

    /**
     * Spawns a {@link Pickup} at the Position of the Enemy for the Player to collect.
     * @param world World object where the Pickup will be activated.
     */
    public void spawnPickup(World world) {
        Pickup pickup = null;
        for (int i = 0; i < Settings.MAX_PICKUPS; i += 1) {
            if (!world.getPickups()[i].getIsActive()) {
                pickup = world.getPickups()[i];
                break;
            }
        }

        if (pickup != null) {
            pickup.init(getX(), getY(), 8, Color.GREEN);
        }
    }

    /**
     * Destroys the Enemy. This spawns a Pickup ({@link Enemy#spawnPickup(World)}) and spawns Particles.
     * @param world World object reference, used for side-effects
     */
    @Override
    public void destroy(World world) {
        if (Collision.pointIsInLevel(getX(), getY(), world))
            spawnPickup(world);

        spawnParticles(getScale() / 4, (int)getScale() / 2, world, Particle.Type.CIRCLE);

        setHealth(0);
        setIsActive(false);
        setSpawnNextFrame(false);
        playDestructionSound();
    }
}
