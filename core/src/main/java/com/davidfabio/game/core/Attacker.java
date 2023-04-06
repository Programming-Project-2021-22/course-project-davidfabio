package com.davidfabio.game.core;

public interface Attacker {
    public float getAttackPower();
    public void setAttackPower(float newAttackPower);

    public default void attack(Attackable attackable, World world) {
        if (!attackable.getIsActive())
            return;

        attackable.setHealth(attackable.getHealth() - this.getAttackPower());

        if (attackable.getHealth() < 0) {
            attackable.destroy(world);
        } else {
            attackable.playHitSound();
        }
    }
}
