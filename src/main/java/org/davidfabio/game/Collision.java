package org.davidfabio.game;
import java.awt.geom.Line2D;

public class Collision {


    public static boolean circleCircle(float x1, float y1, float radius1, float x2, float y2, float radius2) {

        // NOTE (David): ugly temp hack, but for now it will do (this method will not be used in the future anyway)
        radius1 /= 2;
        radius2 /= 2;

        float distanceX = x1 - x2;
        float distanceY = y1 - y2;
        float distance = (float)Math.sqrt((distanceX * distanceX) + (distanceY * distanceY));

        if (distance <= radius1 + radius2)
            return true;

        return false;
    }



    public static void update(World world) {

        Player player = world.getPlayer();

        // player / enemies
        for (Enemy enemy : world.getEnemies()) {
            if (!enemy.getIsActive())
                continue;
            if (enemy.getIsSpawning())
                continue;
            if (polygonPolygon(player, enemy, world)) {
                enemy.attack(player, world);
                enemy.destroy(world);
            }
        }



        // player / pickups
        for (Pickup pickup : world.getPickups()) {
            if (!pickup.getIsActive())
                continue;
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
        return polygonPolygon(entity1.shape.getVertices(), entity2.shape.getVertices(), world);
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
