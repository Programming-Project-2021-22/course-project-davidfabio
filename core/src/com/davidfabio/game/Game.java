package com.davidfabio.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Game extends ApplicationAdapter {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Player player;
	
	@Override
	public void create () {
		this.camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		this.batch = new SpriteBatch();
		this.player = new Player();
	}

	@Override
	public void render () {
		ScreenUtils.clear(0.8f, 0.8f, 0.8f, 1);
		this.camera.update();

		this.batch.setProjectionMatrix(this.camera.combined);
		this.batch.begin();
		this.batch.draw(this.player.getTexture(800, 480),0,0);
		this.batch.end();

		// >>> Player Movement Inputs
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			this.player.moveInDirection(Input.Keys.LEFT);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			this.player.moveInDirection(Input.Keys.RIGHT);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			this.player.moveInDirection(Input.Keys.UP);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			this.player.moveInDirection(Input.Keys.DOWN);
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			this.player.switchToNextColor();
		}
		// <<< Player Movement Inputs
	}
	
	@Override
	public void dispose () {
		this.batch.dispose();
	}
}
