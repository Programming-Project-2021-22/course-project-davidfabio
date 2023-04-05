package com.davidfabio.game;

public interface Attacker {
    public float getAttackPower();
    public void setAttackPower(float newAttackPower);
    public Polarity getPolarity();
    public void setPolarity(Polarity newPolarity);

    public default void attack(Attackable hittable) {
        if (!hittable.getIsActive())
            return;

        if (!this.getPolarity().equals(hittable.getPolarity())) {
            hittable.setHealth(hittable.getHealth() - (this.getAttackPower() * Polarity.OPPOSITE_POLARITY_MULTIPLIER));
        } else {
            hittable.setHealth(hittable.getHealth() - this.getAttackPower());
        }

        if (hittable.getHealth() < 0) {
            hittable.setHealth(0);
            hittable.setIsActive(false);
            hittable.playDestructionSound();
        } else {
            hittable.playHitSound();
        }
    }
}
