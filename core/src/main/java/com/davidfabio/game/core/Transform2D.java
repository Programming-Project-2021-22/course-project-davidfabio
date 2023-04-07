package com.davidfabio.game.core;

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

    public static float getRandomX() { return (float)(Math.random() * Settings.windowWidth); }
    public static float getRandomY() { return (float)(Math.random() * Settings.windowHeight); }
}
