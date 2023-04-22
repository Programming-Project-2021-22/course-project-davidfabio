package com.davidfabio.game.core;

import com.badlogic.gdx.graphics.Color;

public interface Attacker {
    public float getAttackPower();

    public default void attack(Attackable attackable, World world) {
        if (!attackable.getIsActive())
            return;

        // player is invincible under certain circumstances
        if (attackable.getClass() == Player.class) {
            if (attackable.getIsInHitState())
                return;
            if (((Player)attackable).getIsDashing())
                return;
        }

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
