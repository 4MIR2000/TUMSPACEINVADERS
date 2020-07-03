package main.java.model;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.List;

import org.easymock.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import main.java.*;
import main.java.controller.Collision;
import main.java.controller.Gameboard;
import main.java.model.enemies.Enemy;
import main.java.model.enemies.NoobEnemy;

@RunWith(EasyMockRunner.class)
public class ModelTest {

	private NoobEnemy noobEnemyHitted = new NoobEnemy(new Coordinate(130 + 5, 250));
	private NoobEnemy noobEnemyNotHitted = new NoobEnemy(new Coordinate(130 + 5, 250));

	private Shot shotToEnemy = new Shot(Direction.up,new Coordinate(140,255));
	private Shot shotToPlayer = new Shot(Direction.down,new Coordinate(305,675));
	
	private List<Enemy> wrapperList = List.of(noobEnemyHitted,noobEnemyNotHitted);
	private Player player = new Player(new Coordinate(300,672)); 
	
	@TestSubject
	private Collision enemyCollision = new Collision(wrapperList,shotToEnemy);
	
	@TestSubject 
	private Collision playerCollision = new Collision(player,shotToPlayer); 
	
	@TestSubject 
	private Gameboard gameBoard = new Gameboard();
	
	@Mock
	private GameObject obstacle; 
	
	//testing only Collision class
	@Test
	public void collisionTestEnemy() {
		boolean hitted = enemyCollision.detectCollision();
		assertTrue(hitted); 
		assertEquals(enemyCollision.getLoosingEnemy(),noobEnemyHitted);
	}
	
	//testing only Collision class
	@Test
	public void collisionTestPlayer() {
		boolean hitted = playerCollision.detectCollision();
		assertTrue(hitted); 
		assertEquals(playerCollision.getLoosingEnemy(),null);
	}
	
	//testing collision detection of gameboard
	@Test
	public void gameBoardCollisionEnemy() {
		int enemyLivesBeforeCollision = gameBoard.getEnemies().get(0).getLives();
		gameBoard.addShot(new Pair<Shot,GameCharacter>(shotToEnemy,player));
		gameBoard.collisionDetection();
		//player should be hitted 
		assertEquals(gameBoard.getEnemies().get(0).lives,enemyLivesBeforeCollision-1);
		
		//shot should be detroyed after collision
		assertTrue(shotToEnemy.isDestroyed());
		
	}

	@Test
	public void gameBoardCollisionPlayer() {
		int playerLivesBeforeCollision = gameBoard.getPlayer().getLives();
		gameBoard.addShot(new Pair<Shot,GameCharacter>(shotToPlayer,noobEnemyNotHitted));
		gameBoard.collisionDetection();
		//player should be hitted 
		assertEquals(gameBoard.getPlayer().getLives(),playerLivesBeforeCollision-1);
		
		//shot should be detroyed after collision
		assertTrue(shotToPlayer.isDestroyed());
	}
}
