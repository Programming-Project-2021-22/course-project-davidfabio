package org.davidfabio.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
import org.davidfabio.game.Entity;
import org.davidfabio.game.PolygonShape;
import org.davidfabio.game.Score;
import org.davidfabio.utils.Settings;

import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.List;


public class StartScreen extends ScreenAdapter {

    private Stage stage;
    /**
     * The viewport is required by LibGDX to render the Screen.
     */
    private Viewport viewport;


    private PolygonSpriteBatch polygonSpriteBatch;
    private ShapeRenderer shapeRenderer;
    private float circleRadius = 4;

    Entity p;
    ArrayList<Entity> letters;


    @Override
    public void show() {
        viewport = new ExtendViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        stage = new Stage(viewport);

        Gdx.input.setInputProcessor(stage);

        PolygonShape.init();
        polygonSpriteBatch = new PolygonSpriteBatch();
        shapeRenderer = new ShapeRenderer();

        letters = new ArrayList<Entity>();

        p = new Entity();
        p.init(100, Settings.windowHeight - 100, 120, Color.RED);
        p.setShape(PolygonShape.getLetterShape('p', p.getScale()));
        letters.add(p);
    }

    /**
     * This method draws the actual Screen.
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        for (Entity letter : letters) {
            letter.getShape().resetPosition();
            letter.getShape().translatePosition(letter);
            renderLetter(letter);
        }

        stage.draw();
    }



    private void renderLetter(Entity letter) {
        letter.getShape().renderOutline(shapeRenderer, letter.getColor());

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(letter.getColor());
        for (int i = 0; i < letter.getShape().getVertices().length; i += 2) {
            float x = letter.getShape().getVertices()[i];
            float y = letter.getShape().getVertices()[i + 1];
            shapeRenderer.circle(x, y, circleRadius);
        }
        shapeRenderer.end();
    }
}
