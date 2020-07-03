package main.java.model;

import main.java.view.Dimension;

public class Heart extends GameObject{

	private static final String HEARTICON = "heart.png";
	private static final Dimension HEARTSIZE = new Dimension(40,40); 
	
	public Heart(Coordinate position) {
		super(HEARTICON,position,HEARTSIZE);
	}

	public Heart() {
		super(HEARTICON,null,HEARTSIZE);
	}

}
