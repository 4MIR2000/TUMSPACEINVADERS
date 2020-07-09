package main.java.controller;

import java.util.Iterator;
import java.util.List;

import javafx.geometry.Rectangle2D;
import main.java.model.GameCharacter;
import main.java.model.GameObject;
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

		Rectangle2D shotRec = createRectangle(shot);

		if (character != null) {
			// PlayerCollision

			if (createRectangle(character).intersects(shotRec))
				return true;

			return false;
		} else {
			Iterator<Enemy> iterEnemies = enemies.iterator();

			while (iterEnemies.hasNext()) {
				Enemy enemy = iterEnemies.next();

				if (enemy.isAlive() && createRectangle(enemy).intersects(shotRec)) {
					this.loosingEnemy = enemy;
					return true;
				}
			}
			return false;
		}
	}

	private Rectangle2D createRectangle(GameObject object) {
		int charX = object.getPosition().getX();
		int charY = object.getPosition().getY();

		int charWidth = object.getSize().getWidth();
		int charHeight = object.getSize().getHeight();

		return new Rectangle2D(charX, charY, charWidth, charHeight);
	}

	// sets enemyIsLoser to true or false;
	public void evaluate() {

	}

	public Enemy getLoosingEnemy() {
		return loosingEnemy;
	}

}
