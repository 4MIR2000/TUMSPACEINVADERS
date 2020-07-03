package main.java.model;

import main.java.view.Dimension;

public abstract class GameObject implements GameObjectInterface {

	private String icon; 
	private Coordinate position; 
	private Dimension size; 
	
	public GameObject(String icon, Coordinate position, Dimension size) {
		this.icon = icon; 
		this.position = position; 
		this.size = size; 
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
	
	public Dimension getSize() {
		return size; 
	}
}
