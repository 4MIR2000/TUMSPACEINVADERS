package controller;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URL;

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

	public AudioPlayer() {
		this.playingBackgroundMusic = false;
	}

	public void setShotSound(String shotSound) {
		this.shotSound = shotSound;
	}

	public void playShotSound() {
		MediaPlayer mediaPlayerShot = new MediaPlayer(loadAudioFile(shotSound));
		mediaPlayerShot.play();
	}
	
	public void playEnemyHurtSound() {
		MediaPlayer mediaPlayerHurt = new MediaPlayer(loadAudioFile(playerHurtSound));
		mediaPlayerHurt.setVolume(0.5);
		mediaPlayerHurt.play();
	}

	public void playEnemyDeadSound() {
		MediaPlayer mediaPlayerDeathSound = new MediaPlayer(loadAudioFile(enemyDeadSound));
		mediaPlayerDeathSound.setVolume(0.2);
		mediaPlayerDeathSound.play();
	}
	
	public void playPlayerHurtSound() {
		MediaPlayer mediaPlayerHurt = new MediaPlayer(loadAudioFile(playerHurtSound));
		mediaPlayerHurt.setVolume(0.5);
		mediaPlayerHurt.play();
	}
	
	public void playWinSound() {
		MediaPlayer mediaPlayerWin = new MediaPlayer(loadAudioFile(WinSound));
		mediaPlayerWin.setVolume(0.3);
		mediaPlayerWin.play();
	}
	
	public void playGameOverSound() {
		MediaPlayer mediaPlayerGameOver = new MediaPlayer(loadAudioFile(GameOverSound));
		mediaPlayerGameOver.play();
		MediaPlayer mediaPlayerJingle = new MediaPlayer(loadAudioFile(gameOverJingle));
		mediaPlayerJingle.play();
		mediaPlayerGameOver.play();
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
	}

	public void stopBackgroundMusic() {
		if (this.playingBackgroundMusic) {
			this.playingBackgroundMusic = false;
			this.mediaPlayerBackgroundMusic.stop();
		}
	}
}
