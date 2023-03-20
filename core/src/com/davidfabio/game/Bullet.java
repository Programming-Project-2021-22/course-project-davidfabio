package com.davidfabio.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Bullet {
    private static final float SPEED = 500;
    private static final int BULLET_LENGTH = 30;
    private static final int BULLET_WIDTH = 15;
    private Polarity polarity;
    private Vector2 position;
    private Vector2 direction;
    private int firePower;

    public Bullet(int newFirePower) {
        this.polarity = new Polarity();
        this.firePower = newFirePower;
    }

    public Bullet(Polarity newPolarity, int newFirePower) {
        this.polarity = newPolarity.duplicate();
        this.firePower = newFirePower;
    }

    public void setPosition(Vector2 newPosition) {
        this.position = newPosition;
    }

    public Vector2 getPosition() {
        return this.position;
    }

    public void draw(ShapeRenderer renderer) {
        this.update();

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(this.polarity.getCurrentColor());
        renderer.ellipse(this.getPosition().x, this.getPosition().x, Bullet.BULLET_WIDTH, Bullet.BULLET_LENGTH);
        renderer.end();
    }

    private void update() {
        this.moveInDirection();
    }

    private void moveInDirection() {

    }
}
