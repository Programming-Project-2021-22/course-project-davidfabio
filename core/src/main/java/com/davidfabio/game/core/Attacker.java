package com.davidfabio.game.core;

public interface Attacker {
    public float getAttackPower();
    public void setAttackPower(float newAttackPower);
    public Polarity getPolarity();
    public void setPolarity(Polarity newPolarity);

    public default void attack(Attackable attackable, World world) {
        if (!attackable.getIsActive())
            return;

        if (!this.getPolarity().equals(attackable.getPolarity())) {
            attackable.setHealth(attackable.getHealth() - (this.getAttackPower() * Polarity.OPPOSITE_POLARITY_MULTIPLIER));
        } else {
            attackable.setHealth(attackable.getHealth() - this.getAttackPower());
        }

        if (attackable.getHealth() < 0) {
            attackable.destroy(world);
        } else {
            attackable.playHitSound();
        }
    }
}
