package com.davidfabio.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Player {
    private Vector2 position;
    private Circle circle;
    private Color currentColor;
    private static final int PLAYER_RADIUS = 25;
    private static final int PLAYER_SPEED = 200;

    public Player() {
        this.setPosition(new Vector2(275,100));
        this.currentColor = Color.BLACK;
    }

    public Texture getTexture(int screenWidth, int screenHeight) {
        Pixmap playerForm = new Pixmap(screenWidth,screenHeight, Pixmap.Format.RGBA8888);
        playerForm.setColor(this.currentColor);
        playerForm.fillCircle((int)this.circle.x, (int)this.circle.y, (int)this.circle.radius);
        return new Texture(playerForm);
    }

    public void setPosition(Vector2 newPosition) {
        this.position = newPosition;
        this.updateCircle();
    }

    public Vector2 getPosition() {
        return this.position;
    }

    public void moveInDirection(int keyPressed) {
        float newPlayerXPos = this.position.x;
        if (keyPressed == Input.Keys.LEFT) {
            newPlayerXPos = this.getPosition().x - (Player.PLAYER_SPEED * Gdx.graphics.getDeltaTime());
        } else if (keyPressed == Input.Keys.RIGHT) {
            newPlayerXPos = this.getPosition().x + (Player.PLAYER_SPEED * Gdx.graphics.getDeltaTime());
        }

        // Check for Screen Limits
        if (newPlayerXPos < Player.PLAYER_RADIUS) {
            newPlayerXPos = Player.PLAYER_RADIUS;
        } else if (newPlayerXPos > (800 - Player.PLAYER_RADIUS)) {
            newPlayerXPos = 800 - Player.PLAYER_RADIUS;
        }
        // Update X-Position if changed
        if (newPlayerXPos != this.position.x) {
            this.setPosition(new Vector2(newPlayerXPos, this.getPosition().y));
        }

        float newPlayerYPos = this.position.y;
        if (keyPressed == Input.Keys.UP) {
            newPlayerYPos = this.getPosition().y - (Player.PLAYER_SPEED * Gdx.graphics.getDeltaTime());
        } else if (keyPressed == Input.Keys.DOWN) {
            newPlayerYPos = this.getPosition().y + (Player.PLAYER_SPEED * Gdx.graphics.getDeltaTime());
        }

        // Check for Screen Limits
        if (newPlayerYPos < Player.PLAYER_RADIUS) {
            newPlayerYPos = Player.PLAYER_RADIUS;
        } else if (newPlayerYPos > (480 - Player.PLAYER_RADIUS)) {
            newPlayerYPos = 480 - Player.PLAYER_RADIUS;
        }
        // Update X-Position if changed
        if (newPlayerYPos != this.position.y) {
            this.setPosition(new Vector2(this.getPosition().x, newPlayerYPos));
        }
    }

    public void switchToNextColor() {
        if (this.currentColor == Color.BLACK) {
            this.currentColor = Color.RED;
        } else {
            this.currentColor = Color.BLACK;
        }
    }

    private void updateCircle() {
        this.circle = new Circle(this.position.x, this.position.y, Player.PLAYER_RADIUS);
    }
}
