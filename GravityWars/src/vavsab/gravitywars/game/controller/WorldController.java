package vavsab.gravitywars.game.controller;


import java.util.HashMap;
import java.util.Map;

import vavsab.gravitywars.game.model.Body;
import vavsab.gravitywars.game.model.Bullet;
import vavsab.gravitywars.game.model.ControlButton;
import vavsab.gravitywars.game.model.Player;
import vavsab.gravitywars.game.model.Target;
import vavsab.gravitywars.game.model.World;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;



public class WorldController extends ClickListener  {
	enum Keys {
		LEFT, RIGHT, UP, DOWN
	}
	
	World world;
	public Player player;
	public Array<Body> bodies;
	private ControlButton fireButton;
	
	int fingersOnScreen = 0;
	
	@Override
	public void clicked(InputEvent event, float x, float y) {
		if (!Target.enabled) return;
		Body activeBody = Target.activeShip;
		Vector2 power = Target.getPointOfDestination().cpy().sub(activeBody.getCenter());
		Bullet bullet = new Bullet();
		bullet.setName("bullet");
		bullet.setSize(new Vector2(.7f,.7f));
		bullet.setFront(new Vector2(-1, -1));
		bullet.setVelocity(power.cpy());
		bullet.setPosition(activeBody.getCenter().cpy().add(power.cpy().div(power.len()/(activeBody.getSize().x / 2 + 0.7f))));
		bullet.setPlanets(bodies);
		bodies.add(bullet);
		world.lastBullet = bullet;
		Target.enabled = false;
	}
	
	
	public void incFingers()
	{
		fingersOnScreen++;
		fingersStateChanged();
	}
	
	public void decFingers()
	{
		fingersOnScreen--;
		if (fingersOnScreen < 0) fingersOnScreen = 0;
		fingersStateChanged();
	}
	
	private void fingersStateChanged() {
		if (fingersOnScreen == 0)
			fireButton.show();
		else
			fireButton.hide();
	}
	
	static Map<Keys, Boolean> keys = new HashMap<WorldController.Keys, Boolean>();
	static {
		keys.put(Keys.LEFT, false);
		keys.put(Keys.RIGHT, false);
		keys.put(Keys.UP, false);
		keys.put(Keys.DOWN, false);
	};

	public WorldController(World world) {

		this.world = world;
		this.fireButton = world.getButtons().get("fire-button");
		this.bodies = world.getBodies();
	}
	

		
	public void update(float delta) {
		rotateActiveShip();
		for (int i = 0; i < bodies.size; i++)
		{
			Body body = bodies.get(i);
			body.update(delta);
		}
		fireButton.update(delta);
		

	}
	
	private void rotateActiveShip()
	{
		if (Target.getPointOfDestination() == null || Target.activeShip == null) return;
		Vector2 finish = Target.getPointOfDestination();
		finish.sub(Target.activeShip.getCenter());
		Vector2 start = Target.activeShip.getFront();
		//float angle = (float) Math.toDegrees(Math.atan2(finish.x, finish.y));
		float angle = (float) Math.toDegrees(Math.acos((float)start.dot(finish)/(start.len()*finish.len())));
		if (start.crs(finish) < 0) angle = 360 - angle;
		if (Float.isNaN(angle)) angle = 0;
		Target.activeShip.setAngle(angle);
	}
	
}
