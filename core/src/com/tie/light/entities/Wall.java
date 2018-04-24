package com.tie.light.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.tie.light.logic.Collider;

import java.util.logging.Logger;

public class Wall extends Entity implements Collider {

	private final Logger logger = Logger.getLogger(Bike.class.getName());

	private PolygonSprite poly;

	public Wall(Vector2 from, Vector2 to) {
		position = from;
		direction = to;

		setWidth(40);
		setHeight(distance(from, to).floatValue());




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
		poly.setOrigin(0, 0);
		polyBatch = new PolygonSpriteBatch();
		poly.setPosition(position.x, position.y);
	}

	double rot = 0;
	@Override
	public void draw(Batch batch, float parentAlpha) {
		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		polyBatch.begin();
		setHeight(distance(position, direction).floatValue());
		poly.setRotation(getAngle());
		poly.getRegion().getVertices()[2] = getHeight();
		poly.getRegion().getVertices()[4] = getHeight();
		poly.draw(polyBatch);
		polyBatch.end();
	}

}