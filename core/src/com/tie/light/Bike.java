package com.tie.light;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Bike extends Actor {

	private final Logger logger = Logger.getLogger(Bike.class.getName());

	private ShapeRenderer shapeRenderer;

	private Vector3 position = new Vector3();

	private int id;

	private Map<Integer, Consumer<Long>> controlMap = new HashMap<>();


	private Consumer<Long> moveForward = e -> {
		if(System.currentTimeMillis() - e == 0) {
			this.position.x += 10;
		}
	};

	private Consumer<Long> moveBackward = e -> {
		this.position.x -= 10;
	};

	public Bike(int id) {
		this.id = id;
		shapeRenderer = new ShapeRenderer();

		controlMap.put(Input.Keys.W, moveForward);
		controlMap.put(Input.Keys.S, moveBackward);

	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
		shapeRenderer.rect(position.x, position.y, 50, 50);
		shapeRenderer.end();

		LightMain.INPUT_HANDLER.getPressMap().forEach((key, elapsed) ->
				controlMap.getOrDefault(key, e -> logger.log(Level.INFO, "Non bound key pressed"))
						.accept(elapsed));
	}

}