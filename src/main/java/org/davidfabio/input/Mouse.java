package org.davidfabio.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import org.davidfabio.game.Camera;
import org.davidfabio.ui.GameScreen;

public class Mouse {
    private static int x, y; // top-left of the window is 0,0 -- bottom-right is windowWidth,windowHeight
    public static Button left   = new Button(Input.Buttons.LEFT);
    public static Button right  = new Button(Input.Buttons.RIGHT);
    public static Button middle = new Button(Input.Buttons.MIDDLE);

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