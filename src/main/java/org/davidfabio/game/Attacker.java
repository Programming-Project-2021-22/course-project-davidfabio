package org.davidfabio.game;

import com.badlogic.gdx.graphics.Color;
import org.davidfabio.game.enemies.EnemyStar;

/**
 * This interface is used to identify any Class/Object that should be able to attack a Class/Object that implements the
 * {@link Attackable} interface.
 */
public interface Attacker {
    /**
     * @return The damage that is applied upon attack.
     */
    int getAttackPower();

    /**
     * This method implements a default behaviour for attacks. It usually takes an {@link Attackable} object and
     * the {@link World} object in order to process any side-effects.
     * <p>If the Player is the {@link Attackable}, the method verifies that the Player is not dashing or currently otherwise
     * invincible.
     * <p>If the EnemyStar is the {@link Attackable}, the method verifies that the Enemy is not about to blow up.
     *
     * @param attackable Object that will be attacked (that takes damage)
     * @param world World reference, used for side-effects
     */
    default void attack(Attackable attackable, World world) {
        if (!attackable.getIsActive())
            return;

        // player is invincible under certain circumstances
        if (attackable.getClass() == Player.class) {
            if (attackable.getIsInHitState())
                return;
            if (((Player)attackable).getIsDashing()) {
                return;
            }
        }

        // EnemyStar is invincible under certain circumstances
        if (attackable.getClass() == EnemyStar.class) {
            if (((EnemyStar)attackable).getIsBlowingUp())
                return;
        }

        attackable.setIsInHitState(true);
        attackable.setColor(Color.LIGHT_GRAY);
        attackable.setHitCooldown(attackable.getHitDuration());
        attackable.setHealth(attackable.getHealth() - getAttackPower());

        if (attackable.getHealth() <= 0) {
            attackable.destroy(world);
        } else {
            attackable.playHitSound();
        }
    }
}
