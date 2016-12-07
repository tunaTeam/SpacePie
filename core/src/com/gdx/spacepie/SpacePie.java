package com.gdx.spacepie;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import gnu.io.SerialPort;

public class SpacePie extends Game {
	SpriteBatch batch;
	
	public static int screenWidth = 960;
	public static int screenHeight = 540;
	public static InputStream in;
	public static OutputStream out;

	Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new GameScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		WorldRenderer.rocketImg.dispose();
		batch.dispose();
	}
}
