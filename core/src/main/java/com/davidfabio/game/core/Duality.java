package com.davidfabio.game.core;

import com.badlogic.gdx.Game;

public class Duality extends Game {
	@Override
	public void create() {
		//setScreen(new MainMenuScreen());
		setScreen(new GameScreen());
	}
}
