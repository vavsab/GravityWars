package vavsab.gravitywars.game;

import vavsab.gravitywars.game.model.Player;
import vavsab.gravitywars.game.model.Ship;
import vavsab.gravitywars.game.model.Target;
import vavsab.gravitywars.game.model.Player.State;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Array;

public class GameManager implements BlowAndDieListener {
	public enum GameState {
		STARTED, SELECT, ENDED
	}

	private static GameManager instance;

	private GameManager() {
		prefs = Gdx.app.getPreferences("gravity-wars");
		
//		prefs.putBoolean("vibrator", true);
//		prefs.flush();
	}

	public static GameManager getInstance() {
		if (instance == null)
			instance = new GameManager();
		return instance;
	}

	// int playersAmount = 0;
	int playersDied = 0;
	Preferences prefs;
	GameState state = GameState.STARTED;
	Player activePlayer = null;
	Array<Player> players = new Array<Player>();

	
	// public void setAmountOfPlayers(int amountOfPlayers) {
	// this.playersAmount = amountOfPlayers;
	// }
	//
	// public int getAmountOfPlayers() {
	// return this.playersAmount;
	// }

	public void setPlayers(Array<Player> players) {
		Target.enabled = true;
		this.players = players;
	}

	public Array<Player> getPlayers() {
		return this.players;
	}

	public void setActivePlayer(Player player) {
		this.activePlayer = player;
	}

	public Player getActivePlayer() {
		return this.activePlayer;
	}

	@Override
	public void shipDied(Ship ship) {
		if (prefs.getBoolean("vibrator")) {
			Gdx.input.vibrate(100);
		}
		Player pl = getPlayerByShip(ship);
		pl.setState(State.DEAD);
		playersDied++;
	}

	@Override
	public void bulletDied() {
		if (prefs.getBoolean("vibrator")) {
			Gdx.input.vibrate(20);
		}
		if (state != GameState.ENDED) {
			if (playersDied == players.size) {
				state = GameState.ENDED;
			} else {
				selectNextActivePlayer();
			}
			Target.activeShip = activePlayer.getShip();
			if (playersDied < players.size - 1)
				Target.enabled = true;
			else
				state = GameState.ENDED;
		}
	}

	private void selectNextActivePlayer() {
		int cur = players.indexOf(activePlayer, true);
		Player nextPlayer = null;
		boolean flag = false;
		if (cur == players.size - 1)
			cur = -1;
		for (int i = cur + 1; i < players.size; i++) {
			if (players.get(i).getState() != State.DEAD) {
				nextPlayer = players.get(i);
				break;
			}
			if (i == players.size - 1) {
				i = -1;
				flag = true;
			}
			if (flag && i == cur) break;
		}
		activePlayer = nextPlayer;
	}

	private Player getPlayerByShip(Ship ship) {
		for (Player pl : players) {
			if (pl.getShip() == ship) {
				return pl;
			}
		}
		return null;
	}
	
	public static void dispose()
	{
		instance = null;
	}
}
