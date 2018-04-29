package com.tie.light.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
    private Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;
    private Skin skin;
    BitmapFont font24;
    Label playersLabel;
    Label teamLabel;
    private Slider slider;
    private Label.LabelStyle labelStyle;
    private Image image;

    public MainMenu() {
        initFonts();
        labelStyle = new Label.LabelStyle();
        labelStyle.font = font24;
        image = new Image(new Texture(Gdx.files.internal("pics/logo.png")));


//        skin.add("new-font", font24);
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

        //Create buttons


        TextButton playButton = new TextButton("Play", skin);
        playButton.setSize(width*0.15f,height*0.1f);
        playButton.setPosition(width*0.02f,height*0.69f);

        AtomicInteger playerCount = new AtomicInteger(1);
        slider = new Slider(1, 4, 1, false, skin);
        slider.setPosition(width*0.04f,height*0.6f);
        slider.addListener((Event event) -> { playerCount.set((int) slider.getValue()); return false; });

        TextButton exitButton = new TextButton("Exit", skin);
        exitButton.setSize(width*0.15f,height*0.1f);
        exitButton.setPosition(width*0.02f,height*0.47f);

        playersLabel = new Label("Players number: : " + slider.getValue(),labelStyle);
        playersLabel.setPosition(width*0.025f, height*0.63f);

        teamLabel = new Label("Tiny Indigo Elephant",labelStyle);
        teamLabel.setPosition(width*0.025f, 1);
        teamLabel.setFontScale(2,1);

        image.setScale(0.6f);
        image.setPosition(width*0.50f,height*0.20f);

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




        //Add buttons and labels to stage
        stage.addActor(playButton);
        stage.addActor(slider);
        stage.addActor(playersLabel);
        stage.addActor(teamLabel);
        stage.addActor(exitButton);
        stage.addActor(image);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        playersLabel.setText("Players number: " + String.valueOf((int)slider.getValue()));
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
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/mixolydian titling rg.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();

        params.size = 20;
        params.color = Color.WHITE;
        font24 = generator.generateFont(params);



    }

}
