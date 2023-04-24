package org.davidfabio.game;

import java.awt.geom.Line2D;

// TODO: remove this class and use libgdx's in-built collision detection methods


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

        return  (intersectionsCount % 2 != 0);
    }




}
