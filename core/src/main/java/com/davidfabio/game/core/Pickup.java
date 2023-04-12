package com.davidfabio.game.core;

import com.badlogic.gdx.graphics.Color;


public class Pickup extends Entity implements Movable {


    private float moveDistance = 80;



    public void init(float x, float y, float scale, Color color) {
        super.init(x, y, scale, color);
        shape = new PolygonShape(4, scale);
    }

    public void update(float deltaTime, World world) {
        if (!getIsActive())
            return;

        float playerX = world.getPlayer().getX();
        float playerY = world.getPlayer().getY();
        float distanceToPlayer = getDistanceTo(playerX, playerY);

        if (distanceToPlayer < moveDistance) {
            float speed = (moveDistance - distanceToPlayer) * deltaTime * 200;
            setMoveSpeed(speed);
            moveTowards(playerX, playerY, deltaTime);
        }
    }




}
