package vavsab.gravitywars.game.model;

import com.badlogic.gdx.math.Vector2;

public class Target {
	public static final float MAX_POWER = 10f;
	private static Vector2 pointOfDestination = new Vector2(0, 0);
	public static Ship activeShip;
	public static boolean enabled = false;

	public static float getPower() {
		Vector2 res = new Vector2(pointOfDestination);
		res.sub(activeShip.getCenter());
		return res.len();
	}

	public static Vector2 getPointOfDestination() {
		if (activeShip == null) return pointOfDestination;
		if (activeShip.lastPointOfDestination != null) {
			pointOfDestination = activeShip.lastPointOfDestination;
		}
		setPointOfDestination(pointOfDestination); // for max power checking
		return pointOfDestination.cpy();
	}

	public static void setPointOfDestination(Vector2 point) {
		if (activeShip == null)
		{
			pointOfDestination = point.cpy();
			return;
		}
		Vector2 power = point.cpy().sub(activeShip.getCenter());
		if (power.len() > MAX_POWER) {
			power.scl(MAX_POWER / power.len());
		}
		pointOfDestination = activeShip.getCenter().cpy().add(power);
		activeShip.lastPointOfDestination = pointOfDestination;
	}
}
