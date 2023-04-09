package com.davidfabio.game.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.Random;

public class World {
    private Player player;
    private ArrayList<Enemy> enemies;
    private BulletEnemy[] enemyBullets;
    private EnemySpawner enemySpawner;
    private Level level;
    private Score score;

    private Random random;

    public Player getPlayer() { return this.player; }
    public ArrayList<Enemy> getEnemies() { return this.enemies; }
    public BulletEnemy[] getEnemyBullets() { return this.enemyBullets; }

    public Score getScore() { return this.score; }


    public World() {
        this.random = new Random();

        this.level = new Level();
        this.score = new Score();

        this.player = new Player();
        this.player.init(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 32, 240, Color.GOLD);

        this.enemies = new ArrayList<>();
        this.enemySpawner = new EnemySpawner();

        this.enemyBullets = new BulletEnemy[Settings.MAX_ENEMY_BULLETS];
        for (int i = 0; i < Settings.MAX_ENEMY_BULLETS; i += 1)
            this.enemyBullets[i] = new BulletEnemy();
    }

    public void update(float deltaTime) {
        // spawn new enemies
        enemySpawner.update(deltaTime, this);

        // update enemies
        for (Enemy enemy : this.enemies)
            enemy.update(deltaTime, this);

        // update enemy bullets
        for (int i = 0; i < Settings.MAX_ENEMY_BULLETS; i += 1)
            this.enemyBullets[i].update(deltaTime, this);

        this.player.update(deltaTime,this); // player bullets get updated here as well
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

        // Render Player & their Bullets
        this.player.render(polygonSpriteBatch);
        polygonSpriteBatch.end();

        // Render Level
        this.level.render(shapeRenderer);
    }
}
