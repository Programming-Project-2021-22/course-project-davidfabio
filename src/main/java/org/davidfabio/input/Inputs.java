package org.davidfabio.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.math.Vector3;
import org.davidfabio.game.Camera;
import org.davidfabio.ui.GameScreen;

import java.util.ArrayList;

public class Inputs {

    // TODO: support multiple key bindings?
    // TODO: allow user to rebind inputs?

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
}
