package com.davidfabio.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class Game extends ApplicationAdapter {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private ShapeRenderer renderer;
	private Player player;
	
	@Override
	public void create () {
		this.camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		camera.zoom = 0.5f;

		this.batch = new SpriteBatch();
		this.renderer = new ShapeRenderer();
		this.player = new Player();
	}

	@Override
	public void render () {
		// Check for game end
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		}

		// Clear screen
		ScreenUtils.clear(0.8f, 0.8f, 0.8f, 1);

		// Zoom out of player
		if (camera.zoom <= 1.0f) {
			camera.zoom += 0.1f * Gdx.graphics.getDeltaTime();
		}
		this.camera.update();

		// Render scene
		this.renderer.setProjectionMatrix(this.camera.combined);
		this.player.draw(this.renderer);
	}
	
	@Override
	public void dispose () {
		this.batch.dispose();
		this.renderer.dispose();
	}
}
