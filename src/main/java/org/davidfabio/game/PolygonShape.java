package org.davidfabio.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.davidfabio.ui.GameScreen;
import org.davidfabio.utils.Transform2D;

import java.util.Arrays;


public class PolygonShape {
    private float[] vertices, verticesInitial;
    private short[] triangles; // in counter-clockwise direction

    public float[] getVerticesInitial() {
        return Arrays.copyOf(verticesInitial, verticesInitial.length);
    }


    // polygon circle constructor
    public PolygonShape(int triangleCount, float scale) {
        verticesInitial = new float[(triangleCount + 1) * 2];
        vertices = new float[verticesInitial.length];

        float angleDelta = (float)(Math.PI / triangleCount);
        for (int i = 0; i < verticesInitial.length; i += 2) {
            float x = Transform2D.translateX(0, (i * angleDelta), scale / 2);
            float y = Transform2D.translateY(0, (i * angleDelta), scale / 2);
            verticesInitial[i] = x;
            verticesInitial[i + 1] = y;
        }

        triangles = new short[triangleCount * 3];
        for (int i = 0; i < triangleCount; i += 1) {
            triangles[i * 3] = 0;
            triangles[(i * 3) + 1] = (short)(i + 1);
            triangles[(i * 3) + 2] = (short)(i + 2);
        }
    }


    public PolygonShape(float[] vertices, short[] triangles, float scale) {
        this.vertices = vertices;
        this.triangles = triangles;

        verticesInitial = new float[vertices.length];
        for (int i = 0; i < vertices.length; i += 1)
            verticesInitial[i] = vertices[i] * scale;
    }


    public static PolygonShape getEnemyShape(Enemy.Type enemyType, float scale) {
        PolygonShape shape = null;
        float[] vertices;
        short[] triangles;

        switch (enemyType) {
            case BUBBLE:
                shape = new PolygonShape(64, scale);
                break;

            case CHASER: {
                vertices = new float[] {
                        0, -0.5f,
                        -0.25f, 0,
                        0, 0.5f,
                        0.25f, 0
                };
                triangles = new short[] {
                        0, 1, 2,
                        2, 3, 0
                };
                shape = new PolygonShape(vertices, triangles, scale);
                break;
            }

            case KAMIKAZE: {
                vertices = new float[] {
                        0.5f, 0,
                        -0.5f, -0.5f,
                        -0.25f, 0,
                        -0.5f, 0.5f
                };
                triangles = new short[] {
                        0, 1, 2,
                        2, 3, 0
                };
                shape = new PolygonShape(vertices, triangles, scale);
                break;
            }

            case SPINNER: {
                vertices = new float[] {
                        0.5f, 0,
                        0, -0.125f,
                        -0.5f, 0,
                        0, 0.125f
                };
                triangles = new short[] {
                        0, 1, 2,
                        2, 3, 0
                };
                shape = new PolygonShape(vertices, triangles, scale);
                break;
            }

            case TURRET:
                shape = new PolygonShape(4, scale);
                break;
        }

        return shape;
    }



    public void render(PolygonSpriteBatch polygonSpriteBatch, Entity entity, Color color) {
        if (!entity.getIsActive())
            return;

        for (int i = 0; i < verticesInitial.length; i += 1) {
            vertices[i] = verticesInitial[i];

            if (i % 2 == 0)
                vertices[i] += entity.getX();
            else
                vertices[i] += entity.getY();
        }

        PolygonRegion polygonRegion = new PolygonRegion(new TextureRegion(GameScreen.getTextureWhite()), vertices, triangles);
        PolygonSprite polygonSprite = new PolygonSprite(polygonRegion);
        polygonSprite.setColor(color);
        polygonSprite.setOrigin(entity.getX(), entity.getY());
        polygonSprite.rotate(Transform2D.radiansToDegrees(entity.getAngle()));
        polygonSprite.draw(polygonSpriteBatch);
    }

    public void render(PolygonSpriteBatch polygonSpriteBatch, Entity entity, float[] newVertices, Color color) {
        if (!entity.getIsActive())
            return;

        for (int i = 0; i < newVertices.length; i += 1) {
            if (i % 2 == 0)
                newVertices[i] += entity.getX();
            else
                newVertices[i] += entity.getY();
        }

        PolygonRegion polygonRegion = new PolygonRegion(new TextureRegion(GameScreen.getTextureWhite()), newVertices, triangles);
        PolygonSprite polygonSprite = new PolygonSprite(polygonRegion);
        polygonSprite.setColor(color);
        polygonSprite.setOrigin(entity.getX(), entity.getY());
        polygonSprite.rotate(Transform2D.radiansToDegrees(entity.getAngle()));
        polygonSprite.draw(polygonSpriteBatch);
    }

    public void render(PolygonSpriteBatch polygonSpriteBatch, float x, float y, float angle, Color color) {
        for (int i = 0; i < vertices.length; i += 1) {
            vertices[i] = verticesInitial[i];

            if (i % 2 == 0)
                vertices[i] += x;
            else
                vertices[i] += y;
        }

        PolygonRegion polygonRegion = new PolygonRegion(new TextureRegion(GameScreen.getTextureWhite()), vertices, triangles);
        PolygonSprite polygonSprite = new PolygonSprite(polygonRegion);
        polygonSprite.setColor(color);
        polygonSprite.setOrigin(x, y);
        polygonSprite.rotate(angle);
        polygonSprite.draw(polygonSpriteBatch);
    }
}
