package org.davidfabio.ui;

import org.davidfabio.PolygonWars;
import org.davidfabio.game.Score;
import org.davidfabio.game.Sounds;
import org.davidfabio.utils.JSONFileManagement;
import org.davidfabio.utils.Settings;
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

import java.io.File;
import java.util.ArrayList;

/**
 * This Enum provides some default values for the Window Resolutions.
 */
enum DefaultWindowSize {
    W1600H900,
    W1366H768,
    W1280H720,
    W1024H768
}

/**
 * This is the Settings Screen, it contains all the Buttons and Sliders that a user may modify.
 */
public class SettingsScreen extends ScreenAdapter {
    /**
     * The stage is required by LibGDX to render the Screen.
     */
    private Stage stage;
    /**
     * The viewport is required by LibGDX to render the Screen.
     */
    private Viewport viewport;
    /**
     * The mainTable contains any Label, or any Control that the user interacts with.
     */
    private Table mainTable;
    /**
     * These are the scores of past games. They are required as they need to be passed to the next screen if the screen
     * is switched.
     */
    private ArrayList<Score> scores;

    /**
     * The SettingsScreen constructor simply stores the scores passed on by another part of the Application.
     * @param scores scores to be stored for the next page
     */
    public SettingsScreen(ArrayList<Score> scores) {
        this.scores = scores;
    }

    /**
     * This method prepares all the UI elements that need to be shown to the user.
     * It also creates the EventHandlers that need to be executed if a Button, Slider, or Checkbox is triggered.
     */
    @Override
    public void show() {
        viewport = new ExtendViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        stage = new Stage(viewport);

        mainTable = new Table();
        mainTable.setFillParent(true);
        stage.addActor(mainTable);

        UIBuilder.loadSkin();
        UIBuilder.addTitleLabel(mainTable,"SETTINGS",true);
        UIBuilder.addSubtitleLabel(mainTable,"Sound Effects",true);
        UIBuilder.addSubtitleLabel(mainTable,"Graphics",false);
        UIBuilder.addCheckBox(mainTable,"Sound Effects", Settings.sfxEnabled,true, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Settings.sfxEnabled = !Settings.sfxEnabled;
                Sounds.loadSounds();
            }
        });
        UIBuilder.addCheckBox(mainTable,"Fullscreen", Settings.fullscreenEnabled,false, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Settings.fullscreenEnabled = !Settings.fullscreenEnabled;
            }
        });
        UIBuilder.addLabel(mainTable,"",true);
        UIBuilder.addSelectBox(mainTable,false,new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SelectBox selectBox = (SelectBox) actor;
                DefaultWindowSize defaultWindowSize = (DefaultWindowSize) selectBox.getSelected();
                setSettingsFromDefaultWindowSize(defaultWindowSize);
            }
        },getSelectedWindowSize(),DefaultWindowSize.W1024H768,DefaultWindowSize.W1280H720,DefaultWindowSize.W1366H768,DefaultWindowSize.W1600H900);
        UIBuilder.addLabel(mainTable,"Sound Effect Volume",true);
        UIBuilder.addLabel(mainTable,"Changing Display Settings (Resolution, Fullscreen, ...)\nrequires a Restart of the Application.",false);
        UIBuilder.addSlider(mainTable,0f,1f,0.01f,Settings.sfxVolume,true,new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Slider slider = (Slider)event.getListenerActor();
                Settings.sfxVolume = slider.getValue();
                Sounds.playHitSfx();
            }
        });
        UIBuilder.addSubtitleLabel(mainTable,"Music",true);
        UIBuilder.addSubtitleLabel(mainTable,"Username",false);
        UIBuilder.addCheckBox(mainTable,"Music", Settings.musicEnabled,true,new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Settings.musicEnabled = !Settings.musicEnabled;
                Sounds.loadSounds();
            }
        });
        UIBuilder.addTextInput(mainTable, Settings.username, false, new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                TextField textField = (TextField)event.getListenerActor();
                Settings.username = textField.getText();
            }
        });
        UIBuilder.addLabel(mainTable,"Music Volume",true);
        UIBuilder.addSlider(mainTable,0f,1f,0.01f,Settings.musicVolume,true,new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Slider slider = (Slider)event.getListenerActor();
                Settings.musicVolume = slider.getValue();
            }
        });
        UIBuilder.addLabel(mainTable,"",true);
        UIBuilder.addButton(mainTable,"Back",false,new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                File settingsFile = new File(Settings.SETTINGS_FILENAME);
                JSONFileManagement.writeSettingsToFile(settingsFile);
                ((PolygonWars)Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(scores));
            }
        });

        Gdx.input.setInputProcessor(stage);
    }

    /**
     * This method renders the contents from the stage (which are defined by {@link SettingsScreen#show()}.
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    /**
     * This method is called if the window is ever resized.
     * @param width
     * @param height
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    /**
     * This method takes a {@link DefaultWindowSize} enum and writes the correct Resolution to the {@link Settings} class.
     * @param windowSize enum containing the window resolution.
     */
    private void setSettingsFromDefaultWindowSize(DefaultWindowSize windowSize) {
        switch (windowSize) {
            case W1024H768: {
                Settings.windowWidth = 1024;
                Settings.windowHeight = 768;
                break;
            }
            case W1280H720: {
                Settings.windowWidth = 1280;
                Settings.windowHeight = 720;
                break;
            }
            case W1366H768: {
                Settings.windowWidth = 1366;
                Settings.windowHeight = 768;
                break;
            }
            case W1600H900: {
                Settings.windowWidth = 1600;
                Settings.windowHeight = 900;
                break;
            }
        }
    }

    /**
     * This method returns the {@link DefaultWindowSize} enum which corresponds to the currently selected resolution.
     * If no resolution is adequate, this method returns {@link DefaultWindowSize#W1024H768}.
     * @return the enum corresponding to the current window resolution.
     */
    private DefaultWindowSize getSelectedWindowSize() {
        if (Settings.windowWidth == 1024 && Settings.windowHeight == 768) {
            return DefaultWindowSize.W1024H768;
        }
        if (Settings.windowWidth == 1280 && Settings.windowHeight == 720) {
            return DefaultWindowSize.W1280H720;
        }
        if (Settings.windowWidth == 1366 && Settings.windowHeight == 768) {
            return DefaultWindowSize.W1366H768;
        }
        if (Settings.windowWidth == 1600 && Settings.windowHeight == 900) {
            return DefaultWindowSize.W1600H900;
        }
        return DefaultWindowSize.W1024H768;
    }
}
