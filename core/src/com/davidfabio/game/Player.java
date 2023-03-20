package com.davidfabio.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Player {
    private static final int PLAYER_RADIUS = 25;
    private static final int PLAYER_SPEED = 200;
    private Vector2 position;
    private Polarity polarity;

    public Player() {
        this.setPosition(new Vector2(Gdx.graphics.getWidth() / 2 - Player.PLAYER_RADIUS,Gdx.graphics.getHeight() / 2 - Player.PLAYER_RADIUS));
        this.polarity = new Polarity();
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
        renderer.circle(this.getPosition().x, this.getPosition().y, Player.PLAYER_RADIUS, 20);
        renderer.end();

        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.BLACK);
        renderer.circle(this.getPosition().x, this.getPosition().y, Player.PLAYER_RADIUS, 20);
        renderer.end();
    }

    private void update() {
        this.move();
        if (Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_RIGHT)) {
            this.polarity.togglePolarity();
        }
    }

    private void move() {
        float newPlayerXPos = this.getPosition().x;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            newPlayerXPos = this.getPosition().x - (Player.PLAYER_SPEED * Gdx.graphics.getDeltaTime());
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            newPlayerXPos = this.getPosition().x + (Player.PLAYER_SPEED * Gdx.graphics.getDeltaTime());
        }

        // Check for Screen Limits
        newPlayerXPos = Math.max(newPlayerXPos,Player.PLAYER_RADIUS);
        newPlayerXPos = Math.min(newPlayerXPos,Gdx.graphics.getWidth() - Player.PLAYER_RADIUS);

        float newPlayerYPos = this.getPosition().y;
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {  // Upper bound
            newPlayerYPos = this.getPosition().y + (Player.PLAYER_SPEED * Gdx.graphics.getDeltaTime());
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) { // Lower bound
            newPlayerYPos = this.getPosition().y - (Player.PLAYER_SPEED * Gdx.graphics.getDeltaTime());
        }

        // Check for Screen Limits
        newPlayerYPos = Math.max(newPlayerYPos,Player.PLAYER_RADIUS);
        newPlayerYPos = Math.min(newPlayerYPos,Gdx.graphics.getHeight() - Player.PLAYER_RADIUS);
        // Update Y-Position if changed
        if ((newPlayerXPos != this.getPosition().x) || (newPlayerYPos != this.getPosition().y)) {
            this.setPosition(new Vector2(newPlayerXPos, newPlayerYPos));
        }
    }
}
