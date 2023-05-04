package org.davidfabio.game.enemies;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.davidfabio.game.*;
import org.davidfabio.utils.Settings;


/**
 * An enemy can damage the player by colliding with him.
 * When that happens the player loses 1 life and all enemies on screen (including the spawning ones) are destroyed to give the player a brief pause.
 */

public class Enemy extends Entity implements Attackable, Attacker {

    public enum Type {
        CHASER,
        BUBBLE,
        KAMIKAZE,
        TURRET,
        STAR
    }

    private Type type;

    private int initialHealth;
    private int health;

    private int attackPower = 1;  // This is actually the damage an Enemy causes when hitting the player

    private float startSpawningIn;
    private boolean isSpawning;
    private float spawnDuration = 2.0f;
    private float spawnCounter;
    private float transparencyWhileSpawning;
    private boolean transparencyWhileSpawningIncreasing;
    private boolean spawnNextFrame;

    private boolean isInHitState;
    private float hitDuration = 0.03f;
    private float hitCooldown;

    public static final int POINT_VALUE = 1;

    public int getHealth() { return this.health; }
    public void setHealth(int newHealth) { this.health = newHealth; }
    public int getInitialHealth() { return this.initialHealth; }
    public void setInitialHealth(int newInitialHealth) { this.initialHealth = newInitialHealth; }
    public int getAttackPower() { return this.attackPower; }
    public void setIsInHitState(boolean isInHitState) { this.isInHitState = isInHitState; }
    public boolean getIsInHitState() { return isInHitState; }
    public void setHitCooldown(float hitCooldown) { this.hitCooldown = hitCooldown; }
    public float getHitDuration() { return hitDuration; }

    public float getStartSpawningIn() { return startSpawningIn; }
    public void setStartSpawningIn() { this.startSpawningIn = startSpawningIn; }
    public boolean getIsSpawning() { return isSpawning; }
    public void setIsSpawning(boolean isSpawning) { this.isSpawning = isSpawning; }
    public float getSpawnDuration() { return spawnDuration; }
    public float getSpawnCounter() { return spawnCounter; }
    public void setSpawnCounter(float spawnCounter) { this.spawnCounter = spawnCounter; }
    public void setType(Type type) { this.type = type; }
    public Type getType() { return type; }
    public boolean getSpawnNextFrame() { return spawnNextFrame; }
    public void setSpawnNextFrame(boolean spawnNextFrame) { this.spawnNextFrame = spawnNextFrame; }


    public void init(float x, float y, float scale, float moveSpeed, int health, Color color) {
        super.init(x, y, scale, color);
        setShape(PolygonShape.getEnemyShape(getType(), scale));

        setMoveSpeed(moveSpeed);
        setColor(new Color(getColorInitial().r, getColorInitial().g, getColorInitial().b, 0.33f));
        if (initialHealth == 0)
            initialHealth = health;
        //initialHealth = health;
        initializeHealth();

        isInHitState = false;
        hitCooldown = 0;
        isSpawning = true;
        spawnCounter = 0;
        transparencyWhileSpawningIncreasing = true;
        spawnNextFrame = false;
    }

    @Override
    public void render(PolygonSpriteBatch polygonSpriteBatch, ShapeRenderer shapeRenderer) {
        if (!getIsActive())
            return;

        if (getIsSpawning()) {
            getShape().renderOutline(shapeRenderer, getColor());
        } else
            super.render(polygonSpriteBatch, shapeRenderer);
    }

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
