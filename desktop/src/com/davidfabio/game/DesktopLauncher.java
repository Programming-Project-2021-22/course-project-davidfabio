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

		if (Game.startInFullscreenMode)
			config.setFullscreenMode(desktopMode);

		config.useVsync(true);
		config.setForegroundFPS(desktopMode.refreshRate);
		config.setWindowedMode(Game.gameWidth, Game.gameHeight);
		config.setResizable(false);
		config.setTitle("Color Switching Shooter");
		new Lwjgl3Application(new Game(), config);
	}



}
