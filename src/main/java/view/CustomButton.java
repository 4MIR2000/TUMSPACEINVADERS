package main.java.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

public class CustomButton extends Button{

	private final String FONT_PATH = "kenvector_future.ttf";
	private String button_style = "-fx-background-color: transparent; -fx-background-image: url('blue_button.png')";
	private String button_pressed_style = "-fx-background-color: transparent; -fx-background-image: url('blue_button_pressed.png')";
	
	public CustomButton(String text) {
		setText(text); 
		setFont();
		setPrefWidth(190);
		setPrefHeight(49);
		setStyle(button_style);
		initializeListeners();
	}
	
	private void setFont() {
		try {
			setFont(Font.loadFont(new FileInputStream(getClass().getClassLoader().getResource(FONT_PATH).toExternalForm()),23));
		} catch (FileNotFoundException e) {
			//default font
			setFont(Font.font("Verdana",23));
		}
	}
	
	private void setButtonPressedStyle() {
		setStyle(button_pressed_style);
		setPrefHeight(45);
		setLayoutY(getLayoutY()+4);
	
	}
	
	private void setButtonStyle() {
		setStyle(button_style);
		setPrefHeight(49);
		setLayoutY(getLayoutY()-4);
	}
	
	private void initializeListeners() {
		setOnMousePressed((new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(event.getButton().equals(MouseButton.PRIMARY)) {
					setButtonPressedStyle();
				}
			}
			
		}));
			
		setOnMouseReleased((new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(event.getButton().equals(MouseButton.PRIMARY)) {
					setButtonStyle();
				}
				
			}
			
		}));
		
		setOnMouseEntered((new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				setEffect(new DropShadow());
				
			}
			
		}));
		
		setOnMouseExited((new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				setEffect(null);
				
			}
			
		}));
	}
	
	public void setButtonStyle(String style) {
		button_style = style; 
		setStyle(style);
	}
	
	public void setButtonPressedStyle(String style) {
		button_pressed_style = style; 
	}
}
