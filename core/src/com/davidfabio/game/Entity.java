package com.davidfabio.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;

public class Entity implements Movable {
    private float x, y;
    private float scale;
    private float moveSpeed;
    private float angle; // in radians
    private boolean isActive = false;
    private Polarity polarity;

    public float getX() { return x; }
    public float getY() { return y; }
    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }
    public float getScale() { return scale; }
    public void setScale(float scale) { this.scale = scale; }
    public float getMoveSpeed() { return moveSpeed; }
    public float getAngle() { return angle; }
    public void setAngle(float angle) { this.angle = angle; }
    public void setMoveSpeed(float moveSpeed) { this.moveSpeed = moveSpeed; }
    public boolean getIsActive() { return isActive; }
    public void setIsActive(boolean isActive) { this.isActive = isActive; }
    public Polarity getPolarity() { return polarity; }
    public void setPolarity(Polarity polarity) { this.polarity = polarity; }

    private Texture texture = GameScreen.getTextureYellow();
    public PolygonShape shape; // needs to be initialized in the child init method

    public Texture getTexture() { return texture; }
    public void setTexture(Texture texture) { this.texture = texture; }



    // the reason for using this method to setup the entity instead of using constructor is the following:
    // we want to create all entities before the game begins to minimize garbage collection as much as possible
    public void init(float x, float y, float scale, Polarity polarity) {
        this.x = x;
        this.y = y;
        this.scale = scale;
        setPolarity(polarity);
        isActive = true;
        angle = 0;
    }


    public void render(PolygonSpriteBatch polygonSpriteBatch) {
        if (!isActive)
            return;
        shape.render(polygonSpriteBatch, this);
    }


    public boolean isInView() {
        if (x + scale < 0)
            return false;
        else if (x - scale > Settings.windowWidth)
            return false;
        else if (y + scale < 0)
            return false;
        else if (y - scale > Settings.windowHeight)
            return false;

        return true;
    }

    public void moveTowards(float direction, float deltaTime) {
        float speed = getMoveSpeed() * deltaTime;
        float deltaX = (float)Math.cos(direction) * speed;
        float deltaY = (float)Math.sin(direction) * speed;

        setX(getX() + deltaX);
        setY(getY() + deltaY);
    }

    public void moveTowards(float otherX, float otherY, float deltaTime) {
        float dir = getAngleTowards(otherX, otherY);
        moveTowards(dir, deltaTime);
    }

    public float getAngleTowards(float otherX, float otherY) {
        return ((float)Math.atan2(otherY - y, otherX - x));
    }

    public float getDistanceTo(float otherX, float otherY) {
        float distanceX = x - otherX;
        float distanceY = y - otherY;
        return (float)Math.sqrt((distanceX * distanceX) + (distanceY * distanceY));
    }


}
