package com.davidfabio.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;


public class Game extends ApplicationAdapter {

	public static int gameWidth  = 720;
	public static int gameHeight = 540;

	Player player = new Player();

	ShapeRenderer shape;
	SpriteBatch batch;
	Texture img;


	@Override public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		shape = new ShapeRenderer();

		player.init();
	}


	// this is the main update and render loop; there is no separate update method
	@Override public void render () {

		// time passed since last frame in seconds; with VSync on it should be ~16.6ms with a 60hz refresh rate
		float deltaTime = Gdx.graphics.getDeltaTime();

		// get user input
		Inputs.update();

		// close application
		if (Inputs.esc.wasPressed)
			Gdx.app.exit();


		// update game logic
		player.update(deltaTime);



		// rendering
		ScreenUtils.clear(0, 0, 0, 1);
		player.render(shape);



		/*
		batch.begin();
		batch.draw(img, imgX, imgY);
		batch.end();
		 */
	}
	
	@Override public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
