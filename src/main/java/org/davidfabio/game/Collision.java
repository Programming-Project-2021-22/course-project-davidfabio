package org.davidfabio.game;
import java.awt.geom.Line2D;


public class Collision {

    public static void update(World world) {
        Player player = world.getPlayer();

        for (Enemy enemy : world.getEnemies()) {
            if (!enemy.getIsActive())
                continue;
            if (enemy.getIsSpawning())
                continue;

            for (Bullet playerBullet : player.getBullets()) {
                if (!playerBullet.getIsActive())
                    continue;

                // player bullet colliding with enemy
                if (polygonPolygon(playerBullet, enemy, world)) {
                    playerBullet.attack(enemy, world);
                    if (!enemy.getIsActive())
                        world.getScore().setPoints(world.getScore().getPoints() + Enemy.POINT_VALUE);

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
                    world.getScore().setPoints(world.getScore().getPoints() + Enemy.POINT_VALUE);
                    if (!player.getIsDashing())
                        world.destroyAllEnemies();
                }
            }
        }

        for (Bullet enemyBullet : world.getEnemyBullets()) {
            if (!enemyBullet.getIsActive())
                continue;

            // player colliding with enemy bullet
            if (polygonPolygon(enemyBullet, player, world)) {
                enemyBullet.attack(player, world);
                enemyBullet.setIsActive(false);

                if (!player.getIsDashing())
                    world.destroyAllEnemies();
            }
        }

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

    public static boolean polygonPolygon(Entity entity1, Entity entity2, World world) {
        return polygonPolygon(entity1.getShape().getVertices(), entity2.getShape().getVertices(), world);
    }

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

    public static boolean polygonFullyOusideLevel(Entity entity, World world) {
        float[] vertices = entity.getShape().getVertices();
        for (int i = 0; i < vertices.length; i += 2) {
            float x = vertices[i];
            float y = vertices[i + 1];
            if (pointIsInLevel(x, y, world))
                return false;
        }
        return true;
    }


    public static boolean pointIsInLevel(float x, float y, World world) {
        if (x < 0) return false;
        if (x > world.getLevel().getWidth()) return false;
        if (y < 0) return false;
        if (y > world.getLevel().getHeight()) return false;

        return true;
    }
}
