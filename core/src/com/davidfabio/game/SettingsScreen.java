package com.davidfabio.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class SettingsScreen extends ScreenAdapter {
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

        UIFactory.loadSkin();
        UIFactory.addTitleLabel(this.mainTable,"SETTINGS");
        UIFactory.addSubtitleLabel(this.mainTable,"Sound Effects");
        UIFactory.addCheckBox(this.mainTable,"Sound Effects",Settings.sfxEnabled, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Settings.sfxEnabled = !Settings.sfxEnabled;
            }
        });
        UIFactory.addLabel(this.mainTable,"Sound Effect Volume");
        UIFactory.addSlider(this.mainTable,0f,1f,0.01f,Settings.sfxVolume,new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Slider slider = (Slider)event.getListenerActor();
                Settings.sfxVolume = slider.getValue();
            }
        });
        UIFactory.addSubtitleLabel(this.mainTable,"Music");
        UIFactory.addCheckBox(this.mainTable,"Music", Settings.musicEnabled,new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Settings.musicEnabled = !Settings.musicEnabled;
            }
        });
        UIFactory.addLabel(this.mainTable,"Music Volume");
        UIFactory.addSlider(this.mainTable,0f,1f,0.01f,Settings.musicVolume,new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Slider slider = (Slider)event.getListenerActor();
                Settings.musicVolume = slider.getValue();
            }
        });
        UIFactory.addButton(this.mainTable,"Back",new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Duality)Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
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
