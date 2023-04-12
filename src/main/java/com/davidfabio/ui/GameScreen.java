package com.davidfabio.ui;

import com.davidfabio.game.Camera;
import com.davidfabio.game.Inputs;
import com.davidfabio.game.Sounds;
import com.davidfabio.game.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen extends ScreenAdapter {
    private static World world;

    private ShapeRenderer shapeRenderer;
    private static Camera camera;
    private static Stage stage;
    private static UserInterface userInterface;

    public static Camera getCamera() { return camera; }

    private static boolean isPaused = false;
    public static float displayRefreshRate;

    private PolygonSprite polygonSprite;
    private PolygonSpriteBatch polygonSpriteBatch = new PolygonSpriteBatch();
    private Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
    private static Texture textureWhite;
    public static Texture getTextureWhite() { return textureWhite; }



    @Override
    public void show() {
        pixmap.setColor(0xFFFFFFFF); // red, green, blue, alpha
        pixmap.fill();
        textureWhite = new Texture(pixmap);

        polygonSpriteBatch = new PolygonSpriteBatch();

        this.world = new World();

        this.shapeRenderer = new ShapeRenderer();
        this.camera = new Camera();
        this.stage = new Stage();
        Sounds.loadSounds();

        this.userInterface = new UserInterface();
        this.userInterface.init(this.world.getPlayer(),this.world.getScore());
        this.stage.addActor(this.userInterface);

        this.isPaused = false;

        Gdx.input.setInputProcessor(this.stage);
    }

    @Override
    public void render(float delta) {
        // TODO (David): frametimes are uneven when using deltaTime (with VSync enabled), so for now at least we are not using it
        float deltaTime = 1.0f / displayRefreshRate; // float deltaTime = Gdx.graphics.getDeltaTime();

        Inputs.update();

        if (Inputs.esc.getWasPressed())
            isPaused = !isPaused;

        // restart game
        // TODO (David): quick and dirty solution; e.g. all the sounds get reloaded again, which is unnecessary
        if (Inputs.tab.getWasPressed())
            show();

        if (isPaused)
            return;



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

        this.polygonSpriteBatch.begin();
        this.polygonSpriteBatch.setProjectionMatrix(this.camera.combined);
        this.world.render(this.polygonSpriteBatch,this.shapeRenderer);
        this.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width,height);
    }
}
