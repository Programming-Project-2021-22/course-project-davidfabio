package org.davidfabio.input;

public class Input {
    private int binding;
    private boolean isDown, wasPressed, wasReleased, wasDownLastFrame;

    public int getBinding() { return binding; }
    public boolean getIsDown() { return isDown; }
    public boolean getWasPressed() { return wasPressed; }
    public boolean getWasReleased() { return wasReleased; }
    public boolean getWasDownLastFrame() { return wasDownLastFrame; }
    public void setIsDown(boolean isDown) { this.isDown = isDown; }
    public void setWasPressed(boolean wasPressed) { this.wasPressed = wasPressed; }
    public void setWasReleased(boolean wasReleased) { this.wasReleased = wasReleased; }

    public Input(int binding) {
        this.binding = binding;
    }

    void update() {
        wasDownLastFrame = isDown;
        isDown = false;
        wasPressed = false;
        wasReleased = false;
    }
}