package main.java.model.enemies;

import java.util.Random;

import main.java.model.Coordinate;
import main.java.model.Direction;
import main.java.model.Shot;

public class ProEnemy extends Enemy {
	private static final String PROICON = "proEnemy1.png";
	private static final String PROSHOOTINGICON = "proEnemyS.png"; 
	private static final int PROSHOOTINGRATE = 1;
	private static final int PROSPEED = 3;
	private Random rnd; 
	public ProEnemy(Coordinate position) {
		super(PROSPEED, PROSHOOTINGRATE,PROICON,PROSHOOTINGICON,position);
		rnd = new Random(); 
	}	

	@Override
	public void shoot(Shot shot, boolean reset) {
		if(reset) {
			shot.setPosition(getShotStartPosition());
			shot.setDestroyed(false);
			if(rnd.nextInt(2)==0) {
				//bring some randomness :)
				shot.setDirection(Direction.downDiagonal);
			}else {
				shot.setDirection(Direction.down);
			}
			
		}else {
			shot.move();
		}
		
	}
}
