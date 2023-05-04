package org.davidfabio.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.davidfabio.PolygonWars;
import org.davidfabio.game.enemies.*;
import org.davidfabio.ui.GameOverScreen;
import org.davidfabio.utils.Settings;

import java.util.ArrayList;

public class World {
    private Player player;
    private Enemy[] enemies;
    private Bullet[] enemyBullets;
    private EnemySpawner enemySpawner;
    private Pickup[] pickups;
    private Particle[] particles;
    private Level level;
    private Score score;
    private ArrayList<Score> scores;

    public Player getPlayer() { return player; }
    public Enemy[] getEnemies() { return enemies; }
    public Bullet[] getEnemyBullets() { return enemyBullets; }
    public Pickup[] getPickups() { return pickups; }
    public Particle[] getParticles() { return particles; }
    public Level getLevel() { return level; }
    public Score getScore() { return score; }



    public World(ArrayList<Score> scores) {
        level = new Level(Settings.levelWidth, Settings.levelHeight);
        score = new Score();
        this.scores = scores;

        player = new Player();
        player.init(level.getWidth() / 2, level.getHeight() / 2, 32, 240, Color.GOLD);

        int enemyTypesCount = Enemy.Type.values().length;
        Settings.MAX_ENEMIES = Settings.MAX_ENEMIES_PER_TYPE * enemyTypesCount;
        enemies = new Enemy[Settings.MAX_ENEMIES];
        int currentTypeIndex = 0;

        for (int i = 0; i < Settings.MAX_ENEMIES; i += 1) {
            Enemy.Type currentType = Enemy.Type.values()[currentTypeIndex];
            switch (currentType) {
                case BUBBLE:   enemies[i] = new EnemyBubble(); break;
                case CHASER:   enemies[i] = new EnemyChaser(); break;
                case KAMIKAZE: enemies[i] = new EnemyKamikaze(); break;
                case STAR:     enemies[i] = new EnemyStar(); break;
                case TURRET:   enemies[i] = new EnemyTurret(); break;
            }

            if (i % Settings.MAX_ENEMIES_PER_TYPE == 0 && i > 0 && currentTypeIndex < enemyTypesCount - 1)
                currentTypeIndex += 1;
        }



        enemySpawner = new EnemySpawner(this);

        enemyBullets = new Bullet[Settings.MAX_ENEMY_BULLETS];
        for (int i = 0; i < Settings.MAX_ENEMY_BULLETS; i += 1)
            enemyBullets[i] = new Bullet();

        pickups = new Pickup[Settings.MAX_PICKUPS];
        for (int i = 0; i < pickups.length; i += 1)
            pickups[i] = new Pickup();

        particles = new Particle[Settings.MAX_PARTICLES];
        for (int i = 0; i < particles.length; i += 1)
            particles[i] = new Particle();
    }



    public void update(float deltaTime) {
        // update enemy spawner
        enemySpawner.update(deltaTime);

        // update enemies
        for (Enemy enemy : enemies) {
            if (!enemy.getIsActive() && enemy.getSpawnNextFrame()) {
                enemy.setSpawnNextFrame(false);
                enemy.setIsActive(true);
            }
            enemy.update(deltaTime, this);
        }

        // update enemy bullets
        for (int i = 0; i < Settings.MAX_ENEMY_BULLETS; i += 1)
            enemyBullets[i].update(deltaTime, this);

        // update pickups
        for (int i = 0; i < Settings.MAX_PICKUPS; i += 1)
            pickups[i].update(deltaTime, this);

        // update player
        this.player.update(deltaTime, this); // player bullets get updated here as well
        if (player.getHealth() <= 0) {
            score.end(player.getPickupsCollected());
            Sounds.stopBackgroundMusic();
            ((PolygonWars)Gdx.app.getApplicationListener()).setScreen(new GameOverScreen(scores, score));
        }

        // update particles
        for (int i = 0; i < Settings.MAX_PARTICLES; i += 1)
            particles[i].update(deltaTime);

        // collision checks
        Collision.update(this);
    }



    public void render(PolygonSpriteBatch polygonSpriteBatch, ShapeRenderer shapeRenderer) {
        // render enemies
        for (Enemy enemy : enemies)
            enemy.render(polygonSpriteBatch, shapeRenderer);

        // render enemy bullets
        for (int i = 0; i < Settings.MAX_ENEMY_BULLETS; i += 1)
            enemyBullets[i].render(polygonSpriteBatch, shapeRenderer);

        // render pickups
        for (int i = 0; i < Settings.MAX_PICKUPS; i += 1)
            pickups[i].render(polygonSpriteBatch, shapeRenderer);

        // render particles
        for (int i = 0; i < Settings.MAX_PARTICLES; i += 1)
            particles[i].render(polygonSpriteBatch, shapeRenderer);

        // render player / player bullets
        player.render(polygonSpriteBatch, shapeRenderer);

        // render level
        level.render(shapeRenderer);
    }


    public Enemy getEnemy(Enemy.Type type) {
        for (int i = 0; i < enemies.length; i += 1) {
            if (enemies[i].getIsActive())
                continue;
            if (enemies[i].getSpawnNextFrame())
                continue;
            if (type == enemies[i].getType())
                return enemies[i];
        }

        return null;
    }


    public void destroyAllEnemies() {
        for (Enemy enemy : getEnemies()) {
            if (enemy.getSpawnNextFrame())
                enemy.setSpawnNextFrame(false);

            if (!enemy.getIsActive())
                continue;

            if (enemy.getType() == Enemy.Type.STAR) {
                if (Collision.pointIsInLevel(enemy.getX(), enemy.getY(), this))
                    enemy.spawnPickup(this);
                enemy.spawnParticles(enemy.getScale() / 4, (int)enemy.getScale() / 2, this, Particle.Type.CIRCLE);
                enemy.setHealth(0);
                enemy.setIsActive(false);
                enemy.playDestructionSound();
            }
            else
                enemy.destroy(this);
        }
    }




}
