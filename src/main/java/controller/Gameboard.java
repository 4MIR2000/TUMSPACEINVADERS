package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.*;
import model.enemies.Enemy;
import model.enemies.NoobEnemy;
import model.enemies.ProEnemy;
import model.enemies.SemiProEnemy;
import view.GameboardUI;
import view.Options;

public class Gameboard {
	// private Level level;

	// change in analysis object model: aggregation relationship to audioPlayer
	private AudioPlayer audio;
	private Player player;
	private List<Enemy> enemies;
	private List<Shot> shots;

	private final int NUMBEROFENEMIES = 3;

	private final int ENEMIESSTARTX = 130;
	private final int ENEMIESSTARTY = 250;

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
		shots = new ArrayList<Shot>();
		startGame();
	}

	public boolean gameIsClosed() {
		return gameClosed; 
	}
	public void startGame() {
		player = new Player(new Coordinate(GameboardUI.SIZE.getWidth() / 2, GameboardUI.SIZE.getHeight() - 128));
		//number sets shootingRate Random maxBound
		//lower = faster || number != 0
		createEnemies();
		audio.playBackgroundMusic();
	}

	public void stopGame() {
		gameClosed = true; 
		audio.stopBackgroundMusic();
	}

	public void playBackgroundMusic() {

	}

	//the first element of the integer array is the shot index in the list shots and the second is the enemy index
	//(from which enemy the shot comes: is used for animation later on gameboard)
	public List<int[]> enemyShoot() {
		List<int[]> s = new ArrayList<int[]>();
		for (int i=0; i<enemies.size(); i++) {
			if (enemies.get(i).isAlive() && ((enemyShootTimer % enemies.get(i).getShootingRate()) == 0)) {
				Coordinate shotStartPosition = new Coordinate(enemies.get(i).getPosition().getX() + 50,
						enemies.get(i).getPosition().getY() + 100);
				int indexOfShot = reUseShot(shotStartPosition, Direction.down);
				Shot shot; 
				if(indexOfShot==-1) {
					shot = new Shot(Direction.down, shotStartPosition);
					shots.add(shot);
					s.add(new int[]{shots.size()-1,i});
				}else {
					s.add(new int[] {indexOfShot,i});
				}
				
				audio.playShotSound();
				
			}
		}
		enemyShootTimer++;
		return s;
	}

	public List<Shot> getShots() {
		return shots;
	}

	public Shot playerShoot() {
		Coordinate shotStartPosition = new Coordinate(player.getPosition().getX() + 48,
				player.getPosition().getY() + 2);
		Shot shot = new Shot(Direction.up, shotStartPosition);
		audio.playShotSound();
		shots.add(shot);
		return shot;
	}

	// take destroyed shots for new shots
	public int reUseShot(Coordinate shotStartPosition, Direction newDirection) {
		int i = 0;
		for (Shot shot : shots) {
			//player reuses shot
			if (shot.isDestroyed()) {
				
				shot.setPosition(shotStartPosition);
				shot.setDestroyed(false);
				shot.setDirection(newDirection);
				audio.playShotSound();
				return i;
			}
			i++;
		}
		return -1;
	}

	public void moveShots() {
		for (Shot shot : shots) {
			if (!shotIsOutOfFrame(shot))
				shot.move();
			else
				shot.setDestroyed(true);
		}
	}

	public void createHearts() {
		hearts = new ArrayList<Heart>(); 
		int gapSize = 5; 
		IntStream.range(1,4).forEach(i->{
			int x = (int) (GameboardUI.SIZE.getWidth()-i*(Heart.SIZE.getWidth()+gapSize));
			int y = 0; 
			Coordinate position = new Coordinate(x,y); 
			Heart heart = new Heart(position); 
			hearts.add(heart);
		});
	}
	public void createEnemies() {
		//randomize a little bit ;)
		Random r = new Random();
		for (int i = 0; i < NUMBEROFENEMIES; i++) {
			Enemy newEnemy; 
			switch(Options.getChoosenDiff()) {
			
			case easy: 
				newEnemy =  new NoobEnemy(new Coordinate(ENEMIESSTARTX + i * 96 + 5, ENEMIESSTARTY));
				break;
			case medium: 
				newEnemy =  new SemiProEnemy(new Coordinate(ENEMIESSTARTX + i * 96 + 5, ENEMIESSTARTY));
				break;
			case hard: 
				newEnemy =  new ProEnemy(new Coordinate(ENEMIESSTARTX + i * 96 + 5, ENEMIESSTARTY));
				break;
			default: newEnemy =  new NoobEnemy(new Coordinate(ENEMIESSTARTX + i * 96 + 5, ENEMIESSTARTY));
			}
			
			int rate = r.nextInt(newEnemy.getShootingRate())+2;
			newEnemy.setShootingRate(rate);
			enemies.add(newEnemy);
		}
	}

	public void moveEnemies() {
		if (enemies.size() > 0) {
			Enemy lastEnemy = enemies.get(enemies.size() - 1);
			Enemy firstEnemy = enemies.get(0);

			if (lastEnemy.getPosition().getX() + lastEnemy.getSpeed() >= ENEMIESMAXX) {
				// go to left;
				enemiesGoToRight = false;
			} else if (firstEnemy.getPosition().getX() - firstEnemy.getSpeed() <= ENEMIESMINX) {
				enemiesGoToRight = true;
			}
			for (Enemy enemy : enemies) {
				if (enemiesGoToRight) {
					enemy.setPosition(
							new Coordinate(enemy.getPosition().getX() + enemy.getSpeed(), enemy.getPosition().getY()));
				} else {
					enemy.setPosition(
							new Coordinate(enemy.getPosition().getX() - enemy.getSpeed(), enemy.getPosition().getY()));
				}
			}
		}
	}

	public void movePlayer(Direction direction) {
		if (direction.equals(Direction.left)) {
			player.getPosition().setX(player.getPosition().getX() - player.getSpeed());
		} else {
			if (direction.equals(Direction.right)) {
				player.getPosition().setX(player.getPosition().getX() + player.getSpeed());
			}
		}
	}

	public void collisionDetection() {

		for (Shot shot : shots) {
			if (shot.isDestroyed() == false) {
				if (shot.getDirection().equals(Direction.up)) {
					// player shot
					if (enemies.isEmpty() == false) {
						Collision collision = new Collision(enemies, shot);
						if (collision.detectCollision()) {
							Enemy enemy = collision.getLoosingEnemy();
							enemy.reduceLife();
							audio.playEnemyHurtSound();
							if(!enemy.isAlive())
								audio.playEnemyDeadSound();
							shot.setDestroyed(true);
						}
					}
				} else {
					if (shot.getDirection().equals(Direction.down)) {
						// enemy shot
						Collision collision = new Collision(player, shot);
						boolean hitted = collision.detectCollision();
						if (hitted) {
							shot.setDestroyed(true);
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
				|| shot.getPosition().getY() <= ENEMIESSTARTY - 250) {
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
	
	public List<Heart> getHearts(){
		return hearts; 
	}
}
