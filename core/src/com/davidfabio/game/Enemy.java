package com.davidfabio.game;


public class Enemy extends Entity {

    private float healthInitial;
    private float health;

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
    private static float collisionDamage = 2f;
    private static final int POINT_VALUE = 1;

    public float getHealth() { return health; }
    public boolean getIsSpawning() { return isSpawning; }
    public float getFireRateCooldown() { return fireRateCooldown; }
    public void setFireRateCooldown(float fireRateCooldown) { this.fireRateCooldown = fireRateCooldown; }
    public float getCollisionDamage() {
        return collisionDamage;
    }


    public void init(float x, float y, float radius, float direction, Polarity polarity, float moveSpeed, float healthInitial) {
        super.init(x, y, radius, polarity);
        setMoveSpeed(moveSpeed);
        health = this.healthInitial = healthInitial;
        isInHitState = false;
        hitCooldown = 0;
        isSpawning = true;
        spawnCounter = 0;

        if (getPolarity().getColor() == Settings.FIRST_COLOR)
            setTexture(GameScreen.getTextureRedTransparent());
        else
            setTexture(GameScreen.getTextureBlueTransparent());
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
        if (isInHitState)
            setTexture(GameScreen.getTextureWhite());
        else if (getPolarity().getColor() == Settings.FIRST_COLOR)
            setTexture(GameScreen.getTextureRed());
        else
            setTexture(GameScreen.getTextureBlue());
    }

    void shootTowardsPlayer() {
        float angle = getAngleTowards(GameScreen.player.getX(), GameScreen.player.getY());
        getBullet().init(getX(), getY(), bulletScale, getPolarity(), bulletSpeed, angle);
        fireRateCooldown = fireRate;
    }

    void shoot(float angle) {
        getBullet().init(getX(), getY(), bulletScale, getPolarity(), bulletSpeed, angle);
        fireRateCooldown = fireRate;
    }


    public BulletEnemy getBullet() {
        for (int i = 0; i < GameScreen.MAX_ENEMY_BULLETS; i += 1) {
            BulletEnemy bullet = GameScreen.enemyBullets[i];
            if (!bullet.getIsActive() && !bullet.getToDestroyNextFrame()) {
                return bullet;
            }
        }
        return null;
    }



    public void hit(float attackPower) {
        Sounds.playHitSfx();
        isInHitState = true;
        hitCooldown = hitDuration;
        health -= attackPower;

        if (health <= 0) {
            GameScreen.getScore().setPoints(GameScreen.getScore().getPoints() + Enemy.POINT_VALUE);
            setIsActive(false);
            Sounds.playExplosionSfx();
        }
    }

}