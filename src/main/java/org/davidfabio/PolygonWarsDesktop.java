package org.davidfabio;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import org.davidfabio.ui.GameScreen;
import org.davidfabio.utils.JSONFileManagement;
import org.davidfabio.utils.Settings;

import java.io.File;

/**
 * This class is the entry point of the Application.
 * It prepares the Application Window and creates the {@link PolygonWars} object.
 */
public class PolygonWarsDesktop {

	/**
	 * This is the entry point for the PolygonWars application. It handles the loading of the {@link Settings},
	 * and it initializes the Application Window based on the settings.
	 * The {@link Settings} are either initialized or loaded from the settings.json-File.
	 * @param args any command line arguments that may be passed (currently not used)
	 */
	public static void main (String[] args) {
		// Load Settings from file
		File settingsFile = new File(Settings.SETTINGS_FILENAME);
		if (settingsFile.exists()) {
			JSONFileManagement.initSettingsFromFile(settingsFile);
		} else {
			JSONFileManagement.writeSettingsToFile(settingsFile);
		}

		// Prepare Window for Application
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

		// get display information
		Graphics.Monitor primary = Lwjgl3ApplicationConfiguration.getPrimaryMonitor();
		Graphics.DisplayMode desktopMode = Lwjgl3ApplicationConfiguration.getDisplayMode(primary);

		if (Settings.fullscreenEnabled)
			config.setFullscreenMode(desktopMode);

		GameScreen.displayRefreshRate = desktopMode.refreshRate;

		// Instantiate PolygonWars and show Window.
		config.useVsync(true);
		config.setForegroundFPS(desktopMode.refreshRate);
		config.setWindowedMode(Settings.windowWidth, Settings.windowHeight);
		config.setResizable(false);
		config.setTitle(Settings.GAME_TITLE);
		new Lwjgl3Application(new PolygonWars(), config);
	}
}