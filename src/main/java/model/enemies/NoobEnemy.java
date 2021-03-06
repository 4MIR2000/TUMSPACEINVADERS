package main.java.model.enemies;

import main.java.model.Coordinate;
import main.java.model.Direction;
import main.java.model.Shot;

public class NoobEnemy extends Enemy {
	private static final String NOOBICON = "noobEnemy1.png";
	private static final String NOOBSHOOTINGICON = "noobEnemyS.png"; 
	private static final int NOOBSPEED = 1; 
	private static final int NOOBSHOOTINGRATE = 10; 
	
	public NoobEnemy(Coordinate position) {
		super(NOOBSPEED,NOOBSHOOTINGRATE,NOOBICON,NOOBSHOOTINGICON,position); 
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
