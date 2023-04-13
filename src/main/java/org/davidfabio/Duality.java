package org.davidfabio;

import com.badlogic.gdx.Game;
import org.davidfabio.ui.GameScreen;

public class Duality extends Game {
	@Override
	public void create() {
		//setScreen(new MainMenuScreen());
		setScreen(new GameScreen());
	}
}
