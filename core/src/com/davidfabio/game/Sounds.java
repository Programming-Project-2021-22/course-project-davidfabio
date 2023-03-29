package com.davidfabio.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class Sounds {
    private static Sound sfxShoot, sfxExplosion, sfxHit;
    private static Sound musicTrack;

    public static void loadSounds() {
        try {
            sfxShoot = Gdx.audio.newSound(Gdx.files.internal("assets/sfx/shoot1.wav"));
        }
        catch (GdxRuntimeException ex) {
            sfxShoot = null;
            System.out.println("Sound could not be loaded.\n" + ex.getMessage());
        }

        try {
            sfxExplosion = Gdx.audio.newSound(Gdx.files.internal("assets/sfx/explosion1.wav"));
        }
        catch (GdxRuntimeException ex) {
            sfxExplosion = null;
            System.out.println("Sound could not be loaded.\n" + ex.getMessage());
        }

        try {
            sfxHit = Gdx.audio.newSound(Gdx.files.internal("assets/sfx/shoot5.wav"));
        }
        catch (GdxRuntimeException ex) {
            sfxHit = null;
            System.out.println("Sound could not be loaded.\n" + ex.getMessage());
        }

        // NOTE (David): this is commented out for now, because it makes the game load very slowly
        /*
        try {
            musicTrack = Gdx.audio.newSound(Gdx.files.internal("assets/music/track1.mp3"));
        }
        catch (GdxRuntimeException ex) {
            musicTrack = null;
            System.out.println("Sound could not be loaded.\n" + ex.getMessage());
        }
        */
    }

    public static void playHitSfx() {
        if (sfxHit != null) {
            sfxHit.play(Settings.sfxVolume);
        }
    }

    public static void playShootSfx() {
        if (sfxShoot != null) {
            sfxShoot.play(Settings.sfxVolume);
        }
    }

    public static void playExplosionSfx() {
        if (sfxExplosion != null) {
            sfxExplosion.play(Settings.sfxVolume);
        }
    }

    public static void playBackgroundMusic() {
        if (musicTrack != null) {
            musicTrack.play(Settings.musicVolume);
        }
    }
}
