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

            // player colliding with enemy
            if (polygonPolygon(player, enemy, world)) {
                enemy.attack(player, world);
                enemy.destroy(world);
            }

            for (BulletPlayer bulletPlayer : player.getBullets()) {
                if (!bulletPlayer.getIsActive())
                    continue;

                // player bullet colliding with enemy
                if (polygonPolygon(bulletPlayer, enemy, world)) {
                    bulletPlayer.attack(enemy, world);
                    if (!enemy.getIsActive())
                        world.getScore().setPoints(world.getScore().getPoints() + Enemy.POINT_VALUE);

                    bulletPlayer.setIsActive(false);
                }
            }
        }

        for (BulletEnemy bulletEnemy : world.getEnemyBullets()) {
            if (!bulletEnemy.getIsActive())
                continue;

            // player colliding with enemy bullet
            if (polygonPolygon(bulletEnemy, player, world)) {
                bulletEnemy.attack(player, world);
                bulletEnemy.setIsActive(false);
            }
        }

        for (Pickup pickup : world.getPickups()) {
            if (!pickup.getIsActive())
                continue;

            // player colliding with pickup
            if (polygonPolygon(player, pickup, world))
                pickup.setIsActive(false);
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




}
