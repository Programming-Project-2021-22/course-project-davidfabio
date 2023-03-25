package com.davidfabio.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;



public class Enemy extends Entity {

    private float healthInitial;
    private float health;

    private boolean inHitState;
    private float hitDuration = 0.03f;
    private float hitCooldown;

    private float fireRate = 0.1f;
    private float fireRateCooldown = 0.0f;
    private float bulletSpeed = 200;

    private boolean isSpawning;
    private float spawnDuration = 1.5f;
    private float spawnCounter;

    public float getHealth() { return health; }
    public boolean getIsSpawning() { return isSpawning; }


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
        if (!getActive())
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


        // movement
        //moveTowards(Game.player.getX(), Game.player.getY(), deltaTime);


        // ---------------- shooting ----------------
        if (fireRateCooldown > 0)
            fireRateCooldown -= deltaTime;

        if (fireRateCooldown <= 0)
            shoot();
    }


    @Override public void render(ShapeRenderer shape, Color _color) {
        if (!getActive())
            return;

        float _x = Math.round(getX());
        float _y = Settings.windowHeight - Math.round(getY());


        Color outlineColor = getPolarity().getColor();

        if (isSpawning) {
            shape.begin(ShapeRenderer.ShapeType.Line);
            shape.setColor(outlineColor);
            shape.arc(_x, _y, getRadius(), 0, (spawnCounter * 360) / spawnDuration);
            shape.end();
            return;
        }

        if (inHitState)
            _color = new Color(0.5f, 0.5f, 0.5f, 1);
        super.render(shape, _color);


        // draw outline
        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(outlineColor);
        shape.circle(_x, _y, getRadius());
        shape.end();
    }


    void shoot() {
        for (int i = 0; i < Game.MAX_ENEMY_BULLETS; i += 1) {
            EnemyBullet bullet = Game.enemyBullets[i];
            float dir = getAngleTowards(Game.player.getX(), Game.player.getY());

            if (!bullet.getActive() && !bullet.getToDestroyNextFrame()) {
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
            setActive(false);
            Sounds.playExplosionSfx();
        }
    }

}