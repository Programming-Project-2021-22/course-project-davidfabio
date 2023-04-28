package org.davidfabio;

import com.badlogic.gdx.Game;
import org.davidfabio.game.Score;
import org.davidfabio.game.Sounds;
import org.davidfabio.ui.GameScreen;
import org.davidfabio.ui.MainMenuScreen;

import java.util.ArrayList;

public class Duality extends Game {
	private ArrayList<Score> scores;

	@Override
	public void create() {
		scores = new ArrayList<>();
		Sounds.loadSounds();	// Initialize Sounds from current Settings.
		//
		setScreen(new MainMenuScreen(scores));
		//setScreen(new GameScreen(scores));
	}
}
