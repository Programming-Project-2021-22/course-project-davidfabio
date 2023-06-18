package org.davidfabio.input;

import com.badlogic.gdx.Input.Keys;
import java.util.ArrayList;

/**
 * This class keeps track of any keybindings. It allows the Program to use abstract names for actual keybindings.
 * This enables us to map multiple keys to actions and could allow customization.
 */
public class Inputs {
    /**
     * This list keeps track of all inputs available in the Game. This is required as the inputs need to be updated
     * periodically.
     */
    private static ArrayList<Input> inputList = new ArrayList<>();
    // to create a new input you just need to declare a new variable here
    // list of KeyCodes: https://github.com/libgdx/libgdx/blob/master/gdx/src/com/badlogic/gdx/Input.java#L63

    /**
     * This constant is used to rename the LibGDX constant to MOUSE_LEFT.
     */
    private static final int MOUSE_LEFT   = com.badlogic.gdx.Input.Buttons.LEFT;
    /**
     * This constant is used to rename the LibGDX constant to MOUSE_RIGHT.
     */
    private static final int MOUSE_RIGHT  = com.badlogic.gdx.Input.Buttons.RIGHT;

    /**
     * This input indicates that the player wants to move up.
     */
    public static Input moveUp    = new Input(inputList, Keys.W, Keys.UP);
    /**
     * This input indicates that the player wants to move down.
     */
    public static Input moveDown  = new Input(inputList, Keys.S, Keys.DOWN);
    /**
     * This input indicates that the player wants to move left.
     */
    public static Input moveLeft  = new Input(inputList, Keys.A, Keys.LEFT);
    /**
     * This input indicates that the player wants to move right.
     */
    public static Input moveRight = new Input(inputList, Keys.D, Keys.RIGHT);
    /**
     * This input indicates that the user wants to pause.
     */
    public static Input pause     = new Input(inputList, Keys.ESCAPE);
    /**
     * This input indicates that the user wants to restart the game.
     */
    public static Input restart   = new Input(inputList, Keys.TAB);
    /**
     * This input indicates that the player wants to shoot.
     */
    public static Input shoot     = new Input(inputList, MOUSE_LEFT);
    /**
     * This input indicates that the player wants to dash.
     */
    public static Input dash      = new Input(inputList, MOUSE_RIGHT, Keys.SPACE);

    /**
     * This method iterates over all inputs present in the {@link Inputs#inputList} and updates the state of each input.
     * It also updates the state of the {@link Mouse} class.
     */
    public static void update() {
        for (Input input : inputList)
            input.update();

        Mouse.update();
    }
}
