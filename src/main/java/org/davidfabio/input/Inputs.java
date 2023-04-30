package org.davidfabio.input;

import com.badlogic.gdx.Input.Keys;

import java.util.ArrayList;

public class Inputs {

    public static ArrayList<Input> inputList = new ArrayList<>();

    // to create a new input you just need to declare a new variable here
    // list of KeyCodes: https://github.com/libgdx/libgdx/blob/master/gdx/src/com/badlogic/gdx/Input.java#L63

    private static int MOUSE_LEFT   = com.badlogic.gdx.Input.Buttons.LEFT;
    private static int MOUSE_RIGHT  = com.badlogic.gdx.Input.Buttons.RIGHT;
    private static int MOUSE_MIDDLE = com.badlogic.gdx.Input.Buttons.MIDDLE;

    public static Input moveUp    = new Input(Keys.W, Keys.UP);
    public static Input moveDown  = new Input(Keys.S, Keys.DOWN);
    public static Input moveLeft  = new Input(Keys.A, Keys.LEFT);
    public static Input moveRight = new Input(Keys.D, Keys.RIGHT);
    public static Input pause     = new Input(Keys.ESCAPE);
    public static Input restart   = new Input(Keys.TAB);
    public static Input shoot     = new Input(MOUSE_LEFT);
    public static Input dash      = new Input(MOUSE_RIGHT, Keys.SPACE);


    public static void update() {
        for (Input input : inputList)
            input.update();

        Mouse.update();
    }
}
