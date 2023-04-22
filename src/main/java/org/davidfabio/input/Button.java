package org.davidfabio.input;

import com.badlogic.gdx.Gdx;

public class Button extends Input {

    public Button(int binding) {
        super(binding);
    }

    void update() {
        super.update();

        if (Gdx.input.isButtonJustPressed(getBinding()))
            setWasPressed(true);
        if (Gdx.input.isButtonPressed(getBinding()))
            setIsDown(true);
        if (getWasDownLastFrame() && !getIsDown())
            setWasReleased(true);
    }
}