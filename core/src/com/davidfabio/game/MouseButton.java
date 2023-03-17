package com.davidfabio.game;
import com.badlogic.gdx.Gdx;

public class MouseButton {
    int binding;
    public boolean isDown, wasPressed, wasReleased;
    boolean wasDownLastFrame;

    public MouseButton(int binding) {
        this.binding = binding;
    }

    void Update() {
        wasDownLastFrame = isDown;
        isDown = false;
        wasPressed = false;
        wasReleased = false;

        if (Gdx.input.isButtonJustPressed(binding))
            wasPressed = true;
        if (Gdx.input.isButtonPressed(binding))
            isDown = true;
        if (wasDownLastFrame && !isDown)
            wasReleased = true;
    }
}

