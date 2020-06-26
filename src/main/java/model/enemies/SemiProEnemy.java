package model.enemies;

import model.Coordinate;
import model.Direction;
import model.Shot;

public class SemiProEnemy extends Enemy {
	private static final String SEMIPROICON = "semiProEnemy1.png"; 
	private static final String SEMIPROSHOOTINGICON = "semiProEnemyS.png"; 
	private static final int MEDIUMSPEED = 2; 
	private static final int MEDIUMSHOOTINGRATE = 4; 

	public SemiProEnemy(Coordinate position) {
		super(MEDIUMSPEED, MEDIUMSHOOTINGRATE, SEMIPROICON,SEMIPROSHOOTINGICON, position);
	}

	@Override
	public void shoot(Shot shot, boolean reset) {
		if(reset) {
			shot.setPosition(getShotStartPosition());
			shot.setDestroyed(false);
			shot.setDirection(Direction.down);
		}else {
			shot.move();
		}
		
	}
	
}
