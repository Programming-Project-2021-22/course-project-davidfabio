package org.davidfabio.utils;

/**
 * Contains both editable and constant Settings that are used throughout the game.
 * This Class is always accessed in a static way.
 */
public class Settings {
    // Game Setup Settings
    public static final String GAME_TITLE = "Polygon Wars";
    public static final String SETTINGS_FILENAME = "src/main/resources/settings.json";
    public static final String SCORES_FILENAME = "src/main/resources/scores.json";
    public static String username = "Player";
    public static float levelWidth = 800;
    public static float levelHeight = 640;
    public static final int MAX_MULTIPLIER = 5;

    // Display Settings
    public static boolean fullscreenEnabled = false;
    public static int windowWidth = 1024;
    public static int windowHeight = 768;

    // Music & Sound Settings
    public static boolean sfxEnabled = true;
    public static boolean musicEnabled = false;
    public static float sfxVolume = 0.5f; // range: 0 to 1
    public static float musicVolume = 0.1f; // range: 0 to 1

    // Max amount of x that can be active (i.e. on-screen) at once; memory for it gets reserved in World constructor
    public static int MAX_ENEMIES;
    public static final int MAX_ENEMIES_PER_TYPE = 128;
    public static final int MAX_ENEMY_BULLETS = 512;
    public static final int MAX_PLAYER_BULLETS = 64;
    public static final int MAX_PICKUPS = 512;
    public static final int MAX_PARTICLES = 2048;
}
