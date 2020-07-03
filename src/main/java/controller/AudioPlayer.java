package main.java.controller;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URL;

import com.sun.media.jfxmedia.MediaException;

public class AudioPlayer {
	
	private String shotSound = "Sounds/paperThrow.wav";
	//private String enemyHurtSound ="Sounds/";
	private String enemyDeadSound = "Sounds/enemyHurt.wav";
	private String playerHurtSound = "Sounds/playerHurt1.wav";
	private String backgroundMusic1 = "Sounds/backgroundMusic1.wav";
	private final String GameOverSound = "Sounds/gameOver.wav";
	private String gameOverJingle = "Sounds/gameOverJingle.wav";
	private final String WinSound = "Sounds/youWin.wav";
	
	private MediaPlayer mediaPlayerBackgroundMusic;
	private boolean playingBackgroundMusic;

	private MediaPlayer mediaPlayerShot; 
	private MediaPlayer mediaPlayerEnemyHurt; 
	private MediaPlayer mediaPlayerPlayerHurt; 
	
	private MediaPlayer mediaPlayerDeath; 
	public AudioPlayer() {
		this.playingBackgroundMusic = false;
		initializeMediaPlayer();
	}


	public void setShotSound(String shotSound) {
		this.shotSound = shotSound;
	}

	private void initializeMediaPlayer() {
		try {
			mediaPlayerShot = new MediaPlayer(loadAudioFile(shotSound)); 
			mediaPlayerEnemyHurt = new MediaPlayer(loadAudioFile(playerHurtSound));
			mediaPlayerEnemyHurt.setVolume(0.5);
			mediaPlayerDeath = new MediaPlayer(loadAudioFile(enemyDeadSound));
			mediaPlayerDeath.setVolume(0.2);
			mediaPlayerPlayerHurt = new MediaPlayer(loadAudioFile(playerHurtSound));
			mediaPlayerPlayerHurt.setVolume(0.5);
		}catch(Exception e) {
			
		}
		
	}
	
	public void playShotSound() {
		try {
			mediaPlayerShot.seek(mediaPlayerShot.getStartTime());
			mediaPlayerShot.play();
		}catch(Exception e) {
			//System.err.println("sound could not be played: MediaException!"); 
		}
		
	}
	
	public void playEnemyHurtSound() {
		try {
			mediaPlayerEnemyHurt.seek(mediaPlayerEnemyHurt.getStartTime());
			mediaPlayerEnemyHurt.play();
		}catch(Exception e) {
			
		}
		
	}

	public void playEnemyDeadSound() {
		try {
			mediaPlayerDeath.seek(mediaPlayerDeath.getStartTime());
			mediaPlayerDeath.play();
		}catch(Exception e) {
			
		}
		
	}
	
	public void playPlayerHurtSound() {
		try {
			mediaPlayerPlayerHurt.seek(mediaPlayerPlayerHurt.getStartTime());
			mediaPlayerPlayerHurt.play();
		}catch(Exception e) {
			
		}
		
	}
	
	public void playWinSound() {
		try {
			MediaPlayer mediaPlayerWin = new MediaPlayer(loadAudioFile(WinSound));
			mediaPlayerWin.setVolume(0.3);
			mediaPlayerWin.play();
		}catch(Exception e) {
			
		}
		
	}
	
	public void playGameOverSound() {
		try {
			MediaPlayer mediaPlayerGameOver = new MediaPlayer(loadAudioFile(GameOverSound));
			mediaPlayerGameOver.play();
			MediaPlayer mediaPlayerJingle = new MediaPlayer(loadAudioFile(gameOverJingle));
			mediaPlayerJingle.play();
			mediaPlayerGameOver.play();
		}catch(Exception e) {
			
		}
	}

	private Media loadAudioFile(String fileName) {
		URL audioSourceUrl = getClass().getClassLoader().getResource(fileName);
		if (audioSourceUrl == null) {
			throw new RuntimeException("Please ensure that your resources folder contains the appropriate files");
		}
		String musicSource = audioSourceUrl.toString();
		return new Media(musicSource);
	}

	public void playBackgroundMusic() {
		try {
			if (!this.playingBackgroundMusic) {
				this.playingBackgroundMusic = true;
				this.mediaPlayerBackgroundMusic = new MediaPlayer(loadAudioFile(backgroundMusic1));
				mediaPlayerBackgroundMusic.setVolume(0.1);
				this.mediaPlayerBackgroundMusic.setAutoPlay(true);
				// Loop for the main music sound:
				this.mediaPlayerBackgroundMusic
						.setOnEndOfMedia(() -> AudioPlayer.this.mediaPlayerBackgroundMusic.seek(Duration.ZERO));
				this.mediaPlayerBackgroundMusic.play();
			}
		}catch(Exception e) {
			//System.err.println("sound could not be played: MediaException!"); 
		}
		
	}

	public void stopBackgroundMusic() {
		try {
			if (this.playingBackgroundMusic) {
				this.playingBackgroundMusic = false;
				this.mediaPlayerBackgroundMusic.stop();
			}
		}catch(Exception e){
			//System.err.println("sound could not be played: MediaException!"); 

		}
		
	}
}
