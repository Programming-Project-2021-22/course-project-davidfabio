package org.davidfabio.utils;

/**
 * Helper class for increasing/decreasing a float over time, e.g. for the pulsation effect of EnemyStar.
 */
public class Pulsation {

    private float pulsationSpeedMultiplier;
    private float counter = 0f;
    private float changeDirectionAfter;
    private boolean isGrowing = true;

    public float getCounter() { return counter; }

    public Pulsation(float pulsationSpeedMultiplier, float changeDirectionAfter) {
        this.pulsationSpeedMultiplier = pulsationSpeedMultiplier;
        this.changeDirectionAfter = changeDirectionAfter;

    }

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
