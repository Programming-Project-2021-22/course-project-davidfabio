package com.davidfabio.game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.lang.reflect.GenericArrayType;


public class Player extends Entity {

    private float fireRate = 0.06f;
    private float fireRateCooldown = 0.0f;

    private final int MAX_BULLETS = 64;
    private PlayerBullet[] bullets = new PlayerBullet[MAX_BULLETS];


    public void init(float x, float y, float radius, float direction, Polarity polarity, float moveSpeed)  {
        super.init(x, y, radius, direction, polarity);
        this.setMoveSpeed(moveSpeed);

        for (int i = 0; i < MAX_BULLETS; i += 1)
            bullets[i] = new PlayerBullet();
    }

    @Override public void render(ShapeRenderer shape, Color _color) {
        super.render(shape, getColor());

        for (int i = 0; i < MAX_BULLETS; i += 1) {
            bullets[i].render(shape, getColor());
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
        if (Inputs.left.getIsDown() ) nextX -= speed;
        if (Inputs.right.getIsDown()) nextX += speed;

        // prevent player from going offscreen
        nextX = Math.max(nextX, getRadius());
        nextX = Math.min(nextX, Game.gameWidth - getRadius());
        nextY = Math.max(nextY, getRadius());
        nextY = Math.min(nextY, Game.gameHeight - getRadius());

        setX(nextX);
        setY(nextY);


        // switch polarity
        if (Inputs.space.getWasPressed())
            switchPolarity();




        // ---------------- shooting ----------------
        if (fireRateCooldown > 0)
            fireRateCooldown -= deltaTime;

        if (Inputs.Mouse.left.getIsDown() && fireRateCooldown <= 0) {
            // get new bullet from array
            for (int i = 0; i < MAX_BULLETS; i += 1) {
                if (!bullets[i].getActive() && !bullets[i].getToDestroyNextFrame()) {
                    bullets[i].init(getX(), getY(), 8, getDirection(), getPolarity(), 1600);
                    fireRateCooldown = fireRate;
                    Game.sfxShoot.play(Game.sfxVolume);
                    break;
                }
            }
        }

        for (int i = 0; i < MAX_BULLETS; i += 1) {
            bullets[i].update(deltaTime);
        }


        // ---------------- collision detection against enemies ----------------
        for (int i = 0; i < Game.MAX_ENEMIES; i += 1) {
            if (!Game.enemies[i].getActive())
                continue;
            if (Game.enemies[i].getIsSpawning())
                continue;;
            if (Collision.circleCircle(getX(), getY(), getRadius(), Game.enemies[i].getX(), Game.enemies[i].getY(), Game.enemies[i].getRadius()))
                Game.enemies[i].hit(Game.enemies[i].getHealth());
        }


    }


    void switchPolarity() {
        if (getPolarity() == Entity.Polarity.RED)
            setPolarity(Polarity.BLUE);
        else
            setPolarity(Polarity.RED);
    }

}