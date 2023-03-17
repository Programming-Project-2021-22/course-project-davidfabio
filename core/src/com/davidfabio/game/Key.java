package com.davidfabio.game;
import com.badlogic.gdx.Gdx;

public class Key {
    int binding;
    public boolean isDown, wasPressed, wasReleased;
    boolean wasDownLastFrame;

    public Key(int binding) {
        this.binding = binding;
    }

    void Update() {
        wasDownLastFrame = isDown;
        isDown = false;
        wasPressed = false;
        wasReleased = false;

        if (Gdx.input.isKeyJustPressed(binding))
            wasPressed = true;
        if (Gdx.input.isKeyPressed(binding))
            isDown = true;
        if (wasDownLastFrame && !isDown)
            wasReleased = true;
    }
}

