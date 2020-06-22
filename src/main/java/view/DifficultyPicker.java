package view;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Level;

public class DifficultyPicker extends HBox{
	private ImageView circleImage; 
	private CustomLabel label; 
	private static final String CIRCLE = "grey_circle.png"; 
	private static final String CIRCLEChoosen = "blue_boxTick.png"; 

	private boolean isChoosen = false;
	
	public DifficultyPicker(Level level) {
		circleImage = new ImageView(getClass().getClassLoader().getResource(CIRCLE).toExternalForm());
		label = new CustomLabel(level.toString()); 
		setAlignment(Pos.CENTER);
		this.setSpacing(10);
		this.getChildren().add(circleImage); 
		this.getChildren().add(label); 
	}
	
	public void changeChoosen(boolean choosen) {
		this.isChoosen = choosen; 
		if(choosen) {
			circleImage.setImage(new Image(getClass().getClassLoader().getResource(CIRCLEChoosen).toExternalForm()));
		}else {
			circleImage.setImage(new Image(getClass().getClassLoader().getResource(CIRCLE).toExternalForm()));
		}
	}

}
