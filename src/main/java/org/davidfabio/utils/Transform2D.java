package org.davidfabio.utils;

import org.davidfabio.game.Level;

/**
 * This class provides utility functions for manipulating coordinates and angles in 2D space.
 */
public class Transform2D {
    /**
     * Moves the initial x-position along the x-axis based on the provided angle and length.
     * @param x initial x-position
     * @param angle angle for the translation
     * @param length length for the translation
     * @return the translated x-position
     */
    public static float translateX(float x, float angle, float length) {
        return x + (float)Math.cos(angle) * length;
    }

  /**
   * Moves the initial y-position along the y-axis based on the provided angle and length.
   * @param y initial y-position
   * @param angle angle for the translation
   * @param length length for the translation
   * @return the translated y-position
   */
  public static float translateY(float y, float angle, float length) {
        return y + (float)Math.sin(angle) * length;
    }

    /**
     * Transforms the input angle in radians to the same angle in degrees
     * @param angleRadians input angle in radians
     * @return angle in degrees
     */
    public static float radiansToDegrees(float angleRadians) {
        return angleRadians * (float)(180 / Math.PI);
    }

    /**
     * Transforms the input angle in degrees to the same angle in radians.
     * @param angleDegrees input angle in degrees
     * @return angle in radians
     */
    public static float degreesToRadians(float angleDegrees) {
        return angleDegrees * (float)(Math.PI / 180);
    }

    /**
     * Returns the distance between two points (x1, y1) and (x2, y2).
     * @param x1 x-position for the first point
     * @param y1 y-position for the first point
     * @param x2 x-position for the second point
     * @param y2 y-position for the second point
     * @return the distance between the two points
     */
    public static float getDistance(float x1, float y1, float x2, float y2) {
        float distanceX = x1 - x2;
        float distanceY = y1 - y2;
        return (float)Math.sqrt((distanceX * distanceX) + (distanceY * distanceY));
    }

    /**
     * Returns the angle between the two points (x1, y1) and (x2, y2).
     * @param x1 x-position for the first point
     * @param y1 y-position for the first point
     * @param x2 x-position for the second point
     * @param y2 y-position for the second point
     * @return the angle between the two points
     */
    public static float getAngleTowards(float x1, float y1, float x2, float y2) {
        return ((float)Math.atan2(y2 - y1, x2 - x1));
    }
}
