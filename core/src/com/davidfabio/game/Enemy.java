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

        setPolarity(polarity); // we call this again to set the color
    }


    public void update(float deltaTime) {
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
        if (isSpawning) {
            currentTexture = GameScreen.getTextureYellow();
        }
        else if (isInHitState)
            currentTexture = GameScreen.getTextureWhite();
        else if (getPolarity().getColor() == Settings.FIRST_COLOR)
            currentTexture = GameScreen.getTextureRed();
        else
            currentTexture = GameScreen.getTextureBlue();
    }

    void shoot() {
        for (int i = 0; i < GameScreen.MAX_ENEMY_BULLETS; i += 1) {
            BulletEnemy bullet = GameScreen.enemyBullets[i];
            float dir = getAngleTowards(GameScreen.player.getX(), GameScreen.player.getY());

            if (!bullet.getIsActive() && !bullet.getToDestroyNextFrame()) {
                bullet.init(getX(), getY(), bulletScale, getPolarity(), bulletSpeed, dir);
                fireRateCooldown = fireRate;
                break;
            }
        }
    }
}