package controller;

import java.util.Iterator;
import java.util.List;

import model.GameCharacter;
import model.Shot;
import model.enemies.Enemy;

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

		if (character != null) {
			// PlayerCollision
			int charX = character.getPosition().getX();
			int charY = character.getPosition().getY();
			if (shotX >= charX && shotX <= (charX + 70)) {
				if (shotY >= (charY + 12) && shotY < (charY + 128)) {
					return true;
				}
			}
			return false;
		} else {
			Iterator<Enemy> iterEnemies = enemies.iterator();

			while (iterEnemies.hasNext()) {
				Enemy enemy = iterEnemies.next();
				int charX = enemy.getPosition().getX();
				int charY = enemy.getPosition().getY();
				
				if (enemy.isAlive() && shotX >= charX && shotX < (charX + 70)) {
					if (shotY >= (charY + 38) && shotY < (charY + 128)) {
						this.loosingEnemy = enemy;
						return true;
					}
				}
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
