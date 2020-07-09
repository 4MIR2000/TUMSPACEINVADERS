package main.java.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.java.model.AnalyserInterface;
import main.java.model.DataObject;
import main.java.model.Level;
import main.java.model.Skill;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class MainMenu extends Application{

	private static final Dimension SIZE = new Dimension(600,800);
	private BorderPane pane; 
	private Scene scene; 
	private Stage stage; 
	
	
	private final String FONT_PATH = "view/resources/kenvector_future_thin.ttf";

	private CustomButton button_start; 
	private CustomButton button_options;
	private CustomButton button_exit; 
	
	private final String LOGOIMAGEPATH = "logo.png";
	
	private List<DataObject> collectedData = new ArrayList<DataObject>();  
	
	private int highScore; 
	private int winstreak; 
	
	public static void startApp(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		initializeMainMenu();
		stage.show();
		
	}
	
	public void initializeMainMenu() {
		pane = new BorderPane(); 
		pane.setPadding(new Insets(20,20,20,20));

		
		scene = new Scene(pane, SIZE.getWidth(), SIZE.getHeight());
		
		stage = new Stage(); 
		stage.setScene(scene);
		createButtons();
		addButtonListeners();
		createLogo();
	}
	
	public Stage getStage() {
		return stage;
	}

	private void createButtons() {
		button_start = new CustomButton("START"); 
	    button_options = new CustomButton("OPTIONS"); 
		button_exit = new CustomButton("EXIT"); 
	
		/*GridPane buttons_pane = new GridPane(); 
		buttons_pane.addColumn(0, button_start, button_options, button_exit);
		buttons_pane.setAlignment(Pos.CENTER);*/
		VBox buttonsBox = new VBox(50); 
		buttonsBox.getChildren().add(button_start); 
		buttonsBox.getChildren().add(button_options); 
		buttonsBox.getChildren().add(button_exit); 
		buttonsBox.setAlignment(Pos.CENTER);
		pane.setCenter(buttonsBox);
		
	}

	private void addButtonListeners(){
	
		button_start.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				//show the actual game
				new GameboardUI(MainMenu.this); 
				stage.hide(); 
				
			}
			
		});
		button_options.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Options options = new Options(MainMenu.this); 
				stage.hide(); 
			}
			
		});
		button_exit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stage.close();
			}
		});
	}
	
	private void createLogo() {
		ImageView logo = new ImageView(getClass().getClassLoader().getResource(LOGOIMAGEPATH).toExternalForm()); 
		HBox logoBox = new HBox(); 
		logoBox.getChildren().add(logo); 
		logoBox.setAlignment(Pos.CENTER);
		pane.setTop(logoBox);
	}
	
	public void setWinStreak(int winstreak) {
		this.winstreak = winstreak; 
	}
	
	public void setHighscore(int score) {
		this.highScore = score; 
	}
	
	//show menu again
	public void Callback(AnalyserInterface analyser) {
		showStageAgain();
		if(analyser==null) 
			return; 
		
		Skill skill = analyser.getPlayerSkill(); 
		if(skill==Skill.good||skill==Skill.veryGood) {
			winstreak++; 
		}else {
			winstreak = 0; 
		}
		if(winstreak>highScore) {
			highScore = winstreak; 
		}
		
		DataObject dataObject = new DataObject(analyser.getGameStartTime(),LocalDateTime.now(),skill);
		collectedData.add(dataObject); 
	}
	
	public List<DataObject> getCollectedData(){
		return collectedData; 
	}
	
	public int getHeighScore() {
		return highScore; 
	}
	
	public int getWinstreak() {
		return winstreak;
	}
	
	public void showStageAgain() {
		if(stage==null) return;
		stage.show(); 
	}
	private void createBackground() {
		//TODO ADDBackgroundImage
		//Image image = new Image("view/resources/")
	}
	
}
