package org.davidfabio.game;

import com.badlogic.gdx.graphics.Color;

/**
 * This interface is used to identify any Class/Object that can be attacked. Classes/Objects that implement the {@link Attacker}
 * interface can attack this interface.
 */
public interface Attackable {
    /**
     * @return the starting health for the Object
     */
    int getInitialHealth();

    /**
     * @param newInitialHealth the new starting health for the Object
     */
    void setInitialHealth(int newInitialHealth);

    /**
     * @return the current health for the Object
     */
    int getHealth();

    /**
     * @param newHealth the new health for the Object
     */
    void setHealth(int newHealth);

    /**
     * @return returns true if the Class/Object is alive, false otherwise
     */
    boolean getIsActive();

    /**
     * @param newIsActive set to true to render this Class/Object alive, false otherwise
     */
    void setIsActive(boolean newIsActive);

    /**
     * @param color set the new Color for this Class/Object
     */
    void setColor(Color color);

    /**
     * @param isInHitState set to true, to enable invincibility (used for Player), false otherwise
     */
    void setIsInHitState(boolean isInHitState);

    /**
     * The In-Hit-State is used to determine if the Entity was recently hit and is therefore invincible.
     * @return true if invincible, false otherwise
     */
    boolean getIsInHitState();

    /**
     * The Hit-Cooldown is used to determine the length of the invincibility and the In-Hit-State.
     * @param hitCooldown sets the current hit duration
     */
    void setHitCooldown(float hitCooldown);

    /**
     * @return The current length of the HitCooldown, which defines how long the Entity is invincible.
     */
    float getHitDuration();

    /**
     * Plays the Hit-Sound, which should be played when an {@link Attackable} is hit.
     */
    default void playHitSound() {
        Sounds.playHitSfx();
    }

    /**
     * Plays the Destruction-Sound, which should be played when an {@link Attackable} is killed.
     */
    default void playDestructionSound() {
        Sounds.playExplosionSfx();
    }

    /**
     * Sets the Current Health to the Initial Health. This should usually be used when an Entity is initialized.
     */
    default void initializeHealth() {
        setHealth(getInitialHealth());
    }

    /**
     * This Method is used to "kill" an {@link Attackable}. It sets the Health to 0 and renders the {@link Attackable}
     * inactive.
     * @param world World object reference, used for side-effects
     */
    default void destroy(World world) {
        setHealth(0);
        setIsActive(false);
        playDestructionSound();
    }
}
