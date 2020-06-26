package model.enemies;

import model.GameCharacter;
import model.Shot;
import model.Coordinate;
import model.Direction;

public abstract class Enemy extends GameCharacter {
	
	private int shootingRate; 
	
	public Enemy(int speed, int shootingRate, String icon, String shootingIcon,Coordinate position) {
		super(speed, icon, shootingIcon,position);
		this.shootingRate = shootingRate;
	}
	
	public void setShootingRate(int rate) {
		this.shootingRate = rate; 
	}
	
	public int getShootingRate() {
		return shootingRate;
	}
	

	public Coordinate getShotStartPosition() {
		return new Coordinate(getPosition().getX() + 50,
				getPosition().getY() + 100);
	}
	@Override
	public void reduceLife() {
		
		lives--;
	}

	@Override
	public void move(Direction direction) {
		if (direction.equals(Direction.right)) {
			setPosition(
					new Coordinate(getPosition().getX() + getSpeed(), getPosition().getY()));
		} else {
			setPosition(
					new Coordinate(getPosition().getX() - getSpeed(), getPosition().getY()));
		}
		
	}
	
}
