package com.davidfabio.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class BulletPlayer extends Bullet {

    private float firePower = 1.0f;
    private float width = 8;
    private float height = 32;

    @Override public void render(ShapeRenderer shape, Color _color) {
        if (!getIsActive())
            return;

        float _x = getX() - (width / 2);
        float _y = getY() - (height / 2);
        float angle = radiansToDegrees(getDirection() + (float)(Math.PI / 2));

        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(_color);
        shape.ellipse(_x, _y, width, height, angle);
        shape.end();
    }



    public void update(float deltaTime) {
        super.update(deltaTime);

        if (!getIsActive())
            return;


        // ---------------- collision detection ----------------
        for (Enemy enemy : Game.enemies) {
            if (!enemy.getIsActive())
                continue;
            if (enemy.getIsSpawning())
                continue;

            if (Collision.circleCircle(getX(), getY(), getRadius(), enemy.getX(), enemy.getY(), enemy.getRadius())) {

                float _firePower = firePower;
                if (getPolarity() != enemy.getPolarity()) {
                    _firePower *= 2;
                }
                enemy.hit(_firePower);

                // we leave the bullet alive for 1 extra frame, so that we can draw it at the exact position where it touches the enemy
                // TODO (David): now that we changed player bullets to an ellipsis shape, we have to change the collision detection as well
                setToDestroyNextFrame(true);
                while (Collision.circleCircle(getX(), getY(), getRadius(), enemy.getX(), enemy.getY(), enemy.getRadius())) {
                    setX(getX() - (float)Math.cos(getDirection()));
                    setY(getY() - (float)Math.sin(getDirection()));
                }
                break;
            }
        }
    }


}
