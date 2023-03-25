package com.davidfabio.game;

public class EnemyBullet extends Entity {

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


        if (Collision.circleCircle(getX(), getY(), getRadius(), Game.player.getX(), Game.player.getY(), Game.player.getRadius())) {

            // we leave the bullet alive for 1 extra frame, so that we can draw it at the exact position where it touches the player
            toDestroyNextFrame = true;
            while (Collision.circleCircle(getX(), getY(), getRadius(), Game.player.getX(), Game.player.getY(), Game.player.getRadius())) {
                setX(getX() - (float)Math.cos(getDirection()));
                setY(getY() - (float)Math.sin(getDirection()));
            }
        }





    }


}
