package vavsab.gravitywars.game.maps;

import com.badlogic.gdx.utils.Array;

public class MapConfig {
	public String name;
	public String bg;
	public Array<SpawnPoint> spawns = new Array<SpawnPoint>();
	public Array<PlanetConfig> planets = new Array<PlanetConfig>();
}
