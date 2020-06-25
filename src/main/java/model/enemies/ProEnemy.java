package model.enemies;

import model.Coordinate;

public class ProEnemy extends Enemy {
	private static final String PROICON = "proEnemy1.png";
	private static final String PROSHOOTINGICON = "proEnemyS.png"; 
	private static final int PROSHOOTINGRATE = 1;
	private static final int PROSPEED = 3;
	
	public ProEnemy(Coordinate position) {
		super(PROSPEED, PROSHOOTINGRATE,PROICON,PROSHOOTINGICON,position);
	}	

}
