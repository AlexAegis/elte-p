package com.tie.light.input;

import com.badlogic.gdx.controllers.Controller;

import java.util.Objects;

public class InputKey {

	private Integer key;
	private Controller controller;

	public InputKey(Integer key, Controller controller) {
		this.key = key;
		this.controller = controller;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		InputKey inputKey = (InputKey) o;
		return Objects.equals(key, inputKey.key);
				/*
				&&
				(inputKey.controller == null || Objects.equals(controller, inputKey.controller));*/
	}

	@Override
	public int hashCode() {

		return Objects.hash(key, controller);
	}

	@Override
	public String toString() {
		return "InputKey{" +
				"key=" + key +
				", controller=" + controller +
				'}';
	}

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}
}
