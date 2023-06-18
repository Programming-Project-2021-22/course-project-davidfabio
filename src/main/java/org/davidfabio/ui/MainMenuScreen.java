package org.davidfabio.ui;

import org.davidfabio.PolygonWars;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.davidfabio.game.Score;
import org.davidfabio.utils.JSONFileManagement;
import org.davidfabio.utils.Settings;

import java.io.File;
import java.util.ArrayList;

/**
 * This is the Main Menu that offers the User the ability to play, look at high scores, change the settings or exit
 * the application altogether.
 */
public class MainMenuScreen extends ScreenAdapter {
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
     * This constructor initializes the MainMenuScreen object.
     * It receives the list of scores in order to pass them on to any other screen requiring them.
     *
     * @param scores list of all past game scores.
     */
    public MainMenuScreen(ArrayList<Score> scores) {
        this.scores = scores;
    }

    /**
     * This method builds the User interface.
     * It contains any Buttons, Labels or other User Interface Controls that a User may need to see.
     * In this method we create a List of Buttons that allow the User to navigate to any other screen.
     */
    @Override
    public void show() {
        viewport = new ExtendViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        stage = new Stage(viewport);

        mainTable = new Table();
        mainTable.setFillParent(true);
        stage.addActor(mainTable);

        UIBuilder.loadSkin();
        UIBuilder.addTitleLabel(mainTable, Settings.GAME_TITLE,true);
        UIBuilder.addSubtitleLabel(mainTable,"80's Arcade for a modern audience.",true);
        UIBuilder.addLabel(mainTable,"",true);
        UIBuilder.addLabel(mainTable,"Welcome, " + Settings.username + "!",true);
        UIBuilder.addLabel(mainTable,"In order to move use the Arrow Keys or WASD.\nAim using the Mouse pointer.\nWith the Left Mouse you can shoot.\nUse Right Mouse or Space to dash.",true);
        UIBuilder.addLabel(mainTable,"",true);
        UIBuilder.addButton(mainTable,"Play",true,new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((PolygonWars)Gdx.app.getApplicationListener()).setScreen(new GameScreen(scores));
            }
        });
        UIBuilder.addButton(mainTable,"Options",true,new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((PolygonWars)Gdx.app.getApplicationListener()).setScreen(new SettingsScreen(scores));
            }
        });
        UIBuilder.addButton(mainTable,"High Scores",true,new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((PolygonWars)Gdx.app.getApplicationListener()).setScreen(new HighscoreScreen(scores));
            }
        });
    UIBuilder.addButton(
        mainTable, "Quit", true,
        new ClickListener() {
          @Override
          public void clicked(InputEvent event, float x, float y) {
            File scoresFile = new File(Settings.SCORES_FILENAME);
            JSONFileManagement.writeScoresToFile(scoresFile, scores);
            Gdx.app.exit();
          }
        });

        Gdx.input.setInputProcessor(stage);
    }

    /**
     * This method draws the actual Screen.
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
}
