package org.davidfabio.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import org.davidfabio.game.Camera;
import org.davidfabio.ui.GameScreen;

/**
 * This class is used to translate the actual Mouse-position to the Worlds-coordinates.
 * This is required because the {@link Camera} distorts the mouse position, and therefore we need to retrieve
 * the "actual" mouse position for many functionalities.
 */
public class Mouse {
    /**
     * Contains the "actual" mouse position
     */
    private static int x, y; // top-left of the window is 0,0 -- bottom-right is windowWidth,windowHeight

    /**
     * Returns the x-position for the Mouse class
     * @return x-position
     */
    public static int getX() {
        return x;
    }

    /**
     * Returns the y-position for the Mouse class
     * @return y-position
     */
    public static int getY() {
        return y;
    }

    /**
     * Updates the Mouse position based on the position on screen and the current camera-projection.
     */
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
    }
}
