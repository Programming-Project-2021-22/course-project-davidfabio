package org.davidfabio.game;

import com.badlogic.gdx.graphics.Color;

public interface Attackable {
    int getInitialHealth();
    void setInitialHealth(int newInitialHealth);
    int getHealth();
    void setHealth(int newHealth);
    boolean getIsActive();
    void setIsActive(boolean newIsActive);
    void setColor(Color color);
    void setIsInHitState(boolean isInHitState);
    boolean getIsInHitState();
    void setHitCooldown(float hitCooldown);
    float getHitDuration();

    default void playHitSound() {
        Sounds.playHitSfx();
    }
    default void playDestructionSound() {
        Sounds.playExplosionSfx();
    }
    default void initializeHealth() {
        setHealth(getInitialHealth());
    }

    default void destroy(World world) {
        this.setHealth(0);
        this.setIsActive(false);
        this.playDestructionSound();
    }

}
