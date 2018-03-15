package com.tie.light.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InputHandler implements InputProcessor {

	private final Logger logger = Logger.getLogger(InputHandler.class.getName());

	// Stores which key is currently being pressed and when it got pressed
	private Map<Integer, Long> pressMap = new HashMap<>();

	@Override
	public boolean keyDown(int keycode) {
		logger.log(Level.INFO, "OG keyDown: " + keycode + ", " + Input.Keys.toString(keycode));
		pressMap.put(keycode, System.currentTimeMillis());
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		pressMap.remove(keycode);
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	public Map<Integer, Long> getPressMap() {
		return pressMap;
	}

	public void setPressMap(Map<Integer, Long> pressMap) {
		this.pressMap = pressMap;
	}
}