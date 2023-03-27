package com.davidfabio.game;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;


// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {



	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

		// get display information
		Graphics.Monitor primary = Lwjgl3ApplicationConfiguration.getPrimaryMonitor();
		Graphics.DisplayMode desktopMode = Lwjgl3ApplicationConfiguration.getDisplayMode(primary);

		if (Settings.START_IN_FULLSCREEN)
			config.setFullscreenMode(desktopMode);

		config.useVsync(true);
		config.setForegroundFPS(desktopMode.refreshRate);
		config.setWindowedMode(Settings.windowWidth, Settings.windowHeight);
		config.setResizable(false);
		config.setTitle(Settings.GAME_TITLE);
		new Lwjgl3Application(new Game(), config);
	}



}
