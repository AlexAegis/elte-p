package com.tie.light.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.tie.light.logic.Collider;
import com.tie.light.screen.PlayScreen;

import java.util.logging.Logger;
import java.util.stream.Stream;

public class Wall extends Entity implements Collider {

	private final Logger logger = Logger.getLogger(Wall.class.getName());

	private Wall next;
	private String colorStr;

	public Wall(Vector2 from, Vector2 to, String colorStr) {
		this.colorStr = colorStr;
		position = from;
		direction = to;
		setWidth(8);
		poly = createRectanglePolygon(getWidth(), getHeight(), colorStr);
		poly.setPosition(position.x, position.y);
	}

	public Wall(Vector2 from, Vector2 to, Wall next, String color) {
		this(from, to, color);
		this.next = next;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

		overlaps().forEach(b -> b.kill(this));

		setHeight(distance(position, direction).floatValue());
		poly.setRotation(getAngle());
		poly.getRegion().getVertices()[2] = getHeight();
		poly.getRegion().getVertices()[4] = getHeight();

		poly.draw((PolygonSpriteBatch) batch);

		if (next != null) {
			next.draw(batch, parentAlpha);
		}

	}

	public Wall spawnWall(Vector2 position) {
		this.direction = new Vector2(direction);
		return new Wall(new Vector2(position), position, this, colorStr);
	}

	public Stream<Bike> overlaps() {
		return PlayScreen.bikes.stream()
				.filter(bike -> (bike.getWall() != null
						&& bike.getWall().next != null
						&& bike.getWall().next.next != null
						&& !position.equals(bike.getWall().position)
						&& !position.equals(bike.getWall().next.position)
						&& !position.equals(bike.getWall().next.next.position)
						&& (doIntersect(bike.position, bike.destination, this.position, this.direction)
							|| doIntersect(bike.getWall().position, bike.getWall().direction, this.position, this.direction))));
	}

	private Boolean intersect(Entity bike) {
		return doIntersect(bike.position, bike.destination, this.position, this.direction);
	}

	public static boolean onSegment(Vector2 p, Vector2 q, Vector2 r) {
		return (q.x <= Math.max(p.x, r.x) && q.x >= Math.min(p.x, r.x)) &&
				(q.y <= Math.max(p.y, r.y) && q.y >= Math.min(p.y, r.y));
	}

	public static float orientation(Vector2 p, Vector2 q, Vector2 r) {
		float val = (q.y - p.y) * (r.x - q.x) -
				(q.x - p.x) * (r.y - q.y);

		if (val == 0) return 0;

		return (val > 0) ? 1 : 2;
	}

	public static boolean doIntersect(Vector2 a1, Vector2 a2, Vector2 b1, Vector2 b2) {
		//System.out.println(" p1: " + a1 + " q1: " + a2 + " p2: " + b1 + " q2: " + b2);
		// Find the four orientations needed for general and
		// special cases
		float o1 = orientation(a1, a2, b1);
		float o2 = orientation(a1, a2, b2);
		float o3 = orientation(b1, b2, a1);
		float o4 = orientation(b1, b2, a2);

		// General case
		if (o1 != o2 && o3 != o4)
			return true;

		// Special Cases
		// p1, q1 and p2 are colinear and p2 lies on segment p1q1
		if (o1 == 0 && onSegment(a1, b1, a2)) return true;

		// p1, q1 and q2 are colinear and q2 lies on segment p1q1
		if (o2 == 0 && onSegment(a1, b2, a2)) return true;

		// p2, q2 and p1 are colinear and p1 lies on segment p2q2
		if (o3 == 0 && onSegment(b1, a1, b2)) return true;

		// p2, q2 and q1 are colinear and q1 lies on segment p2q2
		if (o4 == 0 && onSegment(b1, a2, b2)) return true;

		return false; // Doesn't fall in any of the above cases
	}

}