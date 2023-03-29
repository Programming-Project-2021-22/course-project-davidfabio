package com.davidfabio.game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


public class Player extends Entity {

    private float fireRate = 0.06f;
    private float fireRateCooldown = 0.0f;
    private float bulletSpeed = 1600;

    private final int MAX_BULLETS = 64;
    private BulletPlayer[] bullets = new BulletPlayer[MAX_BULLETS];


    public void init(float x, float y, float radius, float direction, Polarity polarity, float moveSpeed)  {
        super.init(x, y, radius, direction, polarity);
        this.setMoveSpeed(moveSpeed);

        for (int i = 0; i < MAX_BULLETS; i += 1)
            bullets[i] = new BulletPlayer();
    }

    @Override public void render(ShapeRenderer shape, Color _color) {

        super.render(shape, getPolarity().getColor()); // draw player


        // draw dashed line from player to mouse position
        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(_color);

        float segmentLength = 12;
        float distance = getDistanceTo(Inputs.Mouse.getX(), Inputs.Mouse.getY());
        int segmentCount = (int)(distance / segmentLength / 2);
        float dirTowardsMouse = getAngleTowards(Inputs.Mouse.getX(), Inputs.Mouse.getY());
        float deltaX = (float)Math.cos(dirTowardsMouse) * segmentLength;
        float deltaY = (float)Math.sin(dirTowardsMouse) * segmentLength;
        float startY = getY() + (float)Math.sin(getDirection()) * (getRadius() + segmentLength);
        float startX = getX() + (float)Math.cos(getDirection()) * (getRadius() + segmentLength);
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
        nextX = Math.max(nextX, getRadius());
        nextX = Math.min(nextX, Settings.windowWidth - getRadius());
        nextY = Math.max(nextY, getRadius());
        nextY = Math.min(nextY, Settings.windowHeight - getRadius());

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
        for (int i = 0; i < Game.MAX_ENEMIES; i += 1) {
            if (!Game.enemies[i].getActive())
                continue;
            if (Game.enemies[i].getIsSpawning())
                continue;;
            if (Collision.circleCircle(getX(), getY(), getRadius(), Game.enemies[i].getX(), Game.enemies[i].getY(), Game.enemies[i].getRadius()))
                Game.enemies[i].hit(Game.enemies[i].getHealth());
        }


    }



    void shoot() {
        for (int i = 0; i < MAX_BULLETS; i += 1) {
            if (!bullets[i].getActive() && !bullets[i].getToDestroyNextFrame()) {
                bullets[i].init(getX(), getY(), 8, getDirection(), getPolarity(), bulletSpeed);
                fireRateCooldown = fireRate;
                Sounds.playShootSfx();
                break;
            }
        }
    }



    void switchPolarity() {
        getPolarity().togglePolarity();
    }
}