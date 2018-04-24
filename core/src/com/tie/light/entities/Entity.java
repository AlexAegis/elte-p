package com.tie.light.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tie.light.input.InputEvent;
import com.tie.light.input.InputKey;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Logger;

public abstract class Entity extends Actor {

	protected final Logger logger = Logger.getLogger(Entity.class.getName());


	protected Vector2 position;
	protected Vector2 direction;
	protected PolygonSpriteBatch polyBatch;
	protected Texture textureSolid;

	public Double distance(Vector2 object1, Vector2 object2) {
		return Math.sqrt(Math.pow((object2.x - object1.x), 2) + Math.pow((object2.y - object1.y), 2));
	}

	public float getAngle(Vector2 target) {
		float angle = (float) Math.toDegrees(Math.atan2(target.y - position.y, target.x - position.x));
		if (angle < 0) {
			angle += 360;
		}
		return angle;
	}

	public float getAngle() {
		return getAngle(direction);
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public Vector2 getDirection() {
		return direction;
	}

	public void setDirection(Vector2 direction) {
		this.direction = direction;
	}
}