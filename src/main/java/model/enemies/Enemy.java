package model.enemies;

import model.GameCharacter;
import model.Coordinate;

public class Enemy extends GameCharacter {
	
	private int shootingRate; 
	
	public Enemy(int speed, int shootingRate, String icon, Coordinate position) {
		super(speed, icon, position);
		this.shootingRate = shootingRate;
	}
	
	public void setShootingRate(int rate) {
		this.shootingRate = rate; 
	}
	
	public int getShootingRate() {
		return shootingRate;
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
