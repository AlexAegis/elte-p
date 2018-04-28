package com.tie.light.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tie.light.entities.Bike;

import java.util.HashSet;
import java.util.Set;

import static com.tie.light.LightMain.INPUT_HANDLER;

public class PlayScreen implements Screen {

    private SpriteBatch batch;
    private Set<Bike> bikes = new HashSet<>();
    private int playerCount;

    public PlayScreen(int playerCount) {
        this.playerCount = playerCount;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(INPUT_HANDLER);

        int i;
        for(i = 0; i<playerCount; i++) {
            bikes.add(new Bike(i, null));
        }
        for (Controller controller : Controllers.getControllers()) {
            i++;
            controller.addListener(INPUT_HANDLER);
            bikes.add(new com.tie.light.entities.Bike(i, controller));
        }
        //northernWall = new Wall(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2), new Vector2(800, 200));

        batch = new SpriteBatch();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        //northernWall.getDirection().set(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
        //batch.setProjectionMatrix(new Matrix4(new Quaternion(10,0,0,100) ));
        //northernWall.draw(batch, 1);
        for (Bike bike : bikes) {
            bike.draw(batch, 1);
        }

        batch.end();
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

    }
}
