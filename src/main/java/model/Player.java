package main.java.model;

import main.java.view.Dimension;

public class Player extends GameCharacter{
	private static final String PLAYERICON = "player.png";
 
	private static final Dimension PLAYERSIZE = new Dimension(70,116); 
	
	public Player(Coordinate position) {
		super(1,PLAYERICON,PLAYERICON, position, PLAYERSIZE);
	}

	//the player sets the direction through the keyboard 
	private Direction direction; 
	
	private final String RUNICON1 = "player_run1.png"; 
	private final String RUNICON2 = "player_run2.png"; 
	
	
	public void setDirection(Direction direction) {
		this.direction = direction; 
	}

	public Coordinate getShotStartPosition() {
		return new Coordinate(getPosition().getX() + 48,
				getPosition().getY() + 2);
	}
	
	//also moves the shot 
	@Override
	public void shoot(Shot shot, boolean reset) {
		if(reset) {
			shot.setPosition(getShotStartPosition());
			shot.setDestroyed(false);
			shot.setDirection(Direction.up);
		}else {
			shot.move();
		}
	}

	@Override
	public void reduceLife() {
		lives--;
	}

	@Override
	public void move(Direction direction) {
		if (direction.equals(Direction.left)) {
			getPosition().setX(getPosition().getX() - getSpeed());
		} else {
			if (direction.equals(Direction.right)) {
				getPosition().setX(getPosition().getX() + getSpeed());
			}
		}
	}
	
	public String getRunIcon1() {
		return RUNICON1; 
	}
	
	public String getRunIcon2() {
		return RUNICON2; 
	}
	
	
}
