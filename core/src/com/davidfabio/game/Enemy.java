package com.davidfabio.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;



public class Enemy extends Entity {

    private float healthInitial;
    private float health;


    public void init(float x, float y, float radius, float direction, Polarity polarity, float healthInitial) {
        super.init(x, y, radius, direction, polarity);
        health = this.healthInitial = healthInitial;

        setColorRed(new Color(0.33f, 0, 0, 1));
        setColorBlue(new Color(0, 0, 0.33f, 1));
        setPolarity(polarity); // we call this again to set the color
    }

    public void render(ShapeRenderer shape) {
        super.render(shape);

        if (!getActive())
            return;

        float _x = Math.round(getX());
        float _y = Game.gameHeight - Math.round(getY());

        shape.begin(ShapeRenderer.ShapeType.Line);
        Color outlineColor = Color.RED;
        if (getPolarity() == Polarity.BLUE)
            outlineColor = Color.BLUE;
        shape.setColor(outlineColor);
        shape.circle(_x, _y, getRadius());
        shape.end();
    }


    public void hit(float attackPower) {
        health -= attackPower;

        if (health <= 0)
            setActive(false);
    }

}