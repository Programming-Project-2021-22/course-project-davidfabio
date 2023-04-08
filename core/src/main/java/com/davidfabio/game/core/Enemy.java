package com.davidfabio.game.core;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;

public class Enemy extends Entity implements Attackable, Attacker {
    private float initialHealth;
    private float health;

    private float attackPower = 2.0f;  // This is actually the damage an Enemy causes when hitting the player

    private boolean isSpawning;
    private float spawnDuration = 2.0f;
    private float spawnCounter;
    private float transparencyWhileSpawning;
    private boolean transparencyWhileSpawningIncreasing;

    private boolean isInHitState;
    private float hitDuration = 0.03f;
    private float hitCooldown;

    private float fireRate = 2.0f;
    private float fireRateCooldown;
    private float bulletSpeed = 200;
    private float bulletScale = 16;

    public static final int POINT_VALUE = 1;

    public float getHealth() { return this.health; }
    public void setHealth(float newHealth) { this.health = newHealth; }
    public float getInitialHealth() { return this.initialHealth; }
    public void setInitialHealth(float newInitialHealth) { this.initialHealth = newInitialHealth; }
    public float getFireRateCooldown() { return fireRateCooldown; }
    public void setFireRateCooldown(float fireRateCooldown) { this.fireRateCooldown = fireRateCooldown; }
    public float getAttackPower() { return this.attackPower; }
    public void setAttackPower(float newAttackPower) { this.attackPower = newAttackPower; }
    public void setIsInHitState(boolean isInHitState) { this.isInHitState = isInHitState; }
    public boolean getIsInHitState() { return isInHitState; }
    public void setHitCooldown(float hitCooldown) { this.hitCooldown = hitCooldown; }
    public float getHitDuration() { return hitDuration; }
    public float getBulletScale() { return bulletScale; }
    public float getBulletSpeed() { return bulletSpeed; }

    public boolean getIsSpawning() { return isSpawning; }
    public void setIsSpawning(boolean isSpawning) { this.isSpawning = isSpawning; }
    public float getSpawnDuration() { return spawnDuration; }
    public void setSpawnDuration(float spawnDuration) { this.spawnDuration = spawnDuration; }
    public float getSpawnCounter() { return spawnCounter; }
    public void setSpawnCounter(float spawnCounter) { this.spawnCounter = spawnCounter; }
    public float getTransparencyWhileSpawning() { return transparencyWhileSpawning; }


    public void init(float x, float y, float scale, float moveSpeed, float newInitialHealth, Color color) {
        super.init(x, y, scale, color);
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

    public void shootTowardsPlayer(World world) {
        float angle = getAngleTowards(world.getPlayer().getX(), world.getPlayer().getY());
        Bullet bullet = getBullet(world);
        bullet.init(getX(), getY(), bulletScale, bulletSpeed, angle, getColorInitial());
        fireRateCooldown = fireRate;
    }

    public void shoot(World world, float angle) {
        Bullet bullet = getBullet(world);
        bullet.init(getX(), getY(), bulletScale, bulletSpeed, angle, getColorInitial());
        fireRateCooldown = fireRate;
    }

    public BulletEnemy getBullet(World world) {
        for (int i = 0; i < Settings.MAX_ENEMY_BULLETS; i += 1) {
            BulletEnemy bullet = world.getEnemyBullets()[i];
            if (!bullet.getIsActive() && !bullet.getToDestroyNextFrame()) {
                return bullet;
            }
        }
        return null;
    }
}