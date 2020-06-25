package model;

public class Player extends GameCharacter{
	private static final String PLAYERICON = "player.png";

	public Player(Coordinate position) {
		super(1,PLAYERICON,PLAYERICON, position);
	}

	//the player sets the direction through the keyboard 
	private Direction direction; 
	
	private final String RUNICON1 = "player_run1.png"; 
	private final String RUNICON2 = "player_run2.png"; 
	
	
	public void setDirection(Direction direction) {
		this.direction = direction; 
	}

	@Override
	public void shoot() {
		
	}

	@Override
	public void reduceLife() {
		lives--;
	}

	@Override
	public void move() {
		
	}
	
	public String getRunIcon1() {
		return RUNICON1; 
	}
	
	public String getRunIcon2() {
		return RUNICON2; 
	}
	
	
}
