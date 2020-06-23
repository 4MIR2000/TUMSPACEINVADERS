package model.enemies;

import model.Coordinate;

public class NoobEnemy extends Enemy {
	private static final String NOOBICON = "noobEnemy1.png";
	private static final int NOOBSPEED = 1; 
	private static final int NOOBSHOOTINGRATE = 10; 
	
	public NoobEnemy(Coordinate position) {
		super(NOOBSPEED,NOOBSHOOTINGRATE,NOOBICON,position); 
	}

	
}
