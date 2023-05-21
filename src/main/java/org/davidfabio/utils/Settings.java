package org.davidfabio.utils;

/**
 * Contains both editable and constant Settings that are used throughout the game.
 * This Class is always accessed in a static way.
 */
public class Settings {
    // Game Setup Settings
    /**
     * Stores the game's title
     */
    public static final String GAME_TITLE = "Polygon Wars";
    /**
     * Default path for the Settings .json-file
     */
    public static final String SETTINGS_FILENAME = "src/main/resources/settings.json";
    /**
     * Default path for the Scores .json-file
     */
    public static final String SCORES_FILENAME = "src/main/resources/scores.json";
    /**
     * Username of the Player, can be modified.
     */
    public static String username = "Player";
    /**
     * This is the default Width for a {@link org.davidfabio.game.Level}, which is used as boundaries during the game.
     */
    public static float levelWidth = 800;
    /**
     * This is the default Height for a {@link org.davidfabio.game.Level}, which is used as boundaries during the game.
     */
    public static float levelHeight = 640;
    /**
     * This is the maximum Multiplier a Player may achieve collecting {@link org.davidfabio.game.Pickup}s.
     */
    public static final int MAX_MULTIPLIER = 5;

    // Display Settings
    /**
     * If this is enabled, the application starts in Fullscreen. If changed, the Application must be restarted.
     */
    public static boolean fullscreenEnabled = false;
    /**
     * This is the default width for the Game Window if it is started in windowed mode.
     */
    public static int windowWidth = 1024;
    /**
     * This is the default height for the Game Window if it is started in windowed mode.
     */
    public static int windowHeight = 768;

    // Music & Sound Settings
    /**
     * If this is enabled, the sound effects get played during the game.
     */
    public static boolean sfxEnabled = true;

    /**
     * If this is enabled, the background music is played during the game.
     */
    public static boolean musicEnabled = false;
    /**
     * This indicates the Volume (Loudness) multiplier for Sound effects. It may only range from 0.0f to 0.1f.
     */
    public static float sfxVolume = 0.5f;
    /**
     * This indicates the Volume (Loudness) multiplier for background Music. It may only range from 0.0f to 0.1f.
     */
    public static float musicVolume = 0.1f;


    /**
     * Max amount of x that can be active (i.e. on-screen) at once; memory for it gets reserved in World constructor
     */
    public static int MAX_ENEMIES;
    public static final int MAX_ENEMIES_PER_TYPE = 128;
    public static final int MAX_ENEMY_BULLETS = 512;
    public static final int MAX_PLAYER_BULLETS = 64;
    public static final int MAX_PICKUPS = 512;
    public static final int MAX_PARTICLES = 2048;
}
