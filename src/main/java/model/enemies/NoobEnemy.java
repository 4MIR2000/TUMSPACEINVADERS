package model.enemies;

import model.Coordinate;

public class NoobEnemy extends Enemy {
	private static final String NOOBICON = "enemy1.png";
	public NoobEnemy(Coordinate position) {
		super(1,NOOBICON,position); 
	}

	
}
