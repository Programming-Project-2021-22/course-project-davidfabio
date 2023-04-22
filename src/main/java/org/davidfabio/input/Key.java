package org.davidfabio.input;

import com.badlogic.gdx.Gdx;

public class Key extends Input {

    public Key(int binding) {
        super(binding);
        Inputs.keys.add(this);
    }

    void update() {
        super.update();

        if (Gdx.input.isKeyJustPressed(getBinding()))
            setWasPressed(true);
        if (Gdx.input.isKeyPressed(getBinding()))
            setIsDown(true);
        if (getWasDownLastFrame() && !getIsDown())
            setWasReleased(true);
    }
}