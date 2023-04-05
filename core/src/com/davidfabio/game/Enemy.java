package com.davidfabio.game;


public class Enemy extends Entity implements Attackable, Attacker {
    private float initialHealth;
    private float health;

    private float attackPower = 2.0f;  // This is actually the damage an Enemy causes when hitting the player

    private boolean isInHitState;
    private float hitDuration = 0.03f;
    private float hitCooldown;

    private float fireRate = 2.0f;
    private float fireRateCooldown;
    private float bulletSpeed = 200;
    private float bulletScale = 16;

    private boolean isSpawning;
    private float spawnDuration = 1.5f;
    private float spawnCounter;

    public static final int POINT_VALUE = 1;

    public float getHealth() { return this.health; }
    public void setHealth(float newHealth) { this.health = newHealth; }
    public float getInitialHealth() { return this.initialHealth; }
    public void setInitialHealth(float newInitialHealth) { this.initialHealth = newInitialHealth; }

    public boolean getIsSpawning() { return isSpawning; }
    public float getFireRateCooldown() { return fireRateCooldown; }
    public void setFireRateCooldown(float fireRateCooldown) { this.fireRateCooldown = fireRateCooldown; }
    public float getAttackPower() { return this.attackPower; }
    public void setAttackPower(float newAttackPower) { this.attackPower = newAttackPower; }

    public void init(float x, float y, float radius, float direction, Polarity polarity, float moveSpeed, float newInitialHealth) {
        super.init(x, y, radius, polarity);
        setMoveSpeed(moveSpeed);
        if (this.initialHealth == 0)
            this.initialHealth = newInitialHealth;
        this.initializeHealth();

        isInHitState = false;
        hitCooldown = 0;
        isSpawning = true;
        spawnCounter = 0;

        if (getPolarity().getColor() == Settings.FIRST_COLOR)
            setTexture(GameScreen.getTextureRedTransparent());
        else
            setTexture(GameScreen.getTextureBlueTransparent());
    }

    public void update(float deltaTime, World world) {
        if (!getIsActive())
            return;

        if (isSpawning) {
            spawnCounter += deltaTime;

            if (spawnCounter > spawnDuration)
                isSpawning = false;
            return;
        }

        if (isInHitState) {
            hitCooldown -= deltaTime;

            if (hitCooldown < 0)
                isInHitState = false;
        }

        // set texture
        if (isInHitState)
            setTexture(GameScreen.getTextureWhite());
        else if (getPolarity().getColor() == Settings.FIRST_COLOR)
            setTexture(GameScreen.getTextureRed());
        else
            setTexture(GameScreen.getTextureBlue());
    }

    public void shootTowardsPlayer(World world) {
        float angle = getAngleTowards(world.getPlayer().getX(), world.getPlayer().getY());
        getBullet(world.getEnemyBullets()).init(getX(), getY(), bulletScale, getPolarity(), bulletSpeed, angle);
        fireRateCooldown = fireRate;
    }

    public void shoot(World world, float angle) {
        getBullet(world.getEnemyBullets()).init(getX(), getY(), bulletScale, getPolarity(), bulletSpeed, angle);
        fireRateCooldown = fireRate;
    }

    private BulletEnemy getBullet(BulletEnemy[] enemyBullets) {
        for (int i = 0; i < Settings.MAX_ENEMY_BULLETS; i += 1) {
            BulletEnemy bullet = enemyBullets[i];
            if (!bullet.getIsActive() && !bullet.getToDestroyNextFrame()) {
                return bullet;
            }
        }
        return null;
    }
}