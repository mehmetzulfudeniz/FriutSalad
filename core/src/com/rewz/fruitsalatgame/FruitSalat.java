package com.rewz.fruitsalatgame;




import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g3d.particles.influencers.RegionInfluencer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Locale;
import java.util.Random;


public class FruitSalat extends ApplicationAdapter implements InputProcessor {
	SpriteBatch batch;
	Texture background;
	Texture apple;
	Texture banana;
	Texture ruby;
	Texture heart;
	Texture bill;
	BitmapFont font;
	BitmapFont bitmapFont;
	FreeTypeFontGenerator fontGenerator;
	Random random = new Random();
	Array<Fruit> fruitArray = new Array<>();
	
	int lives = 0;
	int score = 0;

	float genCounter = 0;
	private final float startGenSpeed = 1.1f;
	float genSpeed = startGenSpeed;

	private double currentTime;
	private double gameoverTime = -1.0f;


	@Override
	public void create() {
		batch = new SpriteBatch();
		background = new Texture("background.jpg");
		apple = new Texture("apple.png");
		banana = new Texture("banana.png");
		ruby = new Texture("ruby.png");
		heart = new Texture("heart.gif");
		bill = new Texture("bill.png");

		Fruit.radius = Math.max(Gdx.graphics.getHeight(), Gdx.graphics.getWidth()) / 20f;

		Gdx.input.setInputProcessor(this);

		fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("badabb.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		FreeTypeFontGenerator.FreeTypeFontParameter freeTypeFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.color = Color.CYAN;
		parameter.size = 80;
		parameter.characters = "0123456789 Score:.+-";
		font = fontGenerator.generateFont(parameter);

		freeTypeFontParameter.color = Color.BLUE;
		freeTypeFontParameter.size = 200;
		freeTypeFontParameter.toString().toLowerCase(Locale.ROOT);
		freeTypeFontParameter.characters = "Cutoplay";
		bitmapFont = fontGenerator.generateFont(freeTypeFontParameter);



	}

	@Override
	public void render() {
		super.render();

		batch.begin();
		batch.draw(background, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		double newTime = TimeUtils.millis() / 1000.0;
		System.out.println("newTime :" + newTime);
		double frameTime = Math.min(newTime - currentTime, 0.3);
		System.out.println("frameTime :" + frameTime);
		float deltaTime = (float) frameTime;
		System.out.println("deltaTime :" + deltaTime);
		currentTime = newTime;

		if (lives <= 0 && gameoverTime == 0f) {

			gameoverTime = currentTime;

		}

		if (lives > 0) {

			genSpeed -= deltaTime * 0.02f;

			if (genCounter <= 0f) {
				genCounter = genSpeed;
				addItem();
			} else {
				genCounter -= deltaTime;
			}

			for (int i = 0; i < lives; i++) {
				batch.draw(heart, i * 100f +Gdx.graphics.getWidth() / 1.3f, Gdx.graphics.getHeight() / 1.08f, Gdx.graphics.getWidth()/20,Gdx.graphics.getHeight()/20);
			}

			for (Fruit fruit : fruitArray) {
				fruit.update(deltaTime);

				switch (fruit.type) {
					case REGULAR:
						batch.draw(apple, fruit.getPos().x, fruit.getPos().y, Fruit.radius, Fruit.radius);
						break;
					case EXTRA:
						batch.draw(banana, fruit.getPos().x, fruit.getPos().y, Fruit.radius, Fruit.radius);
						break;
					case ENEMY:
						batch.draw(ruby, fruit.getPos().x, fruit.getPos().y, Fruit.radius, Fruit.radius);
						break;
					case LIFE:
						batch.draw(bill, fruit.getPos().x, fruit.getPos().y, Fruit.radius, Fruit.radius);
						break;
				}
			}

			boolean holdlives = false;
			Array<Fruit> toRemove = new Array<>();
			for (Fruit fruit : fruitArray) {
				if (fruit.outOfScreen()) {
					toRemove.add(fruit);

					if (fruit.living && fruit.type == Fruit.Type.REGULAR) {
						lives--;
						holdlives = true;
						break;
					}
				}
			}

			if (holdlives) {
				for (Fruit f : fruitArray) {
					f.living = false;
				}
			}

			for (Fruit f : toRemove) {
				fruitArray.removeValue(f, true);
			}


		}

		font.draw(batch,"Score : " + score, Gdx.graphics.getWidth()/20, Gdx.graphics.getHeight()/1.02f);
		if (lives <= 0) {
			bitmapFont.draw(batch, "Cut to play", Gdx.graphics.getWidth() * 0.3f, Gdx.graphics.getHeight() * 0.6f);
		}
		batch.end();
	}
	private void addItem() {
		float pos = random.nextFloat() * Math.max(Gdx.graphics.getHeight(), Gdx.graphics.getWidth());

		Fruit item = new Fruit(new Vector2(pos, -Fruit.radius), new Vector2((Gdx.graphics.getWidth() * 0.5f - pos) * (0.3f + (random.nextFloat() - 0.5f)), Gdx.graphics.getHeight() * 0.5f));

		float type = random.nextFloat();
		if (type > 0.98) {
			item.type = Fruit.Type.LIFE;
		} else if (type > 0.88) {
			item.type = Fruit.Type.EXTRA;
		} else if (type > 0.78) {
			item.type = Fruit.Type.ENEMY;
		}
		fruitArray.add(item);
	}

	@Override
	public void dispose() {
		super.dispose();
		batch.dispose();
		font.dispose();
		fontGenerator.dispose();

	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
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
		if (lives <= 0 && currentTime - gameoverTime > 2f) {
			gameoverTime = 0f;
			score = 0;
			lives = 4;
			genSpeed = startGenSpeed;
			fruitArray.clear();
		} else {
			Array<Fruit> toRemove = new Array<>();
			Vector2 pos = new Vector2(screenX, Gdx.graphics.getHeight() - screenY);
			int plusScore = 0;
			for (Fruit f : fruitArray) {
				if (f.clicked(pos)) {
					toRemove.add(f);

					switch (f.type) {
						case REGULAR:
							plusScore++;
							break;
						case EXTRA:
							plusScore+=2;
							break;
						case ENEMY:
							lives--;
							break;
						case LIFE:
							lives++;
							break;
					}
				}

			}

			score += plusScore * plusScore;
			for (Fruit f : toRemove) {
				fruitArray.removeValue(f, true);
			}

		}
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		return false;
	}
}
