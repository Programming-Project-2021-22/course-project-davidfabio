package org.davidfabio.game.enemies;

import com.badlogic.gdx.graphics.Color;
import org.davidfabio.game.*;
import org.davidfabio.utils.Pulsation;

/**
 * Doesn't move.
 * When destroyed it grows larger (until it fills the whole level) and kills all enemies it touches.
 */
public class EnemyStar extends Enemy {
    /**
     * This pulsation is used for the deflation and inflation of the Star-Shape.
     */
    private Pulsation pulsation;
    /**
     * This counter is used when the Enemy dies and blows up. It is used to inflate the Enemy shape.
     */
    private float blowingUpCounter;
    /**
     * This is true if the Enemy is blowing up, or false if it is not.
     */
    private boolean isBlowingUp = false;

    /**
     * @return true if the Enemy is blowing up, or false if not
     */
    public boolean getIsBlowingUp() {
        return isBlowingUp;
    }

    /**
     * Constructor of the EnemyStar-class. It sets the EnemyType to STAR.
     */
    public EnemyStar() {
        setType(Type.STAR);
    }

    /**
     * Initializes the Enemy. It initializes the {@link EnemyStar#pulsation} and the {@link EnemyStar#isBlowingUp} to false.
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
        isBlowingUp = false;
        pulsation = new Pulsation(1, 0.2f);
    }

    /**
     * Updates the EnemyStar state. If it is not blowing up, it simply "pulsates" the star-shape.
     * If it is blowing up, the color changes to red, then it inflates using {@link EnemyStar#blowingUpCounter} until it
     * fills the Level. Then it dies.
     * @param deltaTime Delta by which the game loop updated
     * @param world World object reference used for side-effects
     */
    public void update(float deltaTime, World world) {
        super.update(deltaTime, world);

        if (!getIsActive())
            return;
        if (getIsSpawning())
            return;

        float newScale = getScale();

        if (!isBlowingUp) {
            pulsation.update(deltaTime);
            newScale += pulsation.getCounter() * getScale();
            setTransparency(pulsation.getCounter() + 0.5f);
        } else {
            setColor(new Color(1, 0, 0, 0.5f));
            blowingUpCounter += deltaTime * blowingUpCounter * 5;
            newScale += blowingUpCounter * getScale();
            if (Collision.polygonFullyOutsideLevel(this, world)) {
                setHealth(0);
                setIsActive(false);
            }
        }

        setShape(PolygonShape.getEnemyShape(getType(), newScale));
        getShape().resetPosition();
        getShape().translatePosition(this);
    }

    /**
     * Destroys the EnemyStar. It plays a special sound ({@link Sounds#playExplosionEnemyStarSfx()}).
     * @param world World object reference, used for side-effects
     */
    @Override
    public void destroy(World world) {
        isBlowingUp = true;
        blowingUpCounter = Math.abs(pulsation.getCounter());
        Sounds.playExplosionEnemyStarSfx();
    }
}
