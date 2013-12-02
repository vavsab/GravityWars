package vavsab.gravitywars.game.model;


public interface Blowable {
	public int getCurDyingFrame();
	public boolean isDead();	
	public boolean isDying();
	public void kill();
}
