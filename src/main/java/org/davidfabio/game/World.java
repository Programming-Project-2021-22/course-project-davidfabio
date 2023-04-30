package org.davidfabio.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.davidfabio.Duality;
import org.davidfabio.ui.GameOverScreen;
import org.davidfabio.utils.Settings;

import java.util.ArrayList;

public class World {
    private Player player;
    private ArrayList<Enemy> enemies;
    private ArrayList<Enemy> enemiesTemp; // this is needed so we can add new enemies during the update loop (e.g. when an enemy dies, he spawns new enemies)
    private Bullet[] enemyBullets;
    private EnemySpawner enemySpawner;
    private Pickup[] pickups;
    private Particle[] particles;
    private Level level;
    private Score score;
    private ArrayList<Score> scores;

    public Player getPlayer() { return player; }
    public ArrayList<Enemy> getEnemies() { return enemies; }
    public ArrayList<Enemy> getEnemiesTemp() { return enemiesTemp; }
    public Bullet[] getEnemyBullets() { return enemyBullets; }
    public Pickup[] getPickups() { return pickups; }
    public Particle[] getParticles() { return particles; }
    public Level getLevel() { return level; }
    public Score getScore() { return score; }
    public void addEnemyTemp(Enemy enemy) { enemiesTemp.add(enemy); }



    public World(ArrayList<Score> scores) {
        level = new Level(Settings.levelWidth, Settings.levelHeight);
        score = new Score();
        this.scores = scores;

        player = new Player();
        player.init(level.getWidth() / 2, level.getHeight() / 2, 32, 240, Color.GOLD);

        enemies = new ArrayList<>();
        enemiesTemp = new ArrayList<>();
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
        // update enemies
        for (Enemy enemy : enemiesTemp)
            enemies.add(enemy);
        enemiesTemp.clear();

        enemySpawner.update(deltaTime);

        for (Enemy enemy : enemies)
            enemy.update(deltaTime, this);


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
            ((Duality)Gdx.app.getApplicationListener()).setScreen(new GameOverScreen(scores, score));
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


    public void destroyAllEnemies() {
        for (Enemy enemy : getEnemies()) {
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
        getEnemiesTemp().clear();
    }




}
