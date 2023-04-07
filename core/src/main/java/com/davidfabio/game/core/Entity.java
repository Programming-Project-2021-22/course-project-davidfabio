package com.davidfabio.game.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;

public class Entity implements Movable {
    private float x, y;
    private float scale;
    private float moveSpeed;
    private float angle; // in radians
    private boolean isActive = false;

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

    private Color color, colorInitial;
    public Color getColor() { return color; }
    public Color getColorInitial() { return colorInitial; }
    public void setColor(Color color) { this.color = color; }

    public PolygonShape shape; // needs to be initialized in the child init method



    // the reason for using this method to setup the entity instead of using constructor is the following:
    // we want to create all entities before the game begins to minimize garbage collection as much as possible
    public void init(float x, float y, float scale, Color color) {
        this.x = x;
        this.y = y;
        this.colorInitial = this.color = color;
        this.scale = scale;
        isActive = true;
        angle = 0;
    }

    public void render(PolygonSpriteBatch polygonSpriteBatch) {
        if (!isActive)
            return;
        shape.render(polygonSpriteBatch, this, getColor());
    }
}
