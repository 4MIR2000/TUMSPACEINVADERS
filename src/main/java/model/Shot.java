package model;

public class Shot {
	private int speed = 2;
	private Direction direction;
	private Coordinate position; 
	private String icon =  "shot.png";
	private boolean isDestroyed; 
	
	public Shot(Direction direction, Coordinate startPos) {
		this.direction = direction; 
		this.position = startPos; 
	}
	
	public Shot() {
		
	}
	/*changes the position of the shot 
	 * in a loop and moves it. 
	 */
	public void hitDestination() {
		
	}
	
	public void setDestroyed(boolean destroyed) {
		this.isDestroyed = destroyed; 
	}
	
	public void setPosition(Coordinate position) {
		this.position = position; 
	}
	public Coordinate getPosition() {
		return position; 
	}
	
	public String getIcon() {
		return icon; 
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
			position.setY(position.getY()-speed);
		}else {
			if(direction==Direction.down) {
				position.setY(position.getY()+speed);
			}
		}
	}
	
	private void moveDiagonal() {
		if(direction==Direction.downDiagonal) {
			position.setY(position.getY()+speed);
			position.setX(position.getX()+1);
		}else {
			if(direction==Direction.upDiagonal) {
				position.setY(position.getY()-speed);
				position.setX(position.getX()+1);
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
