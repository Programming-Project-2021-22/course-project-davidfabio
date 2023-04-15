package org.davidfabio.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Level {
    private final float width;
    private final float height;

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
