package com.tie.light.entities;

import com.badlogic.gdx.Gdx;
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

import static com.tie.light.LightMain.PROPERTIES;

@Walldragger
public class Bike extends Entity implements Collider, Controllable {

	private final Logger logger = Logger.getLogger(Bike.class.getName());

	private String player;

	private Float speed;
	private Float dir = 0f;

	private static final Float UNIT_ROTATION = 7f;
	private static final Float UNIT_SPEED = 10f;

	protected PolygonSpriteBatch polyBatch  = new PolygonSpriteBatch();

	private Wall wall;

	private InputKey left;
	private InputKey right;
	private InputKey forward;

	private String name;

	public Bike(String player, Controller controller) {
		this.name = player;
		setWidth(10);
		setHeight(20);
		colorString = LightMain.PROPERTIES.getProperty(player + ".color");

		if(player.equals("p1")) {
			position = new Vector2(50f, 40f);
			destination = new Vector2(50f, 40f);
			direction = new Vector2(1, 0);
		} else if(player.equals("p2")) {
			position = new Vector2(Gdx.graphics.getWidth() - 50f, 40f);
			destination = new Vector2(Gdx.graphics.getWidth() - 50f, 40f);
			direction = new Vector2(-1, 0);
		} else if(player.equals("p3")) {
			position = new Vector2(Gdx.graphics.getWidth() - 50f, Gdx.graphics.getHeight() - 40f);
			destination = new Vector2(Gdx.graphics.getWidth() - 50f, Gdx.graphics.getHeight() - 40f);
			direction = new Vector2(-1, 0);
		} else if(player.equals("p4")) {
			position = new Vector2(50f, Gdx.graphics.getHeight() - 40f);
			destination = new Vector2(50f, Gdx.graphics.getHeight() - 40f);
			direction = new Vector2(1, 0);
		}


		poly = createRectanglePolygon(getWidth(), getHeight(), colorString == null ? defaultColor : colorString);

		speed = UNIT_SPEED;

		forward = new InputKey(Integer.parseInt(LightMain.PROPERTIES.getProperty(player + ".forward")), controller);
		left = new InputKey(Integer.parseInt(LightMain.PROPERTIES.getProperty(player + ".left")), controller);
		right = new InputKey(Integer.parseInt(LightMain.PROPERTIES.getProperty(player + ".right")), controller);
		controlMap.put(forward, moveForward);
		controlMap.put(left, rotateLeft);
		controlMap.put(right, rotateRight);

		wall = new Wall(new Vector2(position), position, colorString);
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
		PlayScreen.graveyard.add(this);
	}

	public Wall getWall() {
		return wall;
	}

	public void setWall(Wall wall) {
		this.wall = wall;
	}
}