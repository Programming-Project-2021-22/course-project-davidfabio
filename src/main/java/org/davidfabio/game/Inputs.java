package org.davidfabio.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector3;
import java.util.ArrayList;
import org.davidfabio.ui.GameScreen;

public class Inputs {

    // TODO: support multiple key bindings?
    // TODO: allow user to rebind inputs?
    // TODO: gamepad support?


    public static ArrayList<Key> keys = new ArrayList<Key>();

    // to create a new input you just need to declare a new variable here
    // list of KeyCodes: https://github.com/libgdx/libgdx/blob/master/gdx/src/com/badlogic/gdx/Input.java#L63
    public static Key up    = new Key(Keys.W);
    public static Key down  = new Key(Keys.S);
    public static Key left  = new Key(Keys.A);
    public static Key right = new Key(Keys.D);
    public static Key esc   = new Key(Keys.ESCAPE);
    public static Key space = new Key(Keys.SPACE);
    public static Key tab   = new Key(Keys.TAB);
    public static Key alt   = new Key(Keys.ALT_LEFT);
    public static Key enter = new Key(Keys.ENTER);



    public static void update() {
        for (Key key : keys)
            key.update();

        Mouse.update();
    }


    public static class Mouse {
        public static Button left   = new Button(Buttons.LEFT);
        public static Button right  = new Button(Buttons.RIGHT);
        public static Button middle = new Button(Buttons.MIDDLE);
        private static int x, y; // top-left of the window is 0,0 -- bottom-right is windowWidth,windowHeight

        public static int getX() { return x; }
        public static int getY() { return y; }

        static void update() {
            Camera gameCamera = GameScreen.getCamera();
            if (gameCamera != null) {
                Vector3 unprojectedCoords = gameCamera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
                x = (int)unprojectedCoords.x;
                y = (int)unprojectedCoords.y;
            } else {
                x = Gdx.input.getX();
                y = Gdx.input.getY();
            }
            left.update();
            right.update();
            middle.update();
        }
    }


    public static class Input {
        private final int binding;
        private boolean isDown, wasPressed, wasReleased, wasDownLastFrame;

        public Input(int binding) {
            this.binding = binding;
        }

        public int getBinding() { return binding; }

        public boolean getIsDown() { return isDown; }

        public void setIsDown(boolean isDown) { this.isDown = isDown; }

        public boolean getWasPressed() { return wasPressed; }

        public void setWasPressed(boolean wasPressed) { this.wasPressed = wasPressed; }

        public boolean getWasReleased() { return wasReleased; }

        public void setWasReleased(boolean wasReleased) { this.wasReleased = wasReleased; }

        public boolean getWasDownLastFrame() { return wasDownLastFrame; }

        void update() {
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


    public static class Button extends Input {

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

}
