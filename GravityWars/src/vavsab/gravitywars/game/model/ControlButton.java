package vavsab.gravitywars.game.model;

import com.badlogic.gdx.math.Vector2;

public class ControlButton extends Body {
	
	final int HIDING_SPEED = 10; 
	Vector2 hidePos;
	Vector2 visiblePos;
	boolean hidden;

	public ControlButton(Vector2 size, String name, Vector2 visiblePos,
			Vector2 hidePos, boolean hidden) {
		this();
		this.weight = 0f;
		this.size = size;
		this.name = name;
		this.angle = 0f;
		this.hidePos = hidePos;
		this.hidden = hidden;
	}

	public boolean setVisiblePosition(Vector2 visiblePosition) {
		visiblePos = visiblePosition;
		return true;
	}

	public Vector2 getVisiblePosition() {
		return visiblePos;
	}
	
	public void hide() {
		hidden = true;
	}

	public void show() {
		hidden = false;
	}

	public boolean isHidden() {
		return hidden;
	}

	public boolean setHidePosition(Vector2 hidePosition) {
		hidePos = hidePosition;
		return true;
	}

	public Vector2 getHidePosition() {
		return hidePos;
	}

	public ControlButton() {
		super();
		visible = false;
		hidePos = new Vector2(0, 0);
		visiblePos = new Vector2(0, 0);
	}

	@Override
	public void update(float delta) {
//		if (!isVisibile())
//		{
//			position = hidePos.cpy();
//			return;
//		}
		Vector2 newPos = hidden ? hidePos : visiblePos;
		if (Math.abs(newPos.x - position.x) > .5f || Math.abs(newPos.y - position.y) > .5f) {
			velocity = position.cpy().sub(newPos);
		}
		position.add(newPos.cpy().sub(position).mul(HIDING_SPEED*delta));
	}

}
