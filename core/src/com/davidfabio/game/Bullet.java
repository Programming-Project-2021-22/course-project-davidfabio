package com.davidfabio.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Bullet {

    // TODO: currently these bullets can only be used by the player (because of the way the collision detection is setup)

    float x, y;
    float radius = 8;
    float moveSpeed = 1600;
    float direction;
    Color color;
    boolean isActive = false;

    float firePower = 1.5f;



    public void init(float x, float y, float direction, Color color) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.color = color;
        isActive = true;
    }




    public void update(float deltaTime) {
        if (!isActive)
            return;

        float speed = moveSpeed * deltaTime;
        float deltaX = (float)Math.cos(direction) * speed;
        float deltaY = (float)Math.sin(direction) * speed;

        x += deltaX;
        y += deltaY;


        // check if bullets have gone offscreen
        if (x + radius < 0)
            isActive = false;
        else if (x - radius > Game.gameWidth)
            isActive = false;
        else if (y + radius < 0)
            isActive = false;
        else if (y - radius > Game.gameHeight)
            isActive = false;



        // check if bullets are colliding with enemies
        for (Enemy enemy : Game.enemies) {
            if (CollisionDetection.circleCircle(x, y, radius, enemy.x, enemy.y, enemy.radius)) {
                enemy.hit(firePower);
                isActive = false;
                break;
            }
        }


    }


    public void render(ShapeRenderer shape) {
        if (!isActive)
            return;

        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(color);
        shape.circle(Math.round(x), Game.gameHeight - Math.round(y), radius);
        shape.end();
    }



}
