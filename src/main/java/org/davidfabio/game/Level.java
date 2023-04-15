package org.davidfabio.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.davidfabio.utils.Settings;

public class Level {
    private float width;
    private float height;

    public Level(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public void render(ShapeRenderer renderer) {
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
