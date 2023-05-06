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
    private static World world;

    private ShapeRenderer shapeRenderer;
    private static Camera camera;
    private static Stage stage;
    private static UserInterface userInterface;

    public static Camera getCamera() { return camera; }

    private static boolean isPaused = false;
    public static float displayRefreshRate;

    private PolygonSpriteBatch polygonSpriteBatch = new PolygonSpriteBatch();
    private ArrayList<Score> scores;

    public GameScreen(ArrayList<Score> scores) {
        this.scores = scores;
    }

    @Override
    public void show() {
        PolygonShape.init();
        polygonSpriteBatch = new PolygonSpriteBatch();

        this.world = new World(scores);

        this.shapeRenderer = new ShapeRenderer();
        this.camera = new Camera(this.world.getLevel());
        this.stage = new Stage();
        Sounds.loadSounds();
        Sounds.playBackgroundMusic();

        this.userInterface = new UserInterface();
        this.userInterface.init(this.world.getPlayer(),this.world.getScore());
        this.stage.addActor(this.userInterface);

        this.isPaused = false;

        Gdx.input.setInputProcessor(this.stage);

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
        this.world.update(deltaTime);

        // Update User Interface
        this.userInterface.update(this.world.getPlayer(),this.world.getScore());



        // ---------------- rendering ----------------
        ScreenUtils.clear(0, 0, 0.2f, 1);

        // Reposition camera on player
        this.camera.updateCameraPosition(deltaTime, this.world.getPlayer());
        this.shapeRenderer.setProjectionMatrix(this.camera.combined);

        this.polygonSpriteBatch.setProjectionMatrix(this.camera.combined);
        this.world.render(this.polygonSpriteBatch,this.shapeRenderer);
        this.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width,height);
    }
}
