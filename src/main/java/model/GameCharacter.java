package main.java.model;

import main.java.view.Dimension;

//in the analysis object model an abstract parent class should be added for Player and Enemy
public abstract class GameCharacter extends GameObject {
	protected int lives = 3;
	private int speed; 
	private String shootingIcon; 
	
	public GameCharacter(int speed,String icon,String shootingIcon, Coordinate position, Dimension size) {
		super(icon,position,size); 
		this.shootingIcon = shootingIcon;
		this.speed = speed; 
		
	}
	

	public abstract void shoot(Shot shot, boolean reset); 
	
	public abstract void reduceLife(); 
	
	public abstract void move(Direction direction); 
	
	
	public String getShootingIcon() {
		return shootingIcon; 
	}
	
	public void setSpeed(int speed) {
		this.speed = speed; 
	}
	
	
	
	public int getSpeed() {
		return speed; 
	}
	
	public boolean isAlive() {
		return lives>0; 
	}
	
	public int getLives() {
		return lives; 
	}
	
	public void setLives(int lives) {
		this.lives = lives; 
	}
}
