package org.davidfabio.game;


import com.badlogic.gdx.graphics.Color;
import org.davidfabio.input.Mouse;
import org.davidfabio.utils.Settings;

public class Enemy extends Entity implements Attackable, Attacker {

    public enum Type {
        CHASER,
        TURRET,
        BUBBLE,
        KAMIKAZE
    }

    private Type type;

    private int initialHealth;
    private int health;

    private int attackPower = 1;  // This is actually the damage an Enemy causes when hitting the player

    private boolean isSpawning;
    private float spawnDuration = 2.0f;
    private float spawnCounter;
    private float transparencyWhileSpawning;
    private boolean transparencyWhileSpawningIncreasing;

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

    public boolean getIsSpawning() { return isSpawning; }
    public void setIsSpawning(boolean isSpawning) { this.isSpawning = isSpawning; }
    public float getSpawnDuration() { return spawnDuration; }
    public float getSpawnCounter() { return spawnCounter; }
    public void setSpawnCounter(float spawnCounter) { this.spawnCounter = spawnCounter; }
    public void setType(Type type) { this.type = type; }
    public Type getType() { return type; }


    public void init(float x, float y, float scale, float moveSpeed, int newInitialHealth, Color color) {
        super.init(x, y, scale, color);
        setShape(PolygonShape.getEnemyShape(getType(), scale));

        setMoveSpeed(moveSpeed);
        setColor(new Color(getColorInitial().r, getColorInitial().g, getColorInitial().b, 0.33f));
        if (this.initialHealth == 0)
            this.initialHealth = newInitialHealth;
        this.initializeHealth();

        isInHitState = false;
        hitCooldown = 0;
        isSpawning = true;
        spawnCounter = 0;
        transparencyWhileSpawningIncreasing = true;
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

            if (transparencyWhileSpawning > 0.6f)
                transparencyWhileSpawningIncreasing = false;
            else if (transparencyWhileSpawning < 0.1f)
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
        this.setHealth(0);
        this.setIsActive(false);
        this.playDestructionSound();

        spawnPickup(world);
    }
}
