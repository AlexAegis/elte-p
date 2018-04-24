package com.tie.light.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tie.light.LightMain;
import com.tie.light.input.InputEvent;
import com.tie.light.input.InputKey;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Bike extends Actor {

	private final Logger logger = Logger.getLogger(Bike.class.getName());

	private ShapeRenderer shapeRenderer;

	private int player;
	private Vector3 direction;
	private Double speed;
	private Rectangle body;

	private Map<InputKey, Consumer<InputEvent>> controlMap = new HashMap<>();

	public Bike(int player, Controller controller) {
		player = player;

		direction = new Vector3(1, 0,0);
		speed = 5.0d;

		body = new Rectangle(40f, 40f, 5f, 10f);

		controlMap.put(new InputKey(Input.Keys.W, controller), moveForward);
		controlMap.put(new InputKey(Input.Keys.S, controller), moveBackward);
		controlMap.put(new InputKey(Input.Keys.A, controller), rotateLeft);
		controlMap.put(new InputKey(Input.Keys.D, controller), rotateRight);
		controlMap.put(new InputKey(0, controller), moveForward);

		shapeRenderer = new ShapeRenderer();

	}

	private Consumer<InputEvent> moveForward = e -> {
		//if(e.fire()) {
			this.body.x += direction.x * speed;
			this.body.y += direction.y * speed;
		System.out.println(direction);
		//}
	};

	private Consumer<InputEvent> moveBackward = e -> {
		if(e.fire()) {
			this.body.x -= 10;
		}
	};

	private Consumer<InputEvent> rotateLeft = inputEvent -> {
		direction = direction.rotate(Vector3.Z, 10);
		//shapeRenderer.rotate(0, 0, 0, 10);
	};

	private Consumer<InputEvent> rotateRight = inputEvent -> {
		direction = direction.rotate(Vector3.Z, -10);
		//shapeRenderer.getTransformMatrix().rotate(position.x, 0, 0, -10);

	};

	@Override
	public void draw(Batch batch, float parentAlpha) {
		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
		shapeRenderer.rect(body.x, body.y, 50, 10);
		shapeRenderer.end();

		LightMain.INPUT_HANDLER.getInputMap().forEach((key, detail) ->
				controlMap.getOrDefault(key, e -> logger.log(Level.FINEST, "Non bound key pressed"))
						.accept(detail));
	}

}