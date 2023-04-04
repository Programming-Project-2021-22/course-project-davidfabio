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

        PolygonRegion polygonRegion = new PolygonRegion(new TextureRegion(currentTexture), vertices, triangles);
        PolygonSprite polygonSprite = new PolygonSprite(polygonRegion);
        polygonSprite.setOrigin(getX(), getY());
        polygonSprite.rotate(Movable.radiansToDegrees(getDirection()));
        polygonSprite.draw(polygonSpriteBatch);
    }
}
