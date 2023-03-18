package com.davidfabio.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;


public class Game extends ApplicationAdapter {

	public static int gameWidth  = 720;
	public static int gameHeight = 540;
	int gameTimer = 0;
	ShapeRenderer shape;

	Player player = new Player();
	public static ArrayList<Enemy> enemies = new ArrayList<Enemy>();



	@Override public void create () {
		shape = new ShapeRenderer();
		player.init();

		for (int i = 0; i < 10; i += 1)
			enemies.add(new Enemy());

	}


	// this is the main update and render loop; there is no separate update method
	@Override public void render () {

		// time passed since last frame in seconds; with VSync on it should be ~16.6ms with a 60hz refresh rate
		float deltaTime = Gdx.graphics.getDeltaTime();
		gameTimer += 1;

		// get user input
		Inputs.update();

		// close application
		if (Inputs.esc.wasPressed)
			Gdx.app.exit();


		// ---------------- update game logic ----------------
		if (gameTimer % 100 == 0)
			enemies.add(new Enemy());

		player.update(deltaTime);


		// ---------------- rendering ----------------
		ScreenUtils.clear(0, 0, 0, 1);

		for (Enemy enemy : enemies)
			enemy.render(shape);

		player.render(shape);
	}


	@Override public void dispose () {

	}

}
