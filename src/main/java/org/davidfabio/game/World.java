package org.davidfabio.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.ArrayList;
import org.davidfabio.Duality;
import org.davidfabio.ui.GameOverScreen;
import org.davidfabio.utils.Settings;

public class World {
    private final Player player;
    private final ArrayList<Enemy> enemies;
    private final ArrayList<Enemy> enemiesTemp; // this is needed so we can add new enemies during the update loop (e.g. when an enemy dies, he spawns new enemies)
    private final BulletEnemy[] enemyBullets;
    private final EnemySpawner enemySpawner;
    private final Pickup[] pickups;
    private final Level level;
    private final Score score;
    private final ArrayList<Score> scores;

    public World(ArrayList<Score> scores) {
        this.level = new Level(Settings.levelWidth, Settings.levelHeight);
        this.score = new Score();
        this.scores = scores;

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

    public Player getPlayer() { return this.player; }

    public ArrayList<Enemy> getEnemies() { return this.enemies; }

    public BulletEnemy[] getEnemyBullets() { return this.enemyBullets; }

    public Pickup[] getPickups() { return pickups; }

    public Level getLevel() { return this.level; }

    public Score getScore() { return this.score; }

    public void addEnemyTemp(Enemy enemy) { enemiesTemp.add(enemy); }

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
            this.score.end();
            ((Duality)Gdx.app.getApplicationListener()).setScreen(new GameOverScreen(this.scores,this.score));
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
