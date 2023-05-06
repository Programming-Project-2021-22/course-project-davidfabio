package org.davidfabio.game;

import org.davidfabio.utils.Settings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

    private static void playSoundEffect(Sound sound) {
        if (!Settings.sfxEnabled)
            return;
        if (sound == null)
            return;

        sound.play(Settings.sfxVolume);
    }

    public static void playBackgroundMusic() {
        if (!Settings.musicEnabled)
            return;
        if (musicTrack == null)
            return;

        musicTrack.loop(Settings.musicVolume);
    }

    public static void stopBackgroundMusic() {
        if (!Settings.musicEnabled)
            return;
        if (musicTrack == null)
            return;

        musicTrack.stop();
    }

}
