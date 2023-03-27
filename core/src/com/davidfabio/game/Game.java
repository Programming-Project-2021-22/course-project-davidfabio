package com.davidfabio.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;


public class Game extends ApplicationAdapter {

	public static Player player;
	public static final int MAX_ENEMIES = 256;
	public static final int MAX_ENEMY_BULLETS = 256;
	public static Enemy[] enemies;
	public static EnemyBullet[] enemyBullets;

	private Random random;
	private ShapeRenderer shape;
	private Camera camera;
	private Stage stage;
	private static int frameCounter = 0; // for testing only



	@Override public void create () {
		random = new Random();

		shape = new ShapeRenderer();
		this.camera = new Camera();
		this.stage = new Stage();
		//Sounds.loadSounds();

		player = new Player();
		player.init(Settings.windowWidth / 2, Settings.windowHeight / 2, 16, 0, new Polarity(), 260);

		enemies = new Enemy[MAX_ENEMIES];
		for (int i = 0; i < MAX_ENEMIES; i += 1)
			enemies[i] = new Enemy();

		enemyBullets = new EnemyBullet[MAX_ENEMY_BULLETS];
		for (int i = 0; i < MAX_ENEMY_BULLETS; i += 1)
			enemyBullets[i] = new EnemyBullet();
	}


	// this is the main update and render loop; there is no separate update method
	@Override public void render () {

		// TODO (David): frametimes are uneven, even if the render method is completely empty; VSync not working correctly?

		// time passed since last frame in seconds; with VSync on it should be ~16.6ms with a 60hz refresh rate
		float deltaTime = Gdx.graphics.getDeltaTime();

		// get user input
		Inputs.update();

		// close application
		if (Inputs.esc.getWasPressed())
			Gdx.app.exit();


		// Reposition camera on player
		this.camera.updateCameraPosition(deltaTime, this.player);
		this.shape.setProjectionMatrix(this.camera.combined);


		// ---------------- update game logic ----------------
		for (int i = 0; i < MAX_ENEMIES; i += 1)
			enemies[i].update(deltaTime);



		// FOR TESTING ONLY
		frameCounter += 1;
		int activeEnemyCount = 0;
		int maxEnemies = 4;
		for (int i = 0; i < MAX_ENEMIES; i += 1)
			if (enemies[i].getActive())
				activeEnemyCount += 1;


		if (activeEnemyCount < maxEnemies) {
			for (int i = 0; i < MAX_ENEMIES; i += 1) {
				if (!enemies[i].getActive()) {
					float randomX = (float)(Math.random() * Settings.windowWidth);
					float randomY = (float)(Math.random() * Settings.windowHeight);
					float minDistanceToPlayer = 240;

					while(player.getDistanceTo(randomX, randomY) < minDistanceToPlayer) {
						randomX = (float)(Math.random() * Settings.windowWidth);
						randomY = (float)(Math.random() * Settings.windowHeight);
					}

					int rand = random.nextInt(16) + 24;

					enemies[i].init(randomX, randomY, rand, 0, new Polarity(), 80, 7);
					break;
				}
			}
		}


		// update enemy bullets
		for (int i = 0; i < MAX_ENEMY_BULLETS; i += 1)
			enemyBullets[i].update(deltaTime);


		player.update(deltaTime); // player bullets get updated here as well



		// ---------------- rendering ----------------
		ScreenUtils.clear(0, 0, 0, 1);
		this.stage.render(this.shape);

		for (int i = 0; i < MAX_ENEMIES; i += 1)
			if (enemies[i].getActive())
				enemies[i].render(shape, enemies[i].getPolarity().getColor());

		for (int i = 0; i < MAX_ENEMY_BULLETS; i += 1) {
			if (enemyBullets[i].getActive())
				enemyBullets[i].render(shape, enemyBullets[i].getPolarity().getColor());
		}

		player.render(shape, player.getPolarity().getColor()); // player bullets get rendered here as well
	}


	@Override public void dispose () {

	}

}
