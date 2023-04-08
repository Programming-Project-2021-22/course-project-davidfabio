package com.davidfabio.game.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;

import java.util.Random;

public class Player extends Entity implements Attackable {
    private float fireRate = 0.06f;
    private float fireRateCooldown = 0.0f;
    private float bulletSpeed = 800;
    private float bulletScale = 32;
    private float bulletSpreadMax = 8;
    private float initialHealth = 20;
    private float health;
    private BulletPlayer[] bullets = new BulletPlayer[Settings.MAX_PLAYER_BULLETS];

    private boolean isInHitState;
    private float hitDuration = 1.0f;
    private float hitCooldown;

    public float getHealth() { return this.health; }
    public void setHealth(float newHealth) { this.health = newHealth; }
    public float getInitialHealth() { return this.initialHealth; }
    public void setInitialHealth(float newInitialHealth) { this.initialHealth = newInitialHealth; }
    public void setIsInHitState(boolean isInHitState) { this.isInHitState = isInHitState; }
    public boolean getIsInHitState() { return isInHitState; }

    public void setHitCooldown(float hitCooldown) { this.hitCooldown = hitCooldown; }
    public float getHitDuration() { return hitDuration; }

    // indicates shooting direction (purely cosmetic)
    private PolygonShape shapeArrow;
    private float arrowScale = 16;
    private float arrowOffset = 24;

    public void init(float x, float y, float scale, float moveSpeed, Color color)  {
        super.init(x, y, scale, color);
        this.setMoveSpeed(moveSpeed);
        this.initializeHealth();

        for (int i = 0; i < Settings.MAX_PLAYER_BULLETS; i += 1)
            bullets[i] = new BulletPlayer();

        float[] vertices = new float[] {
                0, -0.5f,
                -0.5f, 0,
                0, 0.5f,
                0.5f, 0,
                -0.3675f, -0.3675f,
                -0.3675f, 0.3675f,
                0.3675f, 0.3675f,
                0.3675f, -0.3675f
        };
        short[] triangles = new short[] {
                0, 1, 2,
                2, 3, 0,
                0, 4, 1,
                1, 5, 2,
                2, 6, 3,
                3, 7, 0
        };
        shape = new PolygonShape(vertices, triangles, scale);

        /*
        float[] verticesArrow = new float[] {
                0, 0,
                -0.25f, 0.5f,
                0.25f, 0,
                -0.25f, -0.5f
        };
        short[] trianglesArrow = new short[] {
                0, 1, 2,
                2, 3, 0
        };
        */
        float[] verticesArrow = new float[] {
                0, -0.5f,
                0.5f, 0,
                0, 0.5f
        };
        short[] trianglesArrow = new short[] {
                0, 1, 2
        };


        shapeArrow = new PolygonShape(verticesArrow, trianglesArrow, arrowScale);
    }


    public void render(PolygonSpriteBatch polygonSpriteBatch) {
        super.render(polygonSpriteBatch);

        // arrow
        float arrowX = Transform2D.translateX(getX(), getAngle(), arrowOffset);
        float arrowY = Transform2D.translateY(getY(), getAngle(), arrowOffset);
        float arrowAngle = Transform2D.radiansToDegrees(getAngle());
        shapeArrow.render(polygonSpriteBatch, arrowX, arrowY, arrowAngle, Color.WHITE);

        // bullets
        for (int i = 0; i < Settings.MAX_PLAYER_BULLETS; i += 1)
            bullets[i].render(polygonSpriteBatch);
    }



    public void update(float deltaTime, World world) {
        if (isInHitState) {
            hitCooldown -= deltaTime;

            if (hitCooldown < 0) {
                setColor(getColorInitial());
                isInHitState = false;
            }
        }


        // update direction
        setAngle((float)Math.atan2(Inputs.Mouse.getY() - getY(), Inputs.Mouse.getX() - getX()));

        // ---------------- movement ----------------
        float speed = getMoveSpeed() * deltaTime;

        // normalize diagonal movement
        if ((Inputs.up.getIsDown() || Inputs.down.getIsDown()) && (Inputs.left.getIsDown() || Inputs.right.getIsDown()))
            speed *= 0.707106f;

        float nextX = getX();
        float nextY = getY();
        if (Inputs.up.getIsDown())    nextY -= speed;
        if (Inputs.down.getIsDown())  nextY += speed;
        if (Inputs.left.getIsDown())  nextX -= speed;
        if (Inputs.right.getIsDown()) nextX += speed;

        // prevent player from going offscreen
        nextX = Math.max(nextX, getScale() / 2);
        nextX = Math.min(nextX, Settings.windowWidth - getScale() / 2);
        nextY = Math.max(nextY, getScale() / 2);
        nextY = Math.min(nextY, Settings.windowHeight - getScale() / 2);

        setX(nextX);
        setY(nextY);


        // ---------------- shooting ----------------
        if (fireRateCooldown > 0)
            fireRateCooldown -= deltaTime;

        if (Inputs.Mouse.left.getIsDown() && fireRateCooldown <= 0)
            shoot();

        for (int i = 0; i < Settings.MAX_PLAYER_BULLETS; i += 1) {
            bullets[i].update(deltaTime,world);
        }


        // ---------------- collision detection against enemies ----------------
        for (Enemy enemy : world.getEnemies()) {
            if (!enemy.getIsActive())
                continue;
            if (enemy.getIsSpawning())
                continue;
            if (Collision.circleCircle(getX(), getY(), getScale(), enemy.getX(), enemy.getY(), enemy.getScale())) {
                // Enemy collided with Player
                enemy.attack(this, world);
                enemy.destroy(world);
            }
        }
    }

    void shoot() {
        Random random = new Random();
        for (int i = 0; i < Settings.MAX_PLAYER_BULLETS; i += 1) {
            if (!bullets[i].getIsActive() && !bullets[i].getToDestroyNextFrame()) {

                // add random spread to bullet direction
                float randomFloat = random.nextFloat() - 0.5f;
                float angleDelta = Transform2D.degreesToRadians(randomFloat * bulletSpreadMax);

                bullets[i].init(getX(), getY(), bulletScale, bulletSpeed, getAngle() + angleDelta, Color.GOLD);
                fireRateCooldown = fireRate;
                Sounds.playShootSfx();
                break;
            }
        }
    }


}