package com.tie.light.entities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.tie.light.LightMain;
import com.tie.light.input.InputEvent;
import com.tie.light.input.InputKey;
import com.tie.light.logic.Collider;
import com.tie.light.logic.Controllable;
import com.tie.light.logic.Walldragger;
import com.tie.light.screen.PlayScreen;

import java.util.function.Consumer;
import java.util.logging.Logger;

@Walldragger
public class Bike extends Entity implements Collider, Controllable {

	private final Logger logger = Logger.getLogger(Bike.class.getName());

	private int player;

	private Float speed;
	private Float dir = 0f;

	private static final Float UNIT_ROTATION = 7f;
	private static final Float UNIT_SPEED = 10f;

	protected PolygonSpriteBatch polyBatch  = new PolygonSpriteBatch();

	private Wall wall;

	private InputKey left;
	private InputKey right;

	public Bike(int player, Controller controller) {
		this.player = player;
		setWidth(10);
		setHeight(20);

		position = new Vector2(50f, 40f);
		destination = new Vector2(50f, 40f);

		poly = createRectanglePolygon(getWidth(), getHeight());

		direction = new Vector2(1, 0);
		speed = UNIT_SPEED;

		if(player == 0) {
			left = new InputKey(Input.Keys.A, controller);
			right = new InputKey(Input.Keys.D, controller);
			controlMap.put(new InputKey(Input.Keys.W, controller), moveForward);
			controlMap.put(new InputKey(Input.Keys.S, controller), moveBackward);
			controlMap.put(left, rotateLeft);
			controlMap.put(right, rotateRight);
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
		if(!LightMain.INPUT_HANDLER.getInputMap().containsKey(right)) {
			if (e.fire(20L, (a) -> wall = wall.spawnWall(position))) {
				direction = direction.rotate(UNIT_ROTATION);
				dir = (dir + UNIT_ROTATION) % 360;
				poly.setRotation(dir);
			}
		}
	};
	private Consumer<InputEvent> rotateRight = e -> {
		if(!LightMain.INPUT_HANDLER.getInputMap().containsKey(left)) {
			if (e.fire(20L, (a) -> wall = wall.spawnWall(position))) {
				direction = direction.rotate(-UNIT_ROTATION);
				dir = (dir - UNIT_ROTATION) % 360;
				poly.setRotation(dir);
			}
		}
	};

	@Override
	public void draw(Batch batch, float parentAlpha) {
		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		//moveForward.accept(CONTINUOUS_INPUT_EVENT);
		destination = new Vector2(position.x + direction.x, position.y + direction.y);

		polyBatch.begin();
		wall.draw(polyBatch, parentAlpha);
		poly.draw(polyBatch);
		poly.setPosition(position.x, position.y);
		polyBatch.end();
		handleInput();
	}

	public void kill() {
		logger.info("KIIILLED");
		PlayScreen.bikes.remove(this);
	}

	public Wall getWall() {
		return wall;
	}

	public void setWall(Wall wall) {
		this.wall = wall;
	}
}