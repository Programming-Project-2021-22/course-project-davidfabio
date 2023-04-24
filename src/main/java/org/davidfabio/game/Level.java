package org.davidfabio.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.davidfabio.utils.Settings;
import org.davidfabio.utils.Transform2D;

public class Level {
    private float width;
    private float height;

    private float[] starsX, starsY, starsRadii;
    private Color[] starsColors;
    private int starsCount = 256;

    public Level(float width, float height) {
        this.width = width;
        this.height = height;

        starsX = new float[starsCount];
        starsY = new float[starsCount];
        starsRadii = new float[starsCount];
        starsColors = new Color[starsCount];

        for (int i = 0; i < starsCount; i += 1) {
            starsX[i] = Transform2D.getRandomX(this);
            starsY[i] = Transform2D.getRandomY(this);
            starsRadii[i] = (float)Math.random() + 0.25f;
            starsColors[i] = new Color(0, 0, (float)Math.random(), (float)Math.random());
        }
    }

    public void render(ShapeRenderer renderer) {
        // background stars
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < starsCount; i += 1) {
            renderer.setColor(starsColors[i]);
            renderer.circle(starsX[i], starsY[i], starsRadii[i]);
        }
        renderer.end();

        // level border
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.LIGHT_GRAY);
        renderer.rect(0,0,this.width,this.height);
        renderer.end();
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }
}
