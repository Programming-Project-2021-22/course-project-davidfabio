package com.davidfabio.game;

public class EnemyStatic extends Enemy {

    @Override public void update(float deltaTime) {
        super.update(deltaTime);

        if (!getIsActive())
            return;
        if (getIsSpawning())
            return;

        if (getFireRateCooldown() > 0)
            setFireRateCooldown(getFireRateCooldown() - deltaTime);

        if (getFireRateCooldown() <= 0)
            shoot();
    }

}
