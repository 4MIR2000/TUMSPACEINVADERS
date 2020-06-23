package model;

public enum Level {
	easy,medium,hard;
	private final int SPEEDEASY = 1; 
	private final int SPEEDMEDIUM = 2; 
	private final int SPEEDHARD = 3; 
	
	private final int NOOBSHOOTINGRATE = 10; 
	private final int MEDIUMSHOOTINGRATE = 4; 
	private final int HARDSHOOTINGRATE = 1;
	
	public int getSpeed() {
		if(this==easy) {
			return SPEEDEASY; 
		}else {
			if(this==medium) {
				return SPEEDMEDIUM; 
			}else {
				return SPEEDHARD; 
			}
		}
	}
	
	public int getShootingRate() {
		switch(this) {
		case easy: 
			return NOOBSHOOTINGRATE;
			
		case medium: 
			return MEDIUMSHOOTINGRATE; 
		case hard: 
			return HARDSHOOTINGRATE;
		default: return 0; 
		}
	}
	
	
}
