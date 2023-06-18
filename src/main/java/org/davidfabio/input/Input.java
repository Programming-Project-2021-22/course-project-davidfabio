package org.davidfabio.input;

import com.badlogic.gdx.Gdx;
import java.util.ArrayList;

/**
 * This class is used to handle an Input. An Input will be mapped to one or more keys.
 */
public class Input {
    /**
     * List of allowed key-bindings for this Input.
     */
    private ArrayList<Integer> bindings;
    /**
     * The state of the Input, so whether or not it was pressed, released or just recently released.
     */
    private boolean isDown, wasPressed, wasReleased, wasDownLastFrame;

    /**
     * @return true if one of the Keys for this Input is currently pressed.
     */
    public boolean getIsDown() { return isDown; }
    /**
     * @return true if one of the Keys for this Input was just pressed.
     */
    public boolean getWasPressed() { return wasPressed; }
    /**
     * @return true if one of the Keys for this Input was just released.
     */
    public boolean getWasReleased() { return wasReleased; }
    /**
     * @return true if one of the Keys for this Input was pressed on the last frame.
     */
    public boolean getWasDownLastFrame() { return wasDownLastFrame; }

    /**
     * Constructor for the Input instance. This constructor takes an input list in which this instance will be added.
     * After the list it is possible to assign as many key-bindings as one wishes to add.
     *
     * @param inputList List of Inputs on which this input will be added.
     * @param bindings List of Key-Bindings that this Input corresponds to.
     */
    public Input(ArrayList<Input> inputList, int... bindings) {
        this.bindings = new ArrayList<>();
        for (int binding : bindings)
            this.bindings.add(binding);

        inputList.add(this);
    }

    /**
     * Updates the State of the Inputs. This method updates the fields {@link Input#wasDownLastFrame}, {@link Input#isDown},
     * {@link Input#wasPressed}, and {@link Input#wasReleased}.
     */
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
