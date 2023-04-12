package com.davidfabio.game.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class World {
    private Player player;
    private ArrayList<Enemy> enemies;
    private ArrayList<Enemy> enemiesTemp; // this is needed so we can add new enemies during the update loop (e.g. when an enemy dies, he spawns new enemies)
    private BulletEnemy[] enemyBullets;
    private EnemySpawner enemySpawner;
    private Pickup[] pickups;
    private Level level;
    private Score score;

    public Player getPlayer() { return this.player; }
    public ArrayList<Enemy> getEnemies() { return this.enemies; }

    public BulletEnemy[] getEnemyBullets() { return this.enemyBullets; }

    public Pickup[] getPickups() { return pickups; }

    public Score getScore() { return this.score; }

    public void addEnemyTemp(Enemy enemy) { enemiesTemp.add(enemy); }



    public World() {
        this.level = new Level();
        this.score = new Score();

        this.player = new Player();
        this.player.init(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 32, 240, Color.GOLD);

        this.enemies = new ArrayList<>();
        this.enemiesTemp = new ArrayList<>();
        this.enemySpawner = new EnemySpawner(this);

        this.enemyBullets = new BulletEnemy[Settings.MAX_ENEMY_BULLETS];
        for (int i = 0; i < Settings.MAX_ENEMY_BULLETS; i += 1)
            this.enemyBullets[i] = new BulletEnemy();

        this.pickups = new Pickup[Settings.MAX_PICKUPS];
        for (int i = 0; i < pickups.length; i += 1)
            this.pickups[i] = new Pickup();
    }

    public void update(float deltaTime) {
        // ---------------- update enemies ----------------
        for (Enemy enemy : enemiesTemp)
            enemies.add(enemy);
        enemiesTemp.clear();

        enemySpawner.update(deltaTime);

        for (Enemy enemy : this.enemies)
            enemy.update(deltaTime, this);


        // ---------------- update enemy bullets ----------------
        for (int i = 0; i < Settings.MAX_ENEMY_BULLETS; i += 1)
            this.enemyBullets[i].update(deltaTime, this);

        // ---------------- update pickups ----------------
        for (int i = 0; i < Settings.MAX_PICKUPS; i += 1)
            this.pickups[i].update(deltaTime, this);


        // ---------------- update player --------------------
        this.player.update(deltaTime, this); // player bullets get updated here as well
        if (this.player.getHealth() <= 0) {
            ((Duality)Gdx.app.getApplicationListener()).setScreen(new GameOverScreen(this.score));
        }
    }


    public void render(PolygonSpriteBatch polygonSpriteBatch, ShapeRenderer shapeRenderer) {
        // Render Enemies
        for (Enemy enemy : this.enemies)
            enemy.render(polygonSpriteBatch);

        // Render Enemy Bullets
        for (int i = 0; i < Settings.MAX_ENEMY_BULLETS; i += 1)
            this.enemyBullets[i].render(polygonSpriteBatch);

        // Render Pickups
        for (int i = 0; i < Settings.MAX_PICKUPS; i += 1)
            this.pickups[i].render(polygonSpriteBatch);

        // Render Player & their Bullets
        this.player.render(polygonSpriteBatch);
        polygonSpriteBatch.end();

        // Render Level
        this.level.render(shapeRenderer);
    }
}
