package org.davidfabio.utils;


/**
 * Helper class for increasing/decreasing a float over time, e.g. for the pulsation effect of EnemyStar.
 */

public class Pulsation {

    private float pulsationSpeed;
    private float counter = 0;
    private float changeDirectionAfter;
    private boolean isGrowing = true;

    public float getCounter() { return counter; }


    public Pulsation(float pulsationSpeed, float changeDirectionAfter) {
        this.pulsationSpeed = pulsationSpeed;
        this.changeDirectionAfter = changeDirectionAfter;

    }

    public void update(float deltaTime) {
        if (isGrowing)
            counter += deltaTime * pulsationSpeed;
        else
            counter -= deltaTime * pulsationSpeed;

        if (counter > changeDirectionAfter || counter < -changeDirectionAfter)
            isGrowing = !isGrowing;
        counter = Math.min(counter, changeDirectionAfter);
        counter = Math.max(counter, -changeDirectionAfter);
    }

}
