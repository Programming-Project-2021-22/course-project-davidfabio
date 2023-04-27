package org.davidfabio.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.davidfabio.utils.Settings;
import org.davidfabio.utils.Transform2D;

public class Level {
    private float width;
    private float height;

    private Color backgroundColor = Color.BLACK;

    public float getHeight() {
        return height;
    }
    public float getWidth() {
        return width;
    }


    public Level(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public void render(ShapeRenderer renderer) {
        // background color (outside of level area)
        // NOTE (David): this makes sure that enemies/bullets that are partially outside the level are not visible (because we paint this background over them)
        float margin = 1000;
        float twoMargin = margin * 2;
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(backgroundColor);
        renderer.rect(width, -margin, width, height + twoMargin); // right
        renderer.rect(-margin, -margin, margin, height + twoMargin); // left
        renderer.rect(-margin, -margin, width + twoMargin, margin); // top
        renderer.rect(-margin, height, width + twoMargin, margin); // bottom
        renderer.end();

        // level border
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.LIGHT_GRAY);
        renderer.rect(0, 0, width, height);
        renderer.end();
    }

}
