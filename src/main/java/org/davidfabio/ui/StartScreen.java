package org.davidfabio.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.davidfabio.game.Entity;
import org.davidfabio.game.PolygonShape;
import org.davidfabio.utils.Settings;

import java.util.ArrayList;


public class StartScreen extends ScreenAdapter {

    private Stage stage;
    /**
     * The viewport is required by LibGDX to render the Screen.
     */
    private Viewport viewport;


    private PolygonSpriteBatch polygonSpriteBatch;
    private ShapeRenderer shapeRenderer;
    private float letterScale = 350;
    private float letterDotRadius = 4;
    private float xOffsetBetweenLetters = 125;
    private float yFirstLine = Settings.windowHeight - 300;

    Entity p, o, l, y, g, n, w, a, r, s;
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

        initLetter('p', Color.GREEN);
        initLetter('o', Color.RED);
        initLetter('l', Color.CYAN);
        initLetter('y', Color.ORANGE);
        //initLetter('g', Color.BLUE);
        //initLetter('o', Color.YELLOW);
        //initLetter('n', Color.GRAY);

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


    private void initLetter(char character, Color color) {
        Entity letter = new Entity();
        letter.init(100 + letters.size() * xOffsetBetweenLetters, yFirstLine, letterScale, color);
        letter.setShape(PolygonShape.getLetterShape(character, letterScale));
        letters.add(letter);
    }


    private void renderLetter(Entity letter) {
        letter.getShape().renderOutline(shapeRenderer, letter.getColor());

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(letter.getColor());
        for (int i = 0; i < letter.getShape().getVertices().length; i += 2) {
            float x = letter.getShape().getVertices()[i];
            float y = letter.getShape().getVertices()[i + 1];
            //shapeRenderer.circle(x, y, letterDotRadius);
        }
        shapeRenderer.end();
    }
}
