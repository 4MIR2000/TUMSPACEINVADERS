package main.java.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import main.java.model.*;
import main.java.model.enemies.Enemy;
import main.java.model.enemies.NoobEnemy;
import main.java.model.enemies.ProEnemy;
import main.java.model.enemies.SemiProEnemy;
import main.java.view.GameboardUI;
import main.java.view.Options;

public class Gameboard {
	// private Level level;

	// change in analysis object model: aggregation relationship to audioPlayer
	private AudioPlayer audio;
	private Player player;
	private List<Enemy> enemies;
	// value is the gameCharacter who created the shot
	private List<Pair<Shot, GameCharacter>> shots;

	private final int NUMBEROFENEMIES = 3;

	private final int ENEMIESSTARTX = 130;
	private final int ENEMIESSTARTY = 280;

	private final int ENEMIESMAXX = 500;
	private final int ENEMIESMINX = 5;

	private boolean enemiesGoToRight = true;
	private int enemyShootTimer = 0;

	private boolean gameClosed;
	private boolean playerLost;

	private List<Heart> hearts;

	public Gameboard() {
		// this.level = level;
		// this.backgroundImage = image;
		audio = new AudioPlayer();
		enemies = new ArrayList<Enemy>();
		shots = new ArrayList<Pair<Shot, GameCharacter>>();
		startGame();
	}

	public boolean gameIsClosed() {
		return gameClosed;
	}

	public void startGame() {
		player = new Player(new Coordinate(GameboardUI.SIZE.getWidth() / 2, GameboardUI.SIZE.getHeight() - 96));
		// number sets shootingRate Random maxBound
		// lower = faster || number != 0
		createEnemies();
		audio.playBackgroundMusic();
	}

	public void stopGame() {
		gameClosed = true;
		audio.stopBackgroundMusic();
	}

	public void playBackgroundMusic() {

	}

	// returns listof indeces of the new shots in the shots map
	public List<Integer> enemyShoot() {
		List<Integer> s = new ArrayList<Integer>();
		for (int i = 0; i < enemies.size(); i++) {
			if (enemies.get(i).isAlive() && ((enemyShootTimer % enemies.get(i).getShootingRate()) == 0)) {

				int indexOfShot = reUseShot();
				Shot shot;
				if (indexOfShot == -1) {
					shot = new Shot();
					enemies.get(i).shoot(shot, true);
					shots.add(new Pair(shot, enemies.get(i)));
					s.add(shots.size() - 1);
					audio.playShotSound();
				} else {
					Pair<Shot, GameCharacter> reusedShot = shots.get(indexOfShot);
					// change the enemy shooting the shot
					reusedShot.setValue(enemies.get(i));
					enemies.get(i).shoot(reusedShot.getKey(), true);
					s.add(indexOfShot);

				}
			}
		}
		enemyShootTimer++;
		return s;
	}

	public List<Pair<Shot, GameCharacter>> getShots() {
		return shots;
	}

	public Shot playerShoot() {
		Shot shot = new Shot();
		player.shoot(shot, true);
		audio.playShotSound();
		shots.add(new Pair(shot, player));
		return shot;
	}

	// take destroyed shots for new shots
	public int reUseShot() {
		int i = 0;
		for (Pair<Shot, GameCharacter> shot : shots) {
			// player reuses shot
			if (shot.getKey().isDestroyed()) {
				audio.playShotSound();
				return i;
			}
			i++;
		}
		return -1;
	}

	public void moveShots() {
		for (Pair<Shot, GameCharacter> shot : shots) {
			if (!shotIsOutOfFrame(shot.getKey()))
				shot.getValue().shoot(shot.getKey(), false);
			else
				shot.getKey().setDestroyed(true);
		}
	}

	public void addShot(Pair<Shot, GameCharacter> shot) {
		// important: make sure gameboardUI.shots also contains the shot
		shots.add(shot);
	}

	public void createHearts() {
		hearts = new ArrayList<Heart>();
		int gapSize = 5;
		IntStream.range(1, 4).forEach(i -> {
			Heart heart = new Heart();
			int x = (int) (GameboardUI.SIZE.getWidth() - i * (heart.getSize().getWidth() + gapSize));
			int y = 0;
			Coordinate position = new Coordinate(x, y);
			heart.setPosition(position);
			hearts.add(heart);
		});
	}

	public void createEnemies() {
		// randomize a little bit ;)
		Random r = new Random();
		for (int i = 0; i < NUMBEROFENEMIES; i++) {
			Enemy newEnemy;
			switch (Options.getChoosenDiff()) {

			case easy:
				newEnemy = new NoobEnemy(new Coordinate(ENEMIESSTARTX + i * 96 + 5, ENEMIESSTARTY));
				break;
			case medium:
				newEnemy = new SemiProEnemy(new Coordinate(ENEMIESSTARTX + i * 96 + 5, ENEMIESSTARTY));
				break;
			case hard:
				newEnemy = new ProEnemy(new Coordinate(ENEMIESSTARTX + i * 96 + 5, ENEMIESSTARTY));
				break;
			default:
				newEnemy = new NoobEnemy(new Coordinate(ENEMIESSTARTX + i * 96 + 5, ENEMIESSTARTY));
			}

			int rate = r.nextInt(newEnemy.getShootingRate()) + 2;
			newEnemy.setShootingRate(rate);
			enemies.add(newEnemy);
		}
	}

	public void moveEnemies() {
		if (enemies.size() > 0) {
			Enemy lastEnemy = getLastEnemy();
			Enemy firstEnemy = getFirstEnemy();

			if (lastEnemy == null || firstEnemy == null)
				return;

			if (lastEnemy.getPosition().getX() + lastEnemy.getSpeed() >= ENEMIESMAXX) {
				// go to left;
				enemiesGoToRight = false;
			} else if (firstEnemy.getPosition().getX() - firstEnemy.getSpeed() <= ENEMIESMINX) {
				enemiesGoToRight = true;
			}
			for (Enemy enemy : enemies) {
				if (enemy.isAlive()) {
					if (enemiesGoToRight) {
						enemy.move(Direction.right);
					} else {
						enemy.move(Direction.left);
					}
				}
			}
		}
	}

	public Enemy getFirstEnemy() {
		for (Enemy enemy : enemies) {
			if (enemy.isAlive())
				return enemy;
		}
		return null;
	}

	// iterate backwards
	public Enemy getLastEnemy() {
		for (int i = enemies.size() - 1; i >= 0; i--) {
			if (enemies.get(i).isAlive())
				return enemies.get(i);
		}
		return null;
	}

	public void collisionDetection() {

		for (Pair<Shot, GameCharacter> shot : shots) {
			if (shot.getKey().isDestroyed() == false) {
				if (shot.getKey().getDirection().equals(Direction.up)) {
					// player shot
					if (enemies.isEmpty() == false) {
						Collision collision = new Collision(enemies, shot.getKey());
						if (collision.detectCollision()) {
							Enemy enemy = collision.getLoosingEnemy();
							enemy.reduceLife();
							audio.playEnemyHurtSound();
							if (!enemy.isAlive()) {
								audio.playEnemyDeadSound();
							}
							shot.getKey().setDestroyed(true);
						}
					}
				} else {
					if (shot.getKey().getDirection().equals(Direction.down)
							|| shot.getKey().getDirection().equals(Direction.downDiagonal)) {
						// enemy shot
						Collision collision = new Collision(player, shot.getKey());
						boolean hitted = collision.detectCollision();
						if (hitted) {
							shot.getKey().setDestroyed(true);
							player.reduceLife();
							audio.playPlayerHurtSound();
						}
					}
				}
			}
		}
	}

	public boolean playerHasLost() {
		return playerLost;
	}

	public boolean checkIfGameEnded() {
		boolean allEnemiesDead = true;
		for (Enemy enemy : enemies) {
			if (enemy.isAlive())
				allEnemiesDead = false;
		}

		if (!player.isAlive() || allEnemiesDead) {

			if (!allEnemiesDead) {
				playerLost = true;
				audio.playGameOverSound();
			} else {
				audio.playWinSound();
			}
			return true;
		}
		return false;
	}

	public boolean shotIsOutOfFrame(Shot shot) {
		if (shot.getPosition().getY() >= GameboardUI.SIZE.getHeight()
				|| shot.getPosition().getY() <= ENEMIESSTARTY - 20) {
			return true;
		}
		return false;
	}

	public void setShotSound(String sound) {
		this.audio.setShotSound(sound);
	}

	public Player getPlayer() {
		return player;
	}

	public List<Enemy> getEnemies() {
		return enemies;
	}

	public List<Heart> getHearts() {
		return hearts;
	}
}
