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
    private Stage stage;
    private Viewport viewport;
    private Table mainTable;
    private ArrayList<Score> scores;

    public MainMenuScreen(ArrayList<Score> scores) {
        this.scores = scores;
    }

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
