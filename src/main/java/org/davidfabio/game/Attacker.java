package org.davidfabio.game;

import com.badlogic.gdx.graphics.Color;

public interface Attacker {
    int getAttackPower();

    default void attack(Attackable attackable, World world) {
        if (!attackable.getIsActive())
            return;

        // player is invincible under certain circumstances
        if (attackable.getClass() == Player.class) {
            if (attackable.getIsInHitState())
                return;
            if (((Player)attackable).getIsDashing())
                return;

            // destroy all enemies and enemy bullets when player gets hit
            for (Enemy enemy : world.getEnemies()) {
                if (!enemy.getIsActive())
                    continue;
                if (enemy.getType() == Enemy.Type.STAR) {
                    if (Collision.pointIsInLevel(enemy.getX(), enemy.getY(), world))
                        enemy.spawnPickup(world);
                    enemy.spawnParticles(enemy.getScale() / 4, (int)enemy.getScale() / 2, world, Particle.Type.CIRCLE);
                    enemy.setHealth(0);
                    enemy.setIsActive(false);
                    enemy.playDestructionSound();
                }
                else
                    enemy.destroy(world);
            }
            world.getEnemiesTemp().clear();

            for (Bullet enemyBullet : world.getEnemyBullets())
                enemyBullet.setIsActive(false);
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
