package com.davidfabio.game.core;

import com.badlogic.gdx.graphics.Color;

public interface Attacker {
    public float getAttackPower();

    public default void attack(Attackable attackable, World world) {
        if (!attackable.getIsActive())
            return;
        if (attackable.getClass() == Player.class && attackable.getIsInHitState())
            return;

        attackable.setIsInHitState(true);
        attackable.setColor(Color.LIGHT_GRAY);
        attackable.setHitCooldown(attackable.getHitDuration());
        attackable.setHealth(attackable.getHealth() - this.getAttackPower());

        if (attackable.getHealth() < 0) {
            attackable.destroy(world);
        } else {
            attackable.playHitSound();
        }
    }
}
