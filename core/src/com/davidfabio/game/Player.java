package com.davidfabio.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

public class Player {

    float x = Game.gameWidth / 2;
    float y = Game.gameHeight / 2;
    float radius = 32;
    float lookDir;
    Color color = Color.RED;

    float moveSpeed = 260;




    public void update(float deltaTime) {

        // ---------------- movement ----------------
        float speed = moveSpeed * deltaTime;

        // normalize diagonal movement
        if ((Inputs.up.isDown || Inputs.down.isDown) && (Inputs.left.isDown || Inputs.right.isDown))
            speed *= 0.707106f;

        if (Inputs.up.isDown)    y += speed;
        if (Inputs.down.isDown)  y -= speed;
        if (Inputs.left.isDown ) x -= speed;
        if (Inputs.right.isDown) x += speed;

        // prevent player from going offscreen
        x = Math.max(x, radius);
        x = Math.min(x, Game.gameWidth - radius);
        y = Math.max(y, radius);
        y = Math.min(y, Game.gameHeight - radius);



        if (Inputs.space.wasPressed)
            switchColor();
    }


    public void render(ShapeRenderer shape) {
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(color);
        shape.circle(Math.round(x), Math.round(y), radius);
        shape.end();
    }



    void switchColor() {
        if (color == Color.RED)
            color = Color.BLUE;
        else
            color = Color.RED;
    }

}
