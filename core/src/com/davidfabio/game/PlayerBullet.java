package com.davidfabio.game;

public class PlayerBullet extends Entity {

    private boolean toDestroyNextFrame = false;
    private float firePower = 1.0f;

    public boolean getToDestroyNextFrame() { return toDestroyNextFrame; }



    public void init(float x, float y, float radius, float direction, Polarity polarity, float moveSpeed) {
        super.init(x, y, radius, direction, polarity);
        setMoveSpeed(moveSpeed);
    }


    public void update(float deltaTime) {
        if (toDestroyNextFrame) {
            toDestroyNextFrame = false;
            setActive(false);
        }
        if (!getActive())
            return;

        moveTowards(getDirection(), deltaTime);



        // ---------------- collision detection ----------------
        if (!isInView())
            setActive(false);

        for (int i = 0; i < Game.MAX_ENEMIES; i += 1) {
            Enemy enemy = Game.enemies[i];

            if (!enemy.getActive())
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
                toDestroyNextFrame = true;
                while (Collision.circleCircle(getX(), getY(), getRadius(), enemy.getX(), enemy.getY(), enemy.getRadius())) {
                    setX(getX() - (float)Math.cos(getDirection()));
                    setY(getY() - (float)Math.sin(getDirection()));
                }
                break;
            }
        }


    }


}
