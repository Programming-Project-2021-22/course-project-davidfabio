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

    private boolean isSpawning;
    private float spawnDuration;
    private float spawnCounter;
    private float transparencyWhileSpawning;
    private boolean transparencyWhileSpawningIncreasing = true;

    public float getX() { return x; }
    public void setX(float x) { this.x = x; }
    public float getY() { return y; }
    public void setY(float y) { this.y = y; }
    public float getScale() { return scale; }
    public void setScale(float scale) { this.scale = scale; }
    public float getMoveSpeed() { return moveSpeed; }
    public float getAngle() { return angle; }
    public void setAngle(float angle) { this.angle = angle; }
    public void setMoveSpeed(float moveSpeed) { this.moveSpeed = moveSpeed; }
    public boolean getIsActive() { return isActive; }
    public void setIsActive(boolean isActive) { this.isActive = isActive; }
    public boolean getIsSpawning() { return isSpawning; }
    public void setIsSpawning(boolean isSpawning) { this.isSpawning = isSpawning; }
    public float getSpawnDuration() { return spawnDuration; }
    public void setSpawnDuration(float spawnDuration) { this.spawnDuration = spawnDuration; }
    public float getSpawnCounter() { return spawnCounter; }
    public void setSpawnCounter(float spawnCounter) { this.spawnCounter = spawnCounter; }
    public float getTransparencyWhileSpawning() { return transparencyWhileSpawning; }

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
        transparencyWhileSpawning = 0.6f;
    }

    public void update(float deltaTime, World world) {
        if (isSpawning) {
            if (transparencyWhileSpawningIncreasing)
                transparencyWhileSpawning += deltaTime;
            else
                transparencyWhileSpawning -= deltaTime;

            if (transparencyWhileSpawning > 0.6f)
                transparencyWhileSpawningIncreasing = false;
            else if (transparencyWhileSpawning < 0.1f)
                transparencyWhileSpawningIncreasing = true;
        }

        if (getIsSpawning()) {
            setSpawnCounter(getSpawnCounter() + deltaTime);

            if (getSpawnCounter() > getSpawnDuration()) {
                setColor(getColorInitial());
                setIsSpawning(false);
            }
        }
    }


    public void render(PolygonSpriteBatch polygonSpriteBatch) {
        if (!isActive)
            return;

        Color color = getColor();
        if (isSpawning)
            color.a = transparencyWhileSpawning;

        shape.render(polygonSpriteBatch, this, color);
    }
}
