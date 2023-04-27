package org.davidfabio.game;

import org.davidfabio.utils.Settings;

public class BulletEnemySpawner {

    private float bulletSpeed;
    private float bulletScale;

    private float fireRate;
    private float fireRateCooldown;
    private int bulletsPerAttack;
    private int bulletsPerAttackCounter;

    private float angle;
    private float rotationSpeed;
    private boolean rotateClockwise;
    private boolean targetPlayer;

    private float attackRate;
    private float attackRateCooldown;

    private boolean isAttacking;


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

    public BulletEnemySpawner(boolean targetPlayer, float fireRate, float attackRate, int bulletsPerAttack, float bulletSpeed, float bulletScale,
                              float rotationSpeed, boolean rotateClockwise) {
        this(targetPlayer, fireRate, attackRate, bulletsPerAttack, bulletSpeed, bulletScale);

        this.rotationSpeed = rotationSpeed;
        this.rotateClockwise = rotateClockwise;
    }



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



    public void shoot(World world, Entity enemy) {

        if (targetPlayer)
            angle = enemy.getAngleTowards(world.getPlayer().getX(), world.getPlayer().getY());

        Bullet bullet = getEnemyBullet(world);
        bullet.init(enemy.getX(), enemy.getY(), bulletScale, bulletSpeed, angle, enemy.getColorInitial());
        bullet.setShape(PolygonShape.getEnemyBulletShape(bulletScale));

        bulletsPerAttackCounter += 1;
        fireRateCooldown = fireRate;
    }

    public void shoot(World world, Entity enemy, float _angle) {
        Bullet bullet = getEnemyBullet(world);
        bullet.init(enemy.getX(), enemy.getY(), bulletScale, bulletSpeed, _angle, enemy.getColorInitial());
        bullet.setShape(PolygonShape.getEnemyBulletShape(bulletScale));

        bulletsPerAttackCounter += 1;
        fireRateCooldown = fireRate;
    }



    public Bullet getEnemyBullet(World world) {
        for (int i = 0; i < Settings.MAX_ENEMY_BULLETS; i += 1) {
            Bullet bullet = world.getEnemyBullets()[i];
            if (!bullet.getIsActive())
                return bullet;
        }
        return null;
    }


}
