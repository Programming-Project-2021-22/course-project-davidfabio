package com.davidfabio.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


public class Player extends Entity {
    private float fireRate = 0.06f;
    private float fireRateCooldown = 0.0f;
    private float bulletSpeed = 800;
    private float bulletSpreadMax = 8;
    private float initialHealth = 20;
    private float health;
    private final int MAX_BULLETS = 64;
    private BulletPlayer[] bullets = new BulletPlayer[MAX_BULLETS];

    public void init(float x, float y, float scale, Polarity polarity, float moveSpeed)  {
        super.init(x, y, scale, polarity);
        this.setMoveSpeed(moveSpeed);
        this.health = this.initialHealth;

        for (int i = 0; i < MAX_BULLETS; i += 1)
            bullets[i] = new BulletPlayer();
    }

    @Override public void render(ShapeRenderer shape, Color _color) {

        super.render(shape, getPolarity().getColor()); // draw player

        // draw inner white circle
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(Color.WHITE);
        shape.circle(getX(), getY(), getScale() - getScale() / 3);
        shape.end();


        // draw dashed line from player to mouse position
        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(_color);

        float segmentLength = 12;
        float distance = getDistanceTo(Inputs.Mouse.getX(), Inputs.Mouse.getY());
        int segmentCount = (int)(distance / segmentLength / 2);
        float dirTowardsMouse = getAngleTowards(Inputs.Mouse.getX(), Inputs.Mouse.getY());
        float deltaX = (float)Math.cos(dirTowardsMouse) * segmentLength;
        float deltaY = (float)Math.sin(dirTowardsMouse) * segmentLength;
        float startY = getY() + (float)Math.sin(getDirection()) * (getScale() + segmentLength);
        float startX = getX() + (float)Math.cos(getDirection()) * (getScale() + segmentLength);
        float endX = startX + deltaX;
        float endY = startY + deltaY;

        for (int i = 0; i < segmentCount - 1; i += 1) {
            //shape.line(startX, Settings.windowHeight - startY, endX, Settings.windowHeight - endY);
            shape.line(startX, startY, endX, endY);
            startX = endX + deltaX;
            startY = endY + deltaY;
            endX = startX + deltaX;
            endY = startY + deltaY;
        }
        shape.end();

        // draw bullets
        for (int i = 0; i < MAX_BULLETS; i += 1) {
            bullets[i].render(shape, getPolarity().getColor());
        }
    }


    public void update(float deltaTime) {
        // update direction
        setDirection((float)Math.atan2(Inputs.Mouse.getY() - getY(), Inputs.Mouse.getX() - getX()));

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
        nextX = Math.max(nextX, getScale());
        nextX = Math.min(nextX, Settings.windowWidth - getScale());
        nextY = Math.max(nextY, getScale());
        nextY = Math.min(nextY, Settings.windowHeight - getScale());

        setX(nextX);
        setY(nextY);


        // switch polarity
        if (Inputs.space.getWasPressed())
            switchPolarity();




        // ---------------- shooting ----------------
        if (fireRateCooldown > 0)
            fireRateCooldown -= deltaTime;

        if (Inputs.Mouse.left.getIsDown() && fireRateCooldown <= 0)
            shoot();

        for (int i = 0; i < MAX_BULLETS; i += 1) {
            bullets[i].update(deltaTime);
        }


        // ---------------- collision detection against enemies ----------------
        for (Enemy enemy : GameScreen.enemies) {
            if (!enemy.getIsActive())
                continue;
            if (enemy.getIsSpawning())
                continue;;
            if (Collision.circleCircle(getX(), getY(), getScale(), enemy.getX(), enemy.getY(), enemy.getScale())) {
                enemy.hit(enemy.getHealth());
                this.health -= enemy.getCollisionDamage();
            }
        }

    }



    void shoot() {
        for (int i = 0; i < MAX_BULLETS; i += 1) {
            if (!bullets[i].getIsActive() && !bullets[i].getToDestroyNextFrame()) {

                // add random spread to bullet direction
                float random = GameScreen.getRandom().nextFloat() - 0.5f;
                float angleDelta = degreesToRadians(random * bulletSpreadMax);

                bullets[i].init(getX(), getY(), 8, getPolarity(), bulletSpeed, getDirection() + angleDelta);
                fireRateCooldown = fireRate;
                Sounds.playShootSfx();
                break;
            }
        }
    }

    void switchPolarity() {
        getPolarity().togglePolarity();
    }

    public float getHealth() {
        return this.health;
    }

    public float getInitialHealth() {
        return this.initialHealth;
    }
}