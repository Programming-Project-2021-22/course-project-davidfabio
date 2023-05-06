package org.davidfabio.ui;

import org.davidfabio.game.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import org.davidfabio.input.Inputs;

import java.util.ArrayList;

/**
 * This is the game's main screen. It asks the Game's state to {@link GameScreen#render(float)} periodically and
 * provides the Player with a visual representation of the Game's state.
 */
public class GameScreen extends ScreenAdapter {
    /**
     * The whole Game State which is rendered and updated in this class.
     */
    private static World world;
    /**
     * The rendering batch used to render any LibGDX's shapes.
     */
    private ShapeRenderer shapeRenderer;
    /**
     * The camera used to show the Game. This allows for zooming and focusing on the Player.
     */
    private static Camera camera;
    /**
     * The stage is required by LibGDX to render the Screen.
     */
    private Stage stage;
    /**
     * The User Interface that is displayed during the game.
     */
    private static UserInterface userInterface;
    /**
     * This value traces whether or not the game is currently paused. When paused the update()-loop does not change the
     * Game's state.
     */
    private static boolean isPaused = false;
    public static float displayRefreshRate;
    /**
     * The rendering batch that is used to render any LibGDX's PolygonShapes.
     */
    private PolygonSpriteBatch polygonSpriteBatch = new PolygonSpriteBatch();
    /**
     * This List contains the lives that a player has. True indicates an existing life, while False means that this live
     * was already lost.
     */
    private ArrayList<Score> scores;

    public GameScreen(ArrayList<Score> scores) {
        this.scores = scores;
    }
    public static Camera getCamera() { return camera; }

    @Override
    public void show() {
        PolygonShape.init();
        polygonSpriteBatch = new PolygonSpriteBatch();

        world = new World(scores);

        shapeRenderer = new ShapeRenderer();
        camera = new Camera(world.getLevel());
        stage = new Stage();
        Sounds.loadSounds();
        Sounds.playBackgroundMusic();

        userInterface = new UserInterface();
        userInterface.init(world.getPlayer(),world.getScore());
        stage.addActor(userInterface);

        isPaused = false;

        Gdx.input.setInputProcessor(stage);

        Sounds.playGameStartSfx();
    }

    @Override
    public void render(float delta) {
        // TODO (David): frametimes are uneven when using deltaTime (with VSync enabled), so for now at least we are not using it
        float deltaTime = 1.0f / displayRefreshRate; // float deltaTime = Gdx.graphics.getDeltaTime();

        Inputs.update();

        if (Inputs.pause.getWasPressed()) {
            isPaused = !isPaused;
            if (isPaused)
                Sounds.stopBackgroundMusic();
            else
                Sounds.playBackgroundMusic();
        }

        // restart game
        // TODO (David): quick and dirty solution; e.g. all the sounds get reloaded again, which is unnecessary
        if (Inputs.restart.getWasPressed())
            show();

        if (isPaused) {
            return;
        }



        // ---------------- update game logic ----------------
        // Update World
        world.update(deltaTime);

        // Update User Interface
        userInterface.update(world.getPlayer(),world.getScore());



        // ---------------- rendering ----------------
        ScreenUtils.clear(0, 0, 0.2f, 1);

        // Reposition camera on player
        camera.updateCameraPosition(deltaTime, world.getPlayer());
        shapeRenderer.setProjectionMatrix(camera.combined);

        polygonSpriteBatch.setProjectionMatrix(camera.combined);
        world.render(polygonSpriteBatch,shapeRenderer);
        stage.draw();
    }

    /**
     * This method is called if the window is ever resized.
     * @param width
     * @param height
     */
    @Override
    public void resize(int width, int height) {
        super.resize(width,height);
    }
}
