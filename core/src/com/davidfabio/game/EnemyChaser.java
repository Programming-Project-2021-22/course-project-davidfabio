package com.davidfabio.game;

public class EnemyChaser extends Enemy {


    @Override public void update(float deltaTime) {
        super.update(deltaTime);

        if (!getIsActive())
            return;
        if (getIsSpawning())
            return;

        moveTowards(Game.player.getX(), Game.player.getY(), deltaTime);
    }


}
