package com.davidfabio.utils;

import com.badlogic.gdx.graphics.Color;

public class Settings {
    // Game Setup Settings
    public static final String GAME_TITLE = "Duality";
    public static boolean START_IN_FULLSCREEN = false;
    public static int windowWidth = 720;
    public static int windowHeight = 540;

    // Music & Sound Settings
    public static boolean sfxEnabled = false;
    public static boolean musicEnabled = false;
    public static float sfxVolume = 0.1f; // range: 0 to 1
    public static float musicVolume = 0.5f; // range: 0 to 1

    // Game Difficulty Settings
    public static final int MAX_ENEMY_BULLETS = 2048;
    public static final int MAX_PLAYER_BULLETS = 64;
    public static final int MAX_PICKUPS = 64;
}
