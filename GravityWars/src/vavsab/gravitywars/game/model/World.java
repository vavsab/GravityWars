package vavsab.gravitywars.game.model;


import java.util.HashMap;

import vavsab.gravitywars.game.maps.MapConfig;
import vavsab.gravitywars.game.maps.PlanetConfig;
import vavsab.gravitywars.game.maps.RoundConfig;
import vavsab.gravitywars.game.maps.SpawnPoint;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class World {
	final String [] shipNames = {"default", "red", "blue", "purple", "yellow", "orange", "azure",  "light-green"}; 
	Array<Body> bodies = new Array<Body>();
	HashMap<String, ControlButton> controlButtons = new HashMap<String, ControlButton>();
	String status = "";
	
	public Bullet lastBullet;
	public int width;
	public int height;
	
	public HashMap<String, ControlButton>  getButtons() {
		return controlButtons;
	}

	
	
	public Array<Body> getBodies() {
		return bodies;
	}
	
	public World(RoundConfig cfg, int round) {
		
		width = 32;
		height = 24;
		createWorld(cfg, round);
		
	}
	
	public void setStatus(String status)
	{
		this.status= status;
	}
	
	public void addStatus(String status)
	{
		this.status += status;
	}
	
	public String getStatus()
	{
		return status;
	}
	
	public void createWorld(RoundConfig cfg, int round) {
		MapConfig map = cfg.maps.get(round);
		for (PlanetConfig cur : map.planets) {
			Planet body;
			body = new Planet();
			body.setName(cur.type);
			body.setSize(new Vector2(cur.width, cur.height));
			body.setCollisionDiameter(cur.width);
			body.setWeight(cur.weight);
			body.setAngle(cur.angle);
			body.setVelocity(new Vector2(6,6));
			body.setPosition(new Vector2(cur.x, cur.y));
			bodies.add(body);
		} 
		
		for (int i = 0; i < cfg.players; i++) {
			SpawnPoint cur = map.spawns.get(i);
			Ship body;
			body = new Ship();
			body.setName(shipNames[i]);
			body.setSize(new Vector2(cfg.playerWidth, cfg.playerHeight));
			body.setCollisionDiameter(cfg.playerWidth);
			body.setFront(new Vector2(0, 100));
			body.setAngle(cur.angle);
			body.setPosition(new Vector2(cur.x, cur.y));
			bodies.add(body);
		}
		Target.activeShip = (Ship)bodies.get(bodies.size - cfg.players);
		
		ControlButton butt = new ControlButton();
		butt.setName("fire-button");
		butt.setSize(new Vector2(7f,7f));
		butt.setHidePosition(new Vector2(28f, -3f));
		butt.setPosition(new Vector2(30f, -3f));
		butt.setVisiblePosition(new Vector2(25f,0.5f));
		controlButtons.put(butt.getName(), butt);
	}
}
