package com.tie.light;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Shape;
import com.tie.light.Player;

import java.time.Clock;
import java.util.ArrayList;

public class LightMain extends ApplicationAdapter implements InputProcessor {
	SpriteBatch batch;
	BitmapFont font;
	Texture img;
	private int screenWidth, screenHeight;
	GlyphLayout layout;
	private String msg = "No mouse clicked." ;
	float turn;
	Player player;
	Matrix4 mx4Font;
	ShapeRenderer shapeRenderer;
	Polygon polygon;
	ArrayList<Float> list;

	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.setColor(Color.BLUE);
		font.getData().setScale(1);
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		turn = 0;
		player = new Player();
		layout = new GlyphLayout();
		Gdx.input.setInputProcessor(this);
		shapeRenderer = new ShapeRenderer();
		mx4Font = new Matrix4();
		polygon = new Polygon();
		list = new ArrayList<Float>();


		img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor((float)0.75, (float)0.75, (float)0.75, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//layout.setText(font,"why?");

//		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//		shapeRenderer.setColor(80 / 255.0f, 80 / 255.0f, 50 / 255.0f, 1);
//		shapeRenderer.rect(player.x, player.y, 80, 80);
//		shapeRenderer.end();

		polygon = new Polygon(new float[]{0,0,20,0,10,30});
		//polygon.translate(300,300);
		//polygon.scale((float)-0.5);
		polygon.setOrigin(polygon.getOriginX() + 10, polygon.getOriginY() + 15);

		polygon.setPosition(player.x, player.y);


		//System.out.println(polygon.getTransformedVertices()[0] + " : " + polygon.getTransformedVertices()[1]);

		polygon.rotate(-(player.turn()));


			shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
			shapeRenderer.polygon(polygon.getTransformedVertices());
			shapeRenderer.end();

		System.out.println(polygon.getOriginX()+player.x+"  "+polygon.getOriginY()+player.y);

		batch.begin();
		font.draw(batch, screenWidth+"x"+screenHeight,screenWidth/2-layout.width/2,300+screenHeight/2+layout.height/2);

		list.add(polygon.getOriginX()+player.x);
		list.add(polygon.getOriginY()+player.y);
		for(int i = 0; i < list.size(); i+=2){
			font.draw(batch, "o",list.get(i),list.get(i+1));
			if(list.size()>1000){
				//list.remove(0);
				//list.remove(0);
			}
		}
		//font.draw(batch,shapeRenderer,player.x,player.y);

		player.update();

		batch.end();


	}

	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
		img.dispose();
	}

	private void setTurn(float direction){
		turn = direction;
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode){
			case Input.Keys.A:
				player.direction = -1;
				break;
			case Input.Keys.D:
				player.direction = 1;
				break;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode){
			case Input.Keys.A:
				player.direction = 0;
				break;
			case Input.Keys.D:
				player.direction = 0;
				break;
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
