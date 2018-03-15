package com.tie.light.entities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tie.light.LightMain;
import com.tie.light.input.InputDetail;
import com.tie.light.input.InputKey;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Bike extends Actor {

	private final Logger logger = Logger.getLogger(Bike.class.getName());

	private ShapeRenderer shapeRenderer;

	private Vector3 position = new Vector3();

	private int player;

	private Map<InputKey, Consumer<InputDetail>> controlMap = new HashMap<>();

	public Bike(int player, Controller controller) {
		this.player = player;

		controlMap.put(new InputKey(Input.Keys.W, controller), moveForward);
		controlMap.put(new InputKey(Input.Keys.S, controller), moveBackward);
		controlMap.put(new InputKey(0, controller), moveForward);

		shapeRenderer = new ShapeRenderer();
	}

	public Bike(int player, Controller controller, Vector3 position) {
		this(player, controller);
		this.position = position;
	}

	private Consumer<InputDetail> moveForward = e -> {
		if(e.isInitial()) {
			this.position.x += 10;
		}
	};

	private Consumer<InputDetail> moveBackward = e -> {
		if(e.isInitial()) {
			this.position.x -= 10;
		}
	};

	@Override
	public void draw(Batch batch, float parentAlpha) {
		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
		shapeRenderer.rect(position.x, position.y, 50, 50);
		shapeRenderer.end();

		LightMain.INPUT_HANDLER.getPressMap().forEach((key, detail) ->
				controlMap.getOrDefault(key, e -> logger.log(Level.FINEST, "Non bound key pressed"))
						.accept(detail));
	}

}