package com.tie.light.entities;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public abstract class Entity extends Actor {

	protected final Logger logger = Logger.getLogger(Entity.class.getName());

	protected Vector2 position;
	protected Vector2 direction;
	protected Vector2 destination;
	protected PolygonSprite poly;
	protected static final String defaultColor = "0x0010DEAD";
	protected int color;
	protected String colorString;

	public Double distance(Vector2 object1, Vector2 object2) {
		return Math.sqrt(Math.pow((object2.x - object1.x), 2) + Math.pow((object2.y - object1.y), 2));
	}

	public static PolygonSprite createRectanglePolygon(Float width, Float height, String color) {
		Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		if(color != null) {
			pix.setColor(Integer.decode(color));
		} else {
			pix.setColor(Integer.decode(defaultColor));
		}
		pix.fill();
		Texture textureSolid = new Texture(pix);
		PolygonRegion polyReg = new PolygonRegion(new TextureRegion(textureSolid),
				new float[]{                        // Four vertices
						0, -(width / 2),          // Vertex 0         3--2
						height, -(width / 2),      // Vertex 1         | /|
						height, width / 2,          // Vertex 2         |/ |
						0, width / 2               // Vertex 3         0--1
				}, new short[]{
				0, 1, 2,         // Two triangles using vertex indices.
				0, 2, 3          // Take care of the counter-clockwise direction.
		});

		return new PolygonSprite(polyReg);
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

	public PolygonSprite getPoly() {
		return poly;
	}

	public void setPoly(PolygonSprite poly) {
		this.poly = poly;
	}

	public abstract void kill();

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Entity entity = (Entity) o;
		return Objects.equals(position, entity.position) /*&&
				Objects.equals(direction, entity.direction)*/;
	}



/*
	@Override
	public int hashCode() {

		return Objects.hash(position, direction);
	}*/
}