package com.tie.light.entities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.mappings.Xbox;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tie.light.LightMain;
import com.tie.light.input.InputEvent;
import com.tie.light.input.InputKey;
import com.tie.light.logic.Collider;
import com.tie.light.logic.Controllable;
import com.tie.light.logic.Walldragger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

@Walldragger
public class Bike extends Entity implements Collider, Controllable {

	private final Logger logger = Logger.getLogger(Bike.class.getName());

	private int player;

	private Float speed;
	private Float dir = 0f;

	private static final Float UNIT_ROTATION = 5f;
	private static final Float UNIT_SPEED = 10f;

	private Wall wall;

	public Bike(int player, Controller controller) {
		this.player = player;
		setWidth(10);
		setHeight(20);

		position = new Vector2(50f, 40f);

		poly = createRectanglePolygon(getWidth(), getHeight());



		direction = new Vector2(1, 0);
		speed = UNIT_SPEED;

		if(player == 0) {
			controlMap.put(new InputKey(Input.Keys.W, controller), moveForward);
			controlMap.put(new InputKey(Input.Keys.S, controller), moveBackward);
			controlMap.put(new InputKey(Input.Keys.A, controller), rotateLeft);
			controlMap.put(new InputKey(Input.Keys.D, controller), rotateRight);
		} else {
			controlMap.put(new InputKey(Input.Keys.UP, controller), moveForward);
			controlMap.put(new InputKey(Input.Keys.DOWN, controller), moveBackward);
			controlMap.put(new InputKey(Input.Keys.LEFT, controller), rotateLeft);
			controlMap.put(new InputKey(Input.Keys.RIGHT, controller), rotateRight);
		}

		wall = new Wall(new Vector2(position), position);
	}

	private Consumer<InputEvent> moveForward = e -> {
		if (e.fire(true)) {
			this.position.x += direction.x * speed;
			this.position.y += direction.y * speed;
		}
	};

	private Consumer<InputEvent> moveBackward = e -> {
		if (e.fire(true)) {
			this.position.x -= direction.x * speed;
			this.position.y -= direction.y * speed;
		}
	};

	private Consumer<InputEvent> rotateLeft = e -> {
		if (e.fire(true)) {
			direction = direction.rotate(UNIT_ROTATION);
			dir = (dir + UNIT_ROTATION) % 360;
			poly.setRotation(dir);
			wall.spawnWall(position);
		}
	};

	private Consumer<InputEvent> rotateRight = e -> {
		if (e.fire(true)) {
			direction = direction.rotate(-UNIT_ROTATION);
			dir = (dir - UNIT_ROTATION) % 360;
			poly.setRotation(dir);
			wall.spawnWall(position);
		}
	};

	@Override
	public void draw(Batch batch, float parentAlpha) {
		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		//moveForward.accept(CONTINUOUS_INPUT_EVENT);
		wall.draw(batch, parentAlpha);
		polyBatch.begin();
		poly.draw(polyBatch);
		poly.setPosition(position.x, position.y);

		polyBatch.end();
		handleInput();
	}

}