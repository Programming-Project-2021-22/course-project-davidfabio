package org.davidfabio.ui;

import org.davidfabio.PolygonWars;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.davidfabio.game.Score;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the Screen that displays the highest scores that a Player has achieved.
 */
public class HighscoreScreen extends ScreenAdapter {
    /**
     * Maximum number of scores to be displayed.
     */
    private static final int MAX_NO_OF_SCORES = 5;
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
     * These are the scores of past games. They are used to show the highscores and to be passed on to any other screen.
     */
    private ArrayList<Score> scores;

    /**
     * Initializes the HighscoreScreen object. The passed scores are used to find the highest scores to be displayed.
     * The list must contain all scores in order to pass them on to any other screen.
     *
     * @param scores complete list of past scores.
     */
    public HighscoreScreen(ArrayList<Score> scores) {
        this.scores = scores;
    }

    /**
     * This method builds the Screen and adds any UI elements that the User needs to see.
     * This means that in this method the High Scores Table is built and using the {@link HighscoreScreen#getHighscores()}
     * method we find out which Scores to display.
     */
    @Override
    public void show() {
        viewport = new ExtendViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        stage = new Stage(viewport);

        mainTable = new Table();
        mainTable.setFillParent(true);
        stage.addActor(mainTable);
        float columnWidth = Gdx.graphics.getWidth() * 0.4f / 4;

        UIBuilder.loadSkin();
        UIBuilder.addTitleLabel(mainTable,"HIGHSCORES",true);
        Table highScoresTable = new Table();
        UIBuilder.addSubtitleLabel(highScoresTable,"Score",columnWidth,true);
        UIBuilder.addSubtitleLabel(highScoresTable,"Username",columnWidth,false);
        UIBuilder.addSubtitleLabel(highScoresTable,"Pickups",columnWidth,false);
        UIBuilder.addSubtitleLabel(highScoresTable,"Time played",columnWidth,false);
        for(Score score : getHighscores()) {
            long durationInSeconds = score.getDuration() / 1000;
            UIBuilder.addLabel(highScoresTable,"" + score.getPoints(),columnWidth,true);
            UIBuilder.addLabel(highScoresTable,score.getUsername(),columnWidth,false);
            UIBuilder.addLabel(highScoresTable,String.format("%d",score.getPickups()),columnWidth,false);
            UIBuilder.addLabel(highScoresTable,String.format("%02d:%02d", durationInSeconds / 60, (durationInSeconds % 60)),columnWidth,false);
        }
        mainTable.row();
        mainTable.add(highScoresTable).minWidth(Gdx.graphics.getWidth()*0.4f).padBottom(10);
        UIBuilder.addButton(mainTable,"Back",true,new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((PolygonWars)Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(scores));
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

    /**
     * Returns the {@link HighscoreScreen#MAX_NO_OF_SCORES} highest scores found in the {@link HighscoreScreen#scores} list.
     *
     * @return Highest scores to be displayed
     */
    private List<Score> getHighscores() {
        return scores.stream()
                .sorted(Score::compareTo)
                .limit(MAX_NO_OF_SCORES)
                .toList();
    }
}
