package com.tetris.ui;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tetris.game.GameState;


public class MainTetris extends ApplicationAdapter implements InputProcessor
{
	public static int WORLD_WIDTH; // Arbitrary world size (e.g. meters)
	public static int WORLD_HEIGHT;
	// The ammount of world we want to show in our screen
	public static int VIEWPORT_WIDTH;
	public static int VIEWPORT_HEIGHT; // Can be calculated from screen ratio
	// How to transform from pixels to our unit
	public static int PIXEL_TO_METER = 1;
	float ratio;


	private SpriteBatch batch;
	private Sprite sprite;
	private Texture img;
	private GameState gameState;
	private int count = 0;
	private int speed = 30;


	private float BLOCK_SIZE;
	
	@Override
	public void create ()
	{
		//Initialize static values
		WORLD_WIDTH = Gdx.graphics.getWidth();
		WORLD_HEIGHT = Gdx.graphics.getHeight();
		VIEWPORT_WIDTH = Gdx.graphics.getWidth();
		VIEWPORT_HEIGHT = Gdx.graphics.getHeight();
		ratio = ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth());

		batch = new SpriteBatch();

		gameState = new GameState();

		img = new Texture("happySquare.png");
		sprite = new Sprite(img);

		BLOCK_SIZE = Gdx.graphics.getWidth() / 10;
		sprite.setPosition(Gdx.graphics.getWidth() / 2 - sprite.getWidth() / 2,
				Gdx.graphics.getHeight() / 2);

		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render ()
	{
		Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();

		for(int x = 0; x < 10; x ++)
		{
			for(int y = 0; y < 15; y++)
			{
				if(gameState.getDynamicBoard()[y][x] != ' ')
					batch.draw(sprite, x*BLOCK_SIZE, y*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
			}
		}

		batch.end();
		count++;
		if(count >= speed)
		{
			count = 0;
			if (Gdx.input.getAccelerometerX() >= 0.75)
				gameState.strafeLeft();
			else if (Gdx.input.getAccelerometerX() <= -0.75)
				gameState.strafeRight();
			gameState.advance();
		}
	}
	
	@Override
	public void dispose ()
	{
		batch.dispose();
		img.dispose();
	}

	@Override
	public boolean touchDown (int screenX, int screenY, int pointer, int button)
	{
		gameState.rotate();
		return true;
	}

	@Override
	public boolean touchDragged (int screenX, int screenY, int pointer)
	{
        gameState.debug();
		return true;
	}

	@Override
	public boolean touchUp (int screenX, int screenY, int pointer, int button)
	{
		return false;
	}

	@Override
	public void resize (int width, int height)
	{
		return;
	}

	@Override
	public boolean keyDown (int keycode)
	{
		return false;
	}

	@Override
	public boolean keyUp (int keycode)
	{
		return false;
	}

	@Override
	public boolean keyTyped (char character)
	{
		return false;
	}

	@Override
	public boolean scrolled (int amount)
	{
		return false;
	}

	@Override
	public boolean mouseMoved (int screenX, int screenY)
	{
		return false;
	}
}
