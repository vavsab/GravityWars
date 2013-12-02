package vavsab.gravitywars.game.view;


import java.util.HashMap;
import java.util.Map;

import vavsab.gravitywars.R;
import vavsab.gravitywars.game.controller.WorldController;
import vavsab.gravitywars.game.model.Blowable;
import vavsab.gravitywars.game.model.Body;
import vavsab.gravitywars.game.model.Bullet;
import vavsab.gravitywars.game.model.ControlButton;
import vavsab.gravitywars.game.model.Planet;
import vavsab.gravitywars.game.model.Ship;
import vavsab.gravitywars.game.model.Target;
import vavsab.gravitywars.game.model.World;
import android.content.Context;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

public class WorldRenderer {
	public static float CAMERA_WIDTH = 3 * 32f;
	public static float CAMERA_HEIGHT = 3 * 24f;

	Context context;

	private Stage stage;
	TextButton fireButton;

	ShapeRenderer shapeRenderer = new ShapeRenderer();
	SpriteBatch batch = new SpriteBatch();
	Sprite sprite = new Sprite();
	Texture backgroundTexture;
	TextureRegion bulletTexture;
	Map<String, TextureRegion> shipsTexture = new HashMap<String, TextureRegion>();
	Map<String, TextureRegion> planetsTexture = new HashMap<String, TextureRegion>();
	TextureRegion[] blowTexture;
	BitmapFont font = new BitmapFont();

	private World world;
	private WorldController controller;
	public OrthographicCamera cam;
	ShapeRenderer renderer = new ShapeRenderer();
	boolean isActiveGlobalZoom;

	void switchToGlobalZoom() {
		ppuX = (float) width / CAMERA_WIDTH;
		ppuY = (float) height / CAMERA_HEIGHT;
		offsetX = ppuX * CAMERA_WIDTH / 3;
		offsetY = ppuY * CAMERA_HEIGHT / 3;
		isActiveGlobalZoom = true;
	}

	void switchToNormalZoom() {
		ppuX = (float) width / CAMERA_WIDTH * 3;
		ppuY = (float) height / CAMERA_HEIGHT * 3;
		offsetX = 0;
		offsetY = 0;
		isActiveGlobalZoom = false;
	}

	public int width;
	public int height;
	public float ppuX; // пикселей на точку мира по X
	public float ppuY; // пикселей на точку мира по Y
	public float offsetX; // смещение прорисовки мира по Х
	public float offsetY; // смещение прорисовки мира по Y

	public float getPosX(float worldCoordX) {
		return worldCoordX * ppuX + offsetX;
	}

	public float getPosY(float worldCoordY) {
		return worldCoordY * ppuY + offsetY;
	}

	public float getLenX(float worldCoordX) {
		return worldCoordX * ppuX;
	}

	public float getLenY(float worldCoordY) {
		return worldCoordY * ppuY;
	}

	public void setSize(int w, int h) {
		this.width = w;
		this.height = h;
		switchToNormalZoom();
	}

	public void SetCamera(float x, float y) {
		this.cam.position.set(x, y, 0);
		this.cam.update();
	}

	public WorldRenderer(World world, Context context, Stage stage,
			WorldController controller) {
		this.stage = stage;
		this.context = context;
		this.world = world;
		this.controller = controller;
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		SetCamera(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f);
		bulletTexture = new TextureRegion(new Texture(
				Gdx.files.internal("sprites/Bullet.png")));
		backgroundTexture = new Texture(
				Gdx.files.internal("sprites/bgSpace.jpg"));
		// planets
		TextureRegion texture = new TextureRegion(new Texture(
				Gdx.files.internal("sprites/Planets.png")));
		TextureRegion[][] region = texture.split(texture.getRegionHeight() / 4,
				texture.getRegionWidth() / 4);
		String[] match = context.getResources().getStringArray(R.array.planets);
		int i = 0;
		for (TextureRegion[] reg : region) {
			for (TextureRegion cur : reg) {
				if (i >= match.length)
					break;
				planetsTexture.put(match[i], cur);
				i++;
			}
		}

		// ships
		texture = new TextureRegion(new Texture(
				Gdx.files.internal("sprites/Ships.png")));
		region = texture.split(texture.getRegionHeight() / 2,
				texture.getRegionWidth() / 4);
		match = context.getResources().getStringArray(R.array.ships);
		i = 0;
		for (TextureRegion[] reg : region) {
			for (TextureRegion cur : reg) {
				if (i >= match.length)
					break;
				shipsTexture.put(match[i], cur);
				i++;
			}
		}

		// blow
		texture = new TextureRegion(new Texture(
				Gdx.files.internal("sprites/BulletBlow.png")));
		region = texture.split(texture.getRegionHeight() / 3,
				texture.getRegionWidth() / 3);
		blowTexture = new TextureRegion[9];
		i = 0;
		for (TextureRegion[] reg : region) {
			for (TextureRegion cur : reg) {
				blowTexture[i] = cur;
				i++;
			}
		}

		stage.setViewport(width, height, true);
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);

		// Add widgets to the table here.

		TextureRegion upRegion = new TextureRegion(new Texture(
				Gdx.files.internal("buttons/FireButton.png")));
		TextureRegion downRegion = new TextureRegion(new Texture(
				Gdx.files.internal("buttons/FireButton_Pressed.png")));
		BitmapFont buttonFont = new BitmapFont();

		TextButtonStyle style = new TextButtonStyle();
		style.up = new TextureRegionDrawable(upRegion);
		style.down = new TextureRegionDrawable(downRegion);
		style.font = buttonFont;

		fireButton = new TextButton("", style);
		fireButton.setVisible(true);
		// Player pl = new Player(new Vector2());
		// pl.world = this.world;
		fireButton.addListener(controller);

		table.addActor(fireButton);
	}

	public void render() {
		switchToNormalZoom();
		Bullet lastBullet = world.lastBullet;
		if (lastBullet != null && !lastBullet.isDead()) {
			Vector2 pos = lastBullet.getCenter();
			if (pos.x < 0 || pos.y < 0 || pos.x > world.width
					|| pos.y > world.height)
				switchToGlobalZoom();
		}
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		Vector2 point = Target.getPointOfDestination();
		world.setStatus("Last touch pos:" + point.x + ", " + point.y);
		drawBackground();
		// drawBricks();
		drawBodies();
		if (Target.enabled)
			drawTargetLine();

		// draw status
		batch.begin();
		font.setColor(.5f, .5f, .5f, .5f);
		// world.setStatus("Power :" + Float.toString(Target.getPower()));
		font.draw(batch, world.getStatus(), 0, height);
		batch.end();
		// drawPlayer();
		drawButtons();

		if (isActiveGlobalZoom) {
			Gdx.gl.glEnable(GL10.GL_BLEND);
		    Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(0, 1, 0, 0.3f);
			shapeRenderer.rect(getPosX(0), getPosY(0),
					getLenX(world.width), getLenY(world.height));
			shapeRenderer.end();
			Gdx.gl.glDisable(GL10.GL_BLEND);
		}
	}

	private void drawButtons() {
		if (!Target.enabled) {
			fireButton.setVisible(false);
			return;
		}
		fireButton.setVisible(true);
		Color color = fireButton.getColor();
		ControlButton butt = world.getButtons().get("fire-button");
		fireButton.setColor(color.r, color.g, color.b, .7f);
		fireButton.setPosition(getPosX(butt.getPosition().x),
				getPosY(butt.getPosition().y));
		fireButton
				.setSize(getLenX(butt.getSize().x), getLenY(butt.getSize().y));
	}

	private void drawBackground() {
		batch.begin();
		int maxSize = Math.max(width, height);
		batch.draw(backgroundTexture, 0, 0, maxSize, maxSize);
		batch.end();
	}

	private void drawTargetLine() {
		// shapeRenderer.setProjectionMatrix(cam.combined);
		if (Target.enabled) {
			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.setColor(Color.GREEN);
			Vector2 center = Target.activeShip.getCenter();
			Vector2 point = Target.getPointOfDestination();
			shapeRenderer.line(getPosX(center.x), getPosY(center.y),
					getPosX(point.x), getPosY(point.y));
			shapeRenderer.end();
		}
	}

	//
	// private void drawBricks() {
	// renderer.setProjectionMatrix(cam.combined);
	//
	// renderer.begin(ShapeType.FilledRectangle);
	// for (Brick brick : world.getBricks()) {
	// Rectangle rect = brick.getBounds();
	// float x1 = brick.getPosition().x + rect.x;
	// float y1 = brick.getPosition().y + rect.y;
	// renderer.setColor(new Color(0, 0, 0, 1));
	// renderer.filledRect(x1, y1, rect.width, rect.height);
	// }
	//
	// renderer.end();
	// }
	//
	// private void drawPlayer() {
	//
	// renderer.setProjectionMatrix(cam.combined);
	// Player player = world.getPlayer();
	// renderer.begin(ShapeType.Rectangle);
	//
	// Rectangle rect = player.getBounds();
	// float x1 = player.getPosition().x + rect.x;
	// float y1 = player.getPosition().y + rect.y;
	// renderer.setColor(new Color(1, 0, 0, 1));
	// renderer.rect(x1, y1, rect.width, rect.height);
	// renderer.end();
	// }

	private void drawBodies() {
		Array<Body> bodies = world.getBodies();
		// batch.disableBlending();
		batch.begin();
		TextureRegion text = null;
		for (int i = 0; i < bodies.size; i++) {
			Body cur = bodies.get(i);
			if (cur instanceof Planet) {
				text = planetsTexture.get(cur.getName());
				if (text == null)
					text = planetsTexture.get("default");
			} else if (cur instanceof Bullet) {
				text = bulletTexture;
			} else if (cur instanceof Ship) {
				text = shipsTexture.get(cur.getName());
				if (text == null)
					text = shipsTexture.get("default");
				if (cur.getName() == "red")
					world.addStatus(" Angle: " + Float.toString(cur.getAngle()));
			}
			if (text == null)
				continue;
			if (cur instanceof Blowable) {
				Blowable blow = (Blowable) cur;
				if (blow.isDead()) {
					bodies.removeValue(cur, false);
					continue;
				}
				if (blow.isDying()) {
					text = blowTexture[blow.getCurDyingFrame()];
				}
			}

			sprite.setRegion(text);
			sprite.setPosition(getPosX(cur.getPosition().x),
					getPosY(cur.getPosition().y));
			sprite.setSize(getLenX(cur.getSize().x), getLenY(cur.getSize().y));
			sprite.setOrigin(getLenX(cur.getSize().x / 2),
					getLenY(cur.getSize().y / 2));
			sprite.setRotation(cur.getAngle());
			sprite.draw(batch);
		}
		batch.end();
		// batch.enableBlending();
	}
}
