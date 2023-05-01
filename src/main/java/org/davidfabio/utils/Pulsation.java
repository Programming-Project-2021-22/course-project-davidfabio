package org.davidfabio.utils;


/**
 * Helper class for increasing/decreasing a float over time, i.e. for the pulsation effect of EnemyStar.
 */

public class Pulsation {

    private float pulsationSpeed;
    private float counter = 0;
    private float growingStopsAfter;
    private boolean isGrowing = true;

    public float getCounter() { return counter; }


    public Pulsation(float pulsationSpeed, float growingStopsAfter) {
        this.pulsationSpeed = pulsationSpeed;
        this.growingStopsAfter = growingStopsAfter;

    }

    public void update(float deltaTime) {
        if (isGrowing)
            counter += deltaTime * pulsationSpeed;
        else
            counter -= deltaTime * pulsationSpeed;

        if (counter > growingStopsAfter || counter < -growingStopsAfter)
            isGrowing = !isGrowing;
        counter = Math.min(counter, growingStopsAfter);
        counter = Math.max(counter, -growingStopsAfter);
    }

}
