package org.davidfabio;

import com.badlogic.gdx.Game;
import java.util.ArrayList;
import org.davidfabio.game.Score;
import org.davidfabio.ui.MainMenuScreen;

public class Duality extends Game {
	private ArrayList<Score> scores;

	@Override
	public void create() {
		scores = new ArrayList<>();
		setScreen(new MainMenuScreen(scores));
		//for David: setScreen(new GameScreen(scores));
	}
}
