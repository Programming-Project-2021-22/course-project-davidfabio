package com.davidfabio.game;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;

import java.util.Arrays;


public class Player extends Entity {
    private float fireRate = 0.06f;
    private float fireRateCooldown = 0.0f;
    private float bulletSpeed = 800;
    private float bulletScale = 32;
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
    }

    public void render(PolygonSpriteBatch polygonSpriteBatch) {
        shape.render(polygonSpriteBatch, this);

        float[] vertices = shape.getVerticesInitial();
        for (int i = 0; i < vertices.length; i += 1)
            vertices[i] *= 0.75f;
        setTexture(GameScreen.getTextureWhite());
        shape.render(polygonSpriteBatch, this, vertices);

        for (int i = 0; i < MAX_BULLETS; i += 1)
            bullets[i].render(polygonSpriteBatch);
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

        if (getPolarity().getColor() == Settings.FIRST_COLOR)
            setTexture(GameScreen.getTextureRed());
        else
            setTexture(GameScreen.getTextureBlue());




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

                bullets[i].init(getX(), getY(), bulletScale, getPolarity(), bulletSpeed, getDirection() + angleDelta);
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