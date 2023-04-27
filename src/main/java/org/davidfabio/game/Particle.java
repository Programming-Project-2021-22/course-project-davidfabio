package org.davidfabio.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.davidfabio.utils.Transform2D;
import java.util.Random;

public class Particle extends Entity {

    public static Random random = new Random();

    private float[] positionsX, positionsY, moveAngles, rotationAngles;
    private float lifespanInitial = 0.33f;
    private float lifespan;
    private float rotationSpeed = 2f;


    public void init(float x, float y, float scale, Color color, PolygonShape shape) {
        super.init(x, y, scale, color);

        setMoveSpeed(120);
        lifespan = lifespanInitial;

        setShape(shape);
        shape.resetPosition();
        shape.translatePosition(getX(), getY());

        float[] vertices = shape.getVertices();
        positionsX = new float[vertices.length / 2];
        positionsY = new float[vertices.length / 2];

        int counter = 0;
        for (int i = 0; i < vertices.length; i += 2) {
            positionsX[counter] = vertices[i];
            positionsY[counter] = vertices[i + 1];
            counter += 1;
        }

        moveAngles = new float[vertices.length / 2];
        rotationAngles = new float[vertices.length / 2];
        for (int i = 0; i < moveAngles.length; i += 1) {
            moveAngles[i] = getAngleTowards(positionsX[i], positionsY[i]);
            rotationAngles[i] = random.nextFloat(0, (float)Math.PI * 2);
        }
    }


    public void update(float deltaTime) {
        if (!getIsActive())
            return;

        lifespan -= deltaTime;
        if (lifespan < 0)
            setIsActive(false);

        for (int i = 0; i < positionsX.length; i += 1) {
            float speed = getMoveSpeed() * deltaTime;
            float deltaX = (float)Math.cos(moveAngles[i]) * speed;
            float deltaY = (float)Math.sin(moveAngles[i]) * speed;
            float xOld = positionsX[i];
            float yOld = positionsY[i];
            positionsX[i] = xOld + deltaX;
            positionsY[i] = yOld + deltaY;

            rotationAngles[i] += rotationSpeed * deltaTime;
        }
    }

    @Override
    public void render(PolygonSpriteBatch polygonSpriteBatch, ShapeRenderer shapeRenderer) {
        if (!getIsActive())
            return;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(getColor());
        for (int i = 0; i < positionsX.length; i += 1) {
            float x1 = positionsX[i];
            float y1 = positionsY[i];
            float x2 = Transform2D.translateX(x1, rotationAngles[i], getScale());
            float y2 = Transform2D.translateY(y1, rotationAngles[i], getScale());
            shapeRenderer.line(x1, y1, x2, y2);
        }
        shapeRenderer.end();
    }

}
