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
    private Skin skin;
    private Table mainTable;

    @Override
    public void show() {
        this.viewport = new ExtendViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        this.skin = new Skin(Gdx.files.internal("assets/ui/shade/skin/uiskin.json"));
        this.stage = new Stage(this.viewport);

        this.mainTable = new Table();
        this.mainTable.setFillParent(true);
        this.stage.addActor(this.mainTable);

        this.addLabel("SETTINGS");
        this.addLabel("Sound Effects");
        this.addCheckBox("Sound Effects", Settings.sfxEnabled).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Settings.sfxEnabled = !Settings.sfxEnabled;
            }
        });
        this.addLabel("Sound Effect Volume");
        this.addSlider(0f, 1f, 0.01f, Settings.sfxVolume).addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Slider slider = (Slider)event.getListenerActor();
                Settings.sfxVolume = slider.getValue();
            }
        });
        this.addLabel("Music");
        this.addCheckBox("Music", Settings.musicEnabled).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Settings.musicEnabled = !Settings.musicEnabled;
            }
        });
        this.addLabel("Music Volume");
        this.addSlider(0f, 1f, 0.01f, Settings.musicVolume).addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Slider slider = (Slider)event.getListenerActor();
                Settings.musicVolume = slider.getValue();
            }
        });
        this.addButton("Back").addListener(new ClickListener() {
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

    private void addLabel(String text) {
        Label label = new Label(text, this.skin);
        this.mainTable.add(label).width(Gdx.graphics.getWidth()*0.75f).height(30f).padBottom(10);
        this.mainTable.row();
    }

    private CheckBox addCheckBox(String name, boolean initialValue) {
        CheckBox checkBox = new CheckBox(name, this.skin);
        checkBox.setChecked(initialValue);
        this.mainTable.add(checkBox).width(Gdx.graphics.getWidth()*0.75f).height(30f).padBottom(10);
        this.mainTable.row();
        return checkBox;
    }

    private Slider addSlider(float minimum, float maximum, float step, float initialValue) {
        Slider slider = new Slider(minimum, maximum, step, false, this.skin);
        slider.setValue(initialValue);
        this.mainTable.add(slider).width(Gdx.graphics.getWidth()*0.75f).height(30f).padBottom(10);
        this.mainTable.row();
        return slider;
    }

    private TextButton addButton(String name) {
        TextButton button = new TextButton(name, this.skin);
        this.mainTable.add(button).width(Gdx.graphics.getWidth()*0.75f).height(30f).padBottom(10);
        this.mainTable.row();
        return button;
    }
}
