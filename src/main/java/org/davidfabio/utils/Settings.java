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
     * Max amount of Enemies that can be active (i.e. on-screen) at once; memory for it gets reserved in World constructor.
     * We allocate {@link Settings#MAX_ENEMIES_PER_TYPE} Enemies per type of Enemy.
     */
    public static int MAX_ENEMIES;

    /**
     * This number indicates how many enemies of each type of enemy we allocate. This is used to calculate {@link Settings#MAX_ENEMIES}.
     */
    public static final int MAX_ENEMIES_PER_TYPE = 128;

    /**
     * Max amount of Enemy Bullets that may be active at any one time. This number indicates how large the Array in the World
     * Object should be, that holds all Enemy Bullets.
     */
    public static final int MAX_ENEMY_BULLETS = 512;

    /**
     * Max amount of Player bullets that may be active at any one time. This number indicates how large the Array in the World
     * Object should be, that holds all Player Bullets.
     */
    public static final int MAX_PLAYER_BULLETS = 64;

    /**
     * Max amount of Pickups that may be active at any one time. This is used to allocate the Array in the World Object.
     */
    public static final int MAX_PICKUPS = 512;

    /**
     * Max amount of Particles that may be active at any one time. This is used to allocate the Array in the World Object.
     */
    public static final int MAX_PARTICLES = 2048;
}
