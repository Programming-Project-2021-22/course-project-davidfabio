package org.davidfabio;

import com.badlogic.gdx.Game;
import org.davidfabio.ui.GameScreen;
import org.davidfabio.ui.MainMenuScreen;

public class Duality extends Game {
	@Override
	public void create() {
		setScreen(new MainMenuScreen());
	}
}
