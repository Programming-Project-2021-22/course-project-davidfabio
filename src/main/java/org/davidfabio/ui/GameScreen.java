package org.davidfabio.ui;

import org.davidfabio.game.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
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
     * The Pause Menu that has to be shown when paused.
     */
    private static PauseMenu pauseMenu;
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

    /**
     * Initializes the GameScreen object and stores the scores in order to pass them on to other screens.
     * @param scores of past games to be stored
     */
    public GameScreen(ArrayList<Score> scores) {
        this.scores = scores;
    }

    /**
     * @return the camera object of the GameScreen
     */
    public static Camera getCamera() { return camera; }

    /**
     * Prepares the GameScreen layout. It also initializes the {@link GameScreen#world} and {@link GameScreen#camera}
     * objects, as well as the Rendering batches ({@link GameScreen#polygonSpriteBatch} and {@link GameScreen#shapeRenderer}).
     * This method also loads the Sounds and starts the Background Music.
     */
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
        pauseMenu = new PauseMenu();
        pauseMenu.init(scores, () -> {
            isPaused = !isPaused;
            pauseMenu.setVisible(isPaused);
        });
        pauseMenu.setVisible(false);
        stage.addActor(pauseMenu);

        Gdx.input.setInputProcessor(stage);

        Sounds.playGameStartSfx();
    }

    /**
     * Updates the Game Screen and also the Game state by calling {@link World#update(float)}.
     * This method also manages the Pause State.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        // TODO (David): frametimes are uneven when using deltaTime (with VSync enabled), so for now at least we are not using it
        float deltaTime = 1.0f / displayRefreshRate; // float deltaTime = Gdx.graphics.getDeltaTime();

        Inputs.update();
        if (Inputs.pause.getWasPressed()) {
            isPaused = !isPaused;

            pauseMenu.setVisible(isPaused);
            if (isPaused) {
                Sounds.stopBackgroundMusic();
            } else {
                Sounds.playBackgroundMusic();
            }
        }

        // restart game
        // TODO (David): quick and dirty solution; e.g. all the sounds get reloaded again, which is unnecessary
        if (Inputs.restart.getWasPressed())
            show();

        if (isPaused) {
            stage.draw();
            return;
        }

        // ---------------- update game logic ----------------
        // Update World
        world.update(deltaTime);

        // Update User Interface
        userInterface.update(world.getPlayer(),world.getScore());

        // ---------------- rendering ----------------
        // Clear Screen
        ScreenUtils.clear(0, 0, 0.2f, 1);

        // Reposition camera on player
        camera.updateCameraPosition(deltaTime, world.getPlayer());
        shapeRenderer.setProjectionMatrix(camera.combined);

        // Render the Game State
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
