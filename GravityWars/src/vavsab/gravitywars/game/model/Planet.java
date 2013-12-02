package vavsab.gravitywars.game.model;

import com.badlogic.gdx.math.Vector2;

public class Planet extends Body{
	
	public Planet(Vector2 position, float weight, Vector2 size, String name, float angle) {
		this();
		this.position = position;
		this.weight = weight;
		this.size = size;
		this.name = name;
		this.angle = angle;
	}

	public Planet() {
		super();
		
	}
	
	public void update(float delta)
	{
		if (velocity.angle() > 180) delta *= -1;
		this.angle = (this.angle + velocity.len()*delta);
		this.angle = this.angle - 360*((int)(this.angle / 360f));
	}
	
}
