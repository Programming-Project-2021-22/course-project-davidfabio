package org.davidfabio.game;

import org.davidfabio.game.enemies.Enemy;

/**
 * This class allows the definition of Enemy attack behaviour. It allows Enemies to attack in a rotating motion, or target
 * an Enemy.
 */
public class BulletEnemySpawner {
    /**
     * Bullet velocity
     */
    private float bulletSpeed;

    /**
     * Bullet size
     */
    private float bulletScale;

    /**
     * How quickly new Bullets may be fired after eachother.
     */
    private float fireRate;

    /**
     * A timer that keeps track of the last firing. Once it's zero, we may fire again.
     */
    private float fireRateCooldown;

    /**
     * How many Bullets may be spawned for each attack.
     */
    private int bulletsPerAttack;

    /**
     * Counter that keeps track of how many bullets have been spawned for the current attack.
     */
    private int bulletsPerAttackCounter;

    /**
     * Angle at which the Bullet are to be shot.
     */
    private float angle;
    /**
     * When set, the bullets are being shot in a rotating motion. This speed indicates how quickly the rotation occurs.
     */
    private float rotationSpeed;
    /**
     * Defines if the rotation should be done clockwise or counter-clockwise.
     */
    private boolean rotateClockwise;
    /**
     * If true, the Bullets target the Player
     */
    private boolean targetPlayer;

    /**
     * Time between attacks
     */
    private float attackRate;

    /**
     * Timer that keeps track of the time that has passed since the attack.
     */
    private float attackRateCooldown;

    /**
     * Specifies whether the Enemy is currently attacking.
     */
    private boolean isAttacking;

    /**
     * Constructor for the BulletEnemySpawner. This method initializes the Object.
     *
     * @param targetPlayer defines if the Attacks should focus the Player
     * @param fireRate defines the rate at which bullets are being fired during an Attack
     * @param attackRate defines the rate at which attacks are being executed
     * @param bulletsPerAttack how many bullets may be spawned per attack
     * @param bulletSpeed how fast the Bullets travel
     * @param bulletScale the size of the Bullets
     */
    public BulletEnemySpawner(boolean targetPlayer, float fireRate, float attackRate, int bulletsPerAttack, float bulletSpeed, float bulletScale) {
        this.targetPlayer = targetPlayer;
        this.fireRate = fireRate;
        this.attackRate = attackRate;
        this.bulletsPerAttack = bulletsPerAttack;
        this.bulletSpeed = bulletSpeed;
        this.bulletScale = bulletScale;
        angle = 0;
        fireRateCooldown = 0;
        bulletsPerAttackCounter = 0;
        attackRateCooldown = attackRate;
        rotateClockwise = true;
        isAttacking = false;

        rotationSpeed = 0;
    }

    /**
     *
     * @param targetPlayer defines if the Attacks should focus the Player
     * @param fireRate defines the rate at which bullets are being fired during an Attack
     * @param attackRate defines the rate at which attacks are being executed
     * @param bulletsPerAttack how many bullets may be spawned per attack
     * @param bulletSpeed how fast the Bullets travel
     * @param bulletScale the size of the Bullets
     * @param rotationSpeed velocity of rotating motion
     * @param rotateClockwise clockwise or counter-clockwise rotation
     */
    public BulletEnemySpawner(boolean targetPlayer, float fireRate, float attackRate, int bulletsPerAttack, float bulletSpeed, float bulletScale, float rotationSpeed, boolean rotateClockwise) {
        this(targetPlayer, fireRate, attackRate, bulletsPerAttack, bulletSpeed, bulletScale);

        this.rotationSpeed = rotationSpeed;
        this.rotateClockwise = rotateClockwise;
    }

    /**
     * Updates the BulletEnemySpawner's state.
     * This method starts an attack, provided it's not on cooldown.
     * If an Attack is currently active, it starts shooting Bullets in the provided {@link BulletEnemySpawner#fireRate}.
     *
     * @param deltaTime Delta by which the game loop updated
     * @param world World object reference used to retrieve inactive Enemy Bullets
     * @param enemy Enemy object reference that shoots the Bullets
     */
    public void update(float deltaTime, World world, Enemy enemy) {
        if (isAttacking) {
            if (bulletsPerAttackCounter < bulletsPerAttack) {
                if (fireRateCooldown > 0)
                    fireRateCooldown -= deltaTime;

                if (fireRateCooldown <= 0) {
                    if (rotateClockwise)
                        angle += rotationSpeed * deltaTime;
                    else
                        angle -= rotationSpeed * deltaTime;

                    shoot(world, enemy);
                }
            }
            else {
                isAttacking = false;
                attackRateCooldown = attackRate;
                bulletsPerAttackCounter = 0;
            }
        }
        else {
            if (attackRateCooldown > 0)
                attackRateCooldown -= deltaTime;

            if (attackRateCooldown <= 0) {
                isAttacking = true;
            }
        }
    }

    /**
     * Retrieves an inactive Bullet from the World ({@link World#getEnemyBullets()}).
     * If a Bullet was found, it's activated and shot in the direction of the Player (if {@link BulletEnemySpawner#targetPlayer}
     * is active).
     *
     * @param world World object reference containing the Enemy Bullets
     * @param enemy Enemy that shoots the Bullet and provides its start position
     */
    public void shoot(World world, Entity enemy) {
        if (targetPlayer)
            angle = enemy.getAngleTowards(world.getPlayer().getX(), world.getPlayer().getY());

        Bullet bullet = getEnemyBullet(world);
        bullet.init(enemy.getX(), enemy.getY(), bulletScale, bulletSpeed, angle, enemy.getColorInitial(), PolygonShape.getEnemyBulletShape(bulletScale));

        bulletsPerAttackCounter += 1;
        fireRateCooldown = fireRate;
    }

    /**
     * Retrieves an inactive Bullet from the World ({@link World#getEnemyBullets()}).
     * If a Bullet was found, it's activated and shot in the provided angle.
     * The Bullet is shot from the Enemy-position.
     *
     * @param world World object reference containing the Enemy Bullets
     * @param enemy Enemy that shoots the Bullet and provides its start position
     * @param _angle Angle at which the Bullet is shot
     */
    public void shoot(World world, Entity enemy, float _angle) {
        Bullet bullet = getEnemyBullet(world);
        if (bullet == null)
            return; // No bullet available

        bullet.init(enemy.getX(), enemy.getY(), bulletScale, bulletSpeed, _angle, enemy.getColorInitial(), PolygonShape.getEnemyBulletShape(bulletScale));

        bulletsPerAttackCounter += 1;
        fireRateCooldown = fireRate;
    }

    /**
     * Returns the first inactive Enemy bullet that was found in the provided world ({@link World#getEnemyBullets()}).
     *
     * @param world World object reference that holds the Enemy Bullets
     * @return the first inactive Enemy bullet in the provided World, if non is inactive, returns null
     */
    public Bullet getEnemyBullet(World world) {
        for (int i = 0; i < world.getEnemyBullets().length; i += 1) {
            Bullet bullet = world.getEnemyBullets()[i];
            if (!bullet.getIsActive())
                return bullet;
        }
        return null;
    }
}
