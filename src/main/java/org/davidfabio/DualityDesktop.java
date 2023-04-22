package org.davidfabio;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import org.davidfabio.ui.GameScreen;
import org.davidfabio.utils.Settings;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DualityDesktop {
	public static void main (String[] args) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

		// get display information
		Graphics.Monitor primary = Lwjgl3ApplicationConfiguration.getPrimaryMonitor();
		Graphics.DisplayMode desktopMode = Lwjgl3ApplicationConfiguration.getDisplayMode(primary);

		if (Settings.fullscreenEnabled)
			config.setFullscreenMode(desktopMode);

		GameScreen.displayRefreshRate = desktopMode.refreshRate;

		config.useVsync(true);
		config.setForegroundFPS(desktopMode.refreshRate);
		config.setWindowedMode(Settings.windowWidth, Settings.windowHeight);
		config.setResizable(false);
		config.setTitle(Settings.GAME_TITLE);
		new Lwjgl3Application(new Duality(), config);
	}
}
