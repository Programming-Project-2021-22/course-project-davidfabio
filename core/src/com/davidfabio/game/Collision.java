package com.davidfabio.game;

public class Collision {

    public static boolean circleCircle(float x1, float y1, float radius1, float x2, float y2, float radius2) {
        float distanceX = x1 - x2;
        float distanceY = y1 - y2;
        float distance = (float)Math.sqrt((distanceX * distanceX) + (distanceY * distanceY));

        if (distance <= radius1 + radius2)
            return true;

        return false;
    }

}
