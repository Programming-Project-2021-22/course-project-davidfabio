package com.davidfabio.game;

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

    // for testing only
    private static float timeElapsed = 0;
    public static float getTimeElapsed() { return timeElapsed; }

    private PolygonSprite polygonSprite;
    private PolygonSpriteBatch polygonSpriteBatch = new PolygonSpriteBatch();
    private Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
    private static Texture textureRed, textureBlue, textureRedTransparent, textureBlueTransparent, textureWhite, textureYellow;

    public static Texture getTextureRed() { return textureRed; }
    public static Texture getTextureRedTransparent() { return textureRedTransparent; }
    public static Texture getTextureBlueTransparent() { return textureBlueTransparent; }
    public static Texture getTextureBlue() { return textureBlue; }
    public static Texture getTextureWhite() { return textureWhite; }
    public static Texture getTextureYellow() { return textureYellow; }


    @Override
    public void show() {
        pixmap.setColor(0xFF0000FF); // red, green, blue, alpha
        pixmap.fill();
        textureRed = new Texture(pixmap);

        pixmap.setColor(0xFF000044); // red, green, blue, alpha
        pixmap.fill();
        textureRedTransparent = new Texture(pixmap);

        pixmap.setColor(0x0000FFFF); // red, green, blue, alpha
        pixmap.fill();
        textureBlue = new Texture(pixmap);

        pixmap.setColor(0x0000FF44); // red, green, blue, alpha
        pixmap.fill();
        textureBlueTransparent = new Texture(pixmap);

        pixmap.setColor(0xFFFFFFFF); // red, green, blue, alpha
        pixmap.fill();
        textureWhite = new Texture(pixmap);

        pixmap.setColor(0xFFFF00FF); // red, green, blue, alpha
        pixmap.fill();
        textureYellow = new Texture(pixmap);

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
        timeElapsed += deltaTime;

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
        ScreenUtils.clear(0.5f, 0.5f, 0.5f, 1);

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
