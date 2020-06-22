package model.enemies;

import model.Coordinate;

public class ProEnemy extends Enemy {
	private static final String PROICON = "proIcon.png";
	
	public ProEnemy(int speed, Coordinate position) {
		super(speed, PROICON, position);
	}	

}
