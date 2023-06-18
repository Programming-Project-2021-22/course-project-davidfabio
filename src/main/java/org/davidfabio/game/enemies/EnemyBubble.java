package org.davidfabio.game.enemies;

import com.badlogic.gdx.graphics.Color;
import org.davidfabio.game.World;

/**
 * Moves in a random direction and bounces off walls.
 * When destroyed 4 bubble children get spawned with a smaller size (scale). When one of these gets destroyed again 4 even smaller bubbles get spawned.
 */
public class EnemyBubble extends Enemy {
    /**
     * Indicates the number of smaller Bubble-Enemies that should be spawned when the "Main BubbleEnemy" is killed.
     */
    private int childrenCount = 4;

    /**
     * Constructor of the EnemyBubble-class. It sets the EnemyType to BUBBLE.
     */
    public EnemyBubble() {
        setType(Type.BUBBLE);
    }

    /**
     * Initializes the BubbleEnemy. It initializes the Enemy in a random angle.
     *
     * @param x x-position for the Enemy
     * @param y y-position for the Enemy
     * @param scale size for the Enemy (or the shape)
     * @param moveSpeed Enemy velocity
     * @param newInitialHealth initial health, if 0 {@link Enemy#initialHealth} is used
     * @param color color for the display of the Enemy
     */
    @Override
    public void init(float x, float y, float scale, float moveSpeed, int newInitialHealth, Color color) {
        super.init(x, y, scale, moveSpeed, newInitialHealth, color);

        float randomAngle = (float)(Math.PI * 2 * Math.random());
        setAngle(randomAngle);
    }

    /**
     * Updates the EnemyBubble-State. It runs {@link Enemy#update(float, World)} and makes sure that the EnemyBubble
     * does not exceed the Level boundaries. If it hits a "Wall" the EnemyBubble updates the current angle and "bounces"
     * off the Wall. This makes sure that the Bubble randomly bounces around the Level.
     *
     * @param deltaTime Delta by which the game loop updated
     * @param world World object reference used for side-effects
     */
    @Override
    public void update(float deltaTime, World world) {
        super.update(deltaTime, world);

        if (!getIsActive())
            return;
        if (getIsSpawning())
            return;

        if (!isCompletelyInView(world)) {
            setAngle(getAngle() + (float)Math.PI); // flip angle
            restrictToLevel(world.getLevel());
        }

        moveTowards(getAngle(), deltaTime);
    }

    /**
     * Retrieves an inactive Enemy from the World-object. Then it activates the Enemy as a "Child"-Enemy for this EnemyBubble.
     * @param world World object that contains all the Enemies
     */
    private void spawnChild(World world) {
        Enemy child = world.getEnemy(getType());
        if (child == null)
            return;
        child.init(getX(), getY(), getScale() / 2, getMoveSpeed() * 2, getInitialHealth() / 3, getColorInitial());
        child.setIsSpawning(false);
        child.setColor(getColorInitial());
        child.setIsActive(false);
        child.setSpawnNextFrame(true);
    }

    /**
     * Kills the EnemyBubble and spawns {@link EnemyBubble#childrenCount} children using {@link EnemyBubble#spawnChild(World)}.
     * @param world World object reference, used for side-effects
     */
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
