package com.tie.light.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.tie.light.LightMain;
import com.tie.light.entities.Bike;
import com.tie.light.entities.Wall;

import java.util.HashSet;
import java.util.Set;

import static com.tie.light.LightMain.INPUT_HANDLER;

public class PlayScreen implements Screen {

	private SpriteBatch batch;
	private PolygonSpriteBatch polygonSpriteBatch;

	public static Set<Bike> bikes = new HashSet<>();
	private int playerCount;

	private Wall northernWall;
	private Wall southernWall;
	private Wall westernWall;
	private Wall easternWall;

	public PlayScreen(int playerCount) {
		this.playerCount = playerCount;
	}

	@Override
	public void show() {

		Gdx.input.setInputProcessor(INPUT_HANDLER);


		int i = 0;
		bikes.add(new Bike(i, null, LightMain.PROPERTIES.getProperty("p1.color")));

		i++;
		//bikes.add(new Bike(i, null));
		for (Controller controller : Controllers.getControllers()) {
			i++;
			controller.addListener(INPUT_HANDLER);
			bikes.add(new com.tie.light.entities.Bike(i, controller, null));
		}

		northernWall = new Wall(new Vector2(0, Gdx.graphics.getHeight()),
				new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()),
				LightMain.PROPERTIES.getProperty("edge.color"));
		southernWall = new Wall(new Vector2(0, 0),
				new Vector2(Gdx.graphics.getWidth(), 0),
				LightMain.PROPERTIES.getProperty("edge.color"));
		westernWall = new Wall(new Vector2(0, 0),
				new Vector2(0, Gdx.graphics.getHeight()),
				LightMain.PROPERTIES.getProperty("edge.color"));
		easternWall = new Wall(new Vector2(Gdx.graphics.getWidth(), 0),
				new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()),
				LightMain.PROPERTIES.getProperty("edge.color"));

		batch = new SpriteBatch();
		polygonSpriteBatch = new PolygonSpriteBatch();

		batch = new SpriteBatch();

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		batch.begin();


		for (Bike bike : bikes) {
			bike.draw(batch, 1);
		}

		batch.end();
		polygonSpriteBatch.begin();
		northernWall.draw(polygonSpriteBatch, 1);
		southernWall.draw(polygonSpriteBatch, 1);
		westernWall.draw(polygonSpriteBatch, 1);
		easternWall.draw(polygonSpriteBatch, 1);
		polygonSpriteBatch.end();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		batch.dispose();
		polygonSpriteBatch.dispose();
	}

}
