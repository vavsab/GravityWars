package vavsab.gravitywars.game.model;





public class Player {

	public enum State {
		ALIVE, ACTIVE, DEAD
	}
	
	Ship ship;
	String name = "unnamed";
	State state = State.ALIVE;
	int scores = 0;
	
	
	public void setScores(int newScores) {
		this.scores = newScores;
	}
	
	public int getScores() {
		return this.scores;
	}
	
	public void addScores(int scoresToAdd) {
		this.scores += scoresToAdd;
	}
	
	public void subScores(int scoresToSubtract) {
		this.scores -= scoresToSubtract;
	}

	public Player(String name, Ship ship) {
		this.name = name;
		this.ship = ship;
	}
	
	public void setShip(Ship ship) {
		this.ship = ship;
	}
	
	public Ship getShip() {
		return this.ship;
	}
	
	
	public void setState(State state) {
		this.state = state;
	}
	
	public State getState() {
		return this.state;
	}
	
}