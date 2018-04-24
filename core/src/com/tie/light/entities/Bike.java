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

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Bike extends Entity implements Collider, Controllable {

	private final Logger logger = Logger.getLogger(Bike.class.getName());

	private int player;

	private Float speed;
	private Float dir;

	private static final Float UNIT_ROTATION = 9f;
	private static final Float UNIT_SPEED = 10f;

	private PolygonSprite poly;

	private Texture textureSolid;

	public Bike(int player, Controller controller) {
		this.player = player;

		setWidth(10);
		setHeight(20);
		position = new Vector2(50f, 40f);

		this.dir = 0f;

		Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		pix.setColor(0x0010DEAD);
		pix.fill();
		textureSolid = new Texture(pix);
		PolygonRegion polyReg = new PolygonRegion(new TextureRegion(textureSolid),
				new float[]{                     // Four vertices
						0, 0,                    // Vertex 0         3--2
						getHeight(), 0,          // Vertex 1         | /|
						getHeight(), getWidth(), // Vertex 2         |/ |
						0, getWidth()            // Vertex 3         0--1
				}, new short[]{
				0, 1, 2,         // Two triangles using vertex indices.
				0, 2, 3          // Take care of the counter-clockwise direction.
		});
		poly = new PolygonSprite(polyReg);
		poly.setOrigin(getHeight() / 2, getWidth() / 2);
		polyBatch = new PolygonSpriteBatch();


		direction = new Vector2(1, 0);
		speed = UNIT_SPEED;

		controlMap.put(new InputKey(Input.Keys.W, controller), moveForward);
		controlMap.put(new InputKey(Input.Keys.S, controller), moveBackward);
		controlMap.put(new InputKey(Input.Keys.A, controller), rotateLeft);
		controlMap.put(new InputKey(Input.Keys.D, controller), rotateRight);

		//controlMap.put(new InputKey(Xbox.DPAD_UP, controller), moveForward);
		//controlMap.put(new InputKey(Xbox.DPAD_DOWN, controller), moveBackward);
		//controlMap.put(new InputKey(Xbox.DPAD_LEFT, controller), rotateLeft);
		//controlMap.put(new InputKey(Xbox.DPAD_RIGHT, controller), rotateRight);
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
		}
	};

	private Consumer<InputEvent> rotateRight = e -> {
		if (e.fire(true)) {
			direction = direction.rotate(-UNIT_ROTATION);
			dir = (dir - UNIT_ROTATION) % 360;
			poly.setRotation(dir);
		}
	};

	@Override
	public void draw(Batch batch, float parentAlpha) {
		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

		//moveForward.accept(CONTINUOUS_INPUT_EVENT);
		polyBatch.begin();
		poly.draw(polyBatch);
		polyBatch.end();
		poly.setPosition(position.x, position.y);

		handleInput();
	}

}