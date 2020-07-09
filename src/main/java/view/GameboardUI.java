package main.java.view;

import java.awt.Canvas;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.CacheHint;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import main.java.controller.Gameboard;
import main.java.controller.KeyboardController;
import main.java.model.Analyser;
import main.java.model.AnalyserInterface;
import main.java.model.Coordinate;
import main.java.model.Direction;
import main.java.model.GameCharacter;
import main.java.model.Heart;
import main.java.model.Pair;
import main.java.model.Shot;
import main.java.model.enemies.Enemy;

public class GameboardUI extends Canvas{
	private static final String BACKGROUNDIMAGE = "game_background.jpg";
	private static final String LOSTIMAGE = "lost.png";
	private static final String WONIMAGE = "won.png";
	
	public static final Dimension SIZE = new Dimension(600, 800);
	private Gameboard gameboard; 
	private KeyboardController keyboardController; 
	
	private AnchorPane pane; 
	private Scene scene; 
	private Stage stage;
	
	private ImageView playerImageView; 
	private AnimationTimer gameTimer; 
	
	private List<ImageView> enemiesImages; 
	private List<ImageView> shotsImages; 
	private List<ImageView> heartsImages; 
	
	private long nanosOfLastPlayerMovement = -1;
	private long nanosOfLastEnemyMovement = -1;
	private long nanosOfLastShotMovement = -1;
	private long nanosOfLastEnemyShooting = -1;
	
	private long nanosOfLastPlayerShooting = -1;
	
	private long frameCount = 0;
	
	private int enemyShootTimer = 0;
	
	private String backButton_style = "-fx-background-color: transparent; -fx-background-repeat: no-repeat; -fx-background-image: url('blue_sliderLeft.png')";
    
	private Image enemyImage;
	private Image playerImage; 
	private Image playerRightImage; 
	private Image playerLeftImage; 
	
	private MainMenu mainMenu; 
	
	private LocalDateTime gameStartTime; 
	public GameboardUI(MainMenu menu) {
		this.mainMenu = menu; 
		gameSetup(); 
	}
	
	private void gameSetup() {
		pane = new AnchorPane(); 
		pane.setCache(true);
		pane.setCacheHint(CacheHint.SPEED);
		scene = new Scene(pane,SIZE.getWidth(), SIZE.getHeight());
		stage = new Stage(); 
		stage.setScene(scene); 
		gameboard = new Gameboard();
		keyboardController = new KeyboardController(gameboard.getPlayer(), this);
		shotsImages = new ArrayList<ImageView>(); 
		createBackground();
		paintPlayer(); 
		paintEnemies(); 
		paintHearts();
		createBackButton();
		scene.getRoot().requestFocus();
		stage.show();
		startGameLoop();
		gameStartTime = LocalDateTime.now(); 
	}
	

	public void paintPlayer() {
		playerImage = new Image(getClass().getClassLoader().getResource(gameboard.getPlayer().getIcon()).toExternalForm());
		playerRightImage = new Image(getClass().getClassLoader().getResource(gameboard.getPlayer().getRunIcon1()).toExternalForm());
		playerLeftImage = new Image(getClass().getClassLoader().getResource(gameboard.getPlayer().getRunIcon2()).toExternalForm());
		playerImageView = new ImageView(playerImage);
		playerImageView.setLayoutX(gameboard.getPlayer().getPosition().getX());
		playerImageView.setLayoutY(gameboard.getPlayer().getPosition().getY());
		playerImageView.setCache(true);
		playerImageView.setCacheHint(CacheHint.SPEED);
		pane.getChildren().add(playerImageView); 
	}
	
	public void paintEnemies() {
		List<Enemy> enemies = gameboard.getEnemies(); 
		enemiesImages = new ArrayList<ImageView>();
		enemyImage = new Image(getClass().getClassLoader().getResource(enemies.get(0).getIcon()).toExternalForm());
		int i=0; 
		for(Enemy enemy:enemies) {
			ImageView iv = new ImageView(enemyImage);
			iv.setLayoutX(enemy.getPosition().getX());
			iv.setLayoutY(enemy.getPosition().getY());
			iv.setCache(true);
			iv.setCacheHint(CacheHint.SPEED);
			enemiesImages.add(iv);
			pane.getChildren().add(iv);
		}
		 
			
	}
	
	//the index is -1 if there is no destroyed shot
	public void paintShot(Shot shot, int indexOfReUseShot) {
		ImageView shotView; 
		if(indexOfReUseShot==-1) {
			shotView = new ImageView(new Image(getClass().getClassLoader().getResource(shot.getIcon()).toExternalForm(),32,32,false,true));
			shotsImages.add(shotView);
			
		}else {
			shotView = shotsImages.get(indexOfReUseShot); 
		}
		
		
		shotView.setLayoutX(shot.getPosition().getX());
		shotView.setLayoutY(shot.getPosition().getY());
		shotView.setCache(true);
		shotView.setCacheHint(CacheHint.SPEED);
		pane.getChildren().add(shotView);
		
	}
	
	private void startGameLoop() {	
		AnimationTimer playerShootingTimer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				if(keyboardController.isSpaceKeyPressed()) {
					//shoot
					if(nanosOfLastPlayerShooting==-1||now-nanosOfLastPlayerShooting>=400000000) {
						playerShoot();
						nanosOfLastPlayerShooting = now;
					}
				}
				
			}
		};
		playerShootingTimer.start();
		
		AnimationTimer enemyShootingTimer = new AnimationTimer() {

			@Override
			public void handle(long now) {
				if(nanosOfLastEnemyShooting==-1||now-nanosOfLastEnemyShooting>=600000000) {
					enemyShoot();
					nanosOfLastEnemyShooting = now; 
				}
				if(now-nanosOfLastEnemyShooting>=400000000) {						
					//stop shooting animation
					resetEnemyIcons(); 
				}
			}
			
		};
		enemyShootingTimer.start();
		
		AnimationTimer moveShotsTimer = new AnimationTimer() {
			
			@Override
			public void handle(long now) {
				if(nanosOfLastShotMovement==-1||now-nanosOfLastShotMovement>=1000000) {
					moveShots(); 
					gameboard.collisionDetection(); 
					//remove destoryed shots 
					removeDestroyedShots(); 
					removeKilledEnemies();
					removeHeart(); 
					//remove destroyed enemies
					nanosOfLastShotMovement = now;
				}
				
				
			}
		};
		moveShotsTimer.start();
		
		AnimationTimer moveEnemiesTimer = new AnimationTimer() {
			
			@Override
			public void handle(long now) {
				//move enemies slower
				if(nanosOfLastEnemyMovement==-1||now-nanosOfLastEnemyMovement>=1000000) {
					moveEnemies();
					nanosOfLastEnemyMovement = now; 
				}
																				
				
				
			}
		};
		moveEnemiesTimer.start();
		
		gameTimer = new AnimationTimer() {
			
			@Override
			public void handle(long now) {
					if(!gameboard.gameIsClosed()&&!gameboard.checkIfGameEnded()) {
						if(nanosOfLastPlayerMovement==-1||now-nanosOfLastPlayerMovement>=900000) {
							movePlayer();
							nanosOfLastPlayerMovement = now;
						}												
						
					}else {
						if(!gameboard.gameIsClosed()) {
							//gameEnded
							showEndResult();
							createBackButton();
						}
						
						gameTimer.stop();
						playerShootingTimer.stop();
						enemyShootingTimer.stop();
						moveShotsTimer.stop();
						moveEnemiesTimer.stop();
						
					}
			}
				
		};
		gameTimer.start();
	}
	
	private void showEndResult() {
		if(gameboard.playerHasLost()) {
			ImageView view = new ImageView(new Image(getClass().getClassLoader().getResource(LOSTIMAGE).toExternalForm(),SIZE.getWidth(),SIZE.getHeight(),false,true));
			view.setOpacity(20);
			pane.getChildren().add(view);
		}else {
			ImageView view = new ImageView(new Image(getClass().getClassLoader().getResource(WONIMAGE).toExternalForm(),SIZE.getWidth(),SIZE.getHeight(),false,true));
			view.setOpacity(0.5);
			pane.getChildren().add(view);
		}
	}
	//removes heart if player lost one life
	private void removeHeart() {
		if(heartsImages.size()>0&&gameboard.getPlayer().getLives()<heartsImages.size()) {
			//remove one heart
			ImageView lastHeart = heartsImages.get(heartsImages.size()-1);
			pane.getChildren().remove(lastHeart);
			heartsImages.remove(lastHeart);
		}
	}
	private void paintHearts() {
		heartsImages = new ArrayList<ImageView>(); 
		//create three hearts ;)
		gameboard.createHearts();
		for(Heart heart:gameboard.getHearts()) {
			ImageView view = new ImageView(new Image(getClass().getClassLoader().getResource(heart.getIcon()).toExternalForm(),
					heart.getSize().getWidth(), heart.getSize().getHeight(),false,true)); 
			pane.getChildren().add(view); 
			view.setLayoutX(heart.getPosition().getX());
			view.setLayoutY(heart.getPosition().getY());
			view.setCache(true);
			view.setCacheHint(CacheHint.SPEED);
			heartsImages.add(view); 
			
		}
	}
	private void resetEnemyIcons() {
		List<Enemy> enemies = gameboard.getEnemies(); 
		for(int i=0; i<enemies.size(); i++) {
			enemiesImages.get(i).setImage(enemyImage);
		}
	}
	
	private void playerShoot() {
		int indexOfReUseShot = gameboard.reUseShot(); 
		if(indexOfReUseShot==-1) {
			//there is no destroyed shot -> create a new one 
			Shot createdShot = gameboard.playerShoot();
			paintShot(createdShot, -1);
		}else {
			Pair<Shot,GameCharacter> reuseShot = gameboard.getShots().get(indexOfReUseShot);
			reuseShot.setValue(gameboard.getPlayer());
			reuseShot.getValue().shoot(reuseShot.getKey(), true);		
			paintShot(reuseShot.getKey(), indexOfReUseShot);
		}
	}
	
	public void enemyShoot() {
		List<Integer> shotsIndices = gameboard.enemyShoot();
		//implement ShotReUse
		for(int shotIndex:shotsIndices) {
			Pair<Shot,GameCharacter> shot = gameboard.getShots().get(shotIndex);
			if(shotIndex>=shotsImages.size()) {
				paintShot(shot.getKey(), -1);
			}else {
				paintShot(shot.getKey(), shotIndex);
			}
			
			//shootingAnimation
			GameCharacter shootingEnemy = shot.getValue();
			
			int indexOfShootingEnemy = gameboard.getEnemies().indexOf(shot.getValue());
			enemiesImages.get(indexOfShootingEnemy).setImage(new Image(getClass().getClassLoader().getResource(shootingEnemy.getShootingIcon()).toExternalForm()));
		}
		
	
	}
	
	public void removeDestroyedShots() {
		List<Pair<Shot, GameCharacter>> shots = gameboard.getShots(); 
		for(int i=0; i<shotsImages.size(); i++) {
			if(shots.get(i).getKey().isDestroyed()) {
				pane.getChildren().remove(shotsImages.get(i));
			}
		}
	}
	
	public void removeKilledEnemies() {
		List<Enemy> enemies = gameboard.getEnemies();
		for(int i=0; i<enemies.size(); i++) {
			if(!enemies.get(i).isAlive()) {
				pane.getChildren().remove(enemiesImages.get(i));
			}
		}
	}
	
	public void moveShots() {
		gameboard.moveShots();
		List<Pair<Shot, GameCharacter>> shots = gameboard.getShots(); 
		
		for(int i=0; i<shots.size();i++) {
			shotsImages.get(i).setLayoutX(shots.get(i).getKey().getPosition().getX());
			shotsImages.get(i).setLayoutY(shots.get(i).getKey().getPosition().getY());
		}
	}
	public void moveEnemies() {
		gameboard.moveEnemies();
		
		//update positions of the imagViews
		for(int i=0; i<enemiesImages.size(); i++) {
			enemiesImages.get(i).setLayoutX(gameboard.getEnemies().get(i).getPosition().getX());
		}
	}
	public void movePlayer() {
		if(keyboardController.isLeftKeyPressed()&&!keyboardController.isRightKeyPressed()) {
			if(gameboard.getPlayer().getPosition().getX()>-20) {
				gameboard.getPlayer().move(Direction.left);
			}
			
			//change image 
			playerImageView.setImage(playerLeftImage);			
		}
		
		//right key pressed
		if(keyboardController.isRightKeyPressed()&&!keyboardController.isLeftKeyPressed()) {
			if(gameboard.getPlayer().getPosition().getX()<522) {
				gameboard.getPlayer().move(Direction.right);
			}
			playerImageView.setImage(playerRightImage);
		}
		
		if(keyboardController.isRightKeyPressed()&&keyboardController.isLeftKeyPressed()) {
			playerImageView.setImage(playerImage);
		}
		
		if(!keyboardController.isRightKeyPressed()&&!keyboardController.isLeftKeyPressed()) {
			playerImageView.setImage(playerImage);

		}
		
		playerImageView.setLayoutX(gameboard.getPlayer().getPosition().getX());
	}
	
	
	public Scene getGameScene() {
		return scene; 
	}
	
	public AnchorPane getGamePane() {
		return pane; 
	}
	
	public Stage getGameStage() {
		return stage; 
	}
	
	
	public void createBackground() {
		BackgroundImage backgroundView = new BackgroundImage(new Image(
				getClass().getClassLoader().getResource(BACKGROUNDIMAGE).toExternalForm(),SIZE.getWidth(),SIZE.getHeight(),false,true),
		        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
		          BackgroundSize.DEFAULT);;
		 
		pane.setBackground(new Background(backgroundView));
		
	}
	
	private void createBackButton() {
		CustomButton backBtn = new CustomButton("");
		backBtn.setButtonStyle(backButton_style);
		backBtn.setButtonPressedStyle(backButton_style);
		//backBtn.setGraphic(new ImageView("/view/resources/blue_sliderLeft.png"));
		backBtn.setLayoutX(5);
		backBtn.setLayoutY(5);
		backBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				gameboard.stopGame();
				stage.hide();
				returnToMainMenu(new Analyser());
			}
			
		});
		backBtn.setCache(true);
		backBtn.setCacheHint(CacheHint.SPEED);
		pane.getChildren().add(backBtn);
		
	}

	public void returnToMainMenu(AnalyserInterface analyser) {
		
		mainMenu.Callback(analyser,gameStartTime);
		
	}
}
