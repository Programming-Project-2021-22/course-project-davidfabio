package com.davidfabio.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Random;


public class Game extends ApplicationAdapter {

	public static Player player;
	public static final int MAX_ENEMIES = 256;
	public static final int MAX_ENEMY_BULLETS = 256;
	public static ArrayList<Enemy> enemies;
	public static BulletEnemy[] enemyBullets;

	private Random random;
	private ShapeRenderer shape;
	private Camera camera;
	private Stage stage;

	// for testing only
	private static float timeElapsed = 0;
	public static float getTimeElapsed() { return timeElapsed; }


	@Override public void create () {
		random = new Random();

		shape = new ShapeRenderer();
		this.camera = new Camera();
		this.stage = new Stage();
		Sounds.loadSounds();

		player = new Player();
		player.init(Settings.windowWidth / 2, Settings.windowHeight / 2, 16, 0, new Polarity(), 260);

		enemies = new ArrayList<>();
		//for (int i = 0; i < MAX_ENEMIES; i += 1)
		//	enemies.add(new Enemy());

		enemyBullets = new BulletEnemy[MAX_ENEMY_BULLETS];
		for (int i = 0; i < MAX_ENEMY_BULLETS; i += 1)
			enemyBullets[i] = new BulletEnemy();
	}


	// this is the main update and render loop; there is no separate update method
	@Override public void render () {

		// TODO (David): frametimes are uneven, even if the render method is completely empty; VSync not working correctly?

		// time passed since last frame in seconds; with VSync on it should be ~16.6ms with a 60hz refresh rate
		float deltaTime = Gdx.graphics.getDeltaTime();
		timeElapsed += deltaTime;

		// get user input
		Inputs.update();

		// close application
		if (Inputs.esc.getWasPressed())
			Gdx.app.exit();



		// ---------------- update game logic ----------------

		// Reposition camera on player
		this.camera.updateCameraPosition(deltaTime, this.player);
		this.shape.setProjectionMatrix(this.camera.combined);


		// FOR TESTING ONLY: enemy spawning
		int activeEnemyCount = 0;
		int maxEnemies = 4;
		for (Enemy enemy : enemies)
			if (enemy.getIsActive())
				activeEnemyCount += 1;

		if (activeEnemyCount < maxEnemies) {
			float randomX = (float) (Math.random() * Settings.windowWidth);
			float randomY = (float) (Math.random() * Settings.windowHeight);
			float minDistanceToPlayer = 240;
			while (player.getDistanceTo(randomX, randomY) < minDistanceToPlayer) {
				randomX = (float) (Math.random() * Settings.windowWidth);
				randomY = (float) (Math.random() * Settings.windowHeight);
			}

			int rand = random.nextInt(2);
			switch (rand) {
				case 0: {
					EnemyChaser enemyChaser = new EnemyChaser();
					enemyChaser.init(randomX, randomY, 20, 0, new Polarity(), 70, 7);
					enemies.add(enemyChaser);
					break;
				}
				case 1: {
					EnemyStatic enemyStatic = new EnemyStatic();
					enemyStatic.init(randomX, randomY, 28, 0, new Polarity(), 70, 7);
					enemies.add(enemyStatic);
					break;
				}
			}
		}


		// update enemies
		for (Enemy enemy : enemies)
			enemy.update(deltaTime);

		// update enemy bullets
		for (int i = 0; i < MAX_ENEMY_BULLETS; i += 1)
			enemyBullets[i].update(deltaTime);


		player.update(deltaTime); // player bullets get updated here as well



		// ---------------- rendering ----------------
		ScreenUtils.clear(1, 1, 1, 1);
		this.stage.render(this.shape);

		for (Enemy enemy : enemies)
			if (enemy.getIsActive())
				enemy.render(shape, enemy.getPolarity().getColor());

		for (int i = 0; i < MAX_ENEMY_BULLETS; i += 1) {
			if (enemyBullets[i].getIsActive())
				enemyBullets[i].render(shape, enemyBullets[i].getPolarity().getColor());
		}

		player.render(shape, player.getPolarity().getColor()); // player bullets get rendered here as well
	}


	@Override public void dispose () {

	}

}
