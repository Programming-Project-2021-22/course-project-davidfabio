package org.davidfabio.game;

import com.badlogic.gdx.graphics.Color;

public class EnemyBubble extends Enemy {


    @Override
    public void init(float x, float y, float scale, float moveSpeed, int newInitialHealth, Color color) {
        super.init(x, y, scale, moveSpeed, newInitialHealth, color);
        setType(Type.BUBBLE);
        shape = PolygonShape.getEnemyShape(getType(), scale);

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

        if (!isInView(world.getLevel())) {
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
