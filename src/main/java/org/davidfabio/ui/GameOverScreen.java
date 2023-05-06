package org.davidfabio.ui;

import org.davidfabio.PolygonWars;
import org.davidfabio.game.Score;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.davidfabio.game.Sounds;
import org.davidfabio.utils.JSONFileManagement;
import org.davidfabio.utils.Settings;

import java.io.File;
import java.util.ArrayList;

/**
 * This is the Screen that is displayed when the Player loses all their lives.
 * It gives the Player the possibility to go to the Main Menu, Play again or Quit the game.
 */
public class GameOverScreen extends ScreenAdapter {
    private Stage stage;
    private Viewport viewport;
    private Table mainTable;
    private ArrayList<Score> scores;
    private Score score;

    /**
     * This initializes the Game Over Screen. Additionally, it adds the current game's score to the
     * general score list.
     * Once the score was added the updated scores-list is written to file using {@link JSONFileManagement#writeScoresToFile(File, ArrayList)}.
     * @param scores this is the list of scores of past games.
     * @param score this is the current game's score
     */
    public GameOverScreen(ArrayList<Score> scores, Score score) {
        super();
        this.score = score;
        this.scores = scores;
        this.scores.add(score);

        File scoresFile = new File(Settings.SCORES_FILENAME);
        JSONFileManagement.writeScoresToFile(scoresFile, this.scores);
    }

    /**
     * This method initializes the Game Over Screen's contents. It adds the Buttons and Labels displayed to the user.
     */
    @Override
    public void show() {
        viewport = new ExtendViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        stage = new Stage(viewport);

        mainTable = new Table();
        mainTable.setFillParent(true);
        stage.addActor(mainTable);

        UIBuilder.loadSkin();
        UIBuilder.addTitleLabel(mainTable,"GAME OVER!",true);
        UIBuilder.addSubtitleLabel(mainTable,"You scored a total of " + score.getPoints() + " points!",true);
        UIBuilder.addButton(mainTable,"Play again",true,new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((PolygonWars)Gdx.app.getApplicationListener()).setScreen(new GameScreen(scores));
            }
        });
        UIBuilder.addButton(mainTable,"Main Menu",true,new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((PolygonWars)Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(scores));
            }
        });
        UIBuilder.addButton(mainTable,"Quit",true,new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        Gdx.input.setInputProcessor(stage);

        Sounds.playGameOverSfx();
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
