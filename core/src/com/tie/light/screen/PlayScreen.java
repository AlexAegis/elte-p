package com.tie.light.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.tie.light.LightMain;
import com.tie.light.entities.Bike;
import com.tie.light.entities.Wall;

import java.util.HashSet;
import java.util.Set;

import static com.tie.light.LightMain.INPUT_HANDLER;

public class PlayScreen implements Screen {

	private PolygonSpriteBatch polygonSpriteBatch;

	public static Set<Bike> bikes = new HashSet<>();
	public static Set<Bike> graveyard = new HashSet<>();
	private int playerCount;

	private Wall northernWall;
	private Wall southernWall;
	private Wall westernWall;
	private Wall easternWall;

	PlayScreen(int playerCount) {
		this.playerCount = playerCount;

		INPUT_HANDLER.getInputMap().clear();
	}

	@Override
	public void show() {

		Gdx.input.setInputProcessor(INPUT_HANDLER);

		for (int j = 1; j <= playerCount; j++) {
			bikes.add(new Bike("p" + Integer.toString(j), null));
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

		polygonSpriteBatch = new PolygonSpriteBatch();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		polygonSpriteBatch.begin();
		for (Bike bike : bikes) {
			bike.draw(polygonSpriteBatch, 1);
		}
		northernWall.draw(polygonSpriteBatch, 1);
		southernWall.draw(polygonSpriteBatch, 1);
		westernWall.draw(polygonSpriteBatch, 1);
		easternWall.draw(polygonSpriteBatch, 1);
		polygonSpriteBatch.end();

		bikes.removeAll(graveyard);
		if(bikes.size() == 1) {
			Gdx.app.log(":", String.valueOf(bikes.toArray()[0]));
			((Game)Gdx.app.getApplicationListener()).setScreen(new WinScreen("1"));
		}
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
		polygonSpriteBatch.dispose();
	}

}
