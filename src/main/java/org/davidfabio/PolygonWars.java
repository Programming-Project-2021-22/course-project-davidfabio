package org.davidfabio;

import com.badlogic.gdx.Game;
import org.davidfabio.game.Score;
import org.davidfabio.game.Sounds;
import org.davidfabio.ui.MainMenuScreen;
import org.davidfabio.utils.FileManipulations;
import org.davidfabio.utils.Settings;

import java.io.File;
import java.util.ArrayList;

/**
 * This class contains everything that is game related.
 * It shows the {@link MainMenuScreen} and loads the {@link PolygonWars#scores} from file.
 */
public class PolygonWars extends Game {
	/**
	 * The scores ArrayList stores any scores of past games the player played.
	 * This list is passed on to any Screen that the user might see.
	 * The scores are used to display the Highest scores on the {@link org.davidfabio.ui.HighscoreScreen}.
	 */
	private ArrayList<Score> scores;

	/**
	 * The create Method initializes Everything that is needed for the Game.
	 * This means that the {@link PolygonWars#scores} are either initialized or loaded from the scores.json-File.
	 * The sounds are loaded from the resources (Only sounds that are enabled by the settings are loaded).
	 * And finally the Main Menu Screen is shown.
	 */
	@Override
	public void create() {
		// Load scores from the scores.json-File
		File scoresFile = new File(Settings.SCORES_FILENAME);
		if (scoresFile.exists()) {
			scores = FileManipulations.initScoresFromFile(scoresFile);
		} else {
			scores = new ArrayList<>();
			FileManipulations.writeScoresToFile(scoresFile,scores);
		}

		// Initialize Sounds from current Settings.
		Sounds.loadSounds();

		// Show the Main Menu
		setScreen(new MainMenuScreen(scores));
		//setScreen(new GameScreen(scores));
	}
}
