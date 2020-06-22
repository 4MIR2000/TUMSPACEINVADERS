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
	
	//moves the shot according to speed and direction
	public void move() {
		if(direction==Direction.up) {
			position.setY(position.getY()-speed);
		}else {
			if(direction==Direction.down) {
				position.setY(position.getY()+speed);
			}
		}
			
	}
	
	public boolean isDestroyed() {
		return isDestroyed; 
	}
	public Direction getDirection() {
		return direction; 
	}
}
