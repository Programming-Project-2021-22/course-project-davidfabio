package org.davidfabio.input;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;

public class Input {
    private ArrayList<Integer> bindings;
    private boolean isDown, wasPressed, wasReleased, wasDownLastFrame;

    public boolean getIsDown() { return isDown; }
    public boolean getWasPressed() { return wasPressed; }
    public boolean getWasReleased() { return wasReleased; }
    public boolean getWasDownLastFrame() { return wasDownLastFrame; }


    public Input(ArrayList<Input> inputList, int... bindings) {
        this.bindings = new ArrayList<>();
        for (int binding : bindings)
            this.bindings.add(binding);

        inputList.add(this);
    }
    void update() {
        wasDownLastFrame = isDown;
        isDown = false;
        wasPressed = false;
        wasReleased = false;

        for (int binding : bindings) {
            // mouse buttons
            if (binding >= 0 && binding <= 2) {
                if (Gdx.input.isButtonJustPressed(binding))
                    wasPressed = true;
                if (Gdx.input.isButtonPressed(binding))
                    isDown = true;
            }
            // keyboard keys
            else {
                if (Gdx.input.isKeyJustPressed(binding))
                    wasPressed = true;
                if (Gdx.input.isKeyPressed(binding))
                    isDown = true;
            }
        }

        if (getWasDownLastFrame() && !getIsDown())
            wasReleased = true;
    }

}