package com.davidfabio.game;

public interface Attackable {
    public float getInitialHealth();
    public void setInitialHealth(float newInitialHealth);
    public float getHealth();
    public void setHealth(float newHealth);
    public Polarity getPolarity();
    public void setPolarity(Polarity newPolarity);
    public boolean getIsActive();
    public void setIsActive(boolean newIsActive);

    public default void playHitSound() {
        Sounds.playHitSfx();
    }
    public default void playDestructionSound() {
        Sounds.playExplosionSfx();
    }
    public default void initializeHealth() {
        this.setHealth(this.getInitialHealth());
    }

    public default void destroy() {
        this.setHealth(0f);
        this.setIsActive(false);
        this.playDestructionSound();
    }
}
