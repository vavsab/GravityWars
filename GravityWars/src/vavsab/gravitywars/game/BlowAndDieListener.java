package vavsab.gravitywars.game;

import vavsab.gravitywars.game.model.Ship;

public interface BlowAndDieListener {
	public void shipDied(Ship ship);
	public void bulletDied();
}
