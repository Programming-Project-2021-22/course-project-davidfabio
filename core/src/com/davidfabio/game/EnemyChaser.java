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


    private float scale = 60;

    private float xScaleCounter = 0;
    private float xScalingStopsAfter = 0.125f;
    private boolean xScaleIncreasing = true;

    private float[] verticesInitial = { 0, -0.5f,   // top
                                        -0.25f, 0,  // left
                                        0, 0.5f,    // bottom
                                        0.25f, 0 }; // right
    private float[] vertices;



    @Override public void init(float x, float y, float radius, float direction, Polarity polarity, float moveSpeed, float healthInitial) {
        super.init(x, y, radius, direction, polarity, moveSpeed, healthInitial);

        vertices = new float[verticesInitial.length];

        for (int i = 0; i < verticesInitial.length; i += 1) {
            verticesInitial[i] *= scale;
        }
    }



    @Override public void update(float deltaTime) {
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




    public void renderTEST(PolygonSpriteBatch polygonSpriteBatch) {
        if (!getIsActive())
            return;


        // TODO (David): implement spawning animation


        Texture texture;
        if (getIsSpawning()) {
            texture = GameScreen.getTextureYellow();
        }
        else if (getIsInHitState())
            texture = GameScreen.getTextureWhite();
        else if (getPolarity().getColor() == Settings.FIRST_COLOR)
            texture = GameScreen.getTextureRed();
        else
            texture = GameScreen.getTextureBlue();



        for (int i = 0; i < vertices.length; i += 1) {
            vertices[i] = verticesInitial[i];
            if (i % 2 == 0)
                vertices[i] += getX();
            else {
                vertices[i] += getY();
            }
        }

        // squishing/stretching
        vertices[2] -= xScaleCounter * scale;
        vertices[6] += xScaleCounter * scale;



        PolygonRegion polygonRegion = new PolygonRegion(new TextureRegion(texture),
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





    @Override public void render(ShapeRenderer shape, Color _color) {
        /*
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

        */
    }


}
