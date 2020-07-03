package main.java.model;

import main.java.view.Dimension;
//is not implemented yet

//TODO add tables here as obstacles 
public class Obstacle extends GameObject{

	private static final String OBSTACLEICON = "table.png";
	private static final Dimension SIZE = new Dimension(448,473);
	private Coordinate position; 
	
	public Obstacle(Coordinate position) {
		super(OBSTACLEICON,position,SIZE);
	}
	
	
}
