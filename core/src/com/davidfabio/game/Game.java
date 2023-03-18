package com.davidfabio.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;


public class Game extends ApplicationAdapter {

	public static int gameWidth  = 960;
	public static int gameHeight = 540;



	SpriteBatch batch;
	Texture img;

	// INPUT TESTING
	int imgX = 0, imgY = 0;
	float speed = 4.0f;


	@Override public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
	}


	// this is the main update and render loop; there is no separate update method
	@Override public void render () {
		Inputs.Update(); // get user input

		// INPUT TESTING
		if (Inputs.Mouse.left.isDown) {
			imgX = Inputs.Mouse.x - (img.getWidth() / 2);
			imgY = -Inputs.Mouse.y + (gameHeight / 2) + (img.getHeight() / 2);
		}
		else {
			if (Inputs.up.isDown)    imgY += speed;
			if (Inputs.down.isDown)  imgY -= speed;
			if (Inputs.left.isDown ) imgX -= speed;
			if (Inputs.right.isDown) imgX += speed;
		}


		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		batch.draw(img, imgX, imgY);
		batch.end();
	}
	
	@Override public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
