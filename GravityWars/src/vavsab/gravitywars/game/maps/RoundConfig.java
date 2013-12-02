package vavsab.gravitywars.game.maps;

import java.io.Serializable;

import com.badlogic.gdx.utils.Array;

public class RoundConfig implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public String name;
	public Float bulletWidth;
	public Float bulletHeight;
	public String ico;
	public Integer width;
	public Integer height;
	public Integer mapsAmount;
	public Integer maxPlayers;
	public String path;
	public Float playerBlowWidth;
	public Float playerBlowHeight;
	public Float playerWidth;
	public Float playerHeight;
	public Array<MapConfig> maps = new Array<MapConfig>();
	
	public int players;
}
