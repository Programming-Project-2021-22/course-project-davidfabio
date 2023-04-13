package org.davidfabio.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.davidfabio.utils.Settings;

public class Level {
    public Level() {

    }

    public void render(ShapeRenderer renderer) {
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.LIGHT_GRAY);
        renderer.rect(0,0, Settings.windowWidth,Settings.windowHeight);
        renderer.end();
    }
}
