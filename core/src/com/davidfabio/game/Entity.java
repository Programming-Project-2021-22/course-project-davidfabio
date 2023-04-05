package com.davidfabio.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Entity implements Movable {
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
    public void setScale(float scale) { this.scale = scale; }
    public float getMoveSpeed() { return moveSpeed; }
    public float getDirection() { return direction; }
    public void setDirection(float direction) { this.direction = direction; }
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
        direction = 0;
    }


    public void render(PolygonSpriteBatch polygonSpriteBatch) {
        if (!isActive)
            return;
        shape.render(polygonSpriteBatch, this);
    }
}
