package com.davidfabio.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class EnemyChaser extends Enemy {


    private float width = 60;

    private float widthCounter = 0;
    private float widthStopsIncreasingAfter = 0.125f;
    private boolean widthIncreasing = true;


    @Override public void update(float deltaTime) {
        super.update(deltaTime);

        if (!getIsActive())
            return;
        if (getIsSpawning())
            return;


        // stretching/squashing width
        float stretchSpeed = deltaTime / 3;
        if (widthIncreasing)
            widthCounter += stretchSpeed;
        else
            widthCounter -= stretchSpeed;

        if (widthCounter > widthStopsIncreasingAfter || widthCounter < -widthStopsIncreasingAfter)
            widthIncreasing = !widthIncreasing;

        widthCounter = Math.min(widthCounter, widthStopsIncreasingAfter);
        widthCounter = Math.max(widthCounter, -widthStopsIncreasingAfter);


        moveTowards(Game.player.getX(), Game.player.getY(), deltaTime);
    }



    @Override public void render(ShapeRenderer shape, Color _color) {
        if (!getIsActive())
            return;


        // TODO (David): implement spawning animation
        if (getIsSpawning()) {
            _color = Color.WHITE; // quick and dirty temp solution
        }


        // TODO (David): shape changed from circle to polygon; collision detection needs to be updated!

        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(_color);

        float[] vertices = { 0, -0.5f,                  // top
                             0.25f + widthCounter, 0,   // right
                             0, 0.5f,                   // bottom
                            -0.25f - widthCounter, 0 }; // left

        for (int i = 0; i < vertices.length; i += 1) {
            vertices[i] *= width;
            if (i % 2 == 0)
                vertices[i] += getX();
            else
                vertices[i] += getY();
        }

        shape.polygon(vertices);
        shape.end();
    }


}
