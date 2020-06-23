package model.enemies;

import model.Coordinate;

public class SemiProEnemy extends Enemy {
	private static final String SEMIPROICON = "semiProEnemy1.png"; 
	private static final int MEDIUMSPEED = 2; 
	private static final int MEDIUMSHOOTINGRATE = 4; 

	public SemiProEnemy(Coordinate position) {
		super(MEDIUMSPEED, MEDIUMSHOOTINGRATE, SEMIPROICON, position);
	}

	
}
