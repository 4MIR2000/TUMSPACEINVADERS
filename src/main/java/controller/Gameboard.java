package controller;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.ImageView;
import model.*;
import model.enemies.Enemy;
import model.enemies.NoobEnemy;
import view.GameboardUI;

public class Gameboard {
	//private Level level;
	
	//change in analysis object model: aggregation relationship to audioPlayer
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
	

	private boolean gameEnded;
	private boolean playerLost; 
	
	public Gameboard() {
		//this.level = level; 
		//this.backgroundImage = image;
		audio = new AudioPlayer(); 
		player = new Player(new Coordinate(GameboardUI.SIZE.getWidth()/2, GameboardUI.SIZE.getHeight()-128)); 
		enemies = new ArrayList<Enemy>(); 
		shots = new ArrayList<Shot>(); 
		createEnemies();
	}
	
	public void startGame() {
		
	}
	
	public void stopGame() {
		
	}
	
	public void playBackgroundMusic() {
		
	}
	
	public void enemyShoot() {
		
	}

	public List<Shot> getShots() {
		return shots; 
	}
	
	public Shot playerShoot() {
		Coordinate shotStartPosition = new Coordinate(player.getPosition().getX()+48,player.getPosition().getY()+2);
		Shot shot = new Shot(Direction.up,shotStartPosition);
		shots.add(shot);
		return shot; 
	}
	
	//take destroyed shots for new shots
	public int reUseShot() {
		int i=0; 
		for(Shot shot:shots) {
			if(shot.isDestroyed()) {
				Coordinate shotStartPosition = new Coordinate(player.getPosition().getX()+48,player.getPosition().getY()+2);
				shot.setPosition(shotStartPosition);
				shot.setDestroyed(false);
				return i;
			}
			i++;
		}
		return -1; 
	}
	public void moveShots() {
		for(Shot shot:shots) {
			if(!shotIsOutOfFrame(shot))
				shot.move();
			else
				shot.setDestroyed(true);
		}
	}
	public void createEnemies() {
		for(int i=0; i<NUMBEROFENEMIES; i++) {
			enemies.add(new NoobEnemy(new Coordinate(ENEMIESSTARTX+i*96+5,ENEMIESSTARTY))); 
		}
		
	}
	
	public void moveEnemies() {
		
		Enemy lastEnemy = enemies.get(enemies.size()-1);
		Enemy firstEnemy = enemies.get(0);
		
		if(lastEnemy.getPosition().getX()+lastEnemy.getSpeed()>=ENEMIESMAXX) {
			//go to left; 
			enemiesGoToRight = false;
		}else if(firstEnemy.getPosition().getX()-firstEnemy.getSpeed()<=ENEMIESMINX) {
			enemiesGoToRight = true; 
		}
		for(Enemy enemy:enemies) {
			if(enemiesGoToRight) {
				enemy.setPosition(new Coordinate(enemy.getPosition().getX()+enemy.getSpeed(), enemy.getPosition().getY()));
			}else {
				enemy.setPosition(new Coordinate(enemy.getPosition().getX()-enemy.getSpeed(), enemy.getPosition().getY()));
			}
		}
	}
	
	public void movePlayer(Direction direction) {
		if(direction.equals(Direction.left)) {
			player.getPosition().setX(player.getPosition().getX()-player.getSpeed());
		}else {
			if(direction.equals(Direction.right)) {
				player.getPosition().setX(player.getPosition().getX()+player.getSpeed());
			}
		}	
	}
	
	public void collisionDetection() {
		for(Shot shot:shots) {
			if(shot.isDestroyed()) return; 
			if(shot.getDirection().equals(Direction.up)) {
				//player shot
				for(Enemy enemy:enemies) {
					Collision collision = new Collision(enemy, shot); 
					boolean hitted = collision.detectCollision(); 
					if(hitted) {
						shot.setDestroyed(true); 						
						enemy.reduceLife(); 
						
						return;
					}
				}
			}else {
				if(shot.getDirection().equals(Direction.down)) {
					//enemy shot
					Collision collision = new Collision(player, shot); 
					boolean hitted = collision.detectCollision(); 
					if(hitted) {
						shot.setDestroyed(true); 						
						player.reduceLife(); 
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
		for(Enemy enemy:enemies) {
			if(enemy.isAlive()) allEnemiesDead = false;
		}
		
		if(!player.isAlive()||allEnemiesDead) {
			gameEnded = true;
			if(!allEnemiesDead) {
				playerLost = true;
			}
			return true; 
		}
		return false;
	}
	
	public boolean shotIsOutOfFrame(Shot shot) {
		if(shot.getPosition().getY()>=GameboardUI.SIZE.getHeight()||
				shot.getPosition().getY()<=ENEMIESSTARTY-50) {
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
	
	public List<Enemy> getEnemies(){
		return enemies; 
	}
}
