package com.tie.light;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.tie.light.input.InputHandler;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LightMain extends ApplicationAdapter {

	private final static Logger LOGGER_ROOT = LogManager.getLogManager().getLogger("");
	private final static String CONFIG_FILE_NAME = "config.properties";
	private final static String CONFIG_LOGGING_LEVEL = "loggingLevel";

	private final static Properties properties = new Properties();

	private InputHandler inputHandler = new InputHandler();

	@Parameter(names = {"--loggingLevel", "--logLevel", "-log"})
	private String loggingLevel;

	SpriteBatch batch;
	Texture img;

	public LightMain(String[] arg) {
		JCommander.newBuilder()
				.addObject(this)
				.build()
				.parse(arg);
	}

	@Override
	public void create () {
		loadConfig();
		applyConfig();
		Gdx.input.setInputProcessor(inputHandler);

		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	private void loadConfig() {
		try {
			// load default config
			properties.load(Gdx.files.internal(CONFIG_FILE_NAME).read());

			// overwrite default config with command line arguments
			if(loggingLevel != null) {
				properties.setProperty(CONFIG_LOGGING_LEVEL, loggingLevel);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void applyConfig() {
		Level level = Level.parse(properties.getProperty(CONFIG_LOGGING_LEVEL));
		LOGGER_ROOT.setLevel(level);
		for (Handler h : LOGGER_ROOT.getHandlers()) {
			h.setLevel(level);
		}

		LOGGER_ROOT.log(Level.INFO, "Config applied.");
	}
}