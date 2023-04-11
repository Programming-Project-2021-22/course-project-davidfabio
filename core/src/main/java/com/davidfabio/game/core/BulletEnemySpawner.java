package com.davidfabio.game.core;

public class BulletEnemySpawner {

    public enum ShootType {
        TOWARDS_PLAYER,
        CIRLCE
    }

    private float bulletSpeed;
    private float bulletScale;

    private float fireRate;
    private float fireRateCooldown;
    private int bulletsPerAttack;
    private int bulletsPerAttackCounter;
    private int attackCounter; // currently only used for changing rotation

    private float angle;
    private float rotationSpeed;
    private boolean rotateClockwise;
    private int changeRotationAfterXAttacks;

    private float attackRate;
    private float attackRateCooldown;

    private boolean isAttacking;

    private ShootType type;



    public BulletEnemySpawner(ShootType type, float fireRate, float attackRate, int bulletsPerAttack, float bulletSpeed, float bulletScale) {
        this.type = type;
        this.fireRate = fireRate;
        this.attackRate = attackRate;
        this.bulletsPerAttack = bulletsPerAttack;
        this.bulletSpeed = bulletSpeed;
        this.bulletScale = bulletScale;
        angle = 0;
        fireRateCooldown = 0;
        bulletsPerAttackCounter = 0;
        attackCounter =0;
        attackRateCooldown = attackRate;
        rotateClockwise = true;
        isAttacking = false;

        rotationSpeed = 0;
    }

    public BulletEnemySpawner(ShootType type, float fireRate, float attackRate, int bulletsPerAttack, float bulletSpeed, float bulletScale,
                              float rotationSpeed, int changeRotationAfterXAttacks) {
        this(type, fireRate, attackRate, bulletsPerAttack, bulletSpeed, bulletScale);

        this.rotationSpeed = rotationSpeed;
        this.changeRotationAfterXAttacks = changeRotationAfterXAttacks;
    }



    public void update(float deltaTime, World world, Enemy enemy) {
        if (isAttacking) {
            if (bulletsPerAttackCounter < bulletsPerAttack) {
                if (fireRateCooldown > 0)
                    fireRateCooldown -= deltaTime;

                if (fireRateCooldown <= 0) {
                    if (rotateClockwise)
                        angle -= rotationSpeed * deltaTime;
                    else
                        angle += rotationSpeed * deltaTime;

                    if (type == ShootType.TOWARDS_PLAYER)
                        shootTowardsPlayer(world, enemy);
                    else if (type == ShootType.CIRLCE) {
                        shoot(world, enemy);
                    }
                }
            }
            else {
                isAttacking = false;
                attackRateCooldown = attackRate;
                bulletsPerAttackCounter = 0;
                attackCounter += 1;
            }
        }
        else {
            if (attackRateCooldown > 0)
                attackRateCooldown -= deltaTime;

            if (attackRateCooldown <= 0) {
                isAttacking = true;

                if (type == ShootType.CIRLCE && attackCounter % changeRotationAfterXAttacks == 0) {
                    rotateClockwise = !rotateClockwise;
                }
            }
        }
    }


    public void shootTowardsPlayer(World world, Entity enemy) {
        BulletEnemy bullet = getBullet(world);
        float angleTowardsPlayer = enemy.getAngleTowards(world.getPlayer().getX(), world.getPlayer().getY());
        bullet.init(enemy.getX(), enemy.getY(), bulletScale, bulletSpeed, angleTowardsPlayer, enemy.getColorInitial());

        bulletsPerAttackCounter += 1;
        fireRateCooldown = fireRate;
    }

    public void shoot(World world, Entity enemy) {
        BulletEnemy bullet = getBullet(world);
        bullet.init(enemy.getX(), enemy.getY(), bulletScale, bulletSpeed, angle, enemy.getColorInitial());

        bulletsPerAttackCounter += 1;
        fireRateCooldown = fireRate;
    }

    public void shoot(World world, Entity enemy, float _angle) {
        BulletEnemy bullet = getBullet(world);
        bullet.init(enemy.getX(), enemy.getY(), bulletScale, bulletSpeed, _angle, enemy.getColorInitial());

        bulletsPerAttackCounter += 1;
        fireRateCooldown = fireRate;
    }



    public BulletEnemy getBullet(World world) {
        for (int i = 0; i < Settings.MAX_ENEMY_BULLETS; i += 1) {
            BulletEnemy bullet = world.getEnemyBullets()[i];
            if (!bullet.getIsActive() && !bullet.getToDestroyNextFrame()) {
                return bullet;
            }
        }
        return null;
    }


}
