package org.davidfabio.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.davidfabio.utils.Transform2D;
import java.util.Random;

/**
 * This class is used for various effects throughout the game.
 * For example, it is used when an Enemy dies to create an explosion.
 *
 * This class is used to aggregate multiple Particles in a single particle instance.
 * So when an enemy explodes and many particles should be spawned, a single Particle instance is created.
 * However, the single Particle instance will draw many small particles.
 */
public class Particle extends Entity {
    /**
     * Defines the visual type of the Particle, meaning if it should be drawn as a line or a circle.
     */
    public enum Type {
        LINE,
        CIRCLE
    }

    /**
     * Defines the visual type of the Particle, meaning if it should be drawn as a line or a circle.
     */
    private Type type;
    /**
     * Random instance which is used to randomly position and rotate the particle.
     */
    public static Random random = new Random();
    /**
     * Stores the x-positions for all the particles that are drawn with this Particle instance.
     */
    private float[] positionsX;
    /**
     * Stores the y-positions for all the particles that are drawn with this Particle instance.
     */
    private float[] positionsY;
    /**
     * Stores the direction in which each single particle should move.
     */
    private float[] moveAngles;
    /**
     * Stores the rotation angle of each single particle. The rotation velocity is defined in {@link Particle#rotationSpeed}.
     */
    private float[] rotationAngles;
    /**
     * Stores the radius for each single particle. This is only used when the particle type is CIRCLE.
     */
    private float[] radii;
    /**
     * Stores the velocity in which the particle should rotate.
     */
    private float rotationSpeed = 2f;

    /**
     * Initializes the Particle instance.
     *
     * @param x x-position in which the particles spawn
     * @param y y-position in which the particles spawn
     * @param scale scale of the particles, which is used for drawing
     * @param color color of the particles, which is used for drawing
     * @param shape Polygon-Shape for the particles, which is used for drawing
     * @param type type of the particle, indicating how they should be drawn
     */
    public void init(float x, float y, float scale, Color color, PolygonShape shape, Type type) {
        super.init(x, y, scale, color);
        this.type = type;

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

    /**
     * Updates the Particle instance's state.
     * @param deltaTime Delta by which the game loop updated
     */
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

    /**
     * Renders the Particle instance. This means that all particles that are contained in this instance are drawn.
     * @param polygonSpriteBatch rendering batch for anything that is drawn using Sprites
     * @param shapeRenderer rendering batch for anything that is drawn using Shapes
     */
    @Override
    public void render(PolygonSpriteBatch polygonSpriteBatch, ShapeRenderer shapeRenderer) {
        if (!getIsActive())
            return;

        if (type == Type.LINE)
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        else if (type == Type.CIRCLE) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            Gdx.gl.glEnable(GL20.GL_BLEND);
        }

        shapeRenderer.setColor(getColor());

        for (int i = 0; i < positionsX.length; i += 1) {
            float x1 = positionsX[i];
            float y1 = positionsY[i];
            float x2 = Transform2D.translateX(x1, rotationAngles[i], getScale());
            float y2 = Transform2D.translateY(y1, rotationAngles[i], getScale());

            if (type == Type.LINE)
                shapeRenderer.line(x1, y1, x2, y2);
            else if (type == Type.CIRCLE)
                shapeRenderer.circle(x2, y2, radii[i]);
        }

        shapeRenderer.end();
    }
}
