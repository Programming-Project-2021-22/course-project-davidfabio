package org.davidfabio.game;

import com.badlogic.gdx.graphics.Color;

public interface Attackable {
    public float getInitialHealth();
    public void setInitialHealth(float newInitialHealth);
    public float getHealth();
    public void setHealth(float newHealth);
    public boolean getIsActive();
    public void setIsActive(boolean newIsActive);
    public void setColor(Color color);
    public void setIsInHitState(boolean isInHitState);
    public boolean getIsInHitState();
    public void setHitCooldown(float hitCooldown);
    public float getHitDuration();

    public default void playHitSound() {
        Sounds.playHitSfx();
    }
    public default void playDestructionSound() {
        Sounds.playExplosionSfx();
    }
    public default void initializeHealth() {
        this.setHealth(this.getInitialHealth());
    }

    public default void destroy(World world) {
        this.setHealth(0f);
        this.setIsActive(false);
        this.playDestructionSound();
    }

}
