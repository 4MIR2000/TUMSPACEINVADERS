package controller;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Player;
import view.GameboardUI;

public class KeyboardController {
	private Player player; 
	private GameboardUI gameboardUI; 
	//@TODO ADD keyboardHandler
	private int angle; 
	
	
	private boolean isLeftKeyPressed; 
	private boolean isRightKeyPressed; 
	private boolean isSpaceKeyPressed; 
	
	public KeyboardController(Player player, GameboardUI gameboardUI) {
		this.player = player; 
		this.gameboardUI = gameboardUI;
		createListeners(); 
	}
	
	public void createListeners() {
		gameboardUI.getGameScene().setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.LEFT) {
					isLeftKeyPressed = true; 
				}else if(event.getCode()==KeyCode.RIGHT) {
					isRightKeyPressed = true; 
				}else if(event.getCode()==KeyCode.SPACE) {
					isSpaceKeyPressed = true; 
				}
				
			}
			
		});
		
		gameboardUI.getGameScene().setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.LEFT) {
					isLeftKeyPressed = false; 
				}else if(event.getCode()==KeyCode.RIGHT) {
					isRightKeyPressed = false;
				}else if(event.getCode()==KeyCode.SPACE) {
					isSpaceKeyPressed = false; 
				}			
			}
			
		});
		
	}
	
	public boolean isLeftKeyPressed() {
		return isLeftKeyPressed;
	}
	
	public boolean isRightKeyPressed() {
		return isRightKeyPressed; 
	}
	
	public boolean isSpaceKeyPressed() {
		return isSpaceKeyPressed; 
	}
}
