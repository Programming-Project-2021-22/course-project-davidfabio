package org.davidfabio.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.davidfabio.utils.Settings;

public class Entity implements Movable {
    private float x, y;
    private float scale;
    private float moveSpeed;
    private float angle; // in radians
    private boolean isActive = false;
    private PolygonShape shape; // needs to be initialized in the child init method

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
    public PolygonShape getShape() { return shape; }
    public void setShape(PolygonShape shape) { this.shape = shape; }

    private Color color, colorInitial;
    public void setTransparency(float transparency) { color.a = transparency; }
    public Color getColor() { return color; }
    public Color getColorInitial() { return new Color(colorInitial.r, colorInitial.g, colorInitial.b, colorInitial.a); }
    public void setColor(Color color) {
        this.color.r = color.r;
        this.color.g = color.g;
        this.color.b = color.b;
        this.color.a = color.a;
    }


    // the reason for using this method to setup the entity instead of using constructor is the following:
    // we want to create all entities before the game begins to minimize garbage collection as much as possible
    public void init(float x, float y, float scale, Color color) {
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.colorInitial = color;
        this.color = getColorInitial();

        this.angle = 0;
        this.isActive = true;
    }


    public void render(PolygonSpriteBatch polygonSpriteBatch, ShapeRenderer shapeRenderer) {
        if (!isActive)
            return;

        polygonSpriteBatch.begin();
        shape.render(polygonSpriteBatch, color);
        polygonSpriteBatch.end();
    }


    public void spawnParticles(float particleScale, int particleCount, World world, Particle.Type type) {
        for (int i = 0; i < Settings.MAX_PARTICLES; i += 1) {
            Particle particle = world.getParticles()[i];
            if (!particle.getIsActive()) {
                particle.init(getX(), getY(), particleScale, getColorInitial(), new PolygonShape(particleCount, particleScale), type);
                break;
            }
        }
    }

}
