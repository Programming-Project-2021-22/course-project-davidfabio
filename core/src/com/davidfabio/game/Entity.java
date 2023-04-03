package com.davidfabio.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Entity {
    private float x, y;
    private float scale;
    private float moveSpeed;
    private float direction; // in radians
    private boolean isActive = false;
    private Polarity polarity;

    public float getX() { return x; }
    public float getY() { return y; }
    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }
    public float getScale() { return scale; }
    public float getMoveSpeed() { return moveSpeed; }
    public float getDirection() { return direction; }
    public void setDirection(float direction) { this.direction = direction; }
    public void setMoveSpeed(float moveSpeed) { this.moveSpeed = moveSpeed; }
    public boolean getIsActive() { return isActive; }
    public void setIsActive(boolean isActive) { this.isActive = isActive; }
    public Polarity getPolarity() { return polarity; }
    public void setPolarity(Polarity polarity) { this.polarity = polarity; }

    // TESTING
    public float[] vertices, verticesInitial;
    public short[] triangles; // in counter-clockwise direction
    public Texture currentTexture = GameScreen.getTextureYellow();


    // the reason for using this method to setup the entity instead of using constructor is the following:
    // we want to create all entities before the game begins to minimize garbage collection as much as possible
    public void init(float x, float y, float scale, Polarity polarity) {
        this.x = x;
        this.y = y;
        this.scale = scale;
        setPolarity(polarity);
        isActive = true;
        direction = 0;
    }


    public void render(ShapeRenderer shape, Color _color) {
        if (!isActive)
            return;

        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(_color);
        shape.circle(x, y, scale);
        shape.end();
    }


    public void render(PolygonSpriteBatch polygonSpriteBatch) {
        if (!isActive)
            return;

        for (int i = 0; i < vertices.length; i += 1) {
            vertices[i] = verticesInitial[i];
            if (i % 2 == 0)
                vertices[i] += getX();
            else {
                vertices[i] += getY();
            }
        }

        PolygonRegion polygonRegion = new PolygonRegion(new TextureRegion(currentTexture),
                vertices,
                new short[] { // specify triangles in counter-clockwise direction
                        0, 1, 2, // triangle 1
                        2, 3, 0  // triangle 2
                });
        PolygonSprite polygonSprite = new PolygonSprite(polygonRegion);
        polygonSprite.setOrigin(getX(), getY());
        polygonSprite.rotate(getDirection());
        polygonSprite.draw(polygonSpriteBatch);
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

    public float radiansToDegrees(float angleRadians) {
        return angleRadians * (float)(180 / Math.PI);
    }

    public float degreesToRadians(float angleDegrees) {
        return angleDegrees * (float)(Math.PI / 180);
    }

}
