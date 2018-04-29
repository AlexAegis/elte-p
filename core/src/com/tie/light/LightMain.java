package com.tie.light;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.tie.light.input.InputEvent;
import com.tie.light.input.InputHandler;
import com.tie.light.input.InputKey;
import com.tie.light.screen.MainMenu;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LightMain extends Game {

	private final static Logger LOGGER_ROOT = LogManager.getLogManager().getLogger("");
	private final static String CONFIG_FILE_NAME = "config.properties";
	private final static String CONFIG_LOGGING_LEVEL = "loggingLevel";

	public static Properties PROPERTIES;

	public Map<Integer, Map<InputKey, InputEvent>> controls = new HashMap<>();
	public final static InputHandler INPUT_HANDLER = new InputHandler();

	@Parameter(names = {"--loggingLevel", "--logLevel", "-log"})
	private static String loggingLevel;

	public LightMain(String[] arg) {
		JCommander.newBuilder()
				.addObject(this)
				.build()
				.parse(arg);
	}

	@Override
	public void create() {
		loadConfig();
		applyConfig();
		setScreen(new MainMenu());
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1f, 1f, 1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		super.render();
	}
	
	@Override
	public void dispose () {

		//img.dispose();
	}

	public static void loadConfig(String configFileName) {
		try {
			PROPERTIES = new Properties();
			// load default config
			Gdx.files.getLocalStoragePath();
			PROPERTIES.load(Gdx.files.internal(configFileName).read());

			// overwrite default config with command line arguments
			if(loggingLevel != null) {
				PROPERTIES.setProperty(CONFIG_LOGGING_LEVEL, loggingLevel);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void loadConfig() {
		loadConfig(CONFIG_FILE_NAME);
	}

	public static void applyConfig() {
		Level level = Level.parse(PROPERTIES.getProperty(CONFIG_LOGGING_LEVEL));
		LOGGER_ROOT.setLevel(level);
		for (Handler h : LOGGER_ROOT.getHandlers()) {
			h.setLevel(level);
		}
		Object o = PROPERTIES;
		LOGGER_ROOT.log(Level.INFO, "Config applied.");
	}
}