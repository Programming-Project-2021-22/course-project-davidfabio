package com.davidfabio.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input.Buttons;

import java.util.ArrayList;

public class Inputs {

    // TODO: support multiple key bindings? allow user to rebind inputs?


    public static ArrayList<Key> keys = new ArrayList<Key>();

    // to create a new input you just need to declare a new variable here
    // list of KeyCodes: https://github.com/libgdx/libgdx/blob/master/gdx/src/com/badlogic/gdx/Input.java#L63
    public static Key up    = new Key(Keys.W);
    public static Key down  = new Key(Keys.S);
    public static Key left  = new Key(Keys.A);
    public static Key right = new Key(Keys.D);


    public static void Update() {
        for (Key key : keys)
            key.Update();

        Mouse.Update();
    }


    public static class Mouse {
        public static int x, y; // top-left of the window is 0,0 -- bottom-right is windowWidth,windowHeight
        public static Button left   = new Button(Buttons.LEFT);
        public static Button right  = new Button(Buttons.RIGHT);
        public static Button middle = new Button(Buttons.MIDDLE);

        static void Update() {
            x = Gdx.input.getX();
            y = Gdx.input.getY();
            left.Update();
            right.Update();
            middle.Update();
        }
    }


    public static class Input {
        int binding;
        public boolean isDown, wasPressed, wasReleased;
        boolean wasDownLastFrame;

        public Input(int binding) {
            this.binding = binding;
        }

        void Update() {
            wasDownLastFrame = isDown;
            isDown = false;
            wasPressed = false;
            wasReleased = false;
        }
    }


    public static class Key extends Input {

        public Key(int binding) {
            super(binding);
            keys.add(this);
        }

        void Update() {
            super.Update();

            if (Gdx.input.isKeyJustPressed(binding))
                wasPressed = true;
            if (Gdx.input.isKeyPressed(binding))
                isDown = true;
            if (wasDownLastFrame && !isDown)
                wasReleased = true;
        }
    }


    public static class Button extends Input {

        public Button(int binding) {
            super(binding);
        }

        void Update() {
            super.Update();

            if (Gdx.input.isButtonJustPressed(binding))
                wasPressed = true;
            if (Gdx.input.isButtonPressed(binding))
                isDown = true;
            if (wasDownLastFrame && !isDown)
                wasReleased = true;
        }
    }

}
