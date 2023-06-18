package org.davidfabio.game.enemies;

import com.badlogic.gdx.graphics.Color;
import org.davidfabio.game.World;
import org.davidfabio.utils.Pulsation;

/**
 * This Enemy "chases" after the Player.
 */
public class EnemyChaser extends Enemy {
    /**
     * This pulsation is used to change the shape of the EnemyChaser.
     * It defines the Width of the PolygonShape.
     */
    private Pulsation pulsation;

    /**
     * Constructor of the EnemyChaser-class. It sets the EnemyType to CHASER.
     */
    public EnemyChaser() {
        setType(Type.CHASER);
    }

    /**
     * Initializes the EnemyChaser using {@link Enemy#init(float, float, float, float, int, Color)}. It also initializes the
     * {@link EnemyChaser#pulsation}.
     *
     * @param x x-position for the Enemy
     * @param y y-position for the Enemy
     * @param scale size for the Enemy (or the shape)
     * @param moveSpeed Enemy velocity
     * @param newInitialHealth initial health, if 0 {@link Enemy#initialHealth} is used
     * @param color color for the display of the Enemy
     */
    @Override
    public void init(float x, float y, float scale, float moveSpeed, int newInitialHealth, Color color) {
        super.init(x, y, scale, moveSpeed, newInitialHealth, color);

        pulsation = new Pulsation(0.33f, 0.1f);
    }

    /**
     * Updates the EnemyChaser state and therefore updates the {@link EnemyChaser#pulsation}.
     * It also updates the angle in which it moves, in order to chase the player.
     *
     * @param deltaTime Delta by which the game loop updated
     * @param world World object reference used for side-effects
     */
    @Override
    public void update(float deltaTime, World world) {
        super.update(deltaTime, world);

        if (!getIsActive())
            return;
        if (getIsSpawning())
            return;

        pulsation.update(deltaTime);

        getShape().resetPosition();
        getShape().getVertices()[2] -= pulsation.getCounter() * getScale();
        getShape().getVertices()[6] += pulsation.getCounter() * getScale();
        getShape().rotate(getAngle());
        getShape().translatePosition(this);

        float angle = getAngleTowards(world.getPlayer().getX(), world.getPlayer().getY());
        setAngle(angle);
        moveTowards(world.getPlayer().getX(), world.getPlayer().getY(), deltaTime);
    }
}
