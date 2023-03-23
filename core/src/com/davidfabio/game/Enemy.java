package com.davidfabio.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;



public class Enemy extends Entity {

    private float healthInitial;
    private float health;

    private boolean inHitState;
    private float hitDuration = 0.03f;
    private float hitCooldown;

    private boolean isSpawning;
    private float spawnDuration = 1.0f;
    private float spawnCooldown;

    public float getHealth() { return health; }
    public boolean getIsSpawning() { return isSpawning; }


    public void init(float x, float y, float radius, float direction, Polarity polarity, float moveSpeed, float healthInitial) {
        super.init(x, y, radius, direction, polarity);
        setMoveSpeed(moveSpeed);
        health = this.healthInitial = healthInitial;
        inHitState = false;
        hitCooldown = 0;
        isSpawning = true;
        spawnCooldown = spawnDuration;

        setColorRed(new Color(0.33f, 0, 0, 1));
        setColorBlue(new Color(0, 0, 0.33f, 1));
        setPolarity(polarity); // we call this again to set the color
    }


    public void update(float deltaTime) {
        if (isSpawning) {
            spawnCooldown -= deltaTime;

            if (spawnCooldown < 0)
                isSpawning = false;

            return;
        }

        if (inHitState) {
            hitCooldown -= deltaTime;

            if (hitCooldown < 0)
                inHitState = false;
        }

        moveTowards(Game.player.getX(), Game.player.getY(), deltaTime);
    }


    @Override public void render(ShapeRenderer shape, Color _color) {
        if (!getActive())
            return;

        float _x = Math.round(getX());
        float _y = Game.gameHeight - Math.round(getY());


        Color outlineColor = Color.RED;
        if (getPolarity() == Polarity.BLUE)
            outlineColor = Color.BLUE;

        if (isSpawning) {
            shape.begin(ShapeRenderer.ShapeType.Line);
            shape.setColor(outlineColor);
            shape.arc(_x, _y, getRadius(), 0, 360 - (spawnCooldown * 360));
            shape.end();
            return;
        }

        if (inHitState)
            _color = new Color(0.5f, 0.5f, 0.5f, 1);
        super.render(shape, _color);


        // draw outline
        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(outlineColor);
        shape.circle(_x, _y, getRadius());
        shape.end();
    }


    public void hit(float attackPower) {
        inHitState = true;
        hitCooldown = hitDuration;
        health -= attackPower;

        if (health <= 0) {
            setActive(false);
            Game.sfxExplosion.play(Game.sfxVolume);
        }
    }

}