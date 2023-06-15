package org.davidfabio.game;
import org.davidfabio.game.enemies.Enemy;
import org.davidfabio.game.enemies.EnemyStar;

import java.awt.geom.Line2D;

/**
 * This class is used to detect collisions between Entities.
 */
public class Collision {
    /**
     * This method retrieves the {@link Player} and {@link Enemy} entities from the {@link World} class.
     * It checks if
     * <p>- Enemies and Player overlap, and applies damage to the Player
     * <p>- Enemies and Player Bullets overlap, and applies damage to the Enemy
     * <p>- Enemy Bullets and Player overlap, and applies damage to the Player
     * <p>- Pickups and Player overlap, and increments the Player pickup score
     *
     * @param world World object reference in which collisions need to be detected.
     */
    public static void update(World world) {
        Player player = world.getPlayer();

        // Check collisions for all Enemies
        for (Enemy enemy : world.getEnemies()) {
            if (!enemy.getIsActive())
                continue;
            if (enemy.getIsSpawning())
                continue;

            // Check if Player Bullets are colliding with an Enemy
            for (Bullet playerBullet : player.getBullets()) {
                if (!playerBullet.getIsActive())
                    continue;

                // player bullet colliding with enemy
                if (polygonPolygon(playerBullet, enemy, world)) {
                    if (enemy.getType() == Enemy.Type.STAR && ((EnemyStar)enemy).getIsBlowingUp())
                        break;

                    playerBullet.attack(enemy, world);
                    if (!enemy.getIsActive())
                        world.getScore().addPoints(Enemy.POINT_VALUE * player.getMultiplier());

                    playerBullet.setIsActive(false);
                    playerBullet.spawnParticles(playerBullet.getScale() / 4, 3, world, Particle.Type.CIRCLE);
                }
            }

            // enemy colliding with blowing up EnemyStar
            for (Enemy enemyStar : world.getEnemies()) {
                if (enemy.getType() == Enemy.Type.STAR)
                    continue;
                if (!enemyStar.getIsActive())
                    continue;
                if (enemyStar.getIsSpawning())
                    continue;
                if (enemyStar.getType() != Enemy.Type.STAR)
                    continue;
                if (!((EnemyStar)enemyStar).getIsBlowingUp())
                    continue;

                if (Collision.polygonPolygon(enemy, enemyStar, world)) {
                    enemy.destroy(world);
                }
            }

            // player colliding with enemy
            if (polygonPolygon(player, enemy, world)) {
                if ((enemy.getType() != Enemy.Type.STAR) || !((EnemyStar)enemy).getIsBlowingUp()) {
                    enemy.attack(player, world);
                    enemy.destroy(world);
                    world.getScore().addPoints(Enemy.POINT_VALUE * player.getMultiplier());
                    if (!player.getIsDashing()) {
                        player.resetCurrentPickupCollection();
                    }
                }
            }
        }

        // Check if any Enemy Bullets are colliding with the Player
        for (Bullet enemyBullet : world.getEnemyBullets()) {
            if (!enemyBullet.getIsActive())
                continue;

            // player colliding with enemy bullet
            if (polygonPolygon(enemyBullet, player, world)) {
                enemyBullet.attack(player, world);
                enemyBullet.setIsActive(false);

                if (!player.getIsDashing()) {
                    world.destroyAllEnemies();
                    player.resetCurrentPickupCollection();
                }
            }
        }

        // Check if any Pickups are colliding with the Player
        for (Pickup pickup : world.getPickups()) {
            if (!pickup.getIsActive())
                continue;

            // player colliding with pickup
            if (polygonPolygon(player, pickup, world)) {
                pickup.setIsActive(false);
                player.incrementPickups();
                Sounds.playPickupSfx();
            }
        }
    }

    /**
     * This methods verifies if the passed Point (pointX, pointY) intersects with the Polygon defined in vertices.
     *
     * @param pointX x-position for the Point
     * @param pointY y-position for the Point
     * @param vertices vertices that define the Polygon
     * @param world world object Reference used to get the Level Width
     * @return true if point and polygon intersect, false otherwise
     */
    public static boolean pointPolygon(float pointX, float pointY, float[] vertices, World world) {
        Line2D line = new Line2D.Float(pointX, pointY, pointX + world.getLevel().getWidth(), pointY);
        int intersectionsCount = 0;

        for (int i = 0; i < vertices.length - 2; i += 2) {
            Line2D linePolygon = new Line2D.Float(vertices[i], vertices[i + 1], vertices[i + 2], vertices[i + 3]);
            if (line.intersectsLine(linePolygon))
                intersectionsCount += 1;
        }

        Line2D linePolygon = new Line2D.Float(vertices[vertices.length - 2], vertices[vertices.length - 1],
                                              vertices[0], vertices[1]);
        if (line.intersectsLine(linePolygon))
            intersectionsCount += 1;

        return (intersectionsCount % 2 != 0);
    }

    /**
     * This method verifies if two entities (actually their {@link Entity#getShape()}s) overlap.
     * Uses {@link Collision#polygonPolygon(float[], float[], World)} for determining the intersection.
     *
     * @param entity1 first Entity
     * @param entity2 second Entity
     * @param world world object reference
     * @return true if the two Entities intersect, false otherwise
     */
    public static boolean polygonPolygon(Entity entity1, Entity entity2, World world) {
        return polygonPolygon(entity1.getShape().getVertices(), entity2.getShape().getVertices(), world);
    }

    /**
     * This method verifies if two Polygons overlap/intersect.
     * Uses {@link Collision#pointPolygon(float, float, float[], World)} for determining the collision.
     *
     * @param vertices1 vertices that form the first Polygon
     * @param vertices2 vertices that form the second Polygon
     * @param world world object reference
     * @return true if the two Polygons intersect, false otherwise
     */
    public static boolean polygonPolygon(float[] vertices1, float[] vertices2, World world) {
        for (int i = 0; i < vertices1.length; i += 2) {
            float x = vertices1[i];
            float y = vertices1[i + 1];
            if (pointPolygon(x, y, vertices2, world))
                return true;
        }
        for (int i = 0; i < vertices2.length; i += 2) {
            float x = vertices2[i];
            float y = vertices2[i + 1];
            if (pointPolygon(x, y, vertices1, world))
                return true;
        }

        return false;
    }

    /**
     * This method verifies if an Entity (or actually their {@link Entity#getShape()} are fully outside of the
     * Level boundaries.
     *
     * @param entity Entity to verify
     * @param world World object reference to retrieve the Level
     * @return true if the Entity is fully outside, false otherwise
     */
    public static boolean polygonFullyOutsideLevel(Entity entity, World world) {
        float[] vertices = entity.getShape().getVertices();
        for (int i = 0; i < vertices.length; i += 2) {
            float x = vertices[i];
            float y = vertices[i + 1];
            if (pointIsInLevel(x, y, world))
                return false;
        }
        return true;
    }

    /**
     * Verifies if the provided Point (x, y) is actually within the Level boundaries.
     *
     * @param x x-position for the Point
     * @param y y-position for the Point
     * @param world world object reference to retrieve the Level
     * @return true if the Point is inside the level, false otherwise
     */
    public static boolean pointIsInLevel(float x, float y, World world) {
        if (x < 0)
            return false;
        if (x > world.getLevel().getWidth())
            return false;
        if (y < 0)
            return false;
        if (y > world.getLevel().getHeight())
            return false;

        return true;
    }
}
