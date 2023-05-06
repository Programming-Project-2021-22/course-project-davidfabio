package org.davidfabio.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.davidfabio.game.enemies.Enemy;
import org.davidfabio.utils.Transform2D;

import java.util.Arrays;

/**
 * This class is used to draw a PolygonShape. This is used in Enemies, Player, and other classes.
 * If this class is going to be used in a screen, the init method needs to be called IN THAT SCREEN!
 */
public class PolygonShape {
    private static Pixmap pixmap;
    private static Texture textureWhite;

    private float[] vertices, verticesInitial;
    private short[] triangles; // in counter-clockwise direction

    public float[] getVerticesInitial() {
        return Arrays.copyOf(verticesInitial, verticesInitial.length);
    }
    public float[] getVertices() { return vertices; }

    public static void init() {
        pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0xFFFFFFFF); // red, green, blue, alpha
        pixmap.fill();
        textureWhite = new Texture(pixmap);
    }

    /**
     * Polygon circle constructor
     * @param triangleCount
     * @param scale
     */
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
        polygonSpriteBatch.begin();
        PolygonRegion polygonRegion = new PolygonRegion(new TextureRegion(textureWhite), vertices, triangles);
        PolygonSprite polygonSprite = new PolygonSprite(polygonRegion);
        polygonSprite.setColor(color);
        polygonSprite.draw(polygonSpriteBatch);
        polygonSpriteBatch.end();
    }

    public void renderOutline(ShapeRenderer shapeRenderer, Color color) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(color);
        Gdx.gl.glEnable(GL20.GL_BLEND); // needed for transparencies
        for (int i = 0; i < vertices.length - 2; i += 2) {
            float x1 = vertices[i];
            float y1 = vertices[i + 1];
            float x2 = vertices[i + 2];
            float y2 = vertices[i + 3];
            shapeRenderer.line(x1, y1, x2, y2);
        }

        float x1 = vertices[vertices.length - 2];
        float y1 = vertices[vertices.length - 1];
        float x2 = vertices[0];
        float y2 = vertices[1];

        shapeRenderer.line(x1, y1, x2, y2);
        shapeRenderer.end();
    }


    public static PolygonShape getPlayerShape(float scale) {
        return new PolygonShape(12, scale);
    }

    public static PolygonShape getPlayerArrowShape(float scale) {
        float[] vertices = new float[] {
                0, -0.5f,
                0.5f, 0,
                0, 0.5f
        };
        short[] triangles = new short[] {
                0, 1, 2
        };
        return new PolygonShape(vertices, triangles, scale);
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

            case STAR:
                vertices = new float[] {
                        0.25f, 0,
                        0.375f, -0.25f,
                        0.125f, -0.25f,
                        0, -0.5f,
                        -0.125f, -0.25f,
                        -0.375f, -0.25f,
                        -0.25f, 0,
                        -0.375f, 0.25f,
                        -0.125f, 0.25f,
                        0, 0.5f,
                        0.125f, 0.25f,
                        0.375f, 0.25f,
                };
                triangles = new short[] {
                        0, 1, 2,
                        2, 3, 4,
                        4, 5, 6,
                        6, 7, 8,
                        8, 9, 10,
                        10, 11, 0,
                        0, 2, 10,
                        10, 2, 8,
                        2, 4, 8,
                        8, 4, 6
                };
                shape = new PolygonShape(vertices, triangles, scale);
                break;
        }

        return shape;
    }

}
