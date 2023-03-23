package com.davidfabio.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;


public class Game extends ApplicationAdapter {


	public static boolean startInFullscreenMode = false;

	public static int gameWidth  = 720;
	public static int gameHeight = 540;
	int frameCounter = 0; // for testing only
	ShapeRenderer shape;

	public static Player player;

	public static final int MAX_ENEMIES = 256;
	public static Enemy[] enemies;


	public static float sfxVolume = 0.33f; // range: 0 to 1
	public static float musicVolume = 0.5f; // range: 0 to 1
	static Sound sfxShoot, sfxExplosion;
	static Sound musicTrack;

	Random random;




	@Override public void create () {
		random = new Random();

		sfxShoot = Gdx.audio.newSound(Gdx.files.internal("assets/sfx/shoot1.wav"));
		sfxExplosion = Gdx.audio.newSound(Gdx.files.internal("assets/sfx/explosion1.wav"));
		//musicTrack = Gdx.audio.newSound(Gdx.files.internal("assets/music/track1.mp3"));

		shape = new ShapeRenderer();

		player = new Player();
		player.init(gameWidth / 2, gameHeight / 2, 16, 0, Entity.Polarity.RED, 260);

		enemies = new Enemy[MAX_ENEMIES];
		for (int i = 0; i < MAX_ENEMIES; i += 1)
			enemies[i] = new Enemy();


		//musicTrack.play(musicVolume);
	}


	// this is the main update and render loop; there is no separate update method
	@Override public void render () {

		// TODO: frametimes are uneven, even if the render method is completely empty; VSync not working correctly?

		// time passed since last frame in seconds; with VSync on it should be ~16.6ms with a 60hz refresh rate
		float deltaTime = Gdx.graphics.getDeltaTime();

		// get user input
		Inputs.update();

		// close application
		if (Inputs.esc.getWasPressed())
			Gdx.app.exit();


		// ---------------- update game logic ----------------

		for (int i = 0; i < MAX_ENEMIES; i += 1)
			enemies[i].update(deltaTime);


		// FOR TESTING ONLY: create new enemy every x frames
		frameCounter += 1;
		if (frameCounter % 30 == 0) {
			for (int i = 0; i < MAX_ENEMIES; i += 1) {
				if (!enemies[i].getActive()) {
					float randomX = (float)(Math.random() * Game.gameWidth);
					float randomY = (float)(Math.random() * Game.gameHeight);
					float minDistanceToPlayer = 240;

					while(player.getDistanceTo(randomX, randomY) < minDistanceToPlayer) {
						randomX = (float)(Math.random() * Game.gameWidth);
						randomY = (float)(Math.random() * Game.gameHeight);
					}

					Entity.Polarity polarity = Entity.Polarity.RED;
					int rand = random.nextInt(2);
					if (rand == 1)
						polarity = Entity.Polarity.BLUE;

					rand = random.nextInt(16) + 24;

					enemies[i].init(randomX, randomY, rand, 0, polarity, 80, 7);
					break;
				}
			}
		}


		player.update(deltaTime); // player bullets get updated here as well


		// ---------------- rendering ----------------
		ScreenUtils.clear(0, 0, 0, 1);

		for (int i = 0; i < MAX_ENEMIES; i += 1)
			enemies[i].render(shape, enemies[i].getColor());

		player.render(shape, player.getColor()); // player bullets get rendered here as well






	}


	@Override public void dispose () {
		sfxShoot.dispose();
	}

}
