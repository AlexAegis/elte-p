package com.tie.light.entities;

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
	protected PolygonSpriteBatch polyBatch = new PolygonSpriteBatch();

}