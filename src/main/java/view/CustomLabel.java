package main.java.view;


import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

public class CustomLabel extends Label{

	private final String FONT_PATH = "view/resources/kenvector_future.ttf";
	
	public CustomLabel(String text) {
		setPrefWidth(380); 
		setPrefHeight(49); 
		setWrapText(true);
		setText(text);
		setFont();
		setAlignment(Pos.CENTER);
		setListeners(); 
	}

	private void setFont() {
		try {
			setFont(Font.loadFont(new FileInputStream(FONT_PATH),23));
		} catch (FileNotFoundException e) {
			//default font
			setFont(Font.font("Verdana",23));
		}
	}
	
	private void setListeners() {
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
}
