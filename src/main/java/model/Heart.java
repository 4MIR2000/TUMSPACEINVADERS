package model;

import java.awt.Dimension;

public class Heart extends GameObject{

	private static final String HEARTICON = "heart.png";
	public static final Dimension SIZE = new Dimension(40,40); 
	
	public Heart(Coordinate position) {
		super(HEARTICON, position);
	}


}
