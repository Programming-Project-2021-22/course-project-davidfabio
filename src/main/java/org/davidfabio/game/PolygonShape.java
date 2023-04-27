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
    public float[] getVertices() { return vertices; }

    public void setVertices(float[] newVertices) {
        for (int i = 0; i < vertices.length; i += 1)
            vertices[i] = newVertices[i];
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


    public void resetPosition() {
        for (int i = 0; i < verticesInitial.length; i += 2) {
            vertices[i] = verticesInitial[i];
            vertices[i + 1] = verticesInitial[i + 1];
        }
    }

    public void translatePosition(Entity entity) {
        for (int i = 0; i < vertices.length; i += 2) {
            vertices[i] += entity.getX();
            vertices[i + 1] += entity.getY();
        }
    }

    public void translatePosition(float x, float y) {
        for (int i = 0; i < verticesInitial.length; i += 2) {
            vertices[i] += x;
            vertices[i + 1] += y;
        }
    }

    public void rotate(float angle) {
        for (int i = 0; i < vertices.length; i += 2) {
            float xOld = vertices[i];
            float yOld = vertices[i + 1];
            float x = (float)(xOld * Math.cos(angle) - yOld * Math.sin(angle));
            float y = (float)(yOld * Math.cos(angle) + xOld * Math.sin(angle));
            vertices[i] = x;
            vertices[i + 1] = y;
        }
    }

    public void render(PolygonSpriteBatch polygonSpriteBatch, Color color) {
        PolygonRegion polygonRegion = new PolygonRegion(new TextureRegion(GameScreen.getTextureWhite()), vertices, triangles);
        PolygonSprite polygonSprite = new PolygonSprite(polygonRegion);
        polygonSprite.setColor(color);
        polygonSprite.draw(polygonSpriteBatch);
    }


    public static PolygonShape getPlayerBulletShape(float scale) {
        float[] vertices = new float[] {
                -0.5f, -0.25f,
                -0.5f, 0.25f,
                0.5f, 0
        };
        short[] triangles = new short[] {
                0, 1, 2
        };
        return new PolygonShape(vertices, triangles, scale);
    }

    public static PolygonShape getEnemyBulletShape(float scale) {
        return new PolygonShape(16, scale);
    }

    public static PolygonShape getEnemyShape(Enemy.Type enemyType, float scale) {
        PolygonShape shape = null;
        float[] vertices;
        short[] triangles;

        switch (enemyType) {
            case BUBBLE:
                shape = new PolygonShape(12, scale);
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

            case TURRET:
                shape = new PolygonShape(4, scale);
                break;
        }

        return shape;
    }

}
