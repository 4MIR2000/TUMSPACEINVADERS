package main.java.model;

import main.java.view.Dimension;

public class Shot extends GameObject{
	private int speed = 2;
	private Direction direction;
	private static final String SHOTICON=  "shot.png";
	private static final Dimension SHOTSIZE= new Dimension(32,32);
	
	private boolean isDestroyed; 
	
	public Shot(Direction direction, Coordinate position) {
		super(SHOTICON, position, SHOTSIZE);
		this.direction = direction; 
		
	}
	
	public Shot() {
		super(SHOTICON,null,SHOTSIZE);
	}
	
	/*changes the position of the shot 
	 * in a loop and moves it. 
	 */
	public void hitDestination() {
		
	}
	
	public void setDestroyed(boolean destroyed) {
		this.isDestroyed = destroyed; 
	}
	
	public void move() {
		if(direction == Direction.down||direction == Direction.up) {
			moveStraight();
		}else {
			moveDiagonal();
		}
	}
	
	//moves the shot according to speed and direction
	private void moveStraight() {
		if(direction==Direction.up) {
			getPosition().setY(getPosition().getY()-speed);
		}else {
			if(direction==Direction.down) {
				getPosition().setY(getPosition().getY()+speed);
			}
		}
	}
	
	private void moveDiagonal() {
		if(direction==Direction.downDiagonal) {
			getPosition().setY(getPosition().getY()+speed);
			getPosition().setX(getPosition().getX()+1);
		}else {
			if(direction==Direction.upDiagonal) {
				getPosition().setY(getPosition().getY()-speed);
				getPosition().setX(getPosition().getX()+1);
			}
		}
	}
	
	public boolean isDestroyed() {
		return isDestroyed; 
	}
	public Direction getDirection() {
		return direction; 
	}
	
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
}
