package main.java.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Analyser implements AnalyserInterface {
	private LocalDateTime startTime; 
	private LocalDateTime endTime; 

	private boolean hasLost; 
	
	//wurde das Spiel mitten drin geschlossen?
	private boolean gameNotFinished; 
	
	private int numberOfKilledEnemies; 
	
	public Analyser(LocalDateTime start, LocalDateTime end, boolean hasLost, boolean gameNotFinished, int numberOfKilledEnemies) {
		this.startTime = start;
		this.endTime = end; 
		this.hasLost = hasLost; 
		this.numberOfKilledEnemies = numberOfKilledEnemies;
		this.gameNotFinished = gameNotFinished;
	}
	
	@Override
	public Skill getPlayerSkill() {
		long durationInSeconds = Duration.between(startTime,endTime).toSeconds(); 
		//long durationInMinutes = Duration.between(startTime,endTime).toMinutes(); 
		
		if(gameNotFinished) {
			if(durationInSeconds<20) {
				if(numberOfKilledEnemies==2) {
					return Skill.veryGood;
				}else {
					if(numberOfKilledEnemies==1) {
						return Skill.good;
					}else {
						return Skill.bad;
					}
				}
			}else {
				if(numberOfKilledEnemies>=1)
					return Skill.bad;
				else
					return Skill.veryBad;
			}
		}else {
			if(!hasLost) {
				if(durationInSeconds<20) {
					return Skill.veryGood; 
				}else {
					return Skill.good;
				}
			}else {
				if(durationInSeconds<20) {
					return Skill.veryBad; 
				}else {
					return Skill.bad; 
				}
			}
		}
		
	}

	@Override
	public LocalDateTime getGameStartTime() {
		return startTime;
	}

	@Override
	public LocalDateTime getGameEndTime() {
		return endTime;
	}


}
