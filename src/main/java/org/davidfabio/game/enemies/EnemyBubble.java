package org.davidfabio.game.enemies;

import com.badlogic.gdx.graphics.Color;
import org.davidfabio.game.World;
import org.davidfabio.utils.Transform2D;

import java.util.Random;


/**
 * Moves in a random direction and bounces off walls.
 * When destroyed 4 bubble children get spawned with a smaller size (scale). When one of these gets destroyed again 4 even smaller bubbles get spawned.
 */

public class EnemyBubble extends Enemy {

    @Override
    public void init(float x, float y, float scale, float moveSpeed, int newInitialHealth, Color color) {
        setType(Type.BUBBLE); // NOTE (David): type needs to be set BEFORE calling the super constructor!
        super.init(x, y, scale, moveSpeed, newInitialHealth, color);

        float randomAngle = (float)(Math.PI * 2 * Math.random());
        setAngle(randomAngle);

    }

    @Override
    public void update(float deltaTime, World world) {
        super.update(deltaTime, world);

        if (!getIsActive())
            return;
        if (getIsSpawning())
            return;

        if (!isInView(world)) {
            // TODO (David): flipping is not working correctly!
            setAngle(getAngle() + (float)Math.PI); // flip angle
            restrictToLevel(world.getLevel());
        }

        moveTowards(getAngle(), deltaTime);
    }


    @Override
    public void destroy(World world) {
        float halfScale = getScale() / 2;
        if (halfScale > 24) {
            for (int i = 0; i < 4; i += 1) {
                EnemyBubble enemyBubble = new EnemyBubble();
                enemyBubble.init(getX(), getY(), halfScale, getMoveSpeed() * 2, getInitialHealth() / 2, getColorInitial());
                enemyBubble.setIsSpawning(false);
                enemyBubble.setColor(getColorInitial());
                world.addEnemyTemp(enemyBubble);
            }
        }

        super.destroy(world);
    }
}
