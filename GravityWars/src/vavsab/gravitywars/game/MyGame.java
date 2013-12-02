package vavsab.gravitywars.game;
import vavsab.gravitywars.game.maps.RoundConfig;
import vavsab.gravitywars.game.screens.GameScreen;
import android.content.Context;

import com.badlogic.gdx.Game;
public class MyGame extends   Game  {
	//public GameScreen game;
	private Context context;
	private RoundConfig roundConfig;
	public GameScreen game;
	
	public MyGame(Context context, RoundConfig roundConfig)
	{
		this.roundConfig = roundConfig;
		this.context = context;
	}
	@Override
	public void create() {
		// TODO Auto-generated method stub
		
		game = new GameScreen(context, roundConfig);
		setScreen(game);
	}
	
	@Override
	public void resize(int width, int height) {
		
	}
/*
	@Override
	public void render() {

	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}
*/
	@Override
	public void dispose() {
		GameManager.dispose();
		super.dispose();
	}
	
	
}
