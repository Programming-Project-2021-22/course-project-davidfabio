package com.davidfabio.game;

public class BulletPlayer extends Bullet {

    private float firePower = 1.0f;


    public void update(float deltaTime) {
        super.update(deltaTime);

        // ---------------- collision detection ----------------
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
