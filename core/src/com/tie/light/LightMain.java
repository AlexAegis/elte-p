package com.tie.light;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.tie.light.entities.Bike;
import com.tie.light.entities.Wall;
import com.tie.light.input.InputHandler;
import com.tie.light.screen.MainMenu;

import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LightMain extends Game {

	private final static Logger LOGGER_ROOT = LogManager.getLogManager().getLogger("");
	private final static String CONFIG_FILE_NAME = "config.properties";
	private final static String CONFIG_LOGGING_LEVEL = "loggingLevel";

	public final static Properties PROPERTIES = new Properties();

	public final static InputHandler INPUT_HANDLER = new InputHandler();

	@Parameter(names = {"--loggingLevel", "--logLevel", "-log"})
	private String loggingLevel;

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

	private void loadConfig() {
		try {
			// load default config
			PROPERTIES.load(Gdx.files.internal(CONFIG_FILE_NAME).read());

			// overwrite default config with command line arguments
			if(loggingLevel != null) {
				PROPERTIES.setProperty(CONFIG_LOGGING_LEVEL, loggingLevel);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void applyConfig() {
		Level level = Level.parse(PROPERTIES.getProperty(CONFIG_LOGGING_LEVEL));
		LOGGER_ROOT.setLevel(level);
		for (Handler h : LOGGER_ROOT.getHandlers()) {
			h.setLevel(level);
		}
		LOGGER_ROOT.log(Level.INFO, "Config applied.");
	}
}