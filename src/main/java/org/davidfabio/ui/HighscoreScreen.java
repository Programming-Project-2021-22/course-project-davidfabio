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
    private Stage stage;
    private Viewport viewport;
    private Table mainTable;
    private ArrayList<Score> scores;

    public HighscoreScreen(ArrayList<Score> scores) {
        this.scores = scores;
    }

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

    private List<Score> getHighscores() {
        return scores.stream()
                .sorted(Score::compareTo)
                .limit(5)
                .toList();
    }
}
