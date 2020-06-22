package controller;

public class AudioPlayer {
	//TODO add shotSound
	private String shotSound = "view/resources/";
	
	//TODO add backgroundMusic 
	private String backgroundMusic = "view/resources/";
	
	public AudioPlayer() {
		this.backgroundMusic = backgroundMusic;
		this.shotSound = shotSound; 
	}
	
	public void setShotSound(String shotSound) {
		this.shotSound = shotSound; 
	}
	
	public void playShotSound() {
		
	}
	
	public void playBackgroundMusic() {
		
	}
	
	public void stopBackgroundMusic() {
		
	}
}
