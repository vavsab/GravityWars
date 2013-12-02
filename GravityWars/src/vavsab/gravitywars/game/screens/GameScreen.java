package vavsab.gravitywars.game.screens;
import vavsab.gravitywars.game.GameManager;
import vavsab.gravitywars.game.controller.WorldController;
import vavsab.gravitywars.game.maps.RoundConfig;
import vavsab.gravitywars.game.model.Player;
import vavsab.gravitywars.game.model.Ship;
import vavsab.gravitywars.game.model.Target;
import vavsab.gravitywars.game.model.World;
import vavsab.gravitywars.game.view.WorldRenderer;
import android.content.Context;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen, InputProcessor {
	private World 			world;
	private WorldRenderer 	renderer;
	private WorldController	controller;
	private Stage stage;
	private Context context;
	private RoundConfig roundConfig;

	private int width, height;
	
	public GameScreen(Context context, RoundConfig roundConfig)
	{
		this.roundConfig = roundConfig;
		this.context = context;
		stage = new Stage();
		InputMultiplexer inputMultiplexer = new InputMultiplexer(stage, this);
		Gdx.input.setInputProcessor(inputMultiplexer);
	}
	
	
	@Override
	public void show() {

		world = new World(roundConfig, 0);
		controller = new WorldController(world);
		renderer = new WorldRenderer(world, context, stage, controller);
		Array<Player> pls = new Array<Player>();
		for (vavsab.gravitywars.game.model.Body cur : world.getBodies())
		{
			if (cur instanceof Ship)
			{
				Player pl = new Player(cur.getName(), (Ship)cur);
				pls.add(pl);
			}
		}
		GameManager gm = GameManager.getInstance();
		gm.setPlayers(pls);
		gm.setActivePlayer(pls.get(0));
		
		//Target.activeBody = world.getBodies().get(1);
		Target.setPointOfDestination(new Vector2(0, 0));
		

	}
	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		ChangeNavigation(x,y);
		return false;
	}

	
	public boolean touchMoved(int x, int y) {
		return false;
	}

	@Override
	public boolean mouseMoved(int x, int y) {
		return false;
	}
	
	@Override
	public boolean keyTyped(char character) {
		return false;
	}
	
	@Override
	public void resize(int width, int height) {
		renderer.setSize(width, height);
		stage.setViewport(width, height, true);
		this.width = width;
		this.height = height;
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {		
		stage.dispose();
	}


	@Override
	public boolean keyDown(int keycode) {
		
		return true;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		controller.update(delta);
		stage.act(Gdx.graphics.getDeltaTime());
		
		renderer.render();
        stage.draw();
	}
	@Override
	public boolean keyUp(int keycode) {

		return true;
	}
	
	private void ChangeNavigation(int x, int y){
		if (Target.enabled)
		{
			Target.setPointOfDestination(new Vector2(
					(x/renderer.ppuX) - renderer.offsetX/renderer.ppuX,
					((height-y)/renderer.ppuY) - renderer.offsetY/renderer.ppuX));
		}
	}
	
	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {

		if (!Gdx.app.getType().equals(ApplicationType.Android))
			return false;
		ChangeNavigation(x,y);
		controller.incFingers();
		return true;
	} 
	
	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		if (!Gdx.app.getType().equals(ApplicationType.Android))
			return false;
		controller.decFingers();
		return true;
	}
	
	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
