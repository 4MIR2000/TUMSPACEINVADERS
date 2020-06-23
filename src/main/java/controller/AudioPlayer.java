package controller;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URL;

public class AudioPlayer {
	
	private String shotSound = "paperThrow.wav";
	private String enemyHurtSound = "enemyHurt.wav";
	private String backgroundMusic1 = "backgroundMusic1.wav";
	private final String GameOverSound = "gameOver.wav";
	private final String WinSound = "youWin.wav";
	
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
		MediaPlayer mediaPlayerDeathSound = new MediaPlayer(loadAudioFile(enemyHurtSound));
		mediaPlayerDeathSound.setVolume(0.4);
		mediaPlayerDeathSound.play();
	}
	
	public void playWinSound() {
		MediaPlayer mediaPlayerWin = new MediaPlayer(loadAudioFile(WinSound));
		mediaPlayerWin.setVolume(0.3);
		mediaPlayerWin.play();
	}
	
	public void playGameOverSound() {
		MediaPlayer mediaPlayerGameOver = new MediaPlayer(loadAudioFile(GameOverSound));
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
