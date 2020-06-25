package model;

public abstract class GameObject {

	private String icon; 
	private Coordinate position; 
	
	public GameObject(String icon, Coordinate position) {
		this.icon = icon; 
		this.position = position; 
	}
	
	public void setIcon(String url) {
		this.icon = url; 
	}
	
	public void setPosition(Coordinate position) {
		this.position = position; 
	}
	
	public String getIcon() {
		return icon; 
	}
	
	public Coordinate getPosition() {
		return position; 
	}
}
