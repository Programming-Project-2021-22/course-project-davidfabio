package org.davidfabio.utils;

import org.davidfabio.game.Level;

/**
 * This class provides utility functions for manipulating coordinates and angles in 2D space.
 */
public class Transform2D {
    public static float translateX(float x, float angle, float length) {
        return x + (float)Math.cos(angle) * length;
    }
    public static float translateY(float y, float angle, float length) {
        return y + (float)Math.sin(angle) * length;
    }
    public static float radiansToDegrees(float angleRadians) {
        return angleRadians * (float)(180 / Math.PI);
    }
    public static float degreesToRadians(float angleDegrees) {
        return angleDegrees * (float)(Math.PI / 180);
    }
    public static float getDistance(float x1, float y1, float x2, float y2) {
        float distanceX = x1 - x2;
        float distanceY = y1 - y2;
        return (float)Math.sqrt((distanceX * distanceX) + (distanceY * distanceY));
    }
    public static float getAngleTowards(float x1, float y1, float x2, float y2) {
        return ((float)Math.atan2(y2 - y1, x2 - x1));
    }
    public static float restrictTo(float num, float min, float max) {
        num = Math.min(max, num);
        num = Math.max(min, num);
        return num;
    }

}
