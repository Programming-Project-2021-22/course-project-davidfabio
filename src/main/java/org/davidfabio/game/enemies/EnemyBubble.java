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

    private int childrenCount = 4;

    // TODO (BUG): when player collides with an enemy, children still get spawned! (should not happen)



    public EnemyBubble() {
        setType(Type.BUBBLE);
    }

    @Override
    public void init(float x, float y, float scale, float moveSpeed, int newInitialHealth, Color color) {
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

        if (!isCompletelyInView(world)) {
            // TODO (David): flipping is not working correctly!
            setAngle(getAngle() + (float)Math.PI); // flip angle
            restrictToLevel(world.getLevel());
        }

        moveTowards(getAngle(), deltaTime);
    }


    private void spawnChild(World world) {
        Enemy child = world.getEnemy(getType());
        if (child == null)
            return;
        child.init(getX(), getY(), getScale() / 2, getMoveSpeed() * 2, getInitialHealth() / 2, getColorInitial());
        child.setIsSpawning(false);
        child.setColor(getColorInitial());
        child.setIsActive(false);
        child.setSpawnNextFrame(true);
    }


    @Override
    public void destroy(World world) {
        float halfScale = getScale() / 2;
        if (halfScale > 24) {
            for (int i = 0; i < childrenCount; i += 1)
                spawnChild(world);
        }

        super.destroy(world);
    }
}
