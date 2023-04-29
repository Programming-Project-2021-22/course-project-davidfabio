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


    public static void loadSounds() {
        if (Settings.sfxEnabled) {
            if(sfxShoot == null) {
                try {
                    sfxShoot = Gdx.audio.newSound(Gdx.files.internal("src/main/resources/sfx/shoot.wav"));
                }
                catch (GdxRuntimeException ex) {
                    sfxShoot = null;
                    System.out.println("Sound could not be loaded.\n" + ex.getMessage());
                }
            }
        }

        if (Settings.sfxEnabled) {
            if(sfxShoot == null) {
                try {
                    sfxShoot = Gdx.audio.newSound(Gdx.files.internal("src/main/resources/sfx/shoot.wav"));
                }
                catch (GdxRuntimeException ex) {
                    sfxShoot = null;
                    System.out.println("Sound could not be loaded.\n" + ex.getMessage());
                }
            }

            if(sfxExplosion == null) {
                try {
                    sfxExplosion = Gdx.audio.newSound(Gdx.files.internal("src/main/resources/sfx/explosion.wav"));
                }
                catch (GdxRuntimeException ex) {
                    sfxExplosion = null;
                    System.out.println("Sound could not be loaded.\n" + ex.getMessage());
                }
            }

            if(sfxHit == null) {
                try {
                    sfxHit = Gdx.audio.newSound(Gdx.files.internal("src/main/resources/sfx/bullet_hit.wav"));
                }
                catch (GdxRuntimeException ex) {
                    sfxHit = null;
                    System.out.println("Sound could not be loaded.\n" + ex.getMessage());
                }
            }

            if(sfxDash == null) {
                try {
                    sfxDash = Gdx.audio.newSound(Gdx.files.internal("src/main/resources/sfx/dash.wav"));
                }
                catch (GdxRuntimeException ex) {
                    sfxDash = null;
                    System.out.println("Sound could not be loaded.\n" + ex.getMessage());
                }
            }

            if(sfxPickup == null) {
                try {
                    sfxPickup = Gdx.audio.newSound(Gdx.files.internal("src/main/resources/sfx/pickup.wav"));
                }
                catch (GdxRuntimeException ex) {
                    sfxPickup = null;
                    System.out.println("Sound could not be loaded.\n" + ex.getMessage());
                }
            }

            if(sfxExplosionEnemyStar == null) {
                try {
                    sfxExplosionEnemyStar = Gdx.audio.newSound(Gdx.files.internal("src/main/resources/sfx/explosion_enemy_star.wav"));
                }
                catch (GdxRuntimeException ex) {
                    sfxExplosionEnemyStar = null;
                    System.out.println("Sound could not be loaded.\n" + ex.getMessage());
                }
            }

            if(sfxGameStart == null) {
                try {
                    sfxGameStart = Gdx.audio.newSound(Gdx.files.internal("src/main/resources/sfx/game_start.wav"));
                }
                catch (GdxRuntimeException ex) {
                    sfxGameStart = null;
                    System.out.println("Sound could not be loaded.\n" + ex.getMessage());
                }
            }

            if(sfxGameOver == null) {
                try {
                    sfxGameOver = Gdx.audio.newSound(Gdx.files.internal("src/main/resources/sfx/game_over.wav"));
                }
                catch (GdxRuntimeException ex) {
                    sfxGameOver = null;
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

    public static void playDashSfx() {
        if ((sfxDash != null) && (Settings.sfxEnabled)) {
            sfxDash.play(Settings.sfxVolume);
        }
    }

    public static void playPickupSfx() {
        if ((sfxPickup != null) && (Settings.sfxEnabled)) {
            sfxPickup.play(Settings.sfxVolume);
        }
    }

    public static void playExplosionSfx() {
        if ((sfxExplosion != null) && (Settings.sfxEnabled)) {
            sfxExplosion.play(Settings.sfxVolume);
        }
    }

    public static void playExplosionEnemyStarSfx() {
        if ((sfxExplosionEnemyStar != null) && (Settings.sfxEnabled)) {
            sfxExplosionEnemyStar.play(Settings.sfxVolume);
        }
    }

    public static void playGameStartSfx() {
        if ((sfxGameStart != null) && (Settings.sfxEnabled)) {
            sfxGameStart.play(Settings.sfxVolume);
        }
    }

    public static void playGameOverSfx() {
        if ((sfxGameOver != null) && (Settings.sfxEnabled)) {
            sfxGameOver.play(Settings.sfxVolume);
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
