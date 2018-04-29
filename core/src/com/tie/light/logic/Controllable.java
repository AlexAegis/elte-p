package com.tie.light.logic;

import com.tie.light.LightMain;
import com.tie.light.input.InputEvent;
import com.tie.light.input.InputKey;

import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public interface Controllable {
	Logger logger = Logger.getLogger(Controllable.class.getName());

	default void handleInput(Map<InputKey, Consumer<InputEvent>> controlMap) {
		LightMain.INPUT_HANDLER.getInputMap().forEach((key, detail) ->
				controlMap.getOrDefault(key, e -> logger.log(Level.FINEST, "Non bound key pressed"))
						.accept(detail));
	}
}
