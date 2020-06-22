package model;

//in the analysis object model an abstract parent class should be added for Player and Enemy
public abstract class GameCharacter {
	protected int lives = 3;
	private int speed; 
	private String icon; 
	private Coordinate position; 
	
	public GameCharacter(int speed,String icon, Coordinate position) {
		this.icon = icon; 
		this.speed = speed; 
		this.position = position; 
	}
	
	public abstract void shoot(); 
	
	public abstract void reduceLife(); 
	
	public abstract void move(); 
	
	public void setIcon(String url) {
		this.icon = url; 
	}
	
	public void setSpeed(int speed) {
		this.speed = speed; 
	}
	
	public void setPosition(Coordinate position) {
		this.position = position; 
	}
	
	public String getIcon() {
		return icon; 
	}
	
	public Coordinate getPosition() {
		return position; 
	}
	
	public int getSpeed() {
		return speed; 
	}
	
	public boolean isAlive() {
		return lives>0; 
	}
}
