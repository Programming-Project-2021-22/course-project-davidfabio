package org.davidfabio.game;

// TODO: remove this class and use libgdx's in-built collision detection methods


public class Collision {

    public static boolean circleCircle(float x1, float y1, float radius1, float x2, float y2, float radius2) {

        // NOTE (David): ugly temp hack, but for now it will do (this method will not be used in the future anyway)
        radius1 /= 2;
        radius2 /= 2;

        float distanceX = x1 - x2;
        float distanceY = y1 - y2;
        float distance = (float)Math.sqrt((distanceX * distanceX) + (distanceY * distanceY));

        return distance <= radius1 + radius2;
    }

}
