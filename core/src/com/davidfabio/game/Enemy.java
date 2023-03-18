package com.davidfabio.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Enemy {

    float hitPointsInitial = 10;
    private float hitPoints;

    float x, y;
    float radius = 28;
    Color color = new Color(0.25f, 0, 0, 1);
    boolean isActive = false;


    public Enemy() {
        x = (float)Math.random() * Game.gameWidth;
        y = (float)Math.random() * Game.gameHeight;
        hitPoints = hitPointsInitial;
        isActive = true;
    }


    public void hit(float hitPower) {
        hitPoints -= hitPower;

        if (hitPoints <= 0) {
            isActive = false;
            Game.enemies.remove(this);
        }
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

        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(Color.RED);
        shape.circle(_x, _y, radius);
        shape.end();
    }


}
