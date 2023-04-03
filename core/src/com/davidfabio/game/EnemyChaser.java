package com.davidfabio.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class EnemyChaser extends Enemy {


    private float scale = 60;

    private float xScaleCounter = 0;
    private float xScalingStopsAfter = 0.125f;
    private boolean xScaleIncreasing = true;


    @Override public void update(float deltaTime) {
        super.update(deltaTime);

        if (!getIsActive())
            return;
        if (getIsSpawning())
            return;


        // stretching/squashing width
        float stretchSpeed = deltaTime / 3;
        if (xScaleIncreasing)
            xScaleCounter += stretchSpeed;
        else
            xScaleCounter -= stretchSpeed;

        if (xScaleCounter > xScalingStopsAfter || xScaleCounter < -xScalingStopsAfter)
            xScaleIncreasing = !xScaleIncreasing;

        xScaleCounter = Math.min(xScaleCounter, xScalingStopsAfter);
        xScaleCounter = Math.max(xScaleCounter, -xScalingStopsAfter);


        moveTowards(GameScreen.player.getX(), GameScreen.player.getY(), deltaTime);
    }



    @Override public void render(ShapeRenderer shape, Color _color) {
        if (!getIsActive())
            return;


        // TODO (David): implement spawning animation
        if (getIsSpawning()) {
            _color = Color.WHITE; // quick and dirty temp solution
        }

        if (getIsInHitState())
            _color = Color.WHITE;



        // TODO (David): shape changed from circle to polygon; collision detection needs to be updated!

        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(_color);

        float[] vertices = { 0, -0.5f,                  // top
                             0.25f + xScaleCounter, 0,   // right
                             0, 0.5f,                   // bottom
                            -0.25f - xScaleCounter, 0 }; // left

        for (int i = 0; i < vertices.length; i += 1) {
            vertices[i] *= scale;
            if (i % 2 == 0)
                vertices[i] += getX();
            else
                vertices[i] += getY();
        }

        shape.polygon(vertices);
        shape.end();
    }


}
