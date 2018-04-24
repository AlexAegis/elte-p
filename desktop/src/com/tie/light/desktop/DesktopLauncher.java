package com.tie.light.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.tie.light.LightMain;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 3400;
		config.height = 1600;
		new LwjglApplication(new LightMain(arg), config);
	}
}