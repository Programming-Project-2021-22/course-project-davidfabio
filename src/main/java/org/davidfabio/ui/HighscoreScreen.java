package org.davidfabio.ui;

import org.davidfabio.Duality;
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
        this.viewport = new ExtendViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        this.stage = new Stage(this.viewport);

        this.mainTable = new Table();
        this.mainTable.setFillParent(true);
        this.mainTable.setDebug(true);
        this.stage.addActor(this.mainTable);

        UIBuilder.loadSkin();
        UIBuilder.addTitleLabel(this.mainTable,"HIGHSCORES",true);
        Table highScoresTable = new Table();
        highScoresTable.setDebug(true);
        UIBuilder.addSubtitleLabel(highScoresTable,"Score",true);
        UIBuilder.addSubtitleLabel(highScoresTable,"Time played",false);
        for(Score score : scores) {
            long durationInSeconds = score.getDuration() / 1000;
            UIBuilder.addLabel(highScoresTable,"" + score.getPoints(),true);
            UIBuilder.addLabel(highScoresTable, String.format("%02d:%02d", durationInSeconds / 60, (durationInSeconds % 60)), false);
        }
        this.mainTable.row();
        this.mainTable.add(highScoresTable).minWidth(Gdx.graphics.getWidth()*0.4f).padBottom(10);
        UIBuilder.addButton(this.mainTable,"Back",true,new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Duality)Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(scores));
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
