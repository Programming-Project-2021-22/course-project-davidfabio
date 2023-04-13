package org.davidfabio.ui;

import org.davidfabio.Duality;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.davidfabio.utils.Settings;

public class MainMenuScreen extends ScreenAdapter {
    private Stage stage;
    private Viewport viewport;
    private Table mainTable;

    @Override
    public void show() {
        this.viewport = new ExtendViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        this.stage = new Stage(this.viewport);

        this.mainTable = new Table();
        this.mainTable.setFillParent(true);
        this.stage.addActor(this.mainTable);

        UIBuilder.loadSkin();
        UIBuilder.addTitleLabel(this.mainTable, Settings.GAME_TITLE);
        UIBuilder.addSubtitleLabel(this.mainTable,"80's Arcade for a modern audience.");
        UIBuilder.addButton(this.mainTable,"Play",new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Duality)Gdx.app.getApplicationListener()).setScreen(new GameScreen());
            }
        });
        UIBuilder.addButton(this.mainTable,"Options",new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Duality)Gdx.app.getApplicationListener()).setScreen(new SettingsScreen());
            }
        });
        UIBuilder.addButton(this.mainTable,"High Scores",new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Duality)Gdx.app.getApplicationListener()).setScreen(new HighscoreScreen());
            }
        });
        UIBuilder.addButton(this.mainTable,"Quit",new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        Gdx.input.setInputProcessor(this.stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.stage.act();
        this.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}
