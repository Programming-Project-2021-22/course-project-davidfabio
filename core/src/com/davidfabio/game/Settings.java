package com.davidfabio.game;

import com.badlogic.gdx.graphics.Color;

public class Settings {
    public static final String GAME_TITLE = "Duality";
    public static boolean START_IN_FULLSCREEN = false;
    public static int windowWidth = 720;
    public static int windowHeight = 540;
    public static boolean sfxEnabled = false;
    public static boolean musicEnabled = false;
    public static float sfxVolume = 0.33f; // range: 0 to 1
    public static float musicVolume = 0.5f; // range: 0 to 1
    public static Color FIRST_COLOR = new Color(0.5f, 0, 0, 1);
    public static Color SECOND_COLOR = new Color(0, 0, 0.5f, 1);
}
