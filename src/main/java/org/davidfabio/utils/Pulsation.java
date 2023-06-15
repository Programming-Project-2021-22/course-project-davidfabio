package org.davidfabio.utils;

/**
 * Helper class for increasing/decreasing a float over time, e.g. for the pulsation effect of EnemyStar.
 */
public class Pulsation {
    /**
     * Pulsation speed
     */
    private float pulsationSpeedMultiplier;
    /**
     * Counter variable that indicates the "state" of the pulsation
     */
    private float counter = 0f;
    /**
     * Inversion point. If the counter was increasing up until this value, the counter starts decreasing. And vice versa.
     */
    private float changeDirectionAfter;
    /**
     * Indicates whether the counter is increased (isGrowing = true) or decreased.
     */
    private boolean isGrowing = true;

    /**
     * Returns the Pulsation "state".
     * @return current pulsation state
     */
    public float getCounter() {
        return counter;
    }

    /**
     * Constructor for a Pulsation instance. Initializes the {@link Pulsation#pulsationSpeedMultiplier} and {@link Pulsation#changeDirectionAfter} variables.
     * @param pulsationSpeedMultiplier new value for {@link Pulsation#pulsationSpeedMultiplier}
     * @param changeDirectionAfter new value for {@link Pulsation#changeDirectionAfter}
     */
    public Pulsation(float pulsationSpeedMultiplier, float changeDirectionAfter) {
        this.pulsationSpeedMultiplier = pulsationSpeedMultiplier;
        this.changeDirectionAfter = changeDirectionAfter;

    }

    /**
     * Updates the counter based on the properties of the Pulsation instance.
     *
     * @param deltaTime Delta by which the game loop updated
     */
    public void update(float deltaTime) {
        if (isGrowing)
            counter += deltaTime * pulsationSpeedMultiplier;
        else
            counter -= deltaTime * pulsationSpeedMultiplier;

        if (counter > changeDirectionAfter || counter < -changeDirectionAfter)
            isGrowing = !isGrowing;
        counter = Math.min(counter, changeDirectionAfter);
        counter = Math.max(counter, -changeDirectionAfter);
    }
}
