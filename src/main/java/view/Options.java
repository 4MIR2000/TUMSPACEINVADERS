package main.java.view;

import java.util.ArrayList;
import java.util.List;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.model.Level;

public class Options {
	
	private GridPane pane; 
	private Scene scene; 
	private Stage stage; 
	
	private static final Dimension SIZE = new Dimension(600,800);
	private List<DifficultyPicker> diffPickers; 
	//static inorder to keep consistent
	private static Level choosenDiff = Level.easy;
	
	private String backButton_style = "-fx-background-color: transparent; -fx-background-repeat: no-repeat; -fx-background-image: url('blue_sliderLeft.png')";
	
	private MainMenu mainMenu; 
	
	public Options(MainMenu menu) {
		//reference to menu for going back
		mainMenu = menu; 
		pane = new GridPane(); 
		stage = new Stage(); 
		scene = new Scene(pane, SIZE.getWidth(), SIZE.getHeight()); 
		stage.setScene(scene);
		
		pane.setPadding(new Insets(10,10,10,10));
		createDiffPicker();
		createBackButton();
		stage.show();
		
	}
	
	//difficulty picker
	private void createDiffPicker() {
		diffPickers = new ArrayList<DifficultyPicker>();
		VBox diffBox = new VBox();
		CustomLabel title = new CustomLabel("1. choose difficulty");
		title.setUnderline(true);
		title.setAlignment(Pos.TOP_LEFT);
		diffBox.getChildren().add(title);
		
		for(Level level: Level.values()) {
			DifficultyPicker picker = new DifficultyPicker(level); 
			diffPickers.add(picker);
			//set the current choosen to selected
			if(level.equals(choosenDiff))
				picker.changeChoosen(true);
			
			picker.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					//set all others to unchoosen
					for(DifficultyPicker diff: diffPickers) {
						diff.changeChoosen(false);
					}
					picker.changeChoosen(true);
					choosenDiff = level; 
				}
				
			});
			diffBox.getChildren().add(picker);
		}
		
		pane.addRow(1, diffBox);
		
	}
	
	private void createBackButton() {
		CustomButton backBtn = new CustomButton("");
		backBtn.setButtonStyle(backButton_style);
		backBtn.setButtonPressedStyle(backButton_style);
		//backBtn.setGraphic(new ImageView("/view/resources/blue_sliderLeft.png"));
		
		backBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				stage.hide();
				mainMenu.Callback();
			}
			
		});
		pane.addRow(0, backBtn);
		
	}
	public static Level getChoosenDiff() {
		return choosenDiff;
	}
}
