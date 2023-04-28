package org.davidfabio.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.davidfabio.utils.Transform2D;
import java.util.Random;

public class Particle extends Entity {

    public static Random random = new Random();

    private float[] positionsX, positionsY, moveAngles, rotationAngles, radii;
    private float rotationSpeed = 2f;


    // TODO: currently it's impossible to spawn less than 3 particles


    public void init(float x, float y, float scale, Color color, PolygonShape shape) {
        super.init(x, y, scale, color);

        setMoveSpeed(200);

        setShape(shape);
        shape.resetPosition();
        shape.translatePosition(getX(), getY());

        float[] vertices = shape.getVertices();
        int particleCount = vertices.length / 2;
        positionsX = new float[particleCount];
        positionsY = new float[particleCount];
        radii = new float[particleCount];

        int counter = 0;
        for (int i = 0; i < vertices.length; i += 2) {
            positionsX[counter] = vertices[i];
            positionsY[counter] = vertices[i + 1];
            counter += 1;
        }

        moveAngles = new float[particleCount];
        rotationAngles = new float[particleCount];
        for (int i = 0; i < particleCount; i += 1) {
            moveAngles[i] = getAngleTowards(positionsX[i], positionsY[i]);
            rotationAngles[i] = random.nextFloat(0, (float)Math.PI * 2);
            radii[i] = (getScale() + ((float)Math.random() * getScale())) / 4;
        }

    }


    public void update(float deltaTime) {
        if (!getIsActive())
            return;

        float transparency = getColor().a - (deltaTime * 2);
        setTransparency(transparency);

        if (transparency < 0)
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

        //shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(getColor());
        Gdx.gl.glEnable(GL20.GL_BLEND);
        for (int i = 0; i < positionsX.length; i += 1) {
            float x1 = positionsX[i];
            float y1 = positionsY[i];
            float x2 = Transform2D.translateX(x1, rotationAngles[i], getScale());
            float y2 = Transform2D.translateY(y1, rotationAngles[i], getScale());
            //shapeRenderer.line(x1, y1, x2, y2);
            shapeRenderer.circle(x2, y2, radii[i]);
        }
        shapeRenderer.end();
    }

}
