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
        scores.add(score);

        File scoresFile = new File(Settings.SCORES_FILENAME);
        JSONFileManagement.writeScoresToFile(scoresFile, scores);
    }

    /**
     * This method initializes the Game Over Screen's contents. It adds the Buttons and Labels displayed to the user.
     */
    @Override
    public void show() {
        this.viewport = new ExtendViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        this.stage = new Stage(this.viewport);

        this.mainTable = new Table();
        this.mainTable.setFillParent(true);
        this.stage.addActor(this.mainTable);

        UIBuilder.loadSkin();
        UIBuilder.addTitleLabel(this.mainTable,"GAME OVER!",true);
        UIBuilder.addSubtitleLabel(this.mainTable,"You scored a total of " + this.score.getPoints() + " points!",true);
        UIBuilder.addButton(this.mainTable,"Play again",true,new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((PolygonWars)Gdx.app.getApplicationListener()).setScreen(new GameScreen(scores));
            }
        });
        UIBuilder.addButton(this.mainTable,"Main Menu",true,new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((PolygonWars)Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(scores));
            }
        });
        UIBuilder.addButton(this.mainTable,"Quit",true,new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        Gdx.input.setInputProcessor(this.stage);

        Sounds.playGameOverSfx();
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
