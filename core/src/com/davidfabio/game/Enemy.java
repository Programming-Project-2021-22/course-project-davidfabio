package com.davidfabio.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;



public class Enemy extends Entity {

    private float healthInitial;
    private float health;

    private boolean inHitState;
    private float hitDuration = 0.03f;
    private float hitCooldown;

    private float fireRate = 0.2f;
    private float fireRateCooldown = 0.0f;
    private float bulletSpeed = 150;

    private boolean isSpawning;
    private float spawnDuration = 1.5f;
    private float spawnCounter;
    private static float collisionDamage = 2f;

    public float getHealth() { return health; }
    public boolean getIsSpawning() { return isSpawning; }
    public float getSpawnDuration() { return spawnDuration; }
    public float getSpawnCounter() { return spawnCounter; }
    public float getFireRateCooldown() { return fireRateCooldown; }
    public void setFireRateCooldown(float fireRateCooldown) { this.fireRateCooldown = fireRateCooldown; }
    public float getCollisionDamage() {
        return collisionDamage;
    }


    public void init(float x, float y, float radius, float direction, Polarity polarity, float moveSpeed, float healthInitial) {
        super.init(x, y, radius, direction, polarity);
        setMoveSpeed(moveSpeed);
        health = this.healthInitial = healthInitial;
        inHitState = false;
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

        if (inHitState) {
            hitCooldown -= deltaTime;

            if (hitCooldown < 0)
                inHitState = false;
        }
    }


    @Override public void render(ShapeRenderer shape, Color _color) {
        if (!getIsActive())
            return;

        if (isSpawning) {
            shape.begin(ShapeRenderer.ShapeType.Line);
            shape.setColor(_color);
            shape.arc(getX(), getY(), getRadius(), 0, (spawnCounter * 360) / spawnDuration);
            shape.end();
            return;
        }
        if (inHitState)
            _color = new Color(0.5f, 0.5f, 0.5f, 1);

        super.render(shape, _color);
    }


    void shoot() {
        for (int i = 0; i < Game.MAX_ENEMY_BULLETS; i += 1) {
            BulletEnemy bullet = Game.enemyBullets[i];
            float dir = getAngleTowards(Game.player.getX(), Game.player.getY());

            if (!bullet.getIsActive() && !bullet.getToDestroyNextFrame()) {
                bullet.init(getX(), getY(), 8, dir, getPolarity(), bulletSpeed);
                fireRateCooldown = fireRate;
                //Game.sfxShoot.play(Game.sfxVolume);
                break;
            }
        }
    }



    public void hit(float attackPower) {
        Sounds.playHitSfx();
        inHitState = true;
        hitCooldown = hitDuration;
        health -= attackPower;

        if (health <= 0) {
            setIsActive(false);
            Sounds.playExplosionSfx();
        }
    }

}