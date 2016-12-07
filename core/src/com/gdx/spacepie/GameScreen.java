package com.gdx.spacepie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import gnu.io.SerialPort;

public class GameScreen extends ScreenAdapter {
	private SpacePie spacePie;
	OrthographicCamera camera = new OrthographicCamera(SpacePie.screenWidth , SpacePie.screenHeight);
	World world;
	WorldRenderer worldRenderer;
	SpriteBatch batch;
	BitmapFont font;
	public static BufferedReader reader;
	public static SerialPort serialPort;
	private int turn = 0;
	String line;
//	int checkGameOver = 0;

	public GameScreen(SpacePie spacePie) {
		
		this.spacePie = spacePie;
		new Texture("rocket.png");
		world = new World (spacePie);
		worldRenderer = new WorldRenderer(spacePie, world);
		font = new BitmapFont();
	
	
	}

	@Override
	public void render(float delta) {
		
		boolean rocketBoosted = update(delta);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch = spacePie.batch;
		camera.update();
		batch.setProjectionMatrix(camera.combined);	
		worldRenderer.render(delta, rocketBoosted);
		batch.begin();
		renderScore();
		if (world.a !=0) {
			worldRenderer.gameoverRenderer();
		}
		batch.end();
		if (world.justLaunchGame == true) {
			worldRenderer.startRenderer();
			if (turn == 'l' || turn == 'r'|| Gdx.input.isKeyPressed(Keys.ENTER)) {
				world.gameLaunched();
			}
		}
		

	}
	
	@SuppressWarnings("static-access")
	private boolean update (float delta) {
//		System.out.println(1);
		try {
			if (reader.ready()){

				System.out.println("try read");
				turn = reader.read();
				
					
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("after read");
		System.out.println("char : "+(char)turn);
		
		if (!world.justLaunchGame) {
			world.update(delta);
		}
		if ((world.a == 0) && (!world.justLaunchGame)) { 
			updateRocketDirection();
		} else if (world.a != 0) {
			tryAgain();
		}
		camera.position.x = world.getRocket().getPosition().x;
		camera.position.y = world.getRocket().getPosition().y;

		return updateRocketSpeed ();
	}
	
	private void tryAgain() {
		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			world.init(this.spacePie);
			worldRenderer = new WorldRenderer(spacePie, world);
		}
	}
	private boolean updateRocketSpeed () {
		if(Gdx.input.isKeyPressed(Keys.SPACE)) {
			Rocket.boostSpeed(true);
			return true;
		}
		else {
			Rocket.boostSpeed(false);
			return false;
		}
	}
	
	private void updateRocketDirection () {
		//l
		if(turn == 'l' && (!world.justLaunchGame)) {
			Rocket.updateRocketRotation(-1);
			
		}
		//r
		else if(turn == 'r' && (!world.justLaunchGame)) {
			Rocket.updateRocketRotation(1);
		} 
		turn = 0;
		
	}	
	
	@SuppressWarnings("static-access")

	private void renderScore () {
		font.draw(batch, "   Score: " + world.score + "\nHigh score: " + world.highScore, (float) (world.getRocket().getPosition().x-50)
				, (float) (world.getRocket().getPosition().y+0.4*spacePie.screenHeight));
	}
}
