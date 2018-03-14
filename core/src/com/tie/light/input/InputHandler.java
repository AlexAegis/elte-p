package com.tie.light.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

import java.util.logging.Level;
import java.util.logging.Logger;

public class InputHandler implements InputProcessor {

	private final Logger logger = Logger.getLogger(InputHandler.class.getName());

	@Override
	public boolean keyDown(int keycode) {
		logger.log(Level.INFO, "keyDown: " + keycode + ", " + Input.Keys.toString(keycode));

		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
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

}