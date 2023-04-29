package org.davidfabio.game;

import org.davidfabio.utils.Settings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class Sounds {
    private static Sound sfxShoot = null, sfxExplosion = null, sfxHit = null;
    private static Sound musicTrack = null;

    public static void loadSounds() {
        if (Settings.sfxEnabled) {
            if(sfxShoot == null) {
                try {
                    sfxShoot = Gdx.audio.newSound(Gdx.files.internal("src/main/resources/sfx/shoot1.wav"));
                }
                catch (GdxRuntimeException ex) {
                    sfxShoot = null;
                    System.out.println("Sound could not be loaded.\n" + ex.getMessage());
                }
            }

            if(sfxExplosion == null) {
                try {
                    sfxExplosion = Gdx.audio.newSound(Gdx.files.internal("src/main/resources/sfx/explosion1.wav"));
                }
                catch (GdxRuntimeException ex) {
                    sfxExplosion = null;
                    System.out.println("Sound could not be loaded.\n" + ex.getMessage());
                }
            }

            if(sfxHit == null) {
                try {
                    sfxHit = Gdx.audio.newSound(Gdx.files.internal("src/main/resources/sfx/shoot5.wav"));
                }
                catch (GdxRuntimeException ex) {
                    sfxHit = null;
                    System.out.println("Sound could not be loaded.\n" + ex.getMessage());
                }
            }
        }

        if (Settings.musicEnabled) {
            if (musicTrack == null ) {
                try {
                    // Note: Loading Music still takes very long.
                    // However, this is done once and not more often.
                    musicTrack = Gdx.audio.newSound(Gdx.files.internal("src/main/resources/music/track1.mp3"));
                }
                catch (GdxRuntimeException ex) {
                    musicTrack = null;
                    System.out.println("Sound could not be loaded.\n" + ex.getMessage());
                }
            }
        }
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
            musicTrack.loop(Settings.musicVolume);
        }
    }

    public static void stopBackgroundMusic() {
        if ((musicTrack != null) && Settings.musicEnabled) {
            musicTrack.stop();
        }
    }
}
