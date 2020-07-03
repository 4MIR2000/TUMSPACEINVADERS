package main.java.controller;

import java.awt.Rectangle;
import java.util.Iterator;
import java.util.List;

import javafx.geometry.Rectangle2D;
import main.java.model.GameCharacter;
import main.java.model.Shot;
import main.java.model.enemies.Enemy;

public class Collision {
	private boolean enemyIsLoser;
	private GameCharacter character;
	private Shot shot;
	private List<Enemy> enemies;
	private Enemy loosingEnemy;

	public Collision(GameCharacter character, Shot shot) {
		this.character = character;
		this.shot = shot;
	}

	public Collision(List<Enemy> enemies, Shot shot) {
		this.enemies = enemies;
		this.shot = shot;
	}

	public boolean detectCollision() {
		int shotX = shot.getPosition().getX();
		int shotY = shot.getPosition().getY();
		
		int shotWidth = shot.getSize().getWidth(); 
		int shotHeight = shot.getSize().getHeight();
		
		Rectangle2D shotRec = new Rectangle2D(shotX,shotY,shotWidth,shotHeight);
		if (character != null) {
			// PlayerCollision			
			int charX = character.getPosition().getX();
			int charY = character.getPosition().getY();
			
			int charWidth = character.getSize().getWidth(); 
			int charHeight = character.getSize().getHeight();
			
			Rectangle2D charRec = new Rectangle2D(charX,charY,charWidth,charHeight);
			
			if(charRec.intersects(shotRec))
				return true;
			/*if (shotX >= charX && shotX <= (charX + 70)) {
				if (shotY >= (charY + 12) && shotY < (charY + 128)) {
					return true;
				}
			}*/
			return false;
		} else {
			Iterator<Enemy> iterEnemies = enemies.iterator();

			while (iterEnemies.hasNext()) {
				Enemy enemy = iterEnemies.next();
				int charX = enemy.getPosition().getX();
				int charY = enemy.getPosition().getY();
				
				int charWidth = enemy.getSize().getWidth(); 
				int charHeight = enemy.getSize().getHeight();
				
				Rectangle2D charRec = new Rectangle2D(charX,charY,charWidth,charHeight);
				
				if(charRec.intersects(shotRec)) {
					this.loosingEnemy = enemy;
					return true;
				}
					
				/*if (enemy.isAlive() && shotX >= charX && shotX < (charX + 70)) {
					if (shotY >= (charY + 38) && shotY < (charY + 128)) {
						this.loosingEnemy = enemy;
						return true;
					}
				}*/
			}
			return false;
		}
	}

	// sets enemyIsLoser to true or false;
	public void evaluate() {

	}

	public Enemy getLoosingEnemy() {
		return loosingEnemy;
	}

}
