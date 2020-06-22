package controller;

import model.GameCharacter;
import model.Shot;

public class Collision {
	private boolean enemyIsLoser; 
	private GameCharacter character; 
	private Shot shot; 
	
	public Collision(GameCharacter character, Shot shot) {
		this.character = character; 
		this.shot = shot; 
	}
	
	public boolean detectCollision() {
		int shotX = shot.getPosition().getX();
		int shotY = shot.getPosition().getY();
		int charX = character.getPosition().getX(); 
	    int charY = character.getPosition().getY(); 
	    
		if(shotX>=charX&&shotX<=charX+96) {
			if(shotY>=charY-128&&shotY<=charY) {
				return true; 
			}
		}
		return false; 
	}
	
	//sets enemyIsLoser to true or false;
	public void evaluate() {
		
	}
	
}
