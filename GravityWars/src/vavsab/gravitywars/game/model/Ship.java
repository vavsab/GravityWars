package vavsab.gravitywars.game.model;

import vavsab.gravitywars.game.BlowAndDieListener;
import vavsab.gravitywars.game.GameManager;

import com.badlogic.gdx.math.Vector2;

public class Ship extends Body implements Blowable {
	public final int BLOW_FRAMES = 9;
	public final Vector2 BLOW_SIZE = new Vector2(8, 8);
	public final float DYING_TIME = .7f;
	public Vector2 lastPointOfDestination;
	int cur_frame = -1;
	
	public Ship(Vector2 position, float weight, Vector2 size, String name, float angle) {
		this();
		this.position = position;
		this.weight = weight;
		this.size = size;
		this.name = name;
		this.angle = angle;
	}

	public Ship() {
		super();
		
	}

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
		position.add(size.cpy().div(2f).sub(BLOW_SIZE.cpy().div(2f)));
		cur_frame = 0;
		BlowAndDieListener blow = GameManager.getInstance();
		blow.shipDied(this);
	}
	
	float timeElapsed = 0f;
	
	public void update(float delta) {
		if (isDead()) return;
		if (cur_frame > -1)
		{
			angle = 0;
			setSize(BLOW_SIZE);
			timeElapsed += delta;
			while (timeElapsed > (DYING_TIME / BLOW_FRAMES))
			{
				cur_frame++;
				timeElapsed -= (DYING_TIME / BLOW_FRAMES);
			}
			return;
		}
		super.update(delta);
	}

}
