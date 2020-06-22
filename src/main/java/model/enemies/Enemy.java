package model.enemies;

import model.GameCharacter;
import model.Coordinate;

public class Enemy extends GameCharacter {
	

	public Enemy(int speed, String icon, Coordinate position) {
		super(speed, icon, position);
	}
	
	private double shootingRate; 
	
	public void setShootingRate(double rate) {
		this.shootingRate = rate; 
	}
	
	@Override
	public void shoot() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reduceLife() {
		
		lives--;
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}
	
}
