package com.tie.light.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.concurrent.atomic.AtomicInteger;

public class MainMenu implements Screen {

    private SpriteBatch batch;
    protected Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;
    protected Skin skin;
    BitmapFont font24;
    Label label;
    public AssetManager assets;
    Slider slider;

    public MainMenu() {
        assets = new AssetManager();
        initFonts();


        //skin.add("default-font", font24);
        skin = new Skin(Gdx.files.internal("shade/skin/uiskin.json"));

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        stage = new Stage(viewport, batch);
    }


    @Override
    public void show() {
        //Stage should controll input:
        Gdx.input.setInputProcessor(stage);

        final int width = Gdx.graphics.getWidth();
        final int height = Gdx.graphics.getHeight();

        //Create Table
        Table mainTable = new Table();
        //Set table to fill stage
        mainTable.setFillParent(true);
        //Set alignment of contents in the table.
        mainTable.top();

        //Create buttons
        TextButton playButton = new TextButton("Play", skin);
        playButton.setSize(width*0.15f,height*0.1f);
        playButton.setPosition(width*0.02f,height*0.79f);
        AtomicInteger playerCount = new AtomicInteger(1);
         slider = new Slider(1, 4, 1, false, skin);
        slider.setPosition(width*0.02f,height*0.65f);
        slider.addListener((Event event) -> { playerCount.set((int) slider.getValue()); return false; });

        TextButton exitButton = new TextButton("Exit", skin);
        exitButton.setSize(width*0.15f,height*0.1f);
        exitButton.setPosition(width*0.02f,height*0.52f);

        label = new Label("Retro text: " + slider.getValue(),skin); //getValue nem változik
        label.setSize(280,100);
        label.setFontScale(3);
        //label.setPosition(50,Gdx.graphics.getHeight()-120-100);
        //Add listeners to buttons
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new PlayScreen(playerCount.intValue()));
            }
        });
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });


        //Add buttons to table

        mainTable.add(playButton);
        mainTable.row();
        mainTable.add(slider);
        mainTable.row();
        mainTable.add(exitButton);

        //Add table to stage
        //stage.addActor(mainTable); //table nem engedi resize olni a gombokat

        //Add buttons to stage/menu
        stage.addActor(playButton);
        stage.addActor(slider);
        stage.addActor(label);
        stage.addActor(exitButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        label.setText("Retro text: " + String.valueOf((int)slider.getValue()));
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
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
        skin.dispose();
    }

    private void initFonts() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Arcon.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();

        params.size = 24;
        params.color = Color.BLACK;
        font24 = generator.generateFont(params);
    }

}
