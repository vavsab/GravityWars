package vavsab.gravitywars.game.model;


import android.util.Log;

import com.badlogic.gdx.math.Vector2;

public abstract class Body {
	protected Vector2 velocity;
	protected Vector2 position;
	protected float angle; // in degrees
	protected Vector2 front; // where the angle is zero
	protected float weight;
	protected Vector2 size;
	protected String name;
	protected boolean visible = true;
	protected float collisionDiameter = 0;

	public Body(Vector2 velocity, Vector2 position, float weight, Vector2 size,
			Vector2 front, String name, float angle) {
		this.velocity = velocity;
		this.position = position;
		this.weight = weight;
		this.size = size;
		this.front = front;
		this.name = name;
		this.angle = angle;
	}

	public Body() {
		this.velocity = new Vector2(0, 0);
		this.position = new Vector2(0, 0);
		this.weight = 0f;
		this.size = new Vector2(1, 1);
		this.front = new Vector2(1, 0); // right by default
		this.angle = 0;
		this.name = "NoName";
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public boolean setVelocity(Vector2 velocity) {
		this.velocity = velocity;
		return true;
	}

	public Vector2 getPosition() {
		return position;
	}

	public boolean setPosition(Vector2 position) {
		this.position = position;
		return true;
	}

	public float getWeight() {
		return weight;
	}

	public boolean setWeight(float weight) {
		if (weight < 0) {
			Log.w("WARN", "weight cannot be less than zero");
			return false;
		}
		this.weight = weight;
		return true;
	}

	public float getAngle() {
		return angle;
	}

	public boolean setAngle(float angle) {
		this.angle = angle;
		return true;
	}

	public float getCollisionDiameter() {
		return collisionDiameter;
	}

	public void setCollisionDiameter(float collisionDiameter) {
		this.collisionDiameter = collisionDiameter;
	}

	public boolean isVisibile() {
		return visible;
	}

	public boolean setVisibility(boolean visible) {
		this.visible = visible;
		return true;
	}

	public Vector2 getSize() {
		return size;
	}

	public boolean setSize(Vector2 size) {
		if (size.x < 0 || size.y < 0) {
			Log.w("WARN", "size cannot be less than zero");
			return false;
		}
		this.size = size;
		return true;
	}

	public boolean setFront(Vector2 front) {
		this.front = front;
		return true;
	}

	public Vector2 getFront() {
		return front;
	}

	public boolean setName(String name) {
		this.name = name;
		return true;
	}

	public String getName() {
		return name;
	}

	public Vector2 getCenter() {
		Vector2 res = new Vector2(size);
		return res.div(2).add(position);
	}

	public void update(float delta) {
		position.add(velocity.cpy().mul(delta));
	}

}
