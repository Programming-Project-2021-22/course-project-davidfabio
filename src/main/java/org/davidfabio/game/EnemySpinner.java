package org.davidfabio.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;

public class EnemySpinner extends Enemy {


    private float rotationSpeed = 40;
    private float rotationAngle ;


    @Override
    public void init(float x, float y, float scale, float moveSpeed, int newInitialHealth, Color color) {
        super.init(x, y, scale, moveSpeed, newInitialHealth, color);
        setType(Type.SPINNER);
        shape = PolygonShape.getEnemyShape(getType(), scale);

        rotationAngle = 0;
    }



    @Override
    public void render(PolygonSpriteBatch polygonSpriteBatch) {
        if (!getIsActive())
            return;

        shape.render(polygonSpriteBatch, getX(), getY(), rotationAngle * rotationSpeed, getColor());
    }


    @Override
    public void update(float deltaTime, World world) {
        super.update(deltaTime, world);

        if (!getIsActive())
            return;
        if (getIsSpawning())
            return;

        rotationAngle += rotationSpeed * deltaTime;

        float angleTowardsPlayer = getAngleTowards(world.getPlayer().getX(), world.getPlayer().getY());
        setAngle(angleTowardsPlayer);

        moveTowards(getAngle(), deltaTime);
    }

}
