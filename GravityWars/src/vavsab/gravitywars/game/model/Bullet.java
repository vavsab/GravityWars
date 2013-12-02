package vavsab.gravitywars.game.model;



import vavsab.gravitywars.game.BlowAndDieListener;
import vavsab.gravitywars.game.GameManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Bullet extends Body implements Blowable {
	public final float GRAVITY = 1000f;
	public final int BLOW_FRAMES = 9;
	public static final int LIFETIME = 5; // lifetime in seconds
	public final Vector2 BLOW_SIZE = new Vector2(6, 6);
	public final float DYING_TIME = 0.5f;
	int cur_frame = -1;
	float timeOfLife = 0;
	
	Array<Body> planets;
	
	
	public int getCurDyingFrame()
	{
		return cur_frame;
	}
	
	public boolean isDead()
	{
		if (cur_frame == BLOW_FRAMES) return true;
		return false;
	}
	
	public boolean isDying()
	{
		if (cur_frame > -1) return true;
		return false;
	}
	
	public void kill()
	{
		if (isDying()) return;
		position.add(size.cpy().sub(BLOW_SIZE).div(2f));
		//position.sub(BLOW_SIZE.cpy().div(2f));
		cur_frame = 0;
		BlowAndDieListener blow = GameManager.getInstance();
		blow.bulletDied();
	}

	public Bullet(Vector2 position, Vector2 size, Array<Body> planets) {
		this();
		this.position = position;
		this.weight = 0f;
		this.size = size;
		this.name = "";
		this.angle = 0f;
		this.planets = planets;
	}

	public Bullet() {
		super();
	}

	public void setPlanets(Array<Body> planets) {
		this.planets = planets;
	}
	
	public Array<Body> getPlanets() {
		return this.planets;
	}

	float timeElapsed = 0f;
	
	@Override
	public void update(float delta) {
		//delta = .5f;
		timeOfLife += delta;
		if (isDead()) return;
		if (timeOfLife > LIFETIME && !isDying()) kill();
		if (cur_frame > -1)
		{
			setSize(BLOW_SIZE);
			timeElapsed += delta;
			while (timeElapsed > (DYING_TIME / BLOW_FRAMES))
			{
				cur_frame++;
				timeElapsed -= (DYING_TIME / BLOW_FRAMES);
			}
			return;
		}
		
		Vector2 acceleration = getAcceleration();
		Vector2 a_t_2_div_2 = acceleration.cpy().mul(delta*delta / 2f);
		Vector2 speed_t = velocity.cpy().mul(delta);
		position.add( speed_t ).add( a_t_2_div_2 );
		
		Vector2 a_t = acceleration.cpy().mul(delta);
		velocity.add( a_t );
		angle = velocity.cpy().angle() - front.angle();
		
		if (collisionDetected()) kill();
	}

	private Vector2 getAcceleration() {
		Vector2 res = new Vector2();
		for (Body cur : planets) {
			Vector2 length = cur.getCenter().cpy().sub(this.getCenter());
			length.mul(cur.weight).mul(1f/length.len2());
			//Vector2 adder = new Vector2(cur.getWeight()*length.x/Math.abs(length.x*length.x*length.x), cur.getWeight()*length.y/Math.abs(length.y*length.y*length.y));
			if (Float.isNaN(length.x)) length.x = 0;
			if (Float.isNaN(length.y)) length.y = 0;
			res.add(length);
		}
		res.mul(GRAVITY);
		return res;
	}
	
	private boolean collisionDetected() {
		Vector2 center = getCenter();
		for (Body cur : planets) {
			if (cur == this) break;
			Vector2 planetCenter = cur.getCenter();
			if (planetCenter.cpy().sub(center).len() < getCollisionDiameter()/2f + cur.getCollisionDiameter()/2f)
			{
				if (cur instanceof Ship)
				{
					((Ship) cur).kill();
				}
				return true;
			}
		}
		return false;
	}
}
