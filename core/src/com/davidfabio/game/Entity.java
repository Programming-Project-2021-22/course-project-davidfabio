package com.davidfabio.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Entity {

    public enum Polarity {
        RED,
        BLUE
    }

    private float x, y;
    private float radius;
    private float moveSpeed;
    private float direction; // in radians
    private boolean isActive = false;

    private Polarity polarity;
    private Color color;
    private Color colorRed = Color.RED, colorBlue = Color.BLUE;

    public float getX() { return x; }
    public float getY() { return y; }
    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }
    public float getRadius() { return radius; }
    public float getMoveSpeed() { return moveSpeed; }
    public float getDirection() { return direction; }
    public void setDirection(float direction) { this.direction = direction; }
    public void setMoveSpeed(float moveSpeed) { this.moveSpeed = moveSpeed; }
    public boolean getActive() { return isActive; }
    public void setActive(boolean isActive) { this.isActive = isActive; }
    public Polarity getPolarity() { return polarity; }
    public void setColorRed(Color color) { colorRed = color; }
    public void setColorBlue(Color color) { colorBlue = color; }
    public Color getColor() { return color; }



    // the reason for using this method to setup the entity instead of using constructor is the following:
    // we want to create all entities before the game begins to minimize garbage collection as much as possible
    public void init(float x, float y, float radius, float direction, Polarity polarity) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.direction = direction;
        setPolarity(polarity);
        isActive = true;
    }


    public void update(float deltaTime) {

    }


    public void render(ShapeRenderer shape) {
        if (!isActive)
            return;

        float _x = Math.round(x);
        float _y = Game.gameHeight - Math.round(y);

        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(color);
        shape.circle(_x, _y, radius);
        shape.end();
    }


    public void setPolarity(Polarity polarity) {
        this.polarity = polarity;

        switch (polarity) {
            case RED:  color = colorRed; break;
            case BLUE: color = colorBlue; break;
        }
    }


    public boolean isInView() {
        if (x + radius < 0)
            return false;
        else if (x - radius > Game.gameWidth)
            return false;
        else if (y + radius < 0)
            return false;
        else if (y - radius > Game.gameHeight)
            return false;

        return true;
    }



}
