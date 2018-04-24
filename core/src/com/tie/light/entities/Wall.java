package com.tie.light.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.tie.light.logic.Collider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class Wall extends Entity implements Collider {

	private final Logger logger = Logger.getLogger(Wall.class.getName());

	private Wall next;

	public Wall(Vector2 from, Vector2 to) {
		polyBatch = new PolygonSpriteBatch();

		position = from;
		direction = to;
		setWidth(8);

		poly = createRectanglePolygon(getWidth(), getHeight());
		poly.setPosition(position.x, position.y);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

		setHeight(distance(position, direction).floatValue());
		poly.setRotation(getAngle());
		poly.getRegion().getVertices()[2] = getHeight();
		poly.getRegion().getVertices()[4] = getHeight();

		polyBatch.begin();
		poly.draw(polyBatch);
		polyBatch.end();
		if(next != null) {
			next.draw(batch, parentAlpha);
		}
	}

	public void spawnWall(Vector2 direction) {

/*

		poly.setRegion(new PolygonRegion(
				poly.getRegion().getRegion(),
				Arrays.copyOf(poly.getRegion().getVertices(), poly.getRegion().getVertices().length + 2)
			,   Arrays.copyOf(poly.getRegion().getTriangles(), poly.getRegion().getTriangles().length + 6)));

		int vnl = poly.getRegion().getVertices().length;
		poly.getRegion().getVertices()[vnl - 1] = direction.x;*/

		if(this.next == null) {
			this.direction = new Vector2(this.direction);
			this.next = new Wall(new Vector2(direction), direction);
		} else {
			this.next.spawnWall(direction);
		}
	}
}