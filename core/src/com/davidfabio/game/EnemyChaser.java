package com.davidfabio.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Set;

public class EnemyChaser extends Enemy {
    private float xScaleCounter = 0;
    private float xScalingStopsAfter = 0.125f;
    private boolean xScaleIncreasing = true;


    @Override public void init(float x, float y, float scale, float direction, Polarity polarity, float moveSpeed, float healthInitial) {
        super.init(x, y, scale, direction, polarity, moveSpeed, healthInitial);

        verticesInitial = new float[] {
                0, -0.5f,  // top
                -0.25f, 0, // left
                0, 0.5f,   // bottom
                0.25f, 0   // right
        };

        for (int i = 0; i < verticesInitial.length; i += 1) {
            verticesInitial[i] *= getScale();
        }

        vertices = new float[verticesInitial.length];

        triangles = new short[] {
                0, 1, 2,
                2, 3, 0
        };
    }



    //@Override public void update(float deltaTime) {
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (!getIsActive())
            return;
        if (getIsSpawning())
            return;

        float angle = getAngleTowards(GameScreen.player.getX(), GameScreen.player.getY());
        setDirection(radiansToDegrees(angle));


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





    @Override public void render(PolygonSpriteBatch polygonSpriteBatch) {
        if (!getIsActive())
            return;

        for (int i = 0; i < vertices.length; i += 1) {
            vertices[i] = verticesInitial[i];
            if (i % 2 == 0)
                vertices[i] += getX();
            else {
                vertices[i] += getY();
            }
        }

        // NOTE (David): these 2 lines are the only reason we can't use the entity render method; maybe find some better solution than copying the whole method?
        vertices[2] -= xScaleCounter * getScale();
        vertices[6] += xScaleCounter * getScale();


        PolygonRegion polygonRegion = new PolygonRegion(new TextureRegion(currentTexture),
                vertices,
                new short[] { // specify triangles in counter-clockwise direction
                        0, 1, 2, // triangle 1
                        2, 3, 0  // triangle 2
                });
        PolygonSprite polygonSprite = new PolygonSprite(polygonRegion);
        polygonSprite.setOrigin(getX(), getY());
        polygonSprite.rotate(getDirection());
        polygonSprite.draw(polygonSpriteBatch);
    }


}
