package org.davidfabio.utils;

public class Settings {
    // Game Setup Settings
    public static final String GAME_TITLE = "Duality";
    public static String username = "Player";
    public static float levelWidth = 800;
    public static float levelHeight = 640;

    // Display Settings
    public static boolean fullscreenEnabled = false;
    public static int windowWidth = 1024;
    public static int windowHeight = 768;

    // Music & Sound Settings
    public static boolean sfxEnabled = true;
    public static boolean musicEnabled = false;
    public static float sfxVolume = 0.5f; // range: 0 to 1
    public static float musicVolume = 0.1f; // range: 0 to 1

    // Reserve memory for x amount of bullets/pickups
    public static final int MAX_ENEMY_BULLETS = 2048;
    public static final int MAX_PLAYER_BULLETS = 64;
    public static final int MAX_PICKUPS = 64;
    public static final int MAX_PARTICLES = 2048;
}
