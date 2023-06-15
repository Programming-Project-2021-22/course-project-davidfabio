package org.davidfabio.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.davidfabio.input.Inputs;
import org.davidfabio.input.Mouse;
import org.davidfabio.utils.Pulsation;
import org.davidfabio.utils.Settings;
import org.davidfabio.utils.Transform2D;

import java.util.Random;

/**
 * This class represents the Player during the gameplay.
 */
public class Player extends Entity implements Attackable, Attacker {
    /**
     * Indicates the frequency at which the player may shoot bullets.
     */
    private float fireRate = 0.07f;
    /**
     * Indicates the time since the last bullet was fired. This number will be increased by {@link Player#fireRate}
     * in order to increase the cooldown after shooting. At each {@link Player#update(float, World)} the number will be
     * decreased by deltaTime. Once the number is again 0, the player may shoot again.
     */
    private float fireRateCooldown = 0.0f;
    /**
     * Velocity for Player bullets.
     */
    private float bulletSpeed = 800;
    /**
     * Scale for Player bullets.
     */
    private float bulletScale = 32;
    /**
     * Indicates the maximum angle offset that a bullet may be shot. If it's 8, this means that bullets will shoot in
     * the direction indicated with the mouse, +/- 8 degrees of random spread.
     */
    private float bulletSpreadMax = 8;
    /**
     * The health a Player starts a game with.
     */
    private int initialHealth = 3;
    /**
     * The health a Player has left. Initialized by {@link Player#initialHealth}.
     */
    private int health;
    /**
     * A list that stores all {@link Bullet}s a player may shoot. This is initialized at the {@link Player#init} method
     * in order to reduce memory allocation during gameplay. This Array is initialized to {@link Settings#MAX_PLAYER_BULLETS} size.
     */
    private Bullet[] bullets = new Bullet[Settings.MAX_PLAYER_BULLETS];
    /**
     * Indicates the velocity at which the player dashes.
     */
    private float dashSpeed = 800;
    /**
     * Indicates how long a player dash takes.
     */
    private float dashDuration = 0.2f;
    /**
     * Stores the dash angle, as this will be saved once the dash starts and should not be influenced by the Mouse.
     */
    private float dashAngle;
    /**
     * Stores the {@link Player#dashDuration} in order to keep track of the current dash's remaining time.
     * Once this is 0 (or less) the player has finished dashing.
     */
    private float dashDurationCooldown;
    /**
     * Indicates whether the player is currently dashing.
     */
    private boolean isDashing;
    /**
     * Indicates whether the player is currently selecting the dashing-angle.
     */
    private boolean inDashChooseDirectionState;
    /**
     * These variables keep track of the trail where the player dashed through. These are needed for the visual dash effect.
     */
    private float[] dashPositionsX, dashPositionsY, dashTrailTransparencies;
    /**
     * This keeps track of the number of "snapshots" are taken during a dash. This indicates the number of elements in
     * {@link Player#dashPositionsX}, {@link Player#dashPositionsY}, {@link Player#dashTrailTransparencies}.
     */
    private int dashPositionsCount = 0;

    /**
     * Indicates if the player is currently invulnerable due to being recently hit. (Meaning the player is in Hit-state.)
     */
    private boolean isInHitState;
    /**
     * Indicates how long the so-called Hit-state ({@link Player#isInHitState}) lasts.
     */
    private float hitDuration = 2.5f;
    /**
     * Keeps track of the current Hit-state's ({@link Player#isInHitState}) remaining duration.
     */
    private float hitCooldown;
    /**
     * Used to keep track of the Player transparency during a Hit-state ({@link Player#isInHitState}).
     */
    private Pulsation pulsationHitTransparency;

    /**
     * The Attack Power is the damage a Player instance deals to an Enemy instance when hitting it while dashing.
     */
    private int attackPower = 20;
    /**
     * The Pickups Collected Variable stores how many pickups the Player has picked up in total (This may be useful for
     * statistics).
     */
    private int pickupsCollected = 0;
    /**
     * The Current Pickup Collection stores how many Pickups the Player has collected since last taking damage. This is
     * used to calculate a Multiplier for the Player's score.
     */
    private int currentPickupCollection = 0;
    /**
     * This is the number of {@link Player#currentPickupCollection} required in order to gain a higher multiplier.
     * For example if this is 10, the {@link Player#getMultiplier()} is calculated using {@link Player#currentPickupCollection}
     * divided by 10. Only the integer Part is used and it cannot be lower than 1. The maximum Multiplier is defined in
     * the Settings {@link Settings#MAX_MULTIPLIER}.
     */
    private static int pickupMultiplierDivisor = 10;

    /**
     * @return the Player's current health.
     */
    public int getHealth() {
        return health;
    }

    /**
     * @param newHealth the Player's new health
     */
    public void setHealth(int newHealth) {
        health = newHealth;
    }

    /**
     * @return the Player's starting/initial health
     */
    public int getInitialHealth() {
        return initialHealth;
    }

    /**
     * @param newInitialHealth the Player's new starting/initial health
     */
    public void setInitialHealth(int newInitialHealth) {
        initialHealth = newInitialHealth;
    }

    /**
     * @param isInHitState sets the Player's {@link Player#isInHitState}.
     */
    public void setIsInHitState(boolean isInHitState) {
        this.isInHitState = isInHitState;
    }

    /**
     * @return the Player's current {@link Player#isInHitState}.
     */
    public boolean getIsInHitState() {
        return isInHitState;
    }

    /**
     * @return the Player's attack power (used for collisions).
     */
    public int getAttackPower() {
        return attackPower;
    }

    /**
     * @return the amount of total Pickups collected.
     */
    public int getPickupsCollected() {
        return pickupsCollected;
    }

    /**
     * @param hitCooldown the new {@link Player#hitCooldown}
     */
    public void setHitCooldown(float hitCooldown) {
        this.hitCooldown = hitCooldown;
    }

    /**
     * @return {@link Player#hitDuration}.
     */
    public float getHitDuration() {
        return hitDuration;
    }

    /**
     * @return {@link Player#isDashing}
     */
    public boolean getIsDashing() {
        return isDashing;
    }

    /**
     * @return {@link Player#bullets}
     */
    public Bullet[] getBullets() {
        return bullets;
    }

    /**
     * The PolygonShape which is used to indicate the direction in which the Player is shooting in.
     * This is purely cosmetic.
     */
    private PolygonShape shapeArrow;
    /**
     * The scale used to draw the {@link Player#shapeArrow}.
     */
    private float arrowScale = 16;
    /**
     * The offset used to draw the {@link Player#shapeArrow}.
     */
    private float arrowOffset = 24;
    /**
     * The random number generator used in combination with {@link Player#bulletSpreadMax}.
     */
    private Random random;

    /**
     * The initialization method used instead of the Constructor. We explicitly created this method to avoid
     * memory-reallocation.
     *
     * @param x x-position for the Player
     * @param y y-position for the Player
     * @param scale scale for the Player's shape
     * @param moveSpeed Player's movement-speed
     * @param color color for the Player
     */
    public void init(float x, float y, float scale, float moveSpeed, Color color)  {
        super.init(x, y, scale, color);
        this.setMoveSpeed(moveSpeed);
        this.initializeHealth();

        random = new Random();

        pulsationHitTransparency = new Pulsation(4, 0.25f);
        isDashing = false;
        inDashChooseDirectionState = false;

        int dashPositionMax = 32;
        dashPositionsX = new float[dashPositionMax];
        dashPositionsY = new float[dashPositionMax];
        dashTrailTransparencies = new float[dashPositionMax];

        for (int i = 0; i < Settings.MAX_PLAYER_BULLETS; i += 1)
            bullets[i] = new Bullet();
        
        setShape(PolygonShape.getPlayerShape(scale));
        shapeArrow = PolygonShape.getPlayerArrowShape(arrowScale);
    }

    /**
     * This method actually draws the Player on the screen.
     * This method renders the Player itself, the direction arrow, a preview-line for dashing, the dashing effect,
     * and event the Player's bullets.
     *
     * @param polygonSpriteBatch rendering batch for anything that is drawn using Sprites
     * @param shapeRenderer rendering batch for anything that is drawn using Shapes
     */
    public void render(PolygonSpriteBatch polygonSpriteBatch, ShapeRenderer shapeRenderer) {
        // main shape (circle)
        Color color = getColor();
        if (isInHitState)
            color.a = pulsationHitTransparency.getCounter() + 0.6f;
        getShape().render(polygonSpriteBatch, color);

        // direction arrow
        shapeArrow.render(polygonSpriteBatch, Color.LIGHT_GRAY);

        // dash "preview" line + circle outline
        if (inDashChooseDirectionState) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(getColorInitial());
            float dashLineLength = dashSpeed * dashDuration;
            float endX = Transform2D.translateX(getX(), getAngle(), dashLineLength);
            float endY = Transform2D.translateY(getY(), getAngle(), dashLineLength);
            shapeRenderer.line(getX(), getY(), endX, endY);
            shapeRenderer.circle(endX, endY, getScale() / 2);
            shapeRenderer.end();
        }

        // dash trail effect
        if (isDashing) {
            for (int i = dashPositionsCount; i > 0; i -= 1) {
                Color _color = new Color(getColor().r, getColor().g, getColor().b, dashTrailTransparencies[i]);
                getShape().resetPosition();
                getShape().translatePosition(dashPositionsX[i], dashPositionsY[i]);
                getShape().render(polygonSpriteBatch, _color);
            }
        }

        // bullets
        for (int i = 0; i < Settings.MAX_PLAYER_BULLETS; i += 1)
            bullets[i].render(polygonSpriteBatch, shapeRenderer);
    }

    /**
     * Updates the Player's state.
     * This method is in charge of reading the {@link Inputs} and update the Player's state accordingly.
     * It also updates the Shapes and transparencies related to the Player class.
     * It also updates the bullets ({@link Player#bullets}).
     *
     * @param deltaTime Delta by which the game loop updated
     * @param world world object reference, needed for side-effects or references of other objects in the world.
     */
    public void update(float deltaTime, World world) {
        if (isInHitState) {
            hitCooldown -= deltaTime;
            pulsationHitTransparency.update(deltaTime);

            if (hitCooldown < 0) {
                setColor(getColorInitial());
                isInHitState = false;
            }
        }

        setAngle((float)Math.atan2(Mouse.getY() - getY(), Mouse.getX() - getX())); // update direction

        // ---------------- movement ----------------
        float speed = getMoveSpeed() * deltaTime;

        // normalize diagonal movement
        if ((Inputs.moveUp.getIsDown() || Inputs.moveDown.getIsDown()) && (Inputs.moveLeft.getIsDown() || Inputs.moveRight.getIsDown()))
            speed *= 0.707106f;

        float nextX = getX();
        float nextY = getY();
        if (Inputs.moveUp.getIsDown())
            nextY -= speed;
        if (Inputs.moveDown.getIsDown())
            nextY += speed;
        if (Inputs.moveLeft.getIsDown())
            nextX -= speed;
        if (Inputs.moveRight.getIsDown())
            nextX += speed;

        // moving normally
        if (!isDashing && !inDashChooseDirectionState) {
            setX(nextX);
            setY(nextY);
            restrictToLevel(world.getLevel());

            if (Inputs.dash.getWasPressed())
                inDashChooseDirectionState = true;
        }
        // start dashing
        else if (!isDashing && Inputs.dash.getWasReleased()) {
            isDashing = true;
            dashDurationCooldown = dashDuration;
            inDashChooseDirectionState = false;
            dashAngle = getAngle();
            Sounds.playDashSfx();
        }
        // while dashing
        else if (isDashing) {
            dashPositionsCount += 1;
            dashPositionsX[dashPositionsCount] = getX();
            dashPositionsY[dashPositionsCount] = getY();
            dashDurationCooldown -= deltaTime;
            dashTrailTransparencies[dashPositionsCount] = dashPositionsCount * deltaTime * 2;

            if (dashDurationCooldown < 0) {
                isDashing = false;
                dashPositionsCount = 0;
            }

            float _speed = dashSpeed * deltaTime;

            float _nextX = Transform2D.translateX(getX(), dashAngle, _speed);
            float _nextY = Transform2D.translateY(getY(), dashAngle, _speed);
            setX(_nextX);
            setY(_nextY);
            restrictToLevel(world.getLevel());
        }

        // ---------------- shooting ----------------
        if (fireRateCooldown > 0)
            fireRateCooldown -= deltaTime;

        if (!isDashing && !inDashChooseDirectionState && Inputs.shoot.getIsDown() && fireRateCooldown <= 0)
            shoot();

        for (int i = 0; i < Settings.MAX_PLAYER_BULLETS; i += 1) {
            bullets[i].update(deltaTime, world);
        }

        // ---------------- update shape vertices ----------------
        getShape().resetPosition();
        getShape().translatePosition(this);

        shapeArrow.resetPosition();
        shapeArrow.rotate(getAngle());
        float arrowX = Transform2D.translateX(getX(), getAngle(), arrowOffset);
        float arrowY = Transform2D.translateY(getY(), getAngle(), arrowOffset);
        shapeArrow.translatePosition(arrowX, arrowY);
    }

    /**
     * This method iterates over the {@link Player#bullets} array and looks for a non-active Bullet.
     * Whether or not a Bullet is active is checked using the {@link Bullet#getIsActive} method.
     * @return If a non-active Bullet was found, this Method returns the Bullet. Otherwise it returns null.
     */
    public Bullet getBullet() {
        for (int i = 0; i < bullets.length; i += 1) {
            if (!bullets[i].getIsActive())
                return bullets[i];
            }

        return null;
    }

    /**
     * This method is used to initialize a new Bullet from the Player Bullets array ({@link Player#bullets}).
     * First a non-active Bullet is searched using the {@link Player#getBullet()} method. If one is found, it's
     * activated and starts traveling in the direction the player was pointing at.
     * In the end a Shooting sound is played as well.
     */
    public void shoot() {
        Bullet bullet = getBullet();
        if (bullet == null)
            return; // No more bullets available. Shooting is not possible.

        float randomFloat = random.nextFloat() - 0.5f;
        float angleDelta = Transform2D.degreesToRadians(randomFloat * bulletSpreadMax);
        bullet.init(getX(), getY(), bulletScale, bulletSpeed, getAngle() + angleDelta, Color.GOLD, PolygonShape.getPlayerBulletShape(bulletScale));

        fireRateCooldown = fireRate;
        Sounds.playShootSfx();
    }

    /**
     * This method increases the Player's Pickup Count by 1. Both {@link Player#currentPickupCollection} and
     * {@link Player#pickupsCollected} are incremented in this method.
     */
    public void incrementPickups() {
        currentPickupCollection += 1;
        pickupsCollected += 1;
    }

    /**
     * This method resets the Player's {@link Player#currentPickupCollection} to 0. This method is used to reset the
     * Points multiplier once the player gets hit.
     */
    public void resetCurrentPickupCollection() {
        currentPickupCollection = 0;
    }

    /**
     * This method returns a multiplier by which the gained points are multiplied.
     * The multiplier is calculated using the currentPickup-collection. The pickups collected are divided by {@link Player#pickupMultiplierDivisor}
     * and only the integer part of this is used. The Multiplier cannot be lower than 1 and cannot exceed {@link Settings#MAX_MULTIPLIER}.
     * @return An integer value by which the gained points are multiplied.
     */
    public int getMultiplier() {
        int multiplier = (int)currentPickupCollection/Player.pickupMultiplierDivisor + 1;
        return multiplier < Settings.MAX_MULTIPLIER ? multiplier : Settings.MAX_MULTIPLIER;
    }
}
