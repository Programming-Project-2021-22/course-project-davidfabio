package com.davidfabio.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input.Buttons;

public class Inputs {

    // list of KeyCodes: https://github.com/libgdx/libgdx/blob/master/gdx/src/com/badlogic/gdx/Input.java#L63
    // to create a new input: declare a new variable here and call its Update method in Inputs.Update

    public static Key up    = new Key(Keys.W);
    public static Key down  = new Key(Keys.S);
    public static Key left  = new Key(Keys.A);
    public static Key right = new Key(Keys.D);


    public static class Mouse {
        public static int x, y; // top-left of the window is 0,0 -- bottom-right is windowWidth,windowHeight
        public static MouseButton left   = new MouseButton(Buttons.LEFT);
        public static MouseButton right  = new MouseButton(Buttons.RIGHT);
        public static MouseButton middle = new MouseButton(Buttons.MIDDLE);
    }



    public static void Update() {
        up.Update();
        down.Update();
        left.Update();
        right.Update();

        Mouse.x = Gdx.input.getX();
        Mouse.y = Gdx.input.getY();
        Mouse.left.Update();
        Mouse.right.Update();
        Mouse.middle.Update();
    }

}
