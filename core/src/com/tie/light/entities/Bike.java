package com.tie.light.entities;

import com.badlogic.gdx.Gdx;
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
import com.tie.light.screen.PlayScreen;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Bike extends Entity implements Collider, Controllable {

	private final Logger logger = Logger.getLogger(Bike.class.getName());

	private static final Float UNIT_ROTATION = 7f;
	private static final Float UNIT_SPEED = 500f;

	private Float speed = UNIT_SPEED;
	private Float rotation = UNIT_ROTATION;
	private Float dir = 0f;

	private Wall wall;

	private InputKey left;
	private InputKey right;

	private String name;

	private InputEvent forwardInputEvent = new InputEvent(0L);

	private Map<InputKey, Consumer<InputEvent>> controlMap = new HashMap<>();

	public Bike(String player, Controller controller) {
		controlMap.clear();
		this.name = player;
		setWidth(10);
		setHeight(20);
		colorString = LightMain.PROPERTIES.getProperty(player + ".color");

		switch (player) {
			case "p1":
				position = new Vector2(50f, 40f);
				destination = new Vector2(50f, 40f);
				dir = 0f;
				break;
			case "p2":
				position = new Vector2(Gdx.graphics.getWidth() - 50f, 40f);
				destination = new Vector2(Gdx.graphics.getWidth() - 50f, 40f);
				dir = 180f;
				break;
			case "p3":
				position = new Vector2(Gdx.graphics.getWidth() - 50f, Gdx.graphics.getHeight() - 40f);
				destination = new Vector2(Gdx.graphics.getWidth() - 50f, Gdx.graphics.getHeight() - 40f);
				dir = 180f;
				break;
			case "p4":
				position = new Vector2(50f, Gdx.graphics.getHeight() - 40f);
				destination = new Vector2(50f, Gdx.graphics.getHeight() - 40f);
				dir = 0f;
				break;
		}

		direction = new Vector2(1, 0);

		poly = createRectanglePolygon(getWidth(), getHeight(), colorString == null ? defaultColor : colorString);
		poly.setRotation(dir);
		direction = direction.rotate(dir);

		//forward = new InputKey(Integer.parseInt(LightMain.PROPERTIES.getProperty(player + ".forward")), controller);
		left = new InputKey(Integer.parseInt(LightMain.PROPERTIES.getProperty(player + ".left")), controller);
		right = new InputKey(Integer.parseInt(LightMain.PROPERTIES.getProperty(player + ".right")), controller);
		//controlMap.put(forward, moveForward);
		controlMap.put(left, rotateLeft);
		controlMap.put(right, rotateRight);

		wall = new Wall(new Vector2(position), position, colorString);
	}

	private Consumer<InputEvent> moveForward = e -> {
		if (e.fire(150L, 1, 4, a -> wall = wall.spawnWall(position))) {
			this.position.x += direction.x * speed * Gdx.graphics.getDeltaTime();
			this.position.y += direction.y * speed * Gdx.graphics.getDeltaTime();
		}
	};

	private Consumer<InputEvent> moveBackward = e -> {
		if (e.fire(true)) {
			this.position.x -= direction.x * speed * Gdx.graphics.getDeltaTime();
			this.position.y -= direction.y * speed * Gdx.graphics.getDeltaTime();
		}
	};

	private Consumer<InputEvent> rotateLeft = e -> {
		if(!LightMain.INPUT_HANDLER.getInputMap().containsKey(right)) {
			if (e.fire(5L, (a) -> wall = wall.spawnWall(position))) {
				direction = direction.rotate(rotation);
				dir = (dir + rotation) % 360;
				poly.setRotation(dir);
				destination = new Vector2(position.x + direction.x, position.y + direction.y);
			}
		}
	};
	private Consumer<InputEvent> rotateRight = e -> {
		if(!LightMain.INPUT_HANDLER.getInputMap().containsKey(left)) {
			if (e.fire(5L, (a) -> wall = wall.spawnWall(position))) {
				direction = direction.rotate(-rotation);
				dir = (dir - rotation) % 360;
				poly.setRotation(dir);
				destination = new Vector2(position.x + direction.x, position.y + direction.y);
			}
		}
	};
	private Integer fireCount = 0;
	private Long time = System.currentTimeMillis();

	@Override
	public void draw(Batch batch, float parentAlpha) {
		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		moveForward.accept(forwardInputEvent);

		poly.setPosition(position.x, position.y);
		wall.draw(batch, parentAlpha);
		poly.draw((PolygonSpriteBatch) batch);
		handleInput(controlMap);
	}

	public Long elapsedTime() {
		return System.currentTimeMillis() - time;
	}



	public void kill(Wall killer) {
		logger.log(Level.FINE, "killer" + killer + " me " + this.wall);
		PlayScreen.graveyard.add(this);
	}

	public Wall getWall() {
		return wall;
	}

	public String getName(){return name;}

	public void setWall(Wall wall) {
		this.wall = wall;
	}
}