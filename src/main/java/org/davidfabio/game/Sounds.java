package org.davidfabio.game;

import org.davidfabio.utils.Settings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This static class handles all the sound effects and background music tasks.
 */
public class Sounds {
    private static Sound sfxShoot;
    private static Sound sfxExplosion;
    private static Sound sfxExplosionEnemyStar;
    private static Sound sfxHit;
    private static Sound sfxDash;
    private static Sound sfxPickup;
    private static Sound sfxGameStart;
    private static Sound sfxGameOver;
    private static Sound musicTrack;

    public static void playShootSfx() { playSoundEffect(sfxShoot); }
    public static void playExplosionSfx() { playSoundEffect(sfxExplosion); }
    public static void playExplosionEnemyStarSfx() { playSoundEffect(sfxExplosionEnemyStar); }
    public static void playHitSfx() { playSoundEffect(sfxHit); }
    public static void playDashSfx() { playSoundEffect(sfxDash); }
    public static void playPickupSfx() { playSoundEffect(sfxPickup); }
    public static void playGameStartSfx() { playSoundEffect(sfxGameStart); }
    public static void playGameOverSfx() { playSoundEffect(sfxGameOver); }

    /**
     * This method takes a filename and loads the respective file as a {@link Sound} object.
     * If the file cannot be located, the returned sound will be null.
     * @param fileName sound-file to load
     * @return the sound object that was loaded, or null if the file was not loaded
     */
    private static Sound loadSound(String fileName) {
        Sound sound = null;
        try {
            sound = Gdx.audio.newSound(Gdx.files.internal("src/main/resources/" + fileName));
        }
        catch (GdxRuntimeException ex) {
            System.out.println("Sound could not be loaded.\n" + ex.getMessage());
        }
        return sound;
    }

    /**
     * Loads all sounds needed for the Game.
     * If {@link Settings#sfxEnabled} is active, the Sound effects are loaded, otherwise they remain empty (null).
     * If {@link Settings#musicEnabled} is active, the Music is loaded, otherwise it remains empty (null).
     */
    public static void loadSounds() {
        if (Settings.sfxEnabled) {
            sfxShoot = loadSound("sfx/shoot.wav");
            sfxExplosion = loadSound("sfx/explosion.wav");
            sfxExplosionEnemyStar = loadSound("sfx/explosion_enemy_star.wav");
            sfxHit = loadSound("sfx/bullet_hit.wav");
            sfxDash = loadSound("sfx/dash.wav");
            sfxPickup = loadSound("sfx/pickup.wav");
            sfxGameStart = loadSound("sfx/game_start.wav");
            sfxGameOver = loadSound("sfx/game_over.wav");
        }
        if (Settings.musicEnabled) {
            musicTrack = loadSound("music/track1.mp3");
        }
    }

    /**
     * This method plays the passed sound using the volume stored in {@link Settings#sfxVolume}.
     * @param sound sound effect to be played. The sound is only played if {@link Settings#sfxEnabled} is active.
     */
    private static void playSoundEffect(Sound sound) {
        if (!Settings.sfxEnabled)
            return;
        if (sound == null)
            return;

        sound.play(Settings.sfxVolume);
    }

    /**
     * This method plays the background music stored in {@link Sounds#musicTrack} using the volume stored in
     * {@link Settings#musicVolume}. The music is only played if {@link Settings#musicEnabled} is active.
     */
    public static void playBackgroundMusic() {
        if (!Settings.musicEnabled)
            return;
        if (musicTrack == null)
            return;

        musicTrack.loop(Settings.musicVolume);
    }

    /**
     * This method stops the playing of the background music {@link Sounds#musicTrack}.
     * If the track is null or music is not enabled, nothing is done.
     */
    public static void stopBackgroundMusic() {
        if (!Settings.musicEnabled)
            return;
        if (musicTrack == null)
            return;

        musicTrack.stop();
    }
}
