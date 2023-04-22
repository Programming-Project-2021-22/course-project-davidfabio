package org.davidfabio.game;

import org.davidfabio.utils.Settings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class Sounds {
    private static Sound sfxShoot, sfxExplosion, sfxHit;
    private static Sound musicTrack;

    public static void loadSounds() {
        try {
            sfxShoot = Gdx.audio.newSound(Gdx.files.internal("src/main/resources/sfx/shoot1.wav"));
        }
        catch (GdxRuntimeException ex) {
            sfxShoot = null;
            System.out.println("Sound could not be loaded.\n" + ex.getMessage());
        }

        try {
            sfxExplosion = Gdx.audio.newSound(Gdx.files.internal("src/main/resources/sfx/explosion1.wav"));
        }
        catch (GdxRuntimeException ex) {
            sfxExplosion = null;
            System.out.println("Sound could not be loaded.\n" + ex.getMessage());
        }

        try {
            sfxHit = Gdx.audio.newSound(Gdx.files.internal("src/main/resources/sfx/shoot5.wav"));
        }
        catch (GdxRuntimeException ex) {
            sfxHit = null;
            System.out.println("Sound could not be loaded.\n" + ex.getMessage());
        }

        // NOTE (David): this is commented out for now, because it makes the game load very slowly
        /*
        try {
            musicTrack = Gdx.audio.newSound(Gdx.files.internal("src/main/resources/music/track1.mp3"));
        }
        catch (GdxRuntimeException ex) {
            musicTrack = null;
            System.out.println("Sound could not be loaded.\n" + ex.getMessage());
        }
        */
    }

    public static void playHitSfx() {
        if ((sfxHit != null) && (Settings.sfxEnabled)) {
            sfxHit.play(Settings.sfxVolume);
        }
    }

    public static void playShootSfx() {
        if ((sfxExplosion != null) && (Settings.sfxEnabled)) {
            sfxShoot.play(Settings.sfxVolume);
        }
    }

    public static void playExplosionSfx() {
        if ((sfxExplosion != null) && (Settings.sfxEnabled)) {
            sfxExplosion.play(Settings.sfxVolume);
        }
    }

    public static void playBackgroundMusic() {
        if ((musicTrack != null) && (Settings.musicEnabled)) {
            musicTrack.play(Settings.musicVolume);
        }
    }
}
