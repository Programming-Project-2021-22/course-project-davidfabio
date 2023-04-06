package com.davidfabio.game.core;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Arrays;


public class PolygonShape {
    private float[] vertices, verticesInitial;
    private short[] triangles; // in counter-clockwise direction

    public float[] getVerticesInitial() {
        return Arrays.copyOf(verticesInitial, verticesInitial.length);
    }

    public PolygonShape(float[] vertices, short[] triangles, float scale) {
        this.vertices = vertices;
        this.triangles = triangles;

        verticesInitial = new float[vertices.length];
        for (int i = 0; i < vertices.length; i += 1)
            verticesInitial[i] = vertices[i] * scale;
    }

    public void render(PolygonSpriteBatch polygonSpriteBatch, Entity entity) {
        if (!entity.getIsActive())
            return;

        for (int i = 0; i < vertices.length; i += 1) {
            vertices[i] = verticesInitial[i];

            if (i % 2 == 0)
                vertices[i] += entity.getX();
            else
                vertices[i] += entity.getY();
        }

        PolygonRegion polygonRegion = new PolygonRegion(new TextureRegion(entity.getTexture()), vertices, triangles);
        PolygonSprite polygonSprite = new PolygonSprite(polygonRegion);
        polygonSprite.setOrigin(entity.getX(), entity.getY());
        polygonSprite.rotate(Transform2D.radiansToDegrees(entity.getAngle()));
        polygonSprite.draw(polygonSpriteBatch);
    }

    public void render(PolygonSpriteBatch polygonSpriteBatch, Entity entity, float[] newVertices) {
        if (!entity.getIsActive())
            return;

        for (int i = 0; i < newVertices.length; i += 1) {
            if (i % 2 == 0)
                newVertices[i] += entity.getX();
            else
                newVertices[i] += entity.getY();
        }

        PolygonRegion polygonRegion = new PolygonRegion(new TextureRegion(entity.getTexture()), newVertices, triangles);
        PolygonSprite polygonSprite = new PolygonSprite(polygonRegion);
        polygonSprite.setOrigin(entity.getX(), entity.getY());
        polygonSprite.rotate(Transform2D.radiansToDegrees(entity.getAngle()));
        polygonSprite.draw(polygonSpriteBatch);
    }

    public void render(PolygonSpriteBatch polygonSpriteBatch, float x, float y, float angle, Texture texture) {
        for (int i = 0; i < vertices.length; i += 1) {
            vertices[i] = verticesInitial[i];

            if (i % 2 == 0)
                vertices[i] += x;
            else
                vertices[i] += y;
        }

        PolygonRegion polygonRegion = new PolygonRegion(new TextureRegion(texture), vertices, triangles);
        PolygonSprite polygonSprite = new PolygonSprite(polygonRegion);
        polygonSprite.setOrigin(x, y);
        polygonSprite.rotate(angle);
        polygonSprite.draw(polygonSpriteBatch);
    }
}
