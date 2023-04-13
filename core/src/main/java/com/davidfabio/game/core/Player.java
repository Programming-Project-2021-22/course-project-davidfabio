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

    private float dashSpeed = 800;
    private float dashDuration = 0.25f;
    private float dashAngle;
    private float dashDurationCooldown;
    private boolean isDashing;
    private boolean inDashChooseDirectionState;

    private boolean isInHitState;
    private float hitDuration = 2.5f;
    private float hitCooldown;
    private float transparencyWhileInHitState;
    private boolean transparencyWhileInHitStateIncreasing;

    // indicates shooting direction (purely cosmetic)
    private PolygonShape shapeArrow;
    private float arrowScale = 16;
    private float arrowOffset = 24;

    private Random random;

    public float getHealth() { return this.health; }
    public void setHealth(float newHealth) { this.health = newHealth; }
    public float getInitialHealth() { return this.initialHealth; }
    public void setInitialHealth(float newInitialHealth) { this.initialHealth = newInitialHealth; }
    public void setIsInHitState(boolean isInHitState) { this.isInHitState = isInHitState; }
    public boolean getIsInHitState() { return isInHitState; }

    public void setHitCooldown(float hitCooldown) { this.hitCooldown = hitCooldown; }
    public float getHitDuration() { return hitDuration; }


    public void init(float x, float y, float scale, float moveSpeed, Color color)  {
        super.init(x, y, scale, color);
        this.setMoveSpeed(moveSpeed);
        this.initializeHealth();

        random = new Random();

        transparencyWhileInHitStateIncreasing = true;
        isDashing = false;
        inDashChooseDirectionState = false;

        for (int i = 0; i < Settings.MAX_PLAYER_BULLETS; i += 1)
            bullets[i] = new BulletPlayer();


        shape = new PolygonShape(64, scale);

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


    @Override
    public void render(PolygonSpriteBatch polygonSpriteBatch) {

        // player
        Color color = getColor();
        if (isInHitState)
            color.a = transparencyWhileInHitState;
        shape.render(polygonSpriteBatch, this, color);

        // arrow
        float arrowX = Transform2D.translateX(getX(), getAngle(), arrowOffset);
        float arrowY = Transform2D.translateY(getY(), getAngle(), arrowOffset);
        float arrowAngle = Transform2D.radiansToDegrees(getAngle());
        shapeArrow.render(polygonSpriteBatch, arrowX, arrowY, arrowAngle, Color.LIGHT_GRAY);

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

            if (transparencyWhileInHitStateIncreasing)
                transparencyWhileInHitState += deltaTime * 4;
            else
                transparencyWhileInHitState -= deltaTime * 4;

            if (transparencyWhileInHitState > 0.6f)
                transparencyWhileInHitStateIncreasing = false;
            else if (transparencyWhileInHitState < 0.1f)
                transparencyWhileInHitStateIncreasing = true;
        }



        setAngle((float)Math.atan2(Inputs.Mouse.getY() - getY(), Inputs.Mouse.getX() - getX())); // update direction

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


        // moving normally
        if (!isDashing && !inDashChooseDirectionState) {
            setX(nextX);
            setY(nextY);
            restrictToLevel();

            if (Inputs.Mouse.right.getWasPressed())
                inDashChooseDirectionState = true;
        }

        // start dashing
        else if (Inputs.Mouse.right.getWasReleased()) {
            isDashing = true;
            dashDurationCooldown = dashDuration;
            inDashChooseDirectionState = false;
            dashAngle = getAngle();
        }
        // while dashing
        else if (isDashing) {
            dashDurationCooldown -= deltaTime;

            if (dashDurationCooldown < 0)
                isDashing = false;

            float _speed = dashSpeed * deltaTime;

            float _nextX = Transform2D.translateX(getX(), dashAngle, _speed);
            float _nextY = Transform2D.translateY(getY(), dashAngle, _speed);
            setX(_nextX);
            setY(_nextY);
            restrictToLevel();
        }



        // ---------------- shooting ----------------
        if (fireRateCooldown > 0)
            fireRateCooldown -= deltaTime;

        if (!isDashing && !inDashChooseDirectionState && Inputs.Mouse.left.getIsDown() && fireRateCooldown <= 0)
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


        // ---------------- collision detection against pickups ----------------
        for (Pickup pickup : world.getPickups()) {
            if (!pickup.getIsActive())
                continue;
            if (Collision.circleCircle(getX(), getY(), getScale(), pickup.getX(), pickup.getY(), pickup.getScale())) {
                pickup.setIsActive(false);
            }
        }
    }

    private void shoot() {
        for (int i = 0; i < Settings.MAX_PLAYER_BULLETS; i += 1) {
            if (!bullets[i].getIsActive() && !bullets[i].getToDestroyNextFrame()) {

                // add random spread
                float randomFloat = random.nextFloat() - 0.5f;
                float angleDelta = Transform2D.degreesToRadians(randomFloat * bulletSpreadMax);

                bullets[i].init(getX(), getY(), bulletScale, bulletSpeed, getAngle() + angleDelta, Color.GOLD);
                fireRateCooldown = fireRate;
                Sounds.playShootSfx();
                break;
            }
        }
    }


    /*
    private void dash() {
        isDashing = true;
        dashDurationCooldown = dashDuration;
        inDashChooseDirectionState = false;
        dashAngle = getAngle();

        if (Inputs.down.getIsDown() && Inputs.left.getIsDown())
            dashAngle = 135;
        else if (Inputs.down.getIsDown() && Inputs.right.getIsDown())
            dashAngle = 45;
        else if (Inputs.up.getIsDown() && Inputs.left.getIsDown())
            dashAngle = 225;
        else if (Inputs.up.getIsDown() && Inputs.right.getIsDown())
            dashAngle = 315;
        else if (Inputs.down.getIsDown())
            dashAngle = 90;
        else if (Inputs.up.getIsDown())
            dashAngle = 270;
        else if (Inputs.left.getIsDown())
            dashAngle = 180;
        else if (Inputs.right.getIsDown())
            dashAngle = 0;

        dashAngle = Transform2D.degreesToRadians(dashAngle);
    }
    */




}