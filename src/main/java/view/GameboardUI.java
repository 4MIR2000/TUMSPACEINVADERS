package view;

import java.awt.Canvas;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import controller.Gameboard;
import controller.KeyboardController;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
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
import model.Coordinate;
import model.Direction;
import model.Shot;
import model.enemies.Enemy;

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
	
	private ImageView playerImage; 
	private AnimationTimer gameTimer; 
	
	private List<ImageView> enemiesImages; 
	private List<ImageView> shotsImages; 
	
	private long nanosOfLastPlayerMovement = -1;
	private long nanosOfLastEnemyMovement = -1;
	private long nanosOfLastShotMovement = -1;
	private long nanosOfLastEnemyShooting = -1;
	
	private long nanosOfLastPlayerShooting = -1;
	
	private int enemyShootTimer = 0;
	
	private String backButton_style = "-fx-background-color: transparent; -fx-background-repeat: no-repeat; -fx-background-image: url('blue_sliderLeft.png')";

	private MainMenu mainMenu; 
	public GameboardUI(MainMenu menu) {
		this.mainMenu = menu; 
		gameSetup(); 
	}
	
	private void gameSetup() {
		pane = new AnchorPane(); 
		scene = new Scene(pane,SIZE.getWidth(), SIZE.getHeight());
		stage = new Stage(); 
		stage.setScene(scene); 
		gameboard = new Gameboard();
		keyboardController = new KeyboardController(gameboard.getPlayer(), this);
		shotsImages = new ArrayList<ImageView>(); 
		createBackground();
		paintPlayer(); 
		paintEnemies(); 
		createBackButton();
		scene.getRoot().requestFocus();
		stage.show();
		startGameLoop();
	}
	

	public void paintPlayer() {
		playerImage = new ImageView(getClass().getClassLoader().getResource(gameboard.getPlayer().getIcon()).toExternalForm());
		playerImage.setLayoutX(gameboard.getPlayer().getPosition().getX());
		playerImage.setLayoutY(gameboard.getPlayer().getPosition().getY());
		pane.getChildren().add(playerImage); 
	}
	
	public void paintEnemies() {
		List<Enemy> enemies = gameboard.getEnemies(); 
		enemiesImages = new ArrayList<ImageView>();
		int i=0; 
		for(Enemy enemy:enemies) {
			ImageView iv = new ImageView(getClass().getClassLoader().getResource(enemy.getIcon()).toExternalForm());
			iv.setLayoutX(enemy.getPosition().getX());
			iv.setLayoutY(enemy.getPosition().getY());
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
		pane.getChildren().add(shotView);
		
	}
	
	private void startGameLoop() {
		gameTimer = new AnimationTimer() {
		
			@Override
			public void handle(long now) {
				if(!gameboard.gameIsClosed()) {
					if(!gameboard.checkIfGameEnded()) {
						if(nanosOfLastPlayerMovement==-1||now-nanosOfLastPlayerMovement>=1000000) {
							movePlayer();
							nanosOfLastPlayerMovement = now;
						}
							
						
						//move enemies slower
						if(nanosOfLastEnemyMovement==-1||now-nanosOfLastEnemyMovement>=2000000) {
							moveEnemies();
							nanosOfLastEnemyMovement = now; 
						}
						
						if(nanosOfLastEnemyShooting==-1||now-nanosOfLastEnemyShooting>=600000000) {
							enemyShoot();
							nanosOfLastEnemyShooting = now; 
						}
						
						if(keyboardController.isSpaceKeyPressed()) {
							//shoot
							if(nanosOfLastPlayerShooting==-1||now-nanosOfLastPlayerShooting>=300000000) {
								int indexOfReUseShot = gameboard.reUseShot(new Coordinate(gameboard.getPlayer().getPosition().getX() + 48,
										gameboard.getPlayer().getPosition().getY() + 2), Direction.up); 
								if(indexOfReUseShot==-1) {
									//there is no detroyed shot -> create a new one 
									Shot createdShot = gameboard.playerShoot();
									paintShot(createdShot, -1);
								}else {
									paintShot(gameboard.getShots().get(indexOfReUseShot), indexOfReUseShot);
								}
								nanosOfLastPlayerShooting = now;
							}
						}
						
						if(nanosOfLastShotMovement==-1||now-nanosOfLastShotMovement>=2000000) {
							moveShots(); 
							gameboard.collisionDetection(); 
							//remove destoryed shots 
							removeDestroyedShots(); 
							removeKilledEnemies();
							//remove destroyed enemies
							nanosOfLastShotMovement = now;
						}
						
					}else {
						//gameEnded
						if(gameboard.playerHasLost()) {
							ImageView view = new ImageView(new Image(getClass().getClassLoader().getResource(LOSTIMAGE).toExternalForm(),600,800,false,true));
							view.setOpacity(20);
							pane.getChildren().add(view);
						}else {
							ImageView view = new ImageView(new Image(getClass().getClassLoader().getResource(WONIMAGE).toExternalForm(),600,800,false,true));
							view.setOpacity(0.5);
							pane.getChildren().add(view);
						}
						gameTimer.stop();
						scene.getRoot().requestFocus();
						createBackButton();
					}
									
				}else {
					gameTimer.stop();
				}
			}
				
		};
		gameTimer.start();
	}
	
	public void enemyShoot() {
		List<Integer> shotsIndices = gameboard.enemyShoot();
		//implement ShotReUse
		for(int index:shotsIndices) {
			if(index>=shotsImages.size()) {
				paintShot(gameboard.getShots().get(index), -1);
			}else {
				paintShot(gameboard.getShots().get(index), index);
			}
		}
		
	}
	
	public void removeDestroyedShots() {
		List<Shot> shots = gameboard.getShots(); 
		for(int i=0; i<shotsImages.size(); i++) {
			if(shots.get(i).isDestroyed()) {
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
		List<Shot> shots = gameboard.getShots(); 
		
		for(int i=0; i<shots.size();i++) {
			//shotsImages.get(i).setLayoutX(shots.get(i).getPosition().getX());
			shotsImages.get(i).setLayoutY(shots.get(i).getPosition().getY());
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
				gameboard.movePlayer(Direction.left);
			}
			
			//change image 
			playerImage.setImage(new Image(getClass().getClassLoader().getResource(gameboard.getPlayer().getRunIcon1()).toExternalForm()));			
		}
		
		//right key pressed
		if(keyboardController.isRightKeyPressed()&&!keyboardController.isLeftKeyPressed()) {
			if(gameboard.getPlayer().getPosition().getX()<522) {
				gameboard.movePlayer(Direction.right);
			}
			playerImage.setImage(new Image(getClass().getClassLoader().getResource(gameboard.getPlayer().getRunIcon2()).toExternalForm()));
		}
		
		if(keyboardController.isRightKeyPressed()&&keyboardController.isLeftKeyPressed()) {
			playerImage.setImage(new Image(gameboard.getPlayer().getIcon()));
		}
		
		if(!keyboardController.isRightKeyPressed()&&!keyboardController.isLeftKeyPressed()) {
			playerImage.setImage(new Image(gameboard.getPlayer().getIcon()));

		}
		
		playerImage.setLayoutX(gameboard.getPlayer().getPosition().getX());
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
				mainMenu.Callback();
			}
			
		});
		pane.getChildren().add(backBtn);
		
	}

}
