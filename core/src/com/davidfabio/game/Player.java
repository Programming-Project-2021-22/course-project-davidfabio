package com.davidfabio.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

public class Player {

    float x, y;
    float radius = 28;
    float lookDirection; // in radians
    Color color = Color.RED;

    float moveSpeed = 260;
    float fireRate = 0.04f;
    float fireRateCooldown = 0.0f;

    final int MAX_BULLETS = 128;
    Bullet[] bullets = new Bullet[MAX_BULLETS];



    public void init() {
       x = Game.gameWidth / 2;
       y = Game.gameHeight / 2;

        for (int i = 0; i < MAX_BULLETS; i += 1) {
            bullets[i] = new Bullet();
        }
    }



    public void update(float deltaTime) {

        // ---------------- movement ----------------

        lookDirection = (float)Math.atan2(Inputs.Mouse.y - y, Inputs.Mouse.x - x);

        float speed = moveSpeed * deltaTime;

        // normalize diagonal movement
        if ((Inputs.up.isDown || Inputs.down.isDown) && (Inputs.left.isDown || Inputs.right.isDown))
            speed *= 0.707106f;

        if (Inputs.up.isDown)    y -= speed;
        if (Inputs.down.isDown)  y += speed;
        if (Inputs.left.isDown ) x -= speed;
        if (Inputs.right.isDown) x += speed;

        // prevent player from going offscreen
        x = Math.max(x, radius);
        x = Math.min(x, Game.gameWidth - radius);
        y = Math.max(y, radius);
        y = Math.min(y, Game.gameHeight - radius);



        if (Inputs.space.wasPressed)
            switchColor();




        // ---------------- shooting ----------------
        if (fireRateCooldown > 0)
            fireRateCooldown -= deltaTime;

        if (Inputs.Mouse.left.isDown && fireRateCooldown <= 0) {
            // get new bullet
            for (int i = 0; i < MAX_BULLETS; i += 1) {
                if (!bullets[i].isActive) {
                    bullets[i].init(x, y, lookDirection, color);
                    fireRateCooldown = fireRate;
                    break;
                }
            }
        }

        for (int i = 0; i < MAX_BULLETS; i += 1) {
            if (!bullets[i].isActive)
                continue;

            bullets[i].update(deltaTime);
        }


    }


    public void render(ShapeRenderer shape) {
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(color);
        shape.circle(Math.round(x), Game.gameHeight - Math.round(y), radius);
        shape.end();

        for (int i = 0; i < MAX_BULLETS; i += 1) {
            if (!bullets[i].isActive)
                continue;

            bullets[i].render(shape);
        }
    }



    void switchColor() {
        if (color == Color.RED)
            color = Color.BLUE;
        else
            color = Color.RED;
    }

}
