package com.davidfabio.game;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;



public class Player extends Entity {

    private float fireRate = 0.04f;
    private float fireRateCooldown = 0.0f;

    private final int MAX_BULLETS = 16;
    private PlayerBullet[] bullets = new PlayerBullet[MAX_BULLETS];


    public void init(float x, float y, float radius, float direction, Polarity polarity, float moveSpeed)  {
        super.init(x, y, radius, direction, polarity);
        this.setMoveSpeed(moveSpeed);

        for (int i = 0; i < MAX_BULLETS; i += 1)
            bullets[i] = new PlayerBullet();
    }

    public void render(ShapeRenderer shape) {
        super.render(shape);

        for (int i = 0; i < MAX_BULLETS; i += 1) {
            bullets[i].render(shape);
        }
    }


    public void update(float deltaTime) {
        // update direction
        setDirection((float)Math.atan2(Inputs.Mouse.y - getY(), Inputs.Mouse.x - getX()));


        // ---------------- movement ----------------
        float speed = getMoveSpeed() * deltaTime;

        // normalize diagonal movement
        if ((Inputs.up.isDown || Inputs.down.isDown) && (Inputs.left.isDown || Inputs.right.isDown))
            speed *= 0.707106f;

        float nextX = getX();
        float nextY = getY();
        if (Inputs.up.isDown)    nextY -= speed;
        if (Inputs.down.isDown)  nextY += speed;
        if (Inputs.left.isDown ) nextX -= speed;
        if (Inputs.right.isDown) nextX += speed;

        // prevent player from going offscreen
        nextX = Math.max(nextX, getRadius());
        nextX = Math.min(nextX, Game.gameWidth - getRadius());
        nextY = Math.max(nextY, getRadius());
        nextY = Math.min(nextY, Game.gameHeight - getRadius());

        setX(nextX);
        setY(nextY);


        // switch polarity
        if (Inputs.space.wasPressed)
            switchPolarity();




        // ---------------- shooting ----------------
        if (fireRateCooldown > 0)
            fireRateCooldown -= deltaTime;

        if (Inputs.Mouse.left.isDown && fireRateCooldown <= 0) {
            // get new bullet from array
            for (int i = 0; i < MAX_BULLETS; i += 1) {
                if (!bullets[i].getActive() && !bullets[i].getToDestroyNextFrame()) {
                    bullets[i].init(getX(), getY(), 8, getDirection(), getPolarity(), 1600);
                    fireRateCooldown = fireRate;
                    break;
                }
            }
        }

        for (int i = 0; i < MAX_BULLETS; i += 1) {
            bullets[i].update(deltaTime);
        }
    }


    void switchPolarity() {
        if (getPolarity() == Entity.Polarity.RED)
            setPolarity(Polarity.BLUE);
        else
            setPolarity(Polarity.RED);
    }

}